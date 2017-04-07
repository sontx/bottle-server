package com.blogspot.sontx.bottle.server.model.service.room;

import com.blogspot.sontx.bottle.server.model.bean.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRooms(int page, int pageSize);
}
