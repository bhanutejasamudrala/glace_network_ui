package com.glenwood.network.client.application.run;

import java.util.HashMap;
import java.util.List;

import com.glenwood.network.client.application.domain.Node;
import com.glenwood.network.client.customwidgets.selectoionControl.SelectionControl;
import com.google.gwt.user.client.ui.Widget;

public interface RunView {

	Widget asWidget();

	void setPresenter(Presenter presenter);

	public interface Presenter {

		void fetchNodeList(String fileFieldName);

		void fetchNodeList(String searchKeyWord, int offset, SelectionControl ctrl);

	}

	RunClient getClient();

	void setInputist(HashMap<Integer, String> result);

	void removeFileFieldNames(String name);

	void seinputList(SelectionControl ctrl, List<Node> response);

}
