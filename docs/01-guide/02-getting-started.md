---
order: 2
group: 
  title: 介绍
  order: 1
---

# 快速上手

本节将介绍如何在项目中使用 `Mockito`。我们还提供了演示demo的**[源码](https://github.com/xiyun-international/java-unit-docs)**，您可以从我们提供的demo进行入门学习。

## 准备工作

在这里，我将告诉您的是：您不需要准备任何的环境、依赖的包。`Spring`集成了`Mockito`，它会帮您引入较新的版本。截止目前`Spring `采用的 `Mockito`版本为`3.1.0`，`Mockito`最新版本为`3.3.0`，并支持`JUnit5`。在编写代码时直接引入`Mockito`包即可。

```java
import static org.mockito.Mockito.*;
```

如果您在开发中还需要模拟`static`、`private`方法，请在`pom`文件中，添加`PowerMock`的依赖。截止目前，`PowerMock`对`Mockito`的扩展只针对`Mockito2.X`以下版本，并且不支持`JUnit5`。所以请您谨慎使用。

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



## 第一个例子

您可以创建一个新的项目，来执行以下代码。其中我模拟了`List`集合，因为大多数人都熟悉集合接口（例如`add()`、`get()`和`clear()`方法）。在开发时请不要模拟集合类，应使用一个真实的实例。

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
    }

```

一旦模拟类创建，`Mockito`将记住所有交互。然后，您可以有选择地验证您感兴趣的任何交互。譬如我验证了`mockedList`的`add()`及`clear()`方法是否执行。您可以试着验证其他方法，观察运行结果会有什么不同。



## 更多简易用法

### 设置桩代码

```java
	@Test
    void secondExample() {
        //您可以模拟具体的类，而不仅仅是模拟接口
        LinkedList mockedList = mock(LinkedList.class);

        //桩代码
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //运行时将打印 "first"
        System.out.println(mockedList.get(0));

        //运行时将抛出异常
        //System.out.println(mockedList.get(1));

        //因为get(999)没有设置桩代码，所以会打印null
        System.out.println(mockedList.get(999));
        
        verify(mockedList).get(0);
    }
```

默认情况下，对于有返回值的方法，`Mockito`将会根据需要返回`null`。如：`int/Integer返回0`，`boolean/Boolean返回false`。[桩代码](https://baike.baidu.com/item/%E6%A1%A9%E4%BB%A3%E7%A0%81/6907051?fr=aladdin)可以被覆盖。一旦设置桩代码，不管调用多少次，该方法都将始终返回存根值。



### 参数匹配器

```java
	@Test
    void theThirdExample() {
        LinkedList mockedList = mock(LinkedList.class);
        //您可以使用内置的anyInt()匹配器来匹配参数
        when(mockedList.get(anyInt())).thenReturn("element");

        //999是int类型值，所以会打印“element”
        System.out.println(mockedList.get(999));

        //当您验证时，同样可以使用参数匹配器
        verify(mockedList).get(anyInt());
    }
```

参数匹配器允许灵活的设置桩代码或验证。您还可以通过内置的`argThat()`方法，传入您对于`ArgumentMatcher`的实现，去获得一个自定义的匹配器。

### 验证调用次数

```java
 @Test
    void fourthExample() {
        LinkedList mockedList = mock(LinkedList.class);
		//添加1次"once"
        mockedList.add("once");
		//添加2次"twice"
        mockedList.add("twice");
        mockedList.add("twice");
		//添加3次"three times"
        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //以下方法一致，默认验证方法调用次数为1，即times(1)
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //准确的验证调用次数
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //使用nerver()验证，等于times(0)。
        verify(mockedList, never()).add("never happened");

        //使用 atLeast()/atMost()验证。atLeast：最少。atMost：最多。
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }
```

有些情况下，您可能需要验证方法的执行次数。您可以根据**API**灵活的设置调用次数。默认`times(1)`，可以显示的省略使用`times(1)`。

如果您还想了解更多，请访问[**Mockito官方API**](https://javadoc.io/static/org.mockito/mockito-core/3.3.1/org/mockito/Mockito.html)进行深入学习。