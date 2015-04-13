package apps.mrosystem.database;

import java.util.ArrayList;
import java.util.Date;

import java_cup.production;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.VConsole;
import com.vaadin.external.org.slf4j.Logger;

import apps.mrosystem.MROSystemUI;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;
import apps.mrosystem.domain.WarehouseLocation;


public class DatabaseUtils{    
	public DatabaseUtils() {
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
		String qryStr = "SELECT id, name, country, city FROM corh.inventory_location_table";
		
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getAllInventory() {
		String qryStr = "SELECT inventory_table.serial_number, inventory_table.part_no, UNIX_TIMESTAMP(inventory_table.date_purchased), UNIX_TIMESTAMP(inventory_table.date_shipped), asset_table.name, asset_table.desc FROM corh.inventory_table LEFT JOIN corh.asset_table ON inventory_table.part_no = asset_table.part_number;";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getAllCustomers() {
		String qryStr = "SELECT organisation_table.org_name, title, firstname, surname, alias, profile_img, gender , email, phone, timezone , location FROM corh.customer_table LEFT JOIN corh.user_table ON user_table.id = user_id LEFT JOIN corh.organisation_table ON customer_table.org_id = organisation_table.id;";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getAllActiveAssets() {
		String qryStr = "SELECT active_products_table.part_no,  active_products_table.serial_no , asset_table.name, UNIX_TIMESTAMP(active_products_table.first_shipped), UNIX_TIMESTAMP( active_products_table.last_repaired), organisation_table.org_name, shipped_location_table.location_name, shipped_location_table.country, shipped_location_table.longitude, shipped_location_table.latitude FROM corh.active_products_table LEFT JOIN corh.organisation_table ON org_id = organisation_table.id LEFT JOIN corh.asset_table ON asset_table.part_number = part_no LEFT JOIN corh.shipped_location_table ON shipped_location_table.id = shipped_location_id;";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getAllCustomerAssets(String userId) {
		String qryStr = "SELECT active_products_table.part_no,  active_products_table.serial_no , asset_table.name, UNIX_TIMESTAMP(active_products_table.first_shipped), UNIX_TIMESTAMP( active_products_table.last_repaired), shipped_location_table.location_name, shipped_location_table.country, shipped_location_table.longitude, shipped_location_table.latitude, shipped_location_table.id FROM corh.active_products_table LEFT JOIN corh.organisation_table ON org_id = organisation_table.id LEFT JOIN corh.asset_table ON asset_table.part_number = part_no LEFT JOIN corh.shipped_location_table ON shipped_location_table.id = shipped_location_id WHERE organisation_table.id = (SELECT org_id FROM corh.customer_table WHERE user_id =" + userId + ");";		
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getAssetBOM(String partNo) {
		String qryStr = "SELECT child_id FROM corh.asset_bom_table WHERE parent_id = '"+ partNo+"';";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getBOMAndInfo(String partNo) {
		String qryStr = "SELECT asset_table.part_number, asset_table.name, asset_table.desc , asset_attribute_link_table.attribute_value, asset_bom_table.quantity, asset_table.revision FROM corh.asset_bom_table  LEFT JOIN corh.asset_table  ON asset_bom_table.child_id = asset_table.part_number  LEFT JOIN corh.asset_attribute_link_table ON asset_table.id = asset_attribute_link_table.asset_id WHERE asset_bom_table.parent_id = '"+ partNo +"' AND attribute_id = 6;";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getBasicAssetInfo(String partNo) {
		String qryStr = "SELECT part_number, asset_table.name, asset_table.desc, attribute_value FROM corh.asset_table LEFT JOIN corh.asset_attribute_link_table ON asset_table.id = asset_attribute_link_table.asset_id WHERE part_number = '" + partNo + "' AND attribute_id = 6;";
				DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getActiveAssetBOM(String serialNo) {
		String qryStr = "SELECT child_serial_no FROM corh.active_products_bom_table WHERE parent_serial_no = '" + serialNo + "';";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}
	
	
	public ArrayList<ArrayList<String>> getActiveAssetInfo(String serialNo) {
		String qryStr = "SELECT child_serial_no FROM corh.active_products_bom_table WHERE parent_serial_no = '" + serialNo + "';";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}

	public ArrayList<ArrayList<String>> getAllActiveAssetTopLevelBOM() {
		String qryStr = "SELECT parent_serial_no, GROUP_CONCAT(child_serial_no)"
				+ " FROM corh.active_products_bom_table"
				+ " JOIN corh.active_products_table ON parent_serial_no = serial_no"
				+ " WHERE parent_serial_no NOT IN(SELECT child_serial_no FROM corh.active_products_bom_table)"
				+ " GROUP BY parent_serial_no;";
		
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getUniquePartNumbers(String userId) {
		String qryStr = "SELECT part_no FROM corh.active_products_table LEFT JOIN corh.organisation_table ON org_id = organisation_table.id LEFT JOIN corh.asset_table ON asset_table.part_number = part_no LEFT JOIN corh.shipped_location_table ON shipped_location_table.id = shipped_location_id WHERE organisation_table.id = (SELECT org_id FROM corh.customer_table WHERE user_id =" + userId + ") GROUP BY part_no;";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public ArrayList<ArrayList<String>> getOrgNameByUser(String userId) {
		String qryStr = "SELECT org_name FROM corh.customer_table LEFT JOIN corh.organisation_table ON org_id = organisation_table.id WHERE user_id = " + userId+ ";";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public boolean submitWorkOrder(String serialNo, String requesterId,
			String priority, String type, String location,
			String shortDesc, String longDesc, String sDateRequired) {
		
		String qryStr = "INSERT INTO `corh`.`work_order_table` (`asset_serial_no`, `requested_by_id`, `priority`, `work_order_type_id`, `location_id`, `short_desc`, `long_desc`, `scheduled_end_date`) " 
				+ " VALUES ('" + serialNo + "', " + requesterId + ", " + priority + "," + type + " , " + location + ", '"+ shortDesc +"', '" + longDesc + "','" + sDateRequired + "' );";

		DBQuery dbQuery = new DBQuery(qryStr);
		return dbQuery.run();
	}



	public ArrayList<ArrayList<String>> getAllWorkOrders() {
		String qryStr = "SELECT work_order_table.id, asset_serial_no, short_desc, long_desc, UNIX_TIMESTAMP(scheduled_start_date), " +
		"UNIX_TIMESTAMP(scheduled_end_date), UNIX_TIMESTAMP(actual_start_date), UNIX_TIMESTAMP(actual_end_date), work_order_priority_table.priority_desc,  org_name," +
		"	work_order_type_table.type_desc, work_order_status_table.status_desc, location_name, longitude, latitude, country, part_no" +
		"		FROM corh.work_order_table " +
		" LEFT JOIN corh.work_order_priority_table ON work_order_table.priority = work_order_priority_table.id" +
		"	LEFT JOIN corh.work_order_status_table ON work_order_table.state_id = work_order_status_table.id" + 
		"		LEFT JOIN corh.work_order_type_table ON work_order_table.work_order_type_id = work_order_type_table.id" + 
		"			LEFT JOIN corh.shipped_location_table ON work_order_table.location_id = shipped_location_table.id" +
		"				LEFT JOIN corh.customer_table ON work_order_table.requested_by_id = customer_table.user_id" +
		"					LEFT JOIN corh.organisation_table ON customer_table.org_id = organisation_table.id	"
		+ "							LEFT JOIN corh.active_products_table ON work_order_table.asset_serial_no = active_products_table.serial_no";
				
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}
	
	public ArrayList<ArrayList<String>> getWorkOrderDetails(String workOrderId){
		String qryStr = "SELECT work_order_table.id, asset_serial_no, short_desc, long_desc, UNIX_TIMESTAMP(scheduled_start_date), " +
		"UNIX_TIMESTAMP(scheduled_end_date), UNIX_TIMESTAMP(actual_start_date), UNIX_TIMESTAMP(actual_end_date), work_order_priority_table.priority_desc,  org_name," +
		"	work_order_type_table.type_desc, work_order_status_table.status_desc, location_name, longitude, latitude, country, part_no" +
		"		FROM corh.work_order_table " +
		" LEFT JOIN corh.work_order_priority_table ON work_order_table.priority = work_order_priority_table.id" +
		"	LEFT JOIN corh.work_order_status_table ON work_order_table.state_id = work_order_status_table.id" + 
		"		LEFT JOIN corh.work_order_type_table ON work_order_table.work_order_type_id = work_order_type_table.id" + 
		"			LEFT JOIN corh.shipped_location_table ON work_order_table.location_id = shipped_location_table.id" +
		"				LEFT JOIN corh.customer_table ON work_order_table.requested_by_id = customer_table.user_id" +
		"					LEFT JOIN corh.organisation_table ON customer_table.org_id = organisation_table.id	"
		+ "							LEFT JOIN corh.active_products_table ON work_order_table.asset_serial_no = active_products_table.serial_no WHERE work_order_table.id = " +workOrderId+ ";";
				
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
				
		return dbQuery.getArray();
	}
	
	
	public ArrayList<ArrayList<String>> getAllTechniciansDetails(){
		String qryStr = "SELECT USER.firstname, USER.surname, USER.alias, USER.username, USER.title, USER.gender, USER.role, USER.profile_img, USER.email, " +
							"USER.phone, USER.currency, USER.timezone, USER.location, technician_table.longitude, technician_table.latitude, technician_group_table.group_name, technician_group_table.region, USER.id, " +
								"MANAGER.firstname, MANAGER.surname, MANAGER.phone, MANAGER.email FROM corh.technician_table " +
									"LEFT JOIN corh.user_table AS USER ON technician_table.user_id = USER.id " + 
										"LEFT JOIN corh.technician_group_table ON technician_group_table.id = technician_table.technician_group_id " +
											"LEFT JOIN corh.user_table AS MANAGER ON technician_group_table.manager_user_id = MANAGER.id;";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}
	
	public ArrayList<ArrayList<String>> getTechnicianDetails(String userId){
		String qryStr = "SELECT USER.firstname, USER.surname, USER.alias, USER.username, USER.title, USER.gender, USER.role, USER.profile_img, USER.email, " +
				 "USER.phone, USER.currency, USER.timezone, USER.location, technician_table.longitude, technician_table.latitude, technician_group_table.group_name, technician_group_table.region, USER.id, " +
					"MANAGER.firstname, MANAGER.surname, MANAGER.phone, MANAGER.email FROM corh.technician_table " +
						"LEFT JOIN corh.user_table AS USER ON technician_table.user_id = USER.id " +
							"LEFT JOIN corh.technician_group_table ON technician_group_table.id = technician_table.technician_group_id " +
								"LEFT JOIN corh.user_table AS MANAGER ON technician_group_table.manager_user_id = MANAGER.id " +
									" WHERE USER.id =" + userId + ";";
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}



	public static ArrayList<ArrayList<String>> getAllocatedWorkOrders(String technicianId){
		String qryStr = "SELECT work_order_allocation_table.id, work_order_id, user_id, UNIX_TIMESTAMP(work_order_allocation_table.start), " +
				"UNIX_TIMESTAMP(work_order_allocation_table.end), asset_serial_no, priority_desc, status_desc, type_desc, country, short_desc "  +
				"FROM corh.work_order_allocation_table LEFT JOIN corh.work_order_table ON work_order_allocation_table.work_order_id = work_order_table.id "  +
				"LEFT JOIN corh.work_order_priority_table ON work_order_priority_table.id = work_order_table.priority "  +
				"LEFT JOIN corh.work_order_status_table ON work_order_status_table.id = work_order_table.state_id "  +
				"LEFT JOIN corh.work_order_type_table ON work_order_type_table.id = work_order_table.work_order_type_id " +
				"LEFT JOIN corh.shipped_location_table ON shipped_location_table.id = work_order_table.location_id " +
				"WHERE user_id = " + technicianId + ";";
		
		DBQuery dbQuery = new DBQuery(qryStr);
		dbQuery.run();
		return dbQuery.getArray();
	}
	
	public static ArrayList<ArrayList<String>> getAllAllocatedWorkOrders(){
			String qryStr = "SELECT id, work_order_id, user_id, UNIX_TIMESTAMP(start), UNIX_TIMESTAMP(end) FROM corh.work_order_allocation_table;";
			DBQuery dbQuery = new DBQuery(qryStr);
			dbQuery.run();
			return dbQuery.getArray();
	}



	public static boolean assignWorkOrder(String workOrderId,String technicianId, String start, String end) {
		
		String updateWorkOrderAllocation= "INSERT INTO `corh`.`work_order_allocation_table` (`work_order_id`, `user_id`, `start`, `end`) VALUES ('" + workOrderId + "', '" + technicianId + "', '" + start + "', '" + end + "');";
		String updateWorkOrder = "UPDATE `corh`.`work_order_table` SET `state_id`='2' WHERE `id`='" + workOrderId + "';";
		DBQuery updateWorkOrderAllocationTableDbQuery = new DBQuery(updateWorkOrderAllocation);
		DBQuery updateWorkOrderTableDbQuery = new DBQuery(updateWorkOrder);
		return updateWorkOrderAllocationTableDbQuery.run() && updateWorkOrderTableDbQuery.run();

	}



	public static boolean updateWorkOrderAllocation(String jobId, String workOrderId,String technicianId, String start, String end) {
		String qryStr = "UPDATE `corh`.`work_order_allocation_table` SET `user_id`='" + technicianId +"', `start`='" + start + "', `end`='" + end + "' WHERE `id`='" + jobId + "';";
		DBQuery dbQuery = new DBQuery(qryStr);
		return dbQuery.run();
	}



	public static boolean removeWorkOrderAllocation(String jobId) {
		String removeJobQry = "DELETE FROM corh.work_order_allocation_table WHERE id=" + jobId + ";";
		String updateWorkOrderQry = "UPDATE corh.work_order_table SET state_id='1' WHERE work_order_table.id = (SELECT work_order_allocation_table.work_order_id FROM corh.work_order_allocation_table WHERE work_order_allocation_table.id = " +jobId + ");";
		System.out.println(removeJobQry);
		System.out.println(updateWorkOrderQry);
		
		DBQuery removeJobQryDbQuery = new DBQuery(removeJobQry);
		DBQuery updateWorkOrderQryDbQuery = new DBQuery(updateWorkOrderQry);
		
		boolean success = updateWorkOrderQryDbQuery.run();
		if(success){
			success = removeJobQryDbQuery.run();
		}
		return success;
	}
	


}
