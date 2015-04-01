package apps.mrosystem.view;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import apps.mrosystem.domain.User;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Button.ClickEvent;

public class LoginView extends CustomComponent implements View{

    public static final String NAME = "login";

    private final TextField user;

    private final PasswordField password;

    private final Button loginButton;

    public LoginView() {
        setSizeFull();
        user = new TextField("User:");
        user.setWidth("300px");
        user.setValue("customer.test");
        user.setRequired(true);
        user.setInputPrompt("Your username (eg. joe@email.com or joe.smith)");
        //user.addValidator(new EmailValidator("Username must be an email address"));
        user.setInvalidAllowed(false);

        password = new PasswordField("Password:");
        password.setWidth("300px");
        //password.addValidator(new PasswordValidator());
        password.setRequired(true);
        password.setValue("123456");
        password.setNullRepresentation("");

        loginButton = new Button("Login");
        loginButton.setClickShortcut(KeyCode.ENTER);
//        loginButton.addClickListener(new ClickListener() {	
//			public void buttonClick(ClickEvent event) {
//				login();
//				
//			}
//		});

        VerticalLayout fields = new VerticalLayout(user, password, loginButton);
        fields.setCaption("Please login to the system. (/raw12743)");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLACK);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        user.focus();
    }

    
    public void addLoginListener(ClickListener listenerForLoginClick){
    	loginButton.addClickListener(listenerForLoginClick);
    }

    public void displayErrorMessage(String errorMessage){
    	System.out.println(errorMessage);
    }
    
    public String getUser(){
    	return user.getValue();
    }
    
    public String getPassword(){
    	return password.getValue();
    }

    public void authorised(User user) {
    	getSession().setAttribute("userData", user);
        getUI().getNavigator().navigateTo(MainView.NAME);
    }
    
    public void unauthorised(){
        this.password.setValue(null);
        this.password.focus();
    }
    


}