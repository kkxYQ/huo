package com.seven.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ Description
 * @ Date           2019-06-10 19:00
 */
public class BeanUtil {

    public static <T,E> List<T> convertListBean(Class<T> clazz ,List<E> beans ){
        List<T> result = new ArrayList<>();
        try {
            for(E bean:beans){
                result.add(convertBean(bean,clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对象转换
     * @param orimodel
     * @param castClass
     * @param <E>
     * @param <T>
     * @return
     */
    public static  <E,T> T convertBean(E orimodel, Class<T> castClass) {
        T returnModel = null;
        try {
            returnModel = castClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建"+castClass.getName()+"对象失败");
        }
        List<Field> fieldlist = new ArrayList<Field>(); //要转换的字段集合
        while (castClass != null && //循环获取要转换的字段,包括父类的字段
                !castClass.getName().toLowerCase().equals("java.lang.object")) {
            fieldlist.addAll(Arrays.asList(castClass.getDeclaredFields()));
            castClass = (Class<T>) castClass.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field : fieldlist) {
            PropertyDescriptor getpd = null;
            PropertyDescriptor setpd = null;
            try {
                getpd= new PropertyDescriptor(field.getName(), orimodel.getClass());
                setpd=new PropertyDescriptor(field.getName(), returnModel.getClass());
            } catch (Exception e) {
                continue;
            }
            try {
                Method getMethod = getpd.getReadMethod();
                Object transValue = getMethod.invoke(orimodel);
                Method setMethod = setpd.getWriteMethod();
                setMethod.invoke(returnModel, transValue);
            } catch (Exception e) {
                throw  new RuntimeException("cast "+orimodel.getClass().getName()+"to "
                        +castClass.getName()+" failed");
            }
        }
        return returnModel;
    }


    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        try{
            Map<String, Object> map = new HashMap<String,Object>();
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
            return map;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(500,"服务器内部错误");
        }
    }


}
