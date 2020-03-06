---
order: 2
group: 
  title: 介绍
  order: 1
---

# 快速上手

本节将介绍如何在项目中使用 `Mockito`。我们还提供了演示demo的**[源码](https://github.com/xiyun-international/java-unit-docs/tree/master/src/test/java/com/xiyun)**，您可以从我们提供的demo进行入门学习。

## 准备工作

在这里，我将告诉您的是：您不需要准备任何的环境、依赖的包。Spring集成了Mockito，它会帮您引入较新的版本。在您创建项目时Spring也会自动帮您引入starter-test模块，采用JUnit5。

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

您只需要在编写测试代码时，静态的引入Mockito包即可。

```java
import static org.mockito.Mockito.*;
```



## 演示Demo

您可以创建一个新的项目，来执行以下代码。其中我模拟了List接口，因为大多数人都熟悉集合接口（例如add()、get()和clear()方法）。在开发时请不要模拟集合类，应使用一个真实的实例。

```java
//静态的导入Mockito,这样代码看起来更清晰
import static org.mockito.Mockito.*;

    @Test
    void firstExample() {
        
        //创建模拟对象
        List mockedList = mock(List.class);
        
        //使用模拟对象
        mockedList.add("one");
        mockedList.clear();
        
        //默认情况下验证方法调用1次
        verify(mockedList).add("one");
        verify(mockedList).clear();
        
        //验证是否调用remove方法
        verify(mockedList).remove("one");
    }

```



## 运行结果

```
Wanted but not invoked:
list.remove("one");
-> at com.xiyun.xiyuntest.web.XiyunTestWebApplicationTests.firstExample(XiyunTestWebApplicationTests.java:27)
```



## 说明

一旦模拟类创建，Mockito将记住所有交互。然后，您可以有选择地验证您感兴趣的任何交互。譬如我验证了mockedList的add、clear及remove方法是否执行，由于remove方法并没有得到执行，所以产生了异常。您可以试着验证其他方法，观察运行结果会有什么不同。

