package com.glenwood.network.client.mvp;

import com.glenwood.network.client.application.addNetwork.AddNetworkActivity;
import com.glenwood.network.client.application.addNetwork.AddNetworkPlace;
import com.glenwood.network.client.application.avptemplate.AVPActivity;
import com.glenwood.network.client.application.avptemplate.AVPPlace;
import com.glenwood.network.client.application.dragHistory.DragHistoryActivity;
import com.glenwood.network.client.application.dragHistory.DragHistoryPlace;
import com.glenwood.network.client.application.run.RunActivity;
import com.glenwood.network.client.application.run.RunPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DesktopMainActivityMapper implements ActivityMapper {

	Provider<AVPActivity> avpActivityProvider;
	Provider<AddNetworkActivity> addNetworkProvider;
	Provider<DragHistoryActivity> dragHistoryProvider;
	Provider<RunActivity> runProvider;

	@Inject
	public DesktopMainActivityMapper(Provider<AVPActivity> avpActivityProvider, Provider<AddNetworkActivity> addNetworkProvider,
			Provider<DragHistoryActivity> dragHistoryActivity, Provider<RunActivity> runProvider) {
		super();
		this.avpActivityProvider = avpActivityProvider;
		this.addNetworkProvider = addNetworkProvider;
		this.dragHistoryProvider = dragHistoryActivity;
		this.runProvider = runProvider;
	}

	@Override
	public Activity getActivity(Place place) {

		if (place instanceof AVPPlace) {
			return avpActivityProvider.get();
		} else if (place instanceof AddNetworkPlace){
			return addNetworkProvider.get();
		} if(place instanceof DragHistoryPlace){
			return dragHistoryProvider.get();
		} if(place instanceof RunPlace){
			return runProvider.get();
		}

		return null;

	}

}
