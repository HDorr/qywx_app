ALTER TABLE t_grant_ew_card_record MODIFY src_type enum('SMS', 'ORDER', 'GIFT') DEFAULT 'ORDER';
ALTER TABLE t_grant_ew_card_record ADD message_again tinyint(1) DEFAULT '0' NULL COMMENT '是否发送（true:已发送）';