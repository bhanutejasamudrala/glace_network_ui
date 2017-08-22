package com.glenwood.network.client.application.main;

import java.util.List;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.glenwood.network.client.application.domain.DrawNetwork;
import com.glenwood.network.client.application.domain.DrawNode;
import com.glenwood.network.client.application.domain.Network;
import com.glenwood.network.client.event.NodeDragEvent;
import com.glenwood.network.client.event.ReDrawEvent;
import com.glenwood.network.client.event.ReDrawEventHandler;
import com.glenwood.network.client.event.RepositionNodeEvent;
import com.glenwood.network.client.event.RepositionNodeEventHandler;
import com.glenwood.network.client.event.SaveDragHisoryEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author software
 *
 */
public class MainPresenter implements Presenter, MainView.Presenter {

	public MainView view;
	private PlaceController placeController;
	private EventBus eventBus;
	@Inject
	ActionServletURL actionServletURL;

	@Inject
	public MainPresenter(MainView view, PlaceController placeController, EventBus eventBus, ActionServletURL actionServletURL) {
		actionServletURL.getBaseURL();
		this.actionServletURL = actionServletURL;
		this.eventBus = eventBus;
		this.placeController = placeController;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		eventBus.addHandler(ReDrawEvent.TYPE, new ReDrawEventHandler() {
			
			@Override
			public void Onredraw(ReDrawEvent reDrawEvent) {
				view.clearCanvas();
			}
		});
		
		eventBus.addHandler(RepositionNodeEvent.TYPE, new RepositionNodeEventHandler() {
			
			@Override
			public void OnRepositionNode(RepositionNodeEvent eve) {
				view.repositionNode(eve.getNode());
			}
		});
		bind();

	}

	@Override
	public void goTo(Place place) {
		placeController.goTo(place);
	}

	@Override
	public void bind() {

	}

	@Override
	public Widget getWidget() {
		return view.asWidget();
	}

	@Override
	public void stop() { }
		
	/**
	 * Method to fire event to draw network
	 * **/
	@Override
	public void fireReDrawEvent() {
		view.clearProgress();
		view.showProgressBar();
		eventBus.fireEvent(new ReDrawEvent());
	}
	
	/**
	 * To get the current place 
	 */
	@Override
	public Place currentPlace(){
		return placeController.getWhere();
	}

	/**
	 * Method to draw canvas
	 * **/
	@Override
	public void drawCanvas() {
		Storage storage = getStorage();
		String networkId = storage.getItem("networkId");
		String startNodeId = storage.getItem("startNodeId");
		String activeNetworkId = storage.getItem("activeNetworkId");
		int algorithmSelected = view.getSelectedAlgorithm();
		int width = view.getCanvasWidth();
		int height = view.getCanvasHeight();
		loadNetworkOnCanvas(networkId, startNodeId, algorithmSelected, view.getX1(), view.getY1(), width, height, activeNetworkId);
	}
	
	/**
	 * Method call to fetch network list that are available
	 * **/
	public void fetchNetworkData() {

		view.getClient().getNetworkList(new MethodCallback<List<Network>>() {
			
			@Override
			public void onSuccess(Method method,  List<Network> networkList) {
				view.showNetworkList(networkList);
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Error in getting network list");
			}
		});
	}
	
	/**
	 * Method to load network on canvas
	 * @param networkId
	 * @param startNodeId
	 * @param algorithmSelected
	 * @param x1
	 * @param y1
	 * @param height 
	 * @param width 
	 * @param activeNetworkId
	 */
	private void loadNetworkOnCanvas(final String networkId, String startNodeId,final int algorithmSelected, int x1, int y1,final int width,final int height, String activeNetworkId) {
		view.getClient().getVisualizationData(startNodeId, networkId, algorithmSelected, x1, y1, width, height, activeNetworkId, new MethodCallback<DrawNetwork>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				view.hideProgressBar();
				view.clearNavigationHistory();
				Window.alert("Error occored in visualizing network...");
			}

			@Override
			public void onSuccess(Method method, DrawNetwork drawNetwork) {
				if(drawNetwork == null){
					view.hideProgressBar();
					view.clearNavigationHistory();
					Window.alert("Error occored in visualizing network...");
					
				} else {
					view.displayCanvas(drawNetwork);
					view.clearNavigationHistory();
					loadSummary(networkId, algorithmSelected, width, height);
				}
			}
		});
	}
	
	/**
	 * Method to load summary of the network 
	 * @param height 
	 * @param width 
	 * @param algorithmSelected 
	 * @param networkId 
	 * **/
	private void loadSummary(String networkId, int algorithmSelected, int width, int height){
		if(view.isCanvasDirty()){
			view.setCanvasDirty(false);
			view.getClient().getDisplayImage(networkId, algorithmSelected, width, height, new JsonCallback() {
				
				@Override
				public void onSuccess(Method method, JSONValue response) {
					JSONObject mapProperties = JSONParser.parseStrict(response.toString().replaceAll(":\"", ":").replaceAll("\"}", "}").replaceAll("\",", ",").replaceAll("~~~", "\"")).isObject();
					view.setThumbnail(mapProperties.get("image").toString().substring(1, mapProperties.get("image").toString().length()-1));
					view.displayMap();
					view.setMap(mapProperties);
				}
				
				@Override
				public void onFailure(Method method, Throwable exception) {

				}
			
			});
		}
	}
	
	/**
	 * TO check for the position status if graph has default x, y coordinates set to it
	 * @param networkId
	 * **/
	public void promptPositionStatus() {
		int networkId = Integer.parseInt(getStorage().getItem("networkId"));
		view.getClient().promptPositionStatus(networkId, new MethodCallback<Integer>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				
			}

			@Override
			public void onSuccess(Method method, Integer response) {
				view.promptPositionSave(response);
			}
		});
	}
	
	private Storage getStorage(){
		Storage storage = Storage.getSessionStorageIfSupported();
		if(storage == null){
			Window.alert("Your browser donot support local storage.. Please change your browser");
		}
		return storage;
	}
	
	/**
	 * Method to save positions of x,y values of nodes with the selected algorithm
	 * here we are sending width and height of the viewport to assist the snow-flake algorithm if used
	 * @param selectedAlgorithm
	 * 
	 * **/
	@Override
	public void savePosition(int selectedAlgorithm) {
		String networkId  = getStorage().getItem("networkId");
		Integer width = view.getX1();
		Integer height = view.getY1();
		view.getClient().savePositions(networkId, selectedAlgorithm, width, height, new MethodCallback<String>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				view.updateError();
			}

			@Override
			public void onSuccess(Method method, String response) {
				view.updateSuccess();
			}
			
		});
	}

	/**
	 * To fire event when node is dragged
	 * **/
	@Override
	public void updateMoveHistory(int x, int y, DrawNode node) {
		eventBus.fireEvent(new NodeDragEvent(x, y, node));
	}

	/**
	 * To fire event to save node drag history
	 * **/
	@Override
	public void saveDragHistory() {
		eventBus.fireEvent(new SaveDragHisoryEvent());	
	}
	
	/**
	 * Method to fetch network information, given networkId
	 * @param networkId
	 * **/
	@Override
	public void fetchNetworkInformation(int networkId) {
		
		view.getClient().getNetworkInfo(networkId, new MethodCallback<Network>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Failure in navigating to network");
			}

			@Override
			public void onSuccess(Method method, Network response) {
				Storage storage = getStorage();
				String networkId = storage.getItem("networkId");
				String activeNetworkId = storage.getItem("activeNetworkId");
				int algorithmSelected = view.getSelectedAlgorithm();
				view.addHistory(response);
				int width = view.getCanvasWidth();
				int height = view.getCanvasHeight();
				loadNetworkOnCanvas(networkId, response.getStartNodeid(),algorithmSelected, view.getX1(), view.getY1(), width, height, activeNetworkId);
			}
		});
		
	}

}
