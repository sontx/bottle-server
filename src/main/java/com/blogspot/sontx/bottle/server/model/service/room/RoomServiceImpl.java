package com.blogspot.sontx.bottle.server.model.service.room;

import com.blogspot.sontx.bottle.server.model.bean.Room;
import com.blogspot.sontx.bottle.server.model.entity.RoomEntity;
import com.blogspot.sontx.bottle.server.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:bottle-config.properties")
public class RoomServiceImpl implements RoomService {
    @Value("${default.page.size}")
    private int defaultPageSize;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getRooms(int categoryId, int page, int pageSize) {
        if (page < 0)
            page = 0;
        if (pageSize <= 0)
            pageSize = defaultPageSize;
        Pageable pageable = new PageRequest(page, pageSize);
        Page<RoomEntity> roomEntities = roomRepository.findAllByCategoryId(categoryId, pageable);

        List<Room> rooms = new ArrayList<>(roomEntities.getSize());
        for (RoomEntity roomEntity : roomEntities) {
            Room room = createRoomFromEntity(roomEntity);
            rooms.add(room);
        }

        return rooms;
    }

    @Override
    public Room getRoom(int roomId) {
        if (roomId < 0)
            return null;

        RoomEntity roomEntity = roomRepository.findOne(roomId);
        return createRoomFromEntity(roomEntity);
    }

    private Room createRoomFromEntity(RoomEntity roomEntity) {
        Room room = new Room();
        room.setId(roomEntity.getId());
        room.setName(roomEntity.getName());
        room.setDescription(roomEntity.getDescription());
        return room;
    }
}
