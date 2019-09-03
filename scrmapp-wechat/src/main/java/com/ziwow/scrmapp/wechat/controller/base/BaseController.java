package com.ziwow.scrmapp.wechat.controller.base;

import com.google.common.collect.ImmutableMap;
import com.ziwow.scrmapp.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.ziwow.scrmapp.common.result.ResultHelper.success;

/**
 * @author: yyc
 * @date: 19-9-3 上午10:13
 */
public class BaseController {

  protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

  /**
   * 返回分页参数
   *
   * @param <T> T
   * @param list {@link List}
   * @param total {@link Long}
   * @return {@link Result}
   */
  protected static <T> Result toPageMap(List<T> list, long total) {
    return success(ImmutableMap.of("list", list, "total", total));
  }

  /**
   * 返回空的分页参数
   *
   * @return {@link ImmutableMap}
   */
  protected static Result toEmptyPageMap() {
    return toPageMap(new ArrayList<>(), 0);
  }
}
