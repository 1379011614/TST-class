package cn.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Key;
import java.util.*;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {

    private List<String> list = new ArrayList<String>();//敏感词汇集合

    public void init(FilterConfig config) throws ServletException {
        try {
            //加载敏感词汇文件
            //加载文件真实路径
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/resources/sensitiveWords.txt");
            //读取文件
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            //将文件添加到list集合中
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //创建代理对象,增强getParameter()方法
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParameter()方法
                //判断是否为getParameter()
                if (method.getName().equals("getParameter")) {
                    //增强返回值
                    //获取返回值
                    String value = (String) method.invoke(req, args);
                    if (value != null) {
                        for (String str : list) {
                            if (value.contains(str)) {
                                value = value.replaceAll(str, "***");
                            }
                        }
                    }
                    return value;
                }
                //判断方法名是否是getParameterMap
                if (method.getName().equals("getParameterMap")){
                    Map<String, String[]> map=(Map<String, String[]>) method.invoke(req, args);
                    Map<String,String[]> newMap=new HashMap<>();
                    for (String key : map.keySet()) {
                        String[] values = map.get(key);
                        List<String> sb=new ArrayList<>();
                        for (String value : values) {
                            if (value!=null){
                                for (String s : list) {
                                    if (value.contains(s)){
                                        value=value.replaceAll(s, "***");
                                    }
                                }
                                sb.add(value);
                            }
                        }
                        String[] sBuffer=new String[values.length];
                        newMap.put(key, (String[]) sb.toArray(sBuffer));
                    }
                    return newMap;
                }
                //判断方法名是否是getParameterValues
                if (method.getName().equals("getParameterValues")){
                    String[] values=(String[])method.invoke(req, args);
                    List<String> newlist=new ArrayList<>();
                    if (values!=null){
                        for (String value : values) {
                            if (value!=null){
                                for (String s : list) {
                                    if (value.contains(s)) {
                                        value = value.replaceAll(s, "***");
                                    }
                                }
                            }
                            newlist.add(value);
                        }

                    }
                    String[] sBuffer=new String[newlist.size()];
                    String[] newStr=newlist.toArray(sBuffer);//这里要这样写，因为toArray在转型的时候要加参数，长度就是你要转型的类型长度
                    return newStr;
                }
                return method.invoke(req, args);
            }
        });
        //放行
        chain.doFilter(proxy_req, resp);
    }

    public void destroy() {

    }

}
