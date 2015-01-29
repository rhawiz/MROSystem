package apps.mrosystem.controller;

import apps.mrosystem.model.Authentication;
import apps.mrosystem.view.LoginView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;

public class LoginHandler extends CustomComponent{
	private LoginView loginView;
	private Authentication authenticator;

	public LoginHandler(LoginView loginView, Authentication authenticator) {
		this.loginView = loginView;
		this.authenticator = authenticator;
		this.loginView.addLoginListener(new LoginListener());
	}
	
	class LoginListener implements ClickListener{

		@Override
		public void buttonClick(ClickEvent event) {
			String username = loginView.getUser();
			String password = loginView.getPassword();
			Boolean authorised = authenticator.authenticate(username, password);
			if(authorised){
				loginView.authorised(authenticator.getUser());
			}else{
				loginView.unauthorised();
			}
		}
	}
	
	public static String getViewName(){
		return LoginView.NAME;
	}
	
	public LoginView getLoginClass(){
		return loginView;
	}
}
