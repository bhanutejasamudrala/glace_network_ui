package com.glenwood.network.client.event;

import com.glenwood.network.client.application.domain.DrawNode;
import com.google.gwt.event.shared.GwtEvent;

public class NodeDragEvent extends GwtEvent<NodeDragEventHandler>{

	public static Type<NodeDragEventHandler> TYPE = new Type<NodeDragEventHandler>();
	int ex, ey;
	DrawNode node;
	
	public NodeDragEvent(int ex, int ey, DrawNode node){
		this.ex = ex;
		this.ey = ey;
		this.node = node;
	}
	
	@Override
	public Type<NodeDragEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NodeDragEventHandler handler) {
		handler.makeRocord(this);
	}
	
	public DrawNode getDrawNode(){
		return node;
	}
	
	public int getX(){
		return ex;
	}
	
	public int getY(){
		return ey;
	}

}
