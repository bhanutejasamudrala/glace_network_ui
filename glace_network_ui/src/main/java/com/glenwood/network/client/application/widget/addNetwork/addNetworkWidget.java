package com.glenwood.network.client.application.widget.addNetwork;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class addNetworkWidget extends Composite implements HasHandlers {

	private static addNetworkWidgetUiBinder uiBinder = GWT.create(addNetworkWidgetUiBinder.class);
	
	@UiField
	RadioButton manualUpload;
	@UiField
	RadioButton uploadUsingFile;
	@UiField
	FlowPanel uploadPanel;

	Label l = new Label("Network Name");
	TextBox networkNameBox = new TextBox();
	Button submit = new Button("Save");
	FormPanel form;
	FileUpload readFromFile;
	
	public boolean uploadFlag = false;
	public String submitUrl;

	interface addNetworkWidgetUiBinder extends
			UiBinder<Widget, addNetworkWidget> {
	}

	public addNetworkWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		initControl();
	}
	
	/**
	 * To initialize ui handlers
	 */
	private void initControl() {
		if(uploadFlag){
			String networkName = networkNameBox.getText();
			if(form.getAction()!=null){
				form.submit();
			}
		}
	}

	/**
	 * 
	 * @param e
	 */
	private interface AddNetworkHandler extends HasHandlers{}
	
	@UiHandler("uploadUsingFile")
	public void uploadByFile(ClickEvent e){
		FlexTable flex = new FlexTable();
		flex.setWidget(0, 0, l);
		flex.setWidget(0, 1, networkNameBox);
		uploadPanel.add(flex);
		uploadPanel.add(submit);
	}
	
	@UiHandler("manualUpload")
	public void manualUpload(ClickEvent e){
		FlexTable flex = new FlexTable();
		flex.setWidget(0, 0, l);
		flex.setWidget(0, 1, networkNameBox);
		
		
		readFromFile = new FileUpload();
		readFromFile.setName("file");
		readFromFile.getElement().setAttribute("id", "file");
		form = new FormPanel();
		
		flex.setWidget(1, 1, readFromFile);
		
		form.getElement().setAttribute("commandName", "document");

		readFromFile.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String fileName = readFromFile.getFilename();
				if((!fileName.endsWith(".mtx")) && (!fileName.endsWith(".txt"))) {
					uploadFlag = false;
					Window.alert("Please upload .mtx or .txt files");
				} else {
					uploadFlag = true;
				}
			}
		});
		
		uploadPanel.add(flex);
		uploadPanel.add(submit);
		
	}
	
	

}
