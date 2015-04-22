package apps.mrosystem;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import apps.mrosystem.controller.ActiveAssetsHandler;
import apps.mrosystem.controller.AssetsHandler;
import apps.mrosystem.controller.CalendarHandler;
import apps.mrosystem.controller.CustomerAssetsHandler;
import apps.mrosystem.controller.CustomerHandler;
import apps.mrosystem.controller.InventoryHandler;
import apps.mrosystem.controller.LoginHandler;
import apps.mrosystem.controller.WorkOrdersHandler;
import apps.mrosystem.controller.WorkforceHandler;
import apps.mrosystem.controller.PlanningSchedulingHandler;
import apps.mrosystem.database.ObjectDatabaseSerialization;
import apps.mrosystem.database.datasource.Datasource;
import apps.mrosystem.domain.User;
import apps.mrosystem.model.ActiveAssetsModel;
import apps.mrosystem.model.AssetsModel;
import apps.mrosystem.model.CalendarModel;
import apps.mrosystem.model.CustomerAssetsModel;
import apps.mrosystem.model.CustomerModel;
import apps.mrosystem.model.InventoryModel;
import apps.mrosystem.model.WorkOrdersModel;
import apps.mrosystem.model.WorkforceModel;
import apps.mrosystem.model.PlanningSchedulingModel;
import apps.mrosystem.systemtraining.NeuralNetwork;
import apps.mrosystem.view.ActiveAssetsView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.CalendarView;
import apps.mrosystem.view.CustomerAssetsView;
import apps.mrosystem.view.CustomerView;
import apps.mrosystem.view.InventoryView;
import apps.mrosystem.view.LoginView;
import apps.mrosystem.view.MainView;
import apps.mrosystem.view.PlanningSchedulingView;
import apps.mrosystem.view.ReportsView;
import apps.mrosystem.view.ServiceRequestView;
import apps.mrosystem.view.WorkOrdersView;
import apps.mrosystem.view.WorkforceView;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mrosystem")
@Push
public class MROSystemUI extends UI {

	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MROSystemUI.class, widgetset="apps.mrosystem.widgetset.MrosystemWidgetset")
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
        getNavigator().addView(assetsHandler.getViewName(), assetsHandler.getViewInstance());
        
        //Work Orders page
        WorkOrdersModel workOrdersModel = new WorkOrdersModel();
        WorkOrdersView workOrdersView = new WorkOrdersView();
        WorkOrdersHandler workOrdersHandler = new WorkOrdersHandler(workOrdersView,workOrdersModel);
        getNavigator().addView(workOrdersHandler.getViewName(), workOrdersHandler.getViewInstance());
        
        //Planning Scheduling page
        PlanningSchedulingHandler planningSchedulingHandler = new PlanningSchedulingHandler(new PlanningSchedulingView(),new PlanningSchedulingModel());
        getNavigator().addView(planningSchedulingHandler.getViewName(), planningSchedulingHandler.getViewInstance());
        
        //Workforce page
        WorkforceView workforceView = new WorkforceView();
        WorkforceModel workforceModel = new WorkforceModel();
        WorkforceHandler workforceHandler = new WorkforceHandler(workforceView, workforceModel);
        getNavigator().addView(workforceHandler.getViewName(), workforceHandler.getViewInstance());
        
        
        //Customer page
        CalendarModel calendarModel = new CalendarModel();
        CalendarView calendarView = new CalendarView();
        CalendarHandler calendarHandler = new CalendarHandler(calendarView, calendarModel);
        getNavigator().addView(calendarHandler.getViewName(), calendarHandler.getViewInstance());
        
        
        getNavigator().addView(ServiceRequestView.NAME, ServiceRequestView.class);
        
        getNavigator().addView(ReportsView.NAME, ReportsView.class);
        
        
        //Customer page
        CustomerModel customerModel = new CustomerModel();
        CustomerView customerView = new CustomerView();
        CustomerHandler customerHandler = new CustomerHandler(customerView, customerModel);
        getNavigator().addView(customerHandler.getViewName(), customerHandler.getViewInstance());
        

        //Active Products page
        ActiveAssetsModel activeProducts = new ActiveAssetsModel();
        ActiveAssetsView activeProductsView = new ActiveAssetsView();
        ActiveAssetsHandler activeProductsHandler = new ActiveAssetsHandler(activeProductsView, activeProducts);
        getNavigator().addView(activeProductsHandler.getViewName(), activeProductsHandler.getViewInstance());
        
        //Customer Products page
        CustomerAssetsModel customerProducts = new CustomerAssetsModel((User) VaadinSession.getCurrent().getAttribute("userData"));
        CustomerAssetsView customerProductsView = new CustomerAssetsView();
        CustomerAssetsHandler customerProductsHandler = new CustomerAssetsHandler(customerProductsView, customerProducts);
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



