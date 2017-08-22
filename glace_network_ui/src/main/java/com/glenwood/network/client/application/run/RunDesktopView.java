package com.glenwood.network.client.application.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.OnFinishUploaderHandler;

import com.glenwood.network.client.application.domain.Node;
import com.glenwood.network.client.customwidgets.selectoionControl.SearchEvent;
import com.glenwood.network.client.customwidgets.selectoionControl.SearchEventHandler;
import com.glenwood.network.client.customwidgets.selectoionControl.SearchResult;
import com.glenwood.network.client.customwidgets.selectoionControl.SearchResultData;
import com.glenwood.network.client.customwidgets.selectoionControl.SelectionControl;
import com.glenwood.network.client.resource.DesktopResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RunDesktopView extends Composite implements RunView {

	private static RunDesktopViewUiBinder uiBinder = GWT.create(RunDesktopViewUiBinder.class);

	interface RunDesktopViewUiBinder extends UiBinder<Widget, RunDesktopView> {	}
	
	@UiField
	DesktopResources resources;
	
	@UiField
	FocusPanel upload;
	
	@UiField
	FocusPanel input;
	
	@UiField
	FocusPanel output;
	
	@UiField
	FocusPanel run;
	
	@UiField(provided=true)
	FlowPanel fileUploadBody;
	
	@UiField(provided=true)
	FlowPanel inputBody;
	
	@UiField(provided=true)
	FlowPanel outputBody;
	
	@UiField(provided=true)
	FlowPanel runBody;
	
	@UiField(provided=true)
	MultiUploader uploader;
	
	@UiField
	Label stc1;
	
	@UiField
	Label stc2;
	
	@UiField
	Label stc3;
	
	@UiField
	Label stc4;
	
	Button uploadButton;
	Button setInputButton;
	Button setOutputButton;
	
	TextBox fromValueBox;
	TextBox toValueBox;
	ListBox inputList;
	ListBox outputList;
	
	private Presenter presenter;
	
	private FlowPanel inputListWrap;
	private FlowPanel fromValueWrap;
	private FlowPanel toValueWrap;
	private FlowPanel outputListWrap;
	
	private SelectionControl ctrl;
	
	private RunClient client = GWT.create(RunClient.class);
	
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	private ArrayList<String> fileFieldNames = new ArrayList<String>();
	private boolean containsRedundantInput = false;
	private boolean isInputSet = false;
	private Integer maxValue;
	private Integer fromValue;
	private Integer toValue;
	private JSONObject inputValue;
	private JSONObject outputValue;
	
	public RunDesktopView() {
		initPanels();
		initWidget(uiBinder.createAndBindUi(this));
		setStyles();
		initHandlers();
	}
	
	@Override
	public RunClient getClient(){
		return client;
	}
	
	/**
	 * Method to create UI handlers
	 * **/
	private void initHandlers() {
		for(int i=0 ; i< registrations.size() ; i++){
			registrations.get(i).removeHandler();
		}
		registrations.clear();
		
		initUploadHandlers();
		
		initInputHandlers();
		
		initOutputHandlers();
	}

	/**
	 * Method to add output handlers
	 * **/
	private void initOutputHandlers() {
		registrations.add(ctrl.addSearchEventHandler(new SearchEventHandler() {
			@Override
			public void onSearch(SearchEvent event) {
				//ctrl.setLoading();
				presenter.fetchNodeList(ctrl.getSearchKeyWord(), 0, ctrl);
			}
		}));
	}

	/**
	 * Method to add input handlers
	 * **/
	private void initInputHandlers() {
		registrations.add(setInputButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(maxValue == null){
					Window.alert("Input configuration failed, plese try again by uploading file");
					return;
				}
				if(validateIndexes()){
					isInputSet = true;
					output.fireEvent(new ClickEvent(){});
				}
			}
		}));
		
	}

	/**
	 * Method to validate from and to indexes after uploading file
	 * **/
	protected boolean validateIndexes() {
		boolean flag = true;
		if(fromValueBox.getText() == ""){
			Window.alert("Please enter from value");
			return false;
		} else {
			try{
				fromValue = Integer.parseInt(fromValueBox.getText());
				if(fromValue> maxValue) {
					Window.alert("Please enter from value within range");
					return false;
				}

				toValue = Integer.parseInt(toValueBox.getText());
				if(toValue> maxValue) {
					Window.alert("Please enter to value within range");
					return false;
				}
				if(fromValue > toValue){
					Window.alert("Please enter valid range");
					return false;
				}
			} catch(Exception e){
				Window.alert("Please enter numaric range values");
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * Method to create upload handlers
	 * **/
	private void initUploadHandlers() {
		List<IUploader> uploaders = uploader.getUploaders();
		for(IUploader up : uploaders ){
			up.setAutoSubmit(true);
		}
		registrations.add(uploader.addOnFinishUploadHandler( new OnFinishUploaderHandler() {
			public void onFinish(IUploader Iuploader) {
				List<IUploader> uploaders = uploader.getUploaders();
				for(IUploader up : uploaders ){
					if(up.getStatus() == Status.SUCCESS){
						fileFieldNames.addAll(up.getServerMessage().getUploadedFileNames());
					}
				}
				Window.alert("upload sucess");
				input.fireEvent(new ClickEvent(){});
			}

		}));
		
	}

	private void setStyles() {
		inputListWrap.setStyleName(resources.runpanelstyles().inputWrapers());
		fromValueWrap.setStyleName(resources.runpanelstyles().inputWrapers());
		toValueWrap.setStyleName(resources.runpanelstyles().inputWrapers());
		inputList.setStyleName(resources.runpanelstyles().inputSelector());
		setInputButton.addStyleName(resources.runpanelstyles().submitbtn());
		setOutputButton.addStyleName(resources.runpanelstyles().submitbtn());
		outputListWrap.addStyleName(resources.runpanelstyles().inputWrapers());
	}

	/**
	 * To initialize panels
	 * **/
	private void initPanels() {
		initFileUploadBody();
		initInputBody();
		initOutputBody();
		initRunBody();
	}

	/**
	 * TO initialize the file upload panel
	 * **/
	private void initFileUploadBody() {
		fileUploadBody = new FlowPanel();

		uploader= new MultiUploader(FileInputType.LABEL);
		uploader.setValidExtensions ("csv","xlsx","xls");
		uploader.avoidRepeatFiles(false);
		uploader.setMultipleSelection(true);
		
		uploadButton = new Button("Choose File");
		uploader.addButton(uploadButton);
		
	}
	
	/**
	 * To initialize run body
	 * **/
	private void initRunBody() {
		runBody = new FlowPanel();
	}

	/**
	 * To initialize input body
	 * **/
	private void initInputBody() {
		inputBody = new FlowPanel();
		
		Label selectionLabel = new Label("Select input");
		Label fromValueLabel = new Label("From");
		Label toValueLabel = new Label("To");
		
		selectionLabel.setWidth("50%");
		fromValueLabel.setWidth("50%");
		toValueLabel.setWidth("50%");
		
		fromValueBox = new TextBox();
		toValueBox = new TextBox();
		
		inputList = new ListBox();
		inputList.setMultipleSelect(true);
		
		
		inputListWrap = new FlowPanel();
		fromValueWrap = new FlowPanel();
		toValueWrap = new FlowPanel();
			
		inputListWrap.setWidth("100%");
		fromValueWrap.setWidth("100%");
		toValueWrap.setWidth("100%");
		
		inputListWrap.add(selectionLabel);
		inputListWrap.add(inputList);
		
		fromValueWrap.add(fromValueLabel);
		fromValueWrap.add(fromValueBox);
		
		toValueWrap.add(toValueLabel);
		toValueWrap.add(toValueBox);
		
		inputBody.add(inputListWrap);
		inputBody.add(fromValueWrap);
		inputBody.add(toValueWrap);
		
		setInputButton = new Button("Set Input");
		inputBody.add(setInputButton);
		
	}
	
	/**
	 * To initialize output body
	 * **/
	private void initOutputBody() {
		outputBody = new FlowPanel();
		
		outputBody.setWidth("100%");
		
		ctrl = new SelectionControl();
		
		Label outputLabel = new Label("Select output");
		outputList = new ListBox();
		outputLabel.setWidth("20%");
		outputList.setWidth("100px");
		outputListWrap = new FlowPanel();
		outputListWrap.setWidth("100%");
		outputListWrap.setHeight("120px");
		outputListWrap.add(outputLabel);
		//outputListWrap.add(outputList);
		outputListWrap.add(ctrl);
		
		outputBody.add(outputListWrap);
		setOutputButton = new Button("Set Output");
		outputBody.add(setOutputButton);
	}

	@UiHandler("upload")
	public void showUploadContents(ClickEvent e){
		stc1.setStyleName(resources.runpanelstyles().stepCountActive());
		stc2.setStyleName(resources.runpanelstyles().stepCount());
		stc3.setStyleName(resources.runpanelstyles().stepCount());
		stc4.setStyleName(resources.runpanelstyles().stepCount());
		fileUploadBody.setVisible(true);
		inputBody.setVisible(false);
		outputBody.setVisible(false);
		runBody.setVisible(false);
		uploader.reset();
	}
	
	@UiHandler("input")
	public void showInputContents(ClickEvent e){
		stc2.setStyleName(resources.runpanelstyles().stepCountActive());
		stc1.setStyleName(resources.runpanelstyles().stepCount());
		stc3.setStyleName(resources.runpanelstyles().stepCount());
		stc4.setStyleName(resources.runpanelstyles().stepCount());
		fileUploadBody.setVisible(false);
		inputBody.setVisible(true);
		outputBody.setVisible(false);
		runBody.setVisible(false);
		
		inputList.clear();
		for(int i = 0 ; i < fileFieldNames.size() ; i++){
			presenter.fetchNodeList(fileFieldNames.get(i));
		}
		
		
	}
	
	@UiHandler("output")
	public void showOutputContents(ClickEvent e){
		if(!isInputSet){
			Window.alert("Please configure input first");
			return;
		}
		stc3.setStyleName(resources.runpanelstyles().stepCountActive());
		stc2.setStyleName(resources.runpanelstyles().stepCount());
		stc1.setStyleName(resources.runpanelstyles().stepCount());
		stc4.setStyleName(resources.runpanelstyles().stepCount());
		fileUploadBody.setVisible(false);
		inputBody.setVisible(false);
		outputBody.setVisible(true);
		runBody.setVisible(false);
		
	}
	
	@UiHandler("run")
	public void showRunContents(ClickEvent e){
		stc4.setStyleName(resources.runpanelstyles().stepCountActive());
		stc2.setStyleName(resources.runpanelstyles().stepCount());
		stc3.setStyleName(resources.runpanelstyles().stepCount());
		stc1.setStyleName(resources.runpanelstyles().stepCount());
		fileUploadBody.setVisible(false);
		inputBody.setVisible(false);
		outputBody.setVisible(false);
		runBody.setVisible(true);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	/**
	 * Method to set input from the file
	 */
	@Override
	public void setInputist(HashMap<Integer, String> input){
		maxValue = Integer.parseInt(input.get(Integer.MIN_VALUE));
		for (Map.Entry<Integer, String> entrySet : input.entrySet()) {
			int fileEntryId = entrySet.getKey();
			for( int i = 0 ; i < inputList.getItemCount() ; i++ ){
				
				if(inputList.getItemText(i).equals(input.get(fileEntryId))){
					containsRedundantInput  = true;
					Window.alert("Error -- Files uploaded has redundant input "+inputList.getItemText(i));
				}
			}
			inputList.addItem(input.get(fileEntryId), fileEntryId + "");
			if(containsRedundantInput){
				inputList.clear();
			}
		}
	}

	/**
	 * Method to remove file field name from list
	 * **/
	@Override
	public void removeFileFieldNames(String name){
		if(fileFieldNames != null){
			fileFieldNames.remove(name);
			
		}
	}
	
	/**
	 * Method to set the input list
	 * @param ctrl
	 * @param nodes
	 */
	@Override
	public void seinputList(SelectionControl ctrl, List<Node> nodes){
		List<SearchResult>  results = new ArrayList<SearchResult>();
		for(Node n : nodes){
			SearchResultData data = new SearchResultData(n.getNodeId(), n.getNodeName());
			SearchResult result = new SearchResult(n.getNodeName(), data, n.getNodeName());
			results.add(result);
		}
		ctrl.addInput(results);
	}
}
