package com.whut.work.movie.dao.impl;

import org.springframework.stereotype.Component;

import com.whut.work.base.dao.Impl.BaseDaoImpl;
import com.whut.work.movie.dao.ISaleDao;
import com.whut.work.movie.model.Sale;

@Component
public class SaleDaoImpl extends BaseDaoImpl<Sale> implements ISaleDao{

	public SaleDaoImpl(){
		
		super(Sale.class);
	}
}
