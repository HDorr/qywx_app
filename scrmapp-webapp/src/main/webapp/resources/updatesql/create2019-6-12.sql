create table scrm_test.t_ew_card
(
  id                      bigint auto_increment comment 'id'
    primary key,
  fans_id                 bigint       not null comment 'fans_id',
  card_no                 varchar(20)  not null comment '延保卡号',
  purch_date              datetime     null comment '购买时间',
  repair_term             datetime     null comment '保修期限',
  valid_time              int          null comment '延保期限',
  use_status              tinyint(1)   not null comment '使用状态: (1:已使用 0:未使用)',
  product_bar_code_twenty varchar(100) null comment '20位产品条码'
)
  comment '用户延保卡表';