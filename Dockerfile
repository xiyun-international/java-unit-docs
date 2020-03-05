FROM node:10

LABEL wubaiqing <wubaiqing@vip.qq.com>

RUN npm install -g cnpm --registry=https://registry.npm.taobao.org && \
  cnpm install -g dumi

EXPOSE 8000
WORKDIR /workspaceFolder
