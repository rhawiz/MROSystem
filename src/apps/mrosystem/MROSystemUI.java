package apps.mrosystem;

import javax.servlet.annotation.WebServlet;

import apps.mrosystem.controller.LoginHandler;
import apps.mrosystem.model.Authentication;
import apps.mrosystem.view.AdminView;
import apps.mrosystem.view.AssetsView;
import apps.mrosystem.view.CalendarView;
import apps.mrosystem.view.CustomerView;
import apps.mrosystem.view.InventoryView;
import apps.mrosystem.view.LoginView;
import apps.mrosystem.view.MainView;
import apps.mrosystem.view.PlanningSchedulingView;
import apps.mrosystem.view.ReportsView;
import apps.mrosystem.view.ServiceRequestView;
import apps.mrosystem.view.UsersView;
import apps.mrosystem.view.WorkOrdersView;
import apps.mrosystem.view.WorkforceView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
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
        
        new Navigator(this, this);
        
        final LoginHandler loginHandler= new LoginHandler(new LoginView(), new Authentication());

        getNavigator().addView(loginHandler.getViewName(), loginHandler.getLoginClass());//

        getNavigator().addView(MainView.NAME, MainView.class);
        
        getNavigator().addView(InventoryView.NAME, InventoryView.class);
        
        getNavigator().addView(AssetsView.NAME, AssetsView.class);
        
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



