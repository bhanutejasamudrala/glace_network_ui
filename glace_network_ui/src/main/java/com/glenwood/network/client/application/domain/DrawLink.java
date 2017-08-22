package com.glenwood.network.client.application.domain;

public class DrawLink {

	private int linkId;
	private Integer startX;
	private Integer startY;
	private Integer endX;
	private Integer endY;
	private String startNodeId;
	private String endNodeId;
	private Integer linkStrength;
	private Integer direction;
	private Integer cutLevel;
	
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public Integer getStartX() {
		return startX;
	}
	public void setStartX(Integer startX) {
		this.startX = startX;
	}
	public Integer getStartY() {
		return startY;
	}
	public void setStartY(Integer startY) {
		this.startY = startY;
	}
	public Integer getEndX() {
		return endX;
	}
	public void setEndX(Integer endX) {
		this.endX = endX;
	}
	public Integer getEndY() {
		return endY;
	}
	public void setEndY(Integer endY) {
		this.endY = endY;
	}
	public String getStartNodeId() {
		return startNodeId;
	}
	public void setStartNodeId(String startNodeId) {
		this.startNodeId = startNodeId;
	}
	public String getEndNodeId() {
		return endNodeId;
	}
	public void setEndNodeId(String endNodeId) {
		this.endNodeId = endNodeId;
	}
	public Integer getLinkStrength() {
		return linkStrength;
	}
	public void setLinkStrength(Integer linkStrength) {
		this.linkStrength = linkStrength;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getCutLevel() {
		return cutLevel;
	}
	public void setCutLevel(Integer cutLevel) {
		this.cutLevel = cutLevel;
	}

	/**
	 * To String to convert object into JSON object
	 * TODO change method if any modification to class properties
	 * **/
	@Override
	public String toString(){
		return "{\"cutLevel\": "+cutLevel+" , \"direction\": "+direction+" , \"endNodeId\": \""+ endNodeId +"\" , \"endX\": "+endX+" ,  \"endY\": "+endY 
				+" , \"linkId\": "+linkId+" , \"linkStrength\": "+linkStrength+" , \"startNodeId\": \""+startNodeId+"\" , \"startX\": "+startX
				+" , \"startY\": "+ startY +"}";
	}
	
	
}
