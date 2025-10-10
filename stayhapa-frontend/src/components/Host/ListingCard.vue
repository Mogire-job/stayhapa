<!-- src/views/Listings.vue -->
<template>
  <div class="listings-page">
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link v-if="!currentUser" to="/login" class="nav-link">Login</router-link>
          <router-link v-if="!currentUser" to="/signup" class="nav-link signup-btn">Sign Up</router-link>
          <span v-if="currentUser" class="user-welcome">Welcome, {{ currentUser.fullName }}</span>
          <router-link v-if="currentUser && currentUser.userType === 'host'" to="/host/dashboard" class="nav-link">Host Dashboard</router-link>
          <router-link v-if="currentUser && currentUser.userType === 'traveler'" to="/dashboard" class="nav-link">Dashboard</router-link>
          <button v-if="currentUser" @click="handleLogout" class="nav-link">Logout</button>
        </div>
      </div>
    </nav>

    <div class="listings-container">
      <div class="listings-header">
        <h1>Find Your Perfect Stay</h1>
        <p>Discover unique accommodations around the world</p>
      </div>

      <!-- Search and Filters -->
      <div class="filters-section">
        <div class="search-filters">
          <div class="filter-group">
            <label>Location</label>
            <input 
              type="text" 
              v-model="filters.location" 
              placeholder="Where are you going?"
            >
          </div>
          <div class="filter-group">
            <label>Check-in</label>
            <input type="date" v-model="filters.checkIn">
          </div>
          <div class="filter-group">
            <label>Check-out</label>
            <input type="date" v-model="filters.checkOut">
          </div>
          <div class="filter-group">
            <label>Guests</label>
            <select v-model="filters.guests">
              <option value="">Any</option>
              <option value="1">1 Guest</option>
              <option value="2">2 Guests</option>
              <option value="3">3 Guests</option>
              <option value="4">4 Guests</option>
              <option value="5">5+ Guests</option>
            </select>
          </div>
          <div class="filter-group">
            <label>Price Range</label>
            <select v-model="filters.priceRange">
              <option value="">Any</option>
              <option value="0-50">$0 - $50</option>
              <option value="51-100">$51 - $100</option>
              <option value="101-200">$101 - $200</option>
              <option value="201+">$201+</option>
            </select>
          </div>
          <button @click="applyFilters" class="filter-btn">Search</button>
        </div>
      </div>

      <!-- Properties Grid using your ListingCard component -->
      <div class="listings-content">
        <div class="listings-grid">
          <ListingCard 
            v-for="property in filteredProperties" 
            :key="property.id"
            :property="property"
            @click="viewProperty(property.id)"
          />
        </div>

        <div v-if="filteredProperties.length === 0" class="empty-state">
          <p>No properties found matching your criteria.</p>
          <button @click="clearFilters" class="cta-button">Clear Filters</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ListingCard from '@/components/ListingCard.vue'

export default {
  name: 'Listings',
  components: {
    ListingCard
  },
  data() {
    return {
      currentUser: null,
      properties: [],
      filters: {
        location: '',
        checkIn: '',
        checkOut: '',
        guests: '',
        priceRange: ''
      }
    }
  },
  computed: {
    filteredProperties() {
      let filtered = this.properties;

      if (this.filters.location) {
        filtered = filtered.filter(property => 
          property.city?.toLowerCase().includes(this.filters.location.toLowerCase()) ||
          property.country?.toLowerCase().includes(this.filters.location.toLowerCase()) ||
          property.address?.toLowerCase().includes(this.filters.location.toLowerCase())
        );
      }

      if (this.filters.guests) {
        filtered = filtered.filter(property => 
          property.maxGuests >= parseInt(this.filters.guests)
        );
      }

      if (this.filters.priceRange) {
        switch (this.filters.priceRange) {
          case '0-50':
            filtered = filtered.filter(property => property.price <= 50);
            break;
          case '51-100':
            filtered = filtered.filter(property => property.price > 50 && property.price <= 100);
            break;
          case '101-200':
            filtered = filtered.filter(property => property.price > 100 && property.price <= 200);
            break;
          case '201+':
            filtered = filtered.filter(property => property.price > 200);
            break;
        }
      }

      return filtered;
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      this.currentUser = null;
      window.location.reload();
    },
    loadUserData() {
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        this.currentUser = JSON.parse(userData);
      }
    },
    loadProperties() {
      const properties = JSON.parse(localStorage.getItem('stayhapa_properties') || '[]');
      this.properties = properties.filter(prop => prop.status === 'active');
    },
    applyFilters() {
      // Filters are applied automatically through computed property
    },
    clearFilters() {
      this.filters = {
        location: '',
        checkIn: '',
        checkOut: '',
        guests: '',
        priceRange: ''
      };
    },
    viewProperty(propertyId) {
      // In a real app, this would navigate to a property detail page
      alert(`Viewing property ${propertyId}. This would show property details in a real implementation.`);
    }
  },
  mounted() {
    this.loadUserData();
    this.loadProperties();
  }
}
</script>

<style scoped>
.listings-page {
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

.signup-btn {
  background: #0F4C81;
  color: white !important;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-weight: 600;
}

.listings-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.listings-header {
  text-align: center;
  margin-bottom: 3rem;
}

.listings-header h1 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2.5rem;
}

.listings-header p {
  color: #666;
  font-size: 1.1rem;
}

.filters-section {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

.search-filters {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr 1fr auto;
  gap: 1rem;
  align-items: end;
}

.filter-group {
  display: flex;
  flex-direction: column;
}

.filter-group label {
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
  font-size: 0.9rem;
}

.filter-group input,
.filter-group select {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
}

.filter-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.filter-btn:hover {
  background: #0a3a6b;
}

.listings-content {
  margin-top: 2rem;
}

.listings-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 2rem;
}

.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  grid-column: 1 / -1;
}

.empty-state p {
  color: #666;
  margin-bottom: 1.5rem;
  font-size: 1.1rem;
}

.cta-button {
  background: #0F4C81;
  color: white;
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  border: none;
  cursor: pointer;
  display: inline-block;
}

@media (max-width: 768px) {
  .listings-container {
    padding: 1rem;
  }
  
  .search-filters {
    grid-template-columns: 1fr;
  }
  
  .listings-grid {
    grid-template-columns: 1fr;
  }
  
  .nav-links {
    gap: 1rem;
    flex-wrap: wrap;
  }
}
</style>