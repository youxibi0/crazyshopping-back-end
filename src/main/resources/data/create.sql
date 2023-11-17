DROP TABLE IF EXISTS "account";
CREATE TABLE "account" (
                           "username" TEXT NOT NULL,
                           "password" TEXT NOT NULL,
                           "level" integer NOT NULL,
                           PRIMARY KEY ("username")
);

INSERT INTO "account" VALUES ('admin', '123456', 0);

DROP TABLE IF EXISTS "goods";
CREATE TABLE "goods" (
                         "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                         "name" text NOT NULL,
                         "info" TEXT,
                         "price" real NOT NULL,
                         "onEnable" integer NOT NULL DEFAULT 1,
                         "num" INTEGER NOT NULL
);


DROP TABLE IF EXISTS "goodsImages";
CREATE TABLE "goodsImages" (
                               "goodsId" INTEGER NOT NULL,
                               "imgName" TEXT NOT NULL
);


DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders" (
                          "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                          "username" TEXT NOT NULL,
                          "phone" TEXT NOT NULL,
                          "location" text NOT NULL,
                          "goodsId" INTEGER NOT NULL,
                          "goodsInfo" TEXT,
                          "state" integer NOT NULL DEFAULT 1,
                          "goodsPrice" real NOT NULL,
                          "goodsName" TEXT NOT NULL,
                          "imgName" TEXT,
                          "time" text NOT NULL
);


DROP TABLE IF EXISTS "sortGoods";
CREATE TABLE "sortGoods" (
                             "one" TEXT NOT NULL,
                             "two" TEXT NOT NULL,
                             "goodsId" integer NOT NULL
);


DROP TABLE IF EXISTS "sortOne";
CREATE TABLE "sortOne" (
    "one" TEXT NOT NULL
);


DROP TABLE IF EXISTS "sortTwo";
CREATE TABLE "sortTwo" (
                           "one" TEXT NOT NULL,
                           "two" TEXT NOT NULL
);

DROP TABLE IF EXISTS "userInfo";
CREATE TABLE "userInfo" (
                            "username" TEXT NOT NULL,
                            "phone" TEXT NOT NULL,
                            "location" TEXT NOT NULL,
                            PRIMARY KEY ("username")
);



