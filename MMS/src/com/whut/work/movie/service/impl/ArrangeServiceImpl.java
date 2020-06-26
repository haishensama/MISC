package com.whut.work.movie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whut.work.base.model.Page;
import com.whut.work.base.util.JavaStringUtil;
import com.whut.work.movie.dao.impl.ArrangeDaoImpl;
import com.whut.work.movie.dao.impl.HallDaoImpl;
import com.whut.work.movie.dao.impl.MovieDaoImpl;
import com.whut.work.movie.model.Arrange;
import com.whut.work.movie.model.Hall;
import com.whut.work.movie.service.IArrangeService;

@Component
public class ArrangeServiceImpl implements IArrangeService{

	@Autowired
	private ArrangeDaoImpl arrangeDao;
	
	@Autowired
	private MovieDaoImpl movieDao;
	
	@Autowired
	private HallDaoImpl hallDao;
	
	
	@Override
	public Map<String, Object> addOneArrange(String movieName, Integer hall_id, Float price, Date startTime,
			Date endTime) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
	    try {
	    	Arrange arrange = new Arrange();
	    	arrange.setNum(hallDao.findOne("from Hall h where h.id='"+hall_id+"'").num);
	    	arrange.setPrice(price);
	    	arrange.setStartTime(startTime);
	    	arrange.setEndTime(endTime);
	    	
	    	arrange.setMovie(movieDao.findOne(" from Movie m where m.name='"+movieName+"'"));
	    	arrange.setHall(hallDao.findOne(" from Hall h where h.id='"+hall_id+"'"));
	    	
	    	arrangeDao.save(arrange);
	    	
	    	returnMap.put("value", arrange);
            returnMap.put("message", "将排片信息加入排片表成功");
            returnMap.put("success", true);
	    	
	    }catch (Exception e) {
			// TODO: handle exception
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> editOneArrange(Integer id, Integer movie_id, Integer hall_id, Float price,
			Date startTime, Date endTime) throws Exception {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String hql = " from Arrange where id='" + id + "' ";
		Arrange arrange = arrangeDao.findOne(hql);
		
		if (arrange != null) {
			arrange.setNum(hallDao.findOne("from Hall h where h.id='"+hall_id+"'").num);
	    	arrange.setPrice(price);
	    	arrange.setStartTime(startTime);
	    	arrange.setEndTime(endTime);
	    	
	    	arrange.setMovie(movieDao.findOne(" from Movie m where m.id='"+movie_id+"'"));
	    	arrange.setHall(hallDao.findOne(" from Hall h where h.id='"+hall_id+"'"));
	    	
			arrangeDao.update(arrange);
			returnMap.put("value", arrange);
			returnMap.put("message", "编辑成功");
			returnMap.put("success", true);
		}else {
			returnMap.put("message", "编辑失败");
			returnMap.put("success", false);
		}

		return returnMap;
	}

	@Override
	public Map<String, Object> deleteOneArrange(Integer id) throws Exception {
 Map<String,Object> returnMap = new HashMap<String,Object>();
		 
         String hql = " from Arrange where id='" + id + "' ";
         String delhql = " delete Arrange where id='" + id + "' ";
        
         Arrange arrange = arrangeDao.findOne(hql);
         
         if (arrange != null) {
			
        	 arrangeDao.deleteWithHql(delhql);
		}
         
         
         JavaStringUtil.setListInt(new ArrayList<Integer>());
         returnMap.put("message", "已从排片表中删除指定排片信息");
         returnMap.put("success", true);
         return returnMap;
	}

	@Override
	public Map<String, Object> getOneArrange(Integer id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
        Arrange arrange = arrangeDao.findOne(" from Arrange where id = '" + id + "' ");
        if(arrange != null){
            returnMap.put("value", arrange);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
        }else {
            returnMap.put("message", "获取失败");
            returnMap.put("success", false);
        }

        return returnMap;
	}

	@Override
	public Page<Arrange> getArrangePageList(int currentPage, int pageSize) throws Exception {
		String queryHql = "from Arrange m";
		String countHql = "select count(*) from Arrange m ";
		
		Page<Arrange> returnPage = arrangeDao.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

	

	@Override
	public Page<Arrange> getArrangePageListByMovie(int currentPage, int pageSize, int movie_id)
			throws Exception {
		String queryHql = "from Arrange a where a.movie.id='" + movie_id + "'";
		String countHql = "select count(*) from Arrange a where a.movie.id='" + movie_id + "'";
		
		Page<Arrange> returnPage = arrangeDao.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

	@Override
	public List<Arrange>getArrangeListByMovie(Integer movie_id) throws Exception {
		String queryHql = "from Arrange a where a.movie.id='" + movie_id + "'";
		//String countHql = "select count(*) from Arrange a where a.movie.id='" + movie_id + "'";
		//Map<String, Object> returnMap = new HashMap<String,Object>();
		
		List<Arrange> list = arrangeDao.findList(queryHql);
		if (!list.isEmpty()) {
			return list;
        }else {
            return null;
        }
		
	}
	
	@Override
	public List<Arrange>getArrangeList(){
		String queryHql = "from Arrange a";
		//String countHql = "select count(*) from Arrange a where a.movie.id='" + movie_id + "'";
		//Map<String, Object> returnMap = new HashMap<String,Object>();
		try {
			List<Arrange> list  = arrangeDao.findList(queryHql);
			if (!list.isEmpty()) {
				return list;
			}else {
				return null;
			}
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
