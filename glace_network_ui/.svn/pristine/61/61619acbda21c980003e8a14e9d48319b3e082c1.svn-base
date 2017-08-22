package com.glenwood.network.client.event;

import com.glenwood.network.client.application.domain.DrawNode;
import com.google.gwt.event.shared.GwtEvent;

public class RepositionNodeEvent extends GwtEvent<RepositionNodeEventHandler>{

	public static Type<RepositionNodeEventHandler> TYPE = new Type<RepositionNodeEventHandler>();
	private DrawNode node;
	public RepositionNodeEvent(DrawNode node) {
		this.node = node;
	}
	
	public DrawNode getNode(){
		return this.node;
	}

	@Override
	public Type<RepositionNodeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RepositionNodeEventHandler handler) {
		handler.OnRepositionNode(this);
	}

}
