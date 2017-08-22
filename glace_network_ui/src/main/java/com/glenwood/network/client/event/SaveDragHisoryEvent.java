package com.glenwood.network.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SaveDragHisoryEvent extends GwtEvent<SaveDragHistoryEventHandler> {

	public static Type<SaveDragHistoryEventHandler> TYPE = new Type<SaveDragHistoryEventHandler>();
	
	@Override
	public Type<SaveDragHistoryEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveDragHistoryEventHandler handler) {
		handler.saveHistory(this);
	}

}
