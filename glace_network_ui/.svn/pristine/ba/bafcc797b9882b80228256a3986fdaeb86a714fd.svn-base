package com.glenwood.network.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ReDrawEvent extends GwtEvent<ReDrawEventHandler> {
	public static Type<ReDrawEventHandler>  TYPE = new Type<ReDrawEventHandler>();
	
	@Override
	public Type<ReDrawEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReDrawEventHandler handler) {
		handler.Onredraw(this);
	}

}
