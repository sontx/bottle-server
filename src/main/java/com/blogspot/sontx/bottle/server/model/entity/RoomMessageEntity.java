package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"room", "messageDetail", "publicProfile"})
@ToString(exclude = {"room", "messageDetail", "publicProfile"})
@Entity
@Table(name = "room_message", schema = "bottle")
public class RoomMessageEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private RoomEntity room;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detailId", referencedColumnName = "id", nullable = false)
    private MessageDetailEntity messageDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private PublicProfileEntity publicProfile;
}
