# hap-forum

## 1.介绍
hap社交平台后端项目
## 2.软件架构
### 1.微服务架构
#### 1.nacos
#### 2.openfeign
#### 3.gateway

### 2.数据同步
#### 1.rabbitMQ
#### 2.定时任务

### 3.搜索
#### 1.elastic

### 4.智能推荐(暂时)
#### 1.关注
#### 2.常浏览标签

## 3.使用说明
### 1.启动项目
1. 启动nacos
2. 启动docker容器mysql,redis,rabbitMQ
3. 导入sql文件
4. 启动gateway项目
5. 启动其他项目
6. 启动前端项目(后续计划)