package com.yogo.model;

public class Message {
	private String username;
	public String content;
	
	
	public Message() {
		super();
	}

	public Message(String username, String content) {
		super();
		this.username = username;
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
