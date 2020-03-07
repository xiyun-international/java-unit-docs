---
order: 3
group:
  title: 介绍
  order: 1
---

# FAQ

## 技术选型

​ 现在市场上的 mock 框架有，`Mockito`，`EasyMock`，`Jmock`。而`PowerMock`实际上是对`Mockito`以及`EasyMock`的扩展。针对他们不具备模拟静态，私有方法等功能做的扩展，扩展的语法与各自的 mock 框架语法一致。下面简单介绍一下这些 mock 框架。

- **Jmock**

  > `Jmock`是一个利用 mock 对象来测试`JAVA`代码的轻量级测试工具，他是`xUnit`的一员，它从`JUnit`发展而来，是`JUnit`的一个增强库。通过 expect-run-verify （期望-运行-验证）的方式来完成测试过程。这种方式需要在运行前记录严格的期望，这常常会导致您被迫查看无关的交互。

- **EasyMock**

  > `EasyMock`是早期比较流行的 mock 框架。它也是通过 expect-run-verify （期望-运行-验证）的方式完成测试过程，它可以验证方法的调用种类、次数、顺序，可以令 mock 对象返回指定的值或抛出指定异常。

- **Mockito**

  > `Mockito`是一个针对 Java 的 mock 框架。它与`EasyMock`和`JMock`很相似，是一套通过简单的方法对于指定的接口或类生成 mock 对象的类库，避免了手工编写 mock 对象。但`Mockito`是通过在执行后校验哪些行为已经被调用，它消除了对期望行为的需要。使用`Mockito`在准备阶段只需花费很少的时间，可以使用`简洁的API`编写出漂亮的测试，可以对具体的类创建 mock 对象，并且有“监视”非 Mock 对象的能力。

  通过表格我们来看一下他们的能力对比。

|                                      | `JMock` | `EasyMock` | `Mockito` | `PowerMock(EasyMock)` | `PowerMock(Mockito)` |
| :----------------------------------: | :-----: | :--------: | :-------: | :-------------------: | :------------------: |
|              调用数限制              |    √    |     √      |     √     |           √           |          √           |
|          记录严格的预期结果          |    √    |     √      |           |           √           |                      |
|               显式验证               |         |            |     √     |                       |          √           |
|              部分 mock               |    √    |            |     √     |           √           |          √           |
|              级联 mock               |         |            |     √     |                       |          √           |
|             多接口 mock              |         |            |     √     |                       |          √           |
|            注释类型 mock             |         |     √      |     √     |                       |          √           |
|           mock 的自动注入            |         |            |     √     |                       |          √           |
| final、static 和 private 方法的 mock |         |            |           |           √           |          √           |

​ `Mockito`使用起来简单，学习成本很低，功能强大，并且具有非常`简洁的API`，测试代码的可读性很高，所以它十分受欢迎，用户群体也非常多，很多开源软件也选择了`Mockito`。因此，我们选择了`Mockito`做为我们的 mock 框架。如果想了解更多有关`Mockito`的信息，可以访问其[官方网站](https://site.mockito.org/)。

## 模拟 static/private 方法

如果您在开发中还需要模拟 static、private 方法，请在 pom 文件中，添加 PowerMock 的依赖。截止目前 Spring 采用的 Mockito 版本为`3.1.0`，而 PowerMock 对 Mockito 的扩展只针对 Mockito2.X 以下版本，并且`不支持JUnit5`。所以请您谨慎使用。

```java
<dependency>
	<groupId>org.powermock</groupId>
	<artifactId>powermock-module-junit4</artifactId>
	<version>2.0.5</version>
</dependency>
<dependency>
	<groupId>org.powermock</groupId>
	<artifactId>powermock-api-mockito2</artifactId>
	<version>2.0.5</version>
</dependency>
```
