package com.whut.work.movie.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Hall;
import com.whut.work.movie.service.IHallService;

@Controller
@RequestMapping("/hall")
public class HallCtrl {
	
	@Autowired
	private IHallService hallService;
	
	@RequestMapping(value="/addOneHall",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addOneHall(Integer row,Integer col, boolean is_available){
	
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = hallService.addOneHall(row, col, is_available);
        	 returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：新增影厅失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	@RequestMapping(value="/editOneHall",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editOneHall(Integer id, Integer row, Integer col, boolean is_available){
	
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = hallService.editOneHall(id, row, col, is_available);
        	 returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：编辑影厅失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	@RequestMapping(value="/editOneHallStatus",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editOneHallStatus(Integer id, boolean is_available){
	
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = hallService.editOneHallStatus(id, is_available);
        	 returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：编辑影厅失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	@RequestMapping(value="/deleteOneHall",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteOneHall(Integer id){
    
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = hallService.deleteOneHall(id);
        	 returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：删除影厅失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	
	@RequestMapping(value="/getOneHall",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getOneHall(Integer id){
    
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = hallService.getOneHall(id);
        	 returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	@RequestMapping(value="/getHallPageList",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>getHallPageList(int currentPage, int pageSize){ 

        Map<String,Object> returnMap = new HashMap<String,Object>();

        try {
            Page<Hall> result = hallService.getHallPageList(currentPage, pageSize);
            returnMap.put("page", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：操作失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
    }
}
