create table scrm_test.t_ew_card
(
  id                      bigint auto_increment comment 'id'
    primary key,
  fans_id                 bigint                        not null comment 'fans_id',
  card_no                 varchar(20)                   not null comment '延保卡号',
  purch_date              datetime                      null comment '购买时间',
  repair_term             datetime                      null comment '保修期限',
  valid_time              int                           null comment '延保期限',
  product_bar_code_twenty varchar(100)                  null comment '20位产品条码',
  card_status             varchar(45) default 'NOT_USE' not null,
  install_list            bigint(1)                     null comment '是否有安装清单  （1：有  0：无  ）'
)
  comment '用户延保卡表';




create table scrm_test.t_ew_card_items
(
  id         bigint auto_increment comment 'id'
    primary key,
  item_name  varchar(20) not null comment '产品型号',
  item_code  varchar(20) not null comment '产品编码',
  ew_card_id bigint      not null comment '延保卡id'
)
  comment '延保类型表';