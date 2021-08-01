package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

public enum Request {

    REGISTER,
    LOGIN,
    GET,
    RESEND,
    RESPONSE,
    CONFIRM,
    QUICK_MATCH,
    GAME_ROOM,
    GAME_PROCESS,
    ERROR;

    public boolean requireConfirm() {
        switch (this) {
            case RESPONSE:
            case QUICK_MATCH:
            case GAME_PROCESS:
            case GAME_ROOM:
                return true;
        }
        return false;
    }

}
