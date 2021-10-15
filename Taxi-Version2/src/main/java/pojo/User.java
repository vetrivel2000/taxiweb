package pojo;

public class User {
    private String userName;
    private int wallet;
    private int userId;
    private long mobileNumber;

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Override
	public String toString() {
		return "User [userName=" + userName + ", wallet=" + wallet + ", userId=" + userId + ", mobileNumber="
				+ mobileNumber + "]";
	}
}