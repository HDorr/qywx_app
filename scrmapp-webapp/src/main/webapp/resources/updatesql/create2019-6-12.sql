create table t_ew_card
(
  id bigint(20) auto_increment comment 'id',
  fans_id bigint(20) not null comment 'fans_id',
  card_no varchar(20) not null comment '延保卡号',
  item_name varchar(20) null comment '对应的机型',
  purch_date datetime null comment '购买时间',
  repair_term datetime null comment '保修期限',
  valid_time int null comment '延保期限',
  use_status tinyint(1) null comment '使用状态: (1:已使用 0:未使用)',
  constraint t_ew_card_fans_record_pk
    primary key (id)
)
comment '用户延保卡记录表';