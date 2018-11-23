package com.afis.cloud.auth.model;

import com.afis.cloud.entities.model.auth.AppUser;

/**
 * appUser的扩展类
 * 
 * @author Chengen
 *
 */
public class AppUserExt extends AppUser {
	private String appCode;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

}
