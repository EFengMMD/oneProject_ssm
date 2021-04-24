package com.efeng.ssm.listener;

import com.efeng.ssm.domain.DicValue;
import com.efeng.ssm.service.DicValueService;
import com.efeng.utils.ServiceFactory;
import com.efeng.utils.SpringContextHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

@Component
public class SysInitListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DicValueService dicValueService = SpringContextHelper.getBean(DicValueService.class);
        ServletContext application = servletContextEvent.getServletContext();
        Map<String, List<DicValue>> valueMap = dicValueService.getDicValueList();
        Set<String> keySet = valueMap.keySet();
        for (String key : keySet){
            application.setAttribute(key, valueMap.get(key));
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("conf/Stage2Possibility");
        Map<String, String> pMap = new HashMap<>();
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while(enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = resourceBundle.getString(key);
            pMap.put(key, value);
        }
        application.setAttribute("pMap", pMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
