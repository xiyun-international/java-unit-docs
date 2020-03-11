---
order: 1
group:
  title: 复杂场景案例
  order: 3
---

# 代码优化

## 介绍

代码优化是很有意义的一件事情，质量较好的代码，逻辑、结构往往比较清晰。自然而然代码中类和方法的长度就会相对较短，也更易读，这样，做出来的产品问题相对也会较少。（ ps : 我相信没有程序员不喜欢简短的代码）。

对于动则上百行的代码，优化还是非常耗时的。如果我们在开发的过程中，可以参考设计模式的六项原则：[单一职责](https://blog.csdn.net/zhengzhb/article/details/7278174)、[里氏替换](https://blog.csdn.net/zhengzhb/article/details/7281833)、[依赖倒置](https://blog.csdn.net/zhengzhb/article/details/7289269)、[接口隔离](https://blog.csdn.net/zhengzhb/article/details/7296921)、[迪米特法则](https://blog.csdn.net/zhengzhb/article/details/7296930)、[开闭原则](https://blog.csdn.net/zhengzhb/article/details/7296944)及更多模式，可能我们就不会写出一坨坨的代码，那在之后的优化过程中可能就会轻松许多。

如果在开发中，我们已经遇到了一坨坨的业务代码，这时应该怎么办？我们不去讨论业务代码变成这样的历史原因，我们站在便于进行单元测试的角度，来探讨一下，如果时间允许怎么对这样的业务代码进行优化。如果需要重构，请参考设计模式的六项原则及更多模式。



## 减小代码体积

回忆一下项目中的大段代码，是不是或多或少的，都将下方展示的许多方法放到了一个方法中，后人再追加功能，就变的越来越难维护。其实不管多复杂的业务，我们总能像这段代码展示的这样，根据不同的功能点将业务细化。

```
service() {
	// 用户代码
	userService.operate();
	// 支付配置代码
	payConfig.getConfig();
	// 发起支付代码
	payService.pay();
} 

共计：300 行
userService.handler(); // 100 行
payConfig.getConfig(); // 100 行
payService.pay(); //100 行

operate(){
	//验证 20行
	validate(); 
	//处理数据 50行
	ProcessData();
	//获取数据 30行
	gerUser(); 
}

```

想象一下，300 行的代码，在进行单元测试时，有时可能都不知道程序运行到某一行时会得到什么样的结果，这就增加了我们编写桩代码的难度，并且单元测试的代码覆盖率也难以保证。



ps: 这块要加一个spring 源码的展示，现在编译源码有点问题。稍后会加。



## 冗余代码复用

减小了代码体积后，这些功能单一、规模较小的逻辑单元就有了极大的复用性。回忆一下，在我们庞大的业务代码中，是不是总有一些代码由于耦合度较高，没办法复用？如果按照示例一样去做拆分，不管是数据验证，还是业务处理，都能较好的提高代码的复用性。

```
operate(){
	//验证 20行
	validate(); 
	//处理数据 50行
	ProcessData();
	//获取数据 30行
	gerUser(); 
}
```



## 输入输出可预测

如果还想便于进行测试，再有比较重要的一点是，要保持这些逻辑单元的输入输出可预测。我们回忆一下在业务测试一节通过 Mokito 编写桩代码的示例，我们的测试用例在进行单元测试后的结果都是已知的，所以我们才能通过这种方式去做逻辑上的校验。

```
//设置桩代码，模拟查询过程
when(mockUserMapper.selectByMobile(mobile)).thenReturn(userResult);

//验证是否与我们预期的状态值相符
Assertions.assertEquals(CallResult.RETURN_STATUS_OK, loginCallResult.getCode());

```

当然我们可以拥有很复杂的处理数据的过程，但处理后得到的始终是一个结果，如果桩代码的输入参数都不符合预期，那自然也不会得到预期的结果。



## 分享

最后给大家分享一篇文章 [35个 Java 代码性能优化总结](https://wenku.baidu.com/view/d865624053d380eb6294dd88d0d233d4b14e3f30.html)。请根据工程的实际业务场景，参考这些优化点。

