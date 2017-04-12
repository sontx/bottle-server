package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"geoMessage", "roomMessage"})
@ToString(exclude = {"geoMessage", "roomMessage"})
@Entity
@Table(name = "message_detail", schema = "bottle")
public class MessageDetailEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "text", nullable = false, length = 4000)
    private String text;

    @Basic
    @Column(name = "mediaUrl", nullable = true, length = 1000)
    private String mediaUrl;

    @Basic
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "messageDetail")
    private GeoMessageEntity geoMessage;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "messageDetail")
    private RoomMessageEntity roomMessage;

    @PrePersist
    protected void onCreate() {
        timestamp = new Timestamp(new Date().getTime());
    }
}
