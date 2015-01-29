package apps.mrosystem.view;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View {

    public static final String NAME = "";

    
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private SidePanel sidePanel = new SidePanel();
    
    
    public MainView() {
    	
    	setCompositionRoot(mainLayout);
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
    	mainLayout.setStyleName("container-layout");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		sidePanel.setActiveButton(NAME);
		sidePanel.setImmediate(false);
		sidePanel.setWidth("130px");
		sidePanel.setHeight("100.0%");
		mainLayout.addComponent(sidePanel);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

        
    }
}