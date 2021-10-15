package pojo;

import java.util.ArrayList;

public class Taxi {
    private int freeTime;
    private String currentPoint;
    private int totalEarnings;
    private int id;
    private long mobileNumber;
//    private ArrayList<String> trips = new ArrayList<>();

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(int freeTime) {
        this.freeTime = freeTime;
    }

    public String getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(String currentPoint) {
        this.currentPoint = currentPoint;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(int totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

	@Override
	public String toString() {
		return "Taxi [freeTime=" + freeTime + ", currentPoint=" + currentPoint + ", totalEarnings=" + totalEarnings
				+ ", id=" + id + ", mobileNumber=" + mobileNumber + "]";
	}
    
}