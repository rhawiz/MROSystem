package apps.mrosystem.controller;

import java.util.HashMap;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;

import apps.mrosystem.model.Assets;
import apps.mrosystem.model.Attribute;
import apps.mrosystem.model.Part;
import apps.mrosystem.view.AssetDetailsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.AssetsViewImpl;

public class AssetDetailsHandler {
	AssetDetailsView assetDetailsView;
	Assets assetsModel;
	
	public AssetDetailsHandler(AssetDetailsView assetDetailsView, Assets assetsModel) {
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

	public Assets getModel() {
		// TODO Auto-generated method stub
		return assetsModel;
	}
}
