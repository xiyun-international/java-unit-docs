---
order: 2
group: 
  title: 介绍
  order: 1
---

# 快速上手

本节将介绍什么是Mockito及如何在单元测试中快速使用 `Mockito`。我们还提供了后续所有演示demo的**[源码](https://github.com/xiyun-international/java-unit-docs/tree/master/source)**，您可以实际运行我们提供的demo来查看运行结果。

本节的演示demo与业务一节的演示demo相同。这里只介绍Mockito用法。



## Mokito

Mockito是一个针对 Java 的 Mock 框架。它能以一种可控的方式去获取一个假对象，通过假对象来模拟真实的对象行为。单元测测试的思路是想在不涉及依赖关系的情况下，测试代码的有效性，而我们就是通过Mockito来隔离这种依赖关系。



## 准备工作

我们采用的测试工具是JUnit + Mockito。在这里，我将告诉您的是：您不需要准备任何的环境、依赖的包。Spring集成了Mockito，它会帮您引入较新的版本。在您创建项目时Spring也会自动帮您引入starter-test模块。

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
</dependency>
```



**您只需要在编写测试代码时，静态的引入Mockito包即可。**

```java
import static org.mockito.Mockito.*;
```



## 演示Demo



### Mapper代码

```
/**
 * 通过手机号查询
 * @param mobile
 * @return
 */
UserDO selectByMobile(String mobile);
```



### 业务代码

```java
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    @Override
    public CallResult login(UserDO userDO) {
        UserDO userEntity = userDOMapper.selectByMobile(userDO.getMobile());
        if (userEntity == null) {
            log.info("没有该用户信息，请先注册！");
            return CallResult.fail(CallResult.RETURN_STATUS_UNREGISTERED, "没有该用户信息，请先注册！");
        }
        if (!userDO.getPassword().equals(userEntity.getPassword())) {
            return CallResult.fail(CallResult.RETURN_STATUS_PASW_INCORRECT, "您的密码不正确！");
        }
        return CallResult.success(CallResult.RETURN_STATUS_OK, "登录成功！", userEntity);
    }
}
```



### 测试代码

```
@SpringBootTest
class MiddleStageTestServiceByAnnotationApplicationTests {

    @Test
    @DisplayName("登录测试")
    void login() {

        //验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

        //1.模拟登录业务中，依赖的dao层查询接口
        UserDOMapper mockUserDOMapper = mock(UserDOMapper.class);
        //将模拟的接口注入
        UserServiceImpl userService = new UserServiceImpl(mockUserDOMapper);

        //2.当程序运行时，模拟查询结果，返回我们指定的预期结果
        when(mockUserDOMapper.selectByMobile(mobile)).thenReturn(userEntity);

		//3.执行登录方法
        CallResult loginCallResult = userService.login(userDO);

        //4.验证是否执行
        verify(mockUserDOMapper).selectByMobile(mobile);

        //验证是否与我们预期的状态值相符
        Assertions.assertEquals(CallResult.RETURN_STATUS_PASW_INCORRECT, loginCallResult.getCode());
    }
}
```



## 使用介绍

1. 在这里，我模拟了用户登录的场景。通过mock方法，传入具有依赖关系的UserDOMapper的类类型，来获取一个模拟的接口。
2. 将模拟接口设置进UserServiceImpl中，并设置桩代码。当mockUserDOMapper.selectByMobile方法输入参数为mobile时，return模拟的结果。
3. 执行被测试业务方法。
4. 验证业务方法是否执行，及验证业务状态码是否符合预期。



## 运行结果

由于我期望的状态码为1(CallResult.RETURN_STATUS_OK)，实际处理的状态码为-3，密码不匹配的状态码。所以抛出了异常。

```java
org.opentest4j.AssertionFailedError: 
Expected :1
Actual   :-3
```



## 说明

单元测试的思路就是要在不涉及依赖关系的情况下测试代码。如果您依赖的代码通过了单元测试，并且依赖关系也正常，那么他们就会同时工作正常。所以您可以通过Mockito来模拟这种依赖关系，而不产生真实调用。

