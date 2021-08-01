package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HandlingDatagramPacket {

    private final Map<Integer, byte[]> data = new HashMap<>();
    private Packet packet;

    public HandlingDatagramPacket(){}

    private HandlingDatagramPacket(Packet packet) {
        this.packet = packet;
    }

    public static HandlingDatagramPacket getFor(Packet packet) {
        HandlingDatagramPacket re = new HandlingDatagramPacket(packet);

        byte[] rawData = requireNotNull(packet.getData()).getBytes();

        String headerStr =
                requireNotNull(packet.getId()) + "," +
                requireNotNull(packet.getSessionId()) + "," +
                requireNotNull(packet.getRequest()).ordinal() + "," +
                requireNotNull(packet.getSequence()) + "," +
                requireNotNull(packet.getTimestamp()) + "," +
                requireNotNull(packet.getSign()) + ";";
        byte[] header = headerStr.getBytes();

        int totalLen = 2 + header.length + rawData.length;
        byte[] data = new byte[totalLen];

        // Combine header and raw data
        // byte[0] = identifier
        // byte[1] = total packet amount
        data[0] = packet.getIdentifier();
        System.arraycopy(header, 0, data, 2, header.length);
        System.arraycopy(rawData, 0, data, 2 + header.length, rawData.length);

        // Packet separate start
        int index = 0;
        int count = 0;
        do {
            boolean isFirst = count == 0;
            int sliceLen = Math.min(totalLen - index, isFirst ? 1024 : 1023);
            byte[] dataSlice = new byte[isFirst ? sliceLen : sliceLen+1];

            if (!isFirst) {
                dataSlice[0] = (byte) count;
            }

            System.arraycopy(data, index, dataSlice, isFirst ? 0 : 1, sliceLen);

            re.accept(dataSlice);
            index += sliceLen;
            count++;
        } while (index < totalLen);
        re.updateAmount();
        return re;
    }

    private static <T> T requireNotNull(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Packet should not has null data.");
        }
        return obj;
    }

    private static Number requireNotNull(Number num) {
        if (num.longValue() < 0) {
            throw new IllegalArgumentException("Packet should not has null data.");
        }
        return num;
    }

    public Optional<DatagramPacket> getDatagramPacket(int slice, InetPointInfo receiverInfo) {
        byte[] data = this.data.get(slice);
        return data == null ? Optional.empty() : Optional.of(new DatagramPacket(data, data.length, receiverInfo.getAddress(), receiverInfo.getPort()));
    }

    public void accept(byte[] data) {
        byte seq = data[0];
        this.data.put((seq == 0x48) ? 0 : (int)seq, data);
    }

    private void updateAmount() {
        this.data.get(0)[1] = (byte) this.data.size();
    }

    public Packet getPacket() {

        if (this.packet != null) {
            return this.packet;
        }

        if (this.data.isEmpty() || (int)this.data.get(0)[1] != this.data.size()) {
            throw new IllegalStateException("Incomplete packet!");
        }

        // Get data length
        int len = 1;
        for (byte[] slice : this.data.values()) {
            len += slice.length-1;
        }

        byte[] dataBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < this.data.size(); i++) {
            byte[] slice = this.data.get(i);
            // Skip the sequence tag
            int startLoc = Math.min(i, 1);
            System.arraycopy(slice, startLoc, dataBytes, index, slice.length - startLoc);
            index += slice.length - startLoc;
        }

        // Find header delimiter ";" (0x3b)
        int headerIndex = -1;
        for (int i = 0; i < dataBytes.length; i++) {
            if (dataBytes[i] == 0x3b) {
                headerIndex = i;
                break;
            }
        }

        if (headerIndex < 0) {
            throw new IllegalStateException("Illegal packet!");
        }

        // Retrieve header
        String[] header = new String(dataBytes, 2, headerIndex-2).split(",");
        if (header.length != 6) {
            throw new IllegalStateException("Illegal header!");
        }

        // Retrieve packet
        Packet re = new Packet(dataBytes[0]);
        re.setId(Integer.parseInt(header[0]));
        re.setSessionId(header[1]);
        re.setRequest(Request.values()[Integer.parseInt(header[2])]);
        re.setSequence(Integer.parseInt(header[3]));
        re.setTimestamp(Long.parseLong(header[4]));
        re.setSign(header[5]);
        re.setData(new String(dataBytes, headerIndex+1, dataBytes.length-(headerIndex+1)));

        return (this.packet = re);
    }

    public int getSliceCount() {
        return this.data.size();
    }

    public int getTotalSliceCount() {
        byte[] first = this.data.get(0);
        return first == null ? -1 : first[1];
    }

    public List<Integer> getMissingSequence() {
        List<Integer> re = new ArrayList<>();
        int total = getTotalSliceCount();
        if (total == -1) {
            re.add(-1);
        } else {
            for (int i = 1; i < total; i++) {
                if (this.data.get(i) == null) {
                    re.add(i);
                }
            }
        }
        return re;
    }

    public boolean isCompleted() {
        return getSliceCount() == getTotalSliceCount();
    }

}
