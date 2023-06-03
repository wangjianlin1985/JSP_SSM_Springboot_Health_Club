﻿package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.DeviceClassService;
import com.chengxusheji.po.DeviceClass;

//DeviceClass管理控制层
@Controller
@RequestMapping("/DeviceClass")
public class DeviceClassController extends BaseController {

    /*业务层对象*/
    @Resource DeviceClassService deviceClassService;

	@InitBinder("deviceClass")
	public void initBinderDeviceClass(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("deviceClass.");
	}
	/*跳转到添加DeviceClass视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DeviceClass());
		return "DeviceClass_add";
	}

	/*客户端ajax方式提交添加设备类别信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DeviceClass deviceClass, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        deviceClassService.addDeviceClass(deviceClass);
        message = "设备类别添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询设备类别信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)deviceClassService.setRows(rows);
		List<DeviceClass> deviceClassList = deviceClassService.queryDeviceClass(page);
	    /*计算总的页数和总的记录数*/
	    deviceClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = deviceClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = deviceClassService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DeviceClass deviceClass:deviceClassList) {
			JSONObject jsonDeviceClass = deviceClass.getJsonObject();
			jsonArray.put(jsonDeviceClass);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询设备类别信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DeviceClass> deviceClassList = deviceClassService.queryAllDeviceClass();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DeviceClass deviceClass:deviceClassList) {
			JSONObject jsonDeviceClass = new JSONObject();
			jsonDeviceClass.accumulate("classId", deviceClass.getClassId());
			jsonDeviceClass.accumulate("className", deviceClass.getClassName());
			jsonArray.put(jsonDeviceClass);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询设备类别信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<DeviceClass> deviceClassList = deviceClassService.queryDeviceClass(currentPage);
	    /*计算总的页数和总的记录数*/
	    deviceClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = deviceClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = deviceClassService.getRecordNumber();
	    request.setAttribute("deviceClassList",  deviceClassList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "DeviceClass/deviceClass_frontquery_result"; 
	}

     /*前台查询DeviceClass信息*/
	@RequestMapping(value="/{classId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer classId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键classId获取DeviceClass对象*/
        DeviceClass deviceClass = deviceClassService.getDeviceClass(classId);

        request.setAttribute("deviceClass",  deviceClass);
        return "DeviceClass/deviceClass_frontshow";
	}

	/*ajax方式显示设备类别修改jsp视图页*/
	@RequestMapping(value="/{classId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer classId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键classId获取DeviceClass对象*/
        DeviceClass deviceClass = deviceClassService.getDeviceClass(classId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDeviceClass = deviceClass.getJsonObject();
		out.println(jsonDeviceClass.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新设备类别信息*/
	@RequestMapping(value = "/{classId}/update", method = RequestMethod.POST)
	public void update(@Validated DeviceClass deviceClass, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			deviceClassService.updateDeviceClass(deviceClass);
			message = "设备类别更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "设备类别更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除设备类别信息*/
	@RequestMapping(value="/{classId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer classId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  deviceClassService.deleteDeviceClass(classId);
	            request.setAttribute("message", "设备类别删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "设备类别删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条设备类别记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String classIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = deviceClassService.deleteDeviceClasss(classIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出设备类别信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<DeviceClass> deviceClassList = deviceClassService.queryDeviceClass();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DeviceClass信息记录"; 
        String[] headers = { "类别id","类别名称","类别描述"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<deviceClassList.size();i++) {
        	DeviceClass deviceClass = deviceClassList.get(i); 
        	dataset.add(new String[]{deviceClass.getClassId() + "",deviceClass.getClassName(),deviceClass.getClassDesc()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"DeviceClass.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
