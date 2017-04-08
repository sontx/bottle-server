package com.blogspot.sontx.bottle.server.model.service.room;

import com.blogspot.sontx.bottle.server.model.bean.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRooms(int categoryId, int page, int pageSize);

    Room getRoom(int roomId);
}
