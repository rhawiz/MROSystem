package apps.mrosystem.domain;

import java.util.ArrayList;

public class Part implements Cloneable{
		
	private String partNo;
	private String name;
	private String desc;
	private String partClass;
	private ArrayList<Part> childrenParts;
	
	public Part(String partNo, String name, String desc, String partClass, ArrayList<Part> childrenParts) {
		super();
		this.partNo = partNo;
		this.name = name;
		this.desc = desc;
		this.partClass = partClass;
		this.childrenParts = childrenParts;
	}
	
	public Part(String partNo, String name, String desc, String partClass) {
		this(partNo, name,desc,partClass,new ArrayList<Part>());
	}
	
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return desc;
	}
	public void setDescription(String desc) {
		this.desc = desc;
	}
	public ArrayList<Part>getChildren() {
		return childrenParts;
	}
	public String getPartClass() {
		return partClass;
	}

	public void setPartClass(String partClass) {
		this.partClass = partClass;
	}
	
	public boolean isLeaf(){
		return childrenParts.size() == 0;
	}
	
	public void addChild(Part part){
		childrenParts.add(part);
	}
	
	
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	@Override
	public String toString(){
		String out = partNo + " (" + name + ")";
		
		return out;
		
	}
	
	public String getInfo(){
		String out =  "(" + partNo + "," + "name" + " ," + desc + ", [";
		
		for (int i = 0; i < childrenParts.size(); i++) {
			out += childrenParts.get(i) + " ,";
		}
		
		out += "])";
		
		return out;
		
	}



	
	
}
