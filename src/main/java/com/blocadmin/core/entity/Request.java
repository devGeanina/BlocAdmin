package com.blocadmin.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "requests")
public class Request extends CommonEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "request_type", nullable = false)
    private short requestType;
	
	@Column(name = "name", nullable = false)
    private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
    private User owner;
	
	@Column(name = "details", nullable = true)
    private String details;
	
	@Column(name = "is_resolved", nullable = false)
    private boolean isResolved;
	
	@Column(name = "due_date", nullable = false)
    private Date dueDate;

	public Request() {}

	public Request(short requestType, String name, User owner, String details, boolean isResolved, Date dueDate) {
		super();
		this.requestType = requestType;
		this.name = name;
		this.owner = owner;
		this.details = details;
		this.isResolved = isResolved;
		this.dueDate = dueDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public short getRequestType() {
		return requestType;
	}

	public void setRequestType(short requestType) {
		this.requestType = requestType;
	}

	public boolean isResolved() {
		return isResolved;
	}

	public void setResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "Request [requestType=" + requestType + ", name=" + name + ", owner=" + owner + ", details=" + details
				+ ", isResolved=" + isResolved + ", dueDate=" + dueDate + "]";
	}
}
