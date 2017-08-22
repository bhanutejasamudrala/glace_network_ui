package com.glenwood.network.client.customwidgets.selectoionControl;

import java.util.List;

import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.glenwood.network.client.application.domain.Node;
import com.glenwood.network.client.resource.DesktopResources;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Bala Bhaskar
 *
 */
public class SelectionControl extends Composite implements HasHandlers, SelectionControlIf{

	private static SelectionControlUiBinder uiBinder = GWT.create(SelectionControlUiBinder.class);

	@UiField
	TextBox textSearcher;
	
	@UiField
	FocusPanel remove;
	
	@UiField
	FocusPanel add;
	
	@UiField
	FlowPanel inputFilter;
	
	@UiField
	static DesktopResources resources;
	
	@UiField
	Label noResult;
	
	ShowMorePagerPanel pagerPanel;
	
	CellList<SearchResult> cellList;
	
	private Timer timer;
	
	private HandlerManager handlerManager;
	
	interface SelectionControlUiBinder extends UiBinder<Widget, SelectionControl> {
	}

	public SelectionControl() {
		initWidget(uiBinder.createAndBindUi(this));
		initControl();
		setStyles();
	}
	
	/**
	 * Method to set styles
	 */
	private void setStyles() {
		pagerPanel.addStyleName(resources.selectionControl().selectionResultWraper());
	}

	/**
	 * To initialize the search control
	 */
	private void initControl() {
		handlerManager = new HandlerManager(this);
		pagerPanel = new ShowMorePagerPanel(20);
		//cellList = new CellList<SearchResult>(cell);
		pagerPanel.setHeight("92px");
		inputFilter.add(pagerPanel);
	}


	/**
	 * Method to change the value change handler
	 * @param ev
	 */
	@UiHandler("textSearcher")
	public void keyWorkChanged(KeyUpEvent ev){
		//pagerPanel.clear();
		String searchKeyWord =  getSearchKeyWord();
		
		final SearchEvent searchEvent = new SearchEvent(searchKeyWord);
		
		if(!searchKeyWord.trim().equals("")){
			if (timer!=null) {
				timer.cancel();
			}
			timer = new Timer() {
				@Override
				public void run() {
					handlerManager.fireEvent(searchEvent);
				}
			};
			timer.schedule(1000);
		}
		
	}
	
	/**
	 * Method to set loading image
	 */
	public void setLoading(){
		//loading.setSpin(true);
	//	pagerPanel.remove(loading);
	//	pagerPanel.add(loading);
	}
	
	/**
	 * Method to hide loading
	 */
	public void hideLoading(){
		//pagerPanel.remove(loading);
	}
	
	/**
	 * Method to get search keyword
	 */
	@Override
	public String getSearchKeyWord() {
		return textSearcher.getText();
	}
	
	/**
	 * Adds a {@link SearchEvent} handler
	 * 
	 * @param handler the search event handler
	 * @return {@link HandlerRegistration}
	 */
	public HandlerRegistration addSearchEventHandler(SearchEventHandler handler){
		return handlerManager.addHandler(SearchEvent.TYPE, handler);
	}

	/**
	 * To set node list in the input
	 * @param results
	 */
	public void addInput(List<SearchResult> results) {
		/*mainPanel.remove(noresult);
		noResult.setVisible(false);
		if(searchResult.isEmpty()&& offset==0){
			//pagerPanel.setVisible(false);
			mainPanel.add(noresult);
		}
		cellList.setRowData(offset,searchResult);
		int newSize = offset + searchResult.size();	
		pagerPanel.setLoadingIndicator(false);

		if(newSize>=totalCount){
			offset=totalCount;
		}else{
			offset=newSize;
		}*/
	}
	

	/**
	 * Class representing each entry in the results section.
	 * 
	 */

	static class SearchResultCell extends AbstractCell<SearchResult>{
		@Override
		public void render(Context context,SearchResult value, SafeHtmlBuilder sb) {
			if (value == null){
				return;
			}
			if(value.getValue().getKey().equalsIgnoreCase("proc")){
				Icon processing = new Icon(IconType.UNDO);
				processing.setSpin(true);
				sb.appendHtmlConstant(processing.getElement().getString());
			}else{
				if(!value.getDisplayHTML().trim().equals("") && value.getDisplayHTML()!=null){
					sb.appendHtmlConstant(value.getDisplayHTML());
				}else{
					sb.appendHtmlConstant(value.getDescription());
				}
			}
		}
	}

}
