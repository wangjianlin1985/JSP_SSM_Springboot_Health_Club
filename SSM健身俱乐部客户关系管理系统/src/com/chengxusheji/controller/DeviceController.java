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
import com.chengxusheji.service.DeviceService;
import com.chengxusheji.po.Device;
import com.chengxusheji.service.DeviceClassService;
import com.chengxusheji.po.DeviceClass;

//Device管理控制层
@Controller
@RequestMapping("/Device")
public class DeviceController extends BaseController {

    /*业务层对象*/
    @Resource DeviceService deviceService;

    @Resource DeviceClassService deviceClassService;
	@InitBinder("deviceClassObj")
	public void initBinderdeviceClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("deviceClassObj.");
	}
	@InitBinder("device")
	public void initBinderDevice(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("device.");
	}
	/*跳转到添加Device视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Device());
		/*查询所有的DeviceClass信息*/
		List<DeviceClass> deviceClassList = deviceClassService.queryAllDeviceClass();
		request.setAttribute("deviceClassList", deviceClassList);
		return "Device_add";
	}

	/*客户端ajax方式提交添加设备信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Device device, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			device.setDevicePhoto(this.handlePhotoUpload(request, "devicePhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        deviceService.addDevice(device);
        message = "设备添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询设备信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("deviceClassObj") DeviceClass deviceClassObj,String deviceName,String devicePlace,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (deviceName == null) deviceName = "";
		if (devicePlace == null) devicePlace = "";
		if (addTime == null) addTime = "";
		if(rows != 0)deviceService.setRows(rows);
		List<Device> deviceList = deviceService.queryDevice(deviceClassObj, deviceName, devicePlace, addTime, page);
	    /*计算总的页数和总的记录数*/
	    deviceService.queryTotalPageAndRecordNumber(deviceClassObj, deviceName, devicePlace, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = deviceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = deviceService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Device device:deviceList) {
			JSONObject jsonDevice = device.getJsonObject();
			jsonArray.put(jsonDevice);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询设备信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Device> deviceList = deviceService.queryAllDevice();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Device device:deviceList) {
			JSONObject jsonDevice = new JSONObject();
			jsonDevice.accumulate("deviceId", device.getDeviceId());
			jsonDevice.accumulate("deviceName", device.getDeviceName());
			jsonArray.put(jsonDevice);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询设备信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("deviceClassObj") DeviceClass deviceClassObj,String deviceName,String devicePlace,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (deviceName == null) deviceName = "";
		if (devicePlace == null) devicePlace = "";
		if (addTime == null) addTime = "";
		List<Device> deviceList = deviceService.queryDevice(deviceClassObj, deviceName, devicePlace, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    deviceService.queryTotalPageAndRecordNumber(deviceClassObj, deviceName, devicePlace, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = deviceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = deviceService.getRecordNumber();
	    request.setAttribute("deviceList",  deviceList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("deviceClassObj", deviceClassObj);
	    request.setAttribute("deviceName", deviceName);
	    request.setAttribute("devicePlace", devicePlace);
	    request.setAttribute("addTime", addTime);
	    List<DeviceClass> deviceClassList = deviceClassService.queryAllDeviceClass();
	    request.setAttribute("deviceClassList", deviceClassList);
		return "Device/device_frontquery_result"; 
	}

     /*前台查询Device信息*/
	@RequestMapping(value="/{deviceId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer deviceId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键deviceId获取Device对象*/
        Device device = deviceService.getDevice(deviceId);

        List<DeviceClass> deviceClassList = deviceClassService.queryAllDeviceClass();
        request.setAttribute("deviceClassList", deviceClassList);
        request.setAttribute("device",  device);
        return "Device/device_frontshow";
	}

	/*ajax方式显示设备修改jsp视图页*/
	@RequestMapping(value="/{deviceId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer deviceId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键deviceId获取Device对象*/
        Device device = deviceService.getDevice(deviceId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDevice = device.getJsonObject();
		out.println(jsonDevice.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新设备信息*/
	@RequestMapping(value = "/{deviceId}/update", method = RequestMethod.POST)
	public void update(@Validated Device device, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String devicePhotoFileName = this.handlePhotoUpload(request, "devicePhotoFile");
		if(!devicePhotoFileName.equals("upload/NoImage.jpg"))device.setDevicePhoto(devicePhotoFileName); 


		try {
			deviceService.updateDevice(device);
			message = "设备更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "设备更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除设备信息*/
	@RequestMapping(value="/{deviceId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer deviceId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  deviceService.deleteDevice(deviceId);
	            request.setAttribute("message", "设备删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "设备删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条设备记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String deviceIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = deviceService.deleteDevices(deviceIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出设备信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("deviceClassObj") DeviceClass deviceClassObj,String deviceName,String devicePlace,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(deviceName == null) deviceName = "";
        if(devicePlace == null) devicePlace = "";
        if(addTime == null) addTime = "";
        List<Device> deviceList = deviceService.queryDevice(deviceClassObj,deviceName,devicePlace,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Device信息记录"; 
        String[] headers = { "设备id","设备类别","设备名称","设备图片","设备单价","设备库存","设备位置","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<deviceList.size();i++) {
        	Device device = deviceList.get(i); 
        	dataset.add(new String[]{device.getDeviceId() + "",device.getDeviceClassObj().getClassName(),device.getDeviceName(),device.getDevicePhoto(),device.getPrice() + "",device.getDeviceCount() + "",device.getDevicePlace(),device.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Device.xls");//filename是下载的xls的名，建议最好用英文 
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
