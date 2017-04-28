package com.blogspot.sontx.bottle.server.model.repository;

import com.blogspot.sontx.bottle.server.model.entity.GeoMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeoMessageRepository extends JpaRepository<GeoMessageEntity, Integer> {
    @Query("select geo from GeoMessageEntity geo where (geo.latitude < ?1 and geo.latitude > ?2) and (geo.longitude > ?3 and geo.longitude < ?4)")
    List<GeoMessageEntity> findAllAroundLocation(double latitudeTop, double latitudeBottom, double longitudeLeft, double longitudeRight);
}
