<template>
  <div class="guest-dashboard">
    <!-- Header -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <div class="user-menu" @click="showUserMenu = !showUserMenu">
            <div class="user-avatar">
              {{ currentUser?.fullName?.charAt(0) || 'G' }}
            </div>
            <span class="user-name">{{ currentUser?.fullName || 'Guest' }}</span>
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M6 9l6 6 6-6"/>
            </svg>
          </div>
          <div v-if="showUserMenu" class="dropdown-menu">
            <router-link to="/guest/profile" class="dropdown-item">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
              </svg>
              Profile
            </router-link>
            <router-link to="/account" class="dropdown-item">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="3"/>
                <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"/>
              </svg>
              Settings
            </router-link>
            <div class="dropdown-divider"></div>
            <button @click="switchToHost" class="dropdown-item">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
              </svg>
              Switch to Hosting
            </button>
            <button @click="handleLogout" class="dropdown-item logout">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                <polyline points="16 17 21 12 16 7"/>
                <line x1="21" y1="12" x2="9" y2="12"/>
              </svg>
              Logout
            </button>
          </div>
        </div>
      </div>
    </nav>

    <!-- Search Section -->
    <div class="search-section">
      <div class="search-container">
        <div class="search-filters">
          <div class="filter-group">
            <label>Where</label>
            <input 
              type="text" 
              v-model="filters.location" 
              placeholder="Search destinations"
              class="filter-input"
            >
          </div>
          
          <div class="filter-group">
            <label>Check in</label>
            <input 
              type="date" 
              v-model="filters.checkIn" 
              class="filter-input"
            >
          </div>
          
          <div class="filter-group">
            <label>Check out</label>
            <input 
              type="date" 
              v-model="filters.checkOut" 
              class="filter-input"
            >
          </div>
          
          <div class="filter-group">
            <label>Who</label>
            <select v-model="filters.guests" class="filter-input">
              <option value="">Add guests</option>
              <option v-for="n in 10" :key="n" :value="n">{{ n }} guest{{ n > 1 ? 's' : '' }}</option>
            </select>
          </div>
          
          <button @click="searchProperties" class="search-btn">
            <span>🔍</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="main-container">
      <!-- Featured Section -->
      <section class="featured-section">
        <div class="section-header">
          <h2>Popular homes in Mombasa</h2>
          <a href="#" class="see-all">See all ></a>
        </div>
        
        <div class="properties-grid">
          <div 
            v-for="property in featuredProperties" 
            :key="property.id" 
            class="property-card"
            @click="viewProperty(property)"
          >
            <div class="property-image">
              <img 
                v-if="property.images && property.images.length > 0" 
                :src="property.images[0]" 
                :alt="property.title"
              >
              <div v-else class="no-image">No Image</div>
              <div class="property-badge" v-if="property.rating >= 4.8">
                Guest favorite
              </div>
            </div>
            
            <div class="property-details">
              <h3 class="property-title">{{ property.title }}</h3>
              <p class="property-location">{{ property.city }}, {{ property.country }}</p>
              <p class="property-price">KSh {{ calculateTotalPrice(property.price) }} for 2 nights • ★ {{ property.rating }}</p>
              
              <div class="property-features">
                <span>{{ property.bedrooms }} bedroom{{ property.bedrooms !== 1 ? 's' : '' }}</span>
                <span>{{ property.bathrooms }} bathroom{{ property.bathrooms !== 1 ? 's' : '' }}</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- All Listings Section -->
      <section class="all-listings">
        <h2>All Available Stays</h2>
        <div class="listings-grid">
          <div 
            v-for="property in filteredProperties" 
            :key="property.id" 
            class="listing-card"
            @click="viewProperty(property)"
          >
            <div class="listing-image">
              <img 
                v-if="property.images && property.images.length > 0" 
                :src="property.images[0]" 
                :alt="property.title"
              >
              <div v-else class="no-image">No Image</div>
            </div>
            
            <div class="listing-details">
              <h4>{{ property.title }}</h4>
              <p class="listing-type">{{ property.type }} in {{ property.city }}</p>
              <p class="listing-price">KSh {{ calculateTotalPrice(property.price) }} for 2 nights • ★ {{ property.rating }}</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GuestDashboard',
  data() {
    return {
      currentUser: null,
      showUserMenu: false,
      allProperties: [],
      filteredProperties: [],
      filters: {
        location: '',
        checkIn: '',
        checkOut: '',
        guests: ''
      }
    }
  },
  computed: {
    featuredProperties() {
      // Show properties with highest ratings as featured
      return [...this.filteredProperties]
        .sort((a, b) => (b.rating || 0) - (a.rating || 0))
        .slice(0, 6);
    }
  },
  mounted() {
    this.loadUserData();
    this.loadProperties();
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
    loadProperties() {
      const properties = JSON.parse(localStorage.getItem('stayhapa_properties') || '[]');
      // Filter only active properties
      this.allProperties = properties.filter(prop => prop.status === 'active');
      this.filteredProperties = [...this.allProperties];
    },
    searchProperties() {
      let filtered = [...this.allProperties];
      
      // Filter by location
      if (this.filters.location) {
        filtered = filtered.filter(property => 
          property.city.toLowerCase().includes(this.filters.location.toLowerCase()) ||
          property.country.toLowerCase().includes(this.filters.location.toLowerCase()) ||
          property.address.toLowerCase().includes(this.filters.location.toLowerCase())
        );
      }
      
      // Filter by guests
      if (this.filters.guests) {
        filtered = filtered.filter(property => 
          property.maxGuests >= parseInt(this.filters.guests)
        );
      }
      
      this.filteredProperties = filtered;
    },
    calculateTotalPrice(pricePerNight) {
      return (pricePerNight * 2).toLocaleString();
    },
    viewProperty(property) {
      this.$router.push(`/property/${property.id}`);
    },
    switchToHost() {
      // Update user's current mode to host
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        const user = JSON.parse(userData);
        user.currentMode = 'host';
        localStorage.setItem('stayhapa_current_user', JSON.stringify(user));
        
        // Also update in users array for persistence
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        const userIndex = users.findIndex(u => u.id === user.id);
        if (userIndex !== -1) {
          users[userIndex].currentMode = 'host';
          localStorage.setItem('stayhapa_users', JSON.stringify(users));
        }
      }
      this.$router.push('/host/dashboard');
    },
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.guest-dashboard {
  min-height: 100vh;
  background: #ffffff;
}

.navbar {
  padding: 1rem 2rem;
  background: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1400px;
  margin: 0 auto;
}

.nav-logo h2 {
  color: #FF385C;
  font-size: 1.6rem;
  font-weight: 800;
  margin: 0;
  letter-spacing: -0.5px;
}

.nav-logo a {
  text-decoration: none;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-welcome {
  color: #333;
  font-weight: 500;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  border-radius: 24px;
  cursor: pointer;
  transition: all 0.2s;
}

.user-menu:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.user-avatar {
  width: 32px;
  height: 32px;
  background: #FF385C;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 0.9rem;
}

.user-name {
  font-weight: 500;
  color: #333;
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

.search-section {
  background: white;
  padding: 2rem 0;
  border-bottom: 1px solid #e0e0e0;
}

.search-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

.search-filters {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr auto;
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

.filter-input {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.filter-input:focus {
  outline: none;
  border-color: #0F4C81;
}

.search-btn {
  padding: 0.75rem 1.5rem;
  background: #FF385C;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1.2rem;
  transition: background-color 0.3s;
  height: fit-content;
}

.search-btn:hover {
  background: #E31C5F;
}

.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
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
  margin-bottom: 4rem;
}

.property-card {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  background: white;
}

.property-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
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

.property-badge {
  position: absolute;
  top: 1rem;
  left: 1rem;
  background: #FF385C;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: 600;
}

.property-details {
  padding: 1.5rem;
}

.property-title {
  font-size: 1.1rem;
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

.property-features {
  display: flex;
  gap: 1rem;
  font-size: 0.9rem;
  color: #666;
}

.all-listings h2 {
  color: #333;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

.listings-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
}

.listing-card {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s;
  background: white;
}

.listing-card:hover {
  transform: translateY(-2px);
}

.listing-image {
  height: 160px;
  overflow: hidden;
}

.listing-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.listing-details {
  padding: 1rem;
}

.listing-details h4 {
  margin: 0 0 0.5rem 0;
  color: #333;
  font-size: 1rem;
}

.listing-type {
  color: #666;
  margin: 0 0 0.5rem 0;
  font-size: 0.9rem;
  text-transform: capitalize;
}

.listing-price {
  color: #333;
  font-weight: 600;
  margin: 0;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .nav-container {
    flex-direction: column;
    gap: 1rem;
  }
  
  .nav-links {
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .search-filters {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
  
  .main-container {
    padding: 1rem;
  }
  
  .properties-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
  
  .listings-grid {
    grid-template-columns: 1fr;
  }
  
  .section-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  min-width: 200px;
  padding: 0.5rem;
  margin-top: 0.5rem;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1rem;
  color: #333;
  text-decoration: none;
  border-radius: 8px;
  width: 100%;
  border: none;
  background: none;
  cursor: pointer;
  font-size: 0.95rem;
  transition: background 0.2s;
}

.dropdown-item:hover {
  background: #f7f7f7;
}

.dropdown-item.logout {
  color: #e74c3c;
}

.dropdown-divider {
  height: 1px;
  background: #e0e0e0;
  margin: 0.5rem 0;
}

.nav-links {
  position: relative;
}
</style>