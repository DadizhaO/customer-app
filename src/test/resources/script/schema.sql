CREATE SCHEMA IF NOT EXISTS MA_STUDENT;

DROP TABLE IF EXISTS MA_STUDENT.CUSTOMERS;

CREATE TABLE MA_STUDENT.CUSTOMERS
(
  CUST_NUM     DECIMAL(38,0),
  COMPANY      VARCHAR(0),
  CREDIT_LIMIT DECIMAL(38,0),
  CUST_REP     DECIMAL(38,0)
)
