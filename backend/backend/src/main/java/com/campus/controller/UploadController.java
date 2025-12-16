package com.campus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID; // 用于生成唯一文件名

@RestController
@RequestMapping("/api/upload")
public class UploadController {


    private static final String UPLOAD_PATH = "D:/A study/项目/Campus-Secondhand-Platform/campus_upload_images";

    /**
     * API: 图片上传接口
     * 路径: POST /api/upload/image
     * @param file 前端传入的图片文件
     * @return 包含文件URL或错误信息的 Map
     */
    @PostMapping("/image")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "上传文件不能为空");
            return result;
        }

        try {
            // 1. 获取原始文件名和后缀
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 2. 生成新的唯一文件名 (防止文件冲突)
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // 3. 创建目标文件路径
            File dest = new File(UPLOAD_PATH + newFilename);

            // 4. 确保目录存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            // 5. 将文件写入磁盘
            file.transferTo(dest);

            // 6. 返回文件URL (这个URL需要映射到Tomcat才能被访问)
            String fileUrl = "/images/" + newFilename; // 假设通过Tomcat的虚拟路径映射访问

            result.put("success", true);
            result.put("message", "文件上传成功");
            result.put("data", fileUrl); // 返回给前端的图片访问路径

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "文件写入失败，请检查服务器目录权限和路径配置");
        }

        return result;
    }
}