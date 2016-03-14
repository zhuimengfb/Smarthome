package com.minhang.smarthome.presenter;

/**
 * author: bo on 2016/3/14 15:42.
 * email: bofu1993@163.com
 */
public interface HomePresenter {

  void getTemperatureAndHumidity();

  void getBathAndBook();

  void setConditionerLevel(int level);

  void setBathLevel(int level);

  void saveTemperature(String temperature);

  void saveHumidity(String humidity);

  void saveLeftBook(String leftBook);

  void saveRightBook(String rightBook);

  void saveBathLevel(int bathLevel);

  void saveConditionerLevel(int conditionerLevel);

  void saveConditionerMode(boolean isAuto);
}
