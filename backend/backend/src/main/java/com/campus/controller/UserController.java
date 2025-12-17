package com.campus.controller;

import com.campus.model.User;
import com.campus.service.ProductService;
import com.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/user") // 用户相关的接口都放在 /api/user 下
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    /**
     * API: 用户注册接口
     * 路径: POST /api/user/register
     * 接收: JSON 格式的 User 对象（包含 username 和 password）
     *
     * @param user 包含注册信息的 User 对象
     * @return 注册结果的 JSON 响应
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 简单的参数校验
        if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            result.put("success", false);
            result.put("message", "用户名或密码不能为空");
            return result;
        }

        // 2. 直接调用 Service 层新方法，并返回其结果
        return userService.register(user);
    }
        /**
         * 用户登录接口
         * URL: POST /api/user/login
         */
        @PostMapping("/login")
        public Map<String, Object> login (@RequestBody User user){
            // 基础参数校验
            if (user.getUsername() == null || user.getPassword() == null) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", false);
                result.put("msg", "用户名或密码不能为空。");
                return result;
            }

            return userService.login(user);
        }

    /**
     * API: 获取当前用户详情
     * 路径: GET /api/user/info
     * * @return 包含用户信息的 Map
     */
    @GetMapping("/info")
    public Map<String, Object> getUserInfo() {
        Integer currentUserId = 1;

        return userService.getUserInfo(currentUserId);
    }

    /**
     * API: 获取当前用户发布的商品列表
     * 路径: GET /api/user/products?pageNum=1&pageSize=10
     * @return 包含用户发布的商品列表及分页信息的 Map
     */
    @GetMapping("/products")
    public Map<String, Object> getUserProducts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {

        Integer currentUserId = 1;

        return productService.getProductsBySellerId(currentUserId, pageNum, pageSize, keyword);
    }
    }
