<template>
  <div class="search-bar">
    <div class="search-input-container">
      <input 
        type="text" 
        placeholder="Search..." 
        class="search-input"
      />
      <button class="search-button">
        🔍
      </button>
    </div>
  </div>
</template>

<script setup>
// Search functionality can be added here
</script>

<style scoped>
.search-bar {
  width: 400px;
}

.search-input-container {
  position: relative;
  display: flex;
}

.search-input {
  width: 100%;
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 24px;
  font-size: 14px;
}

.search-button {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
}
</style>