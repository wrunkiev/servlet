CREATE TABLE ITEMS(
ID NUMBER NOT NULL,
ITEM_NAME NVARCHAR2(50) NOT NULL,
ITEM_DATE_CREATED TIMESTAMP,
ITEM_LAST_UPDATED_DATE TIMESTAMP,
ITEM_DESCRIPTION NVARCHAR2(50),
CONSTRAINT ITEM_ID PRIMARY KEY(ID)
);