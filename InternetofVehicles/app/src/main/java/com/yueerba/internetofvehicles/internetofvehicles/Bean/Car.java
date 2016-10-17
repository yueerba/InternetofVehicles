package com.yueerba.internetofvehicles.internetofvehicles.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/26.
 */

public class Car implements Parcelable {
    private int id;
    private int PP;
    private String PingPai;
    private String CheXing;
    private String ChePai;
    private String YouXiang;
    private String FaDongJiHao;
    private String JiBie;
    private String LiChengShu;
    private String FZT;
    private String BSZT;
    private String CDZT;
    private String ShengYuYouliang;

    public Car() {
    }

    public Car(int PP, String pingPai, String cheXing, String chePai, String youXiang, String faDongJiHao, String jiBie, String liChengShu, String FZT, String BSZT, String CDZT, String shengYuYouliang) {
        this.PP = PP;
        PingPai = pingPai;
        CheXing = cheXing;
        ChePai = chePai;
        YouXiang = youXiang;
        FaDongJiHao = faDongJiHao;
        JiBie = jiBie;
        LiChengShu = liChengShu;
        this.FZT = FZT;
        this.BSZT = BSZT;
        this.CDZT = CDZT;
        ShengYuYouliang = shengYuYouliang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPP() {
        return PP;
    }

    public void setPP(int PP) {
        this.PP = PP;
    }

    public String getPingPai() {
        return PingPai;
    }

    public void setPingPai(String pingPai) {
        PingPai = pingPai;
    }

    public String getCheXing() {
        return CheXing;
    }

    public void setCheXing(String cheXing) {
        CheXing = cheXing;
    }

    public String getChePai() {
        return ChePai;
    }

    public void setChePai(String chePai) {
        ChePai = chePai;
    }

    public String getYouXiang() {
        return YouXiang;
    }

    public void setYouXiang(String youXiang) {
        YouXiang = youXiang;
    }

    public String getFaDongJiHao() {
        return FaDongJiHao;
    }

    public void setFaDongJiHao(String faDongJiHao) {
        FaDongJiHao = faDongJiHao;
    }

    public String getJiBie() {
        return JiBie;
    }

    public void setJiBie(String jiBie) {
        JiBie = jiBie;
    }

    public String getLiChengShu() {
        return LiChengShu;
    }

    public void setLiChengShu(String liChengShu) {
        LiChengShu = liChengShu;
    }

    public String getFZT() {
        return FZT;
    }

    public void setFZT(String FZT) {
        this.FZT = FZT;
    }

    public String getBSZT() {
        return BSZT;
    }

    public void setBSZT(String BSZT) {
        this.BSZT = BSZT;
    }

    public String getCDZT() {
        return CDZT;
    }

    public void setCDZT(String CDZT) {
        this.CDZT = CDZT;
    }

    public String getShengYuYouliang() {
        return ShengYuYouliang;
    }

    public void setShengYuYouliang(String shengYuYouliang) {
        ShengYuYouliang = shengYuYouliang;
    }

    public static Creator<Car> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.PP);
        dest.writeString(this.PingPai);
        dest.writeString(this.CheXing);
        dest.writeString(this.ChePai);
        dest.writeString(this.YouXiang);
        dest.writeString(this.FaDongJiHao);
        dest.writeString(this.JiBie);
        dest.writeString(this.LiChengShu);
        dest.writeString(this.FZT);
        dest.writeString(this.BSZT);
        dest.writeString(this.CDZT);
        dest.writeString(this.ShengYuYouliang);
    }

    protected Car(Parcel in) {
        this.PP = in.readInt();
        this.PingPai = in.readString();
        this.CheXing = in.readString();
        this.ChePai = in.readString();
        this.YouXiang = in.readString();
        this.FaDongJiHao = in.readString();
        this.JiBie = in.readString();
        this.LiChengShu = in.readString();
        this.FZT = in.readString();
        this.BSZT = in.readString();
        this.CDZT = in.readString();
        this.ShengYuYouliang = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel source) {
            return new Car(source);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}
