package com.zak.domain.model.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class PageGenerics <T>implements Serializable{
	
	private Set<T> data ;
	private Criteria pageable;
	private int actualPage;
	private int size;
	private int totalData;
	private int totalPages;
	private boolean first;
	private boolean last;
	private String sortBy;

	public Set<T> getData() {
		return data;
	}

	public void setData(Set<T> data) {
		this.data = data;
	}

	public int getActualPage() {
		return actualPage;
	}

	public void setActualPage(int actualPage) {
		this.actualPage = actualPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Criteria getPageable() {
		return pageable;
	}

	public void setPageable(Criteria pageable) {
		this.pageable = pageable;
	}

	public int getTotalData() {
		return totalData;
	}
	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}
