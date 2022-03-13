CREATE TABLE `phonebook_details` (
                                     `ID` varchar(100) NOT NULL,
                                     `USER_NAME` varchar(45) NOT NULL,
                                     `PHONE_NUMBER` varchar(20) DEFAULT NULL,
                                     PRIMARY KEY (`ID`),
                                     UNIQUE KEY `PHONEBOOK_DETAILS_ID_uindex` (`ID`)
);

INSERT INTO `phonebook_details` VALUES ('17604629-b69d-499f-bee8-3d423453f28f','string','string'),('e0533429-0ea9-479f-ba00-33aa9267645f','string','string');

