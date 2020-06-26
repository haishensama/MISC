package com.whut.work.movie.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whut.work.base.model.Page;
import com.whut.work.base.util.JavaStringUtil;
import com.whut.work.movie.dao.impl.ArrangeDaoImpl;
import com.whut.work.movie.dao.impl.HallDaoImpl;
import com.whut.work.movie.model.Hall;
import com.whut.work.movie.service.IHallService;

@Component
public class HallServiceImpl implements IHallService{

	@Autowired
	private HallDaoImpl hallDao;
	
	@Autowired
	private ArrangeDaoImpl arrangeDao;
	
	@Override
	public Map<String, Object> addOneHall(Integer row, Integer col, boolean is_available) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
	    try {
			Hall hall = new Hall();
			
			hall.setRow(row);
			hall.setCol(col);
			hall.setIs_available(is_available);
			hall.setNum(row*col);
	    	
			hallDao.save(hall);
			
			returnMap.put("value", hall);
            returnMap.put("message", "将影厅信息加入影厅表成功");
            returnMap.put("success", true);
	    	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> editOneHall(Integer id, Integer row, Integer col, boolean is_available)
			throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String hql = " from Hall where id='" + id + "' ";
		Hall hall = hallDao.findOne(hql);
		
		if (hall != null) {
			
			hall.setRow(row);
			hall.setCol(col);
			hall.setIs_available(is_available);
			hall.setNum(row*col);
			
			hallDao.update(hall);
			
			returnMap.put("value", hall);
			returnMap.put("message", "编辑成功");
			returnMap.put("success", true);
		}else {
			returnMap.put("message", "编辑失败");
			returnMap.put("success", false);
		}

		return returnMap;
	}

	@Override
	public Map<String, Object> editOneHallStatus(Integer id, boolean is_available) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String hql = " from Hall where id='" + id + "' ";
		Hall hall = hallDao.findOne(hql);
		
		if (hall != null) {
			hall.setIs_available(is_available);
			
			hallDao.update(hall);

			returnMap.put("value", hall);
			returnMap.put("message", "编辑成功");
			returnMap.put("success", true);
		}else {
			returnMap.put("message", "编辑失败");
			returnMap.put("success", false);
		}

		return returnMap;
	}

	@Override
	public Map<String, Object> deleteOneHall(Integer id) throws Exception {
		 Map<String,Object> returnMap = new HashMap<String,Object>();
		 
         String hql = " from Hall where id='" + id + "' ";
         String delhql = " delete Hall where id='" + id + "' ";
        
         String hql_arrange = " from Arrange a where a.hall.id='" + id + "' ";
         
         if (arrangeDao.findList(hql_arrange) != null) {
         	arrangeDao.deleteAll(arrangeDao.findList(hql_arrange));
 		}
          
         Hall hall = hallDao.findOne(hql);
         
         if (hall != null) {
			
        	 hallDao.deleteWithHql(delhql);
		}
         
         
         JavaStringUtil.setListInt(new ArrayList<Integer>());
         returnMap.put("message", "已从会议表中删除指定会议信息");
         returnMap.put("success", true);
         return returnMap;
	}

	@Override
	public Map<String, Object> getOneHall(Integer id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
        Hall hall = hallDao.findOne(" from Hall where id = '" + id + "' ");
        if(hall != null){
            returnMap.put("value", hall);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
        }else {
            returnMap.put("message", "获取失败");
            returnMap.put("success", false);
        }

        return returnMap;
	}

	@Override
	public Page<Hall> getHallPageList(int currentPage, int pageSize) throws Exception {
		String queryHql = "from Hall m";
		String countHql = "select count(*) from Hall m ";
		
		Page<Hall> returnPage = hallDao.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

}
