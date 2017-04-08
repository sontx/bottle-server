package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeoMessage extends Message {
    private double longitude;
    private double latitude;
    private String addressName;
}
