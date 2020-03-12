---
order: 1
group:
  title: 复杂场景案例
  order: 3
---

# 代码优化

## 介绍

质量好的代码，在结构上往往比较清晰，容易阅读，代码的功能函数相对也比较简短，做出来的产品问题相对也会较少（ ps : 没有程序员不喜欢简洁的代码）。但在开发中，经常会出大段的逻辑混乱的业务代码，那这时应该怎么办？我们先不去讨论业务代码变成这样的历史原因，站在便于进行单元测试的角度，来探讨一下，如果时间允许怎么对这样的业务代码进行优化。如果需要重构，请参考设计模式的原则及更多模式。

## 减小代码体积

### 案例

为了能理解下方更新门店餐别业务代码，先来大致的了解下处理逻辑：

- 数据为空，向集合中添加【全天】餐别。
- 处理新旧数据集合。获取各自的差集、交集。
- 集合不为空，批量添加，更新，删除。
- 产生变更记录，推送 isv，保存日志。
- 集合中不包括【全天】更新缓存。

![](../assets/670f60a22e085246f3ea8aa8540820d.png)

### 错误示范

没有优化的代码如下图，所有业务处理都在一个方法中。

![](../assets/service.png)

想象一下，130 行的代码，在进行单元测试时，有时可能都不知道程序运行到某一行时会得到什么样的结果，这就增加了我们编写桩代码的难度，并且单元测试的代码覆盖率也难以保证。

### 正确示范

此处展示伪代码。根据不同功能、业务进行代码提取来减小代码体积。

```
operate(){
	//验证并设置基础数据
	baseData = validate();
	//得到新旧餐别数据集合，获取交集、差集
	data = processData(baseData); 
	//批量添加
	batchInsert(data.insertList);
	//批量更新
	batchUpdate(data.updateList);
	//批量删除
	batchDelete(data.deLeteList);
	//更新缓存，推送数据，记录日志
	push(); 
}
```

### 优势

这些功能单一、规模较小的逻辑单元会有较大的复用性。在庞大的业务代码中，是不是总有一些代码由于耦合度较高，没办法复用？以这种提取方式，在之后的开发中，如果需要这些功能，就不需要再写重复的代码去做处理。

```
processData(){
	// 求差集
	getDifference();
	// 求交集
	getIntersection();
}
```



## 输入输出可预测

如果还想便于进行测试，就要保持这些逻辑单元的输入输出可预测。请回忆一下在业务测试一节通过 Mockito 编写桩代码的示例，测试用例在进行单元测试后的结果都是已知的，所以才能通过这种方式去做逻辑上的校验。

```
//设置桩代码，模拟查询过程
when(mockUserMapper.selectByMobile(mobile)).thenReturn(userResult);

//验证是否与我们预期的状态值相符
Assertions.assertEquals(CallResult.RETURN_STATUS_OK, loginCallResult.getCode());

```

当然工程中可以拥有很复杂的处理数据的过程，就像处理交集、差集一样，但处理后得到的始终是一个结果，如果桩代码的输入参数都不符合预期，那自然也不会得到预期的结果。



## 测试代码

此处测试获取差集、交集。

### 工具代码

```
@Data
public class CommonsListUtil<T> {

    /**
     * 输入参数-新数据集合
     */
    private List<T> newObjectList;
    /**
     * 输入参数-旧数据集合
     */
    private List<T> oldObjectList;
    /**
     * 新集合求出的差集
     */
    private List<T> tmpNewObjectList;
    /**
     * 旧集合求出的差集
     */
    private List<T> tmpOldObjectList;
    /**
     * 新集合求出的交集
     */
    private List<T> tmpIntersectNewObjectList;
    /**
     * 旧集合求出的交集
     */
    private List<T> tmpIntersectOldObjectList;

    public CommonsListUtil(List<T> newObjectList, List<T> oldObjectList) {
        this.newObjectList = newObjectList;
        this.oldObjectList = oldObjectList;
    }

    public CommonsListUtil invoke() {
        //复制原集合
        tmpNewObjectList = Lists.newArrayList();
        tmpNewObjectList.addAll(newObjectList);
        tmpOldObjectList = Lists.newArrayList();
        tmpOldObjectList.addAll(oldObjectList);
        tmpIntersectNewObjectList = Lists.newArrayList();
        tmpIntersectNewObjectList.addAll(newObjectList);
        tmpIntersectOldObjectList = Lists.newArrayList();
        tmpIntersectOldObjectList.addAll(oldObjectList);

        //求差集操作（对象必须重写hashCode 和equals方法）
        tmpNewObjectList.removeAll(oldObjectList);
        tmpOldObjectList.removeAll(newObjectList);

        //求交集操作（对象必须重写hashCode 和equals方法）
        tmpIntersectNewObjectList.retainAll(oldObjectList);
        tmpIntersectOldObjectList.retainAll(newObjectList);
        return this;
    }
}
```



### 测试代码

```
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
        log.info("oldObjectList = [{}]", JSONObject.toJSONString(tmpOldObjectList));
        log.info("tmpIntersectNewObjectList = [{}]", JSONObject.toJSONString(tmpIntersectNewObjectList));
        log.info("tmpIntersectOldObjectList = [{}]", JSONObject.toJSONString(tmpIntersectOldObjectList));

        //如果感兴趣，可以验证集合里的数据
        log.info("测试通过");
    }
}
```



## 运行结果

```
2020-03-12 17:41:39.546  INFO 15924 --- [main] dleStageTestOptimizationApplicationTests : tmpNewObjectList = [[{"defaultEndTime":120,"defaultStartTime":60,"dinnerTypeId":1,"dinnerTypeName":"早餐"}]]
2020-03-12 17:41:39.547  INFO 15924 --- [main] dleStageTestOptimizationApplicationTests : oldObjectList = [[{"defaultEndTime":250,"defaultStartTime":200,"dinnerTypeId":3,"dinnerTypeName":"晚餐"}]]
2020-03-12 17:41:39.547  INFO 15924 --- [main] dleStageTestOptimizationApplicationTests : tmpIntersectNewObjectList = [[{"defaultEndTime":180,"defaultStartTime":120,"dinnerTypeId":2,"dinnerTypeName":"午餐"}]]
2020-03-12 17:41:39.547  INFO 15924 --- [main] dleStageTestOptimizationApplicationTests : tmpIntersectOldObjectList = [[{"defaultEndTime":180,"defaultStartTime":130,"dinnerTypeId":2,"dinnerTypeName":"午餐"}]]
2020-03-12 17:41:39.547  INFO 15924 --- [main] dleStageTestOptimizationApplicationTests : 测试通过

```



## 分享

最后给大家分享一篇文章 [35个 Java 代码性能优化总结](https://wenku.baidu.com/view/d865624053d380eb6294dd88d0d233d4b14e3f30.html)。请根据工程的实际业务场景，参考这些优化点。

