package apps.mrosystem.controller;

import java.util.ArrayList;
import java.util.Date;

import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;
import apps.mrosystem.domain.WarehouseLocation;
import apps.mrosystem.model.PurchaseAssetsModel;
import apps.mrosystem.services.PurchaseAssetService;
import apps.mrosystem.threads.ThreadCompleteListener;
import apps.mrosystem.view.PurchaseAssetView;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class PurchaseAssetHandler extends Window{

	PurchaseAssetView view;
	private HierarchicalContainer assetsHierarchicalContainer;
	private PurchaseAssetService service;
	private PurchaseAssetsModel model;
	private Container warehouseLocationsContainer;
	
	public PurchaseAssetHandler(PurchaseAssetView purchaseAssetView, PurchaseAssetsModel model) {
		this.view = purchaseAssetView;
		this.model = model;
		view.setHandler(this);
	}


	private void initData() {
		// TODO Auto-generated method stub
		
		model.addListener(new ThreadCompleteListener() {
			
			@Override
			public void notifyOfThreadComplete(Thread thread) {
				view.setWindowCaption("Purchase Request for item " +model.getPartNo());
				view.setBomContainer(model.getPartListHierarchicalContainer());
				view.setWarehouseLocations(model.getWarehouseLocationHierarchicalContainer());
				view.setRequester(model.getUser().getFirstname() + " " + model.getUser().getSurname());
				UI.getCurrent().push();

			}
		});
		
		model.start();

	}

	public void show() {
		view.buildLayout();
		view.show();
		
		initData();
	}

	public String getPartNo() {
		// TODO Auto-generated method stub
		return model.getPartNo();
	}

	
	public User getUser(){
		return (User) VaadinSession.getCurrent().getAttribute("userData");
	}
	
	
	
	public HierarchicalContainer getAssetBomHierarchicalContainer() {
		return assetsHierarchicalContainer;
	}

	public void submitPurchaseRequest() {		
		

		if(isValid()){
			WarehouseLocation location = view.getSelectedWarehouse();
			Date shippedDate = new Date();
			Date purchasedDate = new Date();
			service = new PurchaseAssetService(model.getPartsArrayList(), location, shippedDate, purchasedDate);
			view.setWaiting();
			service.addListener(new ThreadCompleteListener() {
				@Override
				public void notifyOfThreadComplete(Thread thread) {
					if(service.isSuccessful()){
						view.updateSucess();
					}else{
						view.updateFailed();
					}
					UI.getCurrent().push();
					
				}
			});

			service.start();
		}else{
			view.showShipToError();
		}
		
		
	}


	private boolean isValid() {
		if(view.getSelectedWarehouse() == null){
			return false;
		}
		
		if(view.getShipDate() == null){
			return false;
		}
		return true;
	}

	
}
