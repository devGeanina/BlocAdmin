package com.blocadmin.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "households")
public class Household extends CommonEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "building_nr", nullable = false)
    private int buildingNr;
	
	@Column(name = "appartment_nr", nullable = false)
    private int appartmentNr;
	
	@Column(name = "details", nullable = true)
    private String details;
	
	@Column(name = "rooms_nr", nullable = false)
    private int roomsNr;
	
	@Column(name = "nr_curr_occupants", nullable = false)
    private int nrCurrentOccupants;
	
	@Column(name = "total_capacity", nullable = false)
    private int totalCapacity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
    private User owner;
	
    @ManyToMany(mappedBy = "householdExpenses", fetch=FetchType.LAZY)
    private List<Expense> expenses;
	
	public Household() {}

	public Household(int buildingNr, int appartmentNr, String details, int roomsNr, int nrCurrentOccupants,
			int totalCapacity, User owner) {
		super();
		this.buildingNr = buildingNr;
		this.appartmentNr = appartmentNr;
		this.details = details;
		this.roomsNr = roomsNr;
		this.nrCurrentOccupants = nrCurrentOccupants;
		this.totalCapacity = totalCapacity;
		this.owner = owner;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public int getAppartmentNr() {
		return appartmentNr;
	}

	public void setAppartmentNr(int appartmentNr) {
		this.appartmentNr = appartmentNr;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getRoomsNr() {
		return roomsNr;
	}

	public void setRoomsNr(int roomsNr) {
		this.roomsNr = roomsNr;
	}

	public int getNrCurrentOccupants() {
		return nrCurrentOccupants;
	}

	public void setNrCurrentOccupants(int nrCurrentOccupants) {
		this.nrCurrentOccupants = nrCurrentOccupants;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	@Override
	public String toString() {
		return "Household [buildingNr=" + buildingNr + ", appartmentNr=" + appartmentNr + ", details=" + details
				+ ", roomsNr=" + roomsNr + ", nrCurrentOccupants=" + nrCurrentOccupants + ", totalCapacity="
				+ totalCapacity + ", owner=" + owner + "]";
	}
}
