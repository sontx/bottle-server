package com.blogspot.sontx.bottle.server.model.repository;

import com.blogspot.sontx.bottle.server.model.entity.RoomMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface RoomMessageRepository extends JpaRepository<RoomMessageEntity, Integer> {

    Page<RoomMessageEntity> findAllByRoomIdOrderByMessageDetail_TimestampDesc(int roomId, Pageable pageable);

    void removeOneByIdEquals(int messageId);

    void removeAllByMessageDetail_TimestampBefore(Timestamp timestamp);
}
