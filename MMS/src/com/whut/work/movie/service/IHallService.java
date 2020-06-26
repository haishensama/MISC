package com.whut.work.movie.service;

import java.util.Map;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Hall;

public interface IHallService {

		//新增影厅
		public Map<String,Object> addOneHall(Integer row,Integer col, boolean is_available) throws Exception;
		
		//编辑
		public Map<String,Object> editOneHall(Integer id, Integer row, Integer col, boolean is_available) throws Exception;
		
		//编辑状态
		public Map<String,Object> editOneHallStatus(Integer id, boolean is_available) throws Exception;
		
		//删除
		public Map<String,Object> deleteOneHall(Integer id) throws Exception;
	    
		//根据id获取
	    public Map<String,Object> getOneHall(Integer id) throws Exception;
	    
	    //getPage
	    public Page<Hall>getHallPageList(int currentPage, int pageSize) throws Exception; 

		
}
