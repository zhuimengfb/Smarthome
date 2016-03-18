package com.minhang.smarthome.viewpresenter;

/**
 * author: bo on 2016/3/14 14:55.
 * email: bofu1993@163.com
 */
public interface HomeViewPresenter {

  void setTemperature(String temperature);

  void setHumidity(String humidity);

  void setLeftBook(String leftBook);

  void setRightBook(String rightBook);

  void setBathLevel(int bathLevel);

  void setConditionerLevel(int level);

  void setConditionerManual();

  void setConditionerAuto();

  void setBathManual();

  void setBathAuto();

  void showLoadingProgress();

  void hideLoadingProgress();

  String getIpAddress();

  void showContentPanel();

  void hideIpAddressPanel();

  void showBathNotStable();

  void hideBathNotStable();
}
