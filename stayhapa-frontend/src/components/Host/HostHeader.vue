<template>
  <div class="host-header">
    <div class="header-container">
      <div class="avatar-section">
        <img :src="host.avatar" alt="Host Avatar" class="avatar" />
        <div class="host-info">
          <h1 class="welcome-message">Welcome back, {{ host.name }}!</h1>
          <p class="earnings">${{ host.earnings.toLocaleString() }} earned</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const host = ref({
  name: 'Sarah',
  avatar: '/host-avatar.jpg',
  earnings: 2450
})
</script>

<style scoped>
.host-header {
  background: white;
  border-bottom: 1px solid #e5e5e5;
  padding: 2rem 0;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.avatar-section {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
}

.welcome-message {
  margin: 0 0 0.5rem 0;
  font-size: 1.5rem;
  font-weight: 600;
}

.earnings {
  margin: 0;
  color: #00a699;
  font-size: 1.25rem;
  font-weight: 500;
}
</style>