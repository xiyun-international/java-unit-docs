---
order: 3
group:
  title: 其它
  order: 5
---

# API 介绍

本节将介绍官方文档中的部分 API，如果想了解更多关于 Mockito 的用法，请访问[ **官方 API**](https://javadoc.io/static/org.mockito/mockito-core/3.3.1/org/mockito/Mockito.html) 进行深入学习。

### 设置[桩代码](https://baike.baidu.com/item/%E6%A1%A9%E4%BB%A3%E7%A0%81/6907051?fr=aladdin)

桩代码：简单说，用来代替实际的函数执行，设置输入值，返回模拟结果，实现隔离依赖进行测试。

默认情况下对于模拟有返回值的方法，Mockito 将会根据需要返回`null`，如：方法返回类型为`int/Integer 返回 0`，`boolean/Boolean 返回 false`。

桩代码可以被覆盖，一旦设置了桩代码不管调用多少次，该方法都将始终返回设置的存根值。

```java
    @Test
    void secondExample() {
        //可以模拟具体的类，而不仅仅是模拟接口
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

### 参数匹配器

参数匹配器用在模拟方法的输入参数上，它允许灵活的设置桩代码及验证。如演示代码，桩代码设置了输入任意 int 类型参数，都将得到元素 "element"。还可以通过内置的`argThat()`方法，传入对于`ArgumentMatcher`的实现，去获得一个自定义的匹配器。

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

### 验证调用次数

在开发时可能还需要验证方法的执行次数，这时可以根据 **API** 灵活的设置调用次数。默认`times(1)`，可以显示的省略使用`times(1)`。

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
