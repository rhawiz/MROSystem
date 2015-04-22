package apps.mrosystem.services;

import java.util.ArrayList;

import apps.mrosystem.database.DatabaseUtils;
import apps.mrosystem.domain.Part;
import apps.mrosystem.threads.NotifyingThread;

public class AssetDataServices{

	
	public class RetrieveAllTopLevelBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> allTopLevelBOM;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allTopLevelBOM == null){
				return new DatabaseUtils().getAllTopLevelBOM();

			}else{
				return allTopLevelBOM;
			}
		}
		
		@Override
		public void doRun() {
			allTopLevelBOM = new DatabaseUtils().getAllTopLevelBOM();
			
		}
	}

	public class RetrieveAllBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> allBOM;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allBOM == null){
				return new DatabaseUtils().getAllBOM();

			}else{
				return allBOM;
			}
		}
		
		@Override
		public void doRun() {
			allBOM = new DatabaseUtils().getAllBOM();
			
		}
	}

	
	public class RetrieveBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> bom;
		private String partNo;
		
		public RetrieveBOM(String partNo){
			this.partNo = partNo;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(bom == null){
				return new DatabaseUtils().getAssetBOM(partNo);
				
			}else{
				return bom;
			}
		}
		
		@Override
		public void doRun() {
			bom = new DatabaseUtils().getAssetBOM(partNo);
			
		}
	}
	
	public class RetrieveBOMAndInfo extends NotifyingThread {
		ArrayList<ArrayList<String>> bom;
		private String partNo;
		
		public RetrieveBOMAndInfo(String partNo){
			this.partNo = partNo;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(bom == null){
				return new DatabaseUtils().getBOMAndInfo(partNo);

			}else{
				return bom;
			}
		}
		
		@Override
		public void doRun() {
			bom = new DatabaseUtils().getBOMAndInfo(partNo);
			
		}
	}
	
	public class RetrieveAllPartInformation extends NotifyingThread {
		ArrayList<ArrayList<String>> allPartInformation;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allPartInformation == null){
				return new DatabaseUtils().getAllPartInfo();

			}else{
				return allPartInformation;
			}
		}
		
		@Override
		public void doRun() {
			allPartInformation = new DatabaseUtils().getAllPartInfo();
			
		}
	}
	

	public class RetrievePartInformation extends NotifyingThread {
		ArrayList<ArrayList<String>> partInformation;
		String part;
		public RetrievePartInformation(String part) {
			this.part = part;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(partInformation == null){
				return new DatabaseUtils().getAssetInfo(part);
			}else{
				return partInformation;
			}
		}
		
		@Override
		public void doRun() {
			partInformation = new DatabaseUtils().getAssetInfo(part);
			
		}
	}
	
	public class RetrieveBasicPartInformation extends NotifyingThread {
		ArrayList<ArrayList<String>> partInformation;
		String part;
		public RetrieveBasicPartInformation(String part) {
			this.part = part;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(partInformation == null){
				return new DatabaseUtils().getBasicAssetInfo(part);
			}else{
				return partInformation;
			}
		}
		
		@Override
		public void doRun() {
			partInformation = new DatabaseUtils().getBasicAssetInfo(part);
			
		}
	}
	
	
	public class RetrieveAllActiveAssets extends NotifyingThread {
		ArrayList<ArrayList<String>> allActiveProducts;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allActiveProducts == null){
				return new DatabaseUtils().getAllActiveAssets();

			}else{
				return allActiveProducts;
			}
		}
		
		@Override
		public void doRun() {
			allActiveProducts = new DatabaseUtils().getAllActiveAssets();
			
		}
	}
	
	public class RetrieveAllCustomerAssets extends NotifyingThread {
		ArrayList<ArrayList<String>> allCustomerProducts;
		
		private String userId;
		
		public RetrieveAllCustomerAssets(String userId){
			this.userId = userId;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allCustomerProducts == null){
				return new DatabaseUtils().getAllCustomerAssets(userId);
				
			}else{
				return allCustomerProducts;
			}
		}
		
		@Override
		public void doRun() {
			allCustomerProducts = new DatabaseUtils().getAllCustomerAssets(userId);
			
		}
	}
	
	
	public class RetrieveActiveAssetsUniquePartNumbers extends NotifyingThread {
		ArrayList<ArrayList<String>> allCustomerProducts;
		
		private String userId;
		
		public RetrieveActiveAssetsUniquePartNumbers(String userId){
			this.userId = userId;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allCustomerProducts == null){
				return new DatabaseUtils().getUniquePartNumbers(userId);
				
			}else{
				return allCustomerProducts;
			}
		}
		
		@Override
		public void doRun() {
			allCustomerProducts = new DatabaseUtils().getUniquePartNumbers(userId);
			
		}
	}
	
	public class RetrieveActiveAssetsBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> bom;
		
		private String serialNo;
		
		public RetrieveActiveAssetsBOM(String serialNo){
			this.serialNo= serialNo;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(bom == null){
				return new DatabaseUtils().getActiveAssetBOM(serialNo);
				
			}else{
				return bom;
			}
		}
		
		@Override
		public void doRun() {
			bom = new DatabaseUtils().getActiveAssetBOM(serialNo);
			
		}
	}

	public class RetrieveAllActiveAssetsTopLevelBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> bom;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(bom == null){
				return new DatabaseUtils().getAllActiveAssetTopLevelBOM();

			}else{
				return bom;
			}
		}
		
		@Override
		public void doRun() {
			bom = new DatabaseUtils().getAllActiveAssetTopLevelBOM();
			
		}
	}
	
	public class RetrieveWorkOrderParts extends NotifyingThread {
		ArrayList<ArrayList<String>> parts;
		
		private String workOrderId;
		
		public RetrieveWorkOrderParts(String workOrderId){
			this.workOrderId= workOrderId;
		}
		
		public ArrayList<ArrayList<String>> getArray(){
			if(parts == null){
				return new DatabaseUtils().getWorkOrderParts(workOrderId);
				
			}else{
				return parts;
			}
		}
		
		@Override
		public void doRun() {
			parts = new DatabaseUtils().getWorkOrderParts(workOrderId);
			
		}
	}
	
	public class RetrievePartObject extends NotifyingThread {
		Part part;
		
		private String partNo;
		
		public RetrievePartObject(String partNo){
			this.partNo= partNo;
		}
		
		public Part getPart(){
			if(part == null){
				initPart();
			}
			return part;
		}
		
		private void initPart(){
			ArrayList<String> partInfo = DatabaseUtils.getBasicAssetInfo(partNo).get(0);
			ArrayList<ArrayList<String>> partBOMAndInfo = DatabaseUtils.getBOMAndInfo(partNo);
			
			String partNo = partInfo.get(0);
			String name = partInfo.get(1);
			String desc = partInfo.get(2);
			String partClass = partInfo.get(3);
			
			part = new Part(partNo,name,desc,partClass);
			
			
			if(partBOMAndInfo.size() !=0){
				expandPart(part, partBOMAndInfo);
			}
			
			
			
		}
		
		private void expandPart(Part part, ArrayList<ArrayList<String>> partBOM) {
			for(ArrayList<String> childPartInfo : partBOM){
				
				String partNo = childPartInfo.get(0);
				String name = childPartInfo.get(1);
				String desc = childPartInfo.get(2);
				String partClass = childPartInfo.get(3);
				
				ArrayList<ArrayList<String>> partBOMAndInfo = DatabaseUtils.getBOMAndInfo(partNo);
				
				
				Part childPart = new Part(partNo,name,desc,partClass);
				
				
				if(partBOMAndInfo.size() !=0){
					expandPart(childPart, partBOMAndInfo);
				}
				
				part.addChild(childPart);
				
			}
			
		}
		
		@Override
		public void doRun() {
			initPart();
			
		}
	}
	
	public class RetrievePartObjectBySerialNumber extends NotifyingThread {
		Part part;
		private String serialNo;
		private String partNo;
		
		public RetrievePartObjectBySerialNumber(String serialNo){
			this.serialNo= serialNo;
		}
		
		public Part getPart(){
			if(partNo == null){
				partNo = DatabaseUtils.getPartNoFromSerialNo(serialNo);
			}
			
			if(part == null){
				part = new RetrievePartObject(partNo).getPart();
			}
			
			return part;
		}
		

		@Override
		public void doRun() {
			getPart();
			
		}
	}
	
	

}
