<template>
  <div class="about-page">
    <!-- Navigation Bar (same as contact page) -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link to="/listings" class="nav-link">Browse Stays</router-link>
          <router-link to="/contact" class="nav-link">Contact</router-link>
          <router-link to="/login" class="nav-link">Login</router-link>
        </div>
      </div>
    </nav>

    <div class="about-container">
      <!-- Hero Section -->
      <section class="about-hero">
        <div class="hero-content">
          <h1>About Stayhapa</h1>
          <p>Redefining travel experiences in Kenya through unique accommodations and authentic local stays</p>
        </div>
      </section>

      <!-- Mission Section -->
      <section class="mission-section">
        <div class="mission-content">
          <div class="mission-text">
            <h2>Our Mission</h2>
            <p>At Stayhapa, we believe that travel should be about creating meaningful connections and unforgettable experiences. We're dedicated to connecting travelers with unique accommodations that offer authentic local experiences while providing hosts with opportunities to share their spaces and stories.</p>
            <p>Founded in Nairobi, Kenya, we understand the diverse needs of both travelers and hosts across East Africa. Our platform is built with local context in mind, ensuring seamless experiences for everyone.</p>
          </div>
          <div class="mission-image">
            <img src="https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=500" alt="Stayhapa Mission">
          </div>
        </div>
      </section>

      <!-- Values Section -->
      <section class="values-section">
        <div class="values-container">
          <h2>Our Values</h2>
          <div class="values-grid">
            <div class="value-card">
              <div class="value-icon">🏠</div>
              <h3>Authentic Experiences</h3>
              <p>We prioritize genuine local experiences over standardized accommodations, helping travelers live like locals.</p>
            </div>
            <div class="value-card">
              <div class="value-icon">🤝</div>
              <h3>Community First</h3>
              <p>We build strong relationships between hosts and guests, fostering a community built on trust and respect.</p>
            </div>
            <div class="value-card">
              <div class="value-icon">🌍</div>
              <h3>Sustainable Tourism</h3>
              <p>We promote responsible travel that benefits local communities and preserves cultural heritage.</p>
            </div>
            <div class="value-card">
              <div class="value-icon">💫</div>
              <h3>Innovation</h3>
              <p>We continuously improve our platform to provide the best experience for both hosts and guests.</p>
            </div>
          </div>
        </div>
      </section>

      <!-- Team Section -->
      <section class="team-section">
        <div class="team-container">
          <h2>Meet Our Founder</h2>
          <div class="team-member">
            <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200&h=200&fit=crop&crop=face" alt="Job Mogire" class="member-photo">
            <div class="member-info">
              <h3>Job Mogire</h3>
              <p class="member-role">Founder & CEO</p>
              <p class="member-bio">With a passion for travel and technology, Job founded Stayhapa to revolutionize the accommodation industry in Kenya. His vision is to create a platform that benefits both travelers seeking authentic experiences and hosts looking to share their unique spaces.</p>
              <div class="member-contact">
                <p><strong>Email:</strong> Jobmogire6@gmail.com</p>
                <p><strong>Phone:</strong> +254 798 383 592</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Stats Section -->
      <section class="stats-section">
        <div class="stats-container">
          <h2>Stayhapa in Numbers</h2>
          <div class="stats-grid">
            <div class="stat-item">
              <div class="stat-number">1,000+</div>
              <div class="stat-label">Happy Guests</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">500+</div>
              <div class="stat-label">Trusted Hosts</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">50+</div>
              <div class="stat-label">Destinations</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">98%</div>
              <div class="stat-label">Satisfaction Rate</div>
            </div>
          </div>
        </div>
      </section>

      <!-- CTA Section -->
      <section class="cta-section">
        <div class="cta-container">
          <h2>Ready to Join the Stayhapa Community?</h2>
          <p>Whether you're looking for your next adventure or want to share your space with travelers, we've got you covered.</p>
          <div class="cta-buttons">
            <router-link to="/listings" class="cta-btn primary">Find Your Stay</router-link>
            <router-link to="/become-host" class="cta-btn secondary">Become a Host</router-link>
          </div>
        </div>
      </section>
    </div>

    <!-- Footer (same as contact page) -->
    <footer class="footer">
      <div class="footer-container">
        <div class="footer-content">
          <div class="footer-section">
            <h3>Stayhapa</h3>
            <p>Find your perfect stay experience in Kenya and beyond.</p>
          </div>
          <div class="footer-section">
            <h4>Quick Links</h4>
            <router-link to="/">Home</router-link>
            <router-link to="/listings">Browse Stays</router-link>
            <router-link to="/about">About Us</router-link>
            <router-link to="/contact">Contact</router-link>
          </div>
          <div class="footer-section">
            <h4>Support</h4>
            <a href="#">Help Center</a>
            <a href="#">Safety Information</a>
            <a href="#">Cancellation Options</a>
          </div>
          <div class="footer-section">
            <h4>Contact Info</h4>
            <p>Email: Jobmogire6@gmail.com</p>
            <p>Phone: +254 798 383 592</p>
            <p>Nairobi, Kenya</p>
          </div>
        </div>
        <div class="footer-bottom">
          <p>&copy; 2024 Stayhapa. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.about-page {
  min-height: 100vh;
  background: #f8f9fa;
}

/* About Container */
.about-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

/* Hero Section */
.about-hero {
  background: linear-gradient(135deg, #0F4C81 0%, #0a3a6b 100%);
  color: white;
  text-align: center;
  padding: 4rem 2rem;
  border-radius: 0 0 20px 20px;
  margin-bottom: 4rem;
}

.hero-content h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.hero-content p {
  font-size: 1.2rem;
  opacity: 0.9;
  max-width: 600px;
  margin: 0 auto;
}

/* Mission Section */
.mission-section {
  background: white;
  border-radius: 16px;
  padding: 4rem 2rem;
  margin-bottom: 4rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.mission-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
  align-items: center;
}

.mission-text h2 {
  margin-bottom: 1.5rem;
  color: #333;
}

.mission-text p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.mission-image img {
  width: 100%;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

/* Values Section */
.values-section {
  background: white;
  border-radius: 16px;
  padding: 4rem 2rem;
  margin-bottom: 4rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.values-container h2 {
  text-align: center;
  margin-bottom: 3rem;
  color: #333;
}

.values-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.value-card {
  text-align: center;
  padding: 2rem;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  transition: transform 0.3s;
}

.value-card:hover {
  transform: translateY(-5px);
  border-color: #0F4C81;
}

.value-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.value-card h3 {
  margin-bottom: 1rem;
  color: #333;
}

.value-card p {
  color: #666;
  line-height: 1.6;
}

/* Team Section */
.team-section {
  background: white;
  border-radius: 16px;
  padding: 4rem 2rem;
  margin-bottom: 4rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.team-container h2 {
  text-align: center;
  margin-bottom: 3rem;
  color: #333;
}

.team-member {
  display: grid;
  grid-template-columns: 200px 1fr;
  gap: 3rem;
  align-items: center;
  max-width: 800px;
  margin: 0 auto;
}

.member-photo {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  object-fit: cover;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.member-info h3 {
  margin-bottom: 0.5rem;
  color: #333;
  font-size: 1.5rem;
}

.member-role {
  color: #0F4C81;
  font-weight: 600;
  margin-bottom: 1.5rem;
}

.member-bio {
  color: #666;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.member-contact p {
  color: #666;
  margin: 0.5rem 0;
}

/* Stats Section */
.stats-section {
  background: linear-gradient(135deg, #0F4C81 0%, #0a3a6b 100%);
  color: white;
  border-radius: 16px;
  padding: 4rem 2rem;
  margin-bottom: 4rem;
}

.stats-container h2 {
  text-align: center;
  margin-bottom: 3rem;
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
  margin-bottom: 0.5rem;
}

.stat-label {
  font-size: 1.1rem;
  opacity: 0.9;
}

/* CTA Section */
.cta-section {
  background: white;
  border-radius: 16px;
  padding: 4rem 2rem;
  margin-bottom: 4rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.cta-container h2 {
  margin-bottom: 1rem;
  color: #333;
}

.cta-container p {
  color: #666;
  margin-bottom: 2rem;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.cta-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.cta-btn {
  padding: 1rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s;
}

.cta-btn.primary {
  background: #0F4C81;
  color: white;
}

.cta-btn.primary:hover {
  background: #0a3a6b;
}

.cta-btn.secondary {
  background: white;
  color: #0F4C81;
  border: 2px solid #0F4C81;
}

.cta-btn.secondary:hover {
  background: #0F4C81;
  color: white;
}

/* Responsive Design */
@media (max-width: 768px) {
  .mission-content {
    grid-template-columns: 1fr;
  }
  
  .team-member {
    grid-template-columns: 1fr;
    text-align: center;
  }
  
  .hero-content h1 {
    font-size: 2rem;
  }
  
  .about-hero {
    padding: 3rem 1rem;
  }
  
  .about-container {
    padding: 0 1rem;
  }
}

@media (max-width: 480px) {
  .cta-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .cta-btn {
    width: 200px;
  }
}
</style>