package com.shyrski.profit.tracker.validator;

import static com.shyrski.profit.tracker.util.ValidatorUtil.createValidationMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.model.db.NftType;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NftValidator {

    private static final String OBJECT_NAME = "Nft";
    private static final String ID_IN_MARKETPLACE = "idInMarketplace";

    public void validateNft(NftDto nftDto) {
        if (nftDto.getType().equals(NftType.CUSTOM)) {
            if (!isNull(nftDto.getIdInMarketplace())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If NFT is custom, idInMarketplace should be null",
                                nftDto.getIdInMarketplace(), ID_IN_MARKETPLACE, OBJECT_NAME)));
            }
        }

        if (nftDto.getType().equals(NftType.PUBLIC)) {
            if (isEmpty(nftDto.getContractAddress())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If NFT is public,contractAddress should not be empty",
                                nftDto.getIdInMarketplace(), ID_IN_MARKETPLACE, OBJECT_NAME)));
            }

            if (isEmpty(nftDto.getTokenId())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If NFT is public,tokenId should not be empty",
                                nftDto.getIdInMarketplace(), ID_IN_MARKETPLACE, OBJECT_NAME)));
            }
        }
    }
}
