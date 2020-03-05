---
order: 2
group: 
  title: 介绍
  order: 1
---

# 快速上手


## 准备工作

​		在这里，我将告诉您的是：您不需要准备任何的环境、依赖的包。`Spring`集成了`Mockito`，它会帮我们引入较新的版本。截止目前`Spring `采用的 `Mockito`版本为`3.1.0`，`Mockito`最新版本为`3.3.0`，并支持`JUnit5`。在编写代码时直接引入`Mockito`包即可。

```java
import static org.mockito.Mockito.*;
```

​		如果您在开发中还需要模拟`static`、`private`方法，请在`pom`文件中，添加`PowerMock`的依赖。截止目前，`PowerMock`对`Mockito`的扩展只针对`Mockito2.X`以下版本，并且不支持`JUnit5`。所以请您谨慎使用。

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

​		这是使用`Mockito`进行测试的最简单的例子。其中我模拟了`List`集合，因为大多数人都熟悉集合接口（例如`add()`、`get()`和`clear()`方法）。在开发时请不要模拟集合类，应使用一个真实的实例。

```java
//静态的导入Mockito,这样代码看起来更清晰
import static org.mockito.Mockito.*;

    @Test
    void firstDemo() {
        //创建模拟对象
        List mockedList = mock(List.class);
        
        //使用模拟对象
        mockedList.add("one");
        mockedList.clear();
        
        //验证
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
 
```

​		一旦模拟类创建，`Mockito`将记住所有交互。然后，您可以有选择地验证您感兴趣的任何交互。譬如我验证了`mockedList`的`add()`及`clear()`方法是否执行。您可以试着验证其他方法，观察运行结果会有什么不同。