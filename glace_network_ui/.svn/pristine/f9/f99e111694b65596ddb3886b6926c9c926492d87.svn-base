package com.glenwood.network.client.application.dragHistory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.RestService;

import com.google.gwt.json.client.JSONArray;

public interface DragHistoryClient extends RestService{

	@POST
	@Path("node/updateNodes?nodeList={nodeList}")
	void saveHistory(@PathParam("nodeList")JSONArray nodeList, JsonCallback jsonCallback);

}
