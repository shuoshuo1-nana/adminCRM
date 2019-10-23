package com.bjpowernode.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Author: 王硕
 * 2019/10/21
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到用来过滤字符编码的过滤器");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        chain.doFilter(req, resp);
    }
}
