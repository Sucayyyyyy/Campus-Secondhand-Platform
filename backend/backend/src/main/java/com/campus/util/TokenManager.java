package com.campus.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class TokenManager {

    // 设置 Token 的有效期：2 小时 (毫秒)
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    // --- 1. 定义存储结构 ---
    private static class TokenData {
        public final Integer userId;
        public final long expiryTime; // 存储 Token 的过期时间戳 (毫秒)

        public TokenData(Integer userId, long expiryTime) {
            this.userId = userId;
            this.expiryTime = expiryTime;
        }
    }

    // --- 2. 统一存储 Map 定义 ---
    // 线程安全的 Map 存储 Token -> TokenData
    private static final Map<String, TokenData> tokenStore = new ConcurrentHashMap<>();

    /**
     * 创建并存储 Token
     * @param userId 登录成功的用户ID
     * @return 生成的唯一 Token 字符串
     */
    public static String createToken(Integer userId) {
        String token = UUID.randomUUID().toString().replace("-", "");

        // --- 3. 修正 put 逻辑，存储 TokenData 对象 ---
        // 计算过期时间：当前时间 + 有效期
        long expiryTime = System.currentTimeMillis() + EXPIRATION_TIME;

        // 存储 TokenData 对象
        tokenStore.put(token, new TokenData(userId, expiryTime));

        return token;
    }

    /**
     * 根据 Token 获取用户ID
     * @param token 前端传来的 Token
     * @return 用户ID，如果 Token 无效或过期则返回 null
     */
    public static Integer getUserId(String token) {
        if (token == null) {
            return null;
        }

        TokenData data = tokenStore.get(token);

        if (data == null) {
            return null; // Token 不存在
        }

        // 检查是否过期
        if (data.expiryTime < System.currentTimeMillis()) {
            // 已经过期，从 Map 中移除并返回 null
            tokenStore.remove(token);
            return null;
        }

        // Token 有效，返回用户ID
        return data.userId;
    }

    /**
     * 用户登出时，移除 Token
     */
    public static void removeToken(String token) {
        tokenStore.remove(token);
    }
}