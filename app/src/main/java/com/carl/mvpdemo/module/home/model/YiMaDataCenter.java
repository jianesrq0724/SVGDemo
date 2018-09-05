package com.carl.mvpdemo.module.home.model;

import com.carl.mvpdemo.pub.network.HttpClient;
import com.carl.mvpdemo.pub.utils.SPKeyUtils;
import com.carl.mvpdemo.pub.utils.SPUtils;

import java.util.TreeMap;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public class YiMaDataCenter {
    private static final YiMaDataCenter ourInstance = new YiMaDataCenter();
    private static YiMaService service;
    private String baseUrl = "http://api.fxhyd.cn/interface/";

    public static YiMaDataCenter getInstance() {
        return ourInstance;
    }

    private YiMaDataCenter() {
        service = HttpClient.getInstance().createService(YiMaService.class, baseUrl);
    }

    public Flowable<ResponseBody> login(String username, String password) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("action", "login");
        treeMap.put("username", username);
        treeMap.put("password", password);
        return service.login(treeMap);
    }

}
