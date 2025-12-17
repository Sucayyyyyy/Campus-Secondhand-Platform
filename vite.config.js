import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  // 💡 新增以下代理配置
  server: {
    host: '0.0.0.0', // 允许局域网访问
    port: 5173,      // 前端运行端口
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 👈 指向你的 Tomcat 后端地址
        changeOrigin: true,              // 允许跨域
        // 如果你的后端接口路径里本身就带有 /api，就不需要 rewrite
        // 如果后端路径里没有 /api，则需要加上：rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})