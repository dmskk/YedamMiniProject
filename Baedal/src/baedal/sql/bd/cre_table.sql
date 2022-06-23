CREATE TABLE owners(
    corp_num NUMBER(10) CONSTRAINT owner_corp_num_pk PRIMARY KEY,
    password VARCHAR2(20) CONSTRAINT owner_pwd_nn NOT NULL,
    store_name VARCHAR2(100) CONSTRAINT owner_name_nn NOT NULL,
    store_value NUMBER(1) CONSTRAINT owner_value_nn  NOT NULL,
    time_open NUMBER(2) CONSTRAINT owner_open_nn NOT NULL,
    time_close NUMBER(2) CONSTRAINT owner_close_nn NOT NULL
);

CREATE TABLE customers(
    id VARCHAR2(20) CONSTRAINT customer_id_pk PRIMARY KEY,
    password VARCHAR2(20) CONSTRAINT custommer_pwd_nn NOT NULL,
    name VARCHAR2(20) CONSTRAINT custommer_name_nn  NOT NULL,
    phone_number VARCHAR2(50) CONSTRAINT custommer_phone_number_uk UNIQUE
                                            CONSTRAINT custommer_phone_number_nn NOT NULL,
    addr VARCHAR2(100) CONSTRAINT customer_addr_nn NOT NULL,
    nickname VARCHAR2(30) CONSTRAINT customer_nickname_nn NOT NULL
                                      CONSTRAINT customer_nickname_uk UNIQUE,
    point NUMBER(6) DEFAULT 0,
    grade NUMBER(1) DEFAULT 4
);

CREATE TABLE orders(
    order_date DATE DEFAULT sysdate,
    customer_id VARCHAR2(20),
    owner_num NUMBER(10),
    order_menu VARCHAR2(4000),
    order_price NUMBER(38),
    pay NUMBER(1),
    delivery_status NUMBER(1) DEFAULT 1,
    CONSTRAINT order_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL,
    CONSTRAINT order_owner_num_fk FOREIGN KEY (owner_num) REFERENCES owners(corp_num) ON DELETE SET NULL                                                                                                             
);

CREATE TABLE reviews(
    review_date DATE DEFAULT sysdate,
    writer_id VARCHAR2(20),
    writer_nickname VARCHAR(30),
    star NUMBER(5) DEFAULT 5,
    content VARCHAR2(4000),
    store_name VARCHAR2(100),
    store_num NUMBER(10),
    CONSTRAINT review_id_fk FOREIGN KEY (writer_id) REFERENCES customers(id)  ON DELETE SET NULL,
    CONSTRAINT review_store_num_fk FOREIGN KEY (store_num) REFERENCES owners(corp_num) ON DELETE SET NULL
);

ALTER TABLE orders
ADD store_name VARCHAR2(100);

ALTER TABLE orders
RENAME COLUMN owner_num TO store_num;