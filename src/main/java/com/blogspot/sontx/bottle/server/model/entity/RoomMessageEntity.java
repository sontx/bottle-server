package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "publicProfile")
@Entity
@Table(name = "room_message", schema = "bottle")
public class RoomMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "text", nullable = false, length = 4000)
    private String text;

    @Basic
    @Column(name = "mediaUrl", nullable = false, length = 1000)
    private String mediaUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private PublicProfileEntity publicProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", referencedColumnName = "id", nullable = false)
    private RoomEntity room;
}
