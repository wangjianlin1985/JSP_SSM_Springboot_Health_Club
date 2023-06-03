package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DeviceRepair;

public interface DeviceRepairMapper {
	/*添加设备维修信息*/
	public void addDeviceRepair(DeviceRepair deviceRepair) throws Exception;

	/*按照查询条件分页查询设备维修记录*/
	public ArrayList<DeviceRepair> queryDeviceRepair(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有设备维修记录*/
	public ArrayList<DeviceRepair> queryDeviceRepairList(@Param("where") String where) throws Exception;

	/*按照查询条件的设备维修记录数*/
	public int queryDeviceRepairCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条设备维修记录*/
	public DeviceRepair getDeviceRepair(int repairId) throws Exception;

	/*更新设备维修记录*/
	public void updateDeviceRepair(DeviceRepair deviceRepair) throws Exception;

	/*删除设备维修记录*/
	public void deleteDeviceRepair(int repairId) throws Exception;

}
