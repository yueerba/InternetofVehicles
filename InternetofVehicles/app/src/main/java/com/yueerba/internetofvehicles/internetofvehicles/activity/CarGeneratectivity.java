package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Car;
import com.yueerba.internetofvehicles.internetofvehicles.R;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class CarGeneratectivity extends Activity {

    private ImageView mChineseIv;
    private Car car;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_generatectivity);

        Intent intent=this.getIntent();
        car=intent.getParcelableExtra("carZxing");
        str=car.getPingPai()+"|"+car.getCheXing()+"|"+car.getChePai()+"|"+
            car.getYouXiang()+"|"+car.getFaDongJiHao()+"|"+car.getJiBie()+"|"+
            car.getLiChengShu()+"|"+car.getFZT()+"|"+car.getBSZT()+"|"+
            car.getCDZT()+"|"+car.getShengYuYouliang();

        initView();
        createQRCode();
    }

    private void initView() {
        mChineseIv = (ImageView) findViewById(R.id.iv_chinese);
    }

    private void createQRCode() {
        createChineseQRCode();
    }

    private void createChineseQRCode() {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(str, BGAQRCodeUtil.dp2px(CarGeneratectivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mChineseIv.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(CarGeneratectivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}