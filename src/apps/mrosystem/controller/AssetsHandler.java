package apps.mrosystem.controller;


import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;

import apps.mrosystem.MROSystemUI;
import apps.mrosystem.domain.Attribute;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;
import apps.mrosystem.model.Assets;
import apps.mrosystem.threads.ThreadCompleteListener;
import apps.mrosystem.utils.Utils;
import apps.mrosystem.view.AssetDetailsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.AssetsViewImpl;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;


public class AssetsHandler{
	AssetsViewImpl assetsView;
	Assets assetsModel;
	private String[] authorisedUsers = new String[]{"Admin","Technician","Planner","Management","Customer"};

	
	public AssetsHandler(){
		this(new AssetsViewImpl(), new Assets());
	}
	
	public AssetsHandler(AssetsView assetsView, Assets assetsModel) {
		this.assetsView = (AssetsViewImpl) assetsView;
		this.assetsModel = assetsModel;
		assetsView.setHandler(this);
		
	}
	

	public String getViewName(){
		return assetsView.getName();
	}
	
	public AssetsView getViewInstance(){
		return assetsView;
	}
	
	public void setAssetsTableAllAssets(){
		if(assetsModel.getAllAssetsHierarchicalContainer() != null){
			assetsView.setAssetsTableDataSource(assetsModel.getAllAssetsHierarchicalContainer());
		}
	}
	public void setAssetsTableTopLevel(){
		if(assetsModel.getTopLevelHierarchicalContainer() != null){
			assetsView.setAssetsTableDataSource(assetsModel.getTopLevelHierarchicalContainer());
		}

	}
	public void setAssetsTableSingleLevel(){
		if(assetsModel.getSingleLevelHierarchicalContainer() != null){
			assetsView.setAssetsTableDataSource(assetsModel.getSingleLevelHierarchicalContainer());
		}
	}

	private void initTableData() {
		assetsModel = new Assets();
		assetsView.setWaiting(true);
        assetsModel.addListener(new ThreadCompleteListener() {
			
			@Override
			public void notifyOfThreadComplete(Thread thread) {
				
				if(assetsModel.isSuccessful()){
					initView();
				}else{
					assetsView.showErrorNotification("Failed to retrieve data.");
				}
			}
		});
		assetsModel.start();
		

	}



	private void initView() {
		setAssetsClassFilter();
		setAssetsTableTopLevel();
		assetsView.setWaiting(false);
		UI.getCurrent().push();
		
	}

	public void getAssetInfoDialog(Part part) {
		assetsView.showAssetInformationWindow(new AssetDetailsHandler(new AssetDetailsView(part), assetsModel));
		
	}


	public void setAssetsClassFilter() {
		if(assetsModel.getAssetsClassFilter() != null){
			assetsView.setAssetsClassFilter(assetsModel.getAssetsClassFilter());
		}
		
	}

	private User getUser() {
		return (User) VaadinSession.getCurrent().getAttribute("userData");
	}

	public boolean isAuthorised() {
		if(getUser() != null){
			return getUser().isAuthorised(authorisedUsers);
		}
		return false;
	}
	
	public void init(){
		initTableData();
	}


}
