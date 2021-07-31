package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class SocketPoint {

    private final DatagramSocket socket;
    private final BlockingQueue<DatagramPacket> queue;
    private final Thread tListener;
    private final Thread tHandler;

    public SocketPoint(Consumer<DatagramPacket> handler) throws SocketException {
        this(-1, handler);
    }

    public SocketPoint(int port, Consumer<DatagramPacket> handler) throws SocketException {
        this.socket = port == -1 ? new DatagramSocket() : new DatagramSocket(port);
        queue = new LinkedBlockingDeque<>();

        tListener = new Thread(() -> {
            while (true) {
                //init packet to receive data
                byte[] bytes = new byte[1024];
                DatagramPacket data = new DatagramPacket(bytes, bytes.length);

                try {
                    socket.receive(data);

                    if (!queue.offer(data)) {
                        System.err.println("Cannot add the incoming packet into queue: "+data.getAddress());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        tHandler = new Thread(() -> {
            while (true) {
                try {
                    handler.accept(queue.take());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stop() {
        tListener.interrupt();
        tHandler.interrupt();
    }

    public void send(InetPointInfo info, DatagramPacket packet) throws IOException {
        packet.setAddress(info.getAddress());
        packet.setPort(info.getPort());
        socket.send(packet);
    }

}
