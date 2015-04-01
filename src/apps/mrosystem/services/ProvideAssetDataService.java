package apps.mrosystem.services;

import java.util.ArrayList;

import apps.mrosystem.database.DatabaseHelper;
import apps.mrosystem.domain.Part;
import apps.mrosystem.threads.NotifyingThread;

public class ProvideAssetDataService{

	
	public class RetrieveAllTopLevelBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> allTopLevelBOM;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allTopLevelBOM == null){
				return new DatabaseHelper().getAllTopLevelBOM();

			}else{
				return allTopLevelBOM;
			}
		}
		
		@Override
		public void doRun() {
			allTopLevelBOM = new DatabaseHelper().getAllTopLevelBOM();
			
		}
	}

	public class RetrieveAllBOM extends NotifyingThread {
		ArrayList<ArrayList<String>> allBOM;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allBOM == null){
				return new DatabaseHelper().getAllBOM();

			}else{
				return allBOM;
			}
		}
		
		@Override
		public void doRun() {
			allBOM = new DatabaseHelper().getAllBOM();
			
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
				return new DatabaseHelper().getAssetBOM(partNo);
				
			}else{
				return bom;
			}
		}
		
		@Override
		public void doRun() {
			bom = new DatabaseHelper().getAssetBOM(partNo);
			
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
				return new DatabaseHelper().getBOMAndInfo(partNo);

			}else{
				return bom;
			}
		}
		
		@Override
		public void doRun() {
			bom = new DatabaseHelper().getBOMAndInfo(partNo);
			
		}
	}
	
	public class RetrieveAllPartInformation extends NotifyingThread {
		ArrayList<ArrayList<String>> allPartInformation;
		
		public ArrayList<ArrayList<String>> getArray(){
			if(allPartInformation == null){
				return new DatabaseHelper().getAllPartInfo();

			}else{
				return allPartInformation;
			}
		}
		
		@Override
		public void doRun() {
			allPartInformation = new DatabaseHelper().getAllPartInfo();
			
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
				return new DatabaseHelper().getAssetInfo(part);
			}else{
				return partInformation;
			}
		}
		
		@Override
		public void doRun() {
			partInformation = new DatabaseHelper().getAssetInfo(part);
			
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
				return new DatabaseHelper().getBasicAssetInfo(part);
			}else{
				return partInformation;
			}
		}
		
		@Override
		public void doRun() {
			partInformation = new DatabaseHelper().getBasicAssetInfo(part);
			
		}
	}


}
