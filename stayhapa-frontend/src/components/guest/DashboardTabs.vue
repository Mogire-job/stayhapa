<template>
  <div class="host-dashboard">
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <span class="user-welcome">Welcome, {{ currentUser?.fullName }}</span>
          <button @click="handleLogout" class="nav-link">Logout</button>
        </div>
      </div>
    </nav>

    <div class="dashboard-container">
      <div class="sidebar">
        <div class="sidebar-menu">
          <router-link to="/host/dashboard" class="menu-item active">Dashboard</router-link>
          <router-link to="/host/add-property" class="menu-item">Add Property</router-link>
          <router-link to="/host/properties" class="menu-item">My Properties</router-link>
          <router-link to="/host/bookings" class="menu-item">Bookings</router-link>
          <router-link to="/host/earnings" class="menu-item">Earnings</router-link>
        </div>
      </div>

      <div class="dashboard-content">
        <div class="dashboard-header">
          <h1>Host Dashboard</h1>
          <p>Manage your properties and bookings</p>
        </div>

        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon">🏠</div>
            <div class="stat-info">
              <div class="stat-number">{{ properties.length }}</div>
              <div class="stat-label">Properties</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📅</div>
            <div class="stat-info">
              <div class="stat-number">{{ totalBookings }}</div>
              <div class="stat-label">Total Bookings</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">💰</div>
            <div class="stat-info">
              <div class="stat-number">${{ totalEarnings }}</div>
              <div class="stat-label">Total Earnings</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">⭐</div>
            <div class="stat-info">
              <div class="stat-number">4.8</div>
              <div class="stat-label">Average Rating</div>
            </div>
          </div>
        </div>

        <div class="quick-actions">
          <h2>Quick Actions</h2>
          <div class="actions-grid">
            <router-link to="/host/add-property" class="action-card">
              <div class="action-icon">➕</div>
              <h3>Add New Property</h3>
              <p>List a new property for travelers to book</p>
            </router-link>
            <router-link to="/host/properties" class="action-card">
              <div class="action-icon">🏠</div>
              <h3>Manage Properties</h3>
              <p>View and edit your existing properties</p>
            </router-link>
            <router-link to="/host/bookings" class="action-card">
              <div class="action-icon">📅</div>
              <h3>View Bookings</h3>
              <p>Check upcoming and past bookings</p>
            </router-link>
            <router-link to="/host/earnings" class="action-card">
              <div class="action-icon">💰</div>
              <h3>Earnings Report</h3>
              <p>View your earnings and payout history</p>
            </router-link>
          </div>
        </div>

        <div class="recent-properties">
          <h2>Your Properties</h2>
          <div v-if="properties.length > 0" class="properties-grid">
            <div v-for="property in properties.slice(0, 3)" :key="property.id" class="property-card">
              <img :src="property.images[0]" :alt="property.title" class="property-image">
              <div class="property-info">
                <h3>{{ property.title }}</h3>
                <p class="property-location">{{ property.location }}</p>
                <div class="property-details">
                  <span class="price">${{ property.price }}/night</span>
                  <span class="status" :class="property.status">{{ property.status }}</span>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="empty-state">
            <p>You haven't listed any properties yet.</p>
            <router-link to="/host/add-property" class="cta-button">Add Your First Property</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HostDashboard',
  data() {
    return {
      currentUser: null,
      properties: []
    }
  },
  computed: {
    totalBookings() {
      return this.properties.reduce((total, property) => total + (property.bookings || 0), 0);
    },
    totalEarnings() {
      return this.properties.reduce((total, property) => total + (property.earnings || 0), 0);
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      this.$router.push('/');
    },
    loadUserData() {
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        this.currentUser = JSON.parse(userData);
      } else {
        this.$router.push('/login');
      }
    },
    loadProperties() {
      const properties = JSON.parse(localStorage.getItem('stayhapa_properties') || '[]');
      this.properties = properties.filter(prop => prop.hostId === this.currentUser?.id);
    }
  },
  mounted() {
    this.loadUserData();
    this.loadProperties();
  }
}
</script>

<style scoped>
.host-dashboard {
  min-height: 100vh;
  background: #f8f9fa;
}

.navbar {
  padding: 1rem 2rem;
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1400px;
  margin: 0 auto;
}

.nav-logo h2 {
  color: #0F4C81;
  font-size: 1.8rem;
  font-weight: bold;
  margin: 0;
}

.nav-logo a {
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}

.user-welcome {
  color: #333;
  font-weight: 500;
}

.nav-link {
  color: #333;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s;
  background: none;
  border: none;
  cursor: pointer;
}

.nav-link:hover {
  color: #0F4C81;
}

.dashboard-container {
  display: flex;
  max-width: 1400px;
  margin: 0 auto;
  min-height: calc(100vh - 80px);
}

.sidebar {
  width: 250px;
  background: white;
  padding: 2rem 0;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.sidebar-menu {
  display: flex;
  flex-direction: column;
}

.menu-item {
  padding: 1rem 2rem;
  text-decoration: none;
  color: #333;
  transition: all 0.3s;
  border-left: 3px solid transparent;
}

.menu-item:hover,
.menu-item.active {
  background: #f8f9fa;
  border-left-color: #0F4C81;
  color: #0F4C81;
}

.dashboard-content {
  flex: 1;
  padding: 2rem;
}

.dashboard-header h1 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2.5rem;
}

.dashboard-header p {
  color: #666;
  margin-bottom: 2rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-bottom: 3rem;
}

.stat-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 1rem;
}

.stat-icon {
  font-size: 2rem;
}

.stat-number {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
}

.stat-label {
  color: #666;
  font-size: 0.9rem;
}

.quick-actions {
  margin-bottom: 3rem;
}

.quick-actions h2 {
  color: #333;
  margin-bottom: 1.5rem;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.action-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-decoration: none;
  color: inherit;
  transition: transform 0.3s;
}

.action-card:hover {
  transform: translateY(-4px);
}

.action-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.action-card h3 {
  color: #333;
  margin-bottom: 0.5rem;
}

.action-card p {
  color: #666;
  margin: 0;
}

.recent-properties h2 {
  color: #333;
  margin-bottom: 1.5rem;
}

.properties-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
}

.property-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.property-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.property-info {
  padding: 1.5rem;
}

.property-info h3 {
  color: #333;
  margin-bottom: 0.5rem;
}

.property-location {
  color: #666;
  margin-bottom: 1rem;
}

.property-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  font-weight: bold;
  color: #0F4C81;
  font-size: 1.1rem;
}

.status {
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.status.active {
  background: #e8f5e8;
  color: #2e7d32;
}

.status.pending {
  background: #fff3e0;
  color: #f57c00;
}

.empty-state {
  text-align: center;
  padding: 3rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.empty-state p {
  color: #666;
  margin-bottom: 1.5rem;
}

.cta-button {
  background: #0F4C81;
  color: white;
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  display: inline-block;
}

@media (max-width: 768px) {
  .dashboard-container {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    padding: 1rem 0;
  }
  
  .sidebar-menu {
    flex-direction: row;
    overflow-x: auto;
  }
  
  .menu-item {
    padding: 1rem;
    white-space: nowrap;
    border-left: none;
    border-bottom: 3px solid transparent;
  }
  
  .menu-item.active {
    border-left: none;
    border-bottom-color: #0F4C81;
  }
  
  .stats-grid {
    grid-template-columns: 1fr 1fr;
  }
  
  .actions-grid {
    grid-template-columns: 1fr;
  }
}
</style>