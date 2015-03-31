package apps.mrosystem;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.json.JSONException;
import org.json.JSONObject;

import apps.mrosystem.controller.ActiveProductsHandler;
import apps.mrosystem.controller.AssetsHandler;
import apps.mrosystem.controller.CustomerProductsHandler;
import apps.mrosystem.controller.InventoryHandler;
import apps.mrosystem.controller.LoginHandler;
import apps.mrosystem.controller.CustomerHandler;
import apps.mrosystem.domain.User;
import apps.mrosystem.model.ActiveProductsModel;
import apps.mrosystem.model.AssetsModel;
import apps.mrosystem.model.CustomerProductsModel;
import apps.mrosystem.model.InventoryModel;
import apps.mrosystem.model.CustomerModel;
import apps.mrosystem.view.ActiveProductsView;
import apps.mrosystem.view.AdminView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.CalendarView;
import apps.mrosystem.view.CustomerProductsView;
import apps.mrosystem.view.CustomerView;
import apps.mrosystem.view.InventoryView;
import apps.mrosystem.view.InventoryView;
import apps.mrosystem.view.LoginView;
import apps.mrosystem.view.MainView;
import apps.mrosystem.view.PlanningSchedulingView;
import apps.mrosystem.view.ReportsView;
import apps.mrosystem.view.ServiceRequestView;
import apps.mrosystem.view.UsersView;
import apps.mrosystem.view.WorkOrdersView;
import apps.mrosystem.view.WorkforceView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.ClientMethodInvocation;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Extension;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mrosystem")
@Push
public class MROSystemUI extends UI {

	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MROSystemUI.class)
	@WebInitParam(name = "widgetset", value = "apps.mrosystem.widgetset.MrosystemWidgetset.gwt")
	public static class Servlet extends VaadinServlet {
	}

	Navigator navigator;
    protected static final String MAINVIEW = "main";
    
	@Override
	protected void init(VaadinRequest request) {
        		
		
        getPage().setTitle("Maintainence Repair & Overhaul System");
        
        
        Navigator navigator = new Navigator(this, this);
        
        
        //Login page
        LoginView loginView = new LoginView();
        final LoginHandler loginHandler= new LoginHandler(loginView);
        navigator.addView(loginHandler.getViewName(), loginView);

        
        //Main page
        getNavigator().addView(MainView.NAME, MainView.class);
        
        
        //Inventory page
        InventoryModel inventoryModel = new InventoryModel();
        InventoryView inventoryView = new InventoryView();
        InventoryHandler inventoryHandler = new InventoryHandler(inventoryView, inventoryModel);
        getNavigator().addView(inventoryHandler.getViewName(), inventoryHandler.getViewInstance());
        
        
        //Assets page
        AssetsModel assetsModel = new AssetsModel();
        AssetsView assetsView = new AssetsView();
        AssetsHandler assetsHandler = new AssetsHandler(assetsView, assetsModel);
        navigator.addView(assetsHandler.getViewName(), assetsHandler.getViewInstance());
        
        getNavigator().addView(WorkOrdersView.NAME, WorkOrdersView.class);
        
        getNavigator().addView(PlanningSchedulingView.NAME, PlanningSchedulingView.class);
        
        getNavigator().addView(WorkforceView.NAME, WorkforceView.class);
        
        getNavigator().addView(AdminView.NAME, AdminView.class);
        
        getNavigator().addView(CalendarView.NAME, CalendarView.class);
        
        getNavigator().addView(ServiceRequestView.NAME, ServiceRequestView.class);
        
        getNavigator().addView(ReportsView.NAME, ReportsView.class);
        
        getNavigator().addView(UsersView.NAME, UsersView.class);
        
        //Customer page
        CustomerModel customerModel = new CustomerModel();
        CustomerView customerView = new CustomerView();
        CustomerHandler customerHandler = new CustomerHandler(customerView, customerModel);
        getNavigator().addView(customerHandler.getViewName(), customerHandler.getViewInstance());
        

        //Active Products page
        ActiveProductsModel activeProducts = new ActiveProductsModel();
        ActiveProductsView activeProductsView = new ActiveProductsView();
        ActiveProductsHandler activeProductsHandler = new ActiveProductsHandler(activeProductsView, activeProducts);
        getNavigator().addView(activeProductsHandler.getViewName(), activeProductsHandler.getViewInstance());
        
        //Customer Products page
        CustomerProductsModel customerProducts = new CustomerProductsModel((User) VaadinSession.getCurrent().getAttribute("userData"));
        CustomerProductsView customerProductsView = new CustomerProductsView();
        CustomerProductsHandler customerProductsHandler = new CustomerProductsHandler(customerProductsView, customerProducts);
        getNavigator().addView(customerProductsHandler.getViewName(), customerProductsHandler.getViewInstance());
        
        
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                boolean loggedIn = getSession().getAttribute("userData") != null;
                boolean loginView = event.getNewView() instanceof LoginView;

                if (!loggedIn && !loginView) {

                    getNavigator().navigateTo(loginHandler.getViewName());
                    return false;

                } else if (loggedIn && loginView) {
                	
                    return false;
                }
                
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
            	
            }
        });
    }
	
	public static void unlockUI() {
		UI.getCurrent().getSession().getLockInstance().unlock();

		
	}

	public static void lockUI() {
		UI.getCurrent().getSession().getLockInstance().lock();

		
	}
	
        
}



