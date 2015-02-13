package apps.mrosystem.controller;


import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;

import apps.mrosystem.model.Assets;
import apps.mrosystem.model.Attribute;
import apps.mrosystem.model.Part;
import apps.mrosystem.utils.Utils;
import apps.mrosystem.view.AssetDetailsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.AssetsViewImpl;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
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
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;


public class AssetsHandler{
	AssetsViewImpl assetsView;
	Assets assetsModel;
	private ArrayList<TextField> infoTextFieldArray;
	
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
		assetsView.setAssetsTableDataSource(assetsModel.getAllAssetsHierarchicalContainer());
	}
	public void setAssetsTableTopLevel(){
		assetsView.setAssetsTableDataSource(assetsModel.getTopLevelHierarchicalContainer());

	}
	public void setAssetsTableSingleLevel(){
		assetsView.setAssetsTableDataSource(assetsModel.getSingleLevelHierarchicalContainer());

	}

	public void initTableData() {
		try {
			assetsModel.retrieveData();

		} catch (SQLException | IOException | PropertyVetoException e) {
			// TODO Auto-generated catch block
			assetsView.showWarning("Could not retrieve data.", "Click to close");
			e.printStackTrace();
		}
		
	}



	public void getAssetInfoDialog(Part part) {
		assetsView.showAssetInformationWindow(new AssetDetailsHandler(new AssetDetailsView(part), assetsModel));
		
	}


	public void setAssetsClassFilter() {
		assetsView.setAssetsClassFilter(assetsModel.getAssetsClassFilter());
		
	}


}
