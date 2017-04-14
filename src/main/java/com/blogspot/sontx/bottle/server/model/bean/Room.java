package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

@Data
public class Room {
    private int id;
    private int categoryId;
    private String name;
    private String description;
    private String photoUrl;
}
