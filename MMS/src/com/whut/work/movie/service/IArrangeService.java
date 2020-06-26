package com.whut.work.movie.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Arrange;

public interface IArrangeService {

	//新增排片
	public Map<String,Object> addOneArrange(String movieName,Integer hall_id,  Float price,  Date startTime, Date endTime) throws Exception;
	
	//修改排片
	public Map<String,Object> editOneArrange(Integer id, Integer movie_id,Integer hall_id, Float price,  Date startTime, Date endTime) throws Exception;

	//删除
	public Map<String,Object> deleteOneArrange(Integer id) throws Exception;
    
	//根据id获取
    public Map<String,Object> getOneArrange(Integer id) throws Exception;
    
    //根据id获取
    public List<Arrange> getArrangeListByMovie(Integer movie_id) throws Exception;
    public List<Arrange>getArrangeList() throws Exception;
    
    //getPage
    public Page<Arrange>getArrangePageList(int currentPage, int pageSize) throws Exception; 

    
    public Page<Arrange>getArrangePageListByMovie(int currentPage, int pageSize, int movie_id)throws Exception;
}
