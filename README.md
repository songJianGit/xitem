# xitem

如果 xitem 有帮助到您，请为我们点个赞！<br/>

xitem是一个轻量级项目管理和任务协同系统，适合中小团队。<br/>
参考开源项目 Leantime 的设计思路，删繁就简，简单易上手。<br/>
任何人都能轻松使用，能有效降低沟通成本，增加团队的工作效率。<br/>
是 ClickUp、ONES 或 禅道 的开源替代品。

## 功能模块

- **任务管理**：项目概述、待办任务、里程碑、文章、文件
- **我的工作**：仪表盘（我的待办）、项目中心
- **设置**：个人设置、用户管理、角色管理、日志查询、任务状态、任务优先级

## 通过 docker-compose 快速开始
```
下载文件：wget https://github.com/songJianGit/xitem/releases/download/v0.1/xitem_docker.zip
解压文件：unzip xitem_docker.zip
进入目录：cd xitem_docker
启动项目：docker-compose up -d
```
部署完毕后，浏览器访问：http://ip:9095/

## 项目环境

- JDK 17
- MySQL 8.0+
- Maven 3+