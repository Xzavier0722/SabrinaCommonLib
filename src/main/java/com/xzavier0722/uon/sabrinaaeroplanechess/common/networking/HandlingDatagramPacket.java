package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlingDatagramPacket {

    private final List<byte[]> data = new ArrayList<>();
    private Packet packet;

    public HandlingDatagramPacket(){}

    public HandlingDatagramPacket(Packet packet) {
        this.packet = packet;
    }

    public static HandlingDatagramPacket getFor(Packet packet) {
        HandlingDatagramPacket re = new HandlingDatagramPacket(packet);

        byte[] rawData = requireNotNull(packet.getData()).getBytes();

        String headerStr =
                requireNotNull(packet.getId()) + "," +
                requireNotNull(packet.getSessionId()) + "," +
                requireNotNull(packet.getRequest()) + "," +
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
        do {
            int sliceLen = Math.min(totalLen - index, 1024);
            byte[] dataSlice = new byte[sliceLen];

            System.arraycopy(data, index, dataSlice, 0, sliceLen);

            re.accept(dataSlice);
            index += sliceLen;
        } while (index < totalLen);
        return re;
    }

    private static <T> T requireNotNull(T obj) {
        if (obj == null || (obj instanceof Integer && (int)obj < 0)) {
            throw new IllegalArgumentException("Packet should not has null data.");
        }
        return obj;
    }

    public Optional<DatagramPacket> getDatagramPacket(int slice, InetPointInfo receiverInfo) {
        byte[] data = this.data.get(slice);
        return data == null ? Optional.empty() : Optional.of(new DatagramPacket(data, data.length, receiverInfo.getAddress(), receiverInfo.getPort()));
    }

    public void accept(byte[] data) {
        this.data.add(data);
        // Set total amount
        this.data.get(0)[1] = (byte) this.data.size();
    }

    public Packet getPacket() {

        if (this.packet != null) {
            return this.packet;
        }

        if (this.data.isEmpty() || (int)this.data.get(0)[1] != this.data.size()) {
            throw new IllegalStateException("Incomplete packet!");
        }
        int len = 0;
        for (byte[] slice : this.data) {
            len += slice.length;
        }

        byte[] dataBytes = new byte[len];
        int index = 0;
        for (byte[] slice : this.data) {
            System.arraycopy(slice, 0, dataBytes, index, slice.length);
            index += slice.length;
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
        re.setRequest(Integer.parseInt(header[2]));
        re.setSequence(Integer.parseInt(header[3]));
        re.setTimestamp(Long.parseLong(header[4]));
        re.setSign(header[5]);
        re.setData(new String(dataBytes, headerIndex+1, dataBytes.length));

        return (this.packet = re);
    }

    public int getSliceCount() {
        return this.data.size();
    }

    public int getTotalSliceCount() {
        return this.data.isEmpty() ? -1 : (int)this.data.get(0)[1];
    }

    public boolean isCompleted() {
        return getSliceCount() == getTotalSliceCount();
    }

}
