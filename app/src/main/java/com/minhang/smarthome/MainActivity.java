package com.minhang.smarthome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.minhang.smarthome.presenter.HomePresenter;
import com.minhang.smarthome.presenter.impl.HomePresenterImpl;
import com.minhang.smarthome.viewpresenter.HomeViewPresenter;

public class MainActivity extends AppCompatActivity implements HomeViewPresenter {

  private TextView temperatureTextView;
  private TextView humidityTextView;
  private TextView leftBookTextView;
  private TextView rightBookTextView;
  private Switch conditionerModeSwitch;
  private SeekBar conditionerLevelSeekBar;
  private SeekBar bathLevelSeekBar;
  private ProgressBar loadingProgressBar;
  private SharedPreferences sharedPreferences;
  private HomePresenter homePresenter = new HomePresenterImpl(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    initData();
    initEvent();
  }

  private void initEvent() {
    conditionerModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          setConditionerAuto();
        } else {
          setConditionerManual();
        }
      }
    });
    conditionerLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setConditionerLevel(progress);
        homePresenter.setConditionerLevel(progress);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
    bathLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setBathLevel(progress);
        homePresenter.setBathLevel(progress);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

  }


  private void initData() {
    sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
    setTemperature(sharedPreferences.getString("temperature", ""));
    setHumidity(sharedPreferences.getString("humidity", ""));
    setLeftBook(sharedPreferences.getString("leftBook", ""));
    setRightBook(sharedPreferences.getString("rightBook", ""));
    setBathLevel(sharedPreferences.getInt("bathLevel", 0));
  }

  private void initView() {
    temperatureTextView = (TextView) findViewById(R.id.tv_temperature_number);
    humidityTextView = (TextView) findViewById(R.id.tv_humidity_number);
    leftBookTextView = (TextView) findViewById(R.id.tv_left_book_text);
    rightBookTextView = (TextView) findViewById(R.id.tv_right_book_text);
    conditionerModeSwitch = (Switch) findViewById(R.id.s_air_conditioner);
    conditionerLevelSeekBar = (SeekBar) findViewById(R.id.sb_conditioner_level);
    bathLevelSeekBar = (SeekBar) findViewById(R.id.sb_bath_level);
    loadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
  }

  @Override
  public void setConditionerManual() {
    conditionerLevelSeekBar.setVisibility(View.GONE);
    sharedPreferences.edit().putBoolean("conditioner_auto", false).apply();
  }

  @Override
  public void setConditionerAuto() {
    conditionerLevelSeekBar.setVisibility(View.VISIBLE);
    sharedPreferences.edit().putBoolean("conditioner_auto", true).apply();
  }

  @Override
  public void setTemperature(String temperature) {
    temperatureTextView.setText(temperature);
    sharedPreferences.edit().putString("temperature", temperature).apply();
  }

  @Override
  public void setHumidity(String humidity) {
    humidityTextView.setText(humidity);
    sharedPreferences.edit().putString("humidity", humidity).apply();
  }

  @Override
  public void setLeftBook(String leftBook) {
    leftBookTextView.setText(leftBook);
    sharedPreferences.edit().putString("leftBook", leftBook).apply();
  }

  @Override
  public void setRightBook(String rightBook) {
    rightBookTextView.setText(rightBook);
    sharedPreferences.edit().putString("rightBook", rightBook).apply();
  }

  @Override
  public void setBathLevel(int bathLevel) {
    if (bathLevel <= 3 && bathLevel >= 0) {
      bathLevelSeekBar.setProgress(bathLevel);
      sharedPreferences.edit().putInt("bathLevel", bathLevel).apply();
    }
  }

  @Override
  public void setConditionerLevel(int level) {
    if (level <= 3 && level >= 0) {
      conditionerLevelSeekBar.setProgress(level);
      sharedPreferences.edit().putInt("conditionerLevel", level).apply();
    }
  }

  @Override
  public void showLoadingProgress() {
    loadingProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoadingProgress() {
    loadingProgressBar.setVisibility(View.GONE);
  }
}
