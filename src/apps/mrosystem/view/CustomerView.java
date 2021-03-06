package apps.mrosystem.view;

import java.util.Arrays;

import apps.mrosystem.controller.CustomerHandler;
import apps.mrosystem.domain.User;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerView extends CustomComponent implements View{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	public static final String NAME = "CUSTOMER";
	
	private HorizontalLayout mainContentContainer;
	
	@AutoGenerated
	private HorizontalLayout mainLayout;

	private SidePanel sidePanel;
	
	private Label testLabel;
	private String[] authorisedUsers = new String[]{"Admin","Technician","Planner","Management"};

	private User userData;

	private TabSheet tabSheet;

	private HorizontalSplitPanel viewCustomerTab;

	private TreeTable customerTable;

	protected Filter textFilter;

	private VerticalLayout customerTableControl;

	private TextField customerTableSearchBox;

	private OptionGroup customerTableViewMode;

	private CustomerHandler customerHandler;

	public CustomerView() {

	}
	
	
	private HorizontalLayout unauthorisedAccess() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		setWidth("100.0%");
		setHeight("100.0%");
		
		sidePanel = new SidePanel();
		sidePanel.setActiveButton(NAME);
		sidePanel.setImmediate(false);
		sidePanel.setWidth("160px");
		sidePanel.setHeight("100.0%");
		mainLayout.addComponent(sidePanel);
		
		mainContentContainer = new HorizontalLayout();
		mainContentContainer.setImmediate(false);
		mainContentContainer.setWidth("100.0%");
		mainContentContainer.setHeight("100.0%");
		mainContentContainer.setMargin(false);
		
		Label unauthorisedLabel = new Label();
		unauthorisedLabel.setImmediate(false);
		unauthorisedLabel.setWidth("-1px");
		unauthorisedLabel.setHeight("-1px");
		unauthorisedLabel.setValue("UNAUTHORISED ACCESS. You do not have access to view this page.");
		mainContentContainer.addComponent(unauthorisedLabel);
		
		mainLayout.addComponent(mainContentContainer);
		
		mainLayout.setExpandRatio(mainContentContainer, 1.0f);
		
		return mainLayout;
	}

	public void enter(ViewChangeEvent event) {
		init();
		
	}

	private void init() {
		userData = (User) VaadinSession.getCurrent().getAttribute("userData");
		if(userData != null){
			if(userData.isAuthorised(authorisedUsers)){
				buildMainLayout();
				customerHandler.init();
			}
			else{
				unauthorisedAccess();
			}
			
			setCompositionRoot(mainLayout);
		}
		
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// sidePanel
		sidePanel = new SidePanel();
		sidePanel.setActiveButton(NAME);
		sidePanel.setImmediate(false);
		sidePanel.setWidth("160px");
		sidePanel.setHeight("100.0%");
		mainLayout.addComponent(sidePanel);
		
		// mainContentContainer
		mainContentContainer = buildMainContentContainer();
		mainLayout.addComponent(mainContentContainer);
		
		mainLayout.setExpandRatio(mainContentContainer, 1.0f);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildMainContentContainer() {
		// common part: create layout
		mainContentContainer = new HorizontalLayout();
		mainContentContainer.setImmediate(false);
		mainContentContainer.setWidth("100.0%");
		mainContentContainer.setHeight("100.0%");
		mainContentContainer.setMargin(false);
		
		
		tabSheet = new TabSheet();
		tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		
		
		viewCustomerTab = buildViewCustomerTab();
		
		tabSheet.addTab(viewCustomerTab, "View Customers", FontAwesome.TABLE);

		
		mainContentContainer.addComponent(tabSheet);
		
		return mainContentContainer;
	}
	
	private HorizontalSplitPanel buildViewCustomerTab() {
		// TODO Auto-generated method stub
		viewCustomerTab = new HorizontalSplitPanel();
		viewCustomerTab.setImmediate(false);
		viewCustomerTab.setWidth("100.0%");
		viewCustomerTab.setHeight("100.0%");
		
		viewCustomerTab.setSizeFull();
		viewCustomerTab.setSplitPosition(11, Unit.PERCENTAGE);
		viewCustomerTab.setMaxSplitPosition(11, Unit.PERCENTAGE);
		viewCustomerTab.setStyleName("padding-small");
		
		viewCustomerTab.addComponent(buildViewCustomerTableControl());
		viewCustomerTab.addComponent(buildViewCustomerTable());
		
		return viewCustomerTab;
	}
	
	private VerticalLayout buildViewCustomerTableControl(){
		customerTableControl = new VerticalLayout();
		
		customerTableSearchBox = new TextField();
		customerTableSearchBox.setWidth(100,Unit.PERCENTAGE);
		customerTableSearchBox.setInputPrompt("Filter");
		customerTableSearchBox.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				if(!event.getText().equals("")){
					removeCustomerTableFilter();
					textFilter = new Or(
							new SimpleStringFilter("Part Number", event.getText(),true, false),
							new SimpleStringFilter("Serial Number", event.getText(),true, false),
							new SimpleStringFilter("Name", event.getText(),true, false)
								);
					addCustomerTableFilter(textFilter);


				}else{
					removeCustomerTableFilter();
				}
				
			}
		});
		
		customerTableViewMode = new OptionGroup("View mode");
		customerTableViewMode.setWidth(100,Unit.PERCENTAGE);

		customerTableViewMode.setNullSelectionAllowed(false);
		customerTableViewMode.addItem("All");
		customerTableViewMode.addItem("Organisation");

		
		customerTableViewMode.addValueChangeListener(customerHandler.getTableViewModeItemChangeListener());
		
		
		customerTableControl.addComponent(customerTableSearchBox);

		//customerTableControl.addComponent(customerTableViewMode);
		
		customerTableControl.addStyleName("padding-small");
		
		return customerTableControl;
	}

	
	protected void addCustomerTableFilter(Filter textFilter2) {
		// TODO Auto-generated method stub
		
	}


	protected void removeCustomerTableFilter() {
		// TODO Auto-generated method stub
		
	}


	private TreeTable buildViewCustomerTable(){
		customerTable = new TreeTable();
		customerTable.setSizeFull();
		customerTable.setSelectable(true);
		customerTable.setMultiSelect(true);
		customerTable.setImmediate(true);
		customerTable.setColumnCollapsingAllowed(true);
		
		return customerTable;
	}

	

	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}


	public void showWarning(String caption, String message) {
		new Notification(caption, message,Notification.Type.WARNING_MESSAGE);

		
	}
	
	
	private void unlockUI() {
		UI.getCurrent().getSession().getLockInstance().unlock();

		
	}

	private void lockUI() {
		UI.getCurrent().getSession().getLockInstance().lock();

		
	}

	public void setHandler(CustomerHandler handler) {
		this.customerHandler = handler;
	}


	public void setCustomerTableDataSource(HierarchicalContainer container) {
		lockUI();
		try {
			customerTable.setContainerDataSource(container);
		} finally {
			unlockUI();
		}		
	}


	public void setWaiting(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void clearFilterText() {
		// TODO Auto-generated method stub
		
	}

}
