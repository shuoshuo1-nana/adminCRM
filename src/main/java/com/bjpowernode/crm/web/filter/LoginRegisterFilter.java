package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 王硕
 * 2019/10/21
 */
public class LoginRegisterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("过滤登录");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path) || "/login.jsp".equals(path)) {
            chain.doFilter(req, resp);
            return;
        }
        if (user != null) {
            chain.doFilter(req, resp);

        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
