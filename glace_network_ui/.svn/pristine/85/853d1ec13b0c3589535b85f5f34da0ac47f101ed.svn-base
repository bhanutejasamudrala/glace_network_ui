package com.glenwood.network.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.glenwood.network.client.service.RpcCallService;
/*import com.google.gwt.rpc.client.RpcService;*/
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcCallServiceImpl extends RemoteServiceServlet implements RpcCallService {

	public HashMap<Integer, String> fetchInputNodeList(String fileFieldName, String networkId) {
		DataFileUploadServlet inputFileUpload=new DataFileUploadServlet();
		return inputFileUpload.sendInputNodeNames(fileFieldName, networkId);
	}

	public HashMap<Integer, Double> fetchInputNodeValueMap(String fileFieldName, int csvIndex, int fromIndex, int toIndex) {
		DataFileUploadServlet inputFileUpload=new DataFileUploadServlet();
		HashMap<Integer, Double> nodeList=inputFileUpload.collectValue(fileFieldName, csvIndex, fromIndex, toIndex);
		return nodeList;
	}

	public ArrayList<String> fetchInputText(String fileFieldName) {
		DataFileUploadServlet inputFileUpload = new DataFileUploadServlet();
		ArrayList<String> data = inputFileUpload.collectData(fileFieldName);
		return data;
	}

}
