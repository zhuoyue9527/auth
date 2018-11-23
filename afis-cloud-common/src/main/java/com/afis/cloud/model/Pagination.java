package com.afis.cloud.model;

/**
 * 分页信息
 * 
 * @className: Pagination
 * @Company: Afis
 * @author zhuzhenghua
 * @date 2018年8月31日 下午4:56:09
 * @version
 */
public class Pagination {

	public Pagination(Integer start, Integer limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	private Integer start;

	private Integer limit;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
