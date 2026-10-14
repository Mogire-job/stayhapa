<template>
  <div class="get-started-page">
    <!-- Navigation (same as landing page for consistency) -->
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

    <!-- Hero Section -->
    <section class="get-started-hero">
      <div class="container">
        <div class="hero-content">
          <h1 class="hero-title">Get Started with Stayhapa</h1>
          <p class="hero-subtitle">Join our community of travelers and hosts. Whether you're looking for the perfect stay or want to share your space, we've got you covered.</p>
          
          <!-- User Type Selection -->
          <div class="user-type-selector">
            <div 
              class="user-type-card"
              :class="{ active: selectedUserType === 'guest' }"
              @click="selectUserType('guest')"
            >
              <div class="user-icon">🧳</div>
              <h3>I'm a Traveler</h3>
              <p>Looking for unique accommodations</p>
            </div>
            <div 
              class="user-type-card"
              :class="{ active: selectedUserType === 'host' }"
              @click="selectUserType('host')"
            >
              <div class="user-icon">🏠</div>
              <h3>I'm a Host</h3>
              <p>Want to list my property</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Guest Flow Section -->
    <section v-if="selectedUserType === 'guest'" class="user-flow-section">
      <div class="container">
        <div class="flow-header">
          <h2>Start Your Journey as a Traveler</h2>
          <p>Follow these simple steps to find your perfect stay</p>
        </div>

        <div class="steps-grid">
          <div class="step-card">
            <div class="step-number">1</div>
            <div class="step-content">
              <h3>Create Your Account</h3>
              <p>Sign up in seconds with your email or social accounts</p>
            </div>
          </div>
          <div class="step-card">
            <div class="step-number">2</div>
            <div class="step-content">
              <h3>Search & Discover</h3>
              <p>Find unique accommodations that match your style and budget</p>
            </div>
          </div>
          <div class="step-card">
            <div class="step-number">3</div>
            <div class="step-content">
              <h3>Book Securely</h3>
              <p>Reserve with confidence using our secure payment system</p>
            </div>
          </div>
          <div class="step-card">
            <div class="step-number">4</div>
            <div class="step-content">
              <h3>Enjoy Your Stay</h3>
              <p>Create unforgettable memories and share your experience</p>
            </div>
          </div>
        </div>

        <!-- Guest Benefits -->
        <div class="benefits-section">
          <h3>Why Travel with Stayhapa?</h3>
          <div class="benefits-grid">
            <div class="benefit-card">
              <div class="benefit-icon">🔒</div>
              <h4>Secure Payments</h4>
              <p>Your payments are protected with industry-leading security</p>
            </div>
            <div class="benefit-card">
              <div class="benefit-icon">⭐</div>
              <h4>Verified Reviews</h4>
              <p>Read authentic reviews from real travelers</p>
            </div>
            <div class="benefit-card">
              <div class="benefit-icon">📞</div>
              <h4>24/7 Support</h4>
              <p>We're here to help whenever you need assistance</p>
            </div>
            <div class="benefit-card">
              <div class="benefit-icon">🌍</div>
              <h4>Global Community</h4>
              <p>Join millions of travelers worldwide</p>
            </div>
          </div>
        </div>

        <!-- Guest CTA -->
        <div class="cta-section">
          <div class="cta-content">
            <h2>Ready to Explore?</h2>
            <p>Join thousands of travelers discovering unique stays around the world</p>
            <div class="cta-buttons">
              <router-link to="/signup?type=guest" class="cta-button primary">Sign Up as Traveler</router-link>
              <router-link to="/listings" class="cta-button secondary">Browse Listings</router-link>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Host Flow Section -->
    <section v-if="selectedUserType === 'host'" class="user-flow-section">
      <div class="container">
        <div class="flow-header">
          <h2>Start Hosting with Stayhapa</h2>
          <p>Turn your space into income with our easy hosting process</p>
        </div>

        <div class="steps-grid">
          <div class="step-card">
            <div class="step-number">1</div>
            <div class="step-content">
              <h3>Create Host Profile</h3>
              <p>Set up your hosting account and verify your identity</p>
            </div>
          </div>
          <div class="step-card">
            <div class="step-number">2</div>
            <div class="step-content">
              <h3>List Your Property</h3>
              <p>Add photos, description, and set your availability</p>
            </div>
          </div>
          <div class="step-card">
            <div class="step-number">3</div>
            <div class="step-content">
              <h3>Manage Bookings</h3>
              <p>Accept requests, communicate with guests, and manage your calendar</p>
            </div>
          </div>
          <div class="step-card">
            <div class="step-number">4</div>
            <div class="step-content">
              <h3>Get Paid</h3>
              <p>Receive secure payments and track your earnings</p>
            </div>
          </div>
        </div>

        <!-- Host Benefits -->
        <div class="benefits-section">
          <h3>Why Host with Stayhapa?</h3>
          <div class="benefits-grid">
            <div class="benefit-card">
              <div class="benefit-icon">💰</div>
              <h4>Earn Extra Income</h4>
              <p>Turn your unused space into a source of revenue</p>
            </div>
            <div class="benefit-card">
              <div class="benefit-icon">🛡️</div>
              <h4>Host Protection</h4>
              <p>Our insurance and verification systems keep you secure</p>
            </div>
            <div class="benefit-card">
              <div class="benefit-icon">📊</div>
              <h4>Smart Tools</h4>
              <p>Use our dashboard to manage bookings and pricing</p>
            </div>
            <div class="benefit-card">
              <div class="benefit-icon">🌐</div>
              <h4>Global Reach</h4>
              <p>Connect with travelers from around the world</p>
            </div>
          </div>
        </div>

        <!-- Host Stats -->
        <div class="host-stats-section">
          <div class="stats-grid">
            <div class="stat-item">
              <div class="stat-number">$2,500+</div>
              <div class="stat-label">Average Annual Earnings</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">85%</div>
              <div class="stat-label">Average Occupancy Rate</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">24h</div>
              <div class="stat-label">Fast Payout Processing</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">50K+</div>
              <div class="stat-label">Active Hosts Worldwide</div>
            </div>
          </div>
        </div>

        <!-- Host CTA -->
        <div class="cta-section">
          <div class="cta-content">
            <h2>Ready to Start Hosting?</h2>
            <p>Join our community of successful hosts and start earning today</p>
            <div class="cta-buttons">
              <router-link to="/signup?type=host" class="cta-button primary">Become a Host</router-link>
              <router-link to="/host-guide" class="cta-button secondary">Learn More</router-link>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- FAQ Section -->
    <section class="faq-section">
      <div class="container">
        <h2>Frequently Asked Questions</h2>
        <div class="faq-grid">
          <div class="faq-item">
            <h4>How do I create an account?</h4>
            <p>Click the Sign Up button and choose whether you want to join as a traveler or host. The process takes less than 2 minutes.</p>
          </div>
          <div class="faq-item">
            <h4>Is my payment information secure?</h4>
            <p>Yes, we use industry-standard encryption and secure payment processing to protect your financial information.</p>
          </div>
          <div class="faq-item">
            <h4>How much does it cost to list my property?</h4>
            <p>Listing your property is completely free. We only charge a small commission when you receive a booking.</p>
          </div>
          <div class="faq-item">
            <h4>Can I contact hosts before booking?</h4>
            <p>Yes, our messaging system allows you to communicate with hosts to ask questions before making a reservation.</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'GetStartedPage',
  data() {
    return {
      selectedUserType: 'guest' // Default to guest
    }
  },
  methods: {
    selectUserType(type) {
      this.selectedUserType = type;
    }
  },
  mounted() {
    window.scrollTo({ top: 0, behavior: 'instant' });
    const type = this.$route.query.type;
    if (type && ['guest', 'host'].includes(type)) {
      this.selectedUserType = type;
    }
  }
}
</script>

<style scoped>
.get-started-page {
  min-height: 100vh;
  background: #ffffff;
}

/* Navigation - Reuse from landing page */
.navbar {
  padding: 1rem 2rem;
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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

.nav-link {
  color: #333;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s;
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

/* Hero Section */
.get-started-hero {
  padding: 4rem 2rem;
  background: linear-gradient(135deg, #0F4C81 0%, #1a6fb3 100%);
  color: white;
  text-align: center;
}

.hero-content {
  max-width: 800px;
  margin: 0 auto;
}

.hero-title {
  font-size: 3rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.hero-subtitle {
  font-size: 1.2rem;
  margin-bottom: 3rem;
  opacity: 0.9;
}

/* User Type Selector */
.user-type-selector {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  max-width: 600px;
  margin: 0 auto;
}

.user-type-card {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  padding: 2rem;
  border-radius: 16px;
  border: 2px solid transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  text-align: center;
}

.user-type-card:hover {
  background: rgba(255, 255, 255, 0.15);
  transform: translateY(-4px);
}

.user-type-card.active {
  background: rgba(255, 255, 255, 0.2);
  border-color: white;
  transform: translateY(-4px);
}

.user-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.user-type-card h3 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.user-type-card p {
  opacity: 0.9;
  margin: 0;
}

/* User Flow Section */
.user-flow-section {
  padding: 4rem 2rem;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.flow-header {
  text-align: center;
  margin-bottom: 4rem;
}

.flow-header h2 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 1rem;
}

.flow-header p {
  font-size: 1.2rem;
  color: #666;
}

/* Steps Grid */
.steps-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin-bottom: 4rem;
}

.step-card {
  background: white;
  padding: 2rem;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: relative;
  transition: transform 0.3s ease;
}

.step-card:hover {
  transform: translateY(-8px);
}

.step-number {
  width: 50px;
  height: 50px;
  background: #0F4C81;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  font-weight: bold;
  margin: 0 auto 1.5rem;
}

.step-content h3 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.step-content p {
  color: #666;
  line-height: 1.6;
}

/* Benefits Section */
.benefits-section {
  margin-bottom: 4rem;
}

.benefits-section h3 {
  text-align: center;
  font-size: 2rem;
  color: #333;
  margin-bottom: 3rem;
}

.benefits-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.benefit-card {
  text-align: center;
  padding: 2rem 1rem;
}

.benefit-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.benefit-card h4 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 1.2rem;
}

.benefit-card p {
  color: #666;
  line-height: 1.6;
}

/* Host Stats Section */
.host-stats-section {
  background: #f8f9fa;
  padding: 3rem 2rem;
  border-radius: 16px;
  margin-bottom: 4rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: bold;
  color: #0F4C81;
  margin-bottom: 0.5rem;
}

.stat-label {
  color: #666;
  font-weight: 500;
}

/* CTA Section */
.cta-section {
  background: linear-gradient(135deg, #0F4C81 0%, #1a6fb3 100%);
  color: white;
  padding: 4rem 2rem;
  border-radius: 16px;
  text-align: center;
}

.cta-content h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.cta-content p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.9;
}

.cta-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.cta-button {
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  transition: all 0.3s ease;
  display: inline-block;
}

.cta-button.primary {
  background: white;
  color: #0F4C81;
}

.cta-button.primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(255, 255, 255, 0.3);
}

.cta-button.secondary {
  background: transparent;
  color: white;
  border: 2px solid white;
}

.cta-button.secondary:hover {
  background: white;
  color: #0F4C81;
  transform: translateY(-2px);
}

/* FAQ Section */
.faq-section {
  padding: 4rem 2rem;
  background: #f8f9fa;
}

.faq-section h2 {
  text-align: center;
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 3rem;
}

.faq-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  max-width: 1000px;
  margin: 0 auto;
}

.faq-item {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.faq-item h4 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 1.1rem;
}

.faq-item p {
  color: #666;
  line-height: 1.6;
}

/* Responsive Design */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2.2rem;
  }
  
  .user-type-selector {
    grid-template-columns: 1fr;
  }
  
  .steps-grid {
    grid-template-columns: 1fr;
  }
  
  .benefits-grid {
    grid-template-columns: 1fr;
  }
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .cta-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .cta-button {
    width: 100%;
    max-width: 300px;
    text-align: center;
  }
  
  .nav-links {
    gap: 1rem;
  }
}

@media (max-width: 480px) {
  .navbar {
    padding: 1rem;
  }
  
  .hero-title {
    font-size: 1.8rem;
  }
  
  .flow-header h2 {
    font-size: 2rem;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .faq-grid {
    grid-template-columns: 1fr;
  }
}
</style>