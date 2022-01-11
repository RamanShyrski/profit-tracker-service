package com.shyrski.profit.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shyrski.profit.tracker.model.db.Nft;

public interface NftRepository extends JpaRepository<Nft, Long> {

    @Query(value = "select * from NFT n where n.collection_id=:collectionId AND (:name is NULL or n.NAME LIKE concat('%', :name, '%')) ", nativeQuery = true)
    List<Nft> findAllBySearchCriteria(@Param("collectionId") Long collectionId, @Param("name") String name);
}
