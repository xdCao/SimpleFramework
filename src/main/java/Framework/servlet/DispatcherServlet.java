package Framework.servlet;
/*
    created by xdCao on 2017/10/18
*/

import Framework.entity.Data;
import Framework.entity.Handler;
import Framework.entity.Param;
import Framework.entity.View;
import Framework.helpers.*;
import org.apache.commons.lang3.StringUtils;
import Framework.utils.*;

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
        UploadHelper.init(context);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletHelper.init(req, resp);

        try {
            String requestMethod=req.getMethod().toLowerCase();
            String requestPath=req.getPathInfo();
            if (requestPath.equalsIgnoreCase("/favicon.ico")) {
                return;
            }
            Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
            if (handler!=null){
                Class<?> controllerClass=handler.getControllerClass();
                Object controllerBean= BeanHelper.getBean(controllerClass);

                Param param;

                if (UploadHelper.isMultipart(req)){
                    param=UploadHelper.createParam(req);
                }else {
                    param=RequestHelper.createParams(req);
                }

                Method actionMethod=handler.getActionMethod();
                Object result;
                if (param.isEmpty()){
                    result=ReflectionUtil.invokeMethod(controllerBean,actionMethod);
                }else {
                    result= ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
                }
                if (result instanceof View){
                    handleViewResult(req, resp, (View) result);
                }else if (result instanceof Data){
                    handleDataResult(resp, (Data) result);
                }
            }
        }finally {
            ServletHelper.destroy();
        }
    }

    private void handleDataResult(HttpServletResponse resp, Data result) throws IOException {
        Data data= result;
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

    private void handleViewResult(HttpServletRequest req, HttpServletResponse resp, View result) throws IOException, ServletException {
        View view= result;
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
    }
}
