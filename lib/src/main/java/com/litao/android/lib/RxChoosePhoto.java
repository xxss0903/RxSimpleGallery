package com.litao.android.lib;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.litao.android.lib.rx.RxBus;
import com.litao.android.lib.rx.RxPhotoSubscriber;

import rx.Subscription;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class RxChoosePhoto {

    private static RxChoosePhoto mInstance;

    public static final String ARGUMENT_CONFIG = "CONFIG_PHOTOS_ACTIVITY";

    private static RxPhotoSubscriber<Photos> mPhotoSubscriber;

    private static Context mContext;

    private static Configuration mConfig;

    private RxChoosePhoto() {

    }

//    public RxChoosePhoto(Builder builder) {
//        mContext = builder.context;
//        if (builder.config != null) {
//            mConfig = builder.config;
//        } else {
//            // TODO
//        }
//
//        if (mConfig == null) {
//            throw new RuntimeException("配置文件为空");
//        }
//
//        mPhotoSubscriber = builder.photoSubscriber;
//    }

    public static void initConfig(Configuration config) {
        mConfig = config;
    }

    public static RxChoosePhoto with(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new RxChoosePhoto();
        }
        return mInstance;
    }

    public RxChoosePhoto config(Configuration config) {
        mConfig = config;
        return mInstance;
    }

    public RxChoosePhoto subscribe(RxPhotoSubscriber<Photos> subscriber) {
        mPhotoSubscriber = subscriber;
        return mInstance;
    }

//    public static class Builder {
//
//        private Context context;
//
//        private Configuration config;
//
//        private RxPhotoSubscriber<Photos> photoSubscriber;
//
//        public Builder() {
//
//        }
//
//        public Builder with(Context context) {
//            this.context = context;
//            return this;
//        }
//
//        public Builder subscribe(RxPhotoSubscriber<Photos> subscriber) {
//            this.photoSubscriber = subscriber;
//            return this;
//        }
//
//        public Builder config(Configuration config) {
//            this.config = config;
//            return this;
//        }
//
//        public RxChoosePhoto build() {
//            return new RxChoosePhoto(this);
//        }
//
//    }

    public void openGalleryMulti() {
        openGallery(false);
    }

    public void openGallerySingle() {
        openGallery(true);
    }

    private void openGallery(boolean singleSelect){
        if(singleSelect){
            mConfig.singleSelect = true;
        } else {
            mConfig.singleSelect = false;
        }
        Subscription subscription = RxBus.getDefault()
                .toObservable(Photos.class)
                .subscribe(mPhotoSubscriber);
        RxBus.getDefault().add(subscription);

        Intent intent = new Intent(mContext, PhotosActivity.class);
        intent.putExtra(ARGUMENT_CONFIG, mConfig);
        mContext.startActivity(intent);
    }
}
