package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Xiaofei {
    /*消费id*/
    private Integer xiaofeiId;
    public Integer getXiaofeiId(){
        return xiaofeiId;
    }
    public void setXiaofeiId(Integer xiaofeiId){
        this.xiaofeiId = xiaofeiId;
    }

    /*消费项目*/
    @NotEmpty(message="消费项目不能为空")
    private String xiaofeiName;
    public String getXiaofeiName() {
        return xiaofeiName;
    }
    public void setXiaofeiName(String xiaofeiName) {
        this.xiaofeiName = xiaofeiName;
    }

    /*消费详情*/
    @NotEmpty(message="消费详情不能为空")
    private String xiaofeiDesc;
    public String getXiaofeiDesc() {
        return xiaofeiDesc;
    }
    public void setXiaofeiDesc(String xiaofeiDesc) {
        this.xiaofeiDesc = xiaofeiDesc;
    }

    /*消费金额*/
    @NotNull(message="必须输入消费金额")
    private Float xiaofeiMoney;
    public Float getXiaofeiMoney() {
        return xiaofeiMoney;
    }
    public void setXiaofeiMoney(Float xiaofeiMoney) {
        this.xiaofeiMoney = xiaofeiMoney;
    }

    /*消费会员*/
    private UserInfo memberObj;
    public UserInfo getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(UserInfo memberObj) {
        this.memberObj = memberObj;
    }

    /*消费时间*/
    @NotEmpty(message="消费时间不能为空")
    private String xiaofeiTime;
    public String getXiaofeiTime() {
        return xiaofeiTime;
    }
    public void setXiaofeiTime(String xiaofeiTime) {
        this.xiaofeiTime = xiaofeiTime;
    }

    /*到期时间*/
    @NotEmpty(message="到期时间不能为空")
    private String dqsj;
    public String getDqsj() {
        return dqsj;
    }
    public void setDqsj(String dqsj) {
        this.dqsj = dqsj;
    }

    /*消费备注*/
    private String xiaofeiMemo;
    public String getXiaofeiMemo() {
        return xiaofeiMemo;
    }
    public void setXiaofeiMemo(String xiaofeiMemo) {
        this.xiaofeiMemo = xiaofeiMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonXiaofei=new JSONObject(); 
		jsonXiaofei.accumulate("xiaofeiId", this.getXiaofeiId());
		jsonXiaofei.accumulate("xiaofeiName", this.getXiaofeiName());
		jsonXiaofei.accumulate("xiaofeiDesc", this.getXiaofeiDesc());
		jsonXiaofei.accumulate("xiaofeiMoney", this.getXiaofeiMoney());
		jsonXiaofei.accumulate("memberObj", this.getMemberObj().getName());
		jsonXiaofei.accumulate("memberObjPri", this.getMemberObj().getUser_name());
		jsonXiaofei.accumulate("xiaofeiTime", this.getXiaofeiTime().length()>19?this.getXiaofeiTime().substring(0,19):this.getXiaofeiTime());
		jsonXiaofei.accumulate("dqsj", this.getDqsj());
		jsonXiaofei.accumulate("xiaofeiMemo", this.getXiaofeiMemo());
		return jsonXiaofei;
    }}