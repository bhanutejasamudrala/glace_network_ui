package com.glenwood.network.client;

import org.fusesource.restygwt.client.Defaults;

import com.glenwood.network.client.application.main.ActionServletURL;
import com.glenwood.network.client.ioc.ClientGinjector;
import com.glenwood.network.client.ioc.GinjectorProvider;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Network implements EntryPoint {
	String baseUrl = null;
	public static final ClientGinjector ginjector = ((GinjectorProvider) GWT.create(GinjectorProvider.class)).get();
	ActionServletURL actionServletURL;

	

	/**
	 * This is the entry point method.
	 * 
	 * @return
	 */
	public void onModuleLoad() {
		
		ginjector.getCSSInjector().injectCSS();
		actionServletURL = ginjector.getActionServletURL();
		// Setting the redirect servlet url for AJAX request
		baseUrl = "http://localhost/NetworkREST/rest";
		actionServletURL.setBaseURL(baseUrl);
		Defaults.setServiceRoot(baseUrl);
		ginjector.getMainPresenter().go(RootLayoutPanel.get());
		ginjector.getPlaceHistoryHandler().handleCurrentHistory();
		
		
	}
}