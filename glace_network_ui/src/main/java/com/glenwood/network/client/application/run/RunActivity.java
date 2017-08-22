package com.glenwood.network.client.application.run;

import java.util.HashMap;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.glenwood.network.client.application.domain.Node;
import com.glenwood.network.client.customwidgets.selectoionControl.SelectionControl;
import com.glenwood.network.client.service.RpcCallServiceAsync;
import com.glenwood.network.client.service.RpcInit;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class RunActivity extends AbstractActivity implements RunView.Presenter {

	private RunView view;
	
	private RpcCallServiceAsync rpc;
	
	@Inject
	public RunActivity(RunView view) {
		this.view = view;
		this.view.setPresenter(this);
		rpc = RpcInit.initRpc();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
	}

	public RunActivity withPlace(RunPlace place) {
		place.getToken();
		return this;
	}

	/**
	 * remote procedure call to the server to fetch input node from uploaded file
	 * @param fileFieldName
	 * @param mode
	 **/
	@Override
	public void fetchNodeList(final String fileFieldName) {
		Storage storage = getStorage();
		String networkId = storage.getItem("networkId");
		if(networkId == null){
			Window.alert("Please select a network"); 
			return;
		}
		rpc.fetchInputNodeList(fileFieldName, networkId, new AsyncCallback<HashMap<Integer, String>>() {

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				view.removeFileFieldNames(fileFieldName);
			}

			@Override
			public void onSuccess(HashMap<Integer, String> result) {
				view.setInputist(result);
				view.removeFileFieldNames(fileFieldName);
			}

		});
	}
	
	/**
	 * Method to get the node list of variable type
	 */
	@Override
	public void fetchNodeList(String searchKeyWord, int offset, final SelectionControl ctrl) {
		Storage storage = getStorage();
		String networkId = storage.getItem("networkId");
		view.getClient().fetchNodeList(networkId, offset+"", searchKeyWord, "V", new MethodCallback<List<Node>>() {
			
			@Override
			public void onSuccess(Method method, List<Node> response) {
				view.seinputList(ctrl, response);
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Failure in fetching nodes");
			}
		});
	}
	
	/**
	 * Method to get storage
	 * **/
	private Storage getStorage(){
		Storage storage = Storage.getSessionStorageIfSupported();
		if(storage == null){
			Window.alert("Your browser donot support local storage.. Please change your browser");
		}
		return storage;
	}



}
