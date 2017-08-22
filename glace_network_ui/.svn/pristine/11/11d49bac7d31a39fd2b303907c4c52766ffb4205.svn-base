package com.glenwood.network.client.customwidgets.selectoionControl;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;

/**
 *  <ul>
 *  <li>
 *  	<b>Module</b>: Search control widget <br/>
 *  </li>
 *  <li>
 *  	<b>Purpose:</b> A scrolling pager that automatically increases the range every time the scroll bar reaches the bottom.
 *  </li>
 *  </ul>
 */

public class ShowMorePagerPanel extends AbstractPager {

	private interface NavigationHandler extends ScrollHandler, HasHandlers{
	}
	
	
	/**
	 * The default increment size.
	 */
	private static final int DEFAULT_INCREMENT = 20;

	/**
	 * The increment size.
	 */
	private int incrementSize = DEFAULT_INCREMENT;

	/**
	 * The last scroll position.
	 */
	private int lastScrollPos = 0;

	/**
	 * The scrollable panel.
	 */
	private final ScrollPanel scrollable = new ScrollPanel();

	/**
	 * Construct a new {@link ShowMorePagerPanel}.
	 */
	
	private HandlerManager handlerManager;
	private boolean loadingIndicator = false;
	
	public ShowMorePagerPanel(int increamentSize) {
		initWidget(scrollable);
		this.setIncrementSize(increamentSize);
		
		handlerManager = new HandlerManager(this);
		
		// Do not let the scrollable take tab focus.
		scrollable.getElement().setTabIndex(-1);
		scrollable.setAlwaysShowScrollBars(false);
		// Handle scroll events.
		scrollable.addScrollHandler(new NavigationHandler() {
			
			public void fireEvent(GwtEvent<?> event) {
				handlerManager.fireEvent(event);
			}
			
			public void onScroll(ScrollEvent event) {
				// If scrolling up, ignore the event.
				int oldScrollPos = lastScrollPos;
				lastScrollPos = scrollable.getVerticalScrollPosition();
				if (oldScrollPos >= lastScrollPos) {
					return;
				}

				HasRows display = getDisplay();
				if (display == null) {
					return;
				}
				int maxScrollTop = scrollable.getWidget().getOffsetHeight()- scrollable.getOffsetHeight();
				if (lastScrollPos >= maxScrollTop) {
					// We are near the end, so increase the page size.
					if(!loadingIndicator){
						NavigationEvent navigationEvent = new NavigationEvent("", display.getVisibleRange().getLength(), incrementSize);
						fireEvent(navigationEvent);
						int newPageSize = Math.min(display.getVisibleRange().getLength() + incrementSize,display.getRowCount());
						display.setVisibleRange(0, newPageSize);
					}
				}
			}
		});
	}

	/**
	 * Get the number of rows by which the range is increased when the scrollbar
	 * reaches the bottom.
	 *
	 * @return the increment size
	 */
	public int getIncrementSize() {
		return incrementSize;
	}

	@Override
	public void setDisplay(HasRows display) {
		assert display instanceof Widget : "display must extend Widget";
		scrollable.setWidget((Widget) display);
		
		super.setDisplay(display);
	}
	
	public void scrollToTop(){
		scrollable.scrollToTop();
	}

	/**
	 * Set the number of rows by which the range is increased when the scrollbar
	 * reaches the bottom.
	 *
	 * @param incrementSize the incremental number of rows
	 */
	public void setIncrementSize(int incrementSize) {
		this.incrementSize = incrementSize;
	}

	@Override
	protected void onRangeOrRowCountChanged() {
	}
	
	/**
	 * Adds a {@link NavigationEvent} handler
	 * 
	 * @param handler the navigation event handler
	 * @return {@link HandlerRegistration}
	 */
	
	public HandlerRegistration addNavigationEventHandler(NavigationEventHandler handler){
		return handlerManager.addHandler(NavigationEvent.TYPE, handler);
	}

	/**
	 * Determines whether loading indicator is currently displaying.
	 * @return true if indicator is present.
	 */
	public boolean isLoadingIndicator() {
		return loadingIndicator;
	}

	/**
	 * Sets whether loading indicator is needed or not.
	 * @param loadingIndicator true to display the loading indicator
	 */
	public void setLoadingIndicator(boolean loadingIndicator) {
		this.loadingIndicator = loadingIndicator;
	}
}