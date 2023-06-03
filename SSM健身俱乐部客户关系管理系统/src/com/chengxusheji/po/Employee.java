package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Employee {
    /*员工号*/
    @NotEmpty(message="员工号不能为空")
    private String employeeNo;
    public String getEmployeeNo(){
        return employeeNo;
    }
    public void setEmployeeNo(String employeeNo){
        this.employeeNo = employeeNo;
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

    /*所在部门*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
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

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String birthDate;
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /*员工照片*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*邮箱*/
    @NotEmpty(message="邮箱不能为空")
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

    /*员工档案变动*/
    @NotEmpty(message="员工档案变动不能为空")
    private String employeeDesc;
    public String getEmployeeDesc() {
        return employeeDesc;
    }
    public void setEmployeeDesc(String employeeDesc) {
        this.employeeDesc = employeeDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonEmployee=new JSONObject(); 
		jsonEmployee.accumulate("employeeNo", this.getEmployeeNo());
		jsonEmployee.accumulate("password", this.getPassword());
		jsonEmployee.accumulate("departmentObj", this.getDepartmentObj().getDepartmentName());
		jsonEmployee.accumulate("departmentObjPri", this.getDepartmentObj().getDepartmentNo());
		jsonEmployee.accumulate("name", this.getName());
		jsonEmployee.accumulate("gender", this.getGender());
		jsonEmployee.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonEmployee.accumulate("userPhoto", this.getUserPhoto());
		jsonEmployee.accumulate("telephone", this.getTelephone());
		jsonEmployee.accumulate("email", this.getEmail());
		jsonEmployee.accumulate("address", this.getAddress());
		jsonEmployee.accumulate("employeeDesc", this.getEmployeeDesc());
		return jsonEmployee;
    }}