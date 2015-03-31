package apps.mrosystem.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import apps.mrosystem.domain.PhysicalPart;
import apps.mrosystem.services.ProvideInventoryDataService;
import apps.mrosystem.threads.NotifyingThread;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;

public class InventoryModel extends NotifyingThread {

	private HierarchicalContainer allInventoryContainer;
    private HierarchicalContainer partNumberInventoryContainer;
    
    private ProvideInventoryDataService dataService;

	private boolean success = false;
	private ArrayList<ArrayList<String>> allInventoryArrayList;

	public boolean isSuccessful(){
		return success ;
	}



	private void retrieveData() {
		dataService = new ProvideInventoryDataService();
		allInventoryArrayList  = dataService. new RetrieveAllInventory().getArray();

		if(allInventoryArrayList == null){
			success = false;
			return;
		}
		
		if(!allInventoryArrayList.isEmpty() && allInventoryArrayList != null){
			initAllInventoryContainer();
			initPartNumberInventoryContainer();
		}
		
	}
	
	
	
	private void initAllInventoryContainer() {
		
		
		allInventoryContainer = new HierarchicalContainer();
		
		allInventoryContainer.addContainerProperty("Part Number", String.class, "");
		allInventoryContainer.addContainerProperty("Serial Number", String.class, null);
		allInventoryContainer.addContainerProperty("Name", String.class, "");
		allInventoryContainer.addContainerProperty("Date Purchased", Date.class, "");
		allInventoryContainer.addContainerProperty("Date Shipped", Date.class, "");
		allInventoryContainer.addContainerProperty("Control", String.class, "");

		
		for (ArrayList<String> currentRow : allInventoryArrayList) {
			
			String serialNo = currentRow.get(0);
			String partNo = currentRow.get(1);
			Date datePurchased = new Date(Long.parseLong(currentRow.get(2)) * 1000);
			Date dateShipped = new Date(Long.parseLong(currentRow.get(3)) * 1000);
			String name = currentRow.get(4);
						
			PhysicalPart inventory = new PhysicalPart(serialNo,partNo,name,datePurchased,dateShipped);
        	Item item = allInventoryContainer.addItem(inventory);
            item.getItemProperty("Part Number").setValue(partNo);
            item.getItemProperty("Serial Number").setValue(serialNo);
            item.getItemProperty("Name").setValue(name);
            item.getItemProperty("Date Purchased").setValue(datePurchased);
            item.getItemProperty("Date Shipped").setValue(dateShipped);
            item.getItemProperty("Control").setValue("");
            
            allInventoryContainer.setChildrenAllowed(inventory, false);

		}
		

	}
	
	
	private void initPartNumberInventoryContainer(){
		partNumberInventoryContainer = new HierarchicalContainer();
		partNumberInventoryContainer.addContainerProperty("Part Number", String.class, "");
		partNumberInventoryContainer.addContainerProperty("Serial Number", String.class, null);
		partNumberInventoryContainer.addContainerProperty("Name", String.class, "");
		partNumberInventoryContainer.addContainerProperty("Date Purchased", Date.class, "");
		partNumberInventoryContainer.addContainerProperty("Date Shipped", Date.class, "");
		partNumberInventoryContainer.addContainerProperty("Control", String.class, "");
		
		HashMap<String,ArrayList<PhysicalPart>> partNumberInventoryMap = new HashMap<String,ArrayList<PhysicalPart>>();
		
		for (ArrayList<String> currentRow : allInventoryArrayList) {
			
			String serialNo = currentRow.get(0);
			String partNo = currentRow.get(1);
			Date datePurchased = new Date(Long.parseLong(currentRow.get(2)) * 1000);
			Date dateShipped = new Date(Long.parseLong(currentRow.get(3)) * 1000);
			String name = currentRow.get(4);
			
			PhysicalPart inventory = new PhysicalPart(serialNo,partNo,name,datePurchased,dateShipped);
			
			if(partNumberInventoryMap.get(partNo) == null){
				partNumberInventoryMap.put(partNo, new ArrayList<PhysicalPart>());
			}
			
			partNumberInventoryMap.get(partNo).add(inventory);


		}
		
		for(String partStr : partNumberInventoryMap.keySet()){
        	Item item = partNumberInventoryContainer.addItem(partStr);
            item.getItemProperty("Serial Number").setValue("-");
            item.getItemProperty("Part Number").setValue(partStr);
            item.getItemProperty("Name").setValue("-");
            item.getItemProperty("Date Purchased").setValue(null);
            item.getItemProperty("Date Shipped").setValue(null);
            item.getItemProperty("Control").setValue("");
            partNumberInventoryContainer.setChildrenAllowed(partStr, true);

            for(PhysicalPart physicalPart : partNumberInventoryMap.get(partStr)){
            	Item subItem = partNumberInventoryContainer.addItem(physicalPart);
            	subItem.getItemProperty("Serial Number").setValue(physicalPart.getSerialNo());
            	subItem.getItemProperty("Part Number").setValue(physicalPart.getPartNo());
            	subItem.getItemProperty("Name").setValue(physicalPart.getName());
            	subItem.getItemProperty("Date Purchased").setValue(physicalPart.getDatePurchased());
            	subItem.getItemProperty("Date Shipped").setValue(physicalPart.getDateShipped());
                subItem.getItemProperty("Control").setValue("");
                partNumberInventoryContainer.setParent(physicalPart, partStr);
                partNumberInventoryContainer.setChildrenAllowed(physicalPart, false);

            }
            
		}
		
		
		
	}
	

	public HierarchicalContainer getPartNumberInventoryContainer(){
		return partNumberInventoryContainer;
	}


	
	public HierarchicalContainer getAllInventoryContainer(){
		return allInventoryContainer;
	}



	@Override
	public void doRun() {
		success = true;
		
		retrieveData();

	
	
	}

}
