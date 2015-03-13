package apps.mrosystem.model;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.mrosystem.services.ProvideAssetDataService;
import apps.mrosystem.threads.NotifyingThread;
import apps.mrosystem.utils.Utils;
import apps.mrosystem.view.AssetDetailsView;
import apps.mrosystem.controller.AssetDetailsHandler;
import apps.mrosystem.controller.PurchaseAssetHandler;
import apps.mrosystem.database.DBQuery;
import apps.mrosystem.database.DatabaseHelper;
import apps.mrosystem.domain.Attribute;
import apps.mrosystem.domain.Part;
import apps.mrosystem.view.*;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;

public class Assets extends NotifyingThread{

    private HashMap<String, String[]> topLevelBomData;
	
    private HashMap<String, String[]> allBomData;
    
    private HashMap<String, String[]> partInfoData;
    
    private HashMap<String, Part> partsHashMap;

    private HierarchicalContainer singleLevelAssetsHierarchy;
    
    private  HierarchicalContainer topLevelAssetsHierarchy;
    
	private HierarchicalContainer allAssetsHierarchy;
		
	private Assets model;
	
	private boolean success = true;
	
	public Assets(){
		model = this;
	}
	
	
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
		assetBom.addContainerProperty("Part Number", String.class, null);

        for (Part asset : part.getChildren()) {
        	
        	String partNo = part.getPartNo();
        	String name   = part.getName();
        	String desc   = part.getDescription();
        	
        	
        	        	      
        	Item item = assetBom.addItem(asset);
            item.getItemProperty("Part Number").setValue(partNo);
            

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

		ArrayList<ArrayList<String>> assetInfoArray = new ProvideAssetDataService(). new RetrievePartInformation(partNo).getArray();
				
		HashMap<String,Attribute> assetsAttributes = new HashMap();
		
		for (int i = 0; i < assetInfoArray.size(); i++) {
			
			Attribute attr = new Attribute(assetInfoArray.get(i).get(0), assetInfoArray.get(i).get(1), assetInfoArray.get(i).get(2));
			
			assetsAttributes.put(assetInfoArray.get(i).get(0), attr);
		}
		return assetsAttributes;
	}
	
	private void retrieveData() throws SQLException, IOException, PropertyVetoException {

		ProvideAssetDataService prov = new ProvideAssetDataService();
		
		
		ArrayList<ArrayList<String>> resultArray = new ProvideAssetDataService(). new RetrieveAllTopLevelBOM().getArray();		
		
        topLevelBomData = Utils.arrayListToHashMap(resultArray);
    	
        resultArray = new ProvideAssetDataService(). new RetrieveAllBOM().getArray();
        allBomData = Utils.arrayListToHashMap(resultArray);
        
        resultArray = new ProvideAssetDataService(). new RetrieveAllPartInformation().getArray();
        partInfoData = Utils.arrayListToHashMap(resultArray);

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
        topLevelAssetsHierarchy.addContainerProperty("", HorizontalLayout.class, null);
        
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
            item.getItemProperty("").setValue(createControlPanel(part));

            
            if(!part.isLeaf()){
            	expandChildrenHierarchy(topLevelAssetsHierarchy, part);
            }
        }
        
        singleLevelAssetsHierarchy = new HierarchicalContainer();
        singleLevelAssetsHierarchy.addContainerProperty("Part Number", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("Name", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("Class", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("Description", String.class, null);
        singleLevelAssetsHierarchy.addContainerProperty("", HorizontalLayout.class, null);

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
            item.getItemProperty("").setValue(createControlPanel(part));
            
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
                childItem.getItemProperty("").setValue(createControlPanel(part));
                
                singleLevelAssetsHierarchy.setParent(childPart, part);
                singleLevelAssetsHierarchy.setChildrenAllowed(childPart, false);
                
			}
        }
        
        
        
		allAssetsHierarchy = new HierarchicalContainer();
		allAssetsHierarchy.addContainerProperty("Part Number", String.class, null);
		allAssetsHierarchy.addContainerProperty("Name", String.class, null);
		allAssetsHierarchy.addContainerProperty("Class", String.class, null);
		allAssetsHierarchy.addContainerProperty("Description", String.class, null);
		allAssetsHierarchy.addContainerProperty("", HorizontalLayout.class, null);
        
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
            item.getItemProperty("").setValue(createControlPanel(part));
            
            allAssetsHierarchy.setChildrenAllowed(part, false);
            
        }
         
       
	}
	
	private HorizontalLayout createControlPanel(final Part part) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.setImmediate(false);
		layout.setHeight("100%");
		
		
		Button viewInfoButton = new Button(FontAwesome.INFO);
		viewInfoButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				new AssetDetailsHandler(new AssetDetailsView(part), model).show();
			}
		});
		
		Button purchaseButton = new Button(FontAwesome.SHOPPING_CART);
		purchaseButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				new PurchaseAssetHandler(new PurchaseAssetView(), part).show();
			}
		});
	
		layout.addComponent(viewInfoButton);
		layout.addComponent(purchaseButton);
		return layout;
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

	@SuppressWarnings("unchecked")
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
				if (prop != null) {
					if ((boolean) prop.equals("Part Number")) {
						childItem.getItemProperty(prop).setValue(partNo);
					} else if ((boolean) prop.equals("Name")) {
						childItem.getItemProperty(prop).setValue(name);
					} else if ((boolean) prop.equals("Description")) {
						childItem.getItemProperty(prop).setValue(desc);
					} else if ((boolean) prop.equals("Class")) {
						childItem.getItemProperty(prop).setValue(assetClass);
					} else if ((boolean) prop.equals("")) {
						childItem.getItemProperty(prop).setValue(
								createControlPanel(childPart));
					} else {
						childItem.getItemProperty(prop).setValue(partNo + " (" + name + ")");
					}

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
	
	public boolean isSuccessful(){
		return success;
	}

	@Override
	public void doRun() {
		success = true;

		try {
			retrieveData();
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			success = false;
			e.printStackTrace();
		}

	
	
	}


	
	
}
