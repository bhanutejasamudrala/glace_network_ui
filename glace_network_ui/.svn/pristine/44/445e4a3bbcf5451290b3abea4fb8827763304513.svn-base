package com.glenwood.network.client.application.addNetwork;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AddNetworkPlace extends Place {
	private String token;

	public AddNetworkPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<AddNetworkPlace> {
		@Override
		public String getToken(AddNetworkPlace place) {
			return place.getToken();
		}

		@Override
		public AddNetworkPlace getPlace(String token) {
			return new AddNetworkPlace(token);
		}
	}

}
