package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Music;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.util.MusicListAdapter;
import com.yueerba.internetofvehicles.internetofvehicles.util.PlayerSDCardControlInterface;
import com.yueerba.internetofvehicles.internetofvehicles.util.PlayerSDCardService;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MusicActivity extends Activity {

    private int requestCode = (int) SystemClock.uptimeMillis();

    private ListView listView;

    private static TextView currentTv;
    private static SeekBar mSeekBar;
    private static TextView durationTv;

    private ImageView shangyiqu;
    private ImageView baoFangImg;
    private ImageView xiayiqu;

    private ArrayList<Music> arrayList = null;
    Music music;

    private boolean isBound = false;
    private static int size;

    public static final String MUSIC_PATH = Environment.getExternalStorageDirectory() + File.separator;

    private PlayerSDCardControlInterface playerSDCardControlInterface;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playerSDCardControlInterface = (PlayerSDCardControlInterface) service; //PlayerController对象
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    public static Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int current = bundle.getInt("currentPosition");

            currentTv.setText(toTime(current));
            durationTv.setText(toTime(duration));
            //刷新进度条的进度
            mSeekBar.setMax(duration);
            mSeekBar.setProgress(current);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 注意顺序
        setContentView(R.layout.activity_music);

        findView();

        arrayList = new ArrayList<>();
        music = new Music();

        ContentResolver musicResolver = this.getContentResolver();
        Cursor cursor = musicResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            //歌曲信息
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String uriData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            Music song = new Music(uriData, artist, size, title);
            arrayList.add(song);
        }

        MusicListAdapter adapter = new MusicListAdapter(MusicActivity.this, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playerSDCardControlInterface.start(i);
            }
        });

        size = arrayList.size();
        Toast.makeText(MusicActivity.this, size + "个", Toast.LENGTH_SHORT).show();

        baoFangImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setVisibility(View.VISIBLE);

                if (playerSDCardControlInterface == null)
                {
                    return;
                }

                if (playerSDCardControlInterface.getPlayerStatus() == 1)
                {
                    playerSDCardControlInterface.pause();
                    baoFangImg.setImageResource(R.drawable.start);
                } else if (playerSDCardControlInterface.getPlayerStatus() == 0)
                {
                    playerSDCardControlInterface.start(0);
                    baoFangImg.setImageResource(R.drawable.pause);
                } else
                {
                    playerSDCardControlInterface.start();
                    baoFangImg.setImageResource(R.drawable.pause);
                }
            }
        });

       /* mSeekBar.setVisibility(View.GONE);*/
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //改变播放进度
                playerSDCardControlInterface.seekTo(seekBar.getProgress());
            }
        });
    }

    public void findView() {
        listView = (ListView) findViewById(R.id.listView);

        currentTv = (TextView) findViewById(R.id.currentTv);
        mSeekBar = (SeekBar) findViewById(R.id.beFastBar);
        durationTv = (TextView) findViewById(R.id.durationTv);

        shangyiqu = (ImageView) findViewById(R.id.shangyiqu);
        baoFangImg = (ImageView) findViewById(R.id.baoFangImg);
        xiayiqu = (ImageView) findViewById(R.id.xiayiqu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MusicActivity.this, PlayerSDCardService.class);
        intent.putParcelableArrayListExtra("musicList",arrayList);
        startService(intent);
        bindService(new Intent("com.lzq.BIND"), connection, Context.BIND_AUTO_CREATE);
    }

    class MusicFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3"));
        }
    }

    /**
     * 得到sdcard下所有mp3文件列表
     */

    /**
     * 时间格式转换
     */

    @Override
    protected void onStop() {
        super.onStop();
        super.onStop();
        unbindService(connection);
        isBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void shangYiQu(View v) {
        Toast.makeText(MusicActivity.this, size + "个", Toast.LENGTH_SHORT).show();
        playerSDCardControlInterface.prev();
        baoFangImg.setImageResource(R.drawable.pause);
    }

    public void xiaYiQu(View v) {
        Toast.makeText(MusicActivity.this, size + "个", Toast.LENGTH_SHORT).show();
        playerSDCardControlInterface.next();
        baoFangImg.setImageResource(R.drawable.pause);
    }

    public static String toTime(int time) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            System.out.println("按下了back键 onKeyDown()");
            playerSDCardControlInterface.stop();
            finish();
            return super.onKeyDown(keyCode, event);
        } else
        {
            return false;
        }
    }

}
