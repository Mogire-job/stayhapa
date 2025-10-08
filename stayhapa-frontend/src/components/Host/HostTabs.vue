<template>
  <div class="host-tabs">
    <div class="tabs-container">
      <button 
        v-for="tab in tabs" 
        :key="tab.id"
        :class="['tab-button', { active: activeTab === tab.id }]"
        @click="setActiveTab(tab.id)"
      >
        {{ tab.label }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeTab = ref('listings')

const tabs = [
  { id: 'listings', label: 'Listings' },
  { id: 'calendar', label: 'Calendar' },
  { id: 'bookings', label: 'Bookings' },
  { id: 'earnings', label: 'Earnings' },
  { id: 'messages', label: 'Messages' },
  { id: 'performance', label: 'Performance' }
]

const setActiveTab = (tabId) => {
  activeTab.value = tabId
  emit('tab-change', tabId)
}

const emit = defineEmits(['tab-change'])
</script>

<style scoped>
.host-tabs {
  background: white;
  border-bottom: 1px solid #e5e5e5;
}

.tabs-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  gap: 2rem;
}

.tab-button {
  padding: 1rem 0;
  background: none;
  border: none;
  border-bottom: 2px solid transparent;
  cursor: pointer;
  font-size: 14px;
  color: #666;
}

.tab-button.active {
  color: #000;
  border-bottom-color: #000;
  font-weight: 500;
}

.tab-button:hover {
  color: #000;
}
</style>