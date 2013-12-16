package com.tomkimani.mgwt.demo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class ShowBusyEvent extends GwtEvent<ShowBusyEvent.ShowBusyHandler> {

	public static Type<ShowBusyHandler> TYPE = new Type<ShowBusyHandler>();

	public interface ShowBusyHandler extends EventHandler {
		void onShowBusy(ShowBusyEvent event);
	}

	public ShowBusyEvent() {
	}

	@Override
	protected void dispatch(ShowBusyHandler handler) {
		handler.onShowBusy(this);
	}

	@Override
	public Type<ShowBusyHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ShowBusyHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new ShowBusyEvent());
	}
}
