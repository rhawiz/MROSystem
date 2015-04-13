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


}
