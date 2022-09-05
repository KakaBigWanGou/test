package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.io.BeanFactory;
import com.atguigu.myssm.io.ClassPathXmlApplicationContext;
import com.atguigu.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/** 中央控制器
 *  从url中获取servletPath，在servletPath字符串中提取出名字        url  ->  /Xxx.do  ->  Xxx
 *  加载配置文件xml，读取出bean，存入map容器
 *  根据名字在map中找出对应的处理类，调用operate对应的方法            Xxx  ->   XxxController
 *
 * @author QKC
 * @create 2022-08-06-14:58
 */

@WebServlet("*.do")   //*是通配符，拦截所有以.do结尾的请求
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    public DispatcherServlet(){
    }

    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();  // 得到url中的/Xxx.do
        // 从/Xxx.do中获取Xxx
        servletPath = servletPath.substring(1,servletPath.lastIndexOf(".do"));

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = request.getParameter("operate");
        if (StringUtils.isEmpty(operate)){
            operate = "index";
        }

        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method:methods){
                if (operate.equals(method.getName())){
                //1.统一获取请求参数
                    //获取当前方法的形参列表，返回参数数组
                    Parameter[] parameters = method.getParameters();
                    //parameterValues用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for(int i=0; i<parameters.length; i++){
                        Parameter parameter = parameters[i];
                        String paremeterName = parameter.getName();
                            //处理参数中的request,response,session
                        if ("request".equals(paremeterName)){
                            parameterValues[i] = request;
                        } else if ("response".equals(paremeterName)){
                            parameterValues[i] = response;
                        } else if ("session".equals(paremeterName)) {
                            parameterValues[i] = request.getSession();
                        } else {
                            //从请求中获取参数值
                            String parameterValue = request.getParameter(paremeterName);  //获取到的是字符串
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;
                            if (parameterObj!=null){
                                if ( "java.lang.Integer".equals(typeName) ) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }

                    //2.controller组件中的方法调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj,parameterValues);

                    //3.视图处理
                    String methodReturnStr = (String) returnObj;
                    if (methodReturnStr.startsWith("redirect:")){    //比如："redirect:fruit.do"
                        String redirectStr = methodReturnStr.substring("redirect:".length()); //取出后面的内容
                        response.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr,request,response);    //比如："edit"
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("这里报错");
            e.printStackTrace();
        }

    }
}






















