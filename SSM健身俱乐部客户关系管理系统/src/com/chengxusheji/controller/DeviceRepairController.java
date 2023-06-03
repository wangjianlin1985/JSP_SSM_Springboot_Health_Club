package com.chengxusheji.controller;

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
import com.chengxusheji.service.DeviceRepairService;
import com.chengxusheji.po.DeviceRepair;
import com.chengxusheji.service.DeviceService;
import com.chengxusheji.po.Device;

//DeviceRepair管理控制层
@Controller
@RequestMapping("/DeviceRepair")
public class DeviceRepairController extends BaseController {

    /*业务层对象*/
    @Resource DeviceRepairService deviceRepairService;

    @Resource DeviceService deviceService;
	@InitBinder("deviceObj")
	public void initBinderdeviceObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("deviceObj.");
	}
	@InitBinder("deviceRepair")
	public void initBinderDeviceRepair(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("deviceRepair.");
	}
	/*跳转到添加DeviceRepair视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DeviceRepair());
		/*查询所有的Device信息*/
		List<Device> deviceList = deviceService.queryAllDevice();
		request.setAttribute("deviceList", deviceList);
		return "DeviceRepair_add";
	}

	/*客户端ajax方式提交添加设备维修信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DeviceRepair deviceRepair, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        deviceRepairService.addDeviceRepair(deviceRepair);
        message = "设备维修添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询设备维修信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("deviceObj") Device deviceObj,String deviceQuestion,String repairPlace,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (deviceQuestion == null) deviceQuestion = "";
		if (repairPlace == null) repairPlace = "";
		if(rows != 0)deviceRepairService.setRows(rows);
		List<DeviceRepair> deviceRepairList = deviceRepairService.queryDeviceRepair(deviceObj, deviceQuestion, repairPlace, page);
	    /*计算总的页数和总的记录数*/
	    deviceRepairService.queryTotalPageAndRecordNumber(deviceObj, deviceQuestion, repairPlace);
	    /*获取到总的页码数目*/
	    int totalPage = deviceRepairService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = deviceRepairService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DeviceRepair deviceRepair:deviceRepairList) {
			JSONObject jsonDeviceRepair = deviceRepair.getJsonObject();
			jsonArray.put(jsonDeviceRepair);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询设备维修信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DeviceRepair> deviceRepairList = deviceRepairService.queryAllDeviceRepair();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DeviceRepair deviceRepair:deviceRepairList) {
			JSONObject jsonDeviceRepair = new JSONObject();
			jsonDeviceRepair.accumulate("repairId", deviceRepair.getRepairId());
			jsonDeviceRepair.accumulate("deviceQuestion", deviceRepair.getDeviceQuestion());
			jsonArray.put(jsonDeviceRepair);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询设备维修信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("deviceObj") Device deviceObj,String deviceQuestion,String repairPlace,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (deviceQuestion == null) deviceQuestion = "";
		if (repairPlace == null) repairPlace = "";
		List<DeviceRepair> deviceRepairList = deviceRepairService.queryDeviceRepair(deviceObj, deviceQuestion, repairPlace, currentPage);
	    /*计算总的页数和总的记录数*/
	    deviceRepairService.queryTotalPageAndRecordNumber(deviceObj, deviceQuestion, repairPlace);
	    /*获取到总的页码数目*/
	    int totalPage = deviceRepairService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = deviceRepairService.getRecordNumber();
	    request.setAttribute("deviceRepairList",  deviceRepairList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("deviceObj", deviceObj);
	    request.setAttribute("deviceQuestion", deviceQuestion);
	    request.setAttribute("repairPlace", repairPlace);
	    List<Device> deviceList = deviceService.queryAllDevice();
	    request.setAttribute("deviceList", deviceList);
		return "DeviceRepair/deviceRepair_frontquery_result"; 
	}

     /*前台查询DeviceRepair信息*/
	@RequestMapping(value="/{repairId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer repairId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键repairId获取DeviceRepair对象*/
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepair(repairId);

        List<Device> deviceList = deviceService.queryAllDevice();
        request.setAttribute("deviceList", deviceList);
        request.setAttribute("deviceRepair",  deviceRepair);
        return "DeviceRepair/deviceRepair_frontshow";
	}

	/*ajax方式显示设备维修修改jsp视图页*/
	@RequestMapping(value="/{repairId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer repairId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键repairId获取DeviceRepair对象*/
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepair(repairId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDeviceRepair = deviceRepair.getJsonObject();
		out.println(jsonDeviceRepair.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新设备维修信息*/
	@RequestMapping(value = "/{repairId}/update", method = RequestMethod.POST)
	public void update(@Validated DeviceRepair deviceRepair, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			deviceRepairService.updateDeviceRepair(deviceRepair);
			message = "设备维修更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "设备维修更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除设备维修信息*/
	@RequestMapping(value="/{repairId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer repairId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  deviceRepairService.deleteDeviceRepair(repairId);
	            request.setAttribute("message", "设备维修删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "设备维修删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条设备维修记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String repairIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = deviceRepairService.deleteDeviceRepairs(repairIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出设备维修信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("deviceObj") Device deviceObj,String deviceQuestion,String repairPlace, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(deviceQuestion == null) deviceQuestion = "";
        if(repairPlace == null) repairPlace = "";
        List<DeviceRepair> deviceRepairList = deviceRepairService.queryDeviceRepair(deviceObj,deviceQuestion,repairPlace);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DeviceRepair信息记录"; 
        String[] headers = { "维修id","维修的设备","设备问题","维修数量","送修地点","花费时间","维修费","维修结果"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<deviceRepairList.size();i++) {
        	DeviceRepair deviceRepair = deviceRepairList.get(i); 
        	dataset.add(new String[]{deviceRepair.getRepairId() + "",deviceRepair.getDeviceObj().getDeviceName(),deviceRepair.getDeviceQuestion(),deviceRepair.getRepairCount() + "",deviceRepair.getRepairPlace(),deviceRepair.getCustTime(),deviceRepair.getCostMoney() + "",deviceRepair.getRepairResult()});
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
			response.setHeader("Content-disposition","attachment; filename="+"DeviceRepair.xls");//filename是下载的xls的名，建议最好用英文 
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
