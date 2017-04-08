package com.blogspot.sontx.bottle.server.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"roomCategory", "roomMessages"})
@Entity
@Table(name = "room", schema = "bottle")
public class RoomEntity {
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

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    private RoomCategoryEntity roomCategory;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<RoomMessageEntity> roomMessages;
}
