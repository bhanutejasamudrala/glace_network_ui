package com.glenwood.network.client.application.dragHistory;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class DragHistoryDesktopModule extends AbstractGinModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub

		bind(DragHistoryView.class).to(DragHistoryDesktopView.class).in(Singleton.class);
		bind(DragHistoryActivity.class);

	}

}