package com.stylefeng.guns.core.util;
import java.lang.reflect.Field;

import com.stylefeng.guns.modular.air.model.AirStationData;
 
class Student {//学生类
    private String name;// 姓名
    private Integer age;// 年龄
    private String xq;// 兴趣
 
    public Student() {
    }
 
    public Student(String name, Integer age, String xq) {
        this.name = name;
        this.age = age;
        this.xq = xq;
    }
 
}
 
class Cat{//猫类
    String name;
    Integer age;
    public Cat() {
         
    }
    public Cat(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
     
}
 
public class MyTest {//测试类
 
    public static void main(String[] args) throws Exception {
         
        Student stu1 = new Student();
        if(isAllFieldNull(stu1)) {
            System.out.println("stu1对象所有的属性为null");
        }
         
         
        Student stu2 = new Student("张三",12,null);//只有兴趣属性为null
        if(isAllFieldNull(stu2)) {
            System.out.println("stu2对象所有的属性为null");
        }
         
         
        Cat cat = new Cat();
        if(isAllFieldNull(cat)) {
            System.out.println("cat对象所有的属性为null");
        }
        
        
        System.out.println(isAllFieldNull(new AirStationData()));
    }
     
    //判断该对象是否
    public static boolean isAllFieldNull(Object obj) throws Exception{
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            if("serialVersionUID".equals(f.getName())){
            	continue;
            }
            
            Object val = f.get(obj);// 得到此属性的值
            if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
        }
        return flag;
    }
 
}