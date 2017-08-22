package com.glenwood.network.client.customwidgets;

import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public final class WindowPanel extends FocusPanel {
	/**
	 * WindowPanel direction constant, used in
	 * {@link ResizeDragController#makeDraggable(Widget, DirectionConstant)}.
	 */
	public static class DirectionConstant {
		public final int directionBits;
		public final String directionLetters;
		private DirectionConstant(int directionBits, String directionLetters) {
			this.directionBits = directionBits;
			this.directionLetters = directionLetters;
		}
	}
	/**
	 * Specifies that resizing occur at the east edge.
	 */
	public static final int DIRECTION_EAST = 0x0001;
	/**
	 * Specifies that resizing occur at the both edge.
	 */
	public static final int DIRECTION_NORTH = 0x0002;
	/**
	 * Specifies that resizing occur at the south edge.
	 */
	public static final int DIRECTION_SOUTH = 0x0004;
	/**
	 * Specifies that resizing occur at the west edge.
	 */
	public static final int DIRECTION_WEST = 0x0008;
	/**
	 * Specifies that resizing occur at the east edge.
	 */
	public static final DirectionConstant EAST = new DirectionConstant(DIRECTION_EAST, "e");
	/**
	 * Specifies that resizing occur at the both edge.
	 */
	public static final DirectionConstant NORTH = new DirectionConstant(DIRECTION_NORTH, "n");
	/**
	 * Specifies that resizing occur at the north-east edge.
	 */
	public static final DirectionConstant NORTH_EAST = new DirectionConstant(DIRECTION_NORTH
			| DIRECTION_EAST, "ne");
	/**
	 * Specifies that resizing occur at the north-west edge.
	 */
	public static final DirectionConstant NORTH_WEST = new DirectionConstant(DIRECTION_NORTH
			| DIRECTION_WEST, "nw");
	/**
	 * Specifies that resizing occur at the south edge.
	 */
	public static final DirectionConstant SOUTH = new DirectionConstant(DIRECTION_SOUTH, "s");
	/**
	 * Specifies that resizing occur at the south-east edge.
	 */
	public static final DirectionConstant SOUTH_EAST = new DirectionConstant(DIRECTION_SOUTH
			| DIRECTION_EAST, "se");
	/**
	 * Specifies that resizing occur at the south-west edge.
	 */
	public static final DirectionConstant SOUTH_WEST = new DirectionConstant(DIRECTION_SOUTH
			| DIRECTION_WEST, "sw");
	/**
	 * Specifies that resizing occur at the west edge.
	 */
	public static final DirectionConstant WEST = new DirectionConstant(DIRECTION_WEST, "w");
	private static final int BORDER_THICKNESS = 0;
	private static final String CSS_DEMO_RESIZE_EDGE = "demo-resize-edge";
	private static final String CSS_DEMO_RESIZE_PANEL = "demo-WindowPanel";
	private static final String CSS_DEMO_RESIZE_PANEL_HEADER = "demo-WindowPanel-header";
	private int contentHeight;
	private Widget contentOrScrollPanelWidget;
	private int contentWidth;
	private Widget eastWidget;
	private Grid grid = new Grid(3, 3);
	private final FocusPanel headerContainer;
	private final Widget headerWidget;
	private boolean initialLoad = false;
	private Widget northWidget;
	private Widget southWidget;
	private Widget westWidget;
	public WindowPanel(final WindowController windowController, Widget headerWidget,
			Widget contentWidget, boolean wrapContentInScrollPanel) {
		this.headerWidget = headerWidget;
		addStyleName(CSS_DEMO_RESIZE_PANEL);
		contentOrScrollPanelWidget = wrapContentInScrollPanel ? new ScrollPanel(contentWidget)
		: contentWidget;
		headerContainer = new FocusPanel();
		headerContainer.addStyleName(CSS_DEMO_RESIZE_PANEL_HEADER);
		headerContainer.add(headerWidget);
		windowController.getPickupDragController().makeDraggable(this, headerContainer);
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AbsolutePanel boundaryPanel = windowController.getBoundaryPanel();
				if (boundaryPanel.getWidgetIndex(WindowPanel.this) < boundaryPanel.getWidgetCount() - 1) {
					// force our panel to the top of our z-index context
					WidgetLocation location = new WidgetLocation(WindowPanel.this, boundaryPanel);
					boundaryPanel.add(WindowPanel.this, location.getLeft(), location.getTop());
				}
			}
		});
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(headerContainer);
		verticalPanel.add(contentOrScrollPanelWidget);
		grid.setCellSpacing(0);
		grid.setCellPadding(0);
		add(grid);
		setupCell(0, 0, NORTH_WEST);
		northWidget = setupCell(0, 1, NORTH);
		setupCell(0, 2, NORTH_EAST);
		westWidget = setupCell(1, 0, WEST);
		grid.setWidget(1, 1, verticalPanel);
		eastWidget = setupCell(1, 2, EAST);
		setupCell(2, 0, SOUTH_WEST);
		southWidget = setupCell(2, 1, SOUTH);
		setupCell(2, 2, SOUTH_EAST);
	}
	public int getContentHeight() {
		return contentHeight;
	}
	public int getContentWidth() {
		return contentWidth;
	}
	/**
	 * Move window by specified coordinates
	 * **/
	public void moveBy(int right, int down) {
		AbsolutePanel parent = (AbsolutePanel) getParent();
		Location location = new WidgetLocation(this, parent);
		int left = location.getLeft() + right;
		int top = location.getTop() + down;
		parent.setWidgetPosition(this, left, top);
	}
	/**
	 * Move window to specified position, should be moved after adding to parent(AbsolutePanel)
	 * **/
	public void moveTo(int left, int top) {
		AbsolutePanel parent = (AbsolutePanel) getParent();
		parent.setWidgetPosition(this, left, top);
	}
	/**
	 * Set the size of the content
	 * **/
	public void setContentSize(int width, int height) {
		if (width != contentWidth) {
			contentWidth = width;
			headerContainer.setPixelSize(contentWidth, headerWidget.getOffsetHeight());
			northWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
			southWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
		}
		if (height != contentHeight) {
			contentHeight = height;
			int headerHeight = headerContainer.getOffsetHeight();
			westWidget.setPixelSize(BORDER_THICKNESS, contentHeight + headerHeight);
			eastWidget.setPixelSize(BORDER_THICKNESS, contentHeight + headerHeight);
		}
		contentOrScrollPanelWidget.setPixelSize(contentWidth, contentHeight);
	}
	/**
	 * Set the cell value in the grid
	 * **/
	private Widget setupCell(int row, int col, DirectionConstant direction) {
		final FocusPanel widget = new FocusPanel();
		widget.setPixelSize(BORDER_THICKNESS, BORDER_THICKNESS);
		grid.setWidget(row, col, widget);
		grid.getCellFormatter().addStyleName(row, col,
				CSS_DEMO_RESIZE_EDGE + " demo-resize-" + direction.directionLetters);
		return widget;
	}
	@Override
	protected void onLoad() {
		super.onLoad();
		if (!initialLoad && contentOrScrollPanelWidget.getOffsetHeight() != 0) {
			initialLoad = true;
			headerWidget.setPixelSize(headerWidget.getOffsetWidth(), headerWidget.getOffsetHeight());
			setContentSize(contentOrScrollPanelWidget.getOffsetWidth(),
					contentOrScrollPanelWidget.getOffsetHeight());
		}
	}
	public Widget getContentOrScrollPanelWidget() {
		return contentOrScrollPanelWidget;
	}
	public void setContentOrScrollPanelWidget(Widget contentOrScrollPanelWidget) {
		this.contentOrScrollPanelWidget = contentOrScrollPanelWidget;
	}
}
