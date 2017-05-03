package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "publicProfile")
@ToString(exclude = "publicProfile")
@Entity
@Table(name = "user_setting", schema = "bottle")
public class UserSettingEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "currentRoomId", nullable = true)
    private Integer currentRoomId;

    @Basic
    @Column(name = "currentLatitude", nullable = false)
    private double currentLatitude;

    @Basic
    @Column(name = "currentLongitude", nullable = false)
    private double currentLongitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private PublicProfileEntity publicProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageId", referencedColumnName = "id", nullable = true)
    private GeoMessageEntity messageEntity;
}
