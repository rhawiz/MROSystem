package apps.mrosystem;

import javax.servlet.annotation.WebServlet;

import apps.mrosystem.controller.AssetsHandler;
import apps.mrosystem.controller.LoginHandler;
import apps.mrosystem.model.Assets;
import apps.mrosystem.model.Authentication;
import apps.mrosystem.view.*;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mrosystem")
public class MROSystemUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MROSystemUI.class)
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
        final LoginHandler loginHandler= new LoginHandler(loginView, new Authentication());
        navigator.addView(loginHandler.getViewName(), loginView);

        
        //Main page
        getNavigator().addView(MainView.NAME, MainView.class);
        
        
        //Inventory page
        getNavigator().addView(InventoryView.NAME, InventoryView.class);
        
        
        //Assets page
        Assets assetsModel = new Assets();
        AssetsView assetsView = new AssetsViewImpl();
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
        
        getNavigator().addView(CustomerView.NAME, CustomerView.class);


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
	
	
        
}



