package com.whut.work.movie.service;

import java.util.Date;
import java.util.Map;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Hall;
import com.whut.work.movie.model.Sale;

public interface ISaleService {

	
	//新增销售
	public Map<String,Object> addOneSale(Integer arrange_id, Integer user_id, int seat) throws Exception;
		
	//编辑销售
	public Map<String,Object> editOneSale(Integer id, Integer arrange_id,Integer user_id, int seat) throws Exception;

	//删除
	public Map<String,Object> deleteOneSale(Integer id) throws Exception;

	//删除某用户订单
	public Map<String,Object> deleteSaleByUser(Integer user_id) throws Exception;

	
	//根据id获取
    public Map<String,Object> getOneSale(Integer id) throws Exception;
    
    //根据排片id获取
    public Map<String,Object> getSaleByArrange(Integer arrange_id) throws Exception;
    
    //根据排片id获取总销量
    public int getNumByArrange(Integer arrange_id) throws Exception;
    
    
    //根据用户id获取已订票
    public Page<Sale> getSaleByUser(int currentPage, int pageSize, Integer user_id) throws Exception;
    
    
  //根据用户id获取该场次销售信息
    public Page<Sale> getSalePageByArrange(int currentPage, int pageSize, Integer arrange_id) throws Exception;
    
    //getPage
    public Page<Sale>getSalePageList(int currentPage, int pageSize) throws Exception; 

}
