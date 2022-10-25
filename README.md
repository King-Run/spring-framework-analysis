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

## 开始
根据官方文档中

The Spring Framework is divided into modules. Applications can choose which modules they need. At the heart are the modules of the core container, including a configuration model and a dependency injection mechanism.

Spring框架分为多个模块。应用程序可以选择所需的模块。核心是核心容器的模块，包括配置模型和依赖注入机制。

而在Spring框架核心中，IOC控制反转容器最重要。

Foremost amongst these is the Spring Framework’s Inversion of Control (IoC) container. A thorough treatment of the Spring Framework’s IoC container is closely followed by comprehensive coverage of Spring’s Aspect-Oriented Programming (AOP) technologies. 

**The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC container.** 
The BeanFactory interface provides an advanced configuration mechanism capable of managing any type of object. ApplicationContext is a sub-interface of BeanFactory. It adds:

- Easier integration with Spring’s AOP features

- Message resource handling (for use in internationalization)

- Event publication

- Application-layer specific contexts such as the WebApplicationContext for use in web applications.

而在org.springframework.beans和org.springframework.context包是Spring框架IoC容器的基础。

## IOC 控制反转
### 一、体验
:star:体验一下IOC容器控制反转
前提: 把Spring源码clone下来。(也可以使用我仓库的代码调试)
如果编译报错：
无效发行版本：xx,请把JDK升级为17版本

#### ① 方式一：配置文件注入

Config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
	<bean class="base.SimpleBean"></bean>
</beans>
```

SimpleBean.java

```java
// 需要被控制反转的类
public class SimpleBean {

	public void awkwardSmile(){
		System.out.println("I definitely love coding!");
	}
}
```

Main.java

```java
public class GetStart {

	public static void main(String[] args) {
		byConfigFile();
	}
	public static void byConfigFile() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		SimpleBean bean = context.getBean(SimpleBean.class);
		bean.awkwardSmile();
		context.close();
	}
}
```



#### ② 方式二：配置文件注入

AnnotationSimpleBean.java

```java
// 需要被控制反转的类
public class AnnotationSimpleBean {

	private String name;

	private String email;

	public AnnotationSimpleBean(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public void introduce(){
		System.out.println(String.format("Here is %s, my email is %s. Welcome to send me an office!",this.name,this.email));
	}

}
```

SimpleBeanConfig.java

```java
@Configuration
public class SimpleBeanConfig {

	@Bean
	public AnnotationSimpleBean annotationBean(){
		return new AnnotationSimpleBean("Xander chow","zhrunxin33@gmail.com");
	}
}
```

执行器入口Main.java

```java
public static void byAnnotationConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SimpleBeanConfig.class);
		AnnotationSimpleBean bean = context.getBean(AnnotationSimpleBean.class);
		bean.introduce();
		context.close();
	}
```

可以看到我们创建对象的时候，就不需要使用new xx()。绝大多数时候，我们只需要用到单例，这就意味着我们每个类都需要写单例模式。当对象创建销毁交给Spring容器管理，我们就可以不用考虑（默认是单例模式）。



### 二、继承关系

光标停留在**ClassPathXmlApplicationContext**和**AnnotationConfigApplicationContext**上，使用快捷键Ctrl+Alt+U查看继承图。



**1.ClassPathXmlApplicationContext**

![image-20221019183239926](spring-analysis/doc/images/image-20221019183239926.png)



**2.AnnotationConfigApplicationContext**

![image-20221019183353840](spring-analysis/doc/images/image-20221019183353840.png)

可知BeanFactory是IOC容器功能的根接口。

我们不要害怕阅读，双击左上角的BeanFactory接口。可以看到这个类定义了哪些行为（接口）

![image-20221020162959049](spring-analysis/doc/images/image-20221020162959049.png)

看得到，这里定义了一系列获取Bean实例的方法，并且定义了判断容器是否包含该bean，是否单例或多例模式。

这样，每个接口粗略看看（不用死记的）

- `Beanfactory`: 获取Bean实例的方法，并且定义了判断容器是否包含该bean，是否单例或多例模式.
- `applicationContext`: 应用上下文核心接口， 各类上下文实现类都是它的实现类
- `ListableBeanFactory`: 提供一系列查询Bean的方法，以及查询Bean的配置
- `HierarchicalBeanFactory`: 在 `BeanFactory`基础上提供了关于父 BeanFactory 的支持
- `EnvironmentCapable`: 提供了获取环境配置的方法
- `ApplicationEventPublisher`: 提供事件通知接口，上下文开始创建，停止等操作都会产生事件通知。
- `ResourceLoader`: 定义了获取资源加载的方式

这些接口笔者对其进行了简单的分类. (各位读者如果有新的分类也可以进行补充)

- 第一类是关于资源处理， 比如 `Resource` 、`ResourceLoader`
- 第二类是关于注册形式， 在 Spring 中关于注册的几个核心就是 alias(`AliasRegistry`) 、Bean 定义注册(`BeanDefinitionRegistry`) 、单例Bean注册(`SingletonBeanRegistry`)
- 第三类是关于生命周期的， 生命周期又可以分为两类，
  - 第一类是容器的生命周期， 容器生命周期的核心接口: `Lifecycle`
  - 第二类是Bean的生命周期， Bean 生命周期的接口有: `InitializingBean`、`DisposableBean`
- 第四类是关于Bean拓展的， 如: `BeanPostProcessor`、`Aware` 系列接口
- 第五类是关于上下文的接口主要以: `ApplicationContext` 作为主导接口
- 第六类是读取接口主要用来读取信息，如: `BeanDefinitionReader` . 这也可以归到资源处理中， 笔者在这里还是将其提取出来做一个单独的大类

:star: 记住类继承图是看到整个代码设计脉络很重要的手段。`Cril+Alt+U`



### 三、核心上下文解析

再来看看**ClassPathXmlApplicationContext**类

```java
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	@Nullable
	private Resource[] configResources;
	// 调用父容器
    public ClassPathXmlApplicationContext(ApplicationContext parent) {
    	super(parent);
  	}
    
    public ClassPathXmlApplicationContext(
			String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
			throws BeansException {

		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
			refresh();
		}
	}

}
```

可以看到这个类很简单，基本上都是构造方法。

我们使用`new ClassPathXmlApplicationContext("config.xml")`调用的就是这个方法。

可以看到整体来看源码比较简单，只有setConfigLocations和refresh两个方法没有看到具体的实现。但是如果你因为这个而小巧了Spring那可就大错特错了，setConfigLocations只是一个开胃小菜，refresh才是我们本文的重点



#### **setConfigLocations**

setConfigLocations方法的主要工作有两个：创建环境对象ConfigurableEnvironment和处理ClassPathXmlApplicationContext传入的字符串中的占位符

跟着setConfigLocations方法一直往下走

```java
	public void setConfigLocations(@Nullable String... locations) {
		if (locations != null) {
			Assert.noNullElements(locations, "Config locations must not be null");
			this.configLocations = new String[locations.length];
			for (int i = 0; i < locations.length; i++) {
				this.configLocations[i] = resolvePath(locations[i]).trim();
			}
		}
		else {
			this.configLocations = null;
		}
	}

	protected String resolvePath(String path) {
		return getEnvironment().resolveRequiredPlaceholders(path);
	}
```

这里getEnironment()就涉及到了创建环境变量相关的操作了

##### 1.获取环境变量

```java
	@Override
	public ConfigurableEnvironment getEnvironment() {
		if (this.environment == null) {
			this.environment = createEnvironment();
		}
		return this.environment;
	}
```

实际上创建了一个标准环境对象，可以通过输出来看看

```java
	@Test
	public void printSystemEnvironment(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		ConfigurableEnvironment environment = context.getEnvironment();
		Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
		systemEnvironment.forEach((k,v)->System.out.println(k+":"+v));
		context.close();
	}
```


接着看`createEnvironment()`方法，发现它返回了一个`StandardEnvironment`类，而这个类中的`customizePropertySources`方法就会往资源列表中添加Java进程中的变量和系统的环境变量

```java
protected void customizePropertySources(MutablePropertySources propertySources) {
		propertySources.addLast(
				new PropertiesPropertySource(SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, getSystemProperties()));
		propertySources.addLast(
				new SystemEnvironmentPropertySource(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, getSystemEnvironment()));
	}
```

##### 2.处理占位符

再次回到 `resolvePath`方法后跟进通过上方获取的`ConfigurableEnvironment`接口的`resolveRequiredPlaceholders`方法，终点就是下方的这个方法。这个方法主要就是处理所有使用${}方式的占位符

```java
	protected String parseStringValue(
			String value, PlaceholderResolver placeholderResolver, @Nullable Set<String> visitedPlaceholders) {

		int startIndex = value.indexOf(this.placeholderPrefix);
		if (startIndex == -1) {
			return value;
		}

		StringBuilder result = new StringBuilder(value);
		while (startIndex != -1) {
			int endIndex = findPlaceholderEndIndex(result, startIndex);
			if (endIndex != -1) {
				String placeholder = result.substring(startIndex + this.placeholderPrefix.length(), endIndex);
				String originalPlaceholder = placeholder;
				if (visitedPlaceholders == null) {
					visitedPlaceholders = new HashSet<>(4);
				}
				if (!visitedPlaceholders.add(originalPlaceholder)) {
					throw new IllegalArgumentException(
							"Circular placeholder reference '" + originalPlaceholder + "' in property definitions");
				}
				// Recursive invocation, parsing placeholders contained in the placeholder key.
				placeholder = parseStringValue(placeholder, placeholderResolver, visitedPlaceholders);
				// Now obtain the value for the fully resolved key...
				String propVal = placeholderResolver.resolvePlaceholder(placeholder);
				if (propVal == null && this.valueSeparator != null) {
					int separatorIndex = placeholder.indexOf(this.valueSeparator);
					if (separatorIndex != -1) {
						String actualPlaceholder = placeholder.substring(0, separatorIndex);
						String defaultValue = placeholder.substring(separatorIndex + this.valueSeparator.length());
						propVal = placeholderResolver.resolvePlaceholder(actualPlaceholder);
						if (propVal == null) {
							propVal = defaultValue;
						}
					}
				}
				if (propVal != null) {
					// Recursive invocation, parsing placeholders contained in the
					// previously resolved placeholder value.
					propVal = parseStringValue(propVal, placeholderResolver, visitedPlaceholders);
					result.replace(startIndex, endIndex + this.placeholderSuffix.length(), propVal);
					if (logger.isTraceEnabled()) {
						logger.trace("Resolved placeholder '" + placeholder + "'");
					}
					startIndex = result.indexOf(this.placeholderPrefix, startIndex + propVal.length());
				}
				else if (this.ignoreUnresolvablePlaceholders) {
					// Proceed with unprocessed value.
					startIndex = result.indexOf(this.placeholderPrefix, endIndex + this.placeholderSuffix.length());
				}
				else {
					throw new IllegalArgumentException("Could not resolve placeholder '" +
							placeholder + "'" + " in value \"" + value + "\"");
				}
				visitedPlaceholders.remove(originalPlaceholder);
			}
			else {
				startIndex = -1;
			}
		}
		return result.toString();
	}
```

