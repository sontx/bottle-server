package com.blogspot.sontx.bottle.server.model.repository;

import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicProfileRepository extends JpaRepository<PublicProfileEntity, String> {

}
