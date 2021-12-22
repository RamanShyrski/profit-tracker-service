package com.shyrski.profit.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shyrski.profit.tracker.model.db.Nft;

public interface NftRepository extends JpaRepository<Nft, Long> {

    @Query(value = "select * from NFT n where n.collection_id=:collectionId", nativeQuery = true)
    List<Nft> findAllByCollectionId(Long collectionId);
}
