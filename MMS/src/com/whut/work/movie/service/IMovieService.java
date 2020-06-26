package com.whut.work.movie.service;

import java.util.Date;
import java.util.Map;

import com.whut.work.base.model.Page;
import com.whut.work.movie.model.Movie;

public interface IMovieService {

	//将电影信息加入电影表中
    public Map<String,Object> addToMovie(String name, String director, String actor, Float score, String plot,Date premiere, String url) throws Exception;

    //编辑电影信息
    public Map<String,Object> editOneMovie(int id, String name, String director, String actor, Float score, String plot, Date premiere, String url) throws Exception;

    //删除
    public Map<String,Object> deleteOneMovie(Integer id) throws Exception;
    
    // 根据id获取
    public Map<String,Object> getOneMovie(Integer id) throws Exception;
    
    //getMoviePageList
    public Page<Movie>getMoviePageList(int currentPage, int pageSize,String blurName,String sortRuleArray) throws Exception; 

    
    
}
