---
order: 4
group:
  title: 编写测试用例
  order: 2
---

# HTTP 接口测试

在您保证了Service和DAO层的测试后。对于暴露出来的HTTP接口，您只需要关注它是否可用即可。因为业务和数据访问的正确性都由他们自己的单元测试来保证了。对于Http接口的测试，您同样要关注几点原则：

- 全自动&非交互式
- 设定自动回滚
- 遵守AIR原则
- 遵守BCDE原则



## 演示Demo

### 业务代码

> 与业务测试一节中的代码一致，这里不予展示。
>



### 测试代码

```
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
        Assertions.assertEquals(callResult.getCode(), CallResult.RETURN_STATUS_OK);

    }
}
```



## 代码介绍

- 通过@BeforeAll注解设计测试用例
- 通过Spring自带的MockMvc对象，进行http接口测试。它实现了对http请求的模拟，能够直接以网络的形式，转换到Controller的调用，并且不依赖网络环境。
- 通过MockMvc设置请求接口地址、请求方式、参数类型、参数、打印响应、获取响应。



## 运行结果

通过andDo(MockMvcResultHandlers.print())一行打印MockHttpServletResponse信息。

由于期望的业务状态码为1(CallResult.RETURN_STATUS_OK)，实际为-2(未注册状态码)，所以这里抛出异常。您可对照我打印的响应信息，来熟悉这种方式。

```
MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"application/json;charset=UTF-8"]
     Content type = application/json
             Body = {"code":-2,"msg":"没有该用户信息，请先注册！","content":null}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []

org.opentest4j.AssertionFailedError: 
Expected :-2
Actual   :1
```

