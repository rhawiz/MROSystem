package apps.mrosystem.controller;

import java.util.HashMap;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;

import apps.mrosystem.domain.Attribute;
import apps.mrosystem.domain.Part;
import apps.mrosystem.model.AssetsModel;
import apps.mrosystem.view.AssetDetailsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.AssetsView;

public class AssetDetailsHandler {
	AssetDetailsView assetDetailsView;
	AssetsModel assetsModel;
	
	public AssetDetailsHandler(AssetDetailsView assetDetailsView, AssetsModel assetsModel) {
		this.assetDetailsView = assetDetailsView;
		this.assetsModel = assetsModel;
		assetDetailsView.setHandler(this);
		
	}
	
	public void show(){
		assetDetailsView.show();
	}

	public HashMap<String, Attribute> getAssetInfo(String partNo) {
		return assetsModel.getAssetInfo(partNo);
	}

	public HierarchicalContainer getAssetBomHierarchicalContainer(Part part) {
		// TODO Auto-generated method stub
		return assetsModel.getAssetBomHierarchicalContainer(part);
	}

	public AssetsModel getModel() {
		// TODO Auto-generated method stub
		return assetsModel;
	}
}
