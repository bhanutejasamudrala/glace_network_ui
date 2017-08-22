package com.glenwood.network.client.ioc;

import javax.inject.Singleton;

import com.glenwood.network.client.application.addNetwork.AddNetworkDesktopModule;
import com.glenwood.network.client.application.avptemplate.AVPDesktopModule;
import com.glenwood.network.client.application.dragHistory.DragHistoryDesktopModule;
import com.glenwood.network.client.application.main.ActionServletURL;
import com.glenwood.network.client.application.main.ActionServletURLImpl;
import com.glenwood.network.client.application.main.MainDesktopModule;
import com.glenwood.network.client.application.run.RunDesktopModule;
import com.glenwood.network.client.mvp.DesktopMvpModule;
import com.glenwood.network.client.resource.CSSInjector;
import com.glenwood.network.client.resource.DesktopCSSInjector;
import com.glenwood.network.client.resource.DesktopResources;
import com.google.gwt.inject.client.AbstractGinModule;


public class DesktopModule extends AbstractGinModule{

	@Override
	protected void configure() {
		install(new DesktopMvpModule());
		install(new MainDesktopModule());
		install(new AVPDesktopModule());
		install(new AddNetworkDesktopModule());
		install(new DragHistoryDesktopModule());
		install(new RunDesktopModule());
		bind(DesktopResources.class).in(Singleton.class);
		bind(CSSInjector.class).to(DesktopCSSInjector.class).in(Singleton.class);
		bind(ActionServletURL.class).to(ActionServletURLImpl.class).in(Singleton.class);
	}
}

