package com.shyrski.profit.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shyrski.profit.tracker.mapper.NftMapper;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.nft.NftSearchDto;
import com.shyrski.profit.tracker.repository.NftRepository;

@ExtendWith(MockitoExtension.class)
class NftServiceImplTest {

    @InjectMocks
    private NftServiceImpl nftService;
    @Mock
    private NftRepository nftRepository;
    @Mock
    private NftMapper nftMapper;

    private static final Long TEST_COLLECTION_ID = 1581L;
    private static final String TEST_COLLECTION_NAME = "my collection";

    @Test
    public void shouldReturnCorrectNftsInCollection() {
        NftSearchDto nftSearchDto = new NftSearchDto();
        nftSearchDto.setCollectionId(TEST_COLLECTION_ID);
        nftSearchDto.setName(TEST_COLLECTION_NAME);

        List<Nft> nfts = new ArrayList<>(List.of(new Nft(), new Nft()));
        List<NftDto> nftDtos = new ArrayList<>(List.of(new NftDto(), new NftDto()));

        when(nftRepository.findAllBySearchCriteria(TEST_COLLECTION_ID, TEST_COLLECTION_NAME))
                .thenReturn(nfts);
        when(nftMapper.toDtoList(nfts)).thenReturn(nftDtos);

        List<NftDto> actualResult = nftService.findNftsInCollection(nftSearchDto);

        assertEquals(actualResult, nftDtos);
    }
}