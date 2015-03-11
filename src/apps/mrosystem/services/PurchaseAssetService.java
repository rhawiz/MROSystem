package apps.mrosystem.services;

import java.util.ArrayList;
import java.util.Date;

import apps.mrosystem.database.DatabaseHelper;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.WarehouseLocation;
import apps.mrosystem.threads.NotifyingThread;
import apps.mrosystem.utils.Utils;

public class PurchaseAssetService extends NotifyingThread{

	private ArrayList<Part> assets;
	private boolean success;
	private WarehouseLocation location;
	private Date pDate;
	private Date sDate;
	
	public PurchaseAssetService(ArrayList<Part> assetsArrayList, WarehouseLocation location, Date purchaseDate, Date shipDate ) {
		this.assets = assetsArrayList;
		this.location = location;
		this.pDate = purchaseDate;
		this.sDate = shipDate;
	}
	
	private void submitPurchaseRequest(){
		DatabaseHelper dbHelper = new DatabaseHelper();
		
		ArrayList<String> partIds = new ArrayList<String>();
				
		for(Part part : assets){
			partIds.add(part.getPartNo());
		}
		
		String locationId = location.getId();
		
		String pDateTime = Utils.getMySqlDateTime(pDate);
		String sDateTime = Utils.getMySqlDateTime(sDate);
		
		success = dbHelper.submitPurchaseRequest(partIds, locationId, pDateTime, sDateTime);
	}

	@Override
	public void doRun() {
		submitPurchaseRequest();
	}

	public boolean isSuccessful() {
		// TODO Auto-generated method stub
		return success;
	}
	
}
