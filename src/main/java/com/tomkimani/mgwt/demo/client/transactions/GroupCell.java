package com.tomkimani.mgwt.demo.client.transactions;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;

public interface GroupCell {
	
	public class Header {
		private final Date date;

		public Header(Date date) {
			this.date = date;

		}

		public Date getDate() {
			return date;
		}

		public String getDateStr() {
			return DateTimeFormat.getFormat("EEEE,dd/MM/yyyy").format(getDate());
		}
		
		public String getDateTitle(){
			return DateTimeFormat.getFormat("EEEE").format(getDate());
		}

	}

	public class Content {
		
		private Double amount;
		private String transactionType;
		private String transactionTime;
		private int index;
		
		public String getTransactionType() {
			return transactionType;
		}

		public Double getAmount() {
			return amount;
		}
		
		public String getTransactionTime() {
			DefaultDateTimeFormatInfo info =new DefaultDateTimeFormatInfo();
			final DateTimeFormat dtf = new DateTimeFormat("H:m:s",info){};
			Date time1 = dtf.parse(transactionTime);
			return DateTimeFormat.getFormat("h:mm a").format(time1);
		}
		
		public int getIndex() {
			return index;
		}
	
		public Content(int index, String transactionType,Double amount,String TransactionTime) {
			this.index = index;
			this.transactionType = transactionType;
			this.amount = amount;
			this.transactionTime = TransactionTime;
	  }
	}
}
