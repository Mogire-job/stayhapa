<template>
  <div class="listing-card">
    <div class="listing-content">
      <div class="listing-image">
        <img :src="listing.image" alt="Listing" />
      </div>
      <div class="listing-details">
        <h3 class="listing-title">{{ listing.title }}</h3>
        <p class="listing-location">{{ listing.location }}</p>
        <div class="listing-stats">
          <span class="stat">{{ listing.price }}/night</span>
          <span class="stat">{{ listing.rating }} ★</span>
          <span class="stat">{{ listing.bookings }} bookings</span>
        </div>
      </div>
      <div class="listing-actions">
        <button class="action-btn edit-btn">Edit</button>
        <button class="action-btn stats-btn">View Stats</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const listing = ref({
  image: '/listing-1.jpg',
  title: 'Beautiful Beach House',
  location: 'Miami, FL',
  price: 250,
  rating: 4.9,
  bookings: 24
})
</script>

<style scoped>
.listing-card {
  border: 1px solid #e5e5e5;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 1rem;
}

.listing-content {
  display: flex;
}

.listing-image {
  width: 200px;
  height: 150px;
}

.listing-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.listing-details {
  flex: 1;
  padding: 1rem;
}

.listing-title {
  margin: 0 0 0.5rem 0;
  font-size: 1.125rem;
  font-weight: 600;
}

.listing-location {
  margin: 0 0 0.5rem 0;
  color: #666;
}

.listing-stats {
  display: flex;
  gap: 1rem;
}

.stat {
  font-size: 14px;
  color: #333;
}

.listing-actions {
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  justify-content: center;
}

.action-btn {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  font-size: 14px;
  min-width: 100px;
}

.edit-btn {
  border-color: #00a699;
  color: #00a699;
}

.stats-btn {
  border-color: #666;
  color: #666;
}

.action-btn:hover {
  opacity: 0.8;
}
</style>