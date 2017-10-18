package servlet;
/*
    created by xdCao on 2017/10/18
*/

import entity.Data;
import entity.Handler;
import entity.Param;
import entity.View;
import helpers.BeanHelper;
import helpers.ConfigHelper;
import helpers.ControllerHelper;
import helpers.HelperLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.lf5.util.StreamUtils;
import utils.*;

import javax.imageio.spi.ServiceRegistry;
import javax.naming.event.ObjectChangeListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();
        ServletContext context=config.getServletContext();
        ServletRegistration jspServlet=context.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
        ServletRegistration defaultServlet=context.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod=req.getMethod();
        String requestPath=req.getPathInfo();
        Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
        if (handler!=null){
            Class<?> controllerClass=handler.getControllerClass();
            Object controllerBean= BeanHelper.getBean(controllerClass);
            Map<String,Object> paramMap=new HashMap<String,Object>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                String paramValue=req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body= CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)){
                String[] params=StringUtils.split(body,"&");
                if (ArrayUtil.isNotEmpty(params)){
                    for (String param:params){
                        String[] array=StringUtils.split(param,"=");
                        if (ArrayUtil.isNotEmpty(array)&&array.length==2){
                            String paramName=array[0];
                            String paramValue=array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            Param param=new Param(paramMap);
            Method actionMethod=handler.getActionMethod();
            Object result= ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            if (result instanceof View){
                View view=(View)result;
                String path=view.getPath();
                if (StringUtils.isNotEmpty(path)){
                    if (path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    }else {
                        Map<String,Object> model=view.getModel();
                        for (Map.Entry<String,Object> entry:model.entrySet()){
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                    }
                }
            }else if (result instanceof Data){
                Data data=(Data)result;
                Object model=data.getModel();
                if (model!=null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("utf-8");
                    PrintWriter writer=resp.getWriter();
                    String json= JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
