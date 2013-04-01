package com.example.demo_im.bean;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	List<User> users;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Group(String name, List<User> users) {
		super();
		this.name = name;
		this.users = users;
	}
	@Override
	public String toString() {
		return "Group [name=" + name + ", users=" + users + "]";
	}

	
}
