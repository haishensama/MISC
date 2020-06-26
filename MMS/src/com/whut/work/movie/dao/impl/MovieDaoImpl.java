package com.whut.work.movie.dao.impl;

import org.springframework.stereotype.Component;

import com.whut.work.base.dao.Impl.BaseDaoImpl;
import com.whut.work.movie.dao.IMovieDao;
import com.whut.work.movie.model.Movie;

@Component
public class MovieDaoImpl extends BaseDaoImpl<Movie> implements IMovieDao{
	public MovieDaoImpl(){
		super(Movie.class);
		
	}
}
