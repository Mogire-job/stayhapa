<template>
  <div class="home-page">
    <!-- Hero Section -->
    <section class="hero-section">
      <!-- Navigation Bar -->
      <nav class="navbar">
        <div class="nav-container">
          <div class="nav-logo">
            <router-link to="/">
              <h2>Stayhapa</h2>
            </router-link>
          </div>
          <div class="nav-links">
      
            <router-link to="/login" class="nav-link">Login</router-link>
            <router-link to="/signup" class="nav-link signup-btn">Sign Up</router-link>
          </div>
        </div>
      </nav>

      <!-- Hero Content -->
      <div class="hero-content">
        <div class="hero-text">
          <h1 class="hero-title">Find Your Perfect Stay Experience</h1>
          <p class="hero-subtitle">Discover unique accommodations and create unforgettable memories</p>
        </div>

        <!-- Search Widget -->
        <div class="search-widget">
          <div class="search-tabs">
            <button 
              class="search-tab active" 
              @click="setActiveTab('stays')"
            >
              Stays
            </button>
            <button 
              class="search-tab" 
              @click="setActiveTab('experiences')"
            >
              Experiences
            </button>
          </div>

          <div class="search-form">
            <div class="search-field">
              <label>Where</label>
              <input 
                type="text" 
                placeholder="Search destinations" 
                v-model="searchLocation"
                @focus="showLocationSuggestions = true"
              >
              <div v-if="showLocationSuggestions" class="suggestions-dropdown">
                <div class="suggestion-item">Nairobi, Kenya</div>
                <div class="suggestion-item">Mombasa, Kenya</div>
                <div class="suggestion-item">Zanzibar, Tanzania</div>
                <div class="suggestion-item">Cape Town, South Africa</div>
              </div>
            </div>

            <div class="search-field">
              <label>Check in</label>
              <input 
                type="date" 
                v-model="checkInDate"
              >
            </div>

            <div class="search-field">
              <label>Check out</label>
              <input 
                type="date" 
                v-model="checkOutDate"
              >
            </div>

            <div class="search-field">
              <label>Guests</label>
              <div class="guests-selector" @click="showGuestsModal = true">
                {{ guestsCount }} guest{{ guestsCount !== 1 ? 's' : '' }}
              </div>
            </div>

            <button class="search-btn" @click="handleSearch">
              <span>Search</span>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                <path d="M21 21L16.514 16.506L21 21ZM19 10.5C19 15.194 15.194 19 10.5 19C5.806 19 2 15.194 2 10.5C2 5.806 5.806 2 10.5 2C15.194 2 19 5.806 19 10.5Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Trust Indicators -->
      <div class="trust-indicators">
        <div class="trust-item">
          <div class="trust-number">1M+</div>
          <div class="trust-label">Happy Stays</div>
        </div>
        <div class="trust-item">
          <div class="trust-number">50K+</div>
          <div class="trust-label">Trusted Hosts</div>
        </div>
        <div class="trust-item">
          <div class="trust-number">24/7</div>
          <div class="trust-label">Support</div>
        </div>
      </div>
    </section>

    <!-- Property Categories -->
    <section class="categories-section">
      <div class="container">
        <h2 class="section-title">Explore by Property Type</h2>
        <div class="categories-grid">
          <div 
            v-for="category in propertyCategories" 
            :key="category.id"
            class="category-card"
            @click="filterByCategory(category.type)"
          >
            <div class="category-icon">
              <span>{{ category.emoji }}</span>
            </div>
            <h3>{{ category.name }}</h3>
          </div>
        </div>
      </div>
    </section>

    <!-- Featured Listings -->
    <section class="featured-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">Featured Listings</h2>
          <router-link to="/listings" class="view-all">View all</router-link>
        </div>
        <div class="listings-grid">
          <div 
            v-for="listing in featuredListings" 
            :key="listing.id"
            class="listing-card"
          >
            <div class="listing-image">
              <img :src="listing.image" :alt="listing.title">
              <button class="favorite-btn">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                  <path d="M12 21.35L10.55 20.03C5.4 15.36 2 12.28 2 8.5C2 5.42 4.42 3 7.5 3C9.24 3 10.91 3.81 12 5.09C13.09 3.81 14.76 3 16.5 3C19.58 3 22 5.42 22 8.5C22 12.28 18.6 15.36 13.45 20.04L12 21.35Z" fill="currentColor"/>
                </svg>
              </button>
            </div>
            <div class="listing-info">
              <h3 class="listing-title">{{ listing.title }}</h3>
              <p class="listing-location">{{ listing.location }}</p>
              <div class="listing-rating">
                <span class="stars">★★★★★</span>
                <span class="rating-number">{{ listing.rating }}</span>
              </div>
              <div class="listing-price">
                <span class="price">${{ listing.price }}</span>
                <span class="period">night</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Host CTA Section -->
    <section class="host-cta-section">
      <div class="container">
        <div class="host-cta-content">
          <div class="host-text">
            <h2>Become a Stayhapa Host</h2>
            <p>Earn extra income by sharing your space with travelers from around the world.</p>
            <!-- FIXED: Changed from /become-host to /get-started?type=host -->
            <router-link to="/get-started?type=host" class="cta-button">Get Started</router-link>
          </div>
          <div class="host-stats">
            <div class="stat-item">
              <div class="stat-number">$2,500</div>
              <div class="stat-label">Average annual earnings</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">85%</div>
              <div class="stat-label">Occupancy rate</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Footer Section -->
    <footer class="main-footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-section">
            <h3>Stayhapa</h3>
            <p>Find your perfect stay experience with unique accommodations worldwide.</p>
          </div>
          <div class="footer-section">
            <h4>For Travelers</h4>
            <router-link to="/get-started" class="footer-link">Get Started</router-link>
            <router-link to="/listings" class="footer-link">Browse Listings</router-link>
            <router-link to="/login" class="footer-link">Traveler Login</router-link>
          </div>
          <div class="footer-section">
            <h4>For Hosts</h4>
            <router-link to="/get-started?type=host" class="footer-link">Become a Host</router-link>
            <router-link to="/host-guide" class="footer-link">Hosting Guide</router-link>
            <router-link to="/login" class="footer-link">Host Login</router-link>
          </div>
          <div class="footer-section">
            <h4>Support</h4>
            <router-link to="/help" class="footer-link">Help Center</router-link>
            <router-link to="/safety" class="footer-link">Safety</router-link>
            <router-link to="/contact" class="footer-link">Contact Us</router-link>
          </div>
        </div>
        <div class="footer-bottom">
          <p>&copy; 2024 Stayhapa. All rights reserved.</p>
        </div>
      </div>
    </footer>

    <!-- Guests Modal -->
    <div v-if="showGuestsModal" class="modal-overlay" @click="showGuestsModal = false">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>Guests</h3>
          <button class="close-btn" @click="showGuestsModal = false">×</button>
        </div>
        <div class="guests-controls">
          <div class="guest-control">
            <span>Adults</span>
            <div class="counter">
              <button @click="decrementAdults">−</button>
              <span>{{ adultsCount }}</span>
              <button @click="incrementAdults">+</button>
            </div>
          </div>
          <div class="guest-control">
            <span>Children</span>
            <div class="counter">
              <button @click="decrementChildren">−</button>
              <span>{{ childrenCount }}</span>
              <button @click="incrementChildren">+</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      activeTab: 'stays',
      searchLocation: '',
      checkInDate: '',
      checkOutDate: '',
      showLocationSuggestions: false,
      showGuestsModal: false,
      adultsCount: 1,
      childrenCount: 0,
      propertyCategories: [
        { id: 1, name: 'Apartments', type: 'apartment', emoji: '🏢' },
        { id: 2, name: 'Villas', type: 'villa', emoji: '🏡' },
        { id: 3, name: 'Beach Houses', type: 'beach_house', emoji: '🏖️' },
        { id: 4, name: 'Cabins', type: 'cabin', emoji: '🏕️' },
        { id: 5, name: 'Luxury', type: 'luxury', emoji: '⭐' },
        { id: 6, name: 'Unique Stays', type: 'unique', emoji: '🎪' }
      ],
      featuredListings: [
        {
          id: 1,
          title: 'Modern Apartment in City Center',
          location: 'Nairobi, Kenya',
          rating: 4.9,
          price: 85,
          image: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400'
        },
        {
          id: 2,
          title: 'Beachfront Villa with Private Pool',
          location: 'Mombasa, Kenya',
          rating: 4.8,
          price: 120,
          image: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=400'
        },
        {
          id: 3,
          title: 'Luxury Mountain Cabin',
          location: 'Mount Kenya',
          rating: 4.7,
          price: 95,
          image: 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=400'
        },
        {
          id: 4,
          title: 'Traditional Swahili House',
          location: 'Lamu, Kenya',
          rating: 4.9,
          price: 65,
          image: 'https://images.unsplash.com/photo-1582268611958-ebfd161ef9cf?w=400'
        }
      ]
    }
  },
  computed: {
    guestsCount() {
      return this.adultsCount + this.childrenCount;
    }
  },
  methods: {
    setActiveTab(tab) {
      this.activeTab = tab;
    },
    handleSearch() {
      const searchParams = {
        location: this.searchLocation,
        checkIn: this.checkInDate,
        checkOut: this.checkOutDate,
        guests: this.guestsCount
      };
      console.log('Search initiated:', searchParams);
      this.$router.push({
        path: '/listings',
        query: searchParams
      });
    },
    filterByCategory(category) {
      this.$router.push({
        path: '/listings',
        query: { category }
      });
    },
    incrementAdults() {
      this.adultsCount++;
    },
    decrementAdults() {
      if (this.adultsCount > 1) {
        this.adultsCount--;
      }
    },
    incrementChildren() {
      this.childrenCount++;
    },
    decrementChildren() {
      if (this.childrenCount > 0) {
        this.childrenCount--;
      }
    }
  }
}
</script>

<style scoped>
.home-page {
  min-height: 100vh;
}

/* Hero Section */
.hero-section {
  min-height: 100vh;
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), 
              url('@/assets/hero.jpeg') center/cover no-repeat;
  display: flex;
  flex-direction: column;
  color: white;
  position: relative;
}

/* Navigation */
.navbar {
  padding: 1rem 2rem;
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.nav-logo h2 {
  color: white;
  font-size: 1.8rem;
  font-weight: bold;
  margin: 0;
  text-decoration: none;
}

.nav-logo a {
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: 500;
  transition: opacity 0.3s;
}

.nav-link:hover {
  opacity: 0.8;
}

.signup-btn {
  background: white;
  color: #333 !important;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-weight: 600;
}

/* Hero Content */
.hero-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.hero-subtitle {
  font-size: 1.3rem;
  margin-bottom: 3rem;
  opacity: 0.9;
}

/* Search Widget */
.search-widget {
  background: white;
  border-radius: 16px;
  padding: 1.5rem;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 800px;
}

.search-tabs {
  display: flex;
  margin-bottom: 1.5rem;
  border-bottom: 1px solid #e0e0e0;
}

.search-tab {
  padding: 0.75rem 1.5rem;
  background: none;
  border: none;
  font-size: 1rem;
  color: #666;
  cursor: pointer;
  border-bottom: 2px solid transparent;
}

.search-tab.active {
  color: #0F4C81;
  border-bottom-color: #0F4C81;
  font-weight: 600;
}

.search-form {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr auto;
  gap: 1rem;
  align-items: end;
}

.search-field {
  display: flex;
  flex-direction: column;
  position: relative;
}

.search-field label {
  font-size: 0.8rem;
  color: #666;
  margin-bottom: 0.5rem;
  font-weight: 600;
}

.search-field input,
.guests-selector {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  background: white;
  cursor: pointer;
}

.guests-selector {
  display: flex;
  align-items: center;
}

.suggestions-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  margin-top: 0.25rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.suggestion-item {
  padding: 0.75rem 1rem;
  cursor: pointer;
  border-bottom: 1px solid #f0f0f0;
}

.suggestion-item:hover {
  background: #f8f9fa;
}

.suggestion-item:last-child {
  border-bottom: none;
}

.search-btn {
  background: #0F4C81;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0.75rem 1.5rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-weight: 600;
  transition: background-color 0.3s;
}

.search-btn:hover {
  background: #0a3a6b;
}

/* Trust Indicators */
.trust-indicators {
  display: flex;
  justify-content: center;
  gap: 3rem;
  padding: 2rem;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

.trust-item {
  text-align: center;
}

.trust-number {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 0.25rem;
}

.trust-label {
  font-size: 0.9rem;
  opacity: 0.9;
}

/* Categories Section */
.categories-section {
  padding: 4rem 2rem;
  background: white;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.section-title {
  font-size: 2.5rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 3rem;
  color: #333;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 1.5rem;
}

.category-card {
  text-align: center;
  padding: 1.5rem 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
}

.category-card:hover {
  border-color: #0F4C81;
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(15, 76, 129, 0.1);
}

.category-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.category-card h3 {
  margin: 0;
  color: #333;
  font-weight: 600;
}

/* Featured Listings */
.featured-section {
  padding: 4rem 2rem;
  background: #f8f9fa;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.view-all {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

.listings-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
}

.listing-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
  cursor: pointer;
}

.listing-card:hover {
  transform: translateY(-8px);
}

.listing-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.listing-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.favorite-btn {
  position: absolute;
  top: 0.75rem;
  right: 0.75rem;
  background: rgba(255, 255, 255, 0.9);
  border: none;
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #666;
}

.favorite-btn:hover {
  color: #ff385c;
}

.listing-info {
  padding: 1.5rem;
}

.listing-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.listing-location {
  color: #666;
  margin-bottom: 0.75rem;
}

.listing-rating {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.stars {
  color: #0F4C81;
}

.rating-number {
  color: #666;
  font-weight: 500;
}

.listing-price {
  display: flex;
  align-items: baseline;
  gap: 0.25rem;
}

.price {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.period {
  color: #666;
}

/* Host CTA Section */
.host-cta-section {
  padding: 4rem 2rem;
  background: #0F4C81;
  color: white;
}

.host-cta-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 4rem;
  align-items: center;
}

.host-text h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.host-text p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.9;
}

.cta-button {
  background: white;
  color: #0F4C81;
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  display: inline-block;
  transition: transform 0.3s;
}

.cta-button:hover {
  transform: translateY(-2px);
}

.host-stats {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.stat-label {
  opacity: 0.9;
}

/* Footer Styles */
.main-footer {
  background: #333;
  color: white;
  padding: 3rem 2rem 1rem;
}

.footer-content {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 2rem;
  margin-bottom: 2rem;
}

.footer-section h3 {
  color: white;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}

.footer-section h4 {
  color: white;
  margin-bottom: 1rem;
  font-size: 1.1rem;
}

.footer-section p {
  opacity: 0.8;
  line-height: 1.6;
}

.footer-link {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  transition: color 0.3s;
  display: block;
  margin-bottom: 0.5rem;
}

.footer-link:hover {
  color: white;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding-top: 2rem;
  text-align: center;
  opacity: 0.7;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 16px;
  padding: 2rem;
  width: 90%;
  max-width: 400px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
}

.guests-controls {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.guest-control {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.counter {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.counter button {
  background: #f8f9fa;
  border: 1px solid #e0e0e0;
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.counter button:hover {
  background: #e9ecef;
}

/* Responsive Design */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2.5rem;
  }
  
  .hero-subtitle {
    font-size: 1.1rem;
  }
  
  .search-form {
    grid-template-columns: 1fr;
  }
  
  .trust-indicators {
    flex-direction: column;
    gap: 1.5rem;
  }
  
  .categories-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .host-cta-content {
    grid-template-columns: 1fr;
    gap: 2rem;
    text-align: center;
  }
  
  .nav-links {
    gap: 1rem;
  }
  
  .nav-link {
    font-size: 0.9rem;
  }
  
  .footer-content {
    grid-template-columns: 1fr;
    gap: 2rem;
    text-align: center;
  }
}

@media (max-width: 480px) {
  .navbar {
    padding: 1rem;
  }
  
  .hero-title {
    font-size: 2rem;
  }
  
  .categories-grid {
    grid-template-columns: 1fr;
  }
}
</style>