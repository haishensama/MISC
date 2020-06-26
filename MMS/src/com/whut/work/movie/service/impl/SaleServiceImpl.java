package com.whut.work.movie.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whut.work.base.model.Page;
import com.whut.work.base.util.JavaStringUtil;
import com.whut.work.movie.dao.impl.ArrangeDaoImpl;
import com.whut.work.movie.dao.impl.SaleDaoImpl;
import com.whut.work.movie.model.Sale;
import com.whut.work.movie.service.ISaleService;
import com.whut.work.user.dao.impl.UserDaoImpl;

@Component
public class SaleServiceImpl implements ISaleService{

	@Autowired
	private SaleDaoImpl saleDao;
	
	@Autowired
	private ArrangeDaoImpl arrangeDao;
	
	@Autowired 
	private UserDaoImpl userDao;
	
	@Override
	public Map<String, Object> addOneSale(Integer arrange_id, Integer user_id, int seat) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			Sale sale = new Sale();
			sale.setArrange(arrangeDao.findOne("from Arrange where id='" + arrange_id + "'"));
			sale.setUser(userDao.findOne("from User where id='" + user_id + "'"));
			sale.setSeat(seat);
			
			saleDao.save(sale);
			
			returnMap.put("value", sale);
            returnMap.put("message", "将销售信息加入销售信息表成功");
            returnMap.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> editOneSale(Integer id, Integer arrange_id, Integer user_id, int seat) throws Exception {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String hql = " from Sale where id='" + id + "' ";
		Sale sale = saleDao.findOne(hql);
		if (sale != null) {
			sale.setArrange(arrangeDao.findOne("from Arrange where id='" + arrange_id + "'"));
			sale.setUser(userDao.findOne("from User where id='" + user_id + "'"));
			sale.setSeat(seat);
			
			saleDao.update(sale);
			returnMap.put("value", sale);
			returnMap.put("message", "编辑成功");
			returnMap.put("success", true);
		}else {
			returnMap.put("message", "编辑失败");
			returnMap.put("success", false);
		}

		return returnMap;
	}

	@Override
	public Map<String, Object> deleteOneSale(Integer id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String hql = " from Sale where id='" + id + "' ";
        String delhql = " delete Sale where id='" + id + "' ";
       
        Sale sale = saleDao.findOne(hql);
        
        if (sale != null) {
			
        	saleDao.deleteWithHql(delhql);
		}
        
        
        JavaStringUtil.setListInt(new ArrayList<Integer>());
        returnMap.put("message", "已从销售信息中删除指定销售信息");
        returnMap.put("success", true);
        return returnMap;
	}
	
	@Override
	public Map<String, Object> deleteSaleByUser(Integer user_id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		String hql = " from Sale s where s.user.id='" + user_id + "' ";
        //String delhql = " delete Sale where id='" + user_id + "' ";
       
        List<Sale> list= saleDao.findList(hql);
        
        if (!list.isEmpty()) {
			
        	saleDao.deleteAll(list);
		}
        
        
        JavaStringUtil.setListInt(new ArrayList<Integer>());
        returnMap.put("message", "已从销售信息中删除指定销售信息");
        returnMap.put("success", true);
        return returnMap;
	}
	

	@Override
	public Map<String, Object> getOneSale(Integer id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
        Sale sale = saleDao.findOne(" from Sale where id = '" + id + "' ");
        if(sale != null){
            returnMap.put("value", sale);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
        }else {
            returnMap.put("message", "获取失败");
            returnMap.put("success", false);
        }

        return returnMap;
	}

	@Override
	public Map<String, Object> getSaleByArrange(Integer arrange_id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
        String hql = "from Sale m where m.arrange.id='" + arrange_id + "'";
		
        List<Sale> list = saleDao.findList(hql);
        if (!list.isEmpty()) {
        	
        	returnMap.put("value", list);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
        	
		}
		
		
		return returnMap;
	}

	@Override
	public Page<Sale> getSaleByUser(int currentPage, int pageSize, Integer user_id) throws Exception {
		String queryHql = "from Sale m where m.user.id='" + user_id + "'";
		String countHql = "select count(*) from Sale m where m.user.id='" + user_id + "'";
		
		Page<Sale> returnPage = saleDao.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

	@Override
	public Page<Sale> getSalePageList(int currentPage, int pageSize) throws Exception {
		String queryHql = "from Sale m";
		String countHql = "select count(*) from Sale m ";
		
		Page<Sale> returnPage = saleDao.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

	@Override
	public Page<Sale> getSalePageByArrange(int currentPage, int pageSize, Integer arrange_id) throws Exception {
		
        
        String queryHql = "from Sale s where s.arrange.id='" + arrange_id + "'";
		String countHql = "select count(*) from Sale s where s.arrange.id='" + arrange_id + "'";
	
		Page<Sale> returnPage = saleDao.findPage(currentPage, pageSize, queryHql, countHql);
		return returnPage;
	}

	//获取实际售出总人数
	@Override
	public int getNumByArrange(Integer arrange_id) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
	       
		String queryHql = "from Sale s where s.arrange.id='" + arrange_id + "'";
		List<Sale> list = saleDao.findList(queryHql);
		if (list != null) {
			return list.size();
		}else {
			return 0;
		}
		
	}

	

}
