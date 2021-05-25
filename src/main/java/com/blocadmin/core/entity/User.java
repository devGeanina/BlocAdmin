package com.blocadmin.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends CommonEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "username", nullable = false)
    private String username;
	
	@Column(name = "password", nullable = true)
    private String password;

	@Column(name = "first_name", nullable = false)
    private String firstName;
	
	@Column(name = "last_name", nullable = false)
    private String lastName;
	
	@Column(name = "user_type", nullable = false)
    private short userType;
	
	@Column(name = "building_nr", nullable = false)
    private int buildingNr;
	
	@Column(name = "details", nullable = true)
    private String details;
	
	@Column(name = "appartment_nr", nullable = false)
    private int appartmentNr;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Household> households;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Request> requests;
	
	public User() {}

	public User(String username, String password, String firstName, String lastName, short userType, int buildingNr,
			String details, int appartmentNr, List<Household> households, List<Request> requests) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.buildingNr = buildingNr;
		this.details = details;
		this.appartmentNr = appartmentNr;
		this.households = households;
		this.requests = requests;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getAppartmentNr() {
		return appartmentNr;
	}

	public void setAppartmentNr(int appartmentNr) {
		this.appartmentNr = appartmentNr;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", userType=" + userType + ", buildingNr=" + buildingNr + ", details=" + details
				+ ", appartmentNr=" + appartmentNr + "]";
	}
}
