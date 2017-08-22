package com.glenwood.network.client.application.dragHistory;

import com.glenwood.network.client.application.domain.DrawNode;
import com.glenwood.network.client.event.NodeDragEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;

public interface DragHistoryView {

	Widget asWidget();

	void setPresenter(Presenter presenter);

	void addRecord(NodeDragEvent eve);
	
	public interface Presenter {

		void repositionNode(DrawNode node);

		void updateNodes(JSONArray updateNodeList);

	}

	DragHistoryClient getClient();

	void clearHistory();

	void saveHistory();

}
