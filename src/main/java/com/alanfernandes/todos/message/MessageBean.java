package com.alanfernandes.todos.message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageBean {

	public MessageBean(String info, String description) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, info, description));
	}
}
