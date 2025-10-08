<template>
  <div class="upcoming-trip-card">
    <div class="trip-card-content">
      <div class="property-image">
        <img :src="trip.propertyImage" alt="Property" />
      </div>
      <div class="trip-details">
        <div class="trip-info">
          <h3 class="property-name">{{ trip.propertyName }}</h3>
          <p class="trip-dates">{{ trip.dates }}</p>
          <p class="host-info">Hosted by {{ trip.hostName }}</p>
        </div>
        <div class="trip-status">
          <span :class="['status-badge', trip.status]">{{ trip.status }}</span>
          <div class="trip-actions">
            <button class="action-btn">View Details</button>
            <button class="action-btn">Message Host</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const trip = ref({
  propertyImage: '/property-placeholder.jpg',
  propertyName: 'Beautiful Beach House',
  dates: 'Oct 15 - Oct 22, 2024',
  hostName: 'Sarah Johnson',
  status: 'confirmed'
})
</script>

<style scoped>
.upcoming-trip-card {
  background: white;
  border: 1px solid #e5e5e5;
  border-radius: 12px;
  margin-bottom: 1.5rem;
  overflow: hidden;
}

.trip-card-content {
  display: flex;
}

.property-image {
  width: 300px;
  height: 200px;
}

.property-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.trip-details {
  flex: 1;
  padding: 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.property-name {
  margin: 0 0 0.5rem 0;
  font-size: 1.25rem;
  font-weight: 600;
}

.trip-dates, .host-info {
  margin: 0.25rem 0;
  color: #666;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.confirmed {
  background: #d4edda;
  color: #155724;
}

.trip-actions {
  display: flex;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.action-btn {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  font-size: 14px;
}

.action-btn:hover {
  background: #f5f5f5;
}
</style>