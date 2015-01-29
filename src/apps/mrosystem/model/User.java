package apps.mrosystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
	
	private ArrayList data;
	
	private String user_id;
	private String username;
	private String email;
	private String password;
	private String timezone;
	private String currency;
	private String alias;
	private String firstname;
	private String surname;
	private String profile_img;
	private String is_active;
	private String location;
	private String role;
	private String[] user_profiles;
	
	User(){
		this.user_id ="";
		this.username ="";
		this.email ="";
		this.password ="";
		this.timezone ="";
		this.currency ="";
		this.alias ="";
		this.firstname ="";
		this.surname ="";
		this.profile_img ="";
		this.is_active ="";
		this.location ="";
		this.role = "";
		this.user_profiles = new String[]{} ;
	}
	
	User(	String user_id,
			String username,
			String email,
			String password,
			String timezone,
			String currency,
			String alias,
			String firstname,
			String surname,
			String profile_img,
			String is_active,
			String location,
			String role,
			String[] user_profiles)
	{
		this.user_id = user_id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.timezone = timezone;
		this.currency = currency;
		this.alias = alias;
		this.firstname = firstname;
		this.surname = surname;
		this.profile_img = profile_img;
		this.is_active = is_active;
		this.location = location;
		this.role = role;
		this.user_profiles = user_profiles;
	}
	
	public String getUsername() {
		return username;
	}

	public String getUserId() {
		return user_id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getTimezone() {
		return timezone;
	}


	public String getCurrency() {
		return currency;
	}


	public String getAlias() {
		return alias;
	}


	public String getFirstname() {
		return firstname;
	}


	public String getSurname() {
		return surname;
	}


	public String getProfileImg() {
		return profile_img;
	}


	public String getIsActive() {
		return is_active;
	}

	public String getLocation() {
		return location;
	}
	
	public String getRole(){
		return role;
	}
	
	public String[] getUserProfiles() {
		return user_profiles;
	}
	
	public boolean isAuthorised(String[] profiles){
		for(int i = 0; i < profiles.length ; i++){
			for(int j = 0; j < user_profiles.length;j++){
				if(profiles[i].compareTo(user_profiles[j]) == 0){
					return true;

							
				}
			}
		}
		return false;
	}


}
