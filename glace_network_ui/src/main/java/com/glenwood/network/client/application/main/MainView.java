package com.glenwood.network.client.application.main;

import java.util.List;

import com.glenwood.network.client.application.domain.DrawNetwork;
import com.glenwood.network.client.application.domain.DrawNode;
import com.glenwood.network.client.application.domain.Network;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Widget;

public interface MainView {

	public interface Presenter {

		void goTo(Place place);

		void fetchNetworkData();

		void fireReDrawEvent();

		void drawCanvas();

		void promptPositionStatus();

		void savePosition(int selectedAlgorithm);

		Place currentPlace();

		void updateMoveHistory(int x, int y, DrawNode node);

		void saveDragHistory();

		void fetchNetworkInformation(int networkId);

	}

	Widget asWidget();

	void setPresenter(Presenter presenter);

	MainClient getClient();

	void showNetworkList(List<Network> networkList);

	void clearCanvas();

	int getY1();

	int getX1();

	int getSelectedAlgorithm();

	int getCanvasWidth();

	int getCanvasHeight();

	void displayCanvas(DrawNetwork drawNetwork);

	int getSelectedNetwork();

	void hideProgressBar();

	void showProgressBar();

	void clearProgress();

	void setCanvasDirty(boolean isCanvasDirty);

	boolean isCanvasDirty();

	void promptPositionSave(Integer response);

	boolean isAlgoChanged();

	void setAlgoChanged(boolean isAlgoChanged);

	void updateError();

	void setThumbnail(String imageString);

	void displayMap();

	void setMap(JSONObject mapProperties);

	void hideMap();

	void updateSuccess();

	void repositionNode(DrawNode node);

	void addHistory(Network response);

	void clearNavigationHistory();

}
