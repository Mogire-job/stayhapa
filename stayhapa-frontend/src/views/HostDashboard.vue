<template>
  <div class="host-dashboard">
    <!-- Header -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <span class="user-welcome" v-if="currentUser">
            Welcome, {{ currentUser.fullName }}
          </span>
          <button @click="switchToGuest" class="nav-link switch-btn">Switch to Guest</button>
          <button @click="handleLogout" class="nav-link">Logout</button>
        </div>
      </div>
    </nav>

    <!-- Main Dashboard -->
    <div class="dashboard-container">
      <!-- Stats Overview -->
      <div class="stats-section">
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon">🏠</div>
            <div class="stat-info">
              <h3>{{ hostStats.totalProperties }}</h3>
              <p>Total Properties</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">📅</div>
            <div class="stat-info">
              <h3>{{ hostStats.activeBookings }}</h3>
              <p>Active Bookings</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">⭐</div>
            <div class="stat-info">
              <h3>{{ hostStats.averageRating }}</h3>
              <p>Average Rating</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">💰</div>
            <div class="stat-info">
              <h3>KSh {{ hostStats.totalEarnings.toLocaleString() }}</h3>
              <p>Total Earnings</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Quick Actions -->
      <div class="actions-section">
        <h2>Manage Your Business</h2>
        <div class="actions-grid">
          <router-link to="/host/add-property" class="action-card">
            <div class="action-icon">➕</div>
            <h3>Add New Property</h3>
            <p>List a new space for travelers</p>
          </router-link>
          
          <router-link to="/host/properties" class="action-card">
            <div class="action-icon">🏠</div>
            <h3>Manage Properties</h3>
            <p>View and edit your listings</p>
          </router-link>
          
          <div class="action-card" @click="viewBookings">
            <div class="action-icon">📅</div>
            <h3>Bookings</h3>
            <p>Manage reservations and calendar</p>
          </div>
          
          <div class="action-card" @click="viewEarnings">
            <div class="action-icon">💰</div>
            <h3>Earnings</h3>
            <p>View your income and payouts</p>
          </div>
        </div>
      </div>

      <!-- Recent Properties -->
      <div class="properties-section">
        <div class="section-header">
          <h2>Your Properties</h2>
          <router-link to="/host/properties" class="see-all">View All ></router-link>
        </div>
        
        <div v-if="hostProperties.length > 0" class="properties-grid">
          <div 
            v-for="property in recentProperties" 
            :key="property.id" 
            class="property-card"
          >
            <div class="property-image">
              <img 
                v-if="property.images && property.images.length > 0" 
                :src="property.images[0]" 
                :alt="property.title"
              >
              <div v-else class="no-image">No Image</div>
              <div class="property-status" :class="property.status">
                {{ property.status === 'active' ? 'Active' : 'Inactive' }}
              </div>
            </div>
            
            <div class="property-details">
              <h3 class="property-title">{{ property.title }}</h3>
              <p class="property-location">{{ property.city }}, {{ property.country }}</p>
              <p class="property-price">KSh {{ property.price }} / night</p>
              
              <div class="property-meta">
                <span>★ {{ property.rating || 'New' }}</span>
                <span>👥 {{ property.maxGuests }} guests</span>
                <span>🛏️ {{ property.bedrooms }} beds</span>
              </div>
              
              <div class="property-actions">
                <button @click="editProperty(property)" class="action-btn edit-btn">Edit</button>
                <button @click="togglePropertyStatus(property)" class="action-btn status-btn">
                  {{ property.status === 'active' ? 'Deactivate' : 'Activate' }}
                </button>
              </div>
            </div>
          </div>
        </div>
        
        <div v-else class="empty-state">
          <div class="empty-icon">🏠</div>
          <h3>No Properties Listed Yet</h3>
          <p>Start earning by listing your first property</p>
          <router-link to="/host/add-property" class="add-property-btn">
            List Your First Property
          </router-link>
        </div>
      </div>

      <!-- Recent Activity -->
      <div class="activity-section">
        <h2>Recent Activity</h2>
        <div class="activity-list">
          <div v-if="recentActivity.length > 0" class="activity-items">
            <div 
              v-for="activity in recentActivity" 
              :key="activity.id" 
              class="activity-item"
            >
              <div class="activity-icon" :class="activity.type">
                {{ getActivityIcon(activity.type) }}
              </div>
              <div class="activity-details">
                <p class="activity-message">{{ activity.message }}</p>
                <span class="activity-time">{{ formatTime(activity.timestamp) }}</span>
              </div>
            </div>
          </div>
          <div v-else class="empty-activity">
            <p>No recent activity</p>
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
      hostProperties: [],
      hostStats: {
        totalProperties: 0,
        activeBookings: 0,
        averageRating: 0,
        totalEarnings: 0
      },
      recentActivity: []
    }
  },
  computed: {
    recentProperties() {
      return this.hostProperties.slice(0, 4);
    }
  },
  mounted() {
    this.loadUserData();
    this.loadHostProperties();
    this.calculateStats();
    this.loadRecentActivity();
  },
  methods: {
    loadUserData() {
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        this.currentUser = JSON.parse(userData);
        
        // Check if user is verified
        if (!this.currentUser.isVerified) {
          this.$router.push('/verify-account');
          return;
        }
      } else {
        this.$router.push('/login');
      }
    },
    loadHostProperties() {
      const properties = JSON.parse(localStorage.getItem('stayhapa_properties') || '[]');
      // Filter properties for current host
      this.hostProperties = properties.filter(property => 
        property.hostId === this.currentUser.id
      );
    },
    calculateStats() {
      this.hostStats.totalProperties = this.hostProperties.length;
      
      // Calculate average rating
      const ratedProperties = this.hostProperties.filter(p => p.rating > 0);
      if (ratedProperties.length > 0) {
        const totalRating = ratedProperties.reduce((sum, property) => sum + property.rating, 0);
        this.hostStats.averageRating = (totalRating / ratedProperties.length).toFixed(1);
      } else {
        this.hostStats.averageRating = 0;
      }
      
      // Calculate earnings (simulated - in real app, this would come from bookings)
      this.hostStats.totalEarnings = this.hostProperties.reduce((total, property) => {
        return total + (property.price * 10); // Simulate 10 bookings per property
      }, 0);
      
      // Simulate active bookings
      this.hostStats.activeBookings = Math.floor(Math.random() * 10) + 1;
    },
    loadRecentActivity() {
      // Simulate recent activity
      this.recentActivity = [
        {
          id: 1,
          type: 'booking',
          message: 'New booking for Beachfront Villa',
          timestamp: new Date(Date.now() - 2 * 60 * 60 * 1000) // 2 hours ago
        },
        {
          id: 2,
          message: 'Your property Luxury Apartment got a 5-star review',
          type: 'review',
          timestamp: new Date(Date.now() - 5 * 60 * 60 * 1000) // 5 hours ago
        },
        {
          id: 3,
          type: 'property',
          message: 'Your property City Loft was featured',
          timestamp: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000) // 1 day ago
        }
      ];
    },
    getActivityIcon(type) {
      const icons = {
        booking: '📅',
        review: '⭐',
        property: '🏠',
        earnings: '💰'
      };
      return icons[type] || '📢';
    },
    formatTime(timestamp) {
      const now = new Date();
      const diff = now - timestamp;
      
      const minutes = Math.floor(diff / 60000);
      const hours = Math.floor(diff / 3600000);
      const days = Math.floor(diff / 86400000);
      
      if (minutes < 60) {
        return `${minutes} min ago`;
      } else if (hours < 24) {
        return `${hours} hour${hours > 1 ? 's' : ''} ago`;
      } else {
        return `${days} day${days > 1 ? 's' : ''} ago`;
      }
    },
    editProperty(property) {
      this.$router.push(`/host/edit-property/${property.id}`);
    },
    togglePropertyStatus(property) {
      const properties = JSON.parse(localStorage.getItem('stayhapa_properties') || '[]');
      const propertyIndex = properties.findIndex(p => p.id === property.id);
      
      if (propertyIndex !== -1) {
        properties[propertyIndex].status = properties[propertyIndex].status === 'active' ? 'inactive' : 'active';
        localStorage.setItem('stayhapa_properties', JSON.stringify(properties));
        
        // Reload properties
        this.loadHostProperties();
        
        alert(`Property ${properties[propertyIndex].status === 'active' ? 'activated' : 'deactivated'} successfully!`);
      }
    },
    viewBookings() {
      alert('Bookings management coming soon!');
    },
    viewEarnings() {
      alert('Earnings dashboard coming soon!');
    },
    switchToGuest() {
      // Update user's current mode to guest
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        const user = JSON.parse(userData);
        user.currentMode = 'guest';
        localStorage.setItem('stayhapa_current_user', JSON.stringify(user));
        
        // Also update in users array for persistence
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        const userIndex = users.findIndex(u => u.id === user.id);
        if (userIndex !== -1) {
          users[userIndex].currentMode = 'guest';
          localStorage.setItem('stayhapa_users', JSON.stringify(users));
        }
      }
      this.$router.push('/guest/dashboard');
    },
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      this.$router.push('/');
    }
  }
};
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
  border-bottom: 1px solid #e0e0e0;
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
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
  transition: all 0.3s;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  padding: 0.5rem 1rem;
  border-radius: 6px;
}

.nav-link:hover {
  color: #0F4C81;
  background: #f8f9fa;
}

.switch-btn {
  background: #0F4C81;
  color: white !important;
  border: 2px solid #0F4C81;
}

.switch-btn:hover {
  background: #0a3a6b;
  border-color: #0a3a6b;
  color: white !important;
}

.dashboard-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

/* Stats Section */
.stats-section {
  margin-bottom: 3rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.stat-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-4px);
}

.stat-icon {
  font-size: 2.5rem;
}

.stat-info h3 {
  font-size: 2rem;
  color: #333;
  margin: 0 0 0.5rem 0;
}

.stat-info p {
  color: #666;
  margin: 0;
  font-weight: 500;
}

/* Actions Section */
.actions-section {
  margin-bottom: 3rem;
}

.actions-section h2 {
  color: #333;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
}

.action-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-decoration: none;
  color: inherit;
  transition: all 0.3s;
  cursor: pointer;
  border: 2px solid transparent;
}

.action-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
  border-color: #0F4C81;
}

.action-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.action-card h3 {
  color: #333;
  margin: 0 0 0.5rem 0;
  font-size: 1.3rem;
}

.action-card p {
  color: #666;
  margin: 0;
}

/* Properties Section */
.properties-section {
  margin-bottom: 3rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.section-header h2 {
  color: #333;
  font-size: 1.8rem;
  margin: 0;
}

.see-all {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

.see-all:hover {
  text-decoration: underline;
}

.properties-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.property-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.property-card:hover {
  transform: translateY(-4px);
}

.property-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.property-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.no-image {
  width: 100%;
  height: 100%;
  background: #f0f0f0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
  font-weight: 500;
}

.property-status {
  position: absolute;
  top: 1rem;
  right: 1rem;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  color: white;
}

.property-status.active {
  background: #4caf50;
}

.property-status.inactive {
  background: #ff9800;
}

.property-details {
  padding: 1.5rem;
}

.property-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  color: #333;
}

.property-location {
  color: #666;
  margin: 0 0 0.5rem 0;
  font-size: 0.9rem;
}

.property-price {
  color: #333;
  font-weight: 600;
  margin: 0 0 1rem 0;
}

.property-meta {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  font-size: 0.9rem;
  color: #666;
}

.property-actions {
  display: flex;
  gap: 0.5rem;
}

.action-btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
  flex: 1;
}

.edit-btn {
  background: #0F4C81;
  color: white;
}

.edit-btn:hover {
  background: #0a3a6b;
}

.status-btn {
  background: #f8f9fa;
  color: #333;
  border: 1px solid #e0e0e0;
}

.status-btn:hover {
  background: #e9ecef;
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1.5rem;
}

.empty-state h3 {
  color: #333;
  margin: 0 0 1rem 0;
  font-size: 1.5rem;
}

.empty-state p {
  color: #666;
  margin: 0 0 2rem 0;
}

.add-property-btn {
  display: inline-block;
  padding: 1rem 2rem;
  background: #0F4C81;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 600;
  transition: background-color 0.3s;
}

.add-property-btn:hover {
  background: #0a3a6b;
}

/* Activity Section */
.activity-section {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.activity-section h2 {
  color: #333;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-icon {
  font-size: 1.5rem;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
  border-radius: 8px;
}

.activity-details {
  flex: 1;
}

.activity-message {
  color: #333;
  margin: 0 0 0.25rem 0;
  font-weight: 500;
}

.activity-time {
  color: #666;
  font-size: 0.9rem;
}

.empty-activity {
  text-align: center;
  padding: 2rem;
  color: #666;
}

@media (max-width: 768px) {
  .dashboard-container {
    padding: 1rem;
  }
  
  .nav-container {
    flex-direction: column;
    gap: 1rem;
  }
  
  .nav-links {
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .actions-grid {
    grid-template-columns: 1fr;
  }
  
  .properties-grid {
    grid-template-columns: 1fr;
  }
  
  .section-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }
  
  .property-actions {
    flex-direction: column;
  }
}
</style>