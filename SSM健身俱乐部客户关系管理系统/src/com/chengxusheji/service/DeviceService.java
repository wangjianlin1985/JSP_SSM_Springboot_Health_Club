package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DeviceClass;
import com.chengxusheji.po.Device;

import com.chengxusheji.mapper.DeviceMapper;
@Service
public class DeviceService {

	@Resource DeviceMapper deviceMapper;
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

    /*添加设备记录*/
    public void addDevice(Device device) throws Exception {
    	deviceMapper.addDevice(device);
    }

    /*按照查询条件分页查询设备记录*/
    public ArrayList<Device> queryDevice(DeviceClass deviceClassObj,String deviceName,String devicePlace,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != deviceClassObj && deviceClassObj.getClassId()!= null && deviceClassObj.getClassId()!= 0)  where += " and t_device.deviceClassObj=" + deviceClassObj.getClassId();
    	if(!deviceName.equals("")) where = where + " and t_device.deviceName like '%" + deviceName + "%'";
    	if(!devicePlace.equals("")) where = where + " and t_device.devicePlace like '%" + devicePlace + "%'";
    	if(!addTime.equals("")) where = where + " and t_device.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return deviceMapper.queryDevice(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Device> queryDevice(DeviceClass deviceClassObj,String deviceName,String devicePlace,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != deviceClassObj && deviceClassObj.getClassId()!= null && deviceClassObj.getClassId()!= 0)  where += " and t_device.deviceClassObj=" + deviceClassObj.getClassId();
    	if(!deviceName.equals("")) where = where + " and t_device.deviceName like '%" + deviceName + "%'";
    	if(!devicePlace.equals("")) where = where + " and t_device.devicePlace like '%" + devicePlace + "%'";
    	if(!addTime.equals("")) where = where + " and t_device.addTime like '%" + addTime + "%'";
    	return deviceMapper.queryDeviceList(where);
    }

    /*查询所有设备记录*/
    public ArrayList<Device> queryAllDevice()  throws Exception {
        return deviceMapper.queryDeviceList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(DeviceClass deviceClassObj,String deviceName,String devicePlace,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != deviceClassObj && deviceClassObj.getClassId()!= null && deviceClassObj.getClassId()!= 0)  where += " and t_device.deviceClassObj=" + deviceClassObj.getClassId();
    	if(!deviceName.equals("")) where = where + " and t_device.deviceName like '%" + deviceName + "%'";
    	if(!devicePlace.equals("")) where = where + " and t_device.devicePlace like '%" + devicePlace + "%'";
    	if(!addTime.equals("")) where = where + " and t_device.addTime like '%" + addTime + "%'";
        recordNumber = deviceMapper.queryDeviceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取设备记录*/
    public Device getDevice(int deviceId) throws Exception  {
        Device device = deviceMapper.getDevice(deviceId);
        return device;
    }

    /*更新设备记录*/
    public void updateDevice(Device device) throws Exception {
        deviceMapper.updateDevice(device);
    }

    /*删除一条设备记录*/
    public void deleteDevice (int deviceId) throws Exception {
        deviceMapper.deleteDevice(deviceId);
    }

    /*删除多条设备信息*/
    public int deleteDevices (String deviceIds) throws Exception {
    	String _deviceIds[] = deviceIds.split(",");
    	for(String _deviceId: _deviceIds) {
    		deviceMapper.deleteDevice(Integer.parseInt(_deviceId));
    	}
    	return _deviceIds.length;
    }
}
