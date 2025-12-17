package com.campus.interceptor;

import com.campus.util.TokenManager;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 1. 【已有】放行 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            return true;
        }

        // 2. 【已有】排除白名单路径
        String path = request.getRequestURI();
        if (path.contains("/login") || path.contains("/register") ||
                path.contains("/product/list") || path.matches("/api/product/\\d+") ||
                path.contains("/upload/image")) {
            return true;
        }

        // 3. 【核心修正】：正确提取 Token
        String token = request.getHeader("Authorization");

        // 如果 Token 以 "Bearer " 开头，截取后面的真实 Token 部分
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 这 7 个字符
        }

        // 4. 验证 Token (现在传入的是纯粹的 UUID 字符串了)
        Integer userId = TokenManager.getUserId(token);

        if (userId != null) {
            // 5. 验证成功
            request.setAttribute("currentUserId", userId);
            return true;
        } else {
            // 6. 验证失败
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":false, \"message\":\"未登录或Token无效\"}");
            return false;
        }
    }
}