package com.tomkimani.mgwt.demo.client.ui;

import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class TextField extends MTextBox{
	
	public TextField(String placeHolderValue) {
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
