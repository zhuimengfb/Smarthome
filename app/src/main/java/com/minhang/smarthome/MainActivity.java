package com.minhang.smarthome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.minhang.smarthome.model.impl.HomeModelPresenterImpl;
import com.minhang.smarthome.presenter.HomePresenter;
import com.minhang.smarthome.presenter.impl.HomePresenterImpl;
import com.minhang.smarthome.utils.ToastUtil;
import com.minhang.smarthome.viewpresenter.HomeViewPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeViewPresenter {

  @Bind(R.id.etIpAddress)
  EditText ipAddressEditText;
  @Bind(R.id.btConfirm)
  Button ipAddressConfirmButton;
  @Bind(R.id.refresh_layout)
  SwipeRefreshLayout swipeRefreshLayout;
  @Bind(R.id.tv_temperature_number)
  TextView temperatureTextView;
  @Bind(R.id.tv_humidity_number)
  TextView humidityTextView;
  @Bind(R.id.tv_left_book_text)
  TextView leftBookTextView;
  @Bind(R.id.tv_right_book_text)
  TextView rightBookTextView;
  @Bind(R.id.s_air_conditioner)
  Switch conditionerModeSwitch;
  @Bind(R.id.s_bath)
  Switch bathModeSwitch;
  @Bind(R.id.sb_conditioner_level)
  SeekBar conditionerLevelSeekBar;
  @Bind(R.id.sb_bath_level)
  SeekBar bathLevelSeekBar;
  @Bind(R.id.tv_bath_stable)
  TextView bathStableTextView;
  @Bind(R.id.contentPanel)
  LinearLayout contentLinearLayout;
  @Bind(R.id.netPanel)
  CardView ipAddressCardView;
  private SharedPreferences sharedPreferences;
  private boolean isIpAddressConfirmed = false;
  private boolean isConditionerAutoMode = true;
  private boolean isBathAutoMode = true;
  private HomePresenter homePresenter = new HomePresenterImpl(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initData();
    initEvent();
  }

  private void initEvent() {
    conditionerModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          setConditionerManual();
        } else {
          setConditionerAuto();
        }
      }
    });
    bathModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          setBathManual();
        } else {
          setBathAuto();
        }
      }
    });
    conditionerLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser && isConditionerAutoMode) {
          setConditionerLevel(sharedPreferences.getInt("conditionerLevel", 0));
          ToastUtil.showToast(getApplicationContext(), "目前处于自动状态，不能手动调节");
          return;
        }
        setConditionerLevel(progress);
        if (!isConditionerAutoMode) {
          homePresenter.setConditionerLevel(progress);
        }
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
        if (fromUser && isBathAutoMode) {
          setBathLevel(sharedPreferences.getInt("bathLevel", 0));
          ToastUtil.showToast(getApplicationContext(), "目前处于自动状态，不能手动调节");
          return;
        }
        setBathLevel(progress);
        if (!isBathAutoMode) {
          homePresenter.setBathLevel(progress);
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
    setSwipeRefreshEnable();
    ipAddressConfirmButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        HomeModelPresenterImpl.BASE_URL = "http://" + getIpAddress();
        isIpAddressConfirmed = true;
        hideIpAddressPanel();
        showContentPanel();
        swipeRefreshLayout.setRefreshing(true);
        homePresenter.getTemperatureAndHumidity();
        homePresenter.getBathAndBook();
      }
    });
  }

  private void setSwipeRefreshEnable() {
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        if (isIpAddressConfirmed) {
          homePresenter.getTemperatureAndHumidity();
          homePresenter.getBathAndBook();
        } else {
          ToastUtil.showToast(getApplicationContext(), "目前处于自动状态，不能手动调节");
          swipeRefreshLayout.setRefreshing(false);
        }
      }
    });
  }

  private void initData() {
    sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
  }


  @Override
  public void setConditionerManual() {
    sharedPreferences.edit().putBoolean("conditioner_auto", false).apply();
    isConditionerAutoMode = false;
  }

  @Override
  public void setConditionerAuto() {
    isConditionerAutoMode = true;
    sharedPreferences.edit().putBoolean("conditioner_auto", true).apply();
  }

  @Override
  public void setBathManual() {
    sharedPreferences.edit().putBoolean("bath_auto", false).apply();
    isBathAutoMode = false;
  }

  @Override
  public void setBathAuto() {
    isBathAutoMode = true;
    sharedPreferences.edit().putBoolean("bath_auto", true).apply();
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
    swipeRefreshLayout.setRefreshing(true);
  }

  @Override
  public void hideLoadingProgress() {
    swipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public String getIpAddress() {
    return ipAddressEditText.getEditableText().toString();
  }

  @Override
  public void showContentPanel() {
    contentLinearLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideIpAddressPanel() {
    ipAddressCardView.setVisibility(View.GONE);
  }

  @Override
  public void showBathNotStable() {
    bathStableTextView.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideBathNotStable() {
    bathStableTextView.setVisibility(View.GONE);
  }
}
