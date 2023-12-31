-- H2 2.2.224;
;             
CREATE USER IF NOT EXISTS "SA" SALT '59d0fa2dd2b1d445' HASH 'b01d4f202e64eae6e322a18724bee2d55f168a24ec95b9deed291ecd2de34332' ADMIN;         
CREATE CACHED TABLE "PUBLIC"."TEST"(
    "ID" INTEGER NOT NULL,
    "NAME" CHARACTER VARYING(255)
);       
ALTER TABLE "PUBLIC"."TEST" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" PRIMARY KEY("ID");         
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.TEST;    
INSERT INTO "PUBLIC"."TEST" VALUES
(1, 'Hi');
CREATE CACHED TABLE "PUBLIC"."SUDO_USERS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 12) NOT NULL,
    "USERNAME" CHARACTER VARYING(255) NOT NULL,
    "PASSWORD" CHARACTER VARYING(255) NOT NULL
);     
ALTER TABLE "PUBLIC"."SUDO_USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_5" PRIMARY KEY("ID");   
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.SUDO_USERS;              
INSERT INTO "PUBLIC"."SUDO_USERS" VALUES
(1, 'admin', 'admin');              
CREATE CACHED TABLE "PUBLIC"."USERS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 9) NOT NULL,
    "NAME" CHARACTER VARYING(255) NOT NULL,
    "USERNAME" CHARACTER VARYING(255) NOT NULL,
    "PASSWORD" CHARACTER VARYING(255) NOT NULL
);              
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");        
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.USERS;   
INSERT INTO "PUBLIC"."USERS" VALUES
(2, 'fridulis', 'frida', '1234'),
(8, 'fridatest', 'frida1', '1234');   
CREATE CACHED TABLE "PUBLIC"."TRANSACTIONS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 33) NOT NULL,
    "TYPE" CHARACTER VARYING(255) NOT NULL,
    "AMOUNT" INTEGER NOT NULL,
    "ORIGINID" INTEGER NOT NULL,
    "DESTINYID" INTEGER NOT NULL,
    "DATE" TIMESTAMP NOT NULL,
    "DESCRIPTION" CHARACTER VARYING(255) NOT NULL
);               
ALTER TABLE "PUBLIC"."TRANSACTIONS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID"); 
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.TRANSACTIONS;            
INSERT INTO "PUBLIC"."TRANSACTIONS" VALUES
(1, 'credit', 50, 1, 2, TIMESTAMP '2023-12-06 21:59:43.436', 'test'),
(2, 'debit', 10, 2, 1, TIMESTAMP '2023-12-06 22:09:16.509', 'debito10');   
CREATE CACHED TABLE "PUBLIC"."CARDS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 33) NOT NULL,
    "ACCOUNTID" INTEGER NOT NULL,
    "DUE" INTEGER NOT NULL,
    "AVAILABLE" INTEGER NOT NULL
);         
ALTER TABLE "PUBLIC"."CARDS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("ID");        
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.CARDS;   
INSERT INTO "PUBLIC"."CARDS" VALUES
(1, 1, 0, 210),
(2, 2, 110, 490);       
CREATE CACHED TABLE "PUBLIC"."ACCOUNTS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 34) NOT NULL,
    "TYPE" CHARACTER VARYING(255) NOT NULL,
    "CBU" INTEGER NOT NULL,
    "USERID" INTEGER NOT NULL,
    "ALIAS" CHARACTER VARYING(255) NOT NULL,
    "INTEREST" DOUBLE PRECISION NOT NULL,
    "TOTAL" INTEGER
);
ALTER TABLE "PUBLIC"."ACCOUNTS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_A" PRIMARY KEY("ID");     
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.ACCOUNTS;
INSERT INTO "PUBLIC"."ACCOUNTS" VALUES
(1, 'CuentaCorriente', 0, 2, 'fridacc', 0.0, NULL),
(2, 'CajaDeAhorro', 122333, 2, 'ahorro', 1.0, NULL),
(3, 'CajaDeAhorroUSD', 3333, 2, 'dolarete', 0.0, NULL);    
ALTER TABLE "PUBLIC"."CARDS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3C" FOREIGN KEY("ACCOUNTID") REFERENCES "PUBLIC"."ACCOUNTS"("ID") NOCHECK;   
ALTER TABLE "PUBLIC"."ACCOUNTS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_AF" FOREIGN KEY("USERID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;      
