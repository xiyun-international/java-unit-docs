# 介绍

禧云信息《Java 单元测试作战手册》是中台研发部针对于公司推出的单元测试的作战手册。我们希望用通俗易懂的方式进行描述，让单元测试真正的运用到项目上；

整个手册，主要分为两大类：

**基础讲解**：[快速上手](https://xiyun-international.github.io/java-unit-docs/02-getting-started)、[业务测试](https://xiyun-international.github.io/java-unit-docs/03-testing-method/01-business)、[数据库访问测试](https://xiyun-international.github.io/java-unit-docs/03-testing-method/02-dao)、[HTTP 接口测试](https://xiyun-international.github.io/java-unit-docs/03-testing-method/03-http)。

**高级技巧**：如何查看-[代码覆盖率](https://xiyun-international.github.io/java-unit-docs/03-testing-method/04-coverage)、如何进行[代码重构](https://xiyun-international.github.io/java-unit-docs/04-complex/01-optimize)、遇到[上游系统无法使用](https://xiyun-international.github.io/java-unit-docs/04-complex/02-system-error)该如何做测试。

## 为什么要进行测试

1. 从参与业务的角度出发，可以在不运行代码的前提下，就能知道该函数的预期结果。可以反馈功能输出，验证你的想法。
2. 从项目交接的角度出发，可以知道该函数运行的整体过程、细节和边界条件，保证代码重构的安全性。
3. 从提升质量的角度出发，代表着核心业务的质量与稳定，可以预防第三方服务或者基础设施宕机时，业务毫无征兆地受影响。

## 开发模式

[TDD](https://baike.baidu.com/item/TDD/9064369): 测试驱动开发，英文为 Testing Driven Development，强调的是一种开发方式**以先写测试用例**，来驱动整个项目。**先描述函数的输入以及输出，在去写代码逻辑。** `先想象要什么样的结果，在去写功能的一种开发方式`。

[BDD](https://baike.baidu.com/item/%E8%A1%8C%E4%B8%BA%E9%A9%B1%E5%8A%A8%E5%BC%80%E5%8F%91/9424963?fr=aladdin&fromtitle=BDD&fromid=10735732): 行为驱动开发，英文为 Behavior Driven Development，强调的是`系统最终的实现与用户期望的行为是一致的`，验证代码实现是否符合设计目标。**比如门店创建功能，用 HandleLess 技术模仿用户输入表单信息，点击确定按钮后，页面是否会显示"提交成功"这几个字。** 对功能是否有效的一项开发方式。

## 测试类型

[单元测试](https://baike.baidu.com/item/%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95)：单元可以是一个函数或一个模块类，只要输入不变就会返回同样的输出结果。

[集成测试](https://baike.baidu.com/item/%E9%9B%86%E6%88%90%E6%B5%8B%E8%AF%95/1924552)：集成测试，也叫组装测试或联合测试。在单元测试的基础上，将所有模块按照设计要求组装成为子系统或系统，进行集成测试。。

[功能测试](https://baike.baidu.com/item/%E5%8A%9F%E8%83%BD%E6%B5%8B%E8%AF%95/10921202?fr=aladdin)：功能测试就是对产品的各功能进行验证，根据功能测试用例，逐项测试，检查产品是否达到用户要求的功能。

[冒烟测试](https://baike.baidu.com/item/%E5%86%92%E7%83%9F%E6%B5%8B%E8%AF%95)：在正式提测之前，对主要功能进行的测试，确认主要功能是否满足需要软件是否能正常运行。

## 整体计划

| 阶段 | 目标 | 日期 |
| :---| :---: | :----: |
| 一期 | 单元测试常见的使用方式 | 2020-3-15 |
| 二期 | 如何让工程接入质量管理平台，如 Sonar | -- |
| 三期 | 基于现有的工具基础之上，包一层更好用的封装 | 需要一定的经验积累 |
| 四期 | 如何让[质量监控平台](/java-unit-docs/05-other/03-platform)，贯通整个公司 | 待定 |