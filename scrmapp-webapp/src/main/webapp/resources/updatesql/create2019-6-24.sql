create table scrm_test.t_ew_card_items
(
  id         bigint auto_increment comment 'id'
    primary key,
  item_name  varchar(20) not null comment '产品型号',
  item_code  varchar(20) not null comment '产品编码',
  ew_card_id bigint      not null comment '延保卡id'
)
  comment '延保类型表';
