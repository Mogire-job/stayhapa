<template>
  <div class="quick-stats">
    <div class="stats-container">
      <div class="stat-card">
        <h3 class="stat-number">{{ stats.bookings }}</h3>
        <p class="stat-label">Bookings</p>
      </div>
      <div class="stat-card">
        <h3 class="stat-number">{{ stats.occupancy }}%</h3>
        <p class="stat-label">Occupancy</p>
      </div>
      <div class="stat-card">
        <h3 class="stat-number">{{ stats.rating }}</h3>
        <p class="stat-label">Rating</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const stats = ref({
  bookings: 12,
  occupancy: 78,
  rating: 4.9
})
</script>

<style scoped>
.quick-stats {
  background: white;
  border-bottom: 1px solid #e5e5e5;
  padding: 1.5rem 0;
}

.stats-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  gap: 2rem;
}

.stat-card {
  text-align: center;
  padding: 1rem;
  border: 1px solid #e5e5e5;
  border-radius: 8px;
  min-width: 120px;
}

.stat-number {
  margin: 0 0 0.5rem 0;
  font-size: 1.5rem;
  font-weight: 600;
  color: #000;
}

.stat-label {
  margin: 0;
  color: #666;
  font-size: 14px;
}
</style>