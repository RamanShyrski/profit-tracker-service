package com.shyrski.profit.tracker.validator;

import static com.shyrski.profit.tracker.util.ValidatorUtil.createValidationMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.model.db.CollectionType;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionValidator {
    private static final String OBJECT_NAME = "Collection";
    private static final String MARKETPLACE = "marketplace";
    private static final String ID_IN_MARKETPLACE = "idInMarketplace";

    public void validateCollection(CollectionDto collectionDto) {
        if (collectionDto.getType().equals(CollectionType.CUSTOM)) {
            if (!isNull(collectionDto.getMarketplace())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If collection is custom, marketplace should be null",
                                collectionDto.getMarketplace(), MARKETPLACE, OBJECT_NAME)));
            }

            if (!isNull(collectionDto.getIdInMarketplace())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If collection is custom, idInMarketplace should be null",
                                collectionDto.getIdInMarketplace(), ID_IN_MARKETPLACE, OBJECT_NAME)));
            }
        }

        if (collectionDto.getType().equals(CollectionType.PUBLIC)) {
            if (isEmpty(collectionDto.getMarketplace())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If collection is public, marketplace field should not be empty",
                                null, MARKETPLACE, OBJECT_NAME)));
            }

            if (isEmpty(collectionDto.getIdInMarketplace())) {
                throw new ServerException(ExceptionDetails
                        .validationFailed(createValidationMessage("If collection is public, idInMarketplace field should not be empty",
                                null, ID_IN_MARKETPLACE, OBJECT_NAME)));
            }
        }

    }
}
