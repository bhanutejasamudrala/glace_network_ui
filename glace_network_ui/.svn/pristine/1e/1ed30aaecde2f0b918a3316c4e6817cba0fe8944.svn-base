package com.glenwood.network.client.mvp;

import com.glenwood.network.client.application.avptemplate.AVPPlace;
import com.glenwood.network.client.application.dragHistory.DragHistoryPlace;
import com.glenwood.network.client.application.run.RunPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({ AVPPlace.Tokenizer.class, DragHistoryPlace.Tokenizer.class, RunPlace.Tokenizer.class})
public interface DesktopAppPlaceHistoryMapper extends PlaceHistoryMapper {
}
