package cn.web.servlet;

import cn.domain.User;
import cn.service.UserService;
import cn.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取数据
        //2.1获取用户填写验证码
        String verifycode = request.getParameter("verifycode");
        //3.验证码校验
        String checkCode = (String) request.getSession().getAttribute("checkCode_server");
        request.getSession().removeAttribute("checkCode_server");//确保验证码的一次性
        if (!verifycode.equalsIgnoreCase(checkCode)){
            //验证码不正确
            //给出提示信息
            request.setAttribute("login_msg","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        //4.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //5.调用service查询
        UserService userService = new UserServiceImpl();
        User loginUser = userService.login(user);
        //6.判断是否登录成功
        if (loginUser != null) {
            //登陆成功
            //将信息存入session
            request.getSession().setAttribute("user",loginUser);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else {
            //登录失败
            //给出提示
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
