package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "rooms")
@Entity
@Table(name = "room_category", schema = "bottle")
public class RoomCategoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomCategory", cascade = CascadeType.ALL)
    private Set<RoomEntity> rooms;
}
