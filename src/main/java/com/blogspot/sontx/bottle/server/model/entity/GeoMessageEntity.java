package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "publicProfile")
@Entity
@Table(name = "geo_message", schema = "bottle")
public class GeoMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "text", nullable = false, length = 4000)
    private String text;

    @Basic
    @Column(name = "mediaUrl", nullable = true, length = 1000)
    private String mediaUrl;

    @Basic
    @Column(name = "longitude", nullable = false, precision = 0)
    private double longitude;

    @Basic
    @Column(name = "latitude", nullable = false, precision = 0)
    private double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private PublicProfileEntity publicProfile;
}
