package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"geoMessages", "roomMessages"})
@Entity
@Table(name = "public_profile", schema = "bottle")
public class PublicProfileEntity {
    @Id
    @Column(name = "id", nullable = false, length = 255)
    private String id;

    @Basic
    @Column(name = "displayName", nullable = false, length = 50)
    private String displayName;

    @Basic
    @Column(name = "avatarUrl", nullable = false, length = 255)
    private String avatarUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publicProfile", cascade = CascadeType.ALL)
    private Set<GeoMessageEntity> geoMessages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publicProfile", cascade = CascadeType.ALL)
    private Set<RoomMessageEntity> roomMessages;
}
