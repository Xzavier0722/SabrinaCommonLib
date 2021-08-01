package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

public enum Request {

    REGISTER,
    LOGIN,
    GET,
    RESEND,
    RESPONSE,
    CONFIRM;

    public boolean requireConfirm() {
        return this == Request.RESPONSE;
    }

}
