package com.yueerba.internetofvehicles.internetofvehicles.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Car;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9.
 */

public class CarDao {
    private Context mContext;
    private CarOpenHelper carOpenHelper;
    private SQLiteDatabase sqliteDataBase;

    //构造方法中初始化Context,SQLiteDatabase
    public CarDao(Context cxt) {
        mContext = cxt;
        carOpenHelper = new CarOpenHelper(mContext);
        sqliteDataBase = carOpenHelper.getWritableDatabase();//真正创建数据库
    }

    //添加数据
    public void insertCar(Car car) {
        String INSERT_SQL = "INSERT INTO " + CarOpenHelper.TABLE_NAME + " (" + CarOpenHelper.COLUMN_PP
                + "," + CarOpenHelper.COLUMN_PingPai + "," + CarOpenHelper.COLUMN_CheXing +"," +
                "" + CarOpenHelper.COLUMN_ChePai +"," + CarOpenHelper.COLUMN_YouXiang +"," +
                "" + CarOpenHelper.COLUMN_FaDongJiHao +","+
                "" + CarOpenHelper.COLUMN_JiBie +"," + CarOpenHelper.COLUMN_LiChengShu +"," +
                "" + CarOpenHelper.COLUMN_FZT +"," + CarOpenHelper.COLUMN_BSZT +"," +
                "" + CarOpenHelper.COLUMN_CDZT +"," + CarOpenHelper.COLUMN_ShengYuYouliang +") "
                + "VALUES (" + "'" + car.getPP() + "'" + "," + "'" + car.getPingPai() + "'" + "," + "'" +  car.getCheXing() + "'" + "," + "'" + car.getChePai() + "'" + ","
                + "'" + car.getYouXiang() + "'" + "," + "'" + car.getFaDongJiHao() + "'" + "," + "'" + car.getJiBie() + "'" + "," +
                "" + "'" + car.getLiChengShu() + "'" + "," + "'" + car.getFZT() + "'" + "," + "'" + car.getBSZT() + "'" + "" +
                "," + "'" + car.getCDZT() + "'" + "," + "'" + car.getShengYuYouliang() + "'" + ")";
        sqliteDataBase.execSQL(INSERT_SQL);
    }


    //删除数据
    public void deleteCar(int id) {
        String DELETE_SQL = "DELETE FROM " + CarOpenHelper.TABLE_NAME + " WHERE " + CarOpenHelper.COLUMN_ID
                + " = " + id;
        sqliteDataBase.execSQL(DELETE_SQL);

		/*sqliteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});*/
    }

    //查询所有数据
    public ArrayList<Car> listCar() {
        ArrayList<Car> carList = new ArrayList<Car>();
        Cursor cursor = sqliteDataBase.rawQuery("SELECT * FROM " + CarOpenHelper.TABLE_NAME
                + " WHERE " + CarOpenHelper.COLUMN_ID + " IS NOT ?", new String[] { "NULL" });
    /*    上面是两个分别是query和rawQuery的查询语句，主要区别是rawQuery是直接使用SQL语句进行查询的，
        也就是第一个参数字符串，在字符串内的“？”会被后面的String[]数组逐一对换掉；*/
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(CarOpenHelper.COLUMN_ID));
            int PP = cursor.getInt(cursor.getColumnIndex(CarOpenHelper.COLUMN_PP));
            String COLUMN_PingPai = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_PingPai));
            String COLUMN_CheXing = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_CheXing));
            String COLUMN_ChePai = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_ChePai));
            String COLUMN_YouXiang = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_YouXiang));
            String COLUMN_FaDongJiHao = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_FaDongJiHao));
            String COLUMN_JiBie = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_JiBie));
            String COLUMN_LiChengShu = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_LiChengShu));
            String COLUMN_FZT = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_FZT));
            String COLUMN_BSZT = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_BSZT));
            String COLUMN_CDZT = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_CDZT));
            String COLUMN_ShengYuYouliang = cursor.getString(cursor.getColumnIndex(CarOpenHelper.COLUMN_ShengYuYouliang));
            Car car = new Car();
            car.setId(id);
            car.setPP(PP);
            car.setPingPai(COLUMN_PingPai);
            car.setCheXing(COLUMN_CheXing);
            car.setChePai(COLUMN_ChePai);
            car.setYouXiang(COLUMN_YouXiang);
            car.setFaDongJiHao(COLUMN_FaDongJiHao);
            car.setJiBie(COLUMN_JiBie);
            car.setLiChengShu(COLUMN_LiChengShu);
            car.setFZT(COLUMN_FZT);
            car.setBSZT(COLUMN_BSZT);
            car.setCDZT(COLUMN_CDZT);
            car.setShengYuYouliang(COLUMN_ShengYuYouliang);
            carList.add(car);
        }
        cursor.close();
        return carList;
    }
}
