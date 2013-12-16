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
}
