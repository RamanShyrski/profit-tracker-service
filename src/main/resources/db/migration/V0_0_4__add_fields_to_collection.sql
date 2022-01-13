CREATE TABLE IF NOT EXISTS COLLECTION_MARKETPLACE
(
    COLLECTION_MARKETPLACE_ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    NAME                      VARCHAR(50)     NOT NULL,
    CREATION_DATE             DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    CREATED_BY                VARCHAR(45)     NULL,
    LAST_UPDATE_DATE          DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    LAST_UPDATED_BY           VARCHAR(45)     NULL,
    PRIMARY KEY (`COLLECTION_MARKETPLACE_ID`)
)
    ENGINE = InnoDB;

ALTER TABLE COLLECTION
    ADD COLUMN TYPE              VARCHAR(50)     NOT NULL AFTER IMAGE_KEY,
    ADD COLUMN ID_IN_MARKETPLACE VARCHAR(200)    NULL AFTER TYPE,
    ADD COLUMN MARKETPLACE_ID    BIGINT UNSIGNED NULL AFTER ID_IN_MARKETPLACE;

ALTER TABLE COLLECTION
    ADD CONSTRAINT fk_COLLECTION_MARKETPLACE_ID
        FOREIGN KEY (MARKETPLACE_ID) REFERENCES COLLECTION_MARKETPLACE (COLLECTION_MARKETPLACE_ID);