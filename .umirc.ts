// @see https://d.umijs.org/config
function getMenus(opts: { base: "/docs" }) {
  const menus = {
    "/docs": [
      {
        title: "VERSION 3.X",
        children: []
      },
      {
        title: "介绍",
        children: ["/00-introduce/index.md"]
      }
    ]
  };
  console.log(menus[opts.base]);
  return (menus[opts.base] as []).map((menu: any) => {
    return menu;
  });
}

export default {
  mode: "doc",
  logo: "https://f2.xiyunerp.com/xy-logo.png",
  title: "禧云信息",
  description: "Java 单元测试作战手册",
  publicPath: '/java-unit-docs/',
  menus: {
    "/docs": getMenus({ base: "/docs" })
  },
  navs: [
    {
      title: "GitHub",
      path: "http://github.com/xiyun-international/java-unit-docs"
    }
  ]
};
