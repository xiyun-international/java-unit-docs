---
order: 4
group:
  title: 单元测试
  order: 2
---

# HTTP 接口测试

在您保证了 Service 和 DAO 层的测试后。对于暴露出的HTTP接口，您只需要关注它是否可用即可。对于Http接口的测试，您同样要关注几点原则：

- 全自动&非交互式
- 设定自动回滚
- 遵守AIR原则
- 遵守BCDE原则



## 演示Demo

### Service代码

> 与业务测试一节中的代码一致，这里不予展示。



### Controller代码

这里为用户登录场景。判断数据是否为空，执行登录。

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



### 测试代码

- 通过 @BeforeAll 注解，在测试方法执行前准备测试用例。
- 通过 Spring自带的 MockMvc 对象，进行 HTTP 接口测试。它实现了对 HTTP 请求的模拟，能够直接以网络的形式，转换到 Controller 的调用，并且不依赖网络环境。
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

        //验证测试用例是否创建
        Assertions.assertNotNull(userDO, "userDO is null");

        MockHttpServletResponse response = 		mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userDO)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        //验证http状态码
        Assertions.assertNotEquals(MockMvcResultMatchers.status().isOk(), response.getStatus());
        CallResult callResult = JSONObject.parseObject(response.getContentAsString(), CallResult.class);
        //验证业务状态码
        Assertions.assertEquals(callResult.getCode(), 			               CallResult.RETURN_STATUS_UNREGISTERED);
        log.info("测试通过");

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
2020-03-08 19:44:39.795  INFO 19020 --- [main] s.t.w.MiddleStageTestWebApplicationTests : 测试通过

```

