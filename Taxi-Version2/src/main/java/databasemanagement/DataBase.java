package databasemanagement;

import pojo.Taxi;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static Connection connection = null;
    public DataBase() {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db", "root", "Sara@2303");
            System.out.println("DataBase Connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public int createUser(User user) throws SQLException
    {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        int userId;
        try{
            statement = connection.prepareStatement("insert into Users(Name,MobileNumber,Wallet) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setLong(2, user.getMobileNumber());
            statement.setInt(3,user.getWallet());
            statement.executeUpdate();
            resultSet=statement.getGeneratedKeys();
            resultSet.next();
            userId =resultSet.getInt(1);
        }
        finally {
            try {
                statement.close();
                resultSet.close();
            }catch(Exception e){}
        }
        return userId;
    }
    public int createTaxi(Taxi taxi) throws SQLException
    {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        int taxiId;
        try{
            statement = connection.prepareStatement("insert into Taxi(FreeTime,CurrentPoint,Earnings,MobileNumber) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, taxi.getFreeTime());
            statement.setString(2,taxi.getCurrentPoint());
            statement.setInt(3,taxi.getTotalEarnings());
            statement.setLong(4, taxi.getMobileNumber());
            statement.executeUpdate();
            resultSet=statement.getGeneratedKeys();
            resultSet.next();
            taxiId =resultSet.getInt(1);
        }
        finally {
            try {
                statement.close();
                resultSet.close();
            }catch(Exception e){}
        }
        return taxiId;
    }
    public void updateTaxi(int earnings,String dropPoint,int freeTime,int taxiId)
    {
    	PreparedStatement statement = null;
    	 try
         {
            statement=connection.prepareStatement("update Taxi set FreeTime=?,CurrentPoint=?,Earnings=? where TaxiId=?");
            statement.setInt(1,freeTime);
            statement.setString(2, dropPoint);
            statement.setInt(3, earnings);
            statement.setInt(4, taxiId);
            statement.executeUpdate();
         }
         catch (Exception e)
         {
            System.out.println(e);
         }
    	 finally {
    		 try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }
    }
    public void updateUser(int earnings,int userId)
    {
    	PreparedStatement statement = null;
    	try
        {
           statement=connection.prepareStatement("update Users set Wallet=? where UserId=?");
           statement.setInt(1, earnings);
           statement.setInt(2, userId);
           statement.executeUpdate();
        }
        catch (Exception e)
        {
           System.out.println(e);
        }
    	finally {
    		try {
    			statement.close();
    		}
    		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
    public int getWallet(int userId)
    {
    	int wallet = 0;
    	try {
			PreparedStatement statement = connection.prepareStatement("select Wallet from Users where UserId="+userId);
			ResultSet resultSet=statement.executeQuery();
			resultSet.next();
			wallet=resultSet.getInt("Wallet");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return wallet;
    }
    public ArrayList<User> getUsers()
    {
        ArrayList<User> userList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from Users");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                User object = new User();
                object.setUserId(resultSet.getInt("UserId"));
                object.setUserName(resultSet.getString("Name"));
                object.setMobileNumber(resultSet.getLong("MobileNumber"));
                object.setWallet(resultSet.getInt("Wallet"));
                userList.add(object);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return userList;
    }
    public ArrayList<Taxi> getTaxis()
    {
        ArrayList<Taxi> taxiList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from Taxi");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Taxi object = new Taxi();
                object.setId(resultSet.getInt("TaxiId"));
                object.setCurrentPoint(resultSet.getString("CurrentPoint"));
                object.setFreeTime(resultSet.getInt("FreeTime"));
                object.setMobileNumber(resultSet.getLong("MobileNumber"));
                object.setTotalEarnings(resultSet.getInt("Earnings"));
                taxiList.add(object);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return taxiList;
    }
}