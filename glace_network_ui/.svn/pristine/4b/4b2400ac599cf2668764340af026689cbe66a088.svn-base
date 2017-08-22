package com.glenwood.network.client.customwidgets;


import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;

public final class WindowController {
	private final AbsolutePanel boundaryPanel;
	private PickupDragController pickupDragController;
	private ResizeDragController resizeDragController;
	public WindowController(AbsolutePanel boundaryPanel) {
		this.boundaryPanel = boundaryPanel;
		pickupDragController = new PickupDragController(boundaryPanel, true);
		pickupDragController.setBehaviorConstrainedToBoundaryPanel(true);
		pickupDragController.setBehaviorMultipleSelection(false);
		resizeDragController = new ResizeDragController(boundaryPanel);
		resizeDragController.setBehaviorConstrainedToBoundaryPanel(true);
		resizeDragController.setBehaviorMultipleSelection(false);
	}
	public AbsolutePanel getBoundaryPanel() {
		return boundaryPanel;
	}
	public PickupDragController getPickupDragController() {
		return pickupDragController;
	}
	public ResizeDragController getResizeDragController() {
		return resizeDragController;
	}
	
	
}
