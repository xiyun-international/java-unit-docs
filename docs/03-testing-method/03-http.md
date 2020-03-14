---
order: 3
group:
  title: 单元测试
  order: 3
---

# HTTP 接口测试

## 介绍

在保证了 Service 和 DAO 层的测试后，对于暴露出的 HTTP 接口，只需要关注它是否可用即可。对于 HTTP 接口的测试同样要关注几点[原则](https://github.com/alibaba/p3c/blob/master/p3c-gitbook/%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95.md#L17)：

- 全自动&非交互式
- 设定自动回滚
- 遵守 AIR 原则
- 遵守 BCDE 原则

## 演示 [Demo](https://github.com/xiyun-international/java-unit-docs/tree/master/source/middle-stage-test-web)

### Controller 代码

以用户登录的场景举例：判断数据是否为空，执行登录。

```java
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user/login")
    @ResponseBody
    public CallResult login(@RequestBody UserDO userDO) {
        if (userDO == null) {
            return CallResult.fail(CallResult.RETURN_STATUS_PARAM_ERROR, "参数异常，请检查参数！");
        }
        return userService.login(userDO);
    }
}
```

### Service 代码

```java
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * Mockito使用注解注入依赖关系，需提供构造器
     *
     * @param userMapper
     */
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public CallResult login(UserDO userDO) {
        UserDO userResult = userMapper.selectByMobile(userDO.getMobile());
        if (userResult == null) {
            log.info("没有该用户信息，请先注册！");
            return CallResult.fail(CallResult.RETURN_STATUS_UNREGISTERED, "没有该用户信息，请先注册！");
        }
        if (!userDO.getPassword().equals(userResult.getPassword())) {
            return CallResult.fail(CallResult.RETURN_STATUS_PASW_INCORRECT, "您的密码不正确！");
        }
        return CallResult.success(CallResult.RETURN_STATUS_OK, "登录成功！", userResult);
    }
}
```


### 测试代码

- 通过 @BeforeAll 注解，在测试方法执行前准备测试数据。
- 通过 Spring 自带的 `MockMvc` 对象，进行 HTTP 接口测试。它实现了对 HTTP 请求的模拟，能够直接以网络的形式，转换到 Controller 的调用，并且`不依赖网络环境`。
- 通过 MockMvc 设置请求接口地址、请求方式、参数类型、参数、打印响应、获取响应。

```java
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class MiddleStageTestWebApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    static UserDO userDO;

    @BeforeAll
    static void beforLoginTest() {
        userDO = new UserDO();
        userDO.setMobile("17612345678");
        userDO.setPassword("123456");
    }

    @Test
    void loginInterfaceTest() throws Exception {

        // 验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

        // !注意这里代码
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userDO)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        // 验证 HTTP 状态码
        Assertions.assertNotEquals(MockMvcResultMatchers.status().isOk(), response.getStatus());
        CallResult callResult = JSONObject.parseObject(response.getContentAsString(), CallResult.class);

        // 验证业务状态码
        Assertions.assertEquals(callResult.getCode(),CallResult.RETURN_STATUS_UNREGISTERED);
        log.info("[测试通过]");
    }
}
```

## 运行结果

```java
MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"application/json;charset=UTF-8"]
     Content type = application/json
             Body = {"code":-2,"msg":"没有该用户信息，请先注册！","content":null}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
2020-03-08 19:44:39.795  INFO 19020 --- [main] s.t.w.MiddleStageTestWebApplicationTests : [测试通过]

```
