<template>
  <div class="user-profile-header">
    <div class="profile-container">
      <div class="avatar-section">
        <img :src="user.avatar" alt="User Avatar" class="avatar" />
        <div class="user-info">
          <h1 class="user-name">{{ user.name }}</h1>
          <div class="verification-badge">
            <span class="verified">Verified ✓</span>
            <button class="edit-profile-btn">Edit Profile</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const user = ref({
  name: 'John Doe',
  avatar: '/default-avatar.jpg',
  isVerified: true
})
</script>

<style scoped>
.user-profile-header {
  background: white;
  border-bottom: 1px solid #e5e5e5;
  padding: 2rem 0;
}

.profile-container {
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

.user-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.user-name {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 600;
}

.verification-badge {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.verified {
  color: #00a699;
  font-weight: 500;
}

.edit-profile-btn {
  background: none;
  border: 1px solid #ddd;
  padding: 0.25rem 0.75rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}
</style>