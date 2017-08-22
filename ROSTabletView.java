package com.glenwood.glaceemr.client.application.chart.ros;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.glenwood.glaceemr.client.application.chart.SaveData;
import com.glenwood.glaceemr.client.application.chart.saveDataResponseHandler;
import com.glenwood.glaceemr.client.application.chart.setClinicalElementDirty;
import com.glenwood.glaceemr.client.application.chart.hpi.HPIShortcutHandler;
import com.glenwood.glaceemr.client.application.chart.soap.SOAPView;
import com.glenwood.glaceemr.client.application.chart.widget.quicknotes.QuickNotesResponseHandler;
import com.glenwood.glaceemr.client.application.chart.widget.quicknotes.SoapElementDatalist;
import com.glenwood.glaceemr.client.application.fastbutton.FastButton;
import com.glenwood.glaceemr.client.application.fastbutton.FastCheckbox;
import com.glenwood.glaceemr.client.application.fastbutton.FastToggleCheckbox;
import com.glenwood.glaceemr.client.application.fastbutton.PressEvent;
import com.glenwood.glaceemr.client.application.fastbutton.PressHandler;
import com.glenwood.glaceemr.client.application.glacealert.GlaceAlert;
import com.glenwood.glaceemr.client.application.main.ActionServletURL;
import com.glenwood.glaceemr.client.application.main.CustomLocalStorage;
import com.glenwood.glaceemr.client.application.main.IpadErrorLog;
import com.glenwood.glaceemr.client.application.searchcontrol.SearchControl;
import com.glenwood.glaceemr.client.application.searchcontrol.SearchResult;
import com.glenwood.glaceemr.client.application.searchcontrol.ShowMorePagerPanel;
import com.glenwood.glaceemr.client.resource.TabletResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author cnm
 */
public class ROSTabletView extends Composite implements ROSView {

	private static ROSTabletViewUiBinder uiBinder = GWT
			.create(ROSTabletViewUiBinder.class);

	interface ROSTabletViewUiBinder extends UiBinder<Widget, ROSTabletView> {
	}

	private Presenter presenter;
	
	@UiField
	HorizontalPanel ROSMainPanel;
	@UiField
	FlowPanel systemDataPanel;
	@UiField
	TabletResources resources;
	@UiField
	VerticalPanel systemMainDataVerticalPanel;
	@UiField
	ScrollPanel systemDataPanelscrollbar;
	@UiField
	HorizontalPanel moreOptionPanel;
	@UiField 
	VerticalPanel pageLoadingpanel;
	
	FlowPanel ROSDataPanel = new FlowPanel();
	FlowPanel ROSDataPanel1 = new FlowPanel();
	ShowMorePagerPanel showPager = new ShowMorePagerPanel(10);
	SearchControl searchROS = new SearchControl();
	FlowPanel notescontainer = new FlowPanel();
	FlowPanel systemDataVerticalPanel = new FlowPanel();
	VerticalPanel notesText = new VerticalPanel();
	
	
	private HorizontalPanel tabpopupPanel;
	private FastToggleCheckbox negativeChkBox;
	
	HPIShortcutHandler hpiShortcutHandler;
	FastButton closeBtn;
	FastButton saveButton;
	FastButton cancelButton;
	FastButton moreButton;
	Label ROSHeader;
	FastButton notesSaveButton;
	FastButton notesCloseButton;
	FastButton importBtn;
	static HandlerRegistration importHandler;
	ROSClient rosClient;
	ROSSystemBeanCodec rosSystemBeanCodec;
	private int normal=0;
	
	ArrayList<SearchResult> searchResult = new ArrayList<SearchResult>();
	ArrayList<SearchControl> searchBox = new ArrayList<SearchControl>();

	private static String encounterId;
	private static String chartId;
	private static String patientId;
	private static String templateId;
	private static String formId;
	
	@Inject
	ActionServletURL actionServletURL;
	
	public ROSTabletView() {
		initWidget(uiBinder.createAndBindUi(this));
		rosClient=GWT.create(ROSClient.class);
		rosSystemBeanCodec=GWT.create(ROSSystemBeanCodec.class);
		systemDataPanel.getElement().setAttribute("id", "systemDataPanel");
		ROSDataPanel.setWidth("48%");
		ROSDataPanel.getElement().getStyle().setMarginLeft(1, Unit.PCT);
		ROSDataPanel.getElement().getStyle().setMarginRight(1, Unit.PCT);
		ROSDataPanel1.setWidth("48%");
		ROSDataPanel1.getElement().getStyle().setMarginLeft(1, Unit.PCT);
		ROSDataPanel1.getElement().getStyle().setMarginRight(1, Unit.PCT);
		systemMainDataVerticalPanel.getElement().setAttribute("id", "systemMainPanel");
		systemDataPanelscrollbar.getElement().setAttribute("id", "systemDataScrollpanel");
		systemDataPanelscrollbar.getElement().getStyle().setBackgroundColor("#EFEFF4");
		searchROS.setHeight("300px");
		searchROS.setHeight(83);
		systemDataPanel.add(ROSDataPanel);
		systemDataPanel.add(ROSDataPanel1);
		moreButton = new FastButton("More", resources.tabletstyles().fastButtonBlue(), resources.tabletstyles().fastButtonBlue(),"");
		saveButton = new FastButton("Save", resources.tabletstyles().fastButtonBlue(), resources.tabletstyles().fastButtonBlue(),"");
		importBtn = new FastButton("Import", resources.tabletstyles().fastButtonBlue(), resources.tabletstyles().fastButtonBlue(),"");
		cancelButton = new FastButton("Cancel", resources.tabletstyles().fastButtonBlue(), resources.tabletstyles().fastButtonBlue(),"");

		ROSHeader = new Label("Review of Systems");
		ROSHeader.setStyleName(resources.tabletstyles().headerLabelStyles());
		moreOptionPanel.getElement().getStyle().setFloat(Float.RIGHT);
		moreOptionPanel.setStyleName(resources.cnmstyles().cnmSysDataPanelNormalEndRow());	
		moreOptionPanel.setHeight("50px");
		moreOptionPanel.getElement().getStyle().setBackgroundColor("#EFEFF4");
		HorizontalPanel savebuttonspanel = new HorizontalPanel();
		savebuttonspanel.getElement().getStyle().setFloat(Float.RIGHT);
		moreOptionPanel.add(ROSHeader);
		moreOptionPanel.setCellVerticalAlignment(ROSHeader, HasVerticalAlignment.ALIGN_MIDDLE);
		importBtn.addStyleName(resources.cnmstyles().saveExtraStyle());
		moreButton.addStyleName(resources.cnmstyles().saveExtraStyle());
		saveButton.addStyleName(resources.cnmstyles().saveExtraStyle());
		cancelButton.addStyleName(resources.cnmstyles().saveExtraStyle());
		savebuttonspanel.add(importBtn);
		savebuttonspanel.add(moreButton);
		savebuttonspanel.add(saveButton);
		savebuttonspanel.add(cancelButton);
		moreOptionPanel.add(savebuttonspanel);
		moreOptionPanel.setCellVerticalAlignment(savebuttonspanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		if(importHandler!=null)
			importHandler.removeHandler();
		importHandler = importBtn.addPressHandler(new PressHandler() {
			
			@Override
			public void onPress(PressEvent event) {				
				presenter.fetchOldEncounterDetails(importBtn);
				
				
			}
		});
		
		moreButton.addPressHandler(new PressHandler() {
			@Override
			public void onPress(PressEvent event) {

				final PopupPanel moreOptionsPopup = new PopupPanel();
				ScrollPanel moreOptionsScroll = new ScrollPanel();
				VerticalPanel moreOptionsListPanel = new VerticalPanel();
				FastButton markAllNormalButton = new FastButton("Mark All Normal",resources.cnmstyles().statusButton(), resources.cnmstyles().statusButtonHold(), "");
				markAllNormalButton.addStyleName(resources.cnmstyles().borderBottom());
				markAllNormalButton.getElement().getStyle().setFontWeight(FontWeight.NORMAL);
				markAllNormalButton.addPressHandler(new PressHandler() {

					@Override
					public void onPress(PressEvent event) {
						normal=1;
						getROSChildPanel(systemDataPanel,1,0);
						moreOptionsPopup.hide();
					}
				});
				FastButton clearAllNormalButton = new FastButton("Clear All Normal", resources.cnmstyles().statusButton(), resources.cnmstyles().statusButtonHold(), "");
				clearAllNormalButton.addStyleName(resources.cnmstyles().borderBottom());
				clearAllNormalButton.getElement().getStyle().setFontWeight(FontWeight.NORMAL);
				clearAllNormalButton.addPressHandler(new PressHandler() {

					@Override
					public void onPress(PressEvent event) {
						normal=0;
						getROSChildPanel(systemDataPanel,2,0);
						moreOptionsPopup.hide();
					}
				});
				FastButton markexemptedNormalButton = new FastButton("Mark Exempted Normal", resources.cnmstyles().statusButton(), resources.cnmstyles().statusButtonHold(), "");
				markexemptedNormalButton.addStyleName(resources.cnmstyles().borderBottom());
				markexemptedNormalButton.getElement().getStyle().setFontWeight(FontWeight.NORMAL);
				markexemptedNormalButton.addPressHandler(new PressHandler() {

					@Override
					public void onPress(PressEvent event) {
						//normal=1;
						elements=new StringBuffer();
						systems=new StringBuffer();
						getROSChildPanel(systemDataPanel,3,1);
						getROSChildPanel(systemDataPanel,3,0);
						moreOptionsPopup.hide();
					}
				});

				FastButton FocalShortcuts = new FastButton("Focal Shortcuts",resources.cnmstyles().statusButton(), resources.cnmstyles().statusButtonHold(), "");
				FocalShortcuts.addStyleName(resources.cnmstyles().borderBottom());
				FocalShortcuts.getElement().getStyle().setFontWeight(FontWeight.NORMAL);
				FocalShortcuts.addPressHandler(new PressHandler() {

					@Override
					public void onPress(PressEvent event) {
						save(systemDataPanel,new saveDataResponseHandler() {
							@Override
							public void onSave() {	
							}
						});
						moreOptionsPopup.hide();
						presenter.openfocalshortcuts(2);
					}
				});
				
				moreOptionsListPanel.add(markAllNormalButton);
				moreOptionsListPanel.setCellHorizontalAlignment(markAllNormalButton,HasHorizontalAlignment.ALIGN_CENTER);
				moreOptionsListPanel.add(clearAllNormalButton);
				moreOptionsListPanel.setCellHorizontalAlignment(clearAllNormalButton,HasHorizontalAlignment.ALIGN_CENTER);
				moreOptionsListPanel.add(markexemptedNormalButton);
				moreOptionsListPanel.setCellHorizontalAlignment(markexemptedNormalButton,HasHorizontalAlignment.ALIGN_CENTER);
				moreOptionsListPanel.add(FocalShortcuts);
				moreOptionsListPanel.setCellHorizontalAlignment(FocalShortcuts,HasHorizontalAlignment.ALIGN_CENTER);
				moreOptionsListPanel.setSize("100%", "100%");
				moreOptionsScroll.add(moreOptionsListPanel);
				moreOptionsScroll.getElement().setId("moreOptionsScroll");
				moreOptionsPopup.add(moreOptionsScroll);
				moreOptionsScroll.addStyleName(resources.cnmstyles().morePopup());
				moreOptionsPopup.setPopupPosition(
				moreButton.getAbsoluteLeft() - 329,
				moreButton.getAbsoluteTop() + 37);
				moreOptionsPopup.getElement().setId("moreOptionsPopup");
				moreOptionsPopup.setAutoHideEnabled(true);
				moreOptionsPopup.setGlassEnabled(true);
				moreOptionsPopup.addStyleName(resources.cnmstyles().popupView());
				moreOptionsPopup.addStyleName(resources.cnmstyles().morePopup());
				moreOptionsPopup.show();
				
				Window.addResizeHandler(new ResizeHandler() {
					@Override
					public void onResize(ResizeEvent event) {
						moreOptionsPopup.hide();
					}
				});
			}
		});

		final FlowPanel pageloadingfp=new FlowPanel();
	    Image pageloading=new Image("images/ajax-loader.gif");
	    Label pagepleaseWait=new Label("please wait...");
	    pageloading.setStyleName(resources.cnmstyles().loadingImage());
	    pageLoadingpanel.setWidth("100%");
	    pageloadingfp.add(pageloading);
	    pageLoadingpanel.add(pageloadingfp);
	    pageLoadingpanel.setCellHorizontalAlignment(pageloadingfp, HasHorizontalAlignment.ALIGN_CENTER);
	    pageLoadingpanel.setCellVerticalAlignment(pageloadingfp, HasVerticalAlignment.ALIGN_MIDDLE);
	   
	    Window.addResizeHandler(new ResizeHandler() {
	        @Override
	        public void onResize(ResizeEvent event) {
	        	pageLoadingpanel.setWidth("100%");
	        }
	    });
	    pagepleaseWait.getElement().getStyle().setColor("#2087fc");
	    pagepleaseWait.getElement().getStyle().setFontSize(16, Unit.PX);
	    pagepleaseWait.getElement().getStyle().setMarginLeft(15, Unit.PX);
	    pageLoadingpanel.setVisible(false);
		setHeightsandWidths();
		addhandlers();
	}

	@Override
	public Widget asWidget() {
		showpageLoading();
		systemDataPanel.clear();
		systemDataPanelscrollbar.getElement().getStyle().setBackgroundColor("#EFEFF4");
		setHeightsandWidths();
		return this;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	public Widget getMainPanel() {
		return this.ROSMainPanel;
	}

	 public  void showpageLoading()
	 {
	    pageLoadingpanel.setVisible(true);
	    ROSMainPanel.setVisible(false);
	 }
	 
	 @Override
	 public void stoppageloading()
	 {
	    pageLoadingpanel.setVisible(false);
	    ROSMainPanel.setVisible(true);
	 }
	
	/**
	 * To draw system details list
	 * @param systemList - JsArray<SystemData> - JSObject with system details list
	 */
	
	int flag = 0;
	int evenflag = 0;
	int oddflag = 0;

	@Override
	public void drawSystemDetailsList(final List<ROSSystemBean> systemDetailsList,SOAPView soapview) {
		showpageLoading();
		systemDataPanel.clear();
		ROSDataPanel.clear();
		ROSDataPanel1.clear();
		systemDataVerticalPanel.clear();
		HorizontalPanel markingButtons = new HorizontalPanel();
		int row = 0;
		int column = 0;
		flag = 0;
		evenflag = 0;
		oddflag = 0;
		int systemDetailsLength=0, leftLength=0, rightLength=0;
		for (int i = 0; i < systemDetailsList.size(); i++) {/*
			systemDataVerticalPanel = new FlowPanel();
			notesText = new VerticalPanel();
			notesText.setWidth("100%");
			systemDataVerticalPanel.getElement().setAttribute("id",systemDetailsList.get(i).getSystemName().replaceAll("&#39;", "'"));
			systemDataVerticalPanel.getElement().setAttribute("style", "background-color: #FFF;");
			final HorizontalPanel systemName = new HorizontalPanel();
			final Label systemLabel = new Label(systemDetailsList.get(i).getSystemName().replaceAll("&#39;", "'"));
			final String systemNameNeeded = systemDetailsList.get(i).getSystemName().replaceAll("&#39;", "'");
			systemLabel.setStyleName(resources.cnmstyles().rosSystemLabel());
			systemLabel.getElement().getStyle().setFloat(Float.LEFT);
//			systemLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
//			systemLabel.getElement().getStyle().setColor("#6D6D72");
			systemLabel.addStyleName(resources.tabletstyles().headerLabelStyles());
			systemLabel.getElement().getStyle().setPaddingLeft(10, Unit.PX);
			systemName.add(systemLabel);
			systemName.setCellVerticalAlignment(systemLabel, HasVerticalAlignment.ALIGN_MIDDLE);
			final FastCheckbox markAllNormal = new FastCheckbox();
			markAllNormal.setText("Normal");
			markAllNormal.getElement().setAttribute("systemid",systemDetailsList.get(i).getSystemId()+"");
		
			if(normal==1){
				markAllNormal.setChecked("yes");
			}
			else{
				markAllNormal.setChecked("no");
			}
			final String systemId = systemDetailsList.get(i).getSystemId()+"";
			markAllNormal.addPressHandler(new PressHandler() {
				@Override
				public void onPress(PressEvent event) {				
					markAllNormal(markAllNormal, systemNameNeeded);
				}
			});
			systemName.add(markAllNormal);
			systemName.setCellVerticalAlignment(markAllNormal, HasVerticalAlignment.ALIGN_MIDDLE);
			
			if (Window.getClientHeight() > Window.getClientWidth()) {
				systemName.setCellWidth(systemLabel, "60%");
				systemName.setCellWidth(markAllNormal, "20%");
			} 
			else if (Window.getClientWidth() > Window.getClientHeight()) {
				systemName.setCellWidth(systemLabel, "60%");
				systemName.setCellWidth(markAllNormal, "20%");
			}
			Window.addResizeHandler(new ResizeHandler() {
				@Override
				public void onResize(ResizeEvent event) {
					if (Window.getClientHeight() > Window.getClientWidth()) {
						systemName.setCellWidth(systemLabel, "60%");
						systemName.setCellWidth(markAllNormal, "20%");
					} 
					else if (Window.getClientWidth() > Window.getClientHeight()) {
						systemName.setCellWidth(systemLabel, "60%");
						systemName.setCellWidth(markAllNormal, "20%");
					}

				}
			});
			
			List<ROSElementBean> systemDetails = systemDetailsList.get(i).getROSElements();
			if(systemDetails!=null)
				systemDetailsLength= systemDetails.size();
			else
				systemDetailsLength= 0;
			
			systemName.setStyleName(resources.cnmstyles().systemNameROS());
			if(systemDetails.size()>0)
			systemDataVerticalPanel.add(systemName);
			int normalFlag=0;
			for (int j = 0; j < systemDetails.size(); j++) {
				final HorizontalPanel hPanel = new HorizontalPanel();
				hPanel.setWidth("100%");
				hPanel.setHeight("50px");
				hPanel.getElement().setAttribute("id",systemDetailsList.get(i).getSystemName().replaceAll("&#39;", "'"));
				int datatype = systemDetails.get(j).getDataType();
				if (datatype == 3) {
					FastToggleCheckbox positiveChkBox = new FastToggleCheckbox("Yes", "1", "yes");
					positiveChkBox.getElement().setAttribute("clinicalelementgwid",systemDetails.get(j).getElementGWID());
					positiveChkBox.getElement().setAttribute("name",systemNameNeeded);
					positiveChkBox.getElement().setAttribute("iselementdirty","0");
					positiveChkBox.getElement().setAttribute("clinicalelementtype", datatype + "");
					positiveChkBox.getElement().setAttribute("elementoptionvalue", "1");
					positiveChkBox.getElement().setAttribute("systemid",systemId);
					positiveChkBox.addPressHandler(setClinicalElementDirty.pressHandler());
					positiveChkBox.addPressHandler(new PressHandler() {
						@Override
						public void onPress(PressEvent event) {
							 markAllNormal.setChecked("no");
						}			
					});
					final Label elementLabel = new Label(systemDetails.get(j).getElementName().replaceAll("&#39;", "'"));
					elementLabel.getElement().setAttribute("elementoptionvalue", "label");
					elementLabel.getElement().getStyle().setFontSize(16, Unit.PX);
					elementLabel.getElement().getStyle().setFloat(Float.LEFT);
					elementLabel.getElement().getStyle().setPaddingLeft(15, Unit.PX);
					negativeChkBox = new FastToggleCheckbox("No", "0", "yes");
					negativeChkBox.getElement().setAttribute("clinicalelementgwid",systemDetails.get(j).getElementGWID());
					negativeChkBox.getElement().setAttribute("name",systemNameNeeded);
					negativeChkBox.getElement().setAttribute("iselementdirty","0");
					negativeChkBox.getElement().setAttribute("clinicalelementtype", datatype + "");
					negativeChkBox.getElement().setAttribute("elementoptionvalue", "0");
					negativeChkBox.getElement().getStyle().setFloat(Float.LEFT);
					negativeChkBox.getElement().setAttribute("systemid",systemId);										
					negativeChkBox.addPressHandler(setClinicalElementDirty.pressHandler());
					hPanel.add(elementLabel);
					hPanel.setCellVerticalAlignment(elementLabel, HasVerticalAlignment.ALIGN_MIDDLE);
					if (Window.getClientHeight() > Window.getClientWidth())
						hPanel.setCellWidth(elementLabel, "60%");
					else if (Window.getClientWidth() > Window.getClientHeight())
						hPanel.setCellWidth(elementLabel, "60%");
					
					Window.addResizeHandler(new ResizeHandler() {
						@Override
						public void onResize(ResizeEvent event) {
							if (Window.getClientHeight() > Window
									.getClientWidth())
								hPanel.setCellWidth(elementLabel, "60%");
							else if (Window.getClientWidth() > Window
									.getClientHeight())
								hPanel.setCellWidth(elementLabel, "60%");
						}
					});
				
					final FastButton quickNotesIcon = new FastButton("\ua020", resources.tabletstyles().iconBlue(), resources.tabletstyles().iconBlueHold(), "");
					quickNotesIcon.addStyleName(resources.tabletstyles().glaceIcons());
					quickNotesIcon.getElement().getStyle().setFontSize(24, Unit.PX);
					quickNotesIcon.getElement().getStyle().setPaddingRight(15, Unit.PX);
					quickNotesIcon.getElement().getStyle().setLineHeight(40, Unit.PX);
					hPanel.add(negativeChkBox);
					hPanel.add(positiveChkBox);
					hPanel.add(quickNotesIcon);					

					hPanel.setCellWidth(elementLabel, "60%");
					hPanel.setCellWidth(negativeChkBox, "15%");
					hPanel.setCellWidth(positiveChkBox, "15%");
					hPanel.setCellWidth(quickNotesIcon, "10%");
					hPanel.setCellVerticalAlignment(negativeChkBox, HasVerticalAlignment.ALIGN_MIDDLE);
					hPanel.setCellVerticalAlignment(positiveChkBox, HasVerticalAlignment.ALIGN_MIDDLE);
					hPanel.setCellVerticalAlignment(quickNotesIcon, HasVerticalAlignment.ALIGN_MIDDLE);

					if (systemDetails.get(j).getValue().equals("false")) {
						negativeChkBox.setChecked("yes");
					} 
					else if (systemDetails.get(j).getValue().equals("true")) {
						positiveChkBox.setChecked("yes");
					}
					
					
					final JSONArray shortcutsJson = new JSONArray();
					final ROSElementAssociateBean associateData= systemDetails.get(j).getAssociateElement();
					final FastButton notesText = new FastButton();
					notesText.setVisible(false);					
					if (associateData != null) {
						final String notes = associateData.getElementValue();
						notesText.setText(notes);
						notesText.getElement().setAttribute("clinicalelementgwid",associateData.getElementGWID());
						notesText.getElement().setAttribute("clinicalelementtype",associateData.getElementType());
						notesText.getElement().setAttribute("elementoptionvalue", notes);
						notesText.getElement().setAttribute("iselementdirty","0");
					}
					hPanel.add(notesText);
					
					if(associateData != null) {						
						List<SoapElementDatalist> shortcutsData = associateData.getElementShortcuts();
						if(shortcutsData != null) {
							for(int c=0; c<shortcutsData.size(); c++) {
								String shortcutId = shortcutsData.get(c).getSoapElementDatalistId().toString();
								String data = shortcutsData.get(c).getSoapElementDatalistData();
								JSONObject shortcutObj = new JSONObject();
								shortcutObj.put("datalistid", new JSONString(shortcutId));
								shortcutObj.put("data", new JSONString(data));
								shortcutsJson.set(c, shortcutObj);
							}
						}
					}
					
					quickNotesIcon.setValue(shortcutsJson.toString());
					quickNotesIcon.addPressHandler(new PressHandler() {
						
						@Override
						public void onPress(PressEvent event) {							
							presenter.initializeQuickNotes("detailoption_"+associateData.getElementGWID()+"_text", notesText.getElement().getAttribute("elementoptionvalue"), quickNotesIcon.getValue(), elementLabel.getText(), new QuickNotesResponseHandler() {
								
								@Override
								public void callBack(JSONArray shortcutsArr, String notes, int isSave) {
									quickNotesIcon.setValue(shortcutsArr.toString());
									if(isSave == 1) {
										notesText.getElement().setAttribute("iselementdirty","1");
										notesText.getElement().setAttribute("elementoptionvalue", notes);
									}
								}
							});
						}
					});
					

					systemDataVerticalPanel.add(hPanel);
					
					
					
					if(positiveChkBox.isChecked() || !negativeChkBox.isChecked()){
						normalFlag=1;
						markAllNormal.setChecked("no");
					}
					if(normalFlag==0){
						markAllNormal.setChecked("yes");
					}
					
				} 
				else if (datatype == 2) {
					try {
						final FastButton Popupnotesiconbtn = new FastButton();
						Popupnotesiconbtn.setText("");
						Popupnotesiconbtn.getElement().setAttribute("clinicalelementgwid",systemDetails.get(j).getElementGWID());
						Popupnotesiconbtn.getElement().setAttribute("clinicalelementtype", datatype + "");
						Popupnotesiconbtn.getElement().setAttribute("iselementdirty", "0");
						Popupnotesiconbtn.setText("\ua020");
						Popupnotesiconbtn.setNormalStyle(resources.tabletstyles().iconBlue());
						Popupnotesiconbtn.setHoldPressStyle(resources.tabletstyles().iconBlueHold());
						Popupnotesiconbtn.addStyleName(resources.tabletstyles().glaceIcons());
						Popupnotesiconbtn.getElement().getStyle().setFontSize(24, Unit.PX);
						Popupnotesiconbtn.getElement().getStyle().setLineHeight(46, Unit.PX);
//						Popupnotesiconbtn.getElement().getStyle().setPaddingLeft(20, Unit.PCT);
						Popupnotesiconbtn.getElement().getStyle().setPaddingRight(15, Unit.PX);
						Popupnotesiconbtn.getElement().getStyle().setTextAlign(TextAlign.CENTER);
						Popupnotesiconbtn.getElement().getStyle().setFloat(Float.RIGHT);
				
						if (systemDetails.get(j).getValue().equals("")) {
							Popupnotesiconbtn.setVisible(true);
						} 
						else {
							Popupnotesiconbtn.setVisible(true);
							Popupnotesiconbtn.getElement().setAttribute("elementoptionvalue",systemDetails.get(j).getValue());
						}

						final Label Notes = new Label();
						Notes.setStyleName(resources.cnmstyles().rostextArea());
						Notes.addStyleName(resources.cnmstyles().alignMiddle());
						Notes.getElement().getStyle().setPadding(10, Unit.PX);
						Notes.setText(systemDetails.get(j).getValue());
						
						if (Notes.getText().length() > 250)
							Notes.setText(Notes.getText().substring(0, 225)+ "...");
						
						Notes.addStyleName(resources.cnmstyles().borderNoneStyle());
						
						final String gwId = systemDetails.get(j).getElementGWID();
						final ROSElementAssociateBean associateBean= systemDetails.get(j).getAssociateElement();
						final FastButton notesTextHdn = new FastButton();
						notesTextHdn.setVisible(false);					
						final String notes = systemDetails.get(j).getValue();
						notesTextHdn.setText(notes);
						notesTextHdn.getElement().setAttribute("clinicalelementgwid",gwId);
						notesTextHdn.getElement().setAttribute("clinicalelementtype",systemDetails.get(j).getDataType()+"");
						notesTextHdn.getElement().setAttribute("elementoptionvalue", notes);
						notesTextHdn.getElement().setAttribute("iselementdirty","0");
						
						final JSONArray shortcutsJson = new JSONArray();
						if(associateBean != null) {
							List<SoapElementDatalist> shortcutList = associateBean.getElementShortcuts();
							for(int c=0; c<shortcutList.size(); c++) {
								String shortcutId = shortcutList.get(c).getSoapElementDatalistId().toString();
								String data = shortcutList.get(c).getSoapElementDatalistData();
								JSONObject shortcutObj = new JSONObject();
								shortcutObj.put("datalistid", new JSONString(shortcutId));
								shortcutObj.put("data", new JSONString(data));
								shortcutsJson.set(c, shortcutObj);
							}
						}
						
						Popupnotesiconbtn.setValue(shortcutsJson.toString());
						Popupnotesiconbtn.addPressHandler(new PressHandler() {
							@Override
							public void onPress(PressEvent event) {
								

								presenter.initializeQuickNotes("element_"+gwId, notesTextHdn.getElement().getAttribute("elementoptionvalue"), Popupnotesiconbtn.getValue(), "Notes", new QuickNotesResponseHandler() {
									
									@Override
									public void callBack(JSONArray shortcutsArr, String notes, int isSave) {
										Popupnotesiconbtn.setValue(shortcutsArr.toString());
										if(isSave == 1) {
											notesTextHdn.getElement().setAttribute("iselementdirty","1");
											notesTextHdn.getElement().setAttribute("elementoptionvalue", notes);
											Notes.setText(notes);
											if(Notes.getText().trim().length()>0){
												Notes.setStyleName(resources.cnmstyles().borderTopStyle());								
												Notes.addStyleName(resources.cnmstyles().rostextArea());
												Notes.addStyleName(resources.cnmstyles().alignMiddle());
												Notes.getElement().getStyle().setPadding(10, Unit.PX);																		
											}
											else{
												Notes.setStyleName(resources.cnmstyles().borderNoneStyle());		
												Notes.getElement().getStyle().setPadding(0, Unit.PX);
											}
											
											if (Notes.getText().length() > 250)
												Notes.setText(Notes.getText().substring(0, 225) + "...");

										}
										else {
											notesTextHdn.getElement().setAttribute("iselementdirty","0");
											notesTextHdn.getElement().setAttribute("elementoptionvalue", notes);
										}
									}
								});
							
							}
						});
						
						systemName.add(Popupnotesiconbtn);
						systemName.setCellWidth(Popupnotesiconbtn, "20%");
						systemName.setCellVerticalAlignment(Popupnotesiconbtn, HasVerticalAlignment.ALIGN_MIDDLE);
						
						Notes.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								

								presenter.initializeQuickNotes("element_"+gwId, notesTextHdn.getElement().getAttribute("elementoptionvalue"), Popupnotesiconbtn.getValue(), "Notes", new QuickNotesResponseHandler() {
									
									
									
									@Override
									public void callBack(JSONArray shortcutsArr, String notes, int isSave) {
										Popupnotesiconbtn.setValue(shortcutsArr.toString());
										if(isSave == 1) {
											notesTextHdn.getElement().setAttribute("iselementdirty","1");
											notesTextHdn.getElement().setAttribute("elementoptionvalue", notes);
											notesTextHdn.setValue(notes);
											Notes.setText(notes);
											if(Notes.getText().trim().length()>0){
												Notes.setStyleName(resources.cnmstyles().borderTopStyle());								
												Notes.addStyleName(resources.cnmstyles().rostextArea());
												Notes.addStyleName(resources.cnmstyles().alignMiddle());
												Notes.getElement().getStyle().setPadding(10, Unit.PX);																		
											}
											else{
												Notes.setStyleName(resources.cnmstyles().borderNoneStyle());
												Notes.getElement().getStyle().setPadding(0, Unit.PX);
											}
											
											if (Notes.getText().length() > 250)
												Notes.setText(Notes.getText().substring(0, 225) + "...");

										}
										else {
											notesTextHdn.getElement().setAttribute("iselementdirty","0");
											notesTextHdn.getElement().setAttribute("elementoptionvalue", notes);
										}
									}
								});
							
							}
						});
						
						notesText.add(Notes);
						notesText.add(notesTextHdn);
						if(Notes.getText().trim().length()>0){
							Notes.setStyleName(resources.cnmstyles().borderTopStyle());								
							Notes.addStyleName(resources.cnmstyles().rostextArea());
							Notes.addStyleName(resources.cnmstyles().alignMiddle());
							Notes.getElement().getStyle().setPadding(10, Unit.PX);
						}
						else{
							Notes.setStyleName(resources.cnmstyles().borderNoneStyle());							
						}
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				systemDataVerticalPanel.getElement().getStyle().setMarginBottom(4.5, Unit.PCT);
			}
			systemDataVerticalPanel.add(notesText);

			if (systemName.getWidgetCount() <= 2) {
				final FastButton Popupnotesiconbtn = new FastButton();
				Popupnotesiconbtn.setText("");
				systemName.add(Popupnotesiconbtn);
				systemName.setCellWidth(Popupnotesiconbtn, "20%");
				notesText.setVisible(false);
			}
			
			if(leftLength<=rightLength){
				leftLength+= systemDetailsLength;
				ROSDataPanel.add(systemDataVerticalPanel);
			}
			else {
				rightLength+= systemDetailsLength;
				ROSDataPanel1.add(systemDataVerticalPanel);
			}
			
			if (i % 2 == 0) {
				if (flag == 0) {
					flag = flag + 1;
					ROSDataPanel.add(systemDataVerticalPanel);
					evenflag = evenflag + 1 + systemDetails.length();
				} 
				else {
					if (evenflag > oddflag) {
						ROSDataPanel1.add(systemDataVerticalPanel);
						oddflag = oddflag + systemDetails.length() + 1;
					} 
					else {
						ROSDataPanel.add(systemDataVerticalPanel);
						evenflag = evenflag + systemDetails.length() + 1;
					}
				}
			}
			else {
				if (evenflag > oddflag) {
					ROSDataPanel1.add(systemDataVerticalPanel);
					oddflag = oddflag + systemDetails.length() + 1;
				} 
				else {
					ROSDataPanel.add(systemDataVerticalPanel);
					evenflag = evenflag + systemDetails.length() + 1;
				}
			}
			column++;
			if (column == 2) {
				column = 0;
				row++;
			}
		*/}
		ROSDataPanel.getElement().getStyle().setFloat(Float.LEFT);
		ROSDataPanel1.getElement().getStyle().setFloat(Float.LEFT);
		systemDataPanel.add(ROSDataPanel);
		systemDataPanel.add(ROSDataPanel1);
		presenter.fetchNotes();
		stoppageloading();
	}

	public void markAllNormal(FastCheckbox markAllNormal, String systemName) {
		if (markAllNormal.isChecked()){
			Widget parentPanel = markAllNormal.getParent().getParent();
			if (parentPanel instanceof FlowPanel) {
				FlowPanel parentPaneltoMark = (FlowPanel) markAllNormal.getParent().getParent();
				for (int k = 0; k < parentPaneltoMark.getWidgetCount(); k++) {
					if (parentPaneltoMark.getWidget(k) instanceof HorizontalPanel) {
						if (parentPaneltoMark.getWidget(k).getElement().getAttribute("id").equalsIgnoreCase(systemName)) {
							HorizontalPanel elementsPanel = new HorizontalPanel();
							elementsPanel = (HorizontalPanel) parentPaneltoMark.getWidget(k);
							for (int i = 0; i < elementsPanel.getWidgetCount(); i++) {
								if (elementsPanel.getWidget(i) instanceof FastToggleCheckbox) {
									if (elementsPanel.getWidget(i).getElement().getAttribute("name").equalsIgnoreCase(systemName)) {
										if (elementsPanel.getWidget(i).getElement().getAttribute("elementoptionvalue").equalsIgnoreCase("0")) {
											FastToggleCheckbox checkboxnormal = (FastToggleCheckbox) elementsPanel.getWidget(i);
											checkboxnormal.getElement().setAttribute("iselementdirty","1");
											checkboxnormal.setChecked("yes");
										}
										if (elementsPanel.getWidget(i).getElement().getAttribute("elementoptionvalue").equalsIgnoreCase("1")) {
											FastToggleCheckbox checkboxnotnormal = (FastToggleCheckbox) elementsPanel.getWidget(i);
											checkboxnotnormal.getElement().setAttribute("iselementdirty","0");
											checkboxnotnormal.setChecked("no");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		else if (!markAllNormal.isChecked()) {
			Widget parentPanel = markAllNormal.getParent().getParent();
			if (parentPanel instanceof FlowPanel) {
				FlowPanel parentPaneltoMark = (FlowPanel) markAllNormal.getParent().getParent();
				for (int k = 0; k < parentPaneltoMark.getWidgetCount(); k++) {
					if (parentPaneltoMark.getWidget(k) instanceof HorizontalPanel) {
						if (parentPaneltoMark.getWidget(k).getElement().getAttribute("id").equalsIgnoreCase(systemName)) {
							HorizontalPanel elementsPanel = new HorizontalPanel();
							elementsPanel = (HorizontalPanel) parentPaneltoMark.getWidget(k);
							for (int i = 0; i < elementsPanel.getWidgetCount(); i++) {
								if (elementsPanel.getWidget(i) instanceof FastToggleCheckbox) {
									if (elementsPanel.getWidget(i).getElement().getAttribute("name").equalsIgnoreCase(systemName)) {
										if (elementsPanel.getWidget(i).getElement().getAttribute("elementoptionvalue").equalsIgnoreCase("0")) {											
											FastToggleCheckbox checkboxnormal = (FastToggleCheckbox) elementsPanel.getWidget(i);
											checkboxnormal.getElement().setAttribute("iselementdirty","1");
											checkboxnormal.setChecked("no");
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void drawNotes(final String notesValue) {
		try{
			systemDataPanel.add(presenter.initialize(notesValue.replace("\"", "").replaceAll("\\\\n", "\n").trim(),"0000100400000000001",2));
		}catch(Exception e){
			IpadErrorLog.storeInLocalStorage(e.getMessage());
			//e.printStackTrace();
			systemDataPanel.add(presenter.initialize("","0000100400000000001",2));
		}
	}

	@Override
	public void drawShortcut(JsArray<ROSShortcutsData> shortcutdata) {
	}

	@Override
	public void drawShortcutSearch(String dataValue,HPIShortcutHandler hpiShortcutHandler) {	
	}

	HandlerRegistration handler;

	@Override
	public void addhandlers() {
		saveButton.addPressHandler(new PressHandler() {
			@Override
			public void onPress(PressEvent event) {
				presenter.saveFunction();
			}
		});
		cancelButton.addPressHandler(new PressHandler() {
			@Override
			public void onPress(PressEvent event) {
				presenter.cancelFunction();
			}
		});
	}

	public void setHeightsandWidths()
	{
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if(Window.getClientHeight()>Window.getClientWidth())
				{
					systemDataPanel.setWidth("100%");
					systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(134+49), Unit.PX);
					pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(134+49), Unit.PX);
					if(Location.getHref().contains("pastencounters"))
					{
						systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(190+48), Unit.PX);
					    pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(190+48), Unit.PX);
					}
				}
				else
				{
					systemDataPanel.setWidth("100%");
					systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(154+48), Unit.PX);
				    pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(154+48), Unit.PX);
					if(Location.getHref().contains("pastencounters"))
					{
						systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(210+48), Unit.PX);
						pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(210+48), Unit.PX);
					}
				}
			}
		});

		if(Window.getClientHeight()>Window.getClientWidth())
		{
			systemDataPanel.setWidth("100%");
			systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(134+49), Unit.PX);
		    pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(134+49), Unit.PX);
			if(Location.getHref().contains("pastencounters"))
			{
				systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(190+48), Unit.PX);
				pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(190+48), Unit.PX);
			}
		}
		else{
			systemDataPanel.setWidth("100%");
			systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(154+48), Unit.PX);
		    pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(154+48), Unit.PX);
			if(Location.getHref().contains("pastencounters"))
			{
				systemDataPanelscrollbar.getElement().getStyle().setHeight(Window.getClientHeight()-(210+48), Unit.PX);
				pageLoadingpanel.getElement().getStyle().setHeight(Window.getClientHeight()-(210+48), Unit.PX);
			}
		}
	}	
		
		
	StringBuffer elements;
	StringBuffer systems;
	
	/**
	 * To get Elements from Panels (to perform Mark All, Clear All, Mark Exempted All)
	 * @param rosDataPanel - Panel which contains elements
	 * @param rosFlag - Flag to identify Mark All, Clear All and Mark Exempted All
	 * @param markExemptedFlag - Flag to identify collecting not exempted elements function,Mark Exempted All 
	 */
	public void getROSChildPanel(Widget rosDataPanel, int rosFlag,int markExemptedFlag){
		if(markExemptedFlag==0){
			if(rosDataPanel instanceof HasWidgets){ 	
				if((HasWidgets) rosDataPanel!=null){
					Iterator<Widget> arrayOfWidgets = ((HasWidgets) rosDataPanel).iterator();   
					Widget childWidget = null ;
					while (arrayOfWidgets.hasNext()){
						childWidget = arrayOfWidgets.next();
						if(childWidget instanceof HasWidgets)
							getROSChildPanel(childWidget,rosFlag,markExemptedFlag);
						else if(!childWidget.getElement().getAttribute("iselementdirty").equals("")  && rosFlag!=3)
							getROSChildElements(childWidget,rosFlag,markExemptedFlag);
						else if(!childWidget.getElement().getAttribute("iselementdirty").equals("") && rosFlag==3)
							getROSChildElements(childWidget,rosFlag,markExemptedFlag);  
						else if(!childWidget.getElement().getAttribute("systemid").equals(""))
							getROSChildElements(childWidget,rosFlag,markExemptedFlag);
					}  
				} 
			}
		}
		else if(markExemptedFlag==1){
			if(rosDataPanel instanceof HasWidgets){ 	
	    		if((HasWidgets) rosDataPanel!=null){
	    			Iterator<Widget> arrayOfWidgets = ((HasWidgets) rosDataPanel).iterator();   
	    			Widget childWidget = null ;
	    			while (arrayOfWidgets.hasNext()){
	    				childWidget = arrayOfWidgets.next();
	    				if(childWidget instanceof HasWidgets)
	    					getROSChildPanel(childWidget,rosFlag,markExemptedFlag);
	    				else if(!childWidget.getElement().getAttribute("iselementdirty").equals(""))
	    					getROSChildElements(childWidget,rosFlag,markExemptedFlag);
	    				else if(!childWidget.getElement().getAttribute("systemid").equals("") && rosFlag==3)
							getROSChildElements(childWidget,rosFlag,markExemptedFlag);
	    			}  
	    		} 
	    	}
		}		
	}
	
	/**
	 * To mark Elements from Panels (to perform Mark All, Clear All, Mark Exempted All)
	 * @param childWidget - Element
	 * @param rosFlag - Flag to identify Mark All, Clear All and Mark Exempted All
	 * @param markExemptedFlag - Flag to identify collecting not exempted elements function,Mark Exempted All 
	 */
	public void getROSChildElements(Widget childWidget,int rosFlag,int markExemptedFlag){
		try{
	        if(childWidget instanceof FastToggleCheckbox)
	        {	
	        	FastToggleCheckbox rosElement=((FastToggleCheckbox) childWidget);
	        	String optionValue=rosElement.getElement().getAttribute("elementoptionvalue");	
	        	if(markExemptedFlag==0){     		           
	        		if(!rosElement.isChecked() && rosFlag==1 && optionValue.equalsIgnoreCase("0")){
	        			rosElement.setChecked("yes");
	        			rosElement.getElement().setAttribute("iselementdirty","1");
	        		}	     
	        		else if(rosElement.isChecked() && rosFlag==1 && optionValue.equalsIgnoreCase("1")){
	        			rosElement.setChecked("no");
	        			rosElement.getElement().setAttribute("iselementdirty","1");
	        		}
	        		else if(rosElement.isChecked() && rosFlag==2 && optionValue.equalsIgnoreCase("0")){
	        			rosElement.setChecked("no");
	        			rosElement.getElement().setAttribute("iselementdirty","1");
	        		}
	        		else if(!rosElement.isChecked()  && rosFlag==3 && optionValue.equalsIgnoreCase("0")){
	        			String elementsIds[]=elements.toString().split(",");
	        			int exemptFlag=0;
	        			if(elementsIds.length>0){
	        				for(int i=0;i<elementsIds.length;i++){
	        					if(rosElement.getElement().getAttribute("clinicalelementgwid").equalsIgnoreCase(elementsIds[i])){
	        						exemptFlag=1;
	        					}
	        				}
	        			}
	        			if(exemptFlag==0){
	        				rosElement.setChecked("yes");
    	        			rosElement.getElement().setAttribute("iselementdirty","1");
	        			}
	        		}
	        	}
	        	else if(rosFlag==3 && markExemptedFlag==1 ){
	        		if(rosElement.isChecked() && rosFlag==3 && optionValue.equalsIgnoreCase("1")){
	        			if(elements.length()>0){
	        				elements.append(",");
	        			}
	        			if(systems.length()>0){
	        				systems.append(",");	
	        			}
	        			elements.append(rosElement.getElement().getAttribute("clinicalelementgwid").toString());	
	        			systems.append(rosElement.getElement().getAttribute("systemid").toString());
	        		}   		
	        	}	
	        }
	        else if(childWidget instanceof FastCheckbox){
	        	FastCheckbox rosElement=((FastCheckbox) childWidget);
	        	if(!rosElement.isChecked() && rosFlag==1){
	        		rosElement.setChecked("yes");
	        	} 
	        	else if(rosElement.isChecked() && rosFlag==2){
	        		rosElement.setChecked("no");
	        	} 
	        	else if(!rosElement.isChecked() && rosFlag==3 && markExemptedFlag==0){
	        		String systemsIds[]=systems.toString().split(",");
	        		int exemptSystemFlag=0;
        			if(systemsIds.length>0){
        				for(int i=0;i<systemsIds.length;i++){
        					if(rosElement.getElement().getAttribute("systemid").equalsIgnoreCase(systemsIds[i])){
        						exemptSystemFlag=1;
        					}
        				}
        			}
        			if(exemptSystemFlag==0){
        				rosElement.setChecked("yes");
        			}
	        	} 
	        }
		}
		catch(Exception e){	
			IpadErrorLog.storeInLocalStorage(e.getMessage());
		}		
	}
	
	//for import option 
		@Override
		public void drawEncounterList(JsArray<ImportEncounterData> importEncData,FastButton importButton) {
			
			if(importEncData.length()==0)
				new GlaceAlert("No previous visit");
			else {
			final PopupPanel importPopup=new PopupPanel(true);
			importPopup.setGlassEnabled(true);
			importPopup.setAutoHideEnabled(true);
			importPopup.getElement().setAttribute("style","min-height: 100px !important;");
			importPopup.addStyleName(resources.tabletstyles().popOverStyle());
			importPopup.getElement().getStyle().setBackgroundColor("transparent");
			importPopup.getElement().getStyle().setMarginTop(-10, Unit.PX);

			VerticalPanel outerPanel=new VerticalPanel();
			Label arrowLabel=new Label();
			arrowLabel.setStyleName(resources.cnmstyles().pfshPopupArrowUp());
			arrowLabel.getElement().getStyle().setMarginLeft(390, Unit.PX);
			
			final VerticalPanel innerPanel=new VerticalPanel();
			innerPanel.setStyleName(resources.cnmstyles().pfshPopover());				
			FlexTable importTable=new FlexTable();
		
			final int resultLen = importEncData.length();
			
			ScrollPanel importScroll=new ScrollPanel();
			importScroll.setHeight("249px");
			importScroll.getElement().getStyle().setProperty("overflowX","hidden");				
			importScroll.clear();	
			importScroll.addStyleName(resources.tabletstyles().smoothScrolling());
			
			
			for(int j=0;j<importEncData.length();j++)
			{
				
				HorizontalPanel importHPanel=new HorizontalPanel();
				VerticalPanel importVPanel=new VerticalPanel();
				final Label importLabel= new Label(importEncData.get(j).getEncounterDate());
				
				final FastButton virtualArea;
				if(j==0) {
					virtualArea= new FastButton("", resources.cnmstyles().pfshClickableBgFirst(),  resources.cnmstyles().pfshClickableBgFirstHold() , "");
					importHPanel.addStyleName(resources.cnmstyles().pfshFirstHPanel());
				}
				else if(j==resultLen-1) {
					virtualArea= new FastButton("", resources.cnmstyles().pfshClickableBgLast(),  resources.cnmstyles().pfshClickableBgLastHold() , "");
					importHPanel.addStyleName(resources.cnmstyles().pfshLastHPanel());
				}
				else {
				virtualArea= new FastButton("", resources.cnmstyles().pfshClickableBg(),  resources.cnmstyles().pfshClickableBgHold(), "");
				}
				virtualArea.setValue(importEncData.get(j).getEncounterId());
				virtualArea.setWidth("450px");
				importHPanel.add(importLabel);
				importVPanel.add(importHPanel);															
				importVPanel.add(virtualArea);
				virtualArea.addStyleName(resources.tabletstyles().userSelectNone());
				importHPanel.setWidth("100%");
				importVPanel.setWidth("100%");
				importVPanel.getElement().getStyle().setMarginLeft(5,Unit.PX);
				importVPanel.getElement().getStyle().setMarginRight(5,Unit.PX);
				importVPanel.setWidth("450px");
				importTable.setWidget(j, 0, importVPanel);
															
				importHPanel.setStyleName(resources.cnmstyles().pfshPopupHPanel());
					
				importHPanel.setCellVerticalAlignment(importLabel, HasVerticalAlignment.ALIGN_MIDDLE);
				importHPanel.setCellHorizontalAlignment(importLabel, HasHorizontalAlignment.ALIGN_CENTER);
				
				
				importLabel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						importPopup.hide();	
						showpageLoading();
						presenter.setData(Integer.parseInt(virtualArea.getValue()));
					}
				});
				
				virtualArea.addPressHandler(new PressHandler() {
					
					@Override
					public void onPress(PressEvent event) {
						
						importPopup.hide();	
						showpageLoading();
						presenter.setData(Integer.parseInt(virtualArea.getValue()));
						
					}
				});
				
			}
			importTable.setCellPadding(0);
			importTable.setCellSpacing(0);
			importTable.setWidth("100%");
			
			if(resultLen>5)
			{
				importScroll.add(importTable);
				innerPanel.add(importScroll);
			}
			else
				innerPanel.add(importTable);
			
			outerPanel.add(arrowLabel);
			outerPanel.add(innerPanel);
			importPopup.setWidget(outerPanel);								
			importPopup.showRelativeTo(importButton);		
			importPopup.show();
			
			Window.addResizeHandler(new ResizeHandler() {
				
				@Override
				public void onResize(ResizeEvent event) {
					importPopup.hide();
				}
			});

		}
		}

	@Override
	public ROSClient getROSClient() {
		return rosClient;
		
	}

	@Override
	public ROSSystemBeanCodec getRosSystemBeanCodec() {
		return rosSystemBeanCodec;
	}

	public void save(Widget savePanel){
		final SaveData obj = new SaveData(actionServletURL); 
        obj.getPanelToSave(savePanel,encounterId,chartId,patientId,templateId,formId); 
	}

	public void save(Widget systemDataPanel,saveDataResponseHandler saveDataResponseHandler) {
		final SaveData obj = new SaveData(actionServletURL); 
        obj.getPanelToSaveAndGetResponse(systemDataPanel,encounterId,chartId,patientId,templateId,formId,saveDataResponseHandler); 
		
	}

	@Override
	public void setVariables(String encounterId, String chartId,
			String patientId, String templateId, String formId,
			ActionServletURL actionServletURL) {
		this.actionServletURL= actionServletURL;
		ROSTabletView.encounterId=encounterId;
		ROSTabletView.chartId=chartId;
		ROSTabletView.patientId=patientId;
		ROSTabletView.templateId=templateId;
		ROSTabletView.formId=formId;
	}

}

