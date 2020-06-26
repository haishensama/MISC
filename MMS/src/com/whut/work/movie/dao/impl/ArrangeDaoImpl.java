package com.whut.work.movie.dao.impl;

import org.springframework.stereotype.Component;

import com.whut.work.base.dao.Impl.BaseDaoImpl;
import com.whut.work.movie.dao.IArrangeDao;
import com.whut.work.movie.model.Arrange;

@Component
public class ArrangeDaoImpl extends BaseDaoImpl<Arrange> implements IArrangeDao{

	
	public ArrangeDaoImpl(){
        super(Arrange.class);
    }
}
