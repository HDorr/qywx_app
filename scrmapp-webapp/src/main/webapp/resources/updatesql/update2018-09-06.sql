alter table t_qyh_user_appraisal
add is_repair tinyint(1)
comment '是否维修好,1是,0否';

alter table t_qyh_user_appraisal
add is_order tinyint(1)
comment '是否准时预约,1是,0否';