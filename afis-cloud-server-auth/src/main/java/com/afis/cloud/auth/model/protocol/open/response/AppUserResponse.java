package com.afis.cloud.auth.model.protocol.open.response;

public class AppUserResponse {
	private Long userId;
	private String userAccount;
	private String warrantPermit;
	private String warrant;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getWarrantPermit() {
		return warrantPermit;
	}

	public void setWarrantPermit(String warrantPermit) {
		this.warrantPermit = warrantPermit;
	}

	public String getWarrant() {
		return warrant;
	}

	public void setWarrant(String warrant) {
		this.warrant = warrant;
	}

}
