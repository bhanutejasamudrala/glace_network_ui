package com.glenwood.network.client.application.dragHistory;

import com.google.gwt.user.client.ui.Button;

import org.gwtbootstrap3.client.ui.Description;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.glenwood.network.client.application.domain.DrawNode;
import com.glenwood.network.client.event.NodeDragEvent;
import com.glenwood.network.client.resource.DesktopResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class DragHistoryDesktopView extends Composite implements DragHistoryView {

	private static DragHistoryDesktopViewUiBinder uiBinder = GWT.create(DragHistoryDesktopViewUiBinder.class);

	interface DragHistoryDesktopViewUiBinder extends UiBinder<Widget, DragHistoryDesktopView> {
	}

	private Presenter presenter;

	@UiField
	FlowPanel historyDataContainer;

	@UiField(provided=true)
	FlexTable historyTable;

	@UiField
	DesktopResources resources;
	
	@UiField
	Button saveHistory;
	
	@UiField
	FlowPanel historyDataBody;

	FlexTable historyTableHeader;
	FlexTable historyTableBody;
	ScrollPanel historyTableScroll;
	Description message = new Description();
	DescriptionData p = new DescriptionData();
	DragHistoryClient client = GWT.create(DragHistoryClient.class);

	public DragHistoryDesktopView() {
		initTable();
		initWidget(uiBinder.createAndBindUi(this));
		setStyles();
	}

	private void setStyles() {
		historyTableBody.setStyleName(resources.nodeDragStyles().dragHistoryTable());
		historyTable.getCellFormatter().setStyleName(0, 0, resources.nodeDragStyles().dragHistoryTableHeader());
		checkTableEmpty();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * To initialize the table for adding entries
	 * **/
	private void initTable(){
		historyTable = new FlexTable();
		historyTableHeader = new FlexTable();
		historyTableHeader.setHTML(0, 0, "Node Name");
		historyTableHeader.setHTML(0, 1, "X- Value");
		historyTableHeader.setHTML(0, 2, "Y- Value");
		historyTableHeader.setHTML(0, 4, "nodeId");
		historyTableHeader.getCellFormatter().setWidth(0, 0, "100px");
		historyTableHeader.getCellFormatter().setWidth(0, 1, "100px");
		historyTableHeader.getCellFormatter().setWidth(0, 2, "100px");
		historyTableHeader.getCellFormatter().setVisible(0, 3, false);
		historyTableHeader.getCellFormatter().setVisible(0, 4, false);
		historyTableHeader.setWidth("85%");
		historyTable.setWidget(0, 0, historyTableHeader);
		historyTableBody = new FlexTable();
		historyTableBody.setWidth("100%");
		historyTableScroll = new ScrollPanel();
		historyTableScroll.setHeight("300px");
		historyTableScroll.setWidth("100%");
		historyTableScroll.add(historyTableBody);
		historyTable.setWidget(1, 0, historyTableScroll);
		message.add(p);
	}
	
	@Override
	public DragHistoryClient getClient(){
		return client;
	}
	
	@UiHandler("saveHistory")
	public void saveHistory(ClickEvent eve){
		saveHistory();
	}
	
	@Override
	public void saveHistory(){
		if(historyTableBody.getRowCount()>0){
			JSONArray updateNodeList = new JSONArray();
			for(int i=1; i<historyTableBody.getRowCount(); i++){
				JSONObject updateNode = new JSONObject();
				JSONArray XYValues = new JSONArray();
				JSONObject XValue = new JSONObject();
				JSONObject YValue = new JSONObject();
				XValue.put( "X", new JSONString(historyTableBody.getHTML(i, 1)) );
				YValue.put( "Y", new JSONString(historyTableBody.getHTML(i, 2)) );
				XYValues.set(0, XValue);
				XYValues.set(1, YValue);
				updateNode.put( historyTableBody.getHTML(i, 4) , XYValues );
				updateNodeList.set(i-1, updateNode);
			}
			presenter.updateNodes(updateNodeList);
		}
	}

	/**
	 * To add or modify record in the table if there is no entity
	 * @param eve 
	 * **/
	@Override
	public void addRecord(NodeDragEvent eve) {
		final DrawNode node = eve.getDrawNode();
		final int rX = node.getXValue();
		final int rY = node.getYValue();
		final int x = eve.getX();
		final int y = eve.getY();
		final int rowCount = historyTableBody.getRowCount();
		node.setXValue(x);
		node.setYValue(y);
		for(int i = 0 ; i < rowCount; i++){
			if(node.getNodeId().equals(historyTableBody.getHTML(i, 4))){
				historyTableBody.setHTML(i, 1, x+"");
				historyTableBody.setHTML(i, 2, y+"");
				historyTableBody.getCellFormatter().setVisible(i, 4, false);
				return;
			}
		}
		
		Icon deleteButton = new Icon(IconType.REMOVE);
		deleteButton.setSize(IconSize.LARGE);
		historyTableBody.setHTML(rowCount, 0, node.getNodeName());
		historyTableBody.setHTML(rowCount, 1, node.getXValue()+"");
		historyTableBody.setHTML(rowCount, 2, node.getYValue()+"");
		historyTableBody.setWidget(rowCount, 3, deleteButton);
		historyTableBody.setHTML(rowCount, 4, node.getNodeId());

		historyTableBody.getCellFormatter().setWidth(rowCount, 0, "100px");
		historyTableBody.getCellFormatter().setWidth(rowCount, 1, "100px");
		historyTableBody.getCellFormatter().setWidth(rowCount, 2, "100px");
		historyTableBody.getCellFormatter().setWidth(rowCount, 3, "50px");

		historyTableBody.getCellFormatter().setVisible(rowCount, 4, false);
		displaHistory();

		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				event.getRelativeElement().getParentElement().getParentElement().removeFromParent();
				checkTableEmpty();
				node.setXValue(rX);
				node.setYValue(rY);
				presenter.repositionNode(node);
			}

		});

	}
	
	private void checkTableEmpty() {
		if( historyTableBody.getRowCount() ==0 ){
			historyTable.setVisible(false);
			saveHistory.setEnabled(false);
			p.setText("No changes made/ All changes saved");
			historyDataBody.setVisible(false);
			historyDataContainer.add(message);
		}
		
	}
	
	private void displaHistory(){
		historyTable.setVisible(true);
		saveHistory.setEnabled(true);
		historyDataBody.setVisible(true);
		historyDataContainer.remove(message);
	}

	@Override
	public void clearHistory() {
		historyTableBody.removeAllRows();
		checkTableEmpty();
	}

}
