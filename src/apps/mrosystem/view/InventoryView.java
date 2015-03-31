package apps.mrosystem.view;

import apps.mrosystem.controller.InventoryHandler;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class InventoryView extends CustomComponent implements View{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	public static final String NAME = "INVENTORY";
	
	private HorizontalLayout mainContentContainer;
	
	@AutoGenerated
	private HorizontalLayout mainLayout;

	private SidePanel sidePanel;
	
	private Label testLabel;
	private String[] authorisedUsers = new String[]{"Admin","Technician","Planner","Management"};

	private User userData;

	private InventoryHandler inventoryHandler;

	private TabSheet tabSheet;

	private HorizontalSplitPanel viewInventoryTab;

	private GridLayout manageInventoryTab;

	private Component viewHistoryTab;

	private CssLayout viewEventsTab;

	private TreeTable inventoryTable;

	private VerticalLayout inventoryTableControl;

	private OptionGroup inventoryTableViewMode;

	private Tree inventoryTableClassFilter;

	private TextField inventoryTableSearchBox;

	protected Filter textFilter;

	public InventoryView() {

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
				inventoryHandler.init();
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
/*			assetsTabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);*/
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		
		
		viewInventoryTab = buildViewInventoryTab();
		
		manageInventoryTab = buildManageInventoryTab();
		
		viewEventsTab = buildEventsTab();
		
		tabSheet.addTab(viewInventoryTab, "View Inventory", FontAwesome.TABLE);
		//tabSheet.addTab(manageInventoryTab, "Manage Inventory", FontAwesome.BAR_CHART_O);
		//tabSheet.addTab(viewEventsTab, "View Events", FontAwesome.HISTORY);
		
		mainContentContainer.addComponent(tabSheet);
		
		return mainContentContainer;
	}
	

	private CssLayout buildEventsTab() {
		// TODO Auto-generated method stub
		viewEventsTab = new CssLayout();
		
		return viewEventsTab;
	}


	private GridLayout buildManageInventoryTab() {
		// TODO Auto-generated method stub
		manageInventoryTab = new GridLayout();
		manageInventoryTab.setImmediate(false);
		manageInventoryTab.setWidth("100.0%");
		manageInventoryTab.setHeight("100.0%");
		
		return manageInventoryTab;
	}

	private HorizontalSplitPanel buildViewInventoryTab() {
		// TODO Auto-generated method stub
		viewInventoryTab = new HorizontalSplitPanel();
		viewInventoryTab.setImmediate(false);
		viewInventoryTab.setWidth("100.0%");
		viewInventoryTab.setHeight("100.0%");
		
		viewInventoryTab.setSizeFull();
		viewInventoryTab.setSplitPosition(11, Unit.PERCENTAGE);
		viewInventoryTab.setMaxSplitPosition(11, Unit.PERCENTAGE);
		viewInventoryTab.setStyleName("padding-small");
		
		viewInventoryTab.addComponent(buildViewInventoryTableControl());
		viewInventoryTab.addComponent(buildViewInventoryTable());
		
		return viewInventoryTab;
	}
	
	private VerticalLayout buildViewInventoryTableControl(){
		inventoryTableControl = new VerticalLayout();
		
		inventoryTableSearchBox = new TextField();
		inventoryTableSearchBox.setWidth(100,Unit.PERCENTAGE);
		inventoryTableSearchBox.setInputPrompt("Filter");
		inventoryTableSearchBox.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				if(!event.getText().equals("")){
					removeInventoryTableFilter();
					textFilter = new Or(
							new SimpleStringFilter("Part Number", event.getText(),true, false),
							new SimpleStringFilter("Serial Number", event.getText(),true, false),
							new SimpleStringFilter("Name", event.getText(),true, false)
								);
					addInventoryTableFilter(textFilter);


				}else{
					removeInventoryTableFilter();
				}
				
			}
		});
		
		inventoryTableViewMode = new OptionGroup("View mode");
		inventoryTableViewMode.setWidth(100,Unit.PERCENTAGE);

		inventoryTableViewMode.setNullSelectionAllowed(false);
		inventoryTableViewMode.addItem("All");
		inventoryTableViewMode.addItem("Part Number");
		inventoryTableViewMode.select("All");

		
		inventoryTableViewMode.addValueChangeListener(inventoryHandler.getTableViewModeItemChangeListener());
		
		
		inventoryTableControl.addComponent(inventoryTableSearchBox);

		inventoryTableControl.addComponent(inventoryTableViewMode);
		
		inventoryTableControl.addStyleName("padding-small");
		
		return inventoryTableControl;
	}
	
	protected void addInventoryTableFilter(Filter filter) {
		HierarchicalContainer container = (HierarchicalContainer) inventoryTable.getContainerDataSource();
		container.addContainerFilter(filter);
		
	}


	protected void removeInventoryTableFilter() {
		((HierarchicalContainer) inventoryTable.getContainerDataSource()).removeAllContainerFilters();

		
	}


	public void setInventoryTableDataSource(HierarchicalContainer container){
		lockUI();
		try {
			inventoryTable.setContainerDataSource(container);
		} finally {
			unlockUI();
		}
	}
	
	private TreeTable buildViewInventoryTable(){
		inventoryTable = new TreeTable();
		inventoryTable.setSizeFull();
		inventoryTable.setSelectable(true);
		inventoryTable.setMultiSelect(true);
		inventoryTable.setImmediate(true);
		
		return inventoryTable;
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


	public void setHandler(InventoryHandler handler) {
		this.inventoryHandler = handler;
	}

	public void clearFilterText() {
		inventoryTableSearchBox.setValue("");
		removeAllInventoryTableFilters();

	}

	public void removeAllInventoryTableFilters() {
		((HierarchicalContainer) inventoryTable.getContainerDataSource())
				.removeAllContainerFilters();
	}


	public void setWaiting(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
