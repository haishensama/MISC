package com.whut.work.movie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_hall")
public class Hall {

	
	@Id
    @GeneratedValue
    private Integer id;// 编号ID		
    @Column(name="row")
    public Integer row;//行
    @Column(name="col")
    public Integer col;//列
    @Column(name="num")
    public Integer num;//总人数
    @Column(name="is_available")
    public boolean is_available;//是否可用
	
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public boolean isIs_available() {
		return is_available;
	}
	public void setIs_available(boolean is_available) {
		this.is_available = is_available;
	}
        
}
