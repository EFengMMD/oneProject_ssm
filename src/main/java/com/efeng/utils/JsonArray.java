package com.efeng.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Json转换测试类
 * @author 胡汉三
 *
 */
@SuppressWarnings({"unchecked","unused"})
public class JsonArray {

    /**
     * 根据List集合生成Json数组格式字符串(只支持bean方式)
     * @param <T>
     * @param list 集合对象
     * @return
     */
    public static <T> String ToJsonByList(List<T> list){
        StringBuffer b = new StringBuffer("[");
        if(list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++){
                Object o = list.get(i);
                try {
                    //调用ToJsonByBean方法
                    StringBuffer s = ToJsonByBean(o);
                    if(s!=null&&!s.equals("")){
                        b.append(s).append(",");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        b.append("]");
        return b.replace(b.lastIndexOf(","),b.lastIndexOf(",") + 1 ,"").toString();
    }

    /**
     * 根据javaBean生成Json对象格式字符串
     * @param object 任意javaBean类型对象
     * @return 拼接好的StringBuffer对象
     */
    public static StringBuffer ToJsonByBean(Object object) throws Exception{
        Class clazz = object.getClass();  //获得Class对象
        Field[] fields = clazz.getDeclaredFields();  //获得Class对象的字段数组
        StringBuffer sb = new StringBuffer("{");
        /*
         * 循环字段数组
         */
        for (Field field : fields) {
            String fieldName = field.getName(); //获得字段名称
            //获得字段对应的get方法对象
            Method method = clazz.getMethod("get" + change(fieldName), null);
            //调用get方法获得字段的值
            Object property = method.invoke(object, null);

            /*
             * 生成json字符串数据
             */
            if(property == null){
                sb.append("\""+fieldName+"\":\"\",");
            }else{
                if (field.getType().getName().equals("java.lang.Boolean"))
                    sb.append("\""+fieldName+"\":"+Boolean.valueOf(property.toString())+",");
                else
                    sb.append("\""+fieldName+"\":\""+property+"\",");
            }
        }
        sb.append("}");
        return sb.replace(sb.lastIndexOf(","),sb.lastIndexOf(",") + 1 ,"");
    }

    /**
     * @param src 源字符串
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
     */
    public static String change(String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * 生成Json数组格式字符串
     * @param object 任意类型对象
     * @return 拼接好的StringBuffer对象
     */
    public static StringBuffer ToJsonByAll(Object object) throws Exception{
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sb = new StringBuffer("{[");
        for (Field field : fields) {
            String fieldName = field.getName();
            Method method = clazz.getMethod("get" + change(fieldName), null);
            Object property = method.invoke(object, null);
            if(property == null){
                sb.append("\"\",");
            }else{
                sb.append("\"" + property + "\",");
            }
        }
        sb.append("]}");
        return sb.replace(sb.lastIndexOf(","),sb.lastIndexOf(",") + 1 ,"");
    }


}