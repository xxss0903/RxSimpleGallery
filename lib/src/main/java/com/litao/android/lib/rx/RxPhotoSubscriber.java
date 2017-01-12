package com.litao.android.lib.rx;


import com.litao.android.lib.Photos;

import rx.Subscriber;

/**
 * 订阅类
 * Created by Administrator on 2017/1/11 0011.
 */

public abstract class RxPhotoSubscriber<T extends Photos> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        try {
            onEvent(t);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected abstract void onEvent(T t) throws Exception;
}
