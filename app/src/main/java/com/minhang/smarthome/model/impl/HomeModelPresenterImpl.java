package com.minhang.smarthome.model.impl;

import com.minhang.smarthome.model.HomeModelPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * author: bo on 2016/3/14 16:11.
 * email: bofu1993@163.com
 */
public class HomeModelPresenterImpl implements HomeModelPresenter {

  public static String BASE_URL = "";

  @Override
  public void getTemperatureAndHumidity(final GetTemperatureAndHumidityCallBack callBack) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        OkHttpUtils.get().url(BASE_URL + "/arduino/temphum/13").build().execute(new StringCallback() {


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
        OkHttpUtils.get().url(BASE_URL + "/arduino/bookbath/13").build().execute(new StringCallback() {


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
    OkHttpUtils.get().url(BASE_URL + "/arduino/air/" + String.valueOf(level)).build().execute(new StringCallback() {


      @Override
      public void onError(Call call, Exception e) {

      }

      @Override
      public void onResponse(String response) {

      }
    });
  }

  @Override
  public void setBathLevel(int level) {
    OkHttpUtils.get().url(BASE_URL + "/arduino/bathtub/" + String.valueOf(level)).build()
            .execute(new StringCallback() {
              @Override
              public void onError(Call call, Exception e) {

              }

              @Override
              public void onResponse(String response) {

              }
            });
  }
}
