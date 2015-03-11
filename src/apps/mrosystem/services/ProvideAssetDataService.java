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


}
