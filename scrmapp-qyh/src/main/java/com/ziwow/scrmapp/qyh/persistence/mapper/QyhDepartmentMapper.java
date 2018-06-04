package com.ziwow.scrmapp.qyh.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhDepartment;

public interface QyhDepartmentMapper extends GenericMapper<QyhDepartment, Long> {
	public void batchInsertDepartment(@Param("departments") List<QyhDepartment> departments);
	public void batchUpdateDepartment(@Param("departments") List<QyhDepartment> departments);
	public void batchDeleteDepartment(@Param("departmentId") List<Integer> departmentId, @Param("corpId") String corpId);
	public QyhDepartment getDepartmentByName(@Param("deptName") String deptName, @Param("corpId") String corpId);
}