package com.whut.work.movie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.whut.work.user.model.User;

@Entity
@Table(name = "tb_sale")
public class Sale {

	
	@Id
	@GeneratedValue
	private Integer id; // 编号ID
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "arrange_id")
	private Arrange arrange;//场次
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private com.whut.work.user.model.User user;//用户
	@Column(name="seat")
	public Integer seat;//座位号
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Arrange getArrange() {
		return arrange;
	}
	public void setArrange(Arrange arrange) {
		this.arrange = arrange;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getSeat() {
		return seat;
	}
	public void setSeat(Integer seat) {
		this.seat = seat;
	}
	
	
}
