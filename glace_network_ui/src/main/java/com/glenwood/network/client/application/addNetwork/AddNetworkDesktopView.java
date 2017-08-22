package com.glenwood.network.client.application.addNetwork;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AddNetworkDesktopView extends Composite implements AddNetworkView {

	private static AddDesktopViewUiBinder uiBinder = GWT.create(AddDesktopViewUiBinder.class);

	interface AddDesktopViewUiBinder extends UiBinder<Widget, AddNetworkDesktopView> {
	}

	private Presenter presenter;

	public AddNetworkDesktopView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

}
