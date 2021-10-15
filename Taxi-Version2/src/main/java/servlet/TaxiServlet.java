package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import logical.LogicalLayer;
import pojo.Taxi;
import pojo.User;
public class TaxiServlet extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{ 
		String choice = req.getParameter("users");
		ArrayList<User> userList=null;
		ArrayList<Taxi> taxiList=null;
		LogicalLayer logical= new LogicalLayer();
		if(choice.equals("User"))
		{
			userList = logical.getUser();
			JSONArray array= new JSONArray();
			for(User user:userList)
			{
				JSONObject user1 = new JSONObject(user);
				array.put(user1);
			}
			res.setContentType("application/json");
	        res.getWriter().print(array.toString());
	        res.getWriter().close();
		}
		else if(choice.equals("Taxi"))
		{
			taxiList=logical.getTaxi();
			JSONArray array= new JSONArray();
			for(Taxi taxi:taxiList)
			{
				JSONObject taxi1 = new JSONObject(taxi);
				array.put(taxi1);
			}
			res.setContentType("application/json");
	        res.getWriter().print(array.toString());
	        res.getWriter().close();
		}
		else if(choice.equals("SubmitNewUser"))
		{
			String name=req.getParameter("name");
			int wallet=Integer.parseInt(req.getParameter("wallet"));
			long mobile=Long.parseLong(req.getParameter("mobile"));
			User user=logical.getUserObject(name, mobile, wallet);
			User object=null;
			try {
				object=logical.setUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter pw=res.getWriter();
			pw.println("User Successfully added");
		}
		else if(choice.equals("SubmitNewTaxi"))
		{
			long mobile=Long.parseLong(req.getParameter("mobile"));
			Taxi taxi=logical.getTaxiObject(8,"A",0,mobile);
			Taxi object=null;
			try {
				object=logical.setTaxi(taxi);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter pw=res.getWriter();
			pw.println("Taxi succesfully added");
		}
		else if(choice.equals("BookTaxi"))
		{
			int userId=Integer.parseInt(req.getParameter("userid"));
			int pickupTime=Integer.parseInt(req.getParameter("time"));
			char pickupPoint=req.getParameter("pickuppoint").charAt(0);
			char dropPoint=req.getParameter("droppoint").charAt(0);
			ArrayList<Taxi> taxis=logical.getTaxi();
			ArrayList<Taxi> freeTaxis=logical.getFreeTaxis(taxis, pickupPoint, pickupTime);
			JSONArray array= new JSONArray();
			if(freeTaxis.size()==0)
			{
				array.put("No Taxis available");
			}
			else
			{
				freeTaxis.sort(Comparator.comparingInt(Taxi::getTotalEarnings));
				int bookedTaxiId=logical.BookTaxi(userId, pickupPoint, dropPoint, pickupTime, freeTaxis);
				array.put("Taxi-"+bookedTaxiId+" is alloted");
			}
			res.getWriter().print(array.toString());
			res.getWriter().close();
		}
		
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		doPost(req,res);
	}
}