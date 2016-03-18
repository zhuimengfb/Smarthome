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
        homeViewPresenter.setTemperature(getTemperatureFromResult(parseResult(result)));
        homeViewPresenter.setHumidity(getHumidityFromResult(parseResult(result)));
        homeViewPresenter.setConditionerLevel(getConditionerLevelFromResult(parseResult(result)));
        homeViewPresenter.hideLoadingProgress();
      }
    });
  }

  private String getTemperatureFromResult(String result) {
    return result.substring(2, 4);
  }

  private String getHumidityFromResult(String result) {
    return result.substring(0, 2);
  }

  private int getConditionerLevelFromResult(String result) {
    return Integer.valueOf(result.substring(4, 5));
  }

  @Override
  public void getBathAndBook() {
    homeModelPresenter.getBathAndBook(new HomeModelPresenter.GetBathAndBookCallBack() {
      @Override
      public void getResult(String result) {
        if (getBathLevelFromResult(parseResult(result)) != -1) {
          homeViewPresenter.setBathLevel(getBathLevelFromResult(parseResult(result)));
          homeViewPresenter.hideBathNotStable();
        } else {
          homeViewPresenter.showBathNotStable();
        }
        homeViewPresenter.setLeftBook(getLeftBookFromResult(parseResult(result)));
        homeViewPresenter.setRightBook(getRightBookFromResult(parseResult(result)));
        homeViewPresenter.hideLoadingProgress();
      }
    });
  }

  private String parseResult(String result) {
    return result;
  }

  private int getBathLevelFromResult(String result) {
    int level = 0;
    if (result.substring(2, 3).equals("0")) {
      switch (result.substring(3, 4)) {
        case "0":
          level = 0;
          break;
        case "1":
          level = 1;
          break;
        case "2":
          level = 2;
          break;
        case "3":
          level = 3;
          break;
        default:
          break;
      }
    } else {
      level = -1;
    }
    return level;
  }

  private String getLeftBookFromResult(String result) {
    return getBookFromResult(result.substring(0, 1));
  }

  private String getRightBookFromResult(String result) {
    return getBookFromResult(result.substring(1, 2));
  }

  private String getBookFromResult(String result) {
    String temp = "";
    switch (result) {
      case "0":
        temp = "没有书";
        break;
      case "1":
        temp = "BOOK01";
        break;
      case "2":
        temp = "BOOK02";
        break;
      case "3":
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
