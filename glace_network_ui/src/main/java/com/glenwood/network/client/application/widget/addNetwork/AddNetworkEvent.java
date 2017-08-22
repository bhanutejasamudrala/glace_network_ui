package com.glenwood.network.client.application.widget.addNetwork;

import com.google.gwt.event.shared.GwtEvent;

public class AddNetworkEvent extends GwtEvent<AddNetworkHandler>{

	public static Type<AddNetworkHandler> TYPE = new Type<AddNetworkHandler>();
	private String networkName;
	private String fileName;
	
	@Override
	protected void dispatch(AddNetworkHandler handler) {
		handler.onUpload(this);
	}

	@Override
	public Type<AddNetworkHandler> getAssociatedType() {
		return TYPE;
	}
	

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
