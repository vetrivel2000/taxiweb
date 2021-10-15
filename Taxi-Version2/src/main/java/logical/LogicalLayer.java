package logical;

import databasemanagement.DataBase;
import pojo.Taxi;
import pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class LogicalLayer {
    DataBase dataBase= null;
    public LogicalLayer(){
    	dataBase= new DataBase();
    }
    public User getUserObject(String name,long mobileNumber,int wallet)
    {
    	User user = new User();
    	user.setUserName(name);
    	user.setMobileNumber(mobileNumber);
    	user.setWallet(wallet);
    	return user;
    }
    public Taxi getTaxiObject(int freeTime,String currentPoint,int earnings,long mobileNumber)
    {
    	Taxi taxi=new Taxi();
    	taxi.setCurrentPoint(currentPoint);
    	taxi.setFreeTime(freeTime);
    	taxi.setTotalEarnings(earnings);
    	taxi.setMobileNumber(mobileNumber);
    	return taxi;
    }
    public  ArrayList<Taxi> getFreeTaxis(ArrayList<Taxi> taxis, char pickupPoint, int time)
    {
        ArrayList<Taxi> freeTaxi=new ArrayList<>();
        for (Taxi taxi:taxis) {
        	int distanceBetweenUserAndTaxi=(int)taxi.getCurrentPoint().charAt(0)-(int)pickupPoint;
            if(distanceBetweenUserAndTaxi<0)
            {
            	distanceBetweenUserAndTaxi=-1*distanceBetweenUserAndTaxi;
            }
            if(taxi.getFreeTime()<=time && (distanceBetweenUserAndTaxi<=time-taxi.getFreeTime()))
            {
                freeTaxi.add(taxi);
            }
        }
        return freeTaxi;
    }
    public int BookTaxi(int userId,char pickupPoint,char dropPoint,int pickupTime,ArrayList<Taxi> freeTaxis)
    {
        int min=999;
        int earnings=0;
        int nextFreeTime=0;
        String nextPoint = "";
        Taxi bookedTaxi = null;
        for (Taxi taxi:freeTaxis)
        {
            int distanceBetweenUserAndTaxi=(int)taxi.getCurrentPoint().charAt(0)-(int)pickupPoint;
            if(distanceBetweenUserAndTaxi<0)
            {
            	distanceBetweenUserAndTaxi=-1*distanceBetweenUserAndTaxi;
            }
            if(distanceBetweenUserAndTaxi<min)
            {
                bookedTaxi=taxi;
                int distance=Math.abs(((int)dropPoint-(int)pickupPoint));
                earnings=(distance)*10;
                int dropTime=pickupTime+distance;
                nextFreeTime=dropTime;
                nextPoint= String.valueOf(dropPoint);
                min=distanceBetweenUserAndTaxi;
            }
        }
        bookedTaxi.setTotalEarnings(bookedTaxi.getTotalEarnings()+earnings);
        bookedTaxi.setFreeTime(nextFreeTime);
        bookedTaxi.setCurrentPoint(nextPoint);
        int userWallet=dataBase.getWallet(userId);
//        System.out.println(userWallet);
        int newWallet=userWallet-earnings;
        System.out.println(newWallet);
        dataBase.updateUser(newWallet, userId);
        dataBase.updateTaxi(bookedTaxi.getTotalEarnings(),bookedTaxi.getCurrentPoint(), bookedTaxi.getFreeTime(), bookedTaxi.getId());
        return bookedTaxi.getId();
    }
    public User setUser(User user) throws SQLException
    {
        int userId=dataBase.createUser(user);
        user.setUserId(userId);
        return user;
    }
    public Taxi setTaxi(Taxi taxi) throws SQLException
    {
        int taxiId=dataBase.createTaxi(taxi);
        taxi.setId(taxiId);
        return taxi;
    }
    public ArrayList<User> getUser()
    {
    	return dataBase.getUsers();
    }
    public ArrayList<Taxi> getTaxi()
    {
    	return dataBase.getTaxis();
    }
}
