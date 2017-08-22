package com.glenwood.network.client.ioc;

import com.glenwood.network.client.application.main.ActionServletURL;
import com.glenwood.network.client.application.main.MainPresenter;
import com.glenwood.network.client.resource.CSSInjector;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;

public interface ClientGinjector extends Ginjector {
	PlaceHistoryHandler getPlaceHistoryHandler();

	CSSInjector getCSSInjector();

	MainPresenter getMainPresenter();

	EventBus getEventBus();

	ActionServletURL getActionServletURL();
	
}
