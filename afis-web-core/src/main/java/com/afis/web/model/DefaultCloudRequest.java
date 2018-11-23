package com.afis.web.model;

import com.afis.web.modal.UserDetails;

public class DefaultCloudRequest {

	private UserDetails userDetails;

	public DefaultCloudRequest() {
		super();
	}

	public DefaultCloudRequest(UserDetails userDetails) {
		super();
		this.userDetails = userDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}
