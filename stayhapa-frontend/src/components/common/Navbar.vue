<template>
  <nav class="navbar">
    <div class="navbar-container">
      <div class="navbar-brand">
        <h2>YourLogo</h2>
      </div>
      <div class="navbar-search">
        <SearchBar />
      </div>
      <div class="navbar-menu">
        <a href="#" class="nav-item">Home</a>
        <a href="#" class="nav-item">Messages</a>
        <a href="#" class="nav-item">Notifications</a>
        <div class="user-avatar">
          <img src="/default-avatar.jpg" alt="User" />
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import SearchBar from './SearchBar.vue'
</script>

<style scoped>
.navbar {
  background: white;
  border-bottom: 1px solid #e5e5e5;
  padding: 1rem 0;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1rem;
}

.navbar-brand h2 {
  margin: 0;
  color: #ff385c;
}

.navbar-menu {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.nav-item {
  text-decoration: none;
  color: #333;
  font-weight: 500;
}

.user-avatar img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}
</style>