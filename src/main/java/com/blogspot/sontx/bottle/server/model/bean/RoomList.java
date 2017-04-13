package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

import java.util.List;

@Data
public class RoomList {
    private int categoryId;
    private String categoryName;
    private List<Room> rooms;
}
