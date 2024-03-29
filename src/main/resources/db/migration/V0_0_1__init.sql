CREATE SCHEMA IF NOT EXISTS `profit-tracker-service` DEFAULT CHARACTER SET utf8;
USE `profit-tracker-service`;

CREATE TABLE IF NOT EXISTS PORTFOLIO
(
    PORTFOLIO_ID     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    USER_ID          VARCHAR(100)     NOT NULL,
    NAME             VARCHAR(45)     NOT NULL,
    CREATION_DATE    DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    CREATED_BY       VARCHAR(45)     NULL,
    LAST_UPDATE_DATE DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    LAST_UPDATED_BY  VARCHAR(45)     NULL,
    PRIMARY KEY (`PORTFOLIO_ID`)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS COLLECTION
(
    COLLECTION_ID    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    NAME             VARCHAR(45)     NOT NULL,
    PORTFOLIO_ID     BIGINT UNSIGNED NOT NULL,
    CREATION_DATE    DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    CREATED_BY       VARCHAR(45)     NULL,
    LAST_UPDATE_DATE DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    LAST_UPDATED_BY  VARCHAR(45)     NULL,
    PRIMARY KEY (COLLECTION_ID),
    CONSTRAINT fk_PORTFOLIO_ID
        FOREIGN KEY (PORTFOLIO_ID)
            REFERENCES PORTFOLIO (PORTFOLIO_ID)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS NFT
(
    NFT_ID           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    NAME             VARCHAR(45)     NOT NULL,
    IMAGE_KEY        VARCHAR(100)     NOT NULL,
    COLLECTION_ID    BIGINT UNSIGNED NOT NULL,
    CREATION_DATE    DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    CREATED_BY       VARCHAR(45)     NULL,
    LAST_UPDATE_DATE DATETIME        NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    LAST_UPDATED_BY  VARCHAR(45)     NULL,
    PRIMARY KEY (NFT_ID),
    CONSTRAINT fk_COLLECTION_ID
        FOREIGN KEY (COLLECTION_ID)
            REFERENCES COLLECTION (COLLECTION_ID)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


