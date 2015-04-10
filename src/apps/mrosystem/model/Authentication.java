package apps.mrosystem.model;

import java.util.ArrayList;

import apps.mrosystem.database.DatabaseUtils;
import apps.mrosystem.domain.User;


public class Authentication {
	
	private User userData;
	
	public Authentication(){
		
			

	}
	
	public Boolean authenticate(String login, String password){
		
			//String sql_query = "SELECT user_table.*, profile_table.profile_name FROM mrosystem.user_table LEFT JOIN mrosystem.profile_table ON profile_table.profile_id = user_table.profile_id WHERE (username ='" + login + "' or email = '" + login + "') AND password = '" + password + "';";

			
			DatabaseUtils dbHelper = new DatabaseUtils();
			
			boolean autorised = dbHelper.authenticate(login, password);
			
			if(autorised){		
				userData = dbHelper.getUserInfo(login);
				return true;
			}
			return false;
			

	}
	
	public User getUser(){
		return userData;
	}
	
	
	
	
}
