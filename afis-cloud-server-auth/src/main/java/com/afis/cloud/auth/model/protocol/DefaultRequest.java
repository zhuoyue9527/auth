package com.afis.cloud.auth.model.protocol;
/**
 * 管理端登出请求
 * @author Chengen
 *
 */

import com.afis.web.modal.UserDetails;

public class DefaultRequest {
	
	private UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
