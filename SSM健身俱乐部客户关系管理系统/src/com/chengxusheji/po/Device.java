package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Device {
    /*设备id*/
    private Integer deviceId;
    public Integer getDeviceId(){
        return deviceId;
    }
    public void setDeviceId(Integer deviceId){
        this.deviceId = deviceId;
    }

    /*设备类别*/
    private DeviceClass deviceClassObj;
    public DeviceClass getDeviceClassObj() {
        return deviceClassObj;
    }
    public void setDeviceClassObj(DeviceClass deviceClassObj) {
        this.deviceClassObj = deviceClassObj;
    }

    /*设备名称*/
    @NotEmpty(message="设备名称不能为空")
    private String deviceName;
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /*设备图片*/
    private String devicePhoto;
    public String getDevicePhoto() {
        return devicePhoto;
    }
    public void setDevicePhoto(String devicePhoto) {
        this.devicePhoto = devicePhoto;
    }

    /*设备单价*/
    @NotNull(message="必须输入设备单价")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*设备库存*/
    @NotNull(message="必须输入设备库存")
    private Integer deviceCount;
    public Integer getDeviceCount() {
        return deviceCount;
    }
    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    /*设备描述*/
    @NotEmpty(message="设备描述不能为空")
    private String deviceDesc;
    public String getDeviceDesc() {
        return deviceDesc;
    }
    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    /*设备位置*/
    @NotEmpty(message="设备位置不能为空")
    private String devicePlace;
    public String getDevicePlace() {
        return devicePlace;
    }
    public void setDevicePlace(String devicePlace) {
        this.devicePlace = devicePlace;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDevice=new JSONObject(); 
		jsonDevice.accumulate("deviceId", this.getDeviceId());
		jsonDevice.accumulate("deviceClassObj", this.getDeviceClassObj().getClassName());
		jsonDevice.accumulate("deviceClassObjPri", this.getDeviceClassObj().getClassId());
		jsonDevice.accumulate("deviceName", this.getDeviceName());
		jsonDevice.accumulate("devicePhoto", this.getDevicePhoto());
		jsonDevice.accumulate("price", this.getPrice());
		jsonDevice.accumulate("deviceCount", this.getDeviceCount());
		jsonDevice.accumulate("deviceDesc", this.getDeviceDesc());
		jsonDevice.accumulate("devicePlace", this.getDevicePlace());
		jsonDevice.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonDevice;
    }}