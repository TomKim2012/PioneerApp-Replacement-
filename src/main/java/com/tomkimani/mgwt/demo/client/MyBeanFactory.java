package com.tomkimani.mgwt.demo.client;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.Customer;
import com.tomkimani.mgwt.demo.client.customerSearch.CustomerSearchActivity.CustomerList;
import com.tomkimani.mgwt.demo.client.login.LoginActivity.User;
import com.tomkimani.mgwt.demo.client.transactions.detail.TransactionDetailActivity.TransactionResult;

public interface MyBeanFactory extends AutoBeanFactory {
	AutoBean<User> User();
	//AutoBean<SystemUser> SystemUser();
	AutoBean<com.tomkimani.mgwt.demo.client.settings.SettingsActivity.UsersList> UsersList();
	AutoBean<Customer> Customer();
	AutoBean<CustomerList> CustomerList();
	AutoBean<TransactionResult> transactionResult();
}
