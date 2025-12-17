<template>
  <div class="register-wrapper">
    <el-card class="register-box">
      <h2 class="title">校园二手平台 - 用户注册</h2>
      
      <el-form :model="registerForm" ref="registerRef" :rules="rules" label-width="0px">
        <el-form-item prop="username">
          <el-input 
            v-model="registerForm.username" 
            prefix-icon="User" 
            placeholder="请输入用户名">
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input 
            v-model="registerForm.password" 
            type="password" 
            prefix-icon="Lock" 
            placeholder="请输入密码" 
            show-password>
          </el-input>
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="registerForm.confirmPassword" 
            type="password" 
            prefix-icon="Lock" 
            placeholder="请再次输入密码" 
            show-password>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" class="login-btn" @click="handleRegister">注 册</el-button>
        </el-form-item>

        <div class="footer-links">
          <span>已有账号？</span>
          <router-link to="/login">立即登录</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/user' // 💡 确认你的 api/user.js 中有 register 方法
import { ElMessage } from 'element-plus'
import { User, Lock } from '@