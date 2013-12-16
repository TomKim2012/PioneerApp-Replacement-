package com.tomkimani.mgwt.demo.client.ui;

import com.googlecode.mgwt.ui.client.widget.MPasswordTextBox;

public class PasswordField extends MPasswordTextBox{

	public PasswordField(String placeHolderValue) {
		getElement().getFirstChildElement().setAttribute("placeHolder", placeHolderValue);
	}
	public void setPlaceholder(String placeHolderValue){
		getElement().setAttribute("placeHolder", placeHolderValue);
	}
	
	public void setClass(String className){
		setStyleName(className);
	}
	
	public void setType(String type){
		getElement().setAttribute("type", type);
	}
}
