package com.glenwood.network.client.application.widget.draggable;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.glenwood.network.client.application.main.MainDesktopView;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * Drag Controller for simple widget
 * **/
public class SimpleWidgetDragController extends PickupDragController {

	MainDesktopView view;
	
	public SimpleWidgetDragController(MainDesktopView mainDesktopView, AbsolutePanel boundaryPanel,boolean allowDroppingOnBoundaryPanel) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
		this.view = mainDesktopView;
	}
	
	/**
	 * To change viewport in map
	 * **/
	public void dragEnd() {
	   
	    super.dragEnd();
	    view.dragEnd();
	  }
	


}
