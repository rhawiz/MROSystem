package apps.mrosystem.view;

import apps.mrosystem.controller.AssetsHandler;
import apps.mrosystem.controller.InventoryHandler;

import com.vaadin.navigator.View;

public interface InventoryView extends View{
	public static final String NAME = "INVENTORY";
	public void setHandler(InventoryHandler handler);
	public String getName();
}
