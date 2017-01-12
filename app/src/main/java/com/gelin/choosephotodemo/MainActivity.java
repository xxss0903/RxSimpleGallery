package com.gelin.choosephotodemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.litao.android.lib.Configuration;
import com.litao.android.lib.Photos;
import com.litao.android.lib.RxChoosePhoto;
import com.litao.android.lib.entity.PhotoEntry;
import com.litao.android.lib.rx.RxPhotoSubscriber;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int SINGLE_CHOOSE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSubscriber();
    }

    /**
     * 全局初始化配置参数
     */
    private void initSubscriber() {
        Configuration config = new Configuration.Builder()
                .setMaximum(6)
                .setBottomBarColor(Color.RED)
                .setTopBarColor(Color.RED)
                .hasCamera(true)
                .build();
        RxChoosePhoto.initConfig(config);
    }

    public void singleChoose(View view) {
        RxChoosePhoto
                .with(this)
                .subscribe(new RxPhotoSubscriber<Photos>() {
                    @Override
                    protected void onEvent(Photos photos) throws Exception {
                        for (PhotoEntry p : photos.getPhotos()) {
                            Log.e("John", "MainActivity#onEvent" + " # " + p.getPath());
                        }
                    }
                }).openGallerySingle();
    }

    public void multiChoose(View view) {
        RxChoosePhoto
                .with(this)
                .subscribe(new RxPhotoSubscriber<Photos>() {
                    @Override
                    protected void onEvent(Photos photos) throws Exception {
                        for (PhotoEntry p : photos.getPhotos()) {
                            Log.e("John", "MainActivity#onEvent" + " # " + p.getPath());
                        }
                    }
                }).openGalleryMulti();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SINGLE_CHOOSE_PHOTO:
                Uri photoUri = data.getData();
                cropSinglePhoto(photoUri);
                break;


            default:
                break;
        }
    }

    private void cropSinglePhoto(Uri uri) {
        Log.e("John", "MainActivity#cropSinglePhoto" + " # " + uri);
    }

    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
