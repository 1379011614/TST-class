package cn.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //ServletRequest没有对应的getRequestURI方法,需要强制转化成ServletRequest的子类HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) req;
        //获取资源请求路径
        String uri = request.getRequestURI();
        //判断是否包含登录相关的资源路径,要注意排除掉css,js,验证码等相关的资源
        if (uri.contains("/login.jsp")||uri.contains("/loginServlet")||uri.contains("/css/")
                ||uri.contains("/js/")||uri.contains("/checkCodeServlet")||uri.contains("/fonts/")){
            //包含,放行
            chain.doFilter(req,resp);
        }else {
            //不包含,需要验证用户是否登录
            //从session中获取登录信息user
            Object user = request.getSession().getAttribute("user");
            if (user != null) {
                //已经登录,放行
                chain.doFilter(req,resp);
            }else {
                //没有登录,转发到登录界面
                request.setAttribute("login_msg","您还未登录,请先登录!");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }
//        chain.doFilter(req, resp);
    }
    public void destroy() {

    }

}
