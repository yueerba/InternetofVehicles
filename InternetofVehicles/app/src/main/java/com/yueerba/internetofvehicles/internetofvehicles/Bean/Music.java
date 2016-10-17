package com.yueerba.internetofvehicles.internetofvehicles.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/7/29.
 */
public class Music implements Parcelable {
    private  String uriData;
    private String name;
    private String musicName;
    private long size;

    public Music() {
    }

    public Music(String uriData, String name, long size, String musicName) {
        this.uriData = uriData;
        this.name = name;
        this.size = size;
        this.musicName = musicName;
    }

    public String getUriData() {
        return uriData;
    }

    public void setUriData(String uriData) {
        this.uriData = uriData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uriData);
        dest.writeString(this.name);
        dest.writeString(this.musicName);
        dest.writeLong(this.size);
    }

    protected Music(Parcel in) {
        this.uriData = in.readString();
        this.name = in.readString();
        this.musicName = in.readString();
        this.size = in.readLong();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
