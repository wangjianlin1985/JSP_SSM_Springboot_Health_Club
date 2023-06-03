package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Device;
import com.chengxusheji.po.DeviceRepair;

import com.chengxusheji.mapper.DeviceRepairMapper;
@Service
public class DeviceRepairService {

	@Resource DeviceRepairMapper deviceRepairMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加设备维修记录*/
    public void addDeviceRepair(DeviceRepair deviceRepair) throws Exception {
    	deviceRepairMapper.addDeviceRepair(deviceRepair);
    }

    /*按照查询条件分页查询设备维修记录*/
    public ArrayList<DeviceRepair> queryDeviceRepair(Device deviceObj,String deviceQuestion,String repairPlace,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != deviceObj && deviceObj.getDeviceId()!= null && deviceObj.getDeviceId()!= 0)  where += " and t_deviceRepair.deviceObj=" + deviceObj.getDeviceId();
    	if(!deviceQuestion.equals("")) where = where + " and t_deviceRepair.deviceQuestion like '%" + deviceQuestion + "%'";
    	if(!repairPlace.equals("")) where = where + " and t_deviceRepair.repairPlace like '%" + repairPlace + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return deviceRepairMapper.queryDeviceRepair(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DeviceRepair> queryDeviceRepair(Device deviceObj,String deviceQuestion,String repairPlace) throws Exception  { 
     	String where = "where 1=1";
    	if(null != deviceObj && deviceObj.getDeviceId()!= null && deviceObj.getDeviceId()!= 0)  where += " and t_deviceRepair.deviceObj=" + deviceObj.getDeviceId();
    	if(!deviceQuestion.equals("")) where = where + " and t_deviceRepair.deviceQuestion like '%" + deviceQuestion + "%'";
    	if(!repairPlace.equals("")) where = where + " and t_deviceRepair.repairPlace like '%" + repairPlace + "%'";
    	return deviceRepairMapper.queryDeviceRepairList(where);
    }

    /*查询所有设备维修记录*/
    public ArrayList<DeviceRepair> queryAllDeviceRepair()  throws Exception {
        return deviceRepairMapper.queryDeviceRepairList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Device deviceObj,String deviceQuestion,String repairPlace) throws Exception {
     	String where = "where 1=1";
    	if(null != deviceObj && deviceObj.getDeviceId()!= null && deviceObj.getDeviceId()!= 0)  where += " and t_deviceRepair.deviceObj=" + deviceObj.getDeviceId();
    	if(!deviceQuestion.equals("")) where = where + " and t_deviceRepair.deviceQuestion like '%" + deviceQuestion + "%'";
    	if(!repairPlace.equals("")) where = where + " and t_deviceRepair.repairPlace like '%" + repairPlace + "%'";
        recordNumber = deviceRepairMapper.queryDeviceRepairCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取设备维修记录*/
    public DeviceRepair getDeviceRepair(int repairId) throws Exception  {
        DeviceRepair deviceRepair = deviceRepairMapper.getDeviceRepair(repairId);
        return deviceRepair;
    }

    /*更新设备维修记录*/
    public void updateDeviceRepair(DeviceRepair deviceRepair) throws Exception {
        deviceRepairMapper.updateDeviceRepair(deviceRepair);
    }

    /*删除一条设备维修记录*/
    public void deleteDeviceRepair (int repairId) throws Exception {
        deviceRepairMapper.deleteDeviceRepair(repairId);
    }

    /*删除多条设备维修信息*/
    public int deleteDeviceRepairs (String repairIds) throws Exception {
    	String _repairIds[] = repairIds.split(",");
    	for(String _repairId: _repairIds) {
    		deviceRepairMapper.deleteDeviceRepair(Integer.parseInt(_repairId));
    	}
    	return _repairIds.length;
    }
}
