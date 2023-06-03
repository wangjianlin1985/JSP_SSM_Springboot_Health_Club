package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceRepair {
    /*维修id*/
    private Integer repairId;
    public Integer getRepairId(){
        return repairId;
    }
    public void setRepairId(Integer repairId){
        this.repairId = repairId;
    }

    /*维修的设备*/
    private Device deviceObj;
    public Device getDeviceObj() {
        return deviceObj;
    }
    public void setDeviceObj(Device deviceObj) {
        this.deviceObj = deviceObj;
    }

    /*设备问题*/
    @NotEmpty(message="设备问题不能为空")
    private String deviceQuestion;
    public String getDeviceQuestion() {
        return deviceQuestion;
    }
    public void setDeviceQuestion(String deviceQuestion) {
        this.deviceQuestion = deviceQuestion;
    }

    /*维修数量*/
    @NotNull(message="必须输入维修数量")
    private Integer repairCount;
    public Integer getRepairCount() {
        return repairCount;
    }
    public void setRepairCount(Integer repairCount) {
        this.repairCount = repairCount;
    }

    /*送修地点*/
    @NotEmpty(message="送修地点不能为空")
    private String repairPlace;
    public String getRepairPlace() {
        return repairPlace;
    }
    public void setRepairPlace(String repairPlace) {
        this.repairPlace = repairPlace;
    }

    /*花费时间*/
    @NotEmpty(message="花费时间不能为空")
    private String custTime;
    public String getCustTime() {
        return custTime;
    }
    public void setCustTime(String custTime) {
        this.custTime = custTime;
    }

    /*维修费*/
    @NotNull(message="必须输入维修费")
    private Float costMoney;
    public Float getCostMoney() {
        return costMoney;
    }
    public void setCostMoney(Float costMoney) {
        this.costMoney = costMoney;
    }

    /*维修结果*/
    @NotEmpty(message="维修结果不能为空")
    private String repairResult;
    public String getRepairResult() {
        return repairResult;
    }
    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    /*维修备注*/
    private String repairMemo;
    public String getRepairMemo() {
        return repairMemo;
    }
    public void setRepairMemo(String repairMemo) {
        this.repairMemo = repairMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDeviceRepair=new JSONObject(); 
		jsonDeviceRepair.accumulate("repairId", this.getRepairId());
		jsonDeviceRepair.accumulate("deviceObj", this.getDeviceObj().getDeviceName());
		jsonDeviceRepair.accumulate("deviceObjPri", this.getDeviceObj().getDeviceId());
		jsonDeviceRepair.accumulate("deviceQuestion", this.getDeviceQuestion());
		jsonDeviceRepair.accumulate("repairCount", this.getRepairCount());
		jsonDeviceRepair.accumulate("repairPlace", this.getRepairPlace());
		jsonDeviceRepair.accumulate("custTime", this.getCustTime());
		jsonDeviceRepair.accumulate("costMoney", this.getCostMoney());
		jsonDeviceRepair.accumulate("repairResult", this.getRepairResult());
		jsonDeviceRepair.accumulate("repairMemo", this.getRepairMemo());
		return jsonDeviceRepair;
    }}