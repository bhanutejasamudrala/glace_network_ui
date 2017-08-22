package com.glenwood.network.client.application.dragHistory;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;

import com.glenwood.network.client.application.domain.DrawNode;
import com.glenwood.network.client.event.NodeDragEvent;
import com.glenwood.network.client.event.NodeDragEventHandler;
import com.glenwood.network.client.event.RepositionNodeEvent;
import com.glenwood.network.client.event.SaveDragHisoryEvent;
import com.glenwood.network.client.event.SaveDragHistoryEventHandler;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class DragHistoryActivity extends AbstractActivity implements DragHistoryView.Presenter {

	private DragHistoryView view;
	private EventBus eventBus;
	
	@Inject
	public DragHistoryActivity(DragHistoryView view, EventBus eventBus) {
		this.view = view;
		this.view.setPresenter(this);
		this.eventBus = eventBus;
		addHandlers();
	}

	/**
	 * Method to add handler to event bus
	 * **/
	private void addHandlers() {
		eventBus.addHandler(NodeDragEvent.TYPE, new NodeDragEventHandler() {
			@Override
			public void makeRocord(NodeDragEvent eve) {
				view.addRecord(eve);
			}
		});
		
		eventBus.addHandler(SaveDragHisoryEvent.TYPE, new SaveDragHistoryEventHandler() {
			
			@Override
			public void saveHistory(SaveDragHisoryEvent saveDragHisoryEvent) {
				saveDragHistory();
			}
		});
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				eventBus.fireEvent(new SaveDragHisoryEvent());
			}
		});
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

	public DragHistoryActivity withPlace(DragHistoryPlace place) {
		place.getToken();
		return this;
	}
	
	/**
	 * Method to prompt view to save history
	 * **/
	protected void saveDragHistory() {
		view.saveHistory();
	}
	
	/**
	 * To reposition the node that was moved to its original position
	 * **/
	@Override
	public void repositionNode(DrawNode node) {
		eventBus.fireEvent(new RepositionNodeEvent(node));
	}

	/**
	 * To update node xy values in DB
	 * **/
	@Override
	public void updateNodes(JSONArray nodeList) {
		view.getClient().saveHistory(nodeList, new JsonCallback() {
			
			@Override
			public void onSuccess(Method method, JSONValue response) {
				Window.alert("Successfully saved");
				view.clearHistory();
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Failure");				
			}
		});		
	}

}
