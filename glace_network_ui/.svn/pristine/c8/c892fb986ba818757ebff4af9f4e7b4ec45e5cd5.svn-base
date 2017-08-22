package com.glenwood.network.client.application.run;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RunPlace extends Place {
	private String token;

	public RunPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<RunPlace> {
		@Override
		public String getToken(RunPlace place) {
			return place.getToken();
		}

		@Override
		public RunPlace getPlace(String token) {
			return new RunPlace(token);
		}
	}

}
