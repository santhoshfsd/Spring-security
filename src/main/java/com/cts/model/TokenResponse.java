package com.cts.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TokenResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private  String token;

	private Map<String,String> userDetails = new HashMap<>();

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<String, String> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Map<String, String> userDetails) {
		this.userDetails = userDetails;
	}
}