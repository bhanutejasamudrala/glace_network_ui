package com.glenwood.network.client.application.domain;

import java.util.Arrays;
import java.util.List;


public class DrawNode {

	private String nodeName;
	private String nodeId;
	private Integer xValue;
	private Integer yValue;
	private String nodeType;
	private String currentIo;
	private String otherValue;
	private Integer status;
	private List<Integer> linkid;
	
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getXValue() {
		return xValue;
	}
	public void setXValue(Integer xValue) {
		this.xValue = xValue;
	}
	public Integer getYValue() {
		return yValue;
	}
	public void setYValue(Integer yValue) {
		this.yValue = yValue;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getCurrentIo() {
		return currentIo;
	}
	public void setCurrentIo(String currentIo) {
		this.currentIo = currentIo;
	}
	public String getOtherValue() {
		return otherValue;
	}
	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Integer> getLinkid() {
		return linkid;
	}
	public void setLinkid(List<Integer> linkid) {
		this.linkid = linkid;
	}


	/**
	 * toStringMethod to convert node into JSON format
	 * TODO changes if propertied of node changed
	 */
	public String toString(){
		String returnString = "{\"nodeName\":\""+nodeName+"\",\"nodeId\":\""+nodeId+"\",\"xValue\":"+xValue+",\"yValue\":"+yValue
				+ ",\"nodeType\":\""+nodeType+"\",\"currentIo\":\""+currentIo+"\",\"otherValue\":"+otherValue+",\"status\":"+status
				+",\"linkid\":"+Arrays.toString(linkid.toArray())+"}";
		
		return returnString;
	}
	
	/**
	 * Equals method
	 * **/
	@Override
	public boolean equals(Object n){
		if(!(n instanceof DrawNode)){
			return false;
		}else if(((DrawNode)n).getNodeId().equalsIgnoreCase(this.getNodeId())){
			return true;
		} else {
			return false;
		}
			
	}
}
