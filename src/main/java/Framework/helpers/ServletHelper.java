package Framework.helpers;
/*
    created by xdCao on 2017/10/19
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class ServletHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(ServletHelper.class);

    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public static void init(HttpServletRequest request,HttpServletResponse response){
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request,response));
    }

    public static void destroy(){
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }

    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }

    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }

    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    public static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    public static void setReqAttribute(String key,Object value){
        getRequest().setAttribute(key,value);
    }

    public <T> T getReqAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    public static void removeReqAttribute(String key){
        getRequest().removeAttribute(key);
    }

    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath()+location);
        }catch (Exception e){
            LOGGER.error("重定向失败！！",e);
        }
    }

    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key, value);
    }

    public static <T> T getSessionAttribute(String key){
        return (T) getSession().getAttribute(key);
    }

    public static void removeSessionAttribute(String key){
        getSession().removeAttribute(key);
    }

    public static void invalidateSession(){
        getSession().invalidate();
    }

}
