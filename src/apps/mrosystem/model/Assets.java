package apps.mrosystem.model;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;

import apps.mrosystem.DBQuery;
import apps.mrosystem.datasource.Datasource;
import apps.mrosystem.utils.Utils;

public class Assets {

    private HashMap<String, String[]> topLevelBomData;
	
    private HashMap<String, String[]> allBomData;
    
    private HashMap<String, String[]> partInfoData;
    
    private HashMap<String, Part> partsHashMap;

    private HierarchicalContainer singleLevelAssetsHierarchy;
    
    private  HierarchicalContainer topLevelAssetsHierarchy;
    
	private HierarchicalContainer allAssetsHierarchy;
	
	
	public HierarchicalContainer getTopLevelBom(){
		return topLevelAssetsHierarchy;
	}
	

	public HierarchicalContainer getAllAssetsHierarchicalContainer(){
		return allAssetsHierarchy;
	}
	
	public HierarchicalContainer getSingleLevelHierarchicalContainer(){
		return singleLevelAssetsHierarchy;
	}
	
	public HierarchicalContainer getTopLevelHierarchicalContainer(){
		return topLevelAssetsHierarchy;
	}
	
	public HierarchicalContainer getAssetBomHierarchicalContainer(Part part){
		HierarchicalContainer assetBom = new HierarchicalContainer();
        for (Part asset : part.getChildren()) {
        	
        	String partNo = part.getPartNo();
        	String name   = part.getName();
        	String desc   = part.getDescription();
        	        	      
        	Item item = assetBom.addItem(asset);
            
        	if(!asset.isLeaf()){
        		expandChildrenHierarchy(assetBom, asset);
        	}
        	else{
            	assetBom.setChildrenAllowed(asset, false);
            }
        }
        return assetBom;
		
	}
	
	
	public HashMap<String,Attribute> getAssetInfo(String partNo){
		String assetInfoSqlString = "SELECT attribute_name, attribute_value, attribute_desc FROM mrosystem.asset_attribute_link_table "+
									"	LEFT JOIN mrosystem.asset_attribute_table ON attribute_id = asset_attribute_table.id "+
									"		LEFT JOIN mrosystem.asset_table ON asset_id = asset_table.id"+
									"			WHERE part_number = '" + partNo + "' ORDER BY attribute_name;";
		DBQuery assetInfoQuery = new DBQuery(assetInfoSqlString);
		ArrayList<ArrayList<String>> assetInfoArray = assetInfoQuery.getArray();
		
		HashMap<String,Attribute> assetsAttributes = new HashMap();
		
		for (int i = 0; i < assetInfoArray.size(); i++) {
			
			Attribute attr = new Attribute(assetInfoArray.get(i).get(0), assetInfoArray.get(i).get(1), assetInfoArray.get(i).get(2));
			
			assetsAttributes.put(assetInfoArray.get(i).get(0), attr);
		}
		return assetsAttributes;
	}
	
	public void retrieveData() throws SQLException, IOException, PropertyVetoException {
		String topLevelBomSqlString = "SELECT parent_id, GROUP_CONCAT(child_id)" +
				" 	FROM mrosystem.asset_bom_table " +
				" 		JOIN mrosystem.asset_table ON parent_id = part_number" +
				"				WHERE parent_id NOT IN(SELECT child_id FROM mrosystem.asset_bom_table)" +
				" 	GROUP BY parent_id;";
		
		String allBomSqlString = "SELECT parent_id, GROUP_CONCAT(child_id)"+
									" FROM mrosystem.asset_bom_table " +
									" JOIN mrosystem.asset_table ON parent_id=part_number " +
									" GROUP BY parent_id;";
											
		String partInfoSqlString = ""
				+ "SELECT asset_table.part_number, "
				+ "       GROUP_CONCAT(part_number, ',', asset_table.name, ',', asset_table.desc, ',', asset_attribute_link_table.attribute_value) "
				+ "FROM mrosystem.asset_table "
				+ "LEFT JOIN asset_attribute_link_table ON asset_table.id = asset_attribute_link_table.asset_id "
				+ "WHERE asset_attribute_link_table.attribute_id = 6 "
				+ "GROUP BY part_number "
				+ "UNION ALL "
				+ "  (SELECT asset_table.part_number, "
				+ "          GROUP_CONCAT(part_number, ',', asset_table.name, ',', asset_table.desc, ',null') "
				+ "   FROM mrosystem.asset_table "
				+ "   WHERE asset_table.part_number NOT IN "
				+ "       (SELECT asset_table.part_number "
				+ "        FROM mrosystem.asset_table "
				+ "        LEFT JOIN asset_attribute_link_table ON asset_table.id = asset_attribute_link_table.asset_id "
				+ "        WHERE asset_attribute_link_table.attribute_id = 6 "
				+ "        GROUP BY part_number) "
				+ "   GROUP BY part_number )";
		
		DBQuery topLevelBomQuery = new DBQuery(topLevelBomSqlString);
		DBQuery allBomQuery = new DBQuery(allBomSqlString);
		DBQuery partInfoquery = new DBQuery(partInfoSqlString);
		
		ResultSet resultSet = topLevelBomQuery.getResultSet();		
		
        resultSet.first();
        topLevelBomData = Utils.resultSetToHashMap(resultSet);
    	
        resultSet = allBomQuery.getResultSet();
        allBomData = Utils.resultSetToHashMap(resultSet);
        
        resultSet = partInfoquery.getResultSet();
        partInfoData = Utils.resultSetToHashMap(resultSet);

        partsHashMap = new HashMap<String,Part>();
        
        for (Map.Entry<String, String[]> asset : partInfoData.entrySet()) {
      	   
        	String partNo = partInfoData.get(asset.getKey())[0];
        	String name   =	partInfoData.get(asset.getKey())[1];
        	String desc   = partInfoData.get(asset.getKey())[2];
        	String assetClass  = partInfoData.get(asset.getKey())[3];
        	
        	Part part = new Part(partNo,name,desc,assetClass);
        	
        	if(allBomData.get(partNo) != null){
        		expandChildrenParts(part,allBomData.get(partNo));
        	}
        	
        	partsHashMap.put(partNo, part);

        }
        

        topLevelAssetsHierarchy = new HierarchicalContainer();
        topLevelAssetsHierarchy.addContainerProperty("Part Number", String.class, null);
        topLevelAssetsHierarchy.addContainerProperty("Name", String.class, null);
        topLevelAssetsHierarchy.addContainerProperty("Class", String.class, null);
        topLevelAssetsHierarchy.addContainerProperty("Description", String.class, null);
        
        for (Map.Entry<String, String[]> asset : topLevelBomData.entrySet()) {
     	   
        	Part part = (Part) partsHashMap.get(asset.getKey()).clone();
        	
        	String partNo = part.getPartNo();
        	String name   = part.getName();
        	String desc   = part.getDescription();
        	String assetClass   = part.getPartClass();
        	        	      
        	Item item = topLevelAssetsHierarchy.addItem(part);
            item.getItemProperty("Part Number").setValue(partNo);
            item.getItemProperty("Name").setValue(name);
            item.getItemProperty("Class").setValue(assetClass);
            item.getItemProperty("Description").setValue(desc);
            
            if(!part.isLeaf()){
            	expandChildrenHierarchy(topLevelAssetsHierarchy, part);
            }
        }
        
        singleLevelAssetsHierarchy = new HierarchicalContainer();
        singleLevelAssetsHierarchy.addContainerProperty("Part Number", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("Name", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("Class", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("Description", String.class, null);
        
        for (Map.Entry<String, String[]> asset : allBomData.entrySet()) {
      	   
        	Part part = (Part) partsHashMap.get(asset.getKey()).clone();
        	
        	
        	String partNo = part.getPartNo();
        	String name   = part.getName();
        	String assetClass   = part.getPartClass();
        	String desc   = part.getDescription();
        	        	
        	Item item = singleLevelAssetsHierarchy.addItem(part);
            item.getItemProperty("Part Number").setValue(partNo);
            item.getItemProperty("Name").setValue(name);
            item.getItemProperty("Class").setValue(assetClass);
            item.getItemProperty("Description").setValue(desc);
            
            singleLevelAssetsHierarchy.setChildrenAllowed(part, true);
            
            ArrayList<Part> childrenParts = part.getChildren();
            
            for (int i = 0; i < childrenParts.size(); i++) {
            	
            	
            	Part childPart = (Part) partsHashMap.get(childrenParts.get(i).getPartNo()).clone();
            	
            	String childPartNo = childPart.getPartNo();
            	String childName   = childPart.getName();
            	String childClass   = childPart.getPartClass();
            	String childDesc   = childPart.getDescription();
            	
            	        	      
            	Item childItem = singleLevelAssetsHierarchy.addItem(childPart);
            	childItem.getItemProperty("Part Number").setValue(childPartNo);
            	childItem.getItemProperty("Name").setValue(childName);
            	childItem.getItemProperty("Class").setValue(childClass);
            	childItem.getItemProperty("Description").setValue(childDesc);
                
                singleLevelAssetsHierarchy.setParent(childPart, part);
                singleLevelAssetsHierarchy.setChildrenAllowed(childPart, false);
                
			}
        }
        
        
        
		allAssetsHierarchy = new HierarchicalContainer();
		allAssetsHierarchy.addContainerProperty("Part Number", String.class, null);
		allAssetsHierarchy.addContainerProperty("Name", String.class, null);
		allAssetsHierarchy.addContainerProperty("Class", String.class, null);
		allAssetsHierarchy.addContainerProperty("Description", String.class, null);
        
        for (Map.Entry<String, String[]> asset : partInfoData.entrySet()) {
       	   
        	Part part = partsHashMap.get(asset.getKey());
        	
        	String partNo = part.getPartNo();
        	String name   = part.getName();
        	String assetClass   = part.getPartClass();
        	String desc   = part.getDescription();
        	        	      
        	Item item = allAssetsHierarchy.addItem(part);
            item.getItemProperty("Part Number").setValue(partNo);
            item.getItemProperty("Name").setValue(name);
            item.getItemProperty("Class").setValue(assetClass);
            item.getItemProperty("Description").setValue(desc);
            
            allAssetsHierarchy.setChildrenAllowed(part, false);
            
        }
         
       
	}
	
	public void expandChildrenParts(Part parent, String[] childrenKeys){
		
		for (int i = 0; i < childrenKeys.length; i++) {
        	String partNo = partInfoData.get(childrenKeys[i])[0];
        	String name   =	partInfoData.get(childrenKeys[i])[1];
        	String desc   = partInfoData.get(childrenKeys[i])[2];
        	String assetClass   = partInfoData.get(childrenKeys[i])[3];
        	
        	
        	
			Part childPart = new Part(partNo,name,desc,assetClass);
			
			parent.addChild(childPart);
            
			if(allBomData.get(partNo) != null){
				expandChildrenParts(childPart, allBomData.get(partNo));
			}
			
		}
	}

	public void expandChildrenHierarchy(HierarchicalContainer container, Part parent){
		
		container.setChildrenAllowed(parent, true);
		
		ArrayList<Part> childrenParts = parent.getChildren();
		
		for (int i = 0; i < childrenParts.size(); i++) {
			
			Part childPart = (Part) partsHashMap.get(childrenParts.get(i).getPartNo()).clone();
				
        	String partNo = childPart.getPartNo();
        	String name   = childPart.getName();
        	String assetClass   = childPart.getPartClass();
        	String desc   = childPart.getDescription();
			
            Item childItem = container.addItem(childPart);
            
            for(Object prop: childItem.getItemPropertyIds()){
            	switch((String) prop){
	            	case "Part Number": 
	            		childItem.getItemProperty(prop).setValue(partNo);
	            		break;
	            	case "Name": 
	            		childItem.getItemProperty(prop).setValue(name);
	            		break;
	            	case "Description": 
	            		childItem.getItemProperty(prop).setValue(desc);
	            		break;
	            	case "Class": 
	            		childItem.getItemProperty(prop).setValue(assetClass);
	            		break;
	            	default:
	            		childItem.getItemProperty(prop).setValue(partNo + " ("+name+")");
	            		break;
            		
            	}
            }
          			
            container.setParent(childPart, parent);
            
			if(!childPart.isLeaf()){
				expandChildrenHierarchy(container, childPart);
			}else{
				container.setChildrenAllowed(childPart, false);
			}
			

		}
	}


	public HierarchicalContainer getAssetsClassFilter() {
		HierarchicalContainer classFilters = new HierarchicalContainer();
		classFilters.addItem("All");
		classFilters.setChildrenAllowed("All", false);
		for(Part asset : partsHashMap.values()){
			classFilters.addItem(asset.getPartClass());
			classFilters.setChildrenAllowed(asset.getPartClass(), false);
		}
		
		return classFilters;
	}

	
	
}
