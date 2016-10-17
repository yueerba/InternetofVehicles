package com.yueerba.internetofvehicles.internetofvehicles.util;

public interface PlayerControlInterface {

    void play(String musicName);
    void pause();
    void continuePlay();
    void seekTo(int progress);
}
