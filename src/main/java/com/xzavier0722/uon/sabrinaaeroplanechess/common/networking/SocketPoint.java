package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.threading.QueuedExecutionThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.function.Consumer;

public class SocketPoint extends QueuedExecutionThread {

    private final DatagramSocket socket;
    private final Thread tListener;

    public SocketPoint(Consumer<DatagramPacket> handler) throws SocketException {
        this(-1, handler);
    }

    public SocketPoint(int port, Consumer<DatagramPacket> handler) throws SocketException {
        this.socket = port == -1 ? new DatagramSocket() : new DatagramSocket(port);

        tListener = new Thread(() -> {
            while (true) {
                //init packet to receive data
                byte[] bytes = new byte[1024];
                DatagramPacket data = new DatagramPacket(bytes, bytes.length);

                try {
                    socket.receive(data);
                    if (!schedule(() -> handler.accept(data))) {
                        System.err.println("Cannot add the incoming packet into queue: "+data.getAddress());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void start() {
        tListener.start();
        super.start();
    }

    @Override
    public void abort() {
        tListener.interrupt();
        super.abort();
    }

    public void send(DatagramPacket packet) throws IOException {
        socket.send(packet);
    }

    public void send(InetPointInfo info, DatagramPacket packet) throws IOException {
        packet.setAddress(info.getAddress());
        packet.setPort(info.getPort());
        socket.send(packet);
    }

}
