package com.carl.mvpdemo.module.home.presenter;

import com.carl.mvpdemo.module.home.interfaces.MainI;
import com.carl.mvpdemo.module.home.model.YiMaDataCenter;
import com.carl.mvpdemo.pub.base.BasePresenter;
import com.carl.mvpdemo.pub.base.BaseSubscriber;
import com.carl.mvpdemo.pub.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * @author Carl
 * @version 1.0
 * @since 2018/5/10
 */
public class MainPresenter extends BasePresenter<MainI> {

    /**
     * 模拟网络请求
     */
    public void testLogin() {

        Disposable disposable = Flowable.timer(2 * 1000, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>getScheduler(true, getView()))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getView().loginSuccess("登录成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mRxManage.add(disposable);
    }


    /**
     * 登录
     */
    public void login(String username, String password) {
        BaseSubscriber baseSubscriber = new BaseSubscriber<ResponseBody>() {
            @Override
            protected void onUserSuccess(ResponseBody responseBody) {
                String text = responseBody.source().toString();

            }
        };

        Disposable disposable = YiMaDataCenter.getInstance().login(username, password)
                .compose(RxUtils.<ResponseBody>getScheduler(true, getView()))
                .subscribeWith(baseSubscriber);
        mRxManage.add(disposable);
    }

}
