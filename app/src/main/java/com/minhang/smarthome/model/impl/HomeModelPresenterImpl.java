package com.minhang.smarthome.model.impl;

import com.minhang.smarthome.model.HomeModelPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import java.io.IOException;

/**
 * author: bo on 2016/3/14 16:11.
 * email: bofu1993@163.com
 */
public class HomeModelPresenterImpl implements HomeModelPresenter {

  private static final String BASE_URL = "";
  private static final String TEMPERATURE_HUMIDITY_URL = BASE_URL + "";
  private static final String BATH_BOOK_URL = BASE_URL + "";
  private static final String SET_CONTAINER_URL = BASE_URL + "";
  private static final String SET_BATH_URL = BASE_URL + "";

  @Override
  public void getTemperatureAndHumidity(final GetTemperatureAndHumidityCallBack callBack) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        OkHttpUtils.get().url(TEMPERATURE_HUMIDITY_URL).build().execute(new StringCallback() {
          @Override
          public void onError(Call call, Exception e) {

          }

          @Override
          public void onResponse(String response) {
            callBack.getResult(response);
          }
        });
      }
    }).start();
  }

  @Override
  public void getBathAndBook(final GetBathAndBookCallBack callBack) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        OkHttpUtils.get().url(BATH_BOOK_URL).build().execute(new StringCallback() {
          @Override
          public void onError(Call call, Exception e) {

          }

          @Override
          public void onResponse(String response) {
            callBack.getResult(response);
          }
        });
      }
    }).start();
  }

  @Override
  public void setContainerLevel(int level) {
    try {
      OkHttpUtils.get().url(SET_CONTAINER_URL).build().execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setBathLevel(int level) {
    try {
      OkHttpUtils.get().url(SET_BATH_URL).build().execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
