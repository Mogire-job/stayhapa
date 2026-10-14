<template>
  <div class="listings-management">
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <span class="user-welcome">Welcome, {{ currentUser?.fullName }}</span>
          <router-link to="/host/dashboard" class="nav-link">Dashboard</router-link>
          <button @click="handleLogout" class="nav-link">Logout</button>
        </div>
      </div>
    </nav>

    <div class="management-container">
      <div class="management-header">
        <h1>Manage Your Properties</h1>
        <p>View, edit, and manage all your listed properties</p>
        <router-link to="/host/add-property" class="add-property-btn">
          + Add New Property
        </router-link>
      </div>

      <div class="properties-list">
        <div v-if="loading" class="loading-state">
          <div class="loading-spinner"></div>
          <p>Loading properties...</p>
        </div>

        <div v-else-if="properties.length === 0" class="empty-state">
          <div class="empty-icon">🏠</div>
          <h3>No Properties Listed</h3>
          <p>You haven't listed any properties yet. Start by adding your first property!</p>
          <router-link to="/host/add-property" class="cta-button">Add Your First Property</router-link>
        </div>

        <div v-else class="properties-grid">
          <div 
            v-for="property in properties" 
            :key="property.id"
            class="property-management-card"
          >
            <div class="property-image">
              <img :src="property.images && property.images[0] ? property.images[0] : '/placeholder.jpg'" :alt="property.title">
              <div class="property-status" :class="{ active: property.active }">
                {{ property.active ? 'Active' : 'Inactive' }}
              </div>
            </div>
            
            <div class="property-details">
              <h3>{{ property.title }}</h3>
              <p class="property-location">{{ property.city }}, {{ property.country }}</p>
              
              <div class="property-stats">
                <div class="stat">
                  <span class="stat-value">KES {{ Number(property.pricePerNight).toLocaleString('en-KE') }}</span>
                  <span class="stat-label">per night</span>
                </div>
                <div class="stat">
                  <span class="stat-value">{{ property.bedrooms }}</span>
                  <span class="stat-label">bedrooms</span>
                </div>
                <div class="stat">
                  <span class="stat-value">{{ property.maxGuests }}</span>
                  <span class="stat-label">guests max</span>
                </div>
              </div>

              <div class="property-actions">
                <button @click="editProperty(property)" class="action-btn edit-btn">Edit</button>
                <button @click="togglePropertyStatus(property)" class="action-btn status-btn">
                  {{ property.active ? 'Deactivate' : 'Activate' }}
                </button>
                <button @click="deleteProperty(property.id)" class="action-btn delete-btn">Delete</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import propertyService from '@/services/propertyService';

export default {
  name: 'ListingsManagement',
  data() {
    return {
      currentUser: null,
      properties: [],
      loading: false
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      localStorage.removeItem('stayhapa_token');
      this.$router.push('/');
    },
    loadUserData() {
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        this.currentUser = JSON.parse(userData);
        if (this.currentUser?.userType !== 'host') {
          this.$router.push('/dashboard');
        }
      } else {
        this.$router.push('/login');
      }
    },
    async loadProperties() {
      this.loading = true;
      try {
        this.properties = await propertyService.getMyProperties();
      } catch (error) {
        console.error('Error loading properties:', error);
        alert('Failed to load properties');
      } finally {
        this.loading = false;
      }
    },
    editProperty(property) {
      this.$router.push(`/host/edit-property/${property.id}`);
    },
    async togglePropertyStatus(property) {
      try {
        await propertyService.updateProperty(property.id, {
          ...property,
          active: !property.active
        });
        this.loadProperties();
        alert(`Property ${property.active ? 'deactivated' : 'activated'} successfully!`);
      } catch (error) {
        console.error('Error updating property:', error);
        alert('Failed to update property');
      }
    },
    async deleteProperty(propertyId) {
      if (confirm('Are you sure you want to delete this property? This action cannot be undone.')) {
        try {
          await propertyService.deleteProperty(propertyId);
          this.loadProperties();
          alert('Property deleted successfully!');
        } catch (error) {
          console.error('Error deleting property:', error);
          alert('Failed to delete property');
        }
      }
    }
  },
  mounted() {
    this.loadUserData();
    this.loadProperties();
  }
}
</script>

<style scoped>
.listings-management {
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

.management-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.management-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 3rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.management-header h1 {
  color: #333;
  font-size: 2.5rem;
  margin: 0;
}

.management-header p {
  color: #666;
  margin: 0.5rem 0 0 0;
  flex-basis: 100%;
}

.add-property-btn {
  background: #0F4C81;
  color: white;
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  transition: background-color 0.3s;
}

.add-property-btn:hover {
  background: #0a3a6b;
}

.properties-list {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.empty-state {
  text-align: center;
  padding: 4rem 2rem;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1.5rem;
}

.empty-state h3 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}

.empty-state p {
  color: #666;
  margin-bottom: 2rem;
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

.properties-grid {
  display: flex;
  flex-direction: column;
}

.property-management-card {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 2rem;
  padding: 2rem;
  border-bottom: 1px solid #e0e0e0;
  transition: background-color 0.3s;
}

.property-management-card:hover {
  background: #f8f9fa;
}

.property-management-card:last-child {
  border-bottom: none;
}

.property-image {
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  height: 200px;
}

.property-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.property-status {
  position: absolute;
  top: 1rem;
  right: 1rem;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: capitalize;
}

.property-status.active {
  background: #e8f5e8;
  color: #2e7d32;
}

.property-status.inactive {
  background: #ffebee;
  color: #c62828;
}

.property-details h3 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 1.3rem;
}

.property-location {
  color: #666;
  margin-bottom: 1.5rem;
}

.property-stats {
  display: flex;
  gap: 2rem;
  margin-bottom: 1.5rem;
}

.stat {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 1.5rem;
  font-weight: bold;
  color: #0F4C81;
}

.stat-label {
  display: block;
  font-size: 0.9rem;
  color: #666;
  margin-top: 0.25rem;
}

.property-actions {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.action-btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
}

.edit-btn {
  background: #e3f2fd;
  color: #0F4C81;
}

.edit-btn:hover {
  background: #bbdefb;
}

.status-btn {
  background: #fff3e0;
  color: #f57c00;
}

.status-btn:hover {
  background: #ffe0b2;
}

.delete-btn {
  background: #ffebee;
  color: #c62828;
}

.delete-btn:hover {
  background: #ffcdd2;
}

@media (max-width: 768px) {
  .navbar {
    padding: 1rem;
  }

  .nav-container {
    flex-direction: column;
    align-items: stretch;
    gap: 0.75rem;
  }

  .nav-links {
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 0.5rem;
  }

  .user-welcome {
    flex-basis: 100%;
    overflow-wrap: anywhere;
  }

  .management-container {
    padding: 1rem;
  }
  
  .management-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .property-management-card {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
  
  .property-image {
    height: 200px;
  }
  
  .property-stats {
    flex-wrap: wrap;
    justify-content: space-around;
    gap: 1rem;
  }
  
  .property-actions {
    flex-direction: column;
  }
  
  .action-btn {
    width: 100%;
    text-align: center;
  }
}
</style>