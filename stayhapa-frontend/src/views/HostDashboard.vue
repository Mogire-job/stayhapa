<template>
  <div class="host-dashboard">
    <Navbar />
    <HostHeader />
    <QuickStats />
    <HostTabs @tab-change="handleTabChange" />
    <main class="dashboard-content">
      <ListingsManagement />
    </main>
  </div>
</template>

<script setup>
import Navbar from '@/components/common/Navbar.vue'
import HostHeader from '@/components/host/HostHeader.vue'
import QuickStats from '@/components/host/QuickStats.vue'
import HostTabs from '@/components/host/HostTabs.vue'
import ListingsManagement from '@/components/host/ListingsManagement.vue'

const handleTabChange = (tabId) => {
  console.log('Tab changed to:', tabId)
  // Handle tab change logic here
}
</script>

<style scoped>
.host-dashboard {
  min-height: 100vh;
  background: #f7f7f7;
}

.dashboard-content {
  padding: 2rem 0;
}
</style>