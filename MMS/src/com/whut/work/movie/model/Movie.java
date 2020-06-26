package com.whut.work.movie.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_movie")
public class Movie {

	@Id
    @GeneratedValue
    private Integer id;// 编号ID		
    @Column(name="name")
    private String name;// 名称
    @Column(name="director")
    private String director;//导演			
    @Column(name="actor")
    private String actor;//主演
    @Column(name="score")
    public Float score;//评分
    @Column(name="plot")
    private String plot;//剧情
    @Column(name = "premiere")
	private Date premiere;//上映时间
	@Column(name = "url")
	private String url;
    
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public Date getPremiere() {
		return premiere;
	}
	public void setPremiere(Date premiere) {
		this.premiere = premiere;
	}
    
    
    
	
}
