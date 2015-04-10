package apps.mrosystem.view;

import apps.mrosystem.controller.AssetDetailsHandler;
import apps.mrosystem.controller.UserDetailsHandler;
import apps.mrosystem.domain.Part;
import apps.mrosystem.domain.User;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SidePanel extends CustomComponent implements View{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private CssLayout mainLayout;
	private CssLayout userControlContainer;
	private Button logoutButton;
	private Label usernameLabel;
	private Button settingButton;
	
	private Label title;
	
	
	private String username;
	
	private User userData;
	
	private Button assetsButton;
	private Button inventoryButton;
	private Button workOrderButton;
	private Button planningSchedulingButton;
	private Button workforceButton;
	private Button activeAssetsButton;
	private Button reportsButton;
	private Button serviceRequestButton;
	private Button calendarButton;
	
	private Button customerButton;
	private Button customerAssetsButton;
	private MenuBar settings;
	private MenuItem settingsItem;

	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public SidePanel() {
		userData = (User) VaadinSession.getCurrent().getAttribute("userData");
		if(userData != null){
			mainLayout = buildMainLayout();
			setCompositionRoot(mainLayout);
		}
	}
	
	public void setActiveButton(String button){
		
		assetsButton.setStyleName("side-panel-nav-button assets-button");
		inventoryButton.setStyleName("side-panel-nav-button inventory-button");
		workOrderButton.setStyleName("side-panel-nav-button work-order-button");
		planningSchedulingButton.setStyleName("side-panel-nav-button planning-scheduling-button");
		workforceButton.setStyleName("side-panel-nav-button workforce-button");
		calendarButton.setStyleName("side-panel-nav-button calendar-button");
		serviceRequestButton.setStyleName("side-panel-nav-button service-request-button");
		reportsButton.setStyleName("side-panel-nav-button reports-button");
		customerButton.setStyleName("side-panel-nav-button customer-button");
		activeAssetsButton.setStyleName("side-panel-nav-button active-assets-button");
		customerAssetsButton.setStyleName("side-panel-nav-button customer-assets-button");
		
		if (button.equals("ASSETS")) {
			assetsButton
					.setStyleName("side-panel-nav-button button-selected assets-button");
		} else if (button.equals("INVENTORY")) {
			inventoryButton
					.setStyleName("side-panel-nav-button button-selected inventory-button");
		} else if (button.equals("WORKORDERS")) {
			workOrderButton
					.setStyleName("side-panel-nav-button button-selected work-order-button");
		} else if (button.equals("PLANNINGSCHEDULING")) {
			planningSchedulingButton
					.setStyleName("side-panel-nav-button button-selected planning-scheduling-button");
		} else if (button.equals("WORKFORCE")) {
			workforceButton
					.setStyleName("side-panel-nav-button button-selected workforce-button");
		} else if (button.equals("CALENDAR")) {
			calendarButton
					.setStyleName("side-panel-nav-button button-selected calendar-button");
		} else if (button.equals("SERVICEREQUEST")) {
			serviceRequestButton
					.setStyleName("side-panel-nav-button button-selected service-request-button");
		} else if (button.equals("REPORTS")) {
			reportsButton
					.setStyleName("side-panel-nav-button button-selected reports-button");
		}else if (button.equals("CUSTOMER")) {
			customerButton
					.setStyleName("side-panel-nav-button button-selected customer-button");
		} else if (button.equals("ACTIVEASSETS")) {
			activeAssetsButton
					.setStyleName("side-panel-nav-button button-selected active-assets-button");
		} else if (button.equals("CUSTOMERASSETS")) {
			customerAssetsButton
					.setStyleName("side-panel-nav-button button-selected customer-assets-button");
		}
	
	}
	
	private void initButtons(){
		initAssetsButton();
		initCalendarButton();
		initInventoryButton();
		initPlanningSchedulingButton();
		initReportsButton();
		initServiceRequestButton();
		initWorkforceButton();
		initWorkOrderButton();
		initCustomerButton();
		initActiveAssetsButton();
		initCustomerAssetsButton();
		
	}


	@AutoGenerated
	private CssLayout buildMainLayout() {
		// common part: create layout
		initButtons();
		
		String userType = userData.getRole();

		if (userType.equals("Admin")) {
			mainLayout = buildAdminLayout();
		} else if (userType.equals("Technician")) {
			mainLayout = buildTechnicianLayout();
		} else if (userType.equals("Planner")) {
			mainLayout = buildPlannerLayout();
		} else if (userType.equals("Quality")) {
			mainLayout = buildQualityAssurerLayout();
		} else if (userType.equals("Inspector")) {
			mainLayout = buildInspectorLayout();
		} else if (userType.equals("Manager")) {
			mainLayout = buildManagerLayout();
		} else if (userType.equals("Customer")) {
			mainLayout = buildCustomerLayout();
		}else{
			mainLayout = buildAdminLayout();
		}
		mainLayout.setWidth("160px");
		return mainLayout;
	}	

	
	private CssLayout buildAdminLayout(){
		CssLayout mainLayout = new CssLayout();
		mainLayout.addStyleName("side-panel-container");
		mainLayout.setImmediate(false);
		
		// top-level component properties

		//Title

		title = new Label("MRO System" + "<br><label class='small-text'>Admin Panel</label>");
		title.setContentMode(ContentMode.HTML);
		title.setStyleName("side-panel-main-title-label");
		
		mainLayout.addComponent(title);
		
		mainLayout.addComponent(assetsButton);

		mainLayout.addComponent(customerButton);

		mainLayout.addComponent(inventoryButton);

		mainLayout.addComponent(activeAssetsButton);
		
		mainLayout.addComponent(planningSchedulingButton);
		
		mainLayout.addComponent(reportsButton);

		mainLayout.addComponent(serviceRequestButton);
		
		mainLayout.addComponent(workOrderButton);
		
		mainLayout.addComponent(workforceButton);
		
		mainLayout.addComponent(customerAssetsButton);
		
		
		userControlContainer = buildUserControlContainer();
		mainLayout.addComponent(userControlContainer);
		
		
		return mainLayout;
	}
	
	private CssLayout buildTechnicianLayout(){
		CssLayout mainLayout = new CssLayout();
		mainLayout.addStyleName("side-panel-container");
		mainLayout.setImmediate(false);

		title = new Label("MRO System" + "<br><label class='small-text'>Technician Panel</label>");
		title.setContentMode(ContentMode.HTML);
		title.setStyleName("side-panel-main-title-label");
		
		mainLayout.addComponent(title);
		
		mainLayout.addComponent(assetsButton);

		mainLayout.addComponent(calendarButton);

		mainLayout.addComponent(customerButton);

		mainLayout.addComponent(inventoryButton);
		
		mainLayout.addComponent(workOrderButton);
		
		userControlContainer = buildUserControlContainer();
		mainLayout.addComponent(userControlContainer);
		
		
		return mainLayout;
	}
	
	private CssLayout buildManagerLayout(){
		CssLayout mainLayout = new CssLayout();
		mainLayout.addStyleName("side-panel-container");
		mainLayout.setImmediate(false);

		title = new Label("MRO System" + "<br><label class='small-text'>Manager Panel</label>");
		title.setContentMode(ContentMode.HTML);
		title.setStyleName("side-panel-main-title-label");
		
		mainLayout.addComponent(title);
		

		mainLayout.addComponent(assetsButton);

		mainLayout.addComponent(customerButton);

		mainLayout.addComponent(inventoryButton);

		mainLayout.addComponent(planningSchedulingButton);
		
		mainLayout.addComponent(reportsButton);

		mainLayout.addComponent(workOrderButton);
		
		mainLayout.addComponent(workforceButton);
		
		userControlContainer = buildUserControlContainer();
		mainLayout.addComponent(userControlContainer);
		
		return mainLayout;
	}
	
	private CssLayout buildCustomerLayout(){
		CssLayout mainLayout = new CssLayout();
		mainLayout.addStyleName("side-panel-container");
		mainLayout.setImmediate(false);
		
		title = new Label("MRO System" + "<br><label class='small-text'>Customer Panel</label>");
		title.setContentMode(ContentMode.HTML);
		title.setStyleName("side-panel-main-title-label");
		
		mainLayout.addComponent(title);
		
		mainLayout.addComponent(serviceRequestButton);
		
		mainLayout.addComponent(customerAssetsButton);
		
		userControlContainer = buildUserControlContainer();
		mainLayout.addComponent(userControlContainer);
		
		return mainLayout;
	}
	
	private CssLayout buildPlannerLayout(){
		CssLayout mainLayout = new CssLayout();
		mainLayout.addStyleName("side-panel-container");
		mainLayout.setImmediate(false);

		title = new Label("MRO System" + "<br><label class='small-text'>Planner Panel</label>");
		title.setContentMode(ContentMode.HTML);
		title.setStyleName("side-panel-main-title-label");
		
		mainLayout.addComponent(title);
		userControlContainer = buildUserControlContainer();
		mainLayout.addComponent(userControlContainer);
		
		mainLayout.addComponent(assetsButton);
		
		mainLayout.addComponent(customerButton);
		
		mainLayout.addComponent(inventoryButton);
		
		mainLayout.addComponent(planningSchedulingButton);
		
		mainLayout.addComponent(serviceRequestButton);
				
		mainLayout.addComponent(workOrderButton);
		

		return mainLayout;
	}
	
	
	private CssLayout buildQualityAssurerLayout(){
		return null;
	}
	
	private CssLayout buildInspectorLayout(){
		return null;
	}
	
	@AutoGenerated
	private CssLayout buildUserControlContainer() {
		// common part: create layout
		userControlContainer = new CssLayout();
		//userControlContainer.addStyleName("side-panel-user-control");
		userControlContainer.setImmediate(false);
		
		Command menuItemCommandListener = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				if(selectedItem.getText() == "Sign Out"){
					logout();	
				}
				if(selectedItem.getText() == "Edit Profile"){
					new UserDetailsHandler(new UserDetailsView(),userData).show();
				}
				if(selectedItem.getText() == "Preferences"){
					Notification.show("Pref");
					
				}
				
			}
		};
		
		settings = new MenuBar();
		settings.addStyleName("user-menu");
		settingsItem = settings.addItem(userData.getFirstname(), new ThemeResource(userData.getProfileImg()),null);
		settingsItem.addItem("Edit Profile", menuItemCommandListener);
		settingsItem.addItem("Preferences", menuItemCommandListener);
		settingsItem.addSeparator();
		settingsItem.addItem("Sign Out", menuItemCommandListener);
	        
	    userControlContainer.addComponent(settings);
	        		
		
		
		return userControlContainer;
	}
	
	private void logout(){

			getSession().setAttribute("userData", null);
            // Refresh this view, should redirect to login view
			getUI().getNavigator().navigateTo("");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	
	private void initAssetsButton(){
		assetsButton = new Button();
		assetsButton.setStyleName("side-panel-nav-button assets-button");
		assetsButton.setCaption("Assets");
		assetsButton.setImmediate(true);
		assetsButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(AssetsView.NAME);
			}
		});

	}
	
	private void initInventoryButton(){
		inventoryButton = new Button();
		inventoryButton.setStyleName("side-panel-nav-button inventory-button");
		inventoryButton.setCaption("Inventory");
		inventoryButton.setImmediate(true);
		inventoryButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(InventoryView.NAME);
			}
		});

	}
	
	private void initWorkOrderButton(){
		workOrderButton = new Button();
		workOrderButton.setStyleName("side-panel-nav-button work-order-button");
		workOrderButton.setCaption("Work Orders");
		workOrderButton.setImmediate(true);
		workOrderButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WorkOrdersView.NAME);
			}
		});

	}
	
	private void initPlanningSchedulingButton(){
		planningSchedulingButton = new Button();
		planningSchedulingButton.setStyleName("side-panel-nav-button planning-scheduling-button");
		planningSchedulingButton.setCaption("Planning");
		planningSchedulingButton.setImmediate(true);
		planningSchedulingButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PlanningSchedulingView.NAME);
			}
		});

	}
	
	private void initWorkforceButton(){
		workforceButton = new Button();
		workforceButton.setStyleName("side-panel-nav-button workforce-button");
		workforceButton.setCaption("Workforce");
		workforceButton.setImmediate(true);
		workforceButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WorkforceView.NAME);
			}
		});

	}

	
	
	private void initCalendarButton(){
		calendarButton = new Button();
		calendarButton.setStyleName("side-panel-nav-button calendar-button");
		calendarButton.setCaption("Calendar");
		calendarButton.setImmediate(true);
		calendarButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(CalendarView.NAME);
			}
		});

	}
	
	private void initServiceRequestButton(){
		serviceRequestButton = new Button();
		serviceRequestButton.setStyleName("side-panel-nav-button service-request-button");
		serviceRequestButton.setCaption("Work Request");
		serviceRequestButton.setImmediate(true);
		serviceRequestButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(ServiceRequestView.NAME);
			}
		});

	}
	
	private void initReportsButton(){
		reportsButton = new Button();
		reportsButton.setStyleName("side-panel-nav-button reports-button");
		reportsButton.setCaption("Reports");
		reportsButton.setImmediate(true);
		reportsButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(ReportsView.NAME);
			}
		});

	}

	
	private void initCustomerButton(){
		customerButton = new Button();
		customerButton.setStyleName("side-panel-nav-button customer-button");
		customerButton.setCaption("Customer");
		customerButton.setImmediate(true);
		customerButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(CustomerView.NAME);
			}
		});
		
	}
	private void initActiveAssetsButton(){
		activeAssetsButton = new Button();
		activeAssetsButton.setStyleName("side-panel-nav-button active-products-button");
		activeAssetsButton.setCaption("Active Assets");
		activeAssetsButton.setImmediate(true);
		activeAssetsButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(ActiveAssetsView.NAME);
			}
		});

	}
	

	private void initCustomerAssetsButton() {
		customerAssetsButton = new Button();
		customerAssetsButton.setStyleName("side-panel-nav-button customer-assets-button");
		customerAssetsButton.setCaption("My Assets");
		customerAssetsButton.setImmediate(true);
		customerAssetsButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(CustomerAssetsView.NAME);
			}
		});
		
	}
}
