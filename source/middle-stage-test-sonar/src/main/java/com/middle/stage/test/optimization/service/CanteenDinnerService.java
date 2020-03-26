package com.middle.stage.test.optimization.service;


import com.middle.stage.test.optimization.dao.data.DinnerDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 门店所属餐别业务处理类
 *
 * @author zhaoyongqiang
 * @date 2020/1/6
 */
@Service
public interface CanteenDinnerService {

    void putCanteenDinnerType2Cache(Integer canteenId, List<DinnerDO> dinnerEntityList);
}
