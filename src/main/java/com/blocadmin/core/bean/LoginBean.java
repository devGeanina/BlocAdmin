package com.blocadmin.core.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.blocadmin.core.entity.User;
import com.blocadmin.core.service.LoginService;
import com.blocadmin.core.utils.SessionUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(LoginBean.class);

	private String password;
	private String message, username;

	@Inject
	private LoginService loginService;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		try {
			User user = loginService.login(username, password);
			if (user != null) {
				  this.username = user.getUsername();
				  HttpSession session = SessionUtil.getSession();
				  session.setAttribute("username", username); session.setAttribute("userid",
				  user.getId()); 
				  session.setAttribute("password", user.getPassword());
				return "/gui/home.xhtml?faces-redirect=true";
			} else {
				facesContext.addMessage(null, new FacesMessage("Wrong password or username. Please try again!"));
				return "/login.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			LOGGER.error("Exception logging in the user: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error found when signing in."));
		}
		return null; // will reuse current view
	}

	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			loginService.logout(SessionUtil.getUserId());
			HttpSession session = SessionUtil.getSession();
			session.invalidate();
			return "/login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Exception logging out: " + e.getMessage());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error initializing the login page."));
		}
		return null; // will reuse current view
	}
}
