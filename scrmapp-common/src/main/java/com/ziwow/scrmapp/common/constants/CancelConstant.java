package com.ziwow.scrmapp.common.constants;

public class CancelConstant {
    public static final int CANCEL_ONLY = 1;//仅取消当前产品
    public static final int CANCEL_REFUND = 2;//取消当前产品后,所有产品都为取消状态,提示当前为拒单操作
    public static final int CANCEL_COMPLETE = 3;//取消当前产品后,工单为已完工状态
}
