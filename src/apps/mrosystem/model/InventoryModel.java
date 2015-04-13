package apps.mrosystem.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import apps.mrosystem.controller.AssetDetailsHandler;
import apps.mrosystem.domain.PhysicalPart;
import apps.mrosystem.services.InventoryDataServices;
import apps.mrosystem.threads.NotifyingThread;
import apps.mrosystem.view.AssetDetailsView;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InventoryModel extends NotifyingThread {

	private HierarchicalContainer allInventoryContainer;
    private HierarchicalContainer partNumberInventoryContainer;
    
    private InventoryDataServices dataService;

	private boolean success = false;
	private ArrayList<ArrayList<String>> allInventoryArrayList;

	public boolean isSuccessful(){
		return success ;
	}



	private void retrieveData() {
		dataService = new InventoryDataServices();
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
		allInventoryContainer.addContainerProperty("", CssLayout.class, "");

		
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
            item.getItemProperty("").setValue(createControlPanel(item));
            
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
		partNumberInventoryContainer.addContainerProperty("", CssLayout.class, "");
		
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
            item.getItemProperty("").setValue(createControlPanel(item));
            partNumberInventoryContainer.setChildrenAllowed(partStr, true);

            for(PhysicalPart physicalPart : partNumberInventoryMap.get(partStr)){
            	Item subItem = partNumberInventoryContainer.addItem(physicalPart);
            	subItem.getItemProperty("Serial Number").setValue(physicalPart.getSerialNo());
            	subItem.getItemProperty("Part Number").setValue(physicalPart.getPartNo());
            	subItem.getItemProperty("Name").setValue(physicalPart.getName());
            	subItem.getItemProperty("Date Purchased").setValue(physicalPart.getDatePurchased());
            	subItem.getItemProperty("Date Shipped").setValue(physicalPart.getDateShipped());
                subItem.getItemProperty("").setValue(createControlPanel(subItem));
                partNumberInventoryContainer.setParent(physicalPart, partStr);
                partNumberInventoryContainer.setChildrenAllowed(physicalPart, false);

            }
            
		}
		
		
		
	}
	
	private CssLayout createControlPanel(final Item item) {
		CssLayout layout = new CssLayout();
		Button assetInfoButton = new Button(FontAwesome.INFO);
		assetInfoButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				new AssetDetailsHandler(new AssetDetailsView(), new AssetDetailsModel((String) item.getItemProperty("Part Number").getValue())).show();
			}
		});
		
		assetInfoButton.addStyleName("table-control-button");
		assetInfoButton.addStyleName("quiet");
		assetInfoButton.setDescription("Display asset information");
		
		layout.addComponent(assetInfoButton);
		layout.addStyleName("table-control-layout");
		return layout;
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
