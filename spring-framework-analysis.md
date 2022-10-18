# 读尽天下源码，心中自然无码

:star: 针对最新的5.3.23版本的Spring框架文档
## 主要模块
### Overview 概述

history, design philosophy, feedback, getting started.

发展历史，设计理念，反馈，起步

### Core 核心

IoC Container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP.
Ioc 容器，事件，资源，国际化，校验，数据绑定，类型转换，Spring表达式（Spring Expression Language）,切面编程

### Testing 测试
Mock Objects, TestContext Framework, Spring MVC Test, WebTestClient.

### Data Access
Transactions, DAO Support, JDBC, R2DBC, O/R Mapping, XML Marshalling.

### Web Servlet
Spring MVC, WebSocket, SockJS, STOMP Messaging.

### Web Reactive
Spring WebFlux, WebClient, WebSocket, RSocket.

### Integration
Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Caching.

### Languages
Kotlin, Groovy, Dynamic Languages.
支持多种其他语言

### Appendix
Spring properties.

### Wiki
What’s New, Upgrade Notes, Supported Versions, and other cross-version information.


根据官方文档中
The Spring Framework is divided into modules. Applications can choose which modules they need. At the heart are the modules of the core container, including a configuration model and a dependency injection mechanism.
Spring框架分为多个模块。应用程序可以选择所需的模块。核心是核心容器的模块，包括配置模型和依赖注入机制。

而在Spring框架核心中，IOC控制反转容器最重要。
Foremost amongst these is the Spring Framework’s Inversion of Control (IoC) container. A thorough treatment of the Spring Framework’s IoC container is closely followed by comprehensive coverage of Spring’s Aspect-Oriented Programming (AOP) technologies. 