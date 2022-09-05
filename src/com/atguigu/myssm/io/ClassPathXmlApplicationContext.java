package com.atguigu.myssm.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QKC
 * @create 2022-08-08-17:22
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    private Map<String,Object> beanMap = new HashMap<>();

    public ClassPathXmlApplicationContext(){
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建 DocumentBuilderFactory对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建 DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建 Document对象
            Document document = documentBuilder.parse(in);

            //4.获取所有bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i=0; i< beanNodeList.getLength(); i++){
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){ //如果这是一个元素节点
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class beanClass = Class.forName(className);
                    //创建bean对象实例
                    Object beanObj = beanClass.newInstance();
                    //将实例对象保存到容器中
                    beanMap.put(beanId,beanObj);
                    //bean和bean之间的依赖关系还没设置
                }
            }
            //5.组装bean之间的依赖关系
            for (int i=0; i< beanNodeList.getLength(); i++){
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");

                    NodeList beanChildNodeList = beanElement.getChildNodes();//获取这个元素节点的所有子节点
                    for (int j=0; j<beanChildNodeList.getLength(); j++){
                        Node beanChildNode = beanChildNodeList.item(j);
                        if (beanChildNode.getNodeType()==Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())){//这个子节点是元素节点，且叫property
                            Element propertyElement = (Element) beanChildNode;
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            //1.找到ref对应的实例
                            Object refObj = beanMap.get(propertyRef);//子节点的ref对应着父节点的id (Map的key)
                            //2.将refObj设置到当前bean对应的class实例的property属性上
                            Object beanObj = beanMap.get(beanId);
                            Class beanClazz = beanObj.getClass(); // beanId -> beanObj -> beanClazz
                            Field propertyField = beanClazz.getDeclaredField(propertyName); //获取到父节点class对象的子节点propertyName属性
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj,refObj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }

}


























