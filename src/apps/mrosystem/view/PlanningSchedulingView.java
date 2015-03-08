package apps.mrosystem.view;

import java.util.Arrays;

import apps.mrosystem.domain.User;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class PlanningSchedulingView extends CustomComponent implements View{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	public static final String NAME = "PLANNINGSCHEDULING";
	
	private GridLayout mainContentContainer;
	
	@AutoGenerated
	private HorizontalLayout mainLayout;

	private SidePanel sidePanel;
	
	private Label testLabel;
	private String[] authorisedUsers = new String[]{"Admin","Planner","Management"};

	private User userData;

	public PlanningSchedulingView() {
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
		// TODO add user code here
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
		
		mainContentContainer = new GridLayout();
		mainContentContainer.setImmediate(false);
		mainContentContainer.setWidth("100.0%");
		mainContentContainer.setHeight("100.0%");
		mainContentContainer.setMargin(false);
		mainContentContainer.setRows(2);
		
		Label unauthorisedLabel = new Label();
		unauthorisedLabel.setImmediate(false);
		unauthorisedLabel.setWidth("-1px");
		unauthorisedLabel.setHeight("-1px");
		unauthorisedLabel.setValue("UNAUTHORISED ACCESS. You do not have access to view this page.");
		mainContentContainer.addComponent(unauthorisedLabel, 0, 0);
		
		mainLayout.addComponent(mainContentContainer);
		
		mainLayout.setExpandRatio(mainContentContainer, 1.0f);
		
		return mainLayout;
	}

	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
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
	private GridLayout buildMainContentContainer() {
		// common part: create layout
		mainContentContainer = new GridLayout();
		mainContentContainer.setImmediate(false);
		mainContentContainer.setWidth("100.0%");
		mainContentContainer.setHeight("100.0%");
		mainContentContainer.setMargin(false);
		mainContentContainer.setRows(2);
		
		// page1Label
		testLabel = new Label();
		testLabel.setImmediate(false);
		testLabel.setWidth("-1px");
		testLabel.setHeight("-1px");
		testLabel.setValue(NAME);
		mainContentContainer.addComponent(testLabel, 0, 0);
		
		return mainContentContainer;
	}

}
