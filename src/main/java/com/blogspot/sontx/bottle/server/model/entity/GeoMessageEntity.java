package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"messageDetail", "publicProfile"})
@ToString(exclude = {"messageDetail", "publicProfile"})
@Entity
@Table(name = "geo_message", schema = "bottle")
public class GeoMessageEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "longitude", nullable = false, precision = 0)
    private double longitude;

    @Basic
    @Column(name = "latitude", nullable = false, precision = 0)
    private double latitude;

    @Basic
    @Column(name = "addressName", nullable = true, length = 100)
    private String addressName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detailId", referencedColumnName = "id", nullable = false)
    private MessageDetailEntity messageDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private PublicProfileEntity publicProfile;
}
