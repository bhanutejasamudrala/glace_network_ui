package com.glenwood.network.client.application.avptemplate;

import com.google.gwt.user.client.ui.Widget;

public interface AVPView {

	Widget asWidget();

	void setPresenter(Presenter presenter);

	public interface Presenter {

	}

}
