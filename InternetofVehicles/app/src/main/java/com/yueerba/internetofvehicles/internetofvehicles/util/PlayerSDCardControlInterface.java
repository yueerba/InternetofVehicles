package com.yueerba.internetofvehicles.internetofvehicles.util;

public interface PlayerSDCardControlInterface {

    void stop();
    void start(int index);
    void start();
    void pause();
    void seekTo(int progress);
    void prev();
    void next();
    int getPlayerStatus();
}
