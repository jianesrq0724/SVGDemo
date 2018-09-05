package com.carl.mvpdemo.pub.base;

import com.carl.mvpdemo.pub.network.bean.ResBase;
import com.carl.mvpdemo.pub.utils.ToastUtils;

import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

/**
 * @author Carl
 * version 1.0
 * @since 2018/6/11
 */
public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T t) {

        if (t instanceof ResponseBody) {
            String s = ((ResponseBody) t).source().toString();
            if (s.contains("success")) {
                onUserSuccess(t);
            } else if (s.contains("1004")) {
                onTokenFail();
            } else {
                onUserFail(t);
            }
        } else if (t instanceof ResBase) {
            if (((ResBase) t).code.equals("200")) {
                onUserSuccess(t);
            } else {
                onUserFail(t);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onUserError(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onUserSuccess(T t);

    protected void onTokenFail() {
        ToastUtils.showLong("token fail");
    }

    protected void onUserFail(T t) {
        ToastUtils.showLong("onUserFail");
    }

    protected void onUserError(Throwable t) {
        ToastUtils.showLong(t.getMessage());
    }
}
