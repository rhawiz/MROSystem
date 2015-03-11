package apps.mrosystem.utils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.vaadin.ui.Tree;

public class Utils {
	
	
	public static String getMySqlDateTime(Date date){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(date);
		
	}

	public static void populateTree(Tree tree, Object[] data){
		
		/* Fix this later (only goes down two levels) */

		
		for (int i=0; i<data.length; i++) {
			Object item = ((Object[]) data[i])[0];
			tree.addItem(item);
			
			if(((Object[]) data[i]).length == 1){
				tree.setChildrenAllowed(item, false);
			}
			else{
				
				for (int j = 1; j < ((Object[])data[i]).length; j++) {
					Object subItem = ((Object[])data[i])[j];
					if(subItem instanceof Object[]){
						tree.addItem(((Object[])subItem)[0]);
						tree.setParent( ((Object[])subItem)[0],((Object[]) data[i])[0]);
						for (int k = 1; k < ((Object[])subItem).length; k++) {
							
							tree.addItem(((Object[])subItem)[k]);
							tree.setParent(((Object[])subItem)[k], ((Object[])subItem)[0]);
							tree.setChildrenAllowed(((Object[])subItem)[k],false);
						}
						//populateTree(tree, (Object[]) subItem);
					}
					else{
					
						tree.addItem(subItem);
						tree.setParent(subItem, item);
						tree.setChildrenAllowed(subItem, false);
				
					}
				}
			}
			
		}

	}
	

	
	
	public static HashMap<String, String[]> arrayListToHashMap(ArrayList<ArrayList<String>> array) throws SQLException {

		HashMap<String,String[]> hashMap = new HashMap<String,String[]>();
		
		
        for (int i = 0; i < array.size(); i++) {
        	
        	String key = (String) array.get(i).get(0);
        	
        	String[] value = array.get(i).get(1).split(",");
            
            hashMap.put(key, value);
        }
		return hashMap;
	}
	
	
	public static boolean isNull(Object obj){
		if(obj == null)
			return true;
		
		return false;
	}
	




}
