package apps.mrosystem.controller;

import java.util.HashMap;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.UI;

import apps.mrosystem.domain.Attribute;
import apps.mrosystem.domain.Part;
import apps.mrosystem.model.AssetDetailsModel;
import apps.mrosystem.model.AssetsModel;
import apps.mrosystem.threads.ThreadCompleteListener;
import apps.mrosystem.view.AssetDetailsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.AssetsView;

public class AssetDetailsHandler {
	AssetDetailsView assetDetailsView;
	AssetDetailsModel assetsModel;
	
	public AssetDetailsHandler(AssetDetailsView assetDetailsView, AssetDetailsModel assetsModel) {
		this.assetDetailsView = assetDetailsView;
		this.assetsModel = assetsModel;
		assetDetailsView.setHandler(this);
		
	}
	
	public void show(){
		
		assetsModel.addListener(new ThreadCompleteListener() {
			
			@Override
			public void notifyOfThreadComplete(Thread thread) {
				assetDetailsView.setAssetDetails(assetsModel.getAssetInfo());
				assetDetailsView.setPart(assetsModel.getPart());
				assetDetailsView.show();
				UI.getCurrent().push();

				
			}
		});
		
		assetsModel.start();
		
	}

	public HashMap<String, Attribute> getAssetInfo(String partNo) {
		return assetsModel.getAssetInfo();
	}

	public HierarchicalContainer getAssetBomHierarchicalContainer(Part part) {
		// TODO Auto-generated method stub
		return assetsModel.getAssetBomHierarchicalContainer(part);
	}

}
