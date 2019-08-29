alter table t_wechat_orders
 add delivery_type enum('NORMAL', 'DEALER') default "ETW" not null comment '发货方式  NORMAL:工厂发货  DEALER:带货上门';

alter table t_wechat_orders
 add departmentName varchar(50) null comment '服务网点';