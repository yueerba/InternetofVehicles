package com.yueerba.internetofvehicles.internetofvehicles.util;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.yueerba.internetofvehicles.internetofvehicles.activity.MusicActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerService extends Service{

    private MediaPlayer mediaPlayer;
    private Timer timer;

    private NotificationManager mNotificationManager;

    class PlayerController extends Binder implements PlayerControlInterface{
        @Override
        public void play(String musicName) {
            PlayerService.this.play(musicName);
        }

        @Override
        public void pause() {
            PlayerService.this.pause();
        }

        @Override
        public void continuePlay() {
            PlayerService.this.continuePlay();
        }

        @Override
        public void seekTo(int progress) {
            PlayerService.this.seekTo(progress);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerController();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    //播放音乐
    public void play(String musicName){
        //重置
        mediaPlayer.reset();
        try {
            //加载多媒体文件
            mediaPlayer.setDataSource(musicName);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //准备完毕时，此方法调用
                @Override
                public void onPrepared(MediaPlayer player) {
                    player.start();
                    addTimer();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //继续播放
    public void continuePlay(){
        mediaPlayer.start();
    }

    //暂停播放
    public void pause(){
        mediaPlayer.pause();
    }

    public void seekTo(int progress){
        mediaPlayer.seekTo(progress);
    }

    public void addTimer(){
        if(timer == null){
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    //获取歌曲总时长
                    int duration = mediaPlayer.getDuration();
                    //获取歌曲当前播放进度
                    int currentPosition= mediaPlayer.getCurrentPosition();

                    Message msg = MusicActivity.handler.obtainMessage();
                    //把进度封装至消息对象中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    msg.setData(bundle);
                    MusicActivity.handler.sendMessage(msg);

                }
                //开始计时任务后的5毫秒，第一次执行run方法，以后每500毫秒执行一次
            }, 5, 500);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止播放
        mediaPlayer.stop();
        //释放占用的资源
        mediaPlayer.release();
        mediaPlayer = null;
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}