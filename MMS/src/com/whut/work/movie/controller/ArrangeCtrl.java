package com.whut.work.movie.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Arrange;
import com.whut.work.movie.model.Hall;
import com.whut.work.movie.service.IArrangeService;
import com.whut.work.movie.service.ISaleService;

@Controller
@RequestMapping("/arrange")
public class ArrangeCtrl {

	@Autowired
	private IArrangeService arrangeService;
	
	@Autowired
	private ISaleService saleService;
	
	@RequestMapping(value="/addOneArrange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addOneArrange(String movieName,Integer hall_id, Float price,  Date startTime, Date endTime){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = arrangeService.addOneArrange(movieName, hall_id, price, startTime, endTime);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：新增排片失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
		
	}	
	
	@RequestMapping(value="/editOneArrange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editOneArrange(Integer id, Integer movie_id,Integer hall_id, Float price,  Date startTime, Date endTime){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = arrangeService.editOneArrange(id, movie_id, hall_id,  price, startTime, endTime);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：修改排片失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}	
	
	@RequestMapping(value="/deleteOneArrange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteOneArrange(Integer id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = arrangeService.deleteOneArrange(id);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：删除失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	@RequestMapping(value="/getOneArrange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getOneArrange(Integer id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = arrangeService.getOneArrange(id);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	
	@RequestMapping(value="/getArrangePageList",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getArrangePageList(int currentPage, int pageSize){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	int num[] = new int[100];
    	try {
        	Page<Arrange> result = arrangeService.getArrangePageList(currentPage, pageSize);
        	List<Arrange> list = arrangeService.getArrangeList();
    		
        	for (Arrange arrange:list) {
				num[arrange.getId()] = saleService.getNumByArrange(arrange.getId());
			}
			returnMap.put("num", num);
        	returnMap.put("page", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	@RequestMapping(value="/getArrangePageListByMovie",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getArrangePageListByMovie(int currentPage, int pageSize, int movie_id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	int num[] = new int[100];
    	try {
			Page<Arrange> result = arrangeService.getArrangePageListByMovie(currentPage,pageSize,movie_id);
        	List<Arrange> list = arrangeService.getArrangeListByMovie(movie_id);
			for (Arrange arrange:list) {
				num[arrange.getId()] = saleService.getNumByArrange(arrange.getId());
			}
			returnMap.put("num", num);
        	returnMap.put("page", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
}
