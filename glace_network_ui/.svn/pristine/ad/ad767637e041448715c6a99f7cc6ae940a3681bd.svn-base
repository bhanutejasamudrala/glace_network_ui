package com.glenwood.network.client.customwidgets.selectoionControl;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.GwtEvent;

/**
 *  <ul>
 *  <li>
 *  	<b>Module</b>: Search control widget <br/>
 *  </li>
 *  <li>
 *  	<b>Purpose:</b> Represents a search event in the search control component.
 *  </li>
 *  </ul>
 */

public class SearchEvent extends GwtEvent<SearchEventHandler> {
	/**
	 * Event type for search events. Represents the meta-data associated with this
	 * event.
	 */	
	public static Type<SearchEventHandler> TYPE = new Type<SearchEventHandler>();

	/**
	 * Search string entered during the search event.
	 */
    private final String searchString;

    /**
     * Protected constructor, use
     * {@link DomEvent#fireNativeEvent(com.google.gwt.dom.client.NativeEvent, com.google.gwt.event.shared.HasHandlers)}
     * to fire search events.
     */ 
    protected SearchEvent(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public Type<SearchEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SearchEventHandler handler) {
        handler.onSearch(this);
    }

    /**
     * Gets the search string entered during the event.
     * @param searchString the search string
     */
       
    public String getSearchString() {
        return searchString;
    }
}
