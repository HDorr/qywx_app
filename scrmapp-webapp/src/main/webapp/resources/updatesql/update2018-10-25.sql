alter table t_product
add isAutoBind tinyint(1) default 0
comment '区分自动或手动绑定,0手动,1是自动';