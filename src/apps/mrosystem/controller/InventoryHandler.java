package apps.mrosystem.controller;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.UI;

import apps.mrosystem.model.InventoryModel;
import apps.mrosystem.threads.ThreadCompleteListener;
import apps.mrosystem.view.InventoryView;
import apps.mrosystem.view.InventoryView;



public class InventoryHandler{
	InventoryView inventoryView;
	InventoryModel inventoryModel;
	
	public InventoryHandler(){
		this(new InventoryView(), new InventoryModel());
	}
	
	public InventoryHandler(InventoryView inventoryView, InventoryModel inventoryModel) {
		this.inventoryView = (InventoryView) inventoryView;
		this.inventoryModel = inventoryModel;
		inventoryView.setHandler(this);
		
	}
	

	public String getViewName(){
		return inventoryView.getName();
	}
	
	public InventoryView getViewInstance(){
		return (InventoryView) inventoryView;
	}


	public void initTableData(){
			inventoryModel = new InventoryModel();
			inventoryModel.addListener(new ThreadCompleteListener() {
				
				@Override
				public void notifyOfThreadComplete(Thread thread) {
					initView();
					
				}
			});
			
			inventoryModel.start();

	}
	
	private void initView() {
		inventoryView.setInventoryTableDataSource(inventoryModel.getAllInventoryContainer());
		inventoryView.setWaiting(false);
		UI.getCurrent().push();
		
	}

	
	public void init(){
		initTableData();
	}

	public ValueChangeListener getTableViewModeItemChangeListener() {
		// TODO Auto-generated method stub
		return new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue().equals("All")){
					inventoryView.clearFilterText();
					inventoryView.setInventoryTableDataSource(inventoryModel.getAllInventoryContainer());
				}
				else if(event.getProperty().getValue().equals("Part Number")){
					inventoryView.clearFilterText();
					inventoryView.setInventoryTableDataSource(inventoryModel.getPartNumberInventoryContainer());
					
				}
				
			}
		};
	}


}



