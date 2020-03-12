package com.middle.stage.test.optimization;

import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.optimization.commons.CommonsListUtil;
import com.middle.stage.test.optimization.dao.data.DinnerTypeDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class MiddleStageTestOptimizationApplicationTests {

    private static List<DinnerTypeDO> newDinnerTypeDOList;
    private static List<DinnerTypeDO> oldDinnerTypeDOList;

    @BeforeAll
    static void beforeGetDinnerListTest() {
        newDinnerTypeDOList = new ArrayList<>();
        oldDinnerTypeDOList = new ArrayList<>();
        DinnerTypeDO d1 = new DinnerTypeDO(1, "早餐", 60, 120);
        DinnerTypeDO d2 = new DinnerTypeDO(2, "午餐", 120, 180);
        DinnerTypeDO d3 = new DinnerTypeDO(2, "午餐", 130, 180);
        DinnerTypeDO d4 = new DinnerTypeDO(3, "晚餐", 200, 250);
        newDinnerTypeDOList.add(d1);
        newDinnerTypeDOList.add(d2);
        oldDinnerTypeDOList.add(d3);
        oldDinnerTypeDOList.add(d4);
    }

    @Test
    void getDinnerListTest() {
        Assertions.assertNotNull(newDinnerTypeDOList, "newDinnerTypeDOList can not be null!");
        Assertions.assertNotNull(oldDinnerTypeDOList, "newDinnerTypeDOList can not be null!");
        //通用集合求差集、交集
        CommonsListUtil commonsListUtil = new CommonsListUtil(newDinnerTypeDOList, oldDinnerTypeDOList).invoke();
        //新集合求出交集
        List tmpIntersectNewObjectList = commonsListUtil.getTmpIntersectNewObjectList();
        //就集合求出交集
        List tmpIntersectOldObjectList = commonsListUtil.getTmpIntersectOldObjectList();
        //新集合差集
        List tmpNewObjectList = commonsListUtil.getTmpNewObjectList();
        //旧集合差集
        List tmpOldObjectList = commonsListUtil.getTmpOldObjectList();

        Assertions.assertEquals(1, tmpIntersectNewObjectList.size());
        Assertions.assertEquals(1, tmpIntersectOldObjectList.size());
        Assertions.assertEquals(1, tmpNewObjectList.size());
        Assertions.assertEquals(1, tmpOldObjectList.size());
        log.info("tmpNewObjectList = [{}]", JSONObject.toJSONString(tmpNewObjectList));
        log.info("tmpOldObjectList = [{}]", JSONObject.toJSONString(tmpOldObjectList));
        log.info("tmpIntersectNewObjectList = [{}]", JSONObject.toJSONString(tmpIntersectNewObjectList));
        log.info("tmpIntersectOldObjectList = [{}]", JSONObject.toJSONString(tmpIntersectOldObjectList));

        //如果感兴趣，可以验证集合里的数据
        log.info("[测试通过]");

    }

}
