package com.minhang.smarthome.model.impl;

import com.minhang.smarthome.model.HomeModelPresenter;

/**
 * author: bo on 2016/3/14 16:11.
 * email: bofu1993@163.com
 */
public class HomeModelPresenterImpl implements HomeModelPresenter {

  @Override
  public void getTemperatureAndHumidity(GetTemperatureAndHumidityCallBack callBack) {
    new Thread(new Runnable() {
      @Override
      public void run() {

      }
    }).start();
  }

  @Override
  public void getBathAndBook(GetBathAndBookCallBack callBack) {
    new Thread(new Runnable() {
      @Override
      public void run() {

      }
    }).start();
  }
}
