package com.glenwood.network.client.application.domain;

import java.util.List;

import com.google.gwt.user.client.Window;

public class DrawNetwork {
	
	private List<DrawNode> nodeList;
	private List<DrawLink> linkList;
	
	public List<DrawNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<DrawNode> nodeList) {
		this.nodeList = nodeList;
	}
	
	public List<DrawLink> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<DrawLink> linkList) {
		this.linkList = linkList;
	}
	
	@Override
	public String toString(){
		String JSONString = "{\"nodeList\":["+getNodeJSON()+"],\"linkList\":["+getLinkJSON()+"]}";
		return JSONString;
	}
	
	/**
	 * Method to get 
	 * **/
	private String getLinkJSON() {
		StringBuilder linkJSON = new StringBuilder();
		for(DrawLink l : linkList){
			linkJSON.append(l.toString()+",");
		}
		
		return linkJSON.deleteCharAt(linkJSON.length()-1).toString();
	}
	private String getNodeJSON() {
		StringBuilder nodeJSON = new StringBuilder() ;
		for(DrawNode n : nodeList){
			nodeJSON.append(n.toString()+",");
		}
		return nodeJSON.deleteCharAt(nodeJSON.length()-1).toString();
	}
	
}
