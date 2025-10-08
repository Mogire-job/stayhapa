<template>
  <div class="listings-management">
    <div class="listings-container">
      <ListingCard v-for="listing in listings" :key="listing.id" />
      <button class="add-property-btn">
        + Add New Property
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ListingCard from './ListingCard.vue'

const listings = ref([
  { id: 1 },
  { id: 2 },
  { id: 3 }
])
</script>

<style scoped>
.listings-management {
  padding: 2rem 0;
}

.listings-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.add-property-btn {
  width: 100%;
  padding: 2rem;
  border: 2px dashed #ddd;
  border-radius: 12px;
  background: white;
  color: #666;
  font-size: 1.125rem;
  cursor: pointer;
  transition: all 0.2s;
}

.add-property-btn:hover {
  border-color: #00a699;
  color: #00a699;
}
</style>