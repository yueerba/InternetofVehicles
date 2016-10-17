package com.yueerba.internetofvehicles.internetofvehicles.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/9.
 */

public class CarOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;//此处不为1时表示数据库要进行更新，需要调用onUpgrade

    private static final String DB_NAME = "cars.db";
    public static final String TABLE_NAME = "CAR";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PP = "PP";
    public static final String COLUMN_PingPai = "PingPai";
    public static final String COLUMN_CheXing = "CheXing";
    public static final String COLUMN_ChePai = "ChePai";
    public static final String COLUMN_YouXiang = "YouXiang";
    public static final String COLUMN_FaDongJiHao = "FaDongJiHao";
    public static final String COLUMN_JiBie = "JiBie";
    public static final String COLUMN_LiChengShu = "LiChengShu";
    public static final String COLUMN_FZT = "FZT";
    public static final String COLUMN_BSZT = "BSZT";
    public static final String COLUMN_CDZT = "CDZT";
    public static final String COLUMN_ShengYuYouliang = "ShengYuYouliang";

    public CarOpenHelper(Context context) {
        //创建数据库
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        //创建表
        sqlitedatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PP + " INTEGER,"
                + COLUMN_PingPai + " TEXT,"
                + COLUMN_CheXing + " TEXT,"
                + COLUMN_ChePai + " TEXT,"
                + COLUMN_YouXiang + " TEXT,"
                + COLUMN_FaDongJiHao + " TEXT,"
                + COLUMN_JiBie + " TEXT,"
                + COLUMN_LiChengShu + " TEXT,"
                + COLUMN_FZT + " TEXT,"
                + COLUMN_BSZT + " TEXT,"
                + COLUMN_CDZT + " TEXT,"
                + COLUMN_ShengYuYouliang + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
        //修改表,创建新表,数据备份,删除表
        sqlitedatabase.execSQL("ALTER TABLE" + TABLE_NAME + " RENAME TO CAR_TEMP");
        sqlitedatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(_id integer primary key," +
                "PP integer,PingPai varchar(20),CheXing varchar(20),ChePai varchar(20)," +
                "YouXiang varchar(20),FaDongJiHao varchar(20),JiBie varchar(20)," +
                "LiChengShu varchar(20),FZT varchar(20),BSZT varchar(20)," +
                "CDZT varchar(20),ShengYuYouliang varchar(20))");
        sqlitedatabase.execSQL("INSERT INTO " + TABLE_NAME + "(_id,PP,PingPai,CheXing,ChePai" +
                "YouXiang,FaDongJiHao,JiBie,LiChengShu,FZT,BSZT,CDZT,ShengYuYouliang)");
    }
}
