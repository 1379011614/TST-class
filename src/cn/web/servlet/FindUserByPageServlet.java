package cn.web.servlet;

import cn.domain.PageBean;
import cn.domain.User;
import cn.service.UserService;
import cn.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //接受请求参数currentPage , rows
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)){
            rows = "10";
        }
        //获取条件查询参数
        Map<String, String[]> condition = request.getParameterMap();
        //调用Service查询PageBean
        UserService service = new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(currentPage,rows,condition);
        //将PageBean存入request
        request.setAttribute("pb",pb);
        //将查询条件存入request
        request.setAttribute("condition" ,condition);
//        System.out.println(pb);
        //转发list.jsp展示
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
