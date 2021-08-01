package com.xzavier0722.uon.sabrinaaeroplanechess.common.threading;

public interface QueuedTask {

    default int getDelay() {
        return 0;
    }

    void execute();

}
