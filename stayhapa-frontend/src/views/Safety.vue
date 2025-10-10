<template>
  <div class="safety-page">
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

    <div class="safety-container">
      <!-- Hero Section -->
      <section class="safety-hero">
        <div class="container">
          <h1>Your Safety is Our Priority</h1>
          <p>Learn about our safety measures and guidelines for a secure experience</p>
        </div>
      </section>

      <!-- Safety Overview -->
      <section class="safety-overview">
        <div class="container">
          <div class="overview-grid">
            <div class="overview-card">
              <div class="overview-icon">🔒</div>
              <h3>Secure Payments</h3>
              <p>All transactions are encrypted and processed securely</p>
            </div>
            <div class="overview-card">
              <div class="overview-icon">✅</div>
              <h3>Verified Users</h3>
              <p>Hosts and guests go through verification processes</p>
            </div>
            <div class="overview-card">
              <div class="overview-icon">🛡️</div>
              <h3>24/7 Support</h3>
              <p>Our safety team is available around the clock</p>
            </div>
            <div class="overview-card">
              <div class="overview-icon">📝</div>
              <h3>Clear Guidelines</h3>
              <p>Comprehensive safety policies for all users</p>
            </div>
          </div>
        </div>
      </section>

      <!-- Safety Guidelines -->
      <section class="safety-guidelines">
        <div class="container">
          <div class="guidelines-tabs">
            <button 
              class="tab-button" 
              :class="{ active: activeTab === 'travelers' }"
              @click="activeTab = 'travelers'"
            >
              For Travelers
            </button>
            <button 
              class="tab-button" 
              :class="{ active: activeTab === 'hosts' }"
              @click="activeTab = 'hosts'"
            >
              For Hosts
            </button>
          </div>

          <div class="guidelines-content">
            <!-- Travelers Guidelines -->
            <div v-if="activeTab === 'travelers'" class="tab-content">
              <div class="guideline-section">
                <h3>Before You Book</h3>
                <div class="guideline-list">
                  <div class="guideline-item">
                    <div class="guideline-number">1</div>
                    <div class="guideline-text">
                      <h4>Read Reviews Carefully</h4>
                      <p>Check previous guests' experiences and look for consistent positive feedback about safety and cleanliness.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">2</div>
                    <div class="guideline-text">
                      <h4>Verify Host Identity</h4>
                      <p>Look for verified hosts with complete profiles and government ID verification.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">3</div>
                    <div class="guideline-text">
                      <h4>Check Location Details</h4>
                      <p>Review the neighborhood information and ensure the location meets your safety requirements.</p>
                    </div>
                  </div>
                </div>
              </div>

              <div class="guideline-section">
                <h3>During Your Stay</h3>
                <div class="guideline-list">
                  <div class="guideline-item">
                    <div class="guideline-number">1</div>
                    <div class="guideline-text">
                      <h4>Emergency Information</h4>
                      <p>Locate emergency exits, fire extinguishers, and first aid kits upon arrival.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">2</div>
                    <div class="guideline-text">
                      <h4>Secure Your Belongings</h4>
                      <p>Use provided lockers or safes for valuable items when available.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">3</div>
                    <div class="guideline-text">
                      <h4>Report Concerns Immediately</h4>
                      <p>Contact our 24/7 support team if you feel unsafe or encounter issues.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Hosts Guidelines -->
            <div v-if="activeTab === 'hosts'" class="tab-content">
              <div class="guideline-section">
                <h3>Property Safety Requirements</h3>
                <div class="guideline-list">
                  <div class="guideline-item">
                    <div class="guideline-number">1</div>
                    <div class="guideline-text">
                      <h4>Safety Equipment</h4>
                      <p>Install smoke detectors, carbon monoxide detectors, and fire extinguishers as required by local laws.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">2</div>
                    <div class="guideline-text">
                      <h4>Clear Emergency Instructions</h4>
                      <p>Provide visible emergency contact information and evacuation routes.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">3</div>
                    <div class="guideline-text">
                      <h4>Regular Maintenance</h4>
                      <p>Conduct regular safety inspections and maintenance of all equipment.</p>
                    </div>
                  </div>
                </div>
              </div>

              <div class="guideline-section">
                <h3>Guest Interaction</h3>
                <div class="guideline-list">
                  <div class="guideline-item">
                    <div class="guideline-number">1</div>
                    <div class="guideline-text">
                      <h4>Clear House Rules</h4>
                      <p>Establish and communicate clear rules regarding safety, noise, and property use.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">2</div>
                    <div class="guideline-text">
                      <h4>Respect Privacy</h4>
                      <p>Always respect guest privacy and provide notice before entering the property.</p>
                    </div>
                  </div>
                  <div class="guideline-item">
                    <div class="guideline-number">3</div>
                    <div class="guideline-text">
                      <h4>Prompt Response</h4>
                      <p>Respond quickly to guest concerns and emergencies.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Emergency Section -->
      <section class="emergency-section">
        <div class="container">
          <div class="emergency-content">
            <div class="emergency-info">
              <h2>Emergency Assistance</h2>
              <p>If you're in an emergency situation, contact local authorities immediately, then reach out to our support team.</p>
              <div class="emergency-contacts">
                <div class="contact-item">
                  <strong>Emergency Services</strong>
                  <span>Call 911 (US) or local emergency number</span>
                </div>
                <div class="contact-item">
                  <strong>Stayhapa Safety Team</strong>
                  <span>Available 24/7 through our contact page</span>
                </div>
              </div>
              <router-link to="/contact" class="emergency-button">Contact Safety Team</router-link>
            </div>
            <div class="emergency-image">
              <div class="placeholder-image">🆘</div>
            </div>
          </div>
        </div>
      </section>
    </div>

    <!-- Footer -->
    <footer class="main-footer">
      <!-- Same footer as home page -->
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
  </div>
</template>

<script>
export default {
  name: 'Safety',
  data() {
    return {
      activeTab: 'travelers'
    }
  }
}
</script>

<style scoped>
.safety-page {
  min-height: 100vh;
  background: #f8f9fa;
}

.safety-container {
  min-height: calc(100vh - 200px);
}

/* Hero Section */
.safety-hero {
  background: linear-gradient(135deg, #0F4C81, #1a6bb8);
  color: white;
  padding: 4rem 0;
  text-align: center;
}

.safety-hero h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
  font-weight: bold;
}

.safety-hero p {
  font-size: 1.2rem;
  opacity: 0.9;
}

/* Overview Section */
.safety-overview {
  padding: 4rem 0;
}

.overview-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.overview-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.overview-card:hover {
  transform: translateY(-4px);
}

.overview-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.overview-card h3 {
  color: #333;
  margin-bottom: 1rem;
}

.overview-card p {
  color: #666;
  line-height: 1.6;
}

/* Guidelines Section */
.safety-guidelines {
  padding: 4rem 0;
  background: white;
}

.guidelines-tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 3rem;
  border-bottom: 1px solid #e0e0e0;
}

.tab-button {
  background: none;
  border: none;
  padding: 1rem 2rem;
  font-size: 1.1rem;
  color: #666;
  cursor: pointer;
  border-bottom: 3px solid transparent;
  transition: all 0.3s;
}

.tab-button.active {
  color: #0F4C81;
  border-bottom-color: #0F4C81;
  font-weight: 600;
}

.guideline-section {
  margin-bottom: 3rem;
}

.guideline-section h3 {
  color: #333;
  margin-bottom: 2rem;
  font-size: 1.5rem;
}

.guideline-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.guideline-item {
  display: flex;
  gap: 1.5rem;
  align-items: flex-start;
}

.guideline-number {
  background: #0F4C81;
  color: white;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  flex-shrink: 0;
}

.guideline-text h4 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 1.1rem;
}

.guideline-text p {
  color: #666;
  line-height: 1.6;
}

/* Emergency Section */
.emergency-section {
  padding: 4rem 0;
  background: #fff3cd;
}

.emergency-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 3rem;
  align-items: center;
}

.emergency-info h2 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 2rem;
}

.emergency-info p {
  color: #666;
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.emergency-contacts {
  margin-bottom: 2rem;
}

.contact-item {
  display: flex;
  justify-content: space-between;
  padding: 1rem 0;
  border-bottom: 1px solid #e0e0e0;
}

.contact-item:last-child {
  border-bottom: none;
}

.emergency-button {
  background: #dc3545;
  color: white;
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: bold;
  display: inline-block;
  transition: background-color 0.3s;
}

.emergency-button:hover {
  background: #c82333;
}

.emergency-image {
  text-align: center;
}

.placeholder-image {
  font-size: 8rem;
  opacity: 0.7;
}

/* Responsive Design */
@media (max-width: 768px) {
  .safety-hero h1 {
    font-size: 2.5rem;
  }
  
  .guideline-item {
    flex-direction: column;
    text-align: center;
  }
  
  .emergency-content {
    grid-template-columns: 1fr;
    text-align: center;
  }
  
  .contact-item {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>