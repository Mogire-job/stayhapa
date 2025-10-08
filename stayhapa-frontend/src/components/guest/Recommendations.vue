<template>
  <div class="recommendations">
    <h2 class="section-title">Explore similar properties</h2>
    <div class="carousel">
      <div class="carousel-container">
        <div 
          v-for="property in recommendedProperties" 
          :key="property.id"
          class="property-card"
        >
          <img :src="property.image" alt="Property" class="property-img" />
          <div class="property-info">
            <h4 class="property-title">{{ property.title }}</h4>
            <p class="property-price">{{ property.price }}</p>
            <p class="property-location">{{ property.location }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const recommendedProperties = ref([
  {
    id: 1,
    image: '/property-1.jpg',
    title: 'Modern Apartment',
    price: '$120/night',
    location: 'New York'
  },
  {
    id: 2,
    image: '/property-2.jpg',
    title: 'Cozy Cottage',
    price: '$95/night',
    location: 'California'
  },
  {
    id: 3,
    image: '/property-3.jpg',
    title: 'Luxury Villa',
    price: '$250/night',
    location: 'Miami'
  },
  {
    id: 4,
    image: '/property-4.jpg',
    title: 'City Loft',
    price: '$110/night',
    location: 'Chicago'
  }
])
</script>

<style scoped>
.recommendations {
  margin-top: 2rem;
}

.section-title {
  margin-bottom: 1rem;
  font-size: 1.5rem;
  font-weight: 600;
}

.carousel-container {
  display: flex;
  gap: 1rem;
  overflow-x: auto;
  padding-bottom: 1rem;
}

.property-card {
  min-width: 250px;
  border: 1px solid #e5e5e5;
  border-radius: 12px;
  overflow: hidden;
}

.property-img {
  width: 100%;
  height: 160px;
  object-fit: cover;
}

.property-info {
  padding: 1rem;
}

.property-title {
  margin: 0 0 0.5rem 0;
  font-size: 1rem;
  font-weight: 600;
}

.property-price {
  margin: 0 0 0.25rem 0;
  font-weight: 500;
  color: #000;
}

.property-location {
  margin: 0;
  color: #666;
  font-size: 14px;
}
</style>