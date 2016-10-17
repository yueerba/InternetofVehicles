package com.yueerba.internetofvehicles.internetofvehicles.util;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Music;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.activity.MusicActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerSDCardService extends Service {

    public MediaPlayer mMediaPlayer;
    private Timer timer;

    private NotificationManager mNotificationManager;

    //音乐的具体地址
    private String mMusicUrl;

    //0-idle , 1-started , 2-pause
    private int mPlayerStatus = 0;

    private ArrayList mMusicList;
    private int mMusicIndex;

    class PlayerSDCardController extends Binder implements PlayerSDCardControlInterface {
        @Override
        public void stop() {
            PlayerSDCardService.this.stop();
        }

        @Override
        public void start(int index) {
            PlayerSDCardService.this.start(index);
        }

        @Override
        public void start() {
            PlayerSDCardService.this.start();
        }

        @Override
        public void pause() {
            PlayerSDCardService.this.pause();
        }

        @Override
        public void prev() {
            PlayerSDCardService.this.prev();
        }

        @Override
        public void next() {
            PlayerSDCardService.this.next();
        }

        @Override
        public int getPlayerStatus() {
            return PlayerSDCardService.this.getPlayerStatus();
        }

        @Override
        public void seekTo(int progress) {
            PlayerSDCardService.this.seekTo(progress);
        }
    }

    private void stop() {
        mMusicIndex = 0;
        mMediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerSDCardController();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMusicList = intent.getStringArrayListExtra("musicList");
        //进入activity就播放音乐
        start(0);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public void start(int musicIndex) {
        Music music= (Music) mMusicList.get(musicIndex);
        mMusicUrl = music.getUriData();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mPlayerStatus = 0;
        }
        init();
    }

    private void init() {
        if ((mMediaPlayer == null && mMusicUrl != null) || mPlayerStatus == 0) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    next();
                }
            });
            try {
                mMediaPlayer.setDataSource(mMusicUrl);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.prepareAsync();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    addTimer();
                    mPlayerStatus = 1;
                }
            });
        }
    }

    public void start() {
        if (mMediaPlayer == null) {
            init();
        } else {
            if (mPlayerStatus == 1) {
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        addTimer();
                        mPlayerStatus = 1;
                    }
                });
            } else if (mPlayerStatus == 2 && !mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
                mPlayerStatus = 1;
            }

        }
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayerStatus = 2;
        }
    }

    public void next() {
        if (mMusicIndex == (mMusicList.size() - 1)) {
            mMusicIndex = 0;
        } else {
            ++mMusicIndex;

        }
        start(mMusicIndex);
    }

    public void prev() {
        if (mMusicIndex == 0) {
            mMusicIndex = mMusicList.size() - 1;
        } else {
            --mMusicIndex;

        }
        start(mMusicIndex);
    }

    public void release() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public int getPlayerStatus() {
        return mPlayerStatus;
    }

    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    public void addTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //获取歌曲总时长
                    int duration = mMediaPlayer.getDuration();
                    //获取歌曲当前播放进度
                    int currentPosition = mMediaPlayer.getCurrentPosition();

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
        mMediaPlayer.stop();
        //释放占用的资源
        mMediaPlayer.release();
        mMediaPlayer = null;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void updateNotifycation() {
        // 创建一个NotificationManager的引用
        mNotificationManager = (NotificationManager)
                this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Smart播放器")
                .setContentText("音乐正在播放中...");
        mNotificationManager.notify(0, mBuilder.build());
    }
}