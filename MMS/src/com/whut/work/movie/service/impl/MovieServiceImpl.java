package com.whut.work.movie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whut.work.base.model.Page;
import com.whut.work.base.util.JavaStringUtil;
import com.whut.work.movie.dao.impl.ArrangeDaoImpl;
import com.whut.work.movie.dao.impl.MovieDaoImpl;
import com.whut.work.movie.dao.impl.SaleDaoImpl;
import com.whut.work.movie.model.Movie;
import com.whut.work.movie.service.IMovieService;

@Component
public class MovieServiceImpl implements IMovieService{

	@Autowired
	private MovieDaoImpl movieDaoImpl;
	
	@Autowired
	private ArrangeDaoImpl arrangeDao;
	
	@Autowired
	private SaleDaoImpl saleDao;
	
	@Override
	public Map<String, Object> addToMovie(String name, String director, String actor, Float score, String plot,
			Date premiere,String url) throws Exception {
		 Map<String,Object> returnMap = new HashMap<String,Object>();
	        try {
	        	Movie movie = new Movie();
	            
	        	movie.setName(name);
	        	movie.setDirector(director);
	        	movie.setActor(actor);
	        	movie.setScore(score);
	        	movie.setPlot(plot);
	        	movie.setPremiere(premiere);
	        	movie.setUrl(url);
	        	
	            movieDaoImpl.save(movie);
	            
	            returnMap.put("value", movie);
	            returnMap.put("message", "将电影加入电影表成功");
	            returnMap.put("success", true);
			} catch (Exception e) {
				// TODO: handle exception
			}
	        return returnMap;
	}

	@Override
	public Map<String, Object> editOneMovie(int id, String name, String director, String actor, Float score,
			String plot, Date premiere, String url) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String hql = " from Movie where id='" + id + "' ";
		Movie movie = movieDaoImpl.findOne(hql);
		if (movie != null) {
			movie.setName(name);
        	movie.setDirector(director);
        	movie.setActor(actor);
        	movie.setScore(score);
        	movie.setPlot(plot);
        	movie.setPremiere(premiere);
        	movie.setUrl(url);
        	
        	movieDaoImpl.update(movie);
        	
        	returnMap.put("value", movie);
			returnMap.put("message", "编辑成功");
			returnMap.put("success", true);
		}else {
			returnMap.put("message", "编辑失败");
			returnMap.put("success", false);
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> deleteOneMovie(Integer id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		 
        String hql = " from Movie where id='" + id + "' ";
        String delhql = " delete Movie where id='" + id + "' ";
        
        String hql_arrange = " from Arrange a where a.movie.id='" + id + "' ";
        String hql_sale = " from Sale s where s.arrange.movie.id='" + id + "' ";
        
       if (saleDao.findList(hql_sale) != null) {
        	saleDao.deleteAll(saleDao.findList(hql_sale));
		}
        if (arrangeDao.findList(hql_arrange) != null) {
        	arrangeDao.deleteAll(arrangeDao.findList(hql_arrange));
		}
        
        Movie movie = movieDaoImpl.findOne(hql);
        if (movie != null) {
			
        	movieDaoImpl.deleteWithHql(delhql);
		}
        
        JavaStringUtil.setListInt(new ArrayList<Integer>());
        returnMap.put("message", "已从电影中删除指定电影信息");
        returnMap.put("success", true);
        return returnMap;
	}

	@Override
	public Map<String, Object> getOneMovie(Integer id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
        Movie movie = movieDaoImpl.findOne(" from Movie where id = '" + id + "' ");
        if(movie != null){
            returnMap.put("value", movie);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
        }else {
            returnMap.put("message", "获取失败");
            returnMap.put("success", false);
        }

        return returnMap;
	}

	@Override
	public Page<Movie> getMoviePageList(int currentPage, int pageSize,String blurName, String sortRuleArray) throws Exception {
		String conditions = "";
		String conditionsforcount = "";
        System.out.println(sortRuleArray);
        if(sortRuleArray ==null){
        	sortRuleArray = "000000";
        }
        if(!blurName.equals("") && blurName!=null){
            conditions += " where m.name like '%"+blurName+"%' ";
            conditionsforcount = conditions;
        }
		if(sortRuleArray.equals("000000")){
            conditions += "";
        }else if(sortRuleArray.equals("00tcr0")){
            conditions += " order by m.premiere desc ";
        }else if(sortRuleArray.equals("00trc0")){
            conditions += " order by m.premiere asc ";
        }else if(sortRuleArray.equals("000sms")){
            conditions += " order by m.score desc ";
        }else if(sortRuleArray.equals("000ssm")){
            conditions += " order by m.score asc ";
        }else {
            conditions += " ";
        }
        String queryHql = " from Movie m "+conditions+" ";
        //System.out.println(queryHql);
        String countHql = " select count(*) from Movie m "+conditionsforcount+" " ;
        
		Page<Movie> returnPage = movieDaoImpl.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

}
