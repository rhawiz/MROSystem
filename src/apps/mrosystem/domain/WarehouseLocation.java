package apps.mrosystem.domain;

public class WarehouseLocation {




	String id;
	String city;
	String name;
	String country;

	
	public WarehouseLocation(String id, String city, String name, String country) {
		super();
		this.id = id;
		this.city = city;
		this.name = name;
		this.country = country;
	}
	
	public String getId() {
		return id;
	}


	public String getCity() {
		return city;
	}


	public String getName() {
		return name;
	}


	public String getCountry() {
		return country;
	}

	
	@Override
	public String toString(){
		return name + " (" + city + ", " + country + ")";
	}
	
	
}
