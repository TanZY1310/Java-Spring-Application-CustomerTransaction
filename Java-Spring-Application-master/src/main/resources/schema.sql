CREATE DATABASE batchapi;
USE batchapi;
CREATE TABLE customer (
trx_id INT(3) NOT NULL,
acc_number CHAR(10) NOT NULL,
trx_amount DOUBLE(15,2) NOT NULL,
trx_description CHAR(80) NOT NULL,
trx_date DATE NOT NULL,
trx_time TIME NOT NULL,
cust_id INT(3) NOT NULL,
PRIMARY KEY (trx_id))