package com.tomkimani.mgwt.demo.client.customerSearch;

import java.io.Serializable;

public class CustomerResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7936782549748380830L;
	private String refNo;
	private String firstName;
	private String lastName;

	public CustomerResult(String RefNo, String firstName, String lastName) {
		this.refNo = RefNo;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getRefNo() {
		return refNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getDisplayName(){
		return refNo +" - "+ firstName +" "+ lastName;
	}
}
