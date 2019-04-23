
--创建数据库表
create table TB_USER
(
  id        VARCHAR2(32),
  userid    VARCHAR2(10),
  uname     VARCHAR2(20),
  nick_name VARCHAR2(20),
  portrait  VARCHAR2(100),
  address   VARCHAR2(100),
  email     VARCHAR2(20),
  pwd       VARCHAR2(64),
  state     VARCHAR2(1)
);
create table TB_BOOK
(
  id            VARCHAR2(32),
  bname         VARCHAR2(50),
  cover         BLOB,
  author        VARCHAR2(50),
  origin_author VARCHAR2(50),
  translator    VARCHAR2(50),
  publish_date  VARCHAR2(50),
  page_count    NUMBER(6),
  binding       VARCHAR2(50),
  series        VARCHAR2(50),
  isbn          VARCHAR2(50),
  content_desc  VARCHAR2(200),
  author_desc   VARCHAR2(200)
);
create table TB_REQUEST
(
  id           VARCHAR2(32),
  user_book_id VARCHAR2(32),
  user_id      VARCHAR2(32),
  request_date DATE,
  sn           VARCHAR2(32),
  state        VARCHAR2(1)
);
create table TB_USER2BOOK
(
  id           VARCHAR2(32),
  user_id      VARCHAR2(32),
  book_id      VARCHAR2(32),
  add_date     VARCHAR2(20),
  add_post_x   VARCHAR2(50),
  add_post_y   VARCHAR2(50),
  state        VARCHAR2(1),
  borrow_state VARCHAR2(1)
);