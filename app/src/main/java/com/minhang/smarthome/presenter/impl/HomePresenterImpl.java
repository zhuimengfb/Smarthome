package com.minhang.smarthome.presenter.impl;

import com.minhang.smarthome.model.HomeModelPresenter;
import com.minhang.smarthome.model.impl.HomeModelPresenterImpl;
import com.minhang.smarthome.presenter.HomePresenter;
import com.minhang.smarthome.viewpresenter.HomeViewPresenter;

/**
 * author: bo on 2016/3/14 15:42.
 * email: bofu1993@163.com
 */
public class HomePresenterImpl implements HomePresenter {

  private HomeViewPresenter homeViewPresenter;
  private HomeModelPresenter homeModelPresenter;

  public HomePresenterImpl(HomeViewPresenter homeViewPresenter) {
    this.homeViewPresenter = homeViewPresenter;
    homeModelPresenter = new HomeModelPresenterImpl();
  }

  @Override
  public void getTemperatureAndHumidity() {
    homeModelPresenter.getTemperatureAndHumidity(new HomeModelPresenter
            .GetTemperatureAndHumidityCallBack() {
      @Override
      public void getResult(String result) {
        homeViewPresenter.setTemperature(getTemperatureFromResult(result));
        homeViewPresenter.setHumidity(getHumidityFromResult(result));
        homeViewPresenter.hideLoadingProgress();
      }
    });
  }

  private String getTemperatureFromResult(String result) {
    return result.substring(3, 5);
  }

  private String getHumidityFromResult(String result) {
    return result.substring(1, 3);
  }

  @Override
  public void getBathAndBook() {
    homeModelPresenter.getBathAndBook(new HomeModelPresenter.GetBathAndBookCallBack() {
      @Override
      public void getResult(String result) {
        homeViewPresenter.setBathLevel(getBathLevelFromResult(result));
        homeViewPresenter.setLeftBook(getLeftBookFromResult(result));
        homeViewPresenter.setRightBook(getRightBookFromResult(result));
        homeViewPresenter.hideLoadingProgress();
      }
    });
  }

  private int getBathLevelFromResult(String result) {
    int level = 0;
    if (result.substring(5, 6).equals("1")) {
      switch (result.substring(6, 8)) {
        case "00":
          level = 0;
          break;
        case "01":
          level = 1;
          break;
        case "10":
          level = 2;
          break;
        case "11":
          level = 3;
          break;
        default:
          break;
      }
    }
    return level;
  }

  private String getLeftBookFromResult(String result) {
    return getBookFromResult(result.substring(1, 3));
  }

  private String getRightBookFromResult(String result) {
    return getBookFromResult(result.substring(3, 5));
  }

  private String getBookFromResult(String result) {
    String temp = "";
    switch (result) {
      case "00":
        temp = "没有书";
        break;
      case "01":
        temp = "BOOK01";
        break;
      case "10":
        temp = "BOOK02";
        break;
      case "11":
        temp = "无法识别";
        break;
      default:
        break;
    }
    return temp;
  }

  @Override
  public void setConditionerLevel(int level) {
    homeModelPresenter.setContainerLevel(level);
  }

  @Override
  public void setBathLevel(int level) {
    homeModelPresenter.setBathLevel(level);
  }

  @Override
  public void saveTemperature(String temperature) {

  }

  @Override
  public void saveHumidity(String humidity) {

  }

  @Override
  public void saveLeftBook(String leftBook) {

  }

  @Override
  public void saveRightBook(String rightBook) {

  }

  @Override
  public void saveBathLevel(int bathLevel) {

  }

  @Override
  public void saveConditionerLevel(int conditionerLevel) {

  }

  @Override
  public void saveConditionerMode(boolean isAuto) {

  }
}
