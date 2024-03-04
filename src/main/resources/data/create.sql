DROP TABLE IF EXISTS "account";
CREATE TABLE "account"
(
    "username" TEXT    NOT NULL,
    "password" TEXT    NOT NULL,
    "level"    integer NOT NULL,
    PRIMARY KEY ("username")
);

INSERT INTO "account"
VALUES ('admin', '123456', 0);
INSERT INTO "account"
VALUES ('user', '123456', 1);

DROP TABLE IF EXISTS "goods";
CREATE TABLE "goods"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"     text    NOT NULL,
    "info"     TEXT,
    "price"    real    NOT NULL,
    "onEnable" integer NOT NULL DEFAULT 1,
    "num"      INTEGER NOT NULL
);

DROP TABLE IF EXISTS "cart";
CREATE TABLE "cart"
(
    "goodsId"  INTEGER NOT NULL,
    "username" text    NOT NULL,
    "amount"   integer NOT NULL DEFAULT 1,
    PRIMARY KEY ("goodsId", "username")
);

DROP TABLE IF EXISTS "collection";
CREATE TABLE "collection"
(
    "goodsId"  INTEGER NOT NULL,
    "username" TEXT    NOT NULL,
    PRIMARY KEY ("goodsId", "username")
);


DROP TABLE IF EXISTS "goodsImages";
CREATE TABLE "goodsImages"
(
    "goodsId" INTEGER NOT NULL,
    "imgName" TEXT    NOT NULL
);


DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders"
(
    "id"         INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "goodsId"    INTEGER NOT NULL,
    "goodsInfo"  TEXT,
    "goodsPrice" real    NOT NULL,
    "goodsName"  TEXT    NOT NULL,
    "imgName"    TEXT,
    "ordersId"   INTEGER NOT NULL
);

DROP TABLE IF EXISTS "ordersMain";
CREATE TABLE "ordersMain"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "time"     text,
    "state"    integer,
    "username" TEXT    NOT NULL,
    "name"     TEXT    NOT NULL,
    "phone"    TEXT,
    "location" TEXT,
    "amount"   integer NOT NULL DEFAULT 1
);

DROP TABLE IF EXISTS "location";
CREATE TABLE "location"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "username"     text not null ,
    "location" text not null ,
    "name" text not null ,
    "phone" text not null ,
    "isDefault" integer not null
);

DROP TABLE IF EXISTS "sortGoods";
CREATE TABLE "sortGoods"
(
    "one"     TEXT    NOT NULL,
    "two"     TEXT    NOT NULL,
    "goodsId" integer NOT NULL
);


DROP TABLE IF EXISTS "sortOne";
CREATE TABLE "sortOne"
(
    "one" TEXT NOT NULL
);


DROP TABLE IF EXISTS "sortTwo";
CREATE TABLE "sortTwo"
(
    "one" TEXT NOT NULL,
    "two" TEXT NOT NULL
);

DROP TABLE IF EXISTS "userInfo";
CREATE TABLE "userInfo"
(
    "username" TEXT NOT NULL,
    "phone"    TEXT NOT NULL,
    "location" TEXT NOT NULL,
    PRIMARY KEY ("username")
);
DROP TABLE IF EXISTS "afterserviceimages";
CREATE TABLE "afterserviceimages"
(
    "asId"    INTEGER NOT NULL,
    "imgName" TEXT    NOT NULL,
    PRIMARY KEY ("asId", "imgName")
);

DROP TABLE IF EXISTS "afterservice";
CREATE TABLE "afterservice"
(
    "id"       INTEGER NOT NULL,
    "title"    TEXT    NOT NULL,
    "context"  TEXT,
    "ordersId" INTEGER NOT NULL,
    "state"    TEXT    NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "logistics";
CREATE TABLE "logistics"
(
    "id"       INTEGER NOT NULL,
    "ordersId" INTEGER NOT NULL,
    PRIMARY KEY ("id", "ordersId")
);
INSERT INTO "userInfo"
VALUES ('user', '12345678901', '钱江湾');



