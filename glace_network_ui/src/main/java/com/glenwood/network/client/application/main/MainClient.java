package com.glenwood.network.client.application.main;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.glenwood.network.client.application.domain.DrawNetwork;
import com.glenwood.network.client.application.domain.Network;


public interface MainClient extends RestService{
	
	@GET
	@Path("network/list")
	public void getNetworkList(MethodCallback<List<Network>> methodCallback);

	@POST
	@Path("network/load?startNodeId={startNodeId}&networkId={networkId}&selectedAlgorithm={selectedMethod}&x1={x1}&y1={y1}&width="
			+"{width}&height={height}&activeNetworkId={activeNetworkId}")
	public void getVisualizationData(@PathParam(value="startNodeId")String startNodeId, @PathParam(value="networkId")String networkId,
			@PathParam(value="selectedMethod")Integer selectedMethod, @PathParam(value="x1")Integer x1, @PathParam(value="y1")Integer y1,
			@PathParam(value="width")Integer width, @PathParam(value="height")Integer height, @PathParam(value="activeNetworkId")String activeNetworkId,
			MethodCallback<DrawNetwork> methodCallback);

	@POST
	@Path("network/hasDefaultXY?networkId={networkId}")
	public void promptPositionStatus(@PathParam(value="networkId")int networkId, MethodCallback<Integer> methodCallback);

	@POST
	@Path("network/savePositions?networkId={networkId}&algorithmSelected={algorithm}&width={width}&height={height}")
	public void savePositions(@PathParam(value="networkId")String networkId, @PathParam(value="algorithm") int algorithm, 
			@PathParam(value="width") Integer width, @PathParam(value="height") Integer height, MethodCallback<String> methodCallback);

	@POST
	@Path("/network/loadImage?networkId={networkId}&selectedAlgorithm={algorithmSelected}&width={width}&height={height}")
	public void getDisplayImage(@PathParam(value="networkId") String networkId, @PathParam(value="algorithmSelected") int algorithmSelected, 
			@PathParam(value="width") int width, @PathParam(value="height") int height, JsonCallback methodCallback);

	@POST
	@Path("/network/fetchDetails?networkId={networkId}")
	public void getNetworkInfo(@PathParam(value="networkId")int networkId, MethodCallback<Network> methodCallback);
}
