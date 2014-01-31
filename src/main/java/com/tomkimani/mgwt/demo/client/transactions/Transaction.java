package com.tomkimani.mgwt.demo.client.transactions;


public interface Transaction {
	public String getTransactionId();
	public String getCustNames();
	public String getTransactionAmount();
	public String getTransactionCode();
	public String getTransactionType();
	public String getTransactionDate();
	public String getTransactionTime();
	public String getDisplayName();
	
	public void setCustNames(String names);
	public void setTransactionAmount(String value);
	public void setTransactionCode(String value);
	public void setTransactionDate(String value);
	public void setTransactionTime(String value);
	public void setTransactionType(String value);
}
