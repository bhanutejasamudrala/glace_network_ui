package com.glenwood.network.client.application.domain;

/** Network table **/

public class Network {

	private Integer idNetwork;

	private String name;

	private String startNodeid;

	private boolean complex;

	public Integer getIdNetwork() {
		return idNetwork;
	}

	public boolean isComplex() {
		return complex;
	}

	public void setComplex(boolean complex) {
		this.complex = complex;
	}

	public void setIdNetwork(Integer idNetwork) {
		this.idNetwork = idNetwork;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartNodeid() {
		return startNodeid;
	}

	public void setStartNodeid(String startNodeid) {
		this.startNodeid = startNodeid;
	}

}
