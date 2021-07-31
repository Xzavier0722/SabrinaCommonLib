package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import java.net.InetAddress;

public class InetPointInfo {

    private final InetAddress address;
    private final int port;

    public InetPointInfo(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
