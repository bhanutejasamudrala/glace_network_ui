package com.glenwood.network.client.application.avptemplate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AVPDesktopView extends Composite implements AVPView {

	private static AVPDesktopViewUiBinder uiBinder = GWT
			.create(AVPDesktopViewUiBinder.class);

	interface AVPDesktopViewUiBinder extends UiBinder<Widget, AVPDesktopView> {
	}

	private Presenter presenter;

	public AVPDesktopView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

}
