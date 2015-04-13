package apps.mrosystem.domain;

import java.io.Serializable;


public class User implements Serializable{
		
	private String userId;
	private String username;
	private String email;
	private String password;
	private String timezone;
	private String currency;
	private String alias;
	private String firstname;
	private String surname;
	private String profileImg;
	private String isActive;
	private String location;
	private String role;
	private String[] userProfiles;

	private String phone;

	private String gender;

	private String title;
	
	public User(){
		this.userId ="";
		this.username ="";
		this.email ="";
		this.password ="";
		this.timezone ="";
		this.currency ="";
		this.alias ="";
		this.firstname ="";
		this.surname ="";
		this.profileImg ="profile_img/default.jpg";
		this.isActive ="";
		this.location ="";
		this.role = "";
		this.userProfiles = new String[]{} ;
	}
	
	public User(	String user_id,
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
			String[] user_profiles,
			String phone,
			String gender,
			String title)
	{
		this.userId = user_id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.timezone = timezone;
		this.currency = currency;
		this.alias = alias;
		this.firstname = firstname;
		this.surname = surname;
		this.profileImg = profile_img;
		this.isActive = is_active;
		this.location = location;
		this.role = role;
		this.userProfiles = user_profiles;
		this.phone = phone;
		this.gender = gender;
		this.title = title;
	}
	
	
	public String getUsername() {
		return username;
	}

	public String getUserId() {
		return userId;
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
		return profileImg;
	}


	public String getIsActive() {
		return isActive;
	}

	public String getLocation() {
		return location;
	}
	
	public String getRole(){
		return role;
	}
	
	public String[] getUserProfiles() {
		return userProfiles;
	}
	
	public String getPhone() {
		return phone;
	}

	public String getGender() {
		return gender;
	}

	public String getTitle() {
		return title;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserProfiles(String[] userProfiles) {
		this.userProfiles = userProfiles;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAuthorised(String[] profiles){
		for(int i = 0; i < profiles.length ; i++){
			for(int j = 0; j < userProfiles.length;j++){
				if(profiles[i].compareTo(userProfiles[j]) == 0){
					return true;

							
				}
			}
		}
		return false;
	}


}
