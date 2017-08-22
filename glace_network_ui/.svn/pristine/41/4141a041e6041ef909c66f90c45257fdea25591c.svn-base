package com.glenwood.network.client.application.main;

import java.util.List;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Progress;
import org.gwtbootstrap3.client.ui.ProgressBar;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.ProgressBarType;
import org.gwtbootstrap3.client.ui.constants.ProgressType;

import com.glenwood.network.client.application.domain.DrawNetwork;
import com.glenwood.network.client.application.domain.DrawNode;
import com.glenwood.network.client.application.domain.Network;
import com.glenwood.network.client.application.dragHistory.DragHistoryPlace;
import com.glenwood.network.client.application.run.RunPlace;
import com.glenwood.network.client.application.widget.draggable.SimpleWidget;
import com.glenwood.network.client.application.widget.draggable.SimpleWidgetDragController;
import com.glenwood.network.client.customwidgets.WindowController;
import com.glenwood.network.client.customwidgets.WindowPanel;
import com.glenwood.network.client.resource.DesktopResources;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MainDesktopView extends Composite implements MainView {

	private static MainDesktopViewUiBinder uiBinder = GWT.create(MainDesktopViewUiBinder.class);

	interface MainDesktopViewUiBinder extends UiBinder<Widget, MainDesktopView> {
	}

	@UiField
	SimplePanel mainDisplayPanel;
	
	@UiField
	FlowPanel vizPanel;

	@UiField
	Label networkSelector;

	@UiField
	DesktopResources resources;

	@UiField
	Button createButton;

	@UiField
	Button addButton;

	@UiField
	Button editButton;

	@UiField
	FlowPanel addMenu;

	@UiField
	FlowPanel editMenu;

	@UiField
	FlowPanel addEditMenu;
	
	@UiField
	FlowPanel algorithmWrap;

	@UiField
	Label addArrow;

	@UiField
	Label editArrow;

	@UiField
	Button snowFlake;
	
	@UiField
	Button circular;
	
	@UiField
	Button tree;
		
	@UiField
	Button circuit;
	
	@UiField
	Button wheel;

	@UiField
	Button vizWindowControl;
	
	@UiField
	Button algorithmSelectionButton;
	
	@UiField
	Button configureButton;
	
	@UiField
	Label algorithmName;
	
	@UiField
	Label graphMoveLabel;
	
	@UiField
	AnchorListItem enviewNetwork;
	
	@UiField
	AnchorListItem eneditNetwork;
	
	@UiField
	AnchorListItem ennodeDrag;
	
	@UiField
	Anchor networkModeSelector;
	
	@UiField
	DropDownMenu networkModeSelectionMenu;
	
	@UiField
	AbsolutePanel bodyWrap;
	
	@UiField
	FlowPanel networkNavHistory;
	
	private MainClient mainClient;
	private Presenter presenter;
	PopupPanel networkListPopup = new PopupPanel();
	PopupPanel nodeMenu = new PopupPanel();
	private int x1 = 0, y1 = 0;
	private int selectedAlgorithm = 1; //TODO remove after adding network selection module
	private int width = 0, height=0;
	private DrawNetwork drawNetwork;
	private int selectedNetwork;
	public Progress loadProgress = new Progress();
	public PopupPanel graphLegend = new PopupPanel();
	private boolean isCanvasDirty = false;
	private boolean freeHandMode = false;
	private boolean isAlgoChanged = false;
	private int graphMode = 1; 
	private JSONObject mapProperties;
	
	@Inject
	public MainDesktopView(ActivityMapper mainActivityMapper, ActivityManager mainActivityManager) {

		initWidget(uiBinder.createAndBindUi(this));
		mainClient = GWT.create(MainClient.class);
		mainDisplayPanel.getElement().getStyle().setHeight(Window.getClientHeight() - 43, Unit.PX);
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				mainDisplayPanel.getElement().getStyle().setHeight(Window.getClientHeight() - 43, Unit.PX);

			}
		});
		mainActivityManager.setDisplay(mainDisplayPanel);
		injectJS();
		setStyles();
		Storage storage = getStorage();
		storage.clear();
	}
	
	private void injectJS() {
		if(!isInjucte()) {
			ScriptInjector.fromString(resources.jqueryUI().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			ScriptInjector.fromString(resources.fabricJS().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			ScriptInjector.fromString(resources.canvasEditor().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			ScriptInjector.fromString(resources.fabricViewPort().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
	}
	
	
	/**JS Native methods to check if Js is injected or not.**/

	/**To check if code is injected **/
	private native final boolean isInjucte()/*-{
		if (!(typeof $wnd.fabric === "undefined") && !(null===$wnd.fabric)) {
				return true;
			}
			return false;
	}-*/ ;
	
	
	@Override
	public int getX1(){
		return x1;
	}
	
	@Override 
	public int getY1(){
		return y1;
	}
	
	@Override
	public int getCanvasWidth() {
		return width;
	}

	@Override
	public int getCanvasHeight() {
		return height;
	}
	
	@Override
	public int getSelectedAlgorithm() {
		return selectedAlgorithm;
	}
	
	@Override
	public int getSelectedNetwork(){
		return selectedNetwork;
	}
	
	@Override
	public boolean isCanvasDirty(){
		return isCanvasDirty;
	}
	
	@Override
	public void setCanvasDirty(boolean isCanvasDirty){
		this.isCanvasDirty = isCanvasDirty;
	}
	
	@Override
	public boolean isAlgoChanged() {
		return isAlgoChanged;
	}
	
	@Override
	public void setAlgoChanged(boolean isAlgoChanged) {
		this.isAlgoChanged = isAlgoChanged;
	}
	
	/**
	 * Methods to clear canvas when redraw event occurred
	 * **/
	@Override
	public void clearCanvas() {
		eraseCanvas();
		presenter.drawCanvas();
	};

	public static native void eraseCanvas() /*-{
		$wnd.clearCanvas();
	}-*/;


	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public MainClient getClient(){
		return mainClient;
	}

	public Widget asWidget() {
		return this;
	}

	public void setStyles(){

		networkListPopup.addStyleName(resources.desktopstyles().networkListPopup());
		graphLegend.addStyleName(resources.desktopstyles().graphPopup());
		addEditMenu.setVisible(false);
		algorithmWrap.setVisible(false);
	}
	
	/**
	 * UI Handlers
	 * **/
	@UiHandler("vizWindowControl")
	public void resizeVisualizationPanel(ClickEvent e){
		if(mainDisplayPanel.isVisible()){
			if(freeHandMode){
				return;
			}
			mainDisplayPanel.setVisible(false);
			vizPanel.setWidth("100%");
			setCanvasDirty(true);
		} else {
			setCanvasDirty(true);
			mainDisplayPanel.setVisible(true);
			vizPanel.setWidth("50%");
		}
		resizeCanvas();
	}
	
	@UiHandler("networkSelector")
	public void onNetworkMenuClick(ClickEvent e){
		presenter.fetchNetworkData();
	}

	@UiHandler("addButton")
	public void onAddClick(ClickEvent e){
		if(addMenu.isVisible()){
			addMenu.setVisible(false);
			addArrow.removeStyleName(resources.desktopstyles().roatate());
		} else {
			addMenu.setVisible(true);
			addArrow.addStyleName(resources.desktopstyles().roatate());
		}
		
	}

	@UiHandler("editButton")
	public void onClick(ClickEvent e){
		if(editMenu.isVisible()){
			editMenu.setVisible(false);
			editArrow.removeStyleName(resources.desktopstyles().roatate());
		} else {
			editMenu.setVisible(true);
			editArrow.addStyleName(resources.desktopstyles().roatate());
		}
	}

	@UiHandler("createButton")
	public void onClickCreateButton(ClickEvent e){
		if(addEditMenu.isVisible()){
			addEditMenu.setVisible(false);
		} else {
			addEditMenu.setVisible(true);
		}
	}
	
	@UiHandler("stockModeButton")
	public void enableFreeHand(ClickEvent e){
		if(freeHandMode){
			networkNavHistory.setVisible(false);
			freeHandMode = false;
			Storage storage = getStorage();
			selectedAlgorithm = Integer.parseInt(storage.getItem("from_algo"));
			storage.removeItem("from_algo");
			algorithmSelectionButton.setEnabled(true);
			graphMoveLabel.setText("Stock Mode");
			ennodeDrag.setVisible(false);
			networkModeSelector.setText("View Network");
			networkModeSelector.setIcon(IconType.EYE);
			enableDragViewport();
			
		} else {
			if(!mainDisplayPanel.isVisible()){
				Window.alert("Please minimize your visualization area..");
				return;
			}
			selectedAlgorithm = -1;
			freeHandMode = true;
			Storage storage = getStorage();
			storage.setItem("from_algo", selectedAlgorithm+"");
			algorithmWrap.setVisible(false);
			algorithmSelectionButton.setEnabled(false);
			graphMoveLabel.setText("Free Hand");
			ennodeDrag.setVisible(true);
			graphMode = 3;
			networkModeSelector.setText("Drag Node");
			algorithmName.setText("Free hand");
			networkModeSelector.setIcon(IconType.ADJUST);
			presenter.promptPositionStatus();
		}
	}
	

	@UiHandler("algorithmSelectionButton")
	public void selectAlgorithm(ClickEvent e){
		Storage storage = getStorage();
		
		if(storage.getItem("networkId") == null){
			Window.alert("Please select network first");
		} else {
			if(algorithmWrap.isVisible()){
				algorithmWrap.setVisible(false);
			} else {
				algorithmWrap.setVisible(true);
			}
		}
	}
	
	@UiHandler("snowFlake")
	public void selectSnowFlake(ClickEvent e){
		getStorage().setItem("activeNetworkId", getStorage().getItem("networkId"));
		isCanvasDirty = true;
		algorithmWrap.setVisible(false);
		algorithmName.setText(snowFlake.getText());
		selectedAlgorithm = 0;
		presenter.fireReDrawEvent();
	}
	
	@UiHandler("circular")
	public void selectCircular(ClickEvent e){
		getStorage().setItem("activeNetworkId", getStorage().getItem("networkId"));
		isCanvasDirty = true;
		algorithmWrap.setVisible(false);
		algorithmName.setText(circular.getText());
		selectedAlgorithm = 1;
		presenter.fireReDrawEvent();
	}
	
	@UiHandler("tree")
	public void selectTree(ClickEvent e){
		getStorage().setItem("activeNetworkId", getStorage().getItem("networkId"));
		isCanvasDirty = true;
		algorithmWrap.setVisible(false);
		algorithmName.setText(tree.getText());
		selectedAlgorithm = 2;
		presenter.fireReDrawEvent();
	}
	
	@UiHandler("circuit")
	public void selectCircuit(ClickEvent e){
		resetnetworkNavHistory();
		getStorage().setItem("activeNetworkId", getStorage().getItem("networkId"));
		isCanvasDirty = true;
		algorithmWrap.setVisible(false);
		algorithmName.setText(circuit.getText());
		selectedAlgorithm = 3;
		presenter.fireReDrawEvent();
	}
	
	@UiHandler("wheel")
	public void selectWheel(ClickEvent e){
		getStorage().setItem("activeNetworkId", getStorage().getItem("networkId"));
		isCanvasDirty = true;
		algorithmWrap.setVisible(false);
		algorithmName.setText(wheel.getText());
		selectedAlgorithm = 4;
		presenter.fireReDrawEvent();
	}
	
	@UiHandler("enviewNetwork")
	public void enableViewNetwork(ClickEvent e){
		graphMode = 1;
		isCanvasDirty = true;
		networkModeSelector.setText("View Network");
		networkModeSelector.setIcon(IconType.EYE);
		if(freeHandMode){
			//TODO save the node parameters if changed
			presenter.saveDragHistory();
		}
		enableDragViewport();
		
	}
	
	@UiHandler("eneditNetwork")
	public void enableEditNetwork(ClickEvent e){
		graphMode = 2;
		isCanvasDirty = true;
		networkModeSelector.setText("Edit Network");
		networkModeSelector.setIcon(IconType.EDIT);
		if(freeHandMode){
			//TODO save the node parameters if changed
			presenter.saveDragHistory();
		}
		enableGraphEdit();
		
	}
	
	@UiHandler("ennodeDrag")
	public void ennodeDrag(ClickEvent e){
		graphMode = 3;
		networkModeSelector.setText("Drag Node");
		networkModeSelector.setIcon(IconType.ADJUST);
		allowNodeDrag();
		enableNodeDrag();
	}
	
	@UiHandler("configureButton")
	public void configure(ClickEvent e){
		Window.alert("Configure node");
	}
	
	@UiHandler("runButton")
	public void run(ClickEvent e){
		if(!History.getToken().contains("run")){
			presenter.goTo(new RunPlace("run"));
		}
	}
	
	/**To set view-port drag enable**/
	private native final void enableDragViewport()/*-{
		$wnd.enableDragViewport();
	}-*/;
	private native final void enableNodeDrag()/*-{
		$wnd.enableNodeDrag();
	}-*/;
	private native final void enableGraphEdit()/*-{
		$wnd.enableEditNode();
	}-*/;
	
	/**
	 * Method to get network drop down
	 * @param networkList
	 */
	@Override
	public void showNetworkList( List<Network> networkList) {
		networkListPopup.clear();

		ScrollPanel networkListScroll = new ScrollPanel();
		networkListScroll.setHeight("200px");
		networkListScroll.setAlwaysShowScrollBars(false);
		networkListScroll.addStyleName(resources.desktopstyles().networkItemContainer());
		VerticalPanel networkListWrapper = new VerticalPanel();

		for(int i = 0 ; i < networkList.size() ; i++) {
			final Network n = networkList.get(i);
			FocusPanel fp = new FocusPanel();
			fp.addStyleName(resources.desktopstyles().networkItem());
			fp.add(new Label(n.getName()));
			fp.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					isCanvasDirty = true;
					networkSelector.setText(n.getName());
					networkListPopup.clear();
					networkListPopup.hide();
					
					Storage storage = getStorage();
					x1 = 0;
					y1 = 0;
					resetViewportPosition();
					algorithmName.setText(snowFlake.getText());
					selectedAlgorithm = 0;
					if(storage != null) {
						storage.clear();
						storage.setItem("networkId", n.getIdNetwork()+"");
						storage.setItem("networkName", n.getName());
						storage.setItem("startNodeId", n.getStartNodeid());
						storage.setItem("activeNetworkId", n.getIdNetwork()+"");
						if(width == 0 && height == 0){
							resizeCanvas();
						} else{
							presenter.fireReDrawEvent();
						}
						
					}
				}
			});
			networkListWrapper.add(fp);
		}
		networkListScroll.add(networkListWrapper);
		networkListPopup.add(networkListScroll);
		networkListPopup.showRelativeTo(networkSelector);
		networkListPopup.setAutoHideEnabled(true);

	}
	
	
	/**
	 * To resize canvas size
	 * **/
	private void resizeCanvas(){
		String dimensionString = setDimensions();
		width = getDimensions(dimensionString)[0];
		height = getDimensions(dimensionString)[1];
		presenter.fireReDrawEvent();
	}
	
	/**JSNI method to reset viewport positions**/
	public final native void resetViewportPosition() /*-{
		$wnd.resetViewportPosition();
	}-*/;
	
	
	/**
	 * To get the dimension in integer array
	 * **/
	private int[] getDimensions(String dimensionString) {
		String[] dimension = dimensionString.split(":");
		int[] arr = { Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]) };
		return arr;
	}
	
	/**
	 * Method to call draw canvas
	 * **/
	@Override
	public void displayCanvas(DrawNetwork drawNetwork) {
		Storage storage = getStorage();
		this.drawNetwork = drawNetwork;
		JavaScriptObject network = asDrawNetwork(drawNetwork.toString()).get(0);
		drawCanvas(network , this, storage.getItem("startNodeId"), Integer.parseInt(storage.getItem("networkId")), selectedAlgorithm, this);
		
		if(freeHandMode && graphMode==3) {
			allowNodeDrag();
			enableNodeDrag();
		}
	}

	/**
	 * Method to draw graph
	 * @param networkId 
	 * **/
	native void drawCanvas(JavaScriptObject network, MainDesktopView mainDesktopView, String startNodeId, int networkId, int networkAlgorithm, MainDesktopView view) /*-{
		var nodeList = network.nodeList;
		var linkList = network.linkList;
	
		$wnd.generatePopup = $entry(function(details){
		});
		$wnd.saveNodes = $entry(function(){
		});
		$wnd.showPopup = $entry(function(details){
			view.@com.glenwood.network.client.application.main.MainDesktopView::showTooltip(Ljava/lang/String;)(details);
		});
		$wnd.hidePopup = $entry(function(){
			view.@com.glenwood.network.client.application.main.MainDesktopView::hideTooltip()();
		});
		$wnd.changeviewport = $entry(function(x, y) {
			view.@com.glenwood.network.client.application.main.MainDesktopView::changeXY(II)(x,y);
		});
		$wnd.clearProgress = $entry(function(){
			view.@com.glenwood.network.client.application.main.MainDesktopView::clearProgress();
		});
		$wnd.setProgress = $entry(function(currentProgress, totalProgress){
			view.@com.glenwood.network.client.application.main.MainDesktopView::setProgress(II)(currentProgress, totalProgress);
		});
		$wnd.showProgressBar = $entry(function(){
			view.@com.glenwood.network.client.application.main.MainDesktopView::showProgressBar()();
		});
		$wnd.hideProgressBar = $entry(function(){
			view.@com.glenwood.network.client.application.main.MainDesktopView::hideProgressBar()();
		});
		$wnd.gotoNetwork = $entry(function(networkId){
			view.@com.glenwood.network.client.application.main.MainDesktopView::drawNetwork(I)(networkId)
		});
		var canvas = $wnd.drawGraph(nodeList, linkList, $wnd, startNodeId, networkId, networkAlgorithm);
		canvas.off('object:modified');
		canvas.on({
			'object:modified':function(e){
				var p = e.target;
				var nodeId = p.nodeId;
				var nodeNo = p.nodeNo;
				var xValue = Math.round(p.left);
				var yValue = Math.round(p.top);
				view.@com.glenwood.network.client.application.main.MainDesktopView::dragged(Ljava/lang/String;)(nodeId+"::"+xValue+"::"+yValue);
			}
		});
	}-*/;
	
	/**
	 * Method to hide tooltip
	 * **/
	public void hideTooltip(){
		nodeMenu.clear();
		nodeMenu.removeStyleName(resources.desktopstyles().nodeTooltip());
		nodeMenu.hide();
	}
	
	/**Method to display tooltip
	 * @param details - details where tooltip is to be displayed
	 * **/
	public void showTooltip(String details) {

		nodeMenu.setStyleName(resources.desktopstyles().nodeTooltip());
		String[] detail = details.split("::");
		String nodeId = detail[0];
		int xPosition = Integer.parseInt(detail[1]);
		int yPosition = Integer.parseInt(detail[2]);
		FlexTable tooltipContainer = new FlexTable();
		List<DrawNode> nodeList = drawNetwork.getNodeList();
		for(int i=0; i<nodeList.size(); i++) {
			if(nodeList.get(i).getNodeId().equals(nodeId)) {

				tooltipContainer.setHTML(0, 0, "Node:");
				tooltipContainer.setHTML(0, 1, nodeList.get(i).getNodeName());
				
				String nodeType = nodeList.get(i).getNodeType();
				if(nodeType.equalsIgnoreCase("V")){
					nodeType = "Variable";
				} else if(nodeType.equalsIgnoreCase("C")){
					nodeType = "Constant";
				} else if(nodeType.equalsIgnoreCase("S")){
					nodeType = "Sum Function";
				} else if(nodeType.equalsIgnoreCase("X")){
					nodeType = "Network";
				} else if(nodeType.equalsIgnoreCase("T")){
					nodeType = "Time Function";
				}
				
				tooltipContainer.setHTML(1, 0, "Type:");
				tooltipContainer.setHTML(1, 1, nodeType);

				tooltipContainer.setHTML(2, 0, "X-value:");
				tooltipContainer.setHTML(2, 1, nodeList.get(i).getXValue()+"");

				tooltipContainer.setHTML(3, 0, "Y-value");
				tooltipContainer.setHTML(3, 1, nodeList.get(i).getYValue()+"");
				break;
			}
		}

		nodeMenu.clear();
		nodeMenu.setWidget(tooltipContainer);
		nodeMenu.setPopupPosition(vizPanel.getAbsoluteLeft()+xPosition - 50, vizPanel.getAbsoluteTop()+yPosition-55);
		nodeMenu.show();

	}
	
	public void drawNetwork(int networkId){
		x1 = 0;
		y1 = 0;
		resetViewportPosition();
		eraseCanvas();
		getStorage().setItem("activeNetworkId",  networkId+"");
		setCanvasDirty(true);
		presenter.fetchNetworkInformation(networkId);
	}
	
	/**
	 * Method to view drag node history
	 * @param nodeDetails
	 * **/
	public void dragged(String nodeDetails) {
		if(!History.getToken().contains("dragHistory")){
			presenter.goTo(new DragHistoryPlace("dragHistory"));
		}
		String[] nodeValues = nodeDetails.split("::");
		DrawNode node = null;
		List<DrawNode> nodeList = drawNetwork.getNodeList(); 
		for(int i=0; i< nodeList.size(); i++) {
			if(nodeList.get(i).getNodeId().equals(nodeValues[0])) {
				node = nodeList.get(i);
				break;
			}
		}
		int x = Integer.parseInt(nodeValues[1]);
		int y = Integer.parseInt(nodeValues[2]);
		assert node!= null : "Node cannot be null";
		presenter.updateMoveHistory(x, y, node);
		
	}
	/**
	 * To hide progress bar
	 * **/
	@Override
	public void hideProgressBar() {
		loadProgress.clear();
		graphLegend.setAutoHideEnabled(true);
		graphLegend.hide();
	}
	
	/**
	 * To clear status of progress bar
	 * **/
	@Override
	public void clearProgress() {
		loadProgress.setType(ProgressType.STRIPED);
		ProgressBar progress = new ProgressBar();
		progress.setPercent(100);
		progress.setType(ProgressBarType.INFO);
		loadProgress.setActive(true);
		loadProgress.clear();
		loadProgress.add(progress);
		loadProgress.setActive(true);
	}
	
	/**
	 * To set status of progress bar
	 * **/
	public void setProgress(int currentProgress, int totalProgress) {
		loadProgress.setType(ProgressType.STRIPED);
		loadProgress.setActive(true);
		ProgressBar progress = new ProgressBar();
		progress.setPercent(((1.0*currentProgress)/totalProgress)*100);
		progress.setType(ProgressBarType.INFO);
		progress.setText(currentProgress+"/"+totalProgress);
		loadProgress.setActive(true);
		loadProgress.clear();
		loadProgress.add(progress);
		
	}

	/**
	 * To show progress bar
	 * **/
	@Override
	public void showProgressBar() {
		graphLegend.setGlassEnabled(true);
		graphLegend.setAutoHideEnabled(false);
		graphLegend.clear();
		VerticalPanel loadWrapper = new VerticalPanel();
		loadWrapper.add(new Label("Please wait while graph loading..."));
		loadWrapper.add(loadProgress);

		graphLegend.add(loadWrapper);
		graphLegend.center();
		graphLegend.show();
		
	}
	
	
	/**
	 * Method to change view port coordinates
	 * @param x
	 * @param y
	 * **/
	public void changeXY(int x, int y) {
		x1 = Math.round(x); 
		y1 = Math.round(y);
		presenter.fireReDrawEvent();
		changeSummaryCoords(x,y);
	}
	
	/**JSNI method to set canvas dimensions**/
	private final native String setDimensions() /*-{
		return $wnd.setDimensions();
	}-*/;

	
	/**JSON to JS Object conversion for Network data**/
	@SuppressWarnings("rawtypes")
	private final native JsArray asDrawNetwork(String json) /*-{
		try{
			return eval('['+json+']');
		} catch(e){
			console.log('json string------------->>>'+json);
		}
	}-*/;
	
	private Storage getStorage(){
		Storage storage = Storage.getSessionStorageIfSupported();
		if(storage == null){
			Window.alert("Your browser donot support local storage.. Please change your browser");
		}
		return storage;
	}

	/**
	 * To prompt to save for the network node positions
	 * @param response
	 * **/
	@Override
	public void promptPositionSave(Integer response) {
		setAlgoChanged(true);
		if(response == 1){
			
			if(Window.confirm("Do you want to override the node positions")) {
				clearProgress();
				showProgressBar();
				presenter.savePosition(selectedAlgorithm);
			} else {
				clearProgress();
				hideProgressBar();
				allowNodeDrag();
				presenter.fireReDrawEvent();
			}
		}  else {
			if(Window.confirm("Do you want to set this as default method")) {
				clearProgress();
				showProgressBar();
				presenter.savePosition(selectedAlgorithm);
			} else {
				allowNodeDrag();
			}
		}
	}

	/**To enable and disable node dragging option**/
	public native final void blockNodeDrag()/*-{
		$wnd.blockNodeDrag();
	}-*/;
	public native final void allowNodeDrag()/*-{
		$wnd.allowNodeDrag();
	}-*/;
	
	/**
	 * To show error message to the user
	 * **/
	@Override
	public void updateError(){
		graphLegend.clear();
		graphLegend.add(new Label("Save Failed"));
		graphLegend.center();
		Timer t = new Timer() {
			public void run() {
				graphLegend.clear();
				graphLegend.setAutoHideEnabled(true);
				graphLegend.hide();
			}
		};
		t.schedule(2000);
		allowNodeDrag();
	}
	
	/**
	 * Method to set the thumbnail for the network
	 * @param imageString
	 * **/
	@Override
	public void setThumbnail(String imageString) {
		if( bodyWrap.getWidget(bodyWrap.getWidgetCount()-1).getStyleName().contains("demo-WindowPanel dragdrop-draggable") ) {
			bodyWrap.remove(bodyWrap.getWidgetCount()-1);
		}
		HorizontalPanel header = new HorizontalPanel();
		Label thHead = new Label("Map view");
		AbsolutePanel imageWraper = new AbsolutePanel();
		Image image = new Image("data:image/png;base64,"+imageString);
		image.setHeight("100px");
		image.setWidth("100Px");
		image.addStyleName(resources.desktopstyles().summaryImage());
		imageWraper.add(image);
		FocusPanel movebutton = new FocusPanel();
		header.add(thHead);
		header.add(movebutton);
		header.setCellHorizontalAlignment(thHead, HasHorizontalAlignment.ALIGN_LEFT);
		header.setCellHorizontalAlignment(movebutton, HasHorizontalAlignment.ALIGN_RIGHT);
		header.addStyleName(resources.desktopstyles().windowHeader());

		WindowController wnctrl = new WindowController(bodyWrap);
		WindowPanel wp = new WindowPanel(wnctrl, header, imageWraper, false);

		wp.addStyleName(resources.desktopstyles().floatingSummary());
		bodyWrap.add(wp);
		int left = 75;
		int top = 70;
		wp.moveTo((Window.getClientWidth()/100)*left, (Window.getClientHeight()/100)*top);
	}
	
	/**
	 * Method to display map
	 * **/
	public void displayMap(){
		if( bodyWrap.getWidget(bodyWrap.getWidgetCount()-1).getStyleName().contains("demo-WindowPanel dragdrop-draggable") ) {
			WindowPanel wp = (WindowPanel) bodyWrap.getWidget(bodyWrap.getWidgetCount()-1);
			wp.setVisible(true);
		}
	}
	
	/**
	 * Method to hide map if error occored
	 * **/
	/**
	 * Method to display map
	 * **/
	@Override
	public void hideMap(){
		if( bodyWrap.getWidget(bodyWrap.getWidgetCount()-1).getStyleName().contains("demo-WindowPanel dragdrop-draggable") ) {
			bodyWrap.remove(bodyWrap.getWidgetCount()-1);
		}
	}

	
	/**
	 * To set the window port in network Map
	 * **/
	public void setMap(JSONObject properties) {
		WindowPanel wp = null;
		mapProperties = properties;
		double xRatio = Double.parseDouble(mapProperties.get("scaleX").toString());
		double yRatio = Double.parseDouble(mapProperties.get("scaleY").toString());
		
		double width = Double.parseDouble(mapProperties.get("width").toString());
		double height = Double.parseDouble(mapProperties.get("height").toString());
		
		if( bodyWrap.getWidget(bodyWrap.getWidgetCount()-1).getStyleName().contains("demo-WindowPanel dragdrop-draggable") ) {
			wp = (WindowPanel) bodyWrap.getWidget(bodyWrap.getWidgetCount()-1);
		}
		AbsolutePanel imageWraper = ((AbsolutePanel)wp.getContentOrScrollPanelWidget());
		final SimpleWidget viewport = new SimpleWidget("");
		
		viewport.setWidth(( ( 100.0 / (width/100.0) ) * xRatio)+"px"  );
		viewport.setHeight(( ( 100.0 / (height/100.0) ) * yRatio)+"px"  );
		
		viewport.addStyleName(resources.desktopstyles().imageViewport());
		
		
		SimpleWidgetDragController cntrl = new SimpleWidgetDragController(this, imageWraper, true);
		cntrl.makeDraggable(viewport);
		
		imageWraper.add(viewport);
		imageWraper.setWidgetPosition(viewport, 0, 0);
		
	}
	

	/**
	 * To change viewport positions of graph when map ports are changed
	 * **/
	public void dragEnd() {
		try{
			WindowPanel wp = null;
			
			double width = Double.parseDouble(mapProperties.get("width").toString());
			double height = Double.parseDouble(mapProperties.get("height").toString());
			
			if( bodyWrap.getWidget(bodyWrap.getWidgetCount()-1).getStyleName().contains("demo-WindowPanel dragdrop-draggable") ) {
				wp = (WindowPanel) bodyWrap.getWidget(bodyWrap.getWidgetCount()-1);
			}
			AbsolutePanel imageWraper = ((AbsolutePanel)wp.getContentOrScrollPanelWidget());
			final SimpleWidget viewport = (SimpleWidget) (imageWraper.getWidget(1));
			
			int left = imageWraper.getWidgetLeft(viewport);
			int top = imageWraper.getWidgetTop(viewport);

			x1 = left = (int) Math.ceil((width+110) * left / 100.0) * -1;
			y1 = top = (int) Math.ceil((height+110) * top / 100.0) * -1;
			
			setViewportPosition(left, top);
			presenter.fireReDrawEvent();
		} catch (NullPointerException e){
			Window.alert("Error occord please try again");
		}
	}
	
	/**
	 * To set the coordinates for summary-viewport when viewport is dragged 
	 * **/
	public void changeSummaryCoords(int x, int y) {
		
		WindowPanel wp = null;
		
		double width = Double.parseDouble(mapProperties.get("width").toString());
		double height = Double.parseDouble(mapProperties.get("height").toString());
		
		if( bodyWrap.getWidget(bodyWrap.getWidgetCount()-1).getStyleName().contains("demo-WindowPanel dragdrop-draggable") ) {
			wp = (WindowPanel) bodyWrap.getWidget(bodyWrap.getWidgetCount()-1);
		}
		AbsolutePanel imageWraper = ((AbsolutePanel)wp.getContentOrScrollPanelWidget());
		final SimpleWidget viewport = (SimpleWidget) imageWraper.getWidget(1);
		int left = 0, top = 0;
		
		left = (int) Math.ceil(100*x1/width) * -1;
		top = (int) Math.ceil(100*y1/height) * -1;
		
		imageWraper.setWidgetPosition(viewport, left, top);
	}

	/**JSNI method to set viewport positions**/
	private final native void setViewportPosition(int x, int y) /*-{
		$wnd.setViewportPosition(x, y);
	}-*/; 
	
	/**
	 * To display success message to the user
	 * **/
	public void updateSuccess() {
		hideProgressBar();
		graphLegend.clear();
		graphLegend.add(new Label("Save sucess"));
		graphLegend.center();

		Timer t = new Timer() {
			public void run() {
				graphLegend.clear();
				graphLegend.setAutoHideEnabled(true);
				graphLegend.hide();
			}
		};
		t.schedule(2000);
		beginNodeDrag();
	}
	
	/**
	 * To enable node drag in graph
	 * **/
	private void beginNodeDrag() {
		freeHandMode = true;
		algorithmName.setText("Free hand");
		selectedAlgorithm = -1;
		presenter.saveDragHistory();
		drawNetwork.setNodeList(null);
		drawNetwork.setLinkList(null);
		setCanvasDirty(true);
		presenter.fireReDrawEvent();
		return;
	}

	/**
	 * Method to reposition node to its original position
	 * @param node
	 * **/
	@Override
	public void repositionNode(DrawNode node) {
		repositionNode(node.getXValue(), node.getYValue(), node.getNodeId());
	}
	
	/**To reposition node to its original place when node is dragged 
	 * @param yValue 
	 * @param xValue
	 * @param nodeId
	 * @param nodeNo 
	 * **/
	native void repositionNode(int xValue, int yValue, String nodeId) /*-{
		$wnd.repositionNode(xValue, yValue, nodeId);
	}-*/;

	/**
	 * TODO
	 * Method to add new entries in history
	 * **/
	@Override
	public void addHistory(Network network) {
		String rootNetworkName = getStorage().getItem("networkName");
		final Label networkName = new Label(network.getName());
		
		if(!network.getName().equals(rootNetworkName)){
			networkName.addStyleName(resources.desktopstyles().historyLabel());
		}
		
		final Integer networkId = network.getIdNetwork();
		networkNavHistory.add(networkName);
		
		networkName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int index = networkNavHistory.getWidgetIndex(networkName);
				for(int i = networkNavHistory.getWidgetCount()-1; i >= index ; i--){
					networkNavHistory.remove(i);
				}
				drawNetwork(networkId);
			}
		});
		
	}
	
	/**
	 * Method to reset the navigation history
	 * **/
	private void resetnetworkNavHistory() {
		Storage storage = getStorage();
		networkNavHistory.clear();
		networkNavHistory.setVisible(true);
		final Label networkName = new Label(storage.getItem("networkName"));
		final Integer networkId = Integer.parseInt(storage.getItem("networkId"));
		networkNavHistory.add(networkName);
		networkName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int index = networkNavHistory.getWidgetIndex(networkName);
				for(int i = networkNavHistory.getWidgetCount()-1; i >= index ; i--){
					networkNavHistory.remove(i);
				}
				drawNetwork(networkId);
			}
		});
		
	}
	
	@Override
	public void clearNavigationHistory(){
		if(selectedAlgorithm != 3){
			networkNavHistory.clear();
			networkNavHistory.setVisible(false);
		}
	}

}