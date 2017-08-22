package com.glenwood.network.client.resource;

import com.google.inject.Inject;

public class DesktopCSSInjector implements CSSInjector {

	@Inject
	DesktopResources resources;

	public void injectCSS() {
		resources.desktopstyles().ensureInjected();
		resources.windowstyles().ensureInjected();
		resources.nodeDragStyles().ensureInjected();
		resources.runpanelstyles().ensureInjected();
		resources.selectionControl().ensureInjected();
	}
	
}
