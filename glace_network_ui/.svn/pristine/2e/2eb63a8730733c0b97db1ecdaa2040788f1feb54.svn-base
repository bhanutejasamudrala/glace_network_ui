package com.glenwood.network.client.application.dragHistory;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DragHistoryPlace extends Place {
	private String token;

	public DragHistoryPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DragHistoryPlace> {
		@Override
		public String getToken(DragHistoryPlace place) {
			return place.getToken();
		}

		@Override
		public DragHistoryPlace getPlace(String token) {
			return new DragHistoryPlace(token);
		}
	}

}
