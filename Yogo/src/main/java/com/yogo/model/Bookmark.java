package com.yogo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bookmark {
	private Integer id_bookmark;
	private String name;
	private Integer id_user;
	private String default_location;
	
	public Bookmark() {
		super();
	}
	
	public Bookmark(Integer id_bookmark, String name, Integer id_user, String default_location) {
		super();
		this.id_bookmark = id_bookmark;
		this.name = name;
		this.id_user = id_user;
		this.default_location = default_location;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_bookmark() {
		return id_bookmark;
	}
	public void setId_bookmark(Integer id_bookmark) {
		this.id_bookmark = id_bookmark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId_user() {
		return id_user;
	}
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}
	public String getDefault_location() {
		return default_location;
	}
	public void setDefault_location(String default_location) {
		this.default_location = default_location;
	}
	
	
}
