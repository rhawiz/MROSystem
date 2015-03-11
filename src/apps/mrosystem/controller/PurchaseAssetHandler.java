package apps.mrosystem.controller;

import java.util.ArrayList;
import java.util.Date;

import apps.mrosystem.dataprovider.PurchaseAssetDataProvider;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;
import apps.mrosystem.domain.WarehouseLocation;
import apps.mrosystem.services.PurchaseAssetService;
import apps.mrosystem.threads.ThreadCompleteListener;
import apps.mrosystem.view.PurchaseAssetView;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class PurchaseAssetHandler extends Window{

	Part part;
	PurchaseAssetView view;
	private HierarchicalContainer assetsHierarchicalContainer;
	private PurchaseAssetService service;
	private PurchaseAssetDataProvider provider;
	private Container warehouseLocationsContainer;
	
	public PurchaseAssetHandler(PurchaseAssetView purchaseAssetView, Part part) {
		this.view = new PurchaseAssetView();
		this.part = part;
		this.provider = new PurchaseAssetDataProvider(part);
		view.setHandler(this);
	}


	private void initData() {
		// TODO Auto-generated method stub
		
		provider.addListener(new ThreadCompleteListener() {
			
			@Override
			public void notifyOfThreadComplete(Thread thread) {
				view.setWindowCaption("Purchase Request for item " +part.getPartNo());
				view.setBomContainer(provider.getPartListHierarchicalContainer());
				view.setWarehouseLocations(provider.getWarehouseLocationHierarchicalContainer());
				view.setRequester(provider.getUser().getFirstname() + " " + provider.getUser().getSurname());
				UI.getCurrent().push();

			}
		});
		
		provider.start();

	}

	public void show() {
		view.buildLayout();
		view.show();
		
		initData();
	}

	public String getPartNo() {
		// TODO Auto-generated method stub
		return part.getPartNo();
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
			service = new PurchaseAssetService(provider.getPartsArrayList(), location, shippedDate, purchasedDate);
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
