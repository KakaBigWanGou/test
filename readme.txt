review:
1.  Servlet生命周期中的初始化方法：init() , init(config)
    public void init(ServletConfig config){
        this.config = config;
        init();
    }
    因此，如果我们需要在初始化时执行一些自定义的操作，那么我们可以重写无参的init方法。
    可以通过getConfig()获取ServletConfig对象
    可以通过config.getInitParameter()获取初始化参数

2.  通过ServletContext获取配置的上下文参数

3.  MVC： M:model模型；V:view视图；C:controller控制器
    模型有很多种类：数据访问模型(DAO); 业务逻辑模型(BO); 值对象模型(POJO); 数据传输对象(DTO)

4.  IOC
     IOC -控制反转  /  DI -依赖注入
     控制反转:
     1) 之前在Servlet中，我们创建service对象，FruitService fruitService = new FruitServiceImpl();
        这句话如果出现在servlet中的某个方法内部，那么这个fruitService的作用域(生命周期)应该就是这个方法级别
             如果是在servlet类中的一个成员变量，那么作用域就是这个servlet实例级别
     2) 之后我们在applicationContext.xml中定义了这个fruitService。然后通过解析XML，产生fruitService对象，存放在beanMap中，这个beanMap在一个BeanFactory中
        因此，我们转移了之前的service实例、dao实例等等的生命周期。控制权从程序员转移到BeanFactory。(控制反转)
     依赖注入:
     1) 之前我们在控制层出现代码: FruitService fruitService = new FruitServiceImpl();
        那么，控制层和service层存在耦合。
     2) 之后，我们将代码修改成FruitService fruitservice = null;
        然后，在配置文件中配置:  <bean id="fruit" class="FruitController">
                                   <property name="fruitService" ref="fruitService"/>
                               </bean>




1. 过滤器
1) Filter也属于Servlet规范
2) Filter开发步骤：新建类实现Filter接口，然后实现其中的三个方法：init、doFilter、destory
3) Filter在配置时，和servlet一样，也可以配置通配符，例如 @WebFilter("*.do"),表示拦截所有.do请求
4) 过滤器链
    如果用注解的方式配置，拦截顺序为全类名的排序
    如果用xml配置，按照配置的先后顺序排序

2. 事务管理
    1) 涉及到的组件:
      - OpenSessionInViewFilter
      - TransactionManager
      - ThreadLocal
      - ConnUtil
      - BaseDAO

3. 监视器
    1) ServletContextListener   -监听ServletContext对象的创建和销毁的过程
    2) HttpSessionListener  -监听HttpSession对象的创建和销毁的过程
    3) ServletRequestListener   -监听ServletRequest对象的创建和销毁的过程

    4) ServletContextAttributeListener   -监听ServletContext的保存作用域的改动(add,remove,replace)
    5) HttpSessionAttributeListener   -监听HttpSession的保存作用域的改动
    6) ServletRequestAttributeListener   -监听ServletRequestt的保存作用域的改动

    7) HttpSessionBindingListener   -监听某个对象在Sessino域中的创建与移除
    8) HttpSessionActivationListener   -监听某个对象在Sessino域中的序列化和反序列化
