package apps.mrosystem.view;

import java.util.ArrayList;
import java.util.Set;

import apps.mrosystem.controller.WorkOrdersHandler;
import apps.mrosystem.domain.User;

import com.google.web.bindery.autobean.shared.AutoBeanVisitor.MapPropertyContext;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class WorkOrdersView extends CustomComponent implements View{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	public static final String NAME = "WORKORDERS";
	
	private HorizontalLayout mainContentContainer;
	
	@AutoGenerated
	private HorizontalLayout mainLayout;

	private SidePanel sidePanel;
	
	private Label testLabel;
	private String[] authorisedUsers = new String[]{"Admin","Technician","Planner","Management"};

	private User userData;

	private HorizontalSplitPanel viewWorkOrdersTab;

	private TabSheet tabSheet;

	private TextField searchBox;
	private Or priorityFilter;
	private Or statusFilter;
	private Or typeFilter;

	private VerticalLayout workOrdersTableControl;

	private OptionGroup tableViewMode;

	private OptionGroup tablePriorityFilter;

	private TreeTable workOrdersTable;

	private OptionGroup tableStatusFilter;

	private WorkOrdersHandler handler;

	private OptionGroup tableTypeFilter;

	private HorizontalSplitPanel mapTab;

	private VerticalLayout mapControl;

	private OptionGroup mapStatusFilter;

	private OptionGroup mapTypeFilter;

	private OptionGroup mapPriorityFilter;

	private GoogleMap map;

	public WorkOrdersView() {

	}
	
	private void init() {
		userData = (User) VaadinSession.getCurrent().getAttribute("userData");
		if(userData != null){
			if(userData.isAuthorised(authorisedUsers)){
				buildMainLayout();
			}
			else{
				unauthorisedAccess();
			}
			
			setCompositionRoot(mainLayout);
		}
		
	}
	
	
	private HorizontalLayout unauthorisedAccess() {
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
		unauthorisedLabel.setContentMode(ContentMode.HTML);
		unauthorisedLabel.setValue("UNAUTHORISED ACCESS.<br>You do not have access to view this page.");
		mainContentContainer.addComponent(unauthorisedLabel);
		
		mainLayout.addComponent(mainContentContainer);
		
		mainLayout.setExpandRatio(mainContentContainer, 1.0f);
		
		return mainLayout;
	}

	public void enter(ViewChangeEvent event) {
		init();
		handler.init();
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
		
		
		viewWorkOrdersTab = buildViewWorkOrdersTab();
		
		tabSheet.addTab(viewWorkOrdersTab, "View Work Orders", FontAwesome.TABLE);
		
		mapTab = buildMapTab();
		tabSheet.addTab(mapTab, "Map View", FontAwesome.MAP_MARKER);

		mainContentContainer.addComponent(tabSheet);
		
		return mainContentContainer;
	}

	private HorizontalSplitPanel buildMapTab(){
		mapTab = new HorizontalSplitPanel();
		mapTab.setImmediate(false);
		mapTab.setWidth("100.0%");
		mapTab.setHeight("100.0%");
		
		mapTab.setSizeFull();
		mapTab.setSplitPosition(11, Unit.PERCENTAGE);
		mapTab.setMaxSplitPosition(11, Unit.PERCENTAGE);
		mapTab.setStyleName("padding-small");
		
		
		mapControl = buildMapControl();
		mapTab.addComponent(mapControl);
		
		map = buildMap();
		mapTab.addComponent(map);
		
		return mapTab;
	}
	
	private GoogleMap buildMap() {
		map = new GoogleMap("AIzaSyCOe-8GdvExrUOy8qzdoScoJhGRU8GYc8w", null, "english");
		map.setSizeFull();
		map.setMinZoom(4);
		map.setMaxZoom(16);
		return map;		
	}

	private VerticalLayout buildMapControl() {
		mapControl = new VerticalLayout();
		
		mapPriorityFilter = new OptionGroup("Priority Filter");
		mapPriorityFilter.addStyleName("small");
		mapPriorityFilter.setMultiSelect(true);
		mapPriorityFilter.addItems("Urgent", "Very High", "High", "Medium", "Low");		
		mapPriorityFilter.select("Urgent");
		mapPriorityFilter.select("Very High");
		mapPriorityFilter.select("High");
		mapPriorityFilter.select("Medium");
		mapPriorityFilter.select("Low");
		
		
		mapTypeFilter = new OptionGroup("Work Order Type");
		mapTypeFilter.addStyleName("small");
		mapTypeFilter.setMultiSelect(true);
		mapTypeFilter.addItems("Repair", "Maintenance", "Recertification");		
		mapTypeFilter.select("Repair");
		mapTypeFilter.select("Maintenance");
		mapTypeFilter.select("Recertification");

		mapStatusFilter = new OptionGroup("Status Filter");
		mapStatusFilter.addStyleName("small");
		mapStatusFilter.setMultiSelect(true);
		mapStatusFilter.addItems("Open", "Scheduled", "Work in Progress", "On Hold", "Canceled", "Complete","Closed");
		mapStatusFilter.select("Open");
		mapStatusFilter.select("Scheduled");
		mapStatusFilter.select("Work in Progress");
		mapStatusFilter.select("On Hold");
		mapStatusFilter.select("Canceled");
		mapStatusFilter.select("Complete");
		mapStatusFilter.select("Closed");

		

		mapControl.addComponent(mapTypeFilter);
		mapControl.addComponent(mapStatusFilter);
		mapControl.addComponent(mapPriorityFilter);
		mapControl.addStyleName("padding-small");
		
		return mapControl;
	}
	
	public void clearMapMarkers(){
		map.clearMarkers();
	}
	
	public Object[] getMapPriorityFilterSelection(){
		Set list = (Set) mapPriorityFilter.getValue();
		Object[] array = (Object[]) list.toArray();
		return array;
	}
	public Object[] getMapTypeFilterSelection(){
		Set list = (Set) mapTypeFilter.getValue();
		return (Object[]) list.toArray();
	}
	public Object[] getMapStatusFilterSelection(){
		Set list = (Set) mapStatusFilter.getValue();
		return (Object[]) list.toArray();
	}
	
	public void setMapPriorityValueChangeListener(ValueChangeListener listener){
		mapPriorityFilter.addValueChangeListener(listener);
	}
	public void setMapTypeValueChangeListener(ValueChangeListener listener){
		mapTypeFilter.addValueChangeListener(listener);
	}
	public void setMapStatusValueChangeListener(ValueChangeListener listener){
		mapStatusFilter.addValueChangeListener(listener);
	}

	private HorizontalSplitPanel buildViewWorkOrdersTab() {
		viewWorkOrdersTab = new HorizontalSplitPanel();
		viewWorkOrdersTab.setImmediate(false);
		viewWorkOrdersTab.setWidth("100.0%");
		viewWorkOrdersTab.setHeight("100.0%");
		
		viewWorkOrdersTab.setSizeFull();
		viewWorkOrdersTab.setSplitPosition(11, Unit.PERCENTAGE);
		viewWorkOrdersTab.setMaxSplitPosition(11, Unit.PERCENTAGE);
		viewWorkOrdersTab.setStyleName("padding-small");
		
		viewWorkOrdersTab.addComponent(buildViewWorkOrdersTableControl());
		viewWorkOrdersTab.addComponent(buildViewWorkOrdersTable());
		
		return viewWorkOrdersTab;
		
	}

	private TreeTable buildViewWorkOrdersTable() {
		workOrdersTable = new TreeTable();
		workOrdersTable.setSizeFull();
		workOrdersTable.setSelectable(true);
		workOrdersTable.setMultiSelect(true);
		workOrdersTable.setImmediate(true);
		workOrdersTable.setColumnCollapsingAllowed(true);
		
		return workOrdersTable;
	}

	private Component buildViewWorkOrdersTableControl() {
		workOrdersTableControl = new VerticalLayout();
		
		searchBox = new TextField();
		searchBox.setWidth(100,Unit.PERCENTAGE);
		searchBox.setInputPrompt("Filter");
		searchBox.addTextChangeListener(new TextChangeListener() {
			private Or textFilter;

			@Override
			public void textChange(TextChangeEvent event) {
				if(!event.getText().equals("")){
					removeAllTableFilters();
					textFilter = new Or(
							new SimpleStringFilter("Asset Serial Number (Part Number)", event.getText(),true, false),
							new SimpleStringFilter("Organisation", event.getText(),true, false),
							new SimpleStringFilter("Short Description", event.getText(),true, false),
							new SimpleStringFilter("Location", event.getText(),true, false)
								);
					addTableFilter(textFilter);


				}else{
					removeAllTableFilters();
				}
				
			}

		});
		
		tableViewMode = new OptionGroup("View mode");
		tableViewMode.addStyleName("small");
		tableViewMode.setWidth(100,Unit.PERCENTAGE);
		tableViewMode.setNullSelectionAllowed(false);
		tableViewMode.addItem("All");
		tableViewMode.addItem("Type");
		tableViewMode.addItem("Status");
		tableViewMode.addItem("Priority");
		tableViewMode.addItem("Organisation");		
		tableViewMode.select("All");	
		tableViewMode.addValueChangeListener(handler.getTableValueChangeListener());

	
		
		tablePriorityFilter = new OptionGroup("Priority Filter");
		tablePriorityFilter.addStyleName("small");
		tablePriorityFilter.setMultiSelect(true);
		tablePriorityFilter.addItems("Urgent", "Very High", "High", "Medium", "Low");		
		tablePriorityFilter.select("Urgent");
		tablePriorityFilter.select("Very High");
		tablePriorityFilter.select("High");
		tablePriorityFilter.select("Medium");
		tablePriorityFilter.select("Low");
		
		
		tablePriorityFilter.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				removeTableFilter(priorityFilter);
				Set list = (Set) event.getProperty().getValue();
				Object[] array = (Object[]) list.toArray();
				SimpleStringFilter[] filters = new SimpleStringFilter[array.length];
				
				for(int i = 0;i<array.length;i++){
					String item = (String) array[i];
					
					filters[i] = new SimpleStringFilter("Priority", item,true, false);
					
				}
				
				priorityFilter = new Or(filters);
				addTableFilter(priorityFilter);
			}
		});
		
		
		tableTypeFilter = new OptionGroup("Work Order Type");
		tableTypeFilter.addStyleName("small");
		tableTypeFilter.setMultiSelect(true);
		tableTypeFilter.addItems("Repair", "Maintenance", "Recertification");		
		tableTypeFilter.select("Repair");
		tableTypeFilter.select("Maintenance");
		tableTypeFilter.select("Recertification");


		
		tableTypeFilter.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				removeTableFilter(typeFilter);
				Set list = (Set) event.getProperty().getValue();
				Object[] array = (Object[]) list.toArray();
				SimpleStringFilter[] filters = new SimpleStringFilter[array.length];
				
				for(int i = 0;i<array.length;i++){
					String item = (String) array[i];
					
					filters[i] = new SimpleStringFilter("Work Order Type", item,true, false);
					
				}
				
				typeFilter = new Or(filters);
				addTableFilter(typeFilter);
			}
		});
		
		tableStatusFilter = new OptionGroup("Status Filter");
		tableStatusFilter.addStyleName("small");
		tableStatusFilter.setMultiSelect(true);
		tableStatusFilter.addItems("Open", "Scheduled", "Work in Progress", "On Hold", "Canceled", "Complete","Closed");
		tableStatusFilter.select("Open");
		tableStatusFilter.select("Scheduled");
		tableStatusFilter.select("Work in Progress");
		tableStatusFilter.select("On Hold");
		tableStatusFilter.select("Canceled");
		tableStatusFilter.select("Complete");
		tableStatusFilter.select("Closed");
		tableStatusFilter.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				removeTableFilter(statusFilter);
				Set list = (Set) event.getProperty().getValue();
				Object[] array = (Object[]) list.toArray();
				SimpleStringFilter[] filters = new SimpleStringFilter[array.length];
				
				for(int i = 0;i<array.length;i++){
					String item = (String) array[i];
					
					filters[i] = new SimpleStringFilter("Status", item,true, false);
					
				}
				
				statusFilter = new Or(filters);
				addTableFilter(statusFilter);
				
			}
		});
		
		
		workOrdersTableControl.addComponent(searchBox);

		workOrdersTableControl.addComponent(tableViewMode);
		workOrdersTableControl.addComponent(tableTypeFilter);
		workOrdersTableControl.addComponent(tableStatusFilter);
		workOrdersTableControl.addComponent(tablePriorityFilter);
		workOrdersTableControl.addStyleName("padding-small");
		
		return workOrdersTableControl;
	}
	


	public void setWorkOrdersTableDataSource(HierarchicalContainer container) {
		lockUI();
		try {
			workOrdersTable.setContainerDataSource(container);
			UI.getCurrent().push();
		} finally {
			unlockUI();
		}		
	}
	
	private void unlockUI() {
		UI.getCurrent().getSession().getLockInstance().unlock();

		
	}

	private void lockUI() {
		UI.getCurrent().getSession().getLockInstance().lock();

		
	}

	public String getName(){
		return NAME;
	}
	
	public void addTableFilter(Filter textFilter) {
		HierarchicalContainer container = (HierarchicalContainer) workOrdersTable.getContainerDataSource();
		container.addContainerFilter(textFilter);		
	}
	public void removeTableFilter(Filter textFilter){
		((HierarchicalContainer) workOrdersTable.getContainerDataSource()).removeContainerFilter(textFilter);

	}
	public void removeAllTableFilters() {
		((HierarchicalContainer) workOrdersTable.getContainerDataSource()).removeAllContainerFilters();
		
	}
	public void setHandler(WorkOrdersHandler workOrdersHandler) {
		this.handler = workOrdersHandler;
		
	}

	public void clearSearchBox() {
		searchBox.setValue("");		
	}

	public void enableAllFilters() {
		tablePriorityFilter.setEnabled(true);
		for(Object itemId : tablePriorityFilter.getItemIds()){
			tablePriorityFilter.select(itemId);
		}
		tableStatusFilter.setEnabled(true);
		for(Object itemId : tableStatusFilter.getItemIds()){
			tableStatusFilter.select(itemId);
		}
		tableTypeFilter.setEnabled(true);
		for(Object itemId : tableTypeFilter.getItemIds()){
			tableTypeFilter.select(itemId);
		}
		UI.getCurrent().push();

	}

	public void disablePriorityFilter() {
		tablePriorityFilter.setEnabled(false);
		UI.getCurrent().push();

	}

	public void disableStatusFilter() {
		tableStatusFilter.setEnabled(false);
		UI.getCurrent().push();

	}

	public void disableTypeFilter() {
		tableTypeFilter.setEnabled(false);
		UI.getCurrent().push();

	}

	public void setMapMarkers(ArrayList<GoogleMapMarker> mapMarkers) {
		for(GoogleMapMarker marker : mapMarkers){
			map.addMarker(marker);
		}
		UI.getCurrent().push();

		
	}

}
