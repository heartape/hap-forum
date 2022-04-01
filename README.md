# hap-forum

## 1.介绍
```text
hap社交平台后端项目
```
## 2.软件架构
### 1.微服务架构
#### 1.nacos
```text
使用参考https://nacos.io/zh-cn/docs/quick-start.html
无法启动的话设置standalone模式
```
#### 2.openfeign
#### 3.gateway

### 2.数据库
#### 1.持久层框架(mybatis-plus)

#### 2.分库分表(shardingsphere)

### 3.安全
#### 1.spring security
```text
目前已经整合进了gateway
```
### 4.推荐(后续)

### 5.搜索elastic(后续)

## 3.使用说明
### 1.启动项目
1. 启动nacos
2. 启动mysql,redis
3. 导入sql文件
4. 启动后端项目
5. 启动前端项目:https://gitee.com/heartape/hap-forum-web
6. 启动后台项目(后续)