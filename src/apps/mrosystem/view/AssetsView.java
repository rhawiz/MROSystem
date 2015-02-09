package apps.mrosystem.view;

import apps.mrosystem.controller.AssetsHandler;

import com.vaadin.navigator.View;

public interface AssetsView extends View{
	public static final String NAME = "ASSETS";
	public void setHandler(AssetsHandler handler);
	public String getName();
}
