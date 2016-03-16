package com.minhang.smarthome.model;

/**
 * author: bo on 2016/3/14 16:11.
 * email: bofu1993@163.com
 */
public interface HomeModelPresenter {

  void getTemperatureAndHumidity(GetTemperatureAndHumidityCallBack callBack);

  void getBathAndBook(GetBathAndBookCallBack callBack);

  void setContainerLevel(int level);

  void setBathLevel(int level);

  interface GetBathAndBookCallBack {
    void getResult(String result);
  }

  interface GetTemperatureAndHumidityCallBack {
    void  getResult(String result);
  }
}
