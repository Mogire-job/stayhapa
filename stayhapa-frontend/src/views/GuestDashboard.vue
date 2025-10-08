<template>
  <div class="guest-dashboard">
    <Navbar />
    <UserProfileHeader />
    <DashboardTabs @tab-change="handleTabChange" />
    <main class="dashboard-content">
      <div class="content-container">
        <UpcomingTripCard />
        <Recommendations />
      </div>
    </main>
  </div>
</template>

<script setup>
import Navbar from '@/components/common/Navbar.vue'
import UserProfileHeader from '@/components/guest/UserProfileHeader.vue'
import DashboardTabs from '@/components/guest/DashboardTabs.vue'
import UpcomingTripCard from '@/components/guest/UpcomingTripCard.vue'
import Recommendations from '@/components/guest/Recommendations.vue'

const handleTabChange = (tabId) => {
  console.log('Tab changed to:', tabId)
  // Handle tab change logic here
}
</script>

<style scoped>
.guest-dashboard {
  min-height: 100vh;
  background: #f7f7f7;
}

.dashboard-content {
  padding: 2rem 0;
}

.content-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
</style>