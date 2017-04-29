package com.blogspot.sontx.bottle.server.model.service.message;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.bean.GeoMessage;

import java.util.List;

public interface GeoMessageService {
    List<GeoMessage> getMessagesAroundLocation(double latitude, double longitude, double latitudeRadius, double longitudeRadius);

    GeoMessage postMessage(GeoMessage message, AuthData authData);
}
