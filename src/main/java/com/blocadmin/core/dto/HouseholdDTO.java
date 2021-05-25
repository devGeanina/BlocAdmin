package com.blocadmin.core.dto;

import java.io.Serializable;
import java.util.List;
import com.blocadmin.core.entity.User;

public class HouseholdDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private int buildingNr;
    private int appartmentNr;
    private String details;
    private int roomsNr;
    private int nrCurrentOccupants;
    private int totalCapacity;
    private String ownerName;
    private Long ownerId;
	private User selectedOwner;
	private double totalDebt;
	private List<Long> expensesIds;
	private List<Double> expensesSum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
	public User getSelectedOwner() {
		return selectedOwner;
	}

	public void setSelectedOwner(User selectedOwner) {
		this.selectedOwner = selectedOwner;
	}

	public double getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}

	public List<Long> getExpensesIds() {
		return expensesIds;
	}

	public void setExpensesIds(List<Long> expensesIds) {
		this.expensesIds = expensesIds;
	}

	public List<Double> getExpensesSum() {
		return expensesSum;
	}

	public void setExpensesSum(List<Double> expensesSum) {
		this.expensesSum = expensesSum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + appartmentNr;
		result = prime * result + buildingNr;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((expensesIds == null) ? 0 : expensesIds.hashCode());
		result = prime * result + ((expensesSum == null) ? 0 : expensesSum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + nrCurrentOccupants;
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		result = prime * result + roomsNr;
		result = prime * result + ((selectedOwner == null) ? 0 : selectedOwner.hashCode());
		result = prime * result + totalCapacity;
		long temp;
		temp = Double.doubleToLongBits(totalDebt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HouseholdDTO other = (HouseholdDTO) obj;
		if (appartmentNr != other.appartmentNr)
			return false;
		if (buildingNr != other.buildingNr)
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (expensesIds == null) {
			if (other.expensesIds != null)
				return false;
		} else if (!expensesIds.equals(other.expensesIds))
			return false;
		if (expensesSum == null) {
			if (other.expensesSum != null)
				return false;
		} else if (!expensesSum.equals(other.expensesSum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nrCurrentOccupants != other.nrCurrentOccupants)
			return false;
		if (ownerId == null) {
			if (other.ownerId != null)
				return false;
		} else if (!ownerId.equals(other.ownerId))
			return false;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		if (roomsNr != other.roomsNr)
			return false;
		if (selectedOwner == null) {
			if (other.selectedOwner != null)
				return false;
		} else if (!selectedOwner.equals(other.selectedOwner))
			return false;
		if (totalCapacity != other.totalCapacity)
			return false;
		if (Double.doubleToLongBits(totalDebt) != Double.doubleToLongBits(other.totalDebt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HouseholdDTO [id=" + id + ", buildingNr=" + buildingNr + ", appartmentNr=" + appartmentNr + ", details="
				+ details + ", roomsNr=" + roomsNr + ", nrCurrentOccupants=" + nrCurrentOccupants + ", totalCapacity="
				+ totalCapacity + ", ownerName=" + ownerName + ", ownerId=" + ownerId + ", selectedOwner="
				+ selectedOwner + ", totalDebt=" + totalDebt + ", expensesIds=" + expensesIds + ", expensesSum="
				+ expensesSum + "]";
	}
}
