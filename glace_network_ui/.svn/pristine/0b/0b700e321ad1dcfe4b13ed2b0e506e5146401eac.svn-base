package com.glenwood.network.client.application.avptemplate;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class AVPActivity extends AbstractActivity implements AVPView.Presenter {

	private AVPView view;
	
	@Inject
	public AVPActivity(AVPView view) {
		this.view = view;
		this.view.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		panel.setWidget(view.asWidget());

	}

	public AVPActivity withPlace(AVPPlace place) {
		place.getToken();
		return this;
	}

}
