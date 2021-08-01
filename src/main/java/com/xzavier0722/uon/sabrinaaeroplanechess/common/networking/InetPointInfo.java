package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class InetPointInfo {

    private final InetAddress address;
    private final int port;

    public InetPointInfo(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public static InetPointInfo get(DatagramPacket packet) {
        return new InetPointInfo(packet.getAddress(), packet.getPort());
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return address.toString()+":"+port;
    }
}
