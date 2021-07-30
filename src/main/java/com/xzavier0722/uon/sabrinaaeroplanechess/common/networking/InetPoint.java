package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import com.google.gson.Gson;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class InetPoint {

    private final InetAddress address;
    private final int port;

    public InetPoint(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void send(DatagramSocket socket, Packet packet) throws IOException {
        byte[] data = Utils.getGson().toJson(packet).getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(data, data.length, address, port));
    }
}
