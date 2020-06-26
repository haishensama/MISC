package com.whut.work.movie.dao.impl;

import org.springframework.stereotype.Component;

import com.whut.work.base.dao.Impl.BaseDaoImpl;
import com.whut.work.movie.dao.IHallDao;
import com.whut.work.movie.model.Hall;

@Component
public class HallDaoImpl extends BaseDaoImpl<Hall> implements IHallDao{

	public HallDaoImpl() {
		super(Hall.class);// TODO Auto-generated constructor stub
	}
	
}
