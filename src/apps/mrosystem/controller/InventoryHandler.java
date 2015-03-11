package apps.mrosystem.controller;

import java.sql.SQLException;

import apps.mrosystem.model.Inventory;
import apps.mrosystem.view.InventoryView;
import apps.mrosystem.view.InventoryViewImpl;



public class InventoryHandler{
	InventoryViewImpl inventoryView;
	Inventory inventoryModel;
	
	public InventoryHandler(){
		this((InventoryView) new InventoryViewImpl(), new Inventory());
	}
	
	public InventoryHandler(InventoryView inventoryView, Inventory inventoryModel) {
		this.inventoryView = (InventoryViewImpl) inventoryView;
		this.inventoryModel = inventoryModel;
		inventoryView.setHandler(this);
		
	}
	

	public String getViewName(){
		return inventoryView.getName();
	}
	
	public InventoryView getViewInstance(){
		return (InventoryView) inventoryView;
	}
	



	public void initTableData() throws SQLException {
			inventoryModel.retrieveData();

	}


}
