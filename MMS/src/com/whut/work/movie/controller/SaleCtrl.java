package com.whut.work.movie.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Sale;
import com.whut.work.movie.service.ISaleService;

@Controller
@RequestMapping("/sale")
public class SaleCtrl {

	@Autowired
	private ISaleService saleService;
	
	@RequestMapping(value="/addOneSale",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addOneSale(Integer arrange_id, Integer user_id, int seat){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = saleService.addOneSale(arrange_id, user_id, seat);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：新增销售失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
		
	}	
	
	@RequestMapping(value="/editOneSale",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editOneSale(Integer id, Integer arrange_id,Integer user_id, int seat){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = saleService.editOneSale(id, arrange_id, user_id, seat);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：修改销售失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}	
	
	@RequestMapping(value="/deleteOneSale",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteOneSale(Integer id){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = saleService.deleteOneSale(id);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：删除失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	
	@RequestMapping(value="/deleteSaleByUser",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteSaleByUser(Integer user_id){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = saleService.deleteSaleByUser(user_id);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：删除失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	
	@RequestMapping(value="/getOneSale",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getOneSale(Integer id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = saleService.getOneSale(id);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	//根据排片id 获取
	@RequestMapping(value="/getSaleByArrange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getSaleByArrange(Integer arrange_id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	Map<String,Object> result = saleService.getSaleByArrange(arrange_id);
        	returnMap.put("value", result);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	
	//获取某场次的总销量
	@RequestMapping(value="/getNumByArrange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getNumByArrange(Integer arrange_id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
        	int num = saleService.getNumByArrange(arrange_id);
        	returnMap.put("value", num);
            returnMap.put("success", true);
        } catch (Exception e) {
            returnMap.put("message", "异常：获取失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
	}
	
	//获取某用户的订单
	@RequestMapping(value="/getSaleByUser",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSaleByUser(int currentPage, int pageSize, Integer user_id) {
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
	    	Page<Sale> result = saleService.getSaleByUser(currentPage, pageSize, user_id);
	    	returnMap.put("page", result);
	        returnMap.put("success", true);
	    } catch (Exception e) {
	        returnMap.put("message", "异常：获取失败!");
	        returnMap.put("success", false);
	        e.printStackTrace();
	    }
	    return returnMap;
	}

	//获取某场次销售情况
	@RequestMapping(value="/getSalePageByArrange",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSalePageByArrange(int currentPage, int pageSize, Integer arrange_id){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
	    	Page<Sale> result = saleService.getSalePageByArrange(currentPage, pageSize, arrange_id);
	    	returnMap.put("page", result);
	        returnMap.put("success", true);
	    } catch (Exception e) {
	        returnMap.put("message", "异常：获取失败!");
	        returnMap.put("success", false);
	        e.printStackTrace();
	    }
	    return returnMap;
	}

	
	@RequestMapping(value="/getSalePageList",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSalePageList(int currentPage, int pageSize){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
	    	Page<Sale> result = saleService.getSalePageList(currentPage, pageSize);
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