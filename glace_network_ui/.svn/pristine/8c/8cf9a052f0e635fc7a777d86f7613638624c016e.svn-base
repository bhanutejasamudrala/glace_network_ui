package com.glenwood.network.client.application.avptemplate;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AVPPlace extends Place {
	private String token;

	public AVPPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<AVPPlace> {
		@Override
		public String getToken(AVPPlace place) {
			return place.getToken();
		}

		@Override
		public AVPPlace getPlace(String token) {
			return new AVPPlace(token);
		}
	}

}
