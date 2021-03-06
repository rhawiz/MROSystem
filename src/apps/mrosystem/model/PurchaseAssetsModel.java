package apps.mrosystem.model;

import java.util.ArrayList;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.VaadinSession;

import apps.mrosystem.database.DatabaseUtils;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;
import apps.mrosystem.domain.WarehouseLocation;
import apps.mrosystem.services.AssetDataServices;
import apps.mrosystem.threads.NotifyingThread;

public class PurchaseAssetsModel extends NotifyingThread {

	HierarchicalContainer partListContainer;
	HierarchicalContainer warehouseContainer;
	ArrayList<Part> partArrayList;
	User user;
	String partNo;
	Part part;


	public PurchaseAssetsModel(Part part) {
		this.part = part;
	}
	
	public PurchaseAssetsModel(String partNo) {
		this.partNo = partNo;
	}

	private void initWarehouseContainer() {
		warehouseContainer = new HierarchicalContainer();
		ArrayList<ArrayList<String>> whLocationsArrayList = new DatabaseUtils().getWarehouseLocations();
		for (ArrayList<String> row : whLocationsArrayList) {
			String id = row.get(0);
			String name = row.get(1);
			String country = row.get(2);
			String city = row.get(3);

			WarehouseLocation location = new WarehouseLocation(id, city, name,
					country);
			warehouseContainer.addItem(location);
		}
	}
	
	private void initPartListContainer(){
		partListContainer = new HierarchicalContainer();
		partArrayList = new ArrayList<Part>();
		expandPartListContainer(part);
	}

	private void expandPartListContainer(Part parent) {
		if(partListContainer == null){
			partListContainer = new HierarchicalContainer();
		}
		if(partArrayList == null){
			partArrayList = new ArrayList<Part>();
		}
			
		if (parent.isLeaf()) {
			partListContainer.addItem(parent);
			partArrayList.add(parent);

			
		} else {
			for (Part childPart : parent.getChildren()) {
				if (childPart.isLeaf()) {
					partListContainer.addItem(childPart);
					partArrayList.add(childPart);
				}else{
					expandPartListContainer(childPart);
				}
			}
		}

	}
	

	private void retrieveData() {
		if(part == null){
			this.part = new AssetDataServices(). new RetrievePartObject(partNo).getPart();
		}
		initWarehouseContainer();
		initPartListContainer();
	}

	public HierarchicalContainer getPartListHierarchicalContainer() {
		if(partListContainer == null)
			initPartListContainer();
		
		return partListContainer;
	}

	public HierarchicalContainer getWarehouseLocationHierarchicalContainer() {
		if (warehouseContainer == null)
			initWarehouseContainer();

		return warehouseContainer;

	}

	public User getUser() {
		// TODO Auto-generated method stub
		return (User) VaadinSession.getCurrent().getAttribute("userData");

	}
	
	

	@Override
	public void doRun() {
		retrieveData();
	}

	public ArrayList<Part> getPartsArrayList() {
		// TODO Auto-generated method stub
		return partArrayList;
	}

	public String getPartNo() {
		// TODO Auto-generated method stub
		return partNo;
	}

}
