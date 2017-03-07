package com.xifar.console.session;

import java.io.Serializable;

public class User implements Serializable {  
	  
    private static final long serialVersionUID = -1267719235225203410L;  
  
    private String uid;  
  
    private String address;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}  
}  
