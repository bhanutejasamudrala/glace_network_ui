package com.glenwood.network.client.application.addNetwork;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class AddNetworkActivity extends AbstractActivity implements AddNetworkView.Presenter {

	private AddNetworkView view;
	
	@Inject
	public AddNetworkActivity(AddNetworkView view) {
		this.view = view;
		this.view.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		panel.setWidget(view.asWidget());

	}

	public AddNetworkActivity withPlace(AddNetworkPlace place) {
		place.getToken();
		return this;
	}

}
