package apps.mrosystem.controller;

import java.util.HashMap;

import apps.mrosystem.database.DatabaseHelper;
import apps.mrosystem.domain.User;
import apps.mrosystem.view.UserDetailsView;

public class UserDetailsHandler {
	UserDetailsView userDetailsView;
	User user;
	
	public UserDetailsHandler(UserDetailsView userDetailsView, User user) {
		this.userDetailsView = userDetailsView;
		this.user = user;
		userDetailsView.setHandler(this);
		
	}
	
	public void show(){
		userDetailsView.show();
	}


	public User getUser() {
		// TODO Auto-generated method stub
		return this.user;
	}

	public String getFirstName() {
		// TODO Auto-generated method stub
		return user.getFirstname();
	}

	public String getSurname() {
		// TODO Auto-generated method stub
		return user.getSurname();
	}

	public String getAlias() {
		// TODO Auto-generated method stub
		return user.getAlias();
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return user.getTitle();
	}

	public String getGender() {
		// TODO Auto-generated method stub
		return user.getGender();
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	public String getLocation() {
		// TODO Auto-generated method stub
		return user.getLocation();
	}

	public String getPhone() {
		// TODO Auto-generated method stub
		return user.getPhone();
	}

	public String getUserId(){
		return user.getUserId();
	}
	
	public void updateProfile(HashMap<String,String> newValues) {
		DatabaseHelper dbHelper = new DatabaseHelper();
		
		if(dbHelper.updateUserDetails(newValues.get("id"),
				newValues.get("firstname"), newValues.get("lastname"),
				newValues.get("alias"), newValues.get("title"),
				newValues.get("gender"), newValues.get("email"),
				newValues.get("location"), newValues.get("phone"))){
			userDetailsView.updateSuccess();
		}else{
			userDetailsView.updateFailed();
		}
		
		
	}
}
