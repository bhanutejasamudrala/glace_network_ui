package com.glenwood.network.client.customwidgets.selectoionControl;

import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.GwtEvent;

/**
 *  <ul>
 *  <li>
 *  	<b>Module</b>: Search control widget <br/>
 *  </li>
 *  <li>
 *  	<b>Purpose:</b> Represents a navigation event in the search control component.
 *  </li>
 *  </ul>
 */

public class NavigationEvent extends GwtEvent<NavigationEventHandler> {
	/**
	 * Event type for navigation events. Represents the meta-data associated with this
	 * event.
	 */	
	public static Type<NavigationEventHandler> TYPE = new Type<NavigationEventHandler>();

	/**
	 * Search string entered during the search.
	 */	
    private final String searchString;
    
	/**
	 * Current offset in the navigation process.
	 */
    private final int offset;
    
	/**
	 * Number of records to be fetched for each navigation.
	 */
    private final int limit;

    /**
     * Protected constructor, use
     * {@link DomEvent#fireNativeEvent(com.google.gwt.dom.client.NativeEvent, com.google.gwt.event.shared.HasHandlers)}
     * to fire navigation events.
     */ 
    protected NavigationEvent(String searchString, int offset, int limit) {
        this.searchString = searchString;
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * Gets the search string used during the search.
     * @return the search string.
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Gets the current offset of the navigation process
     * @return current offset
     */
    public int getOffset() {
		return offset;
	}

    /**
     * Gets the number of records to be fetched for each navigation
     * @return number of records
     */
	public int getLimit() {
		return limit;
	}

    @Override
    public Type<NavigationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(NavigationEventHandler handler) {
        handler.onNavigation(this);
    }	
}