package com.dao;
/**
 * <h3>This class represents that pagination for view records in particular number</h3>
 * @author : Hinal Bhavsar
 * @version 1.01 24-07-2024
 */
public class Pagination {

	private int offset;
	private int limit;

	public Pagination(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}