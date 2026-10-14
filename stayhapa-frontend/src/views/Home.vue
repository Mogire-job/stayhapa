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
            <router-link to="/listings" class="nav-link">Explore stays</router-link>
            <router-link to="/signup?userType=host" class="nav-link">Become a host</router-link>
            <router-link to="/login" class="nav-link">Login</router-link>
            <router-link to="/signup?userType=guest" class="nav-link signup-btn">Join Stayhapa</router-link>
          </div>
        </div>
      </nav>

      <!-- Hero Content -->
      <div class="hero-slideshow">
        <div 
          v-for="(img, index) in heroImages" 
          :key="index"
          class="hero-slide"
          :class="{ active: currentSlide === index }"
          :style="{ backgroundImage: `url(${img})` }"
        ></div>
        <div class="hero-overlay"></div>
      </div>
      
      <div class="hero-content">
        <!-- Animated Logo -->
        <div class="hero-logo">
          <span class="logo-text">Stay</span><span class="logo-accent">hapa</span>
        </div>
        
        <div class="hero-text">
          <p class="hero-eyebrow">A little closer to feeling at home</p>
          <h1 class="hero-title">Find a stay that feels like <em>yours.</em></h1>
          <p class="hero-subtitle">Thoughtful spaces, local hosts, and room to make the trip your own.</p>
          <div class="hero-buttons">
            <router-link to="/listings" class="btn-primary">Explore available stays <span aria-hidden="true">→</span></router-link>
            <router-link to="/signup?userType=host" class="btn-secondary">Share your space</router-link>
          </div>
        </div>
      </div>

      <!-- Trust Indicators -->
      <div class="trust-indicators">
        <div class="trust-item">
          <div class="trust-number">Local</div>
          <div class="trust-label">Kenyan stays</div>
        </div>
        <div class="trust-item">
          <div class="trust-number">Real</div>
          <div class="trust-label">Host-published homes</div>
        </div>
        <div class="trust-item">
          <div class="trust-number">Two</div>
          <div class="trust-label">Separate guest & host spaces</div>
        </div>
      </div>
    </section>

    <!-- How It Works -->
    <section class="how-it-works">
      <div class="container">
        <h2 class="section-title">How Stayhapa Works</h2>
        <div class="steps-grid">
          <div class="step-card">
            <div class="step-icon">
              <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <circle cx="12" cy="7" r="4"></circle>
              </svg>
            </div>
            <h3>Create Account</h3>
            <p>Choose the way you want to use Stayhapa: explore as a guest or publish as a host.</p>
          </div>
          <div class="step-card">
            <div class="step-icon">
              <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8"></circle>
                <path d="M21 21l-4.35-4.35"></path>
              </svg>
            </div>
            <h3>Explore stays</h3>
            <p>Browse active homes and see the details, amenities, and nightly price.</p>
          </div>
          <div class="step-card">
            <div class="step-icon">
              <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                <polyline points="22 4 12 14.01 9 11.01"></polyline>
              </svg>
            </div>
            <h3>Host your own</h3>
            <p>Have a place to share? Create a host account and publish a listing.</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Property Categories -->
    <section class="categories-section">
      <div class="container">
        <h2 class="section-title">Find your kind of getaway</h2>
        <div class="categories-grid">
          <router-link
            v-for="category in propertyCategories" 
            :key="category.id"
            :to="{ path: '/listings', query: { category: category.type } }"
            class="category-card"
          >
            <div class="category-icon">
              <span>{{ category.emoji }}</span>
            </div>
            <h3>{{ category.name }}</h3>
          </router-link>
        </div>
      </div>
    </section>

    <!-- Featured Listings -->
    <section id="featured-stays" class="featured-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">Featured Listings</h2>
          <router-link to="/listings" class="view-all">View all</router-link>
        </div>
        <div class="listings-grid">
          <router-link
            v-for="listing in featuredListings" 
            :key="listing.id"
            :to="`/property/${listing.id}`"
            class="listing-card"
          >
            <div class="listing-image">
              <img v-if="listing.image" :src="listing.image" :alt="listing.title">
              <div v-else class="home-image-placeholder">A new stay to discover</div>
            </div>
            <div class="listing-info">
              <h3 class="listing-title">{{ listing.title }}</h3>
              <p class="listing-location">{{ listing.location }}</p>
              <div class="listing-price">
                <span class="price">KSh {{ Number(listing.price).toLocaleString() }}</span>
                <span class="period">night</span>
              </div>
              <span class="details-link">View stay details <span aria-hidden="true">↗</span></span>
            </div>
          </router-link>
        </div>
        <p v-if="featuredLoading" class="featured-message" role="status">Finding lovely stays for you…</p>
        <p v-else-if="featuredError" class="featured-message" role="alert">{{ featuredError }}</p>
        <p v-else-if="featuredListings.length === 0" class="featured-message">
          No stays are published yet. Check back soon—or <router-link to="/signup?userType=host">be the first to host</router-link>.
        </p>
      </div>
    </section>

    <!-- Host CTA Section -->
    <section class="host-cta-section">
      <div class="container">
        <div class="host-cta-content">
          <div class="host-text">
            <h2>Become a Stayhapa Host</h2>
            <p>Welcome travelers into a place you love. Create a host account, publish your space, and manage your listing in your own dashboard.</p>
            <router-link to="/signup?userType=host" class="cta-button">Become a host <span aria-hidden="true">→</span></router-link>
          </div>
          <div class="host-stats">
            <span class="host-orbit host-orbit-one" aria-hidden="true"></span>
            <span class="host-orbit host-orbit-two" aria-hidden="true"></span>
            <div class="host-note">
              <span class="host-note-icon" aria-hidden="true">⌂</span>
              <p>Your space has a story.</p>
              <span>Give it a place to be found.</span>
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
            <p>Stay a little closer to the places and people that make a trip memorable.</p>
          </div>
          <div class="footer-section">
            <h4>For Travelers</h4>
            <router-link to="/signup?userType=guest" class="footer-link">Create a guest account</router-link>
            <router-link to="/listings" class="footer-link">Browse Listings</router-link>
            <router-link to="/login" class="footer-link">Traveler Login</router-link>
          </div>
          <div class="footer-section">
            <h4>For Hosts</h4>
            <router-link to="/signup?userType=host" class="footer-link">Become a Host</router-link>
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
          <p>&copy; {{ new Date().getFullYear() }} Stayhapa. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import propertyService from '@/services/propertyService';

export default {
  name: 'HomePage',
  data() {
    return {
      currentSlide: 0,
      slideInterval: null,
      propertyCategories: [
        { id: 1, name: 'Apartments', type: 'apartment', emoji: '▦' },
        { id: 2, name: 'Villas', type: 'villa', emoji: '⌂' },
        { id: 3, name: 'Houses', type: 'house', emoji: '⌂' },
        { id: 4, name: 'Cabins', type: 'cabin', emoji: '⌑' },
        { id: 5, name: 'Cottages', type: 'cottage', emoji: '⌂' },
        { id: 6, name: 'Studios', type: 'studio', emoji: '▤' }
      ],
      featuredListings: [],
      featuredLoading: true,
      featuredError: ''
    }
  },
  computed: {
    heroImages() {
      return [
        'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=1800&q=85',
        'https://images.unsplash.com/photo-1582268611958-ebfd161ef9cf?w=1800&q=85',
        'https://images.unsplash.com/photo-1613977257363-707ba9348227?w=1800&q=85',
        'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=1800&q=85'
      ];
    }
  },
  mounted() {
    this.loadFeaturedListings();
    this.slideInterval = setInterval(() => {
      this.currentSlide = (this.currentSlide + 1) % this.heroImages.length;
    }, 5000);
  },
  beforeUnmount() {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
  },
  methods: {
    async loadFeaturedListings() {
      this.featuredLoading = true;
      this.featuredError = '';
      try {
        const properties = await propertyService.getAllProperties();
        this.featuredListings = properties.filter(property => property.active).slice(0, 4).map(property => ({
          id: property.id,
          title: property.title,
          location: [property.city, property.country].filter(Boolean).join(', '),
          price: property.pricePerNight,
          image: property.images?.[0] || ''
        }));
      } catch (error) {
        console.error('Could not load featured stays:', error);
        this.featuredError = error.message || 'Featured stays are temporarily unavailable.';
      } finally {
        this.featuredLoading = false;
      }
    },
  }
}
</script>

<style scoped>
.home-page {
  min-height: 100vh;
}

/* Hero Section with Slideshow */
.hero-section {
  position: relative;
  min-height: 85vh;
  display: flex;
  flex-direction: column;
}

.hero-slideshow {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.hero-slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  opacity: 0;
  transition: opacity 1s ease-in-out;
}

.hero-slide.active {
  opacity: 1;
}

.hero-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.5));
}

/* Navigation */
.navbar {
  padding: 1rem 2rem;
  background: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);
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
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: all 0.3s;
}

.nav-link:hover {
  background: rgba(255, 255, 255, 0.15);
}

.signup-btn {
  background: white;
  color: #222 !important;
  font-weight: 600;
}

.signup-btn:hover {
  background: #f0f0f0 !important;
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
  position: relative;
  z-index: 2;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
  line-height: 1.2;
  color: white;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

.hero-logo {
  font-size: 4rem;
  font-weight: 800;
  margin-bottom: 1.5rem;
  animation: fadeInDown 1s ease-out;
}

.hero-logo .logo-text {
  color: white;
  text-shadow: 0 2px 10px rgba(0,0,0,0.3);
}

.hero-logo .logo-accent {
  background: linear-gradient(135deg, #FF385C 0%, #FF6B6B 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.hero-subtitle {
  font-size: 1.3rem;
  margin-bottom: 2rem;
  opacity: 0.9;
  color: white;
}

.hero-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.btn-primary {
  background: linear-gradient(135deg, #0F4C81 0%, #1a6fa8 100%);
  color: white;
  padding: 1rem 2.5rem;
  border-radius: 50px;
  text-decoration: none;
  font-weight: 600;
  font-size: 1rem;
  transition: all 0.3s;
  box-shadow: 0 4px 15px rgba(15, 76, 129, 0.4);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(15, 76, 129, 0.5);
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  color: white;
  padding: 1rem 2.5rem;
  border-radius: 50px;
  text-decoration: none;
  font-weight: 600;
  font-size: 1rem;
  transition: all 0.3s;
  border: 2px solid rgba(255, 255, 255, 0.3);
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
}

/* Search Widget */
.search-widget {
  background: white;
  border-radius: 32px;
  padding: 0.5rem;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
  width: 100%;
  max-width: 850px;
  overflow: hidden;
  display: flex;
  align-items: center;
}

.search-widget .search-form {
  display: flex;
  align-items: center;
  gap: 0;
  flex: 1;
}

.search-widget .search-field {
  flex: 1;
  padding: 0.75rem 1.25rem;
  border-right: 1px solid #e0e0e0;
  position: relative;
}

.search-widget .search-field:last-of-type {
  border-right: none;
}

.search-widget .search-field label {
  display: block;
  font-size: 0.75rem;
  font-weight: 700;
  color: #333;
  margin-bottom: 0.25rem;
}

.search-widget .search-field input {
  border: none;
  outline: none;
  width: 100%;
  font-size: 0.9rem;
  padding: 0;
  color: #666;
}

.search-widget .search-field input::placeholder {
  color: #999;
}

.search-widget .guests-selector {
  padding: 0.75rem 1.25rem;
  font-size: 0.9rem;
  color: #666;
  border-right: 1px solid #e0e0e0;
}

.search-widget .search-btn {
  background: #FF385C;
  color: white;
  border: none;
  border-radius: 24px;
  padding: 0.875rem 1.5rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-weight: 600;
  transition: all 0.3s;
  margin-left: 0.5rem;
  white-space: nowrap;
}

.search-widget .search-btn:hover {
  background: #e0314c;
  transform: scale(1.02);
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

/* How It Works */
.how-it-works {
  padding: 5rem 2rem;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.how-it-works .section-title {
  margin-bottom: 3rem;
}

.steps-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2.5rem;
  max-width: 1000px;
  margin: 0 auto;
}

.step-card {
  text-align: center;
  padding: 2rem;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s;
}

.step-card:hover {
  transform: translateY(-8px);
}

.step-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 1.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #0F4C81 0%, #1a6fa8 100%);
  border-radius: 50%;
  color: white;
}

.step-card h3 {
  font-size: 1.25rem;
  font-weight: 700;
  color: #1a1a2e;
  margin-bottom: 0.75rem;
}

.step-card p {
  color: #6b7280;
  font-size: 0.95rem;
  line-height: 1.6;
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
  
  .hero-logo {
    font-size: 2.5rem;
  }
  
  .hero-subtitle {
    font-size: 1.1rem;
  }
  
  .hero-buttons {
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .btn-primary,
  .btn-secondary {
    padding: 0.875rem 2rem;
    font-size: 0.95rem;
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
  
  .steps-grid {
    grid-template-columns: 1fr;
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

.home-page {
  --home-ink: #1f332e;
  --home-muted: #687873;
  --home-green: #1f6255;
  --home-coral: #dc8062;
  color: var(--home-ink);
  background: #fbfaf7;
  font-family: Inter, "Avenir Next", Avenir, sans-serif;
}

.hero-section {
  min-height: min(850px, 100svh);
  overflow: hidden;
  isolation: isolate;
  background: #183b35;
}

.hero-slideshow::after {
  position: absolute;
  inset: 0;
  content: "";
  background:
    linear-gradient(90deg, rgb(13 33 29 / 70%) 0%, rgb(13 33 29 / 36%) 55%, rgb(13 33 29 / 12%) 100%),
    linear-gradient(0deg, rgb(13 33 29 / 42%), transparent 52%);
}

.hero-slide {
  transform: scale(1);
  transition: opacity 1.2s ease;
}

.hero-slide.active {
  animation: hero-drift 9s ease-out both;
}

.hero-overlay {
  display: none;
}

.navbar {
  position: relative;
  z-index: 4;
  padding: 1.15rem clamp(1.2rem, 5vw, 5rem);
  background: rgb(16 39 35 / 23%);
  border-bottom: 1px solid rgb(255 255 255 / 17%);
  backdrop-filter: blur(18px);
}

.nav-container {
  max-width: 1400px;
}

.nav-logo h2 {
  font-size: 1.65rem;
  letter-spacing: -0.06em;
}

.nav-links {
  gap: clamp(0.2rem, 1.4vw, 1.4rem);
}

.nav-link {
  font-size: 0.92rem;
}

.signup-btn {
  padding: 0.72rem 1.15rem;
  border-radius: 999px;
  background: #f3c58f;
  color: #253b34 !important;
  box-shadow: 0 8px 24px rgb(10 30 25 / 18%);
}

.signup-btn:hover {
  background: #ffdaa7 !important;
  transform: translateY(-2px);
}

.hero-content {
  width: min(100%, 1240px);
  min-height: 560px;
  align-items: flex-start;
  text-align: left;
  padding: clamp(4rem, 11vh, 8rem) clamp(1.5rem, 8vw, 7rem) 4rem;
}

.hero-logo {
  margin-bottom: 1.2rem;
  font-size: clamp(2.8rem, 6vw, 4.8rem);
  line-height: 0.95;
  animation: soft-rise 800ms cubic-bezier(.2,.8,.2,1) both;
}

.hero-logo .logo-accent {
  background: linear-gradient(120deg, #f5c48c, #f28e70);
  -webkit-background-clip: text;
  background-clip: text;
}

.hero-text {
  max-width: 700px;
  animation: soft-rise 850ms 120ms cubic-bezier(.2,.8,.2,1) both;
}

.hero-eyebrow {
  margin: 0 0 1rem;
  color: #f4c995;
  font-size: 0.82rem;
  font-weight: 800;
  letter-spacing: 0.16em;
  text-transform: uppercase;
}

.hero-title {
  max-width: 730px;
  margin: 0;
  font-size: clamp(3.1rem, 7.3vw, 6.3rem);
  font-weight: 700;
  letter-spacing: -0.075em;
  line-height: 0.99;
  text-wrap: balance;
  text-shadow: 0 8px 40px rgb(0 0 0 / 25%);
}

.hero-title em {
  color: #f4c995;
  font-family: Georgia, "Times New Roman", serif;
  font-weight: 500;
}

.hero-subtitle {
  max-width: 540px;
  margin: 1.35rem 0 2rem;
  color: rgb(255 255 255 / 87%);
  font-size: clamp(1.05rem, 1.8vw, 1.26rem);
  line-height: 1.65;
}

.hero-buttons {
  justify-content: flex-start;
  flex-wrap: wrap;
  gap: 0.8rem;
  animation: soft-rise 850ms 240ms cubic-bezier(.2,.8,.2,1) both;
}

.btn-primary,
.btn-secondary {
  min-height: 54px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.65rem;
  padding: 0.95rem 1.45rem;
  border-radius: 999px;
  font-size: 0.96rem;
  font-weight: 750;
}

.btn-primary {
  position: relative;
  overflow: hidden;
  background: linear-gradient(115deg, #e99570, #f0ba7d);
  color: #273c35;
  box-shadow: 0 14px 36px rgb(5 20 16 / 27%);
}

.btn-primary::after {
  position: absolute;
  top: -60%;
  left: -45%;
  width: 36%;
  height: 220%;
  content: "";
  transform: rotate(25deg);
  background: rgb(255 255 255 / 43%);
  transition: left 550ms ease;
}

.btn-primary:hover::after {
  left: 125%;
}

.btn-primary:hover,
.btn-secondary:hover {
  transform: translateY(-3px);
}

.btn-secondary {
  border: 1px solid rgb(255 255 255 / 48%);
  background: rgb(255 255 255 / 12%);
  color: white;
  backdrop-filter: blur(12px);
}

.trust-indicators {
  position: relative;
  z-index: 2;
  width: min(880px, calc(100% - 3rem));
  margin: 0 auto 2rem;
  padding: 1rem 1.2rem;
  border: 1px solid rgb(255 255 255 / 24%);
  border-radius: 18px;
  background: rgb(19 46 40 / 42%);
  backdrop-filter: blur(18px);
  animation: soft-rise 900ms 360ms both;
}

.trust-item {
  flex: 1;
  padding: 0.2rem 1rem;
  border-right: 1px solid rgb(255 255 255 / 22%);
}

.trust-item:last-child {
  border: 0;
}

.trust-number {
  color: #f4c995;
  font-size: 1rem;
  font-weight: 750;
}

.trust-label {
  margin-top: 0.25rem;
  color: rgb(255 255 255 / 82%);
  font-size: 0.82rem;
}

.how-it-works,
.categories-section,
.featured-section {
  padding: clamp(4rem, 8vw, 7rem) 0;
}

.how-it-works {
  background:
    radial-gradient(ellipse at 12% 0%, rgb(229 189 146 / 18%), transparent 44%),
    #fbfaf7;
}

.section-title {
  color: var(--home-ink);
  font-size: clamp(2rem, 4vw, 3.1rem);
  font-weight: 700;
  letter-spacing: -0.06em;
  line-height: 1.1;
}

.step-card,
.category-card {
  border: 1px solid #e9e6de;
  border-radius: 20px;
  background: rgb(255 255 255 / 82%);
  box-shadow: 0 12px 34px rgb(32 48 41 / 5%);
  transition: transform 300ms cubic-bezier(.2,.8,.2,1), box-shadow 300ms ease, border-color 300ms ease;
}

.step-card {
  animation: soft-rise 650ms both;
}

.listing-card:nth-child(2) { animation-delay: 80ms; }
.listing-card:nth-child(3) { animation-delay: 160ms; }
.listing-card:nth-child(4) { animation-delay: 240ms; }

.step-card:nth-child(2) { animation-delay: 100ms; }
.step-card:nth-child(3) { animation-delay: 200ms; }

.step-card:hover,
.category-card:hover {
  transform: translateY(-7px);
  border-color: #d6aa7d;
  box-shadow: 0 20px 44px rgb(32 48 41 / 11%);
}

.step-icon {
  color: var(--home-green);
}

.categories-section {
  background: #f2f0e9;
}

.category-card {
  cursor: pointer;
}

.category-icon {
  width: 56px;
  height: 56px;
  display: grid;
  place-items: center;
  border-radius: 17px;
  background: #e8f0e9;
  color: var(--home-green);
  font-size: 1.8rem;
}

.featured-section {
  background: #fbfaf7;
}

.featured-section .section-header {
  align-items: end;
  margin-bottom: 2rem;
}

.view-all,
.details-link {
  color: var(--home-green);
  font-weight: 750;
}

.listings-grid {
  gap: 1.25rem;
}

.listing-card {
  overflow: hidden;
  border: 1px solid #e8e6df;
  border-radius: 19px;
  background: white;
  box-shadow: 0 12px 34px rgb(32 48 41 / 6%);
  color: inherit;
  text-decoration: none;
  transition: transform 320ms cubic-bezier(.2,.8,.2,1), box-shadow 320ms ease;
  animation: soft-rise 650ms both;
}

.listing-card:hover {
  transform: translateY(-7px);
  box-shadow: 0 24px 50px rgb(32 48 41 / 14%);
}

.listing-image {
  height: 235px;
  overflow: hidden;
  background: #e9eee8;
}

.listing-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 700ms cubic-bezier(.2,.8,.2,1);
}

.listing-card:hover .listing-image img {
  transform: scale(1.055);
}

.home-image-placeholder {
  height: 100%;
  display: grid;
  place-items: center;
  color: var(--home-muted);
}

.listing-info {
  padding: 1.2rem;
}

.listing-title {
  color: var(--home-ink);
  font-size: 1.08rem;
  letter-spacing: -0.025em;
}

.listing-location,
.period {
  color: var(--home-muted);
}

.listing-price {
  margin: 1rem 0 0.75rem;
}

.listing-price .price {
  color: var(--home-ink);
}

.details-link {
  display: flex;
  justify-content: space-between;
  padding-top: 0.75rem;
  border-top: 1px solid #eeece6;
  font-size: 0.9rem;
}

.featured-message {
  margin: 1.5rem 0 0;
  color: var(--home-muted);
  line-height: 1.6;
}

.featured-message a {
  color: var(--home-green);
  font-weight: 750;
}

.host-cta-section {
  overflow: hidden;
  padding: clamp(3.5rem, 7vw, 6rem) 0;
  background: linear-gradient(120deg, #193e36, #285d4e 65%, #346d59);
}

.host-cta-content {
  grid-template-columns: minmax(0, 1.1fr) minmax(260px, 0.9fr);
  gap: 3rem;
  align-items: center;
}

.host-text h2 {
  font-size: clamp(2.3rem, 5vw, 4rem);
  letter-spacing: -0.065em;
  line-height: 1.04;
}

.host-text p {
  max-width: 570px;
  color: rgb(255 255 255 / 78%);
  font-size: 1.08rem;
  line-height: 1.7;
}

.cta-button {
  border-radius: 999px;
  background: #f2bd82;
  color: #243c34;
  box-shadow: 0 12px 30px rgb(5 20 16 / 22%);
}

.cta-button:hover {
  transform: translateY(-3px);
  background: #ffd19a;
}

.host-stats {
  position: relative;
  min-height: 270px;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.host-orbit {
  position: absolute;
  width: 240px;
  height: 240px;
  border: 1px solid rgb(255 255 255 / 26%);
  border-radius: 50%;
  animation: orbit-breathe 5s ease-in-out infinite;
}

.host-orbit-two {
  width: 185px;
  height: 185px;
  animation-delay: -2.5s;
}

.host-note {
  position: relative;
  padding: 1.7rem;
  border: 1px solid rgb(255 255 255 / 30%);
  border-radius: 20px;
  background: rgb(255 255 255 / 11%);
  backdrop-filter: blur(14px);
  box-shadow: 0 24px 60px rgb(6 21 17 / 20%);
  animation: gentle-float 5s ease-in-out infinite;
}

.host-note-icon {
  color: #f4c995;
  font-size: 2.5rem;
}

.host-note p {
  margin: 0.8rem 0 0.25rem;
  color: white;
  font-size: 1.3rem;
  font-weight: 750;
}

.host-note > span:last-child {
  color: rgb(255 255 255 / 75%);
}

.main-footer {
  background: #172c27;
}

.footer-link:hover {
  color: #f4c995;
}

@keyframes soft-rise {
  from { opacity: 0; transform: translateY(22px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes hero-drift {
  from { transform: scale(1); }
  to { transform: scale(1.09); }
}

@keyframes gentle-float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-9px); }
}

@keyframes orbit-breathe {
  0%, 100% { transform: scale(0.94); opacity: 0.45; }
  50% { transform: scale(1.06); opacity: 1; }
}

@supports (animation-timeline: view()) {
  .step-card,
  .category-card,
  .listing-card,
  .host-cta-content {
    animation-name: scroll-reveal;
    animation-duration: 1ms;
    animation-timing-function: linear;
    animation-fill-mode: both;
    animation-timeline: view();
    animation-range: entry 0% entry 85%;
  }

  @keyframes scroll-reveal {
    from { opacity: 0.35; transform: translateY(26px); }
    to { opacity: 1; transform: translateY(0); }
  }
}

@media (max-width: 768px) {
  .hero-section {
    min-height: 760px;
    min-height: max(760px, 100svh);
  }

  .navbar {
    padding-inline: 1rem;
  }

  .nav-links {
    gap: 0.25rem;
  }

  .nav-link {
    padding: 0.48rem 0.6rem;
    font-size: 0.8rem;
  }

  .nav-links .nav-link:nth-child(2) {
    display: none;
  }

  .hero-content {
    min-height: 510px;
    padding: 4.5rem 1.35rem 3rem;
  }

  .hero-title {
    font-size: clamp(3rem, 12vw, 5rem);
  }

  .trust-indicators {
    width: calc(100% - 2rem);
  }

  .trust-item {
    padding-inline: 0.45rem;
  }

  .trust-label {
    font-size: 0.72rem;
  }

  .host-cta-content {
    grid-template-columns: 1fr;
  }

  .host-stats {
    min-height: 230px;
  }
}

@media (max-width: 480px) {
  .nav-logo h2 {
    font-size: 1.35rem;
  }

  .nav-links .nav-link:first-child {
    display: none;
  }

  .hero-content {
    min-height: 510px;
  }

  .hero-buttons {
    align-items: stretch;
    flex-direction: column;
    max-width: 330px;
  }

  .trust-indicators {
    gap: 0.25rem;
    padding: 0.8rem 0.35rem;
  }

  .trust-number {
    font-size: 0.9rem;
  }
}

@media (prefers-reduced-motion: reduce) {
  .home-page *,
  .home-page *::before,
  .home-page *::after {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    scroll-behavior: auto !important;
    transition-duration: 0.01ms !important;
  }
}
</style>