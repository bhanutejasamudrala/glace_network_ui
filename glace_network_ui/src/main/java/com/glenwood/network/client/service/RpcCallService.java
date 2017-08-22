package com.glenwood.network.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcService")
public interface RpcCallService extends RemoteService {
	public HashMap<Integer, String> fetchInputNodeList(String fileFieldName, String networkId);
	HashMap<Integer, Double> fetchInputNodeValueMap(String fileFieldName, int csvIndex, int fromIndex, int toIndex);
	ArrayList<String> fetchInputText(String fileFieldName);
}
