import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const service = axios.create({
  // 【重要】这是你的后端接口地址
  baseURL: 'http://localhost:8080/api', 
  timeout: 5000 // 请求超时时间
})

// 请求拦截器：每次发请求前都会执行
service.interceptors.request.use(
  config => {
    // 如果本地有 token，就带在请求头里
    const token = localStorage.getItem('campus_user_token')
    if (token) {
      config.headers['Authorization'] = token // 或者根据后端要求写 `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器：后端返回数据后，先经过这里
service.interceptors.response.use(
  response => {
    // 这里的 response.data 是后端返回的标准 JSON
    return response.data
  },
  error => {
    // 统一处理报错
    ElMessage.error(error.message || '网络请求失败')
    return Promise.reject(error)
  }
)

export default service