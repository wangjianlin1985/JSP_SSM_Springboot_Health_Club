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
import com.chengxusheji.service.XiaofeiService;
import com.chengxusheji.po.Xiaofei;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Xiaofei管理控制层
@Controller
@RequestMapping("/Xiaofei")
public class XiaofeiController extends BaseController {

    /*业务层对象*/
    @Resource XiaofeiService xiaofeiService;

    @Resource UserInfoService userInfoService;
	@InitBinder("memberObj")
	public void initBindermemberObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("memberObj.");
	}
	@InitBinder("xiaofei")
	public void initBinderXiaofei(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("xiaofei.");
	}
	/*跳转到添加Xiaofei视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Xiaofei());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Xiaofei_add";
	}

	/*客户端ajax方式提交添加会员消费信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Xiaofei xiaofei, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        xiaofeiService.addXiaofei(xiaofei);
        message = "会员消费添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询会员消费信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String xiaofeiName,@ModelAttribute("memberObj") UserInfo memberObj,String xiaofeiTime,String dqsj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (xiaofeiName == null) xiaofeiName = "";
		if (xiaofeiTime == null) xiaofeiTime = "";
		if (dqsj == null) dqsj = "";
		if(rows != 0)xiaofeiService.setRows(rows);
		List<Xiaofei> xiaofeiList = xiaofeiService.queryXiaofei(xiaofeiName, memberObj, xiaofeiTime, dqsj, page);
	    /*计算总的页数和总的记录数*/
	    xiaofeiService.queryTotalPageAndRecordNumber(xiaofeiName, memberObj, xiaofeiTime, dqsj);
	    /*获取到总的页码数目*/
	    int totalPage = xiaofeiService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = xiaofeiService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Xiaofei xiaofei:xiaofeiList) {
			JSONObject jsonXiaofei = xiaofei.getJsonObject();
			jsonArray.put(jsonXiaofei);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询会员消费信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Xiaofei> xiaofeiList = xiaofeiService.queryAllXiaofei();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Xiaofei xiaofei:xiaofeiList) {
			JSONObject jsonXiaofei = new JSONObject();
			jsonXiaofei.accumulate("xiaofeiId", xiaofei.getXiaofeiId());
			jsonXiaofei.accumulate("xiaofeiName", xiaofei.getXiaofeiName());
			jsonArray.put(jsonXiaofei);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询会员消费信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String xiaofeiName,@ModelAttribute("memberObj") UserInfo memberObj,String xiaofeiTime,String dqsj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (xiaofeiName == null) xiaofeiName = "";
		if (xiaofeiTime == null) xiaofeiTime = "";
		if (dqsj == null) dqsj = "";
		List<Xiaofei> xiaofeiList = xiaofeiService.queryXiaofei(xiaofeiName, memberObj, xiaofeiTime, dqsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    xiaofeiService.queryTotalPageAndRecordNumber(xiaofeiName, memberObj, xiaofeiTime, dqsj);
	    /*获取到总的页码数目*/
	    int totalPage = xiaofeiService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = xiaofeiService.getRecordNumber();
	    request.setAttribute("xiaofeiList",  xiaofeiList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("xiaofeiName", xiaofeiName);
	    request.setAttribute("memberObj", memberObj);
	    request.setAttribute("xiaofeiTime", xiaofeiTime);
	    request.setAttribute("dqsj", dqsj);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Xiaofei/xiaofei_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询会员消费信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String xiaofeiName,@ModelAttribute("memberObj") UserInfo memberObj,String xiaofeiTime,String dqsj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (xiaofeiName == null) xiaofeiName = "";
		if (xiaofeiTime == null) xiaofeiTime = "";
		if (dqsj == null) dqsj = "";
		
		memberObj = new UserInfo();
		memberObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<Xiaofei> xiaofeiList = xiaofeiService.queryXiaofei(xiaofeiName, memberObj, xiaofeiTime, dqsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    xiaofeiService.queryTotalPageAndRecordNumber(xiaofeiName, memberObj, xiaofeiTime, dqsj);
	    /*获取到总的页码数目*/
	    int totalPage = xiaofeiService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = xiaofeiService.getRecordNumber();
	    request.setAttribute("xiaofeiList",  xiaofeiList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("xiaofeiName", xiaofeiName);
	    request.setAttribute("memberObj", memberObj);
	    request.setAttribute("xiaofeiTime", xiaofeiTime);
	    request.setAttribute("dqsj", dqsj);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Xiaofei/xiaofei_userFrontquery_result"; 
	}
	

     /*前台查询Xiaofei信息*/
	@RequestMapping(value="/{xiaofeiId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer xiaofeiId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键xiaofeiId获取Xiaofei对象*/
        Xiaofei xiaofei = xiaofeiService.getXiaofei(xiaofeiId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("xiaofei",  xiaofei);
        return "Xiaofei/xiaofei_frontshow";
	}

	/*ajax方式显示会员消费修改jsp视图页*/
	@RequestMapping(value="/{xiaofeiId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer xiaofeiId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键xiaofeiId获取Xiaofei对象*/
        Xiaofei xiaofei = xiaofeiService.getXiaofei(xiaofeiId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonXiaofei = xiaofei.getJsonObject();
		out.println(jsonXiaofei.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新会员消费信息*/
	@RequestMapping(value = "/{xiaofeiId}/update", method = RequestMethod.POST)
	public void update(@Validated Xiaofei xiaofei, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			xiaofeiService.updateXiaofei(xiaofei);
			message = "会员消费更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "会员消费更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除会员消费信息*/
	@RequestMapping(value="/{xiaofeiId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer xiaofeiId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  xiaofeiService.deleteXiaofei(xiaofeiId);
	            request.setAttribute("message", "会员消费删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "会员消费删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条会员消费记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String xiaofeiIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = xiaofeiService.deleteXiaofeis(xiaofeiIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出会员消费信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String xiaofeiName,@ModelAttribute("memberObj") UserInfo memberObj,String xiaofeiTime,String dqsj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(xiaofeiName == null) xiaofeiName = "";
        if(xiaofeiTime == null) xiaofeiTime = "";
        if(dqsj == null) dqsj = "";
        List<Xiaofei> xiaofeiList = xiaofeiService.queryXiaofei(xiaofeiName,memberObj,xiaofeiTime,dqsj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Xiaofei信息记录"; 
        String[] headers = { "消费id","消费项目","消费金额","消费会员","消费时间","到期时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<xiaofeiList.size();i++) {
        	Xiaofei xiaofei = xiaofeiList.get(i); 
        	dataset.add(new String[]{xiaofei.getXiaofeiId() + "",xiaofei.getXiaofeiName(),xiaofei.getXiaofeiMoney() + "",xiaofei.getMemberObj().getName(),xiaofei.getXiaofeiTime(),xiaofei.getDqsj()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Xiaofei.xls");//filename是下载的xls的名，建议最好用英文 
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
