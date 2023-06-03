package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
    /*会员名*/
    @NotEmpty(message="会员名不能为空")
    private String user_name;
    public String getUser_name(){
        return user_name;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*职业*/
    @NotEmpty(message="职业不能为空")
    private String zhiye;
    public String getZhiye() {
        return zhiye;
    }
    public void setZhiye(String zhiye) {
        this.zhiye = zhiye;
    }

    /*生日*/
    @NotEmpty(message="生日不能为空")
    private String birthDate;
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /*会员照片*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*联系方式*/
    @NotEmpty(message="联系方式不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*Email*/
    @NotEmpty(message="Email不能为空")
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*健身目标*/
    @NotEmpty(message="健身目标不能为空")
    private String jsmb;
    public String getJsmb() {
        return jsmb;
    }
    public void setJsmb(String jsmb) {
        this.jsmb = jsmb;
    }

    /*健身习惯*/
    @NotEmpty(message="健身习惯不能为空")
    private String jsxg;
    public String getJsxg() {
        return jsxg;
    }
    public void setJsxg(String jsxg) {
        this.jsxg = jsxg;
    }

    /*客户备注*/
    private String khbz;
    public String getKhbz() {
        return khbz;
    }
    public void setKhbz(String khbz) {
        this.khbz = khbz;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonUserInfo=new JSONObject(); 
		jsonUserInfo.accumulate("user_name", this.getUser_name());
		jsonUserInfo.accumulate("password", this.getPassword());
		jsonUserInfo.accumulate("name", this.getName());
		jsonUserInfo.accumulate("gender", this.getGender());
		jsonUserInfo.accumulate("zhiye", this.getZhiye());
		jsonUserInfo.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonUserInfo.accumulate("userPhoto", this.getUserPhoto());
		jsonUserInfo.accumulate("telephone", this.getTelephone());
		jsonUserInfo.accumulate("email", this.getEmail());
		jsonUserInfo.accumulate("address", this.getAddress());
		jsonUserInfo.accumulate("jsmb", this.getJsmb());
		jsonUserInfo.accumulate("jsxg", this.getJsxg());
		jsonUserInfo.accumulate("khbz", this.getKhbz());
		jsonUserInfo.accumulate("regTime", this.getRegTime().length()>19?this.getRegTime().substring(0,19):this.getRegTime());
		return jsonUserInfo;
    }}