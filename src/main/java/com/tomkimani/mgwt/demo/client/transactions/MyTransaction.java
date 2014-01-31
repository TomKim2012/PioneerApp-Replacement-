package com.tomkimani.mgwt.demo.client.transactions;

import com.google.gwt.core.client.JavaScriptObject;

public class MyTransaction extends JavaScriptObject implements Transaction{

	protected MyTransaction(){
		
	}
	public final native String getTransactionId()/*-{ return this.transaction_id }-*/;
	public final native String getCustNames()/*-{ return this.custNames; }-*/;
	public final native String getTransactionAmount()/*-{ return this.transaction_amount; }-*/;
	public final native String getTransactionCode()/*-{ return this.transaction_code; }-*/;
	public final native String getTransactionType()/*-{ return this.transaction_type; }-*/;
	public final native String getTransactionDate()/*-{ return this.transaction_date; }-*/;
	public final native String getTransactionTime()/*-{ return this.transaction_time; }-*/;
	public final native String getError()/*-{ return this.error; }-*/;
	
	public final native String getDisplayName()/*-{ return this.transaction_type+" - "+"Ksh "+this.transaction_amount+" ("+this.transaction_time+")"
												; }-*/;
	@Override
	public final void setCustNames(String names) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void setTransactionAmount(String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void setTransactionCode(String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void setTransactionDate(String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void setTransactionTime(String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void setTransactionType(String value) {
		// TODO Auto-generated method stub
		
	}

	
	
}
