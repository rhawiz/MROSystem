package apps.mrosystem.database;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.VConsole;
import com.vaadin.external.org.slf4j.Logger;

import apps.mrosystem.MROSystemUI;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;
import apps.mrosystem.domain.WarehouseLocation;


public class DatabaseHelper{    
	public DatabaseHelper() {
	}



	public boolean updateUserDetails(String id, String firstname, String lastname, String alias, String title, String gender, String email, String location, String phone){
		
		String sqlQuery = 
				
				"UPDATE corh.user_table SET "
				+ "email='" + email  + "' "
				+ ", alias='" + alias + "' "
				+ ", firstname='" + firstname + "' "
				+ ", surname='" + lastname + "' "
				+ ", location='" + location  + "' "
				+ ", phone='" + phone  + "' "
				+ ", gender='" + gender + "' "
				+ ", title='" + title + "' "
				+ " WHERE id='" +id+"';";
		
		return new DBQuery(sqlQuery).run();

	}

	public boolean authenticate(String login, String password) {
		String sql_query = "SELECT COUNT(*) FROM corh.user_table WHERE username = '"+ login + "' AND password = '" + password + "'";
				
		DBQuery query = new DBQuery(sql_query);
		query.run();
		
		int result = 0;
		if(query.getRowCount() > 0){
			result = Integer.parseInt(query.get(0,0));
		}
		return result == 1;
		
		
	}

	public User getUserInfo(String login) {
		String sql_query = 
				  "SELECT user_table.id, user_table.username, user_table.email, user_table.password, "
				  + "user_table.timezone,user_table.currency, user_table.alias, user_table.firstname, "
				  + "user_table.surname, user_table.profile_img, user_table.is_active, user_table.location,  "
				  + "role_table.role_name, profile_table.profile_name, "
				  + "user_table.phone, user_table.gender, user_table.title "
				  + "FROM corh.user_table "
				+ " LEFT JOIN ("
				+ "	corh.user_profiles_table"
				+ "		LEFT JOIN corh.profile_table ON"
				+ "			user_profiles_table.profile_id = profile_table.id"
				+ "	) ON"
				+ "		user_table.id = user_profiles_table.user_id"
				+ " 		LEFT JOIN corh.role_table ON"
				+ "				user_table.role = role_table.id"
				+ "					WHERE (username ='" + login + "' or email = '" + login +"' )";
		
		
		DBQuery query = new DBQuery(sql_query);
		if(query.run()){
			ArrayList<String> userInfo = (ArrayList<String>) query.getArray().get(0);

			String user_id = (String) userInfo.get(0);
			String username = (String) userInfo.get(1);
			String email= (String) userInfo.get(2);
			String password = (String) userInfo.get(3);
			String timezone= (String) userInfo.get(4);
			String currency= (String) userInfo.get(5);
			String alias= (String) userInfo.get(6);
			String firstname= (String) userInfo.get(7);
			String surname= (String) userInfo.get(8);
			String profile_img= (String) userInfo.get(9);
			String is_active= (String) userInfo.get(10);
			String location= (String) userInfo.get(11);
			String role = userInfo.get(12);
			String phone = (String) userInfo.get(14);
			String gender = (String) userInfo.get(15);
			String title = (String) userInfo.get(16);
			
			String[] user_profiles = new String[query.getArray().size()];

			for (int i = 0; i < query.getArray().size(); i++) {
				user_profiles[i] = query.getArray().get(i).get(13);
			}
			
			return new User(user_id,username,email,password,timezone,
								currency,alias,firstname,surname,
									profile_img,is_active, location,role,user_profiles,
									phone, gender, title);

			
		}


		
		return new User();
		
			
			
	}
	
	
	public ArrayList<ArrayList<String>> getAssetInfo(String partNo){
		String assetInfoSqlString = "SELECT attribute_name, attribute_value, attribute_desc FROM corh.asset_attribute_link_table "+
				"	LEFT JOIN corh.asset_attribute_table ON attribute_id = asset_attribute_table.id "+
				"		LEFT JOIN corh.asset_table ON asset_id = asset_table.id"+
				"			WHERE part_number = '" + partNo + "' ORDER BY attribute_name;";
		DBQuery assetInfoQuery = new DBQuery(assetInfoSqlString);
		assetInfoQuery.run();
		return assetInfoQuery.getArray();
	}
	
	
	public ArrayList<ArrayList<String>>  getAllTopLevelBOM(){
		String topLevelBomSqlString = "SELECT parent_id, GROUP_CONCAT(child_id)" +
				" 	FROM corh.asset_bom_table " +
				" 		JOIN corh.asset_table ON parent_id = part_number" +
				"				WHERE parent_id NOT IN(SELECT child_id FROM corh.asset_bom_table)" +
				" 	GROUP BY parent_id;";
		


		
		DBQuery topLevelBomQuery = new DBQuery(topLevelBomSqlString);
		topLevelBomQuery.run();
		return topLevelBomQuery.getArray();
		
		
	}
	
	
	public ArrayList<ArrayList<String>> getAllBOM(){
		String allBomSqlString = "SELECT parent_id, GROUP_CONCAT(child_id)"+
				" FROM corh.asset_bom_table " +
				" JOIN corh.asset_table ON parent_id=part_number " +
				" GROUP BY parent_id;";
		DBQuery allBomQuery = new DBQuery(allBomSqlString);
		allBomQuery.run();
		return allBomQuery.getArray();
	}
	
	public ArrayList<ArrayList<String>> getAllPartInfo(){
		
		String partInfoSqlString = ""
		+ "SELECT asset_table.part_number, "
		+ "       GROUP_CONCAT(asset_table.part_number, ',', asset_table.name, ',', asset_table.desc, ',', asset_attribute_link_table.attribute_value) "
		+ "FROM corh.asset_table "
		+ "LEFT JOIN corh.asset_attribute_link_table ON asset_table.id = asset_attribute_link_table.asset_id "
		+ "WHERE asset_attribute_link_table.attribute_id = 6 "
		+ "GROUP BY part_number "
		+ "UNION ALL "
		+ "  (SELECT asset_table.part_number, "
		+ "          GROUP_CONCAT(part_number, ',', asset_table.name, ',', asset_table.desc, ',null') "
		+ "   FROM corh.asset_table "
		+ "   WHERE asset_table.part_number NOT IN "
		+ "       (SELECT asset_table.part_number "
		+ "        FROM corh.asset_table "
		+ "        LEFT JOIN corh.asset_attribute_link_table ON asset_table.id = asset_attribute_link_table.asset_id "
		+ "        WHERE asset_attribute_link_table.attribute_id = 6 "
		+ "        GROUP BY part_number) "
		+ "   GROUP BY part_number )";
		DBQuery partInfoquery = new DBQuery(partInfoSqlString);
		partInfoquery.run();
		return partInfoquery.getArray();
	}

	public boolean submitPurchaseRequest(ArrayList<String> assetsArrayList, String locationId, String purchaseDate , String shipDate	) {
		String qryStr = "INSERT INTO `corh`.`inventory_table` (`part_no`, `location_id`, `date_purchased`, `date_shipped`) VALUES";
		
		
		for(String partNo: assetsArrayList){
			qryStr += "\n('"  + partNo + "'," + "'" +  locationId + "', '" + purchaseDate + "', '" + shipDate + "'),";
		}
		qryStr = qryStr.substring(0, qryStr.length() - 1);
		
		qryStr += ";";
		
		DBQuery dbQuery = new DBQuery(qryStr);
		return dbQuery.run();
		
	}



	public ArrayList<ArrayList<String>> getWarehouseLocations() {
		// TODO Auto-generated method stub
		String qryStr = "SELECT id, name, country, city FROM corh.inventory_location_table";
		
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		
		return dbQuery.getArray();
	}

}
