package com.glenwood.network.client.application.run;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.glenwood.network.client.application.domain.Node;

public interface RunClient extends RestService {

	@POST
	@Path("/node/list?networkId={networkId}")
	void fetchNodeList(@PathParam(value="networkId") String networkId, MethodCallback<List<Node>> methodCallback);
	
	@POST
	@Path("/node/list?networkId={networkId}&offset={offset}&param={param}&nodeType={nodeType}")
	void fetchNodeList(@PathParam(value="networkId")String networkId, @PathParam(value="offset")String offset,
			@PathParam(value="param")String param, @PathParam(value="nodeType")String nodeType, MethodCallback<List<Node>> methodCallback);

}
