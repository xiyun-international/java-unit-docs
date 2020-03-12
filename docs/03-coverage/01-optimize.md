---
order: 1
group:
  title: 复杂场景案例
  order: 3
---

# 代码优化

## 介绍

质量好的代码，在结构上往往比较清晰，容易阅读，做出来的产品问题相对也会较少。（ ps : 我相信没有程序员不喜欢简短的代码）。对于动则上百行的代码，优化还是比较耗时的。如果在开发的过程中，可以参考设计模式的原则：[单一职责](https://blog.csdn.net/zhengzhb/article/details/7278174)、[里氏替换](https://blog.csdn.net/zhengzhb/article/details/7281833)、[依赖倒置](https://blog.csdn.net/zhengzhb/article/details/7289269)、[接口隔离](https://blog.csdn.net/zhengzhb/article/details/7296921)、[迪米特法则](https://blog.csdn.net/zhengzhb/article/details/7296930)、[开闭原则](https://blog.csdn.net/zhengzhb/article/details/7296944)及更多模式，可能就不会写出大段的业务代码，在之后的优化过程中可能就会轻松许多。

如果在开发中，已经出现了大段的业务代码，这时应该怎么办？先不去讨论业务代码变成这样的历史原因，站在便于进行单元测试的角度，来探讨一下，如果时间允许怎么对这样的业务代码进行优化。如果需要重构，请参考设计模式的原则及更多模式。



## 减小代码体积

为了能清楚理解下方业务代码，请先来观看业务的流程图。

![](../assets/670f60a22e085246f3ea8aa8540820d.png)

在编辑餐别数据时：

- 判断数据是否为空，如果为空则向集合中添加【全天】餐别。
- 获取门店历史餐别数据集合，处理新旧数据集合。获取各自的差集，及交集。
- 如果集合不为空，旧数据求出的差集批量删除，新集合求出差集批量添加，交集批量更新。
- 判断是否有数据变更记录，如果产生记录，则推送 isv，保存日志。
- 判断变更的数据集合中是否只有【全天】，如果不是，更新缓存。

了解了大致的业务流程后，请看没有优化的业务代码。

![](../assets/service.png)



想象一下，130 行的代码，在进行单元测试时，有时可能都不知道程序运行到某一行时会得到什么样的结果，这就增加了我们编写桩代码的难度，并且单元测试的代码覆盖率也难以保证。所以可以像下方伪代码展示出的这样，根据不同功能、业务进行代码提取来减小代码体积。

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



## 冗余代码复用

减小代码体积后，这些功能单一、规模较小的逻辑单元就有了极大的复用性。回忆一下，在我们庞大的业务代码中，是不是总有一些代码由于耦合度较高，没办法复用？比如现在这种提取方式，以后再需要处理集合获得差集、交集，就不需要再去写业务处理了。

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

## 分享

最后给大家分享一篇文章 [35个 Java 代码性能优化总结](https://wenku.baidu.com/view/d865624053d380eb6294dd88d0d233d4b14e3f30.html)。请根据工程的实际业务场景，参考这些优化点。

