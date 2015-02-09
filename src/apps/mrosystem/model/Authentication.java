package apps.mrosystem.model;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;

import apps.mrosystem.DatabaseConnection;
import apps.mrosystem.DBQuery;
import apps.mrosystem.datasource.Datasource;

public class Authentication {
	
	private User userData;
	
	public Authentication(){
		
			

	}
	
	public Boolean authenticate(String login, String password){
		
			//String sql_query = "SELECT user_table.*, profile_table.profile_name FROM mrosystem.user_table LEFT JOIN mrosystem.profile_table ON profile_table.profile_id = user_table.profile_id WHERE (username ='" + login + "' or email = '" + login + "') AND password = '" + password + "';";

			
			String sql_query = 
				  "SELECT user_table.id, user_table.username, user_table.email, user_table.password, user_table.timezone,user_table.currency, user_table.alias, user_table.firstname, user_table.surname, user_table.profile_img, user_table.is_active, user_table.location,  role_table.role_name, profile_table.profile_name FROM mrosystem.user_table "
				+ " LEFT JOIN ("
				+ "	mrosystem.user_profiles_table"
				+ "		LEFT JOIN mrosystem.profile_table ON"
				+ "			user_profiles_table.profile_id = profile_table.id"
				+ "	) ON"
				+ "		user_table.id = user_profiles_table.user_id"
				+ " 		LEFT JOIN mrosystem.role_table ON"
				+ "				user_table.role = role_table.id"
				+ "					WHERE (username ='" + login + "' or email = '" + login +"' ) AND password = '" + password + "';";
			
			

			DBQuery query = new DBQuery(sql_query);
	
			
			if(query.getRowCount() >= 1){
				String user_id = query.get(0,0);
				String username = query.get(0,1);
				String email= query.get(0,2);
				String timezone= query.get(0,4);
				String currency= query.get(0,5);
				String alias= query.get(0,6);
				String firstname= query.get(0,7);
				String surname= query.get(0,8);
				String profile_img= query.get(0,9);
				String is_active= query.get(0,10);
				String location= query.get(0,11);
				String role = query.get(0, 12);
				
				String[] user_profiles = new String[query.getRowCount()];
				for (int i = 0; i < query.getRowCount(); i++) {
					user_profiles[i] = query.get(i,13);
				}
				userData = new User(user_id,username,email,password,timezone,
									currency,alias,firstname,surname,
										profile_img,is_active, location,role,user_profiles);
				return true;
			}
			else{
				return false;
			}

	}
	
	public User getUser(){
		return userData;
	}
	
	
	
	
}
