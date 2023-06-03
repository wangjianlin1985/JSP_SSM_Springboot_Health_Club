<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.DeviceRepair" %>
<%@ page import="com.chengxusheji.po.Device" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<DeviceRepair> deviceRepairList = (List<DeviceRepair>)request.getAttribute("deviceRepairList");
    //获取所有的deviceObj信息
    List<Device> deviceList = (List<Device>)request.getAttribute("deviceList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Device deviceObj = (Device)request.getAttribute("deviceObj");
    String deviceQuestion = (String)request.getAttribute("deviceQuestion"); //设备问题查询关键字
    String repairPlace = (String)request.getAttribute("repairPlace"); //送修地点查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>设备维修查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#deviceRepairListPanel" aria-controls="deviceRepairListPanel" role="tab" data-toggle="tab">设备维修列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>DeviceRepair/deviceRepair_frontAdd.jsp" style="display:none;">添加设备维修</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="deviceRepairListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>维修id</td><td>维修的设备</td><td>设备问题</td><td>维修数量</td><td>送修地点</td><td>花费时间</td><td>维修费</td><td>维修结果</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<deviceRepairList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		DeviceRepair deviceRepair = deviceRepairList.get(i); //获取到设备维修对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=deviceRepair.getRepairId() %></td>
 											<td><%=deviceRepair.getDeviceObj().getDeviceName() %></td>
 											<td><%=deviceRepair.getDeviceQuestion() %></td>
 											<td><%=deviceRepair.getRepairCount() %></td>
 											<td><%=deviceRepair.getRepairPlace() %></td>
 											<td><%=deviceRepair.getCustTime() %></td>
 											<td><%=deviceRepair.getCostMoney() %></td>
 											<td><%=deviceRepair.getRepairResult() %></td>
 											<td>
 												<a href="<%=basePath  %>DeviceRepair/<%=deviceRepair.getRepairId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="deviceRepairEdit('<%=deviceRepair.getRepairId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="deviceRepairDelete('<%=deviceRepair.getRepairId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>设备维修查询</h1>
		</div>
		<form name="deviceRepairQueryForm" id="deviceRepairQueryForm" action="<%=basePath %>DeviceRepair/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="deviceObj_deviceId">维修的设备：</label>
                <select id="deviceObj_deviceId" name="deviceObj.deviceId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Device deviceTemp:deviceList) {
	 					String selected = "";
 					if(deviceObj!=null && deviceObj.getDeviceId()!=null && deviceObj.getDeviceId().intValue()==deviceTemp.getDeviceId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=deviceTemp.getDeviceId() %>" <%=selected %>><%=deviceTemp.getDeviceName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="deviceQuestion">设备问题:</label>
				<input type="text" id="deviceQuestion" name="deviceQuestion" value="<%=deviceQuestion %>" class="form-control" placeholder="请输入设备问题">
			</div>






			<div class="form-group">
				<label for="repairPlace">送修地点:</label>
				<input type="text" id="repairPlace" name="repairPlace" value="<%=repairPlace %>" class="form-control" placeholder="请输入送修地点">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="deviceRepairEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;设备维修信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="deviceRepairEditForm" id="deviceRepairEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="deviceRepair_repairId_edit" class="col-md-3 text-right">维修id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="deviceRepair_repairId_edit" name="deviceRepair.repairId" class="form-control" placeholder="请输入维修id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="deviceRepair_deviceObj_deviceId_edit" class="col-md-3 text-right">维修的设备:</label>
		  	 <div class="col-md-9">
			    <select id="deviceRepair_deviceObj_deviceId_edit" name="deviceRepair.deviceObj.deviceId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_deviceQuestion_edit" class="col-md-3 text-right">设备问题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="deviceRepair_deviceQuestion_edit" name="deviceRepair.deviceQuestion" class="form-control" placeholder="请输入设备问题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_repairCount_edit" class="col-md-3 text-right">维修数量:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="deviceRepair_repairCount_edit" name="deviceRepair.repairCount" class="form-control" placeholder="请输入维修数量">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_repairPlace_edit" class="col-md-3 text-right">送修地点:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="deviceRepair_repairPlace_edit" name="deviceRepair.repairPlace" class="form-control" placeholder="请输入送修地点">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_custTime_edit" class="col-md-3 text-right">花费时间:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="deviceRepair_custTime_edit" name="deviceRepair.custTime" class="form-control" placeholder="请输入花费时间">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_costMoney_edit" class="col-md-3 text-right">维修费:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="deviceRepair_costMoney_edit" name="deviceRepair.costMoney" class="form-control" placeholder="请输入维修费">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_repairResult_edit" class="col-md-3 text-right">维修结果:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="deviceRepair_repairResult_edit" name="deviceRepair.repairResult" class="form-control" placeholder="请输入维修结果">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="deviceRepair_repairMemo_edit" class="col-md-3 text-right">维修备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="deviceRepair_repairMemo_edit" name="deviceRepair.repairMemo" rows="8" class="form-control" placeholder="请输入维修备注"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#deviceRepairEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDeviceRepairModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.deviceRepairQueryForm.currentPage.value = currentPage;
    document.deviceRepairQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.deviceRepairQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.deviceRepairQueryForm.currentPage.value = pageValue;
    documentdeviceRepairQueryForm.submit();
}

/*弹出修改设备维修界面并初始化数据*/
function deviceRepairEdit(repairId) {
	$.ajax({
		url :  basePath + "DeviceRepair/" + repairId + "/update",
		type : "get",
		dataType: "json",
		success : function (deviceRepair, response, status) {
			if (deviceRepair) {
				$("#deviceRepair_repairId_edit").val(deviceRepair.repairId);
				$.ajax({
					url: basePath + "Device/listAll",
					type: "get",
					success: function(devices,response,status) { 
						$("#deviceRepair_deviceObj_deviceId_edit").empty();
						var html="";
		        		$(devices).each(function(i,device){
		        			html += "<option value='" + device.deviceId + "'>" + device.deviceName + "</option>";
		        		});
		        		$("#deviceRepair_deviceObj_deviceId_edit").html(html);
		        		$("#deviceRepair_deviceObj_deviceId_edit").val(deviceRepair.deviceObjPri);
					}
				});
				$("#deviceRepair_deviceQuestion_edit").val(deviceRepair.deviceQuestion);
				$("#deviceRepair_repairCount_edit").val(deviceRepair.repairCount);
				$("#deviceRepair_repairPlace_edit").val(deviceRepair.repairPlace);
				$("#deviceRepair_custTime_edit").val(deviceRepair.custTime);
				$("#deviceRepair_costMoney_edit").val(deviceRepair.costMoney);
				$("#deviceRepair_repairResult_edit").val(deviceRepair.repairResult);
				$("#deviceRepair_repairMemo_edit").val(deviceRepair.repairMemo);
				$('#deviceRepairEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除设备维修信息*/
function deviceRepairDelete(repairId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "DeviceRepair/deletes",
			data : {
				repairIds : repairId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#deviceRepairQueryForm").submit();
					//location.href= basePath + "DeviceRepair/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交设备维修信息表单给服务器端修改*/
function ajaxDeviceRepairModify() {
	$.ajax({
		url :  basePath + "DeviceRepair/" + $("#deviceRepair_repairId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#deviceRepairEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#deviceRepairQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

})
</script>
</body>
</html>

