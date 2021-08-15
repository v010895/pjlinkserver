package com.project.pjlinkserver.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "serverstatus_table")
public class ServerStatus {
  @PrimaryKey
  private int id;
  private boolean auth;
  private String key;
  private String power;
  private String powerStatus;
  private String input;
  private String inputStatus;
  private String avmt;
  private String avmtStatus;
  private String fanError;
  private String lampError;
  private String tempError;
  private String coverError;
  private String filterError;
  private String otherError;
  public ServerStatus(int id,
      boolean auth,
      String key,
      String power,
      String powerStatus,
      String input,
      String inputStatus,
      String avmt,
      String avmtStatus,
      String fanError,
      String lampError,
      String tempError,
      String coverError,
      String filterError,
      String otherError
      ){
    this.id = id;
    this.auth = auth;
    this.key = key;
    this.power = power;
    this.powerStatus = powerStatus;
    this.avmt = avmt;
    this.avmtStatus = avmtStatus;
    this.input = input;
    this.inputStatus = inputStatus;
    this.fanError = fanError;
    this.lampError = lampError;
    this.tempError = tempError;
    this.coverError = coverError;
    this.filterError = filterError;
    this.otherError = otherError;

  }

  public String getFanError() {
    return fanError;
  }

  public String getLampError() {
    return lampError;
  }

  public String getTempError() {
    return tempError;
  }

  public String getCoverError() {
    return coverError;
  }

  public String getFilterError() {
    return filterError;
  }

  public String getOtherError() {
    return otherError;
  }

  public int getId() {
    return id;
  }

  public boolean isAuth() {
    return auth;
  }

  public String getKey() {
    return key;
  }

  public String getPower() {
    return power;
  }

  public String getPowerStatus() {
    return powerStatus;
  }

  public String getInput() {
    return input;
  }

  public String getInputStatus() {
    return inputStatus;
  }

  public String getAvmt() {
    return avmt;
  }

  public String getAvmtStatus() {
    return avmtStatus;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setAuth(boolean auth) {
    this.auth = auth;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public void setPowerStatus(String powerStatus) {
    this.powerStatus = powerStatus;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public void setInputStatus(String inputStatus) {
    this.inputStatus = inputStatus;
  }

  public void setAvmt(String avmt) {
    this.avmt = avmt;
  }

  public void setAvmtStatus(String avmtStatus) {
    this.avmtStatus = avmtStatus;
  }

  public void setFanError(String fanError) {
    this.fanError = fanError;
  }

  public void setLampError(String lampError) {
    this.lampError = lampError;
  }

  public void setTempError(String tempError) {
    this.tempError = tempError;
  }

  public void setCoverError(String coverError) {
    this.coverError = coverError;
  }

  public void setFilterError(String filterError) {
    this.filterError = filterError;
  }

  public void setOtherError(String otherError) {
    this.otherError = otherError;
  }
}
