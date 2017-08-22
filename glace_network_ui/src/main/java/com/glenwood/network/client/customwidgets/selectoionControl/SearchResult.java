package com.glenwood.network.client.customwidgets.selectoionControl;

import com.google.gwt.view.client.ProvidesKey;

/**
 *  <ul>
 *  <li>
 *  	<b>Module</b>: Selection control widget <br/>
 *  </li>
 *  <li>
 *  	<b>Purpose:</b> Represents a search result entry.
 *  </li>
 *  </ul>
 */


public class SearchResult {
	/**
	 * Description of a result entry.
	 */
	public String description;
	
	/**
	 * Value of a result entry.
	 */
	
	public SearchResultData value;
	
	/**
	 * HTML representation of a result entry.
	 */
	public String displayHTML;
	
	/**
	 * Gets the description of a result entry.
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the result entry.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the value of the result entry.
	 * @return value.
	 */
	public SearchResultData getValue() {
		return value;
	}

	
	/**
	 * Sets the value of the result entry. 
	 * @param value
	 */
	public void setValue(SearchResultData value) {
		this.value = value;
	}


	/**
	 * Gets the HTML representation of the result entry.
	 * @return display HTML representation
	 */
	public String getDisplayHTML() {
		return displayHTML;
	}
	
	

	/**
	 * Sets the HTML representation of the result entry.
	 * @param displayHTML
	 */
	public void setDisplayHTML(String displayHTML) {
		this.displayHTML = displayHTML;
	}
	
	/**
	 * Constructor
	 * @param description
	 * @param value
	 * @param displayHTML
	 */
	public SearchResult(String description, SearchResultData value, String displayHTML){
		this.description = description;
		this.value = value;
		this.displayHTML = displayHTML;
	}
	
	/**
	 * Key value when this value is selected in the results list.
	 */
	public static final ProvidesKey<SearchResult> KEY_PROVIDER = new ProvidesKey<SearchResult>() {
	      public Object getKey(SearchResult item) {
	        return item == null ? null : item.getValue();
	      }
	};

}
