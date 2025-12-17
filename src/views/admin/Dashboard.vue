<template>
  <div class="dashboard-content">
    <el-card class="welcome-card" shadow="hover">
      <div class="welcome-box">
        <el-avatar :size="60" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        <div class="welcome-text">
          <h2>欢迎回来，超级管理员！</h2>
          <p>今天是交易活跃的一天，快来看看今天的经营数据吧。</p>
        </div>
      </div>
    </el-card>

    <el-row :gutter="20" class="stat-row">
      <el-col :span="6" v-for="item in stats" :key="item.title">
        <el-card shadow="always" class="stat-card" :style="{ borderTop: '4px solid ' + item.color }">
          <div class="stat-title">{{ item.title }}</div>
          <div class="stat-value">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never">
      <el-empty description="系统运行良好，暂无预警通知" />
    </el-card>
  </div>
</template>

<script setup>
const stats = [
  { title: '今日发布', value: '12 件', color: '#409EFF' },
  { title: '待审核', value: '3 件', color: '#67C23A' },
  { title: '活跃用户', value: '156 人', color: '#E6A23C' },
  { title: '总成交额', value: '￥2,450', color: '#F56C6C' }
];
</script>

<style scoped>
.welcome-card { margin-bottom: 20px; border-radius: 12px; }
.welcome-box { display: flex; align-items: center; gap: 20px; }
.welcome-text h2 { margin: 0 0 8px 0; color: #303133; }
.welcome-text p { margin: 0; color: #909399; font-size: 14px; }
.stat-row { margin-bottom: 20px; }
.stat-title { color: #909399; font-size: 14px; margin-bottom: 10px; }
.stat-value { font-size: 24px; font-weight: bold; color: #303133; }
</style>