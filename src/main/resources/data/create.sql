

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS "goods";
CREATE TABLE "goods" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" text NOT NULL,
  "info" TEXT,
  "img" TEXT,
  "price" real NOT NULL,
  "isFreeze" integer NOT NULL DEFAULT 0,
  "onEnable" integer NOT NULL DEFAULT 1
);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS "orders";
CREATE TABLE "orders" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL,
  "phone" TEXT NOT NULL,
  "time" text NOT NULL,
  "goodsId" INTEGER NOT NULL,
  "info" TEXT DEFAULT 等待处理,
  "isComplete" integer NOT NULL DEFAULT 0
);



-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  "password" text NOT NULL
);
INSERT INTO "user" VALUES ('123456');


