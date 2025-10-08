<template>
  <div class="contact-page">
    <!-- Navigation Bar -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link to="/listings" class="nav-link">Browse Stays</router-link>
          <router-link to="/about" class="nav-link">About</router-link>
          <router-link to="/login" class="nav-link">Login</router-link>
        </div>
      </div>
    </nav>

    <div class="contact-container">
      <!-- Hero Section -->
      <section class="contact-hero">
        <div class="hero-content">
          <h1>Get in Touch</h1>
          <p>We're here to help you find your perfect stay or answer any questions about hosting</p>
        </div>
      </section>

      <div class="contact-content">
        <!-- Contact Information -->
        <div class="contact-info">
          <div class="info-card">
            <div class="info-icon">📧</div>
            <h3>Email Us</h3>
            <p>Send us an email anytime</p>
            <a href="mailto:Jobmogire6@gmail.com" class="contact-link">Jobmogire6@gmail.com</a>
          </div>

          <div class="info-card">
            <div class="info-icon">📞</div>
            <h3>Call Us</h3>
            <p>Mon-Fri from 8am to 6pm</p>
            <a href="tel:+254798383592" class="contact-link">+254 798 383 592</a>
          </div>

          <div class="info-card">
            <div class="info-icon">💬</div>
            <h3>Live Chat</h3>
            <p>24/7 customer support</p>
            <button class="chat-btn" @click="startChat">Start Chat</button>
          </div>

          <div class="info-card">
            <div class="info-icon">🏠</div>
            <h3>Visit Us</h3>
            <p>Nairobi, Kenya</p>
            <span class="contact-link">Headquarters</span>
          </div>
        </div>

        <!-- Contact Form -->
        <div class="contact-form-section">
          <div class="form-container">
            <h2>Send us a Message</h2>
            <p>Fill out the form below and we'll get back to you within 24 hours</p>

            <form @submit.prevent="submitForm" class="contact-form">
              <div class="form-row">
                <div class="form-group">
                  <label for="firstName">First Name *</label>
                  <input 
                    type="text" 
                    id="firstName" 
                    v-model="form.firstName" 
                    required
                    placeholder="Enter your first name"
                  >
                </div>
                <div class="form-group">
                  <label for="lastName">Last Name *</label>
                  <input 
                    type="text" 
                    id="lastName" 
                    v-model="form.lastName" 
                    required
                    placeholder="Enter your last name"
                  >
                </div>
              </div>

              <div class="form-group">
                <label for="email">Email Address *</label>
                <input 
                  type="email" 
                  id="email" 
                  v-model="form.email" 
                  required
                  placeholder="Enter your email address"
                >
              </div>

              <div class="form-group">
                <label for="phone">Phone Number</label>
                <input 
                  type="tel" 
                  id="phone" 
                  v-model="form.phone" 
                  placeholder="Enter your phone number"
                >
              </div>

              <div class="form-group">
                <label for="subject">Subject *</label>
                <select id="subject" v-model="form.subject" required>
                  <option value="">Select a subject</option>
                  <option value="booking">Booking Assistance</option>
                  <option value="hosting">Become a Host</option>
                  <option value="support">Technical Support</option>
                  <option value="billing">Billing Issue</option>
                  <option value="feedback">Feedback</option>
                  <option value="other">Other</option>
                </select>
              </div>

              <div class="form-group">
                <label for="message">Message *</label>
                <textarea 
                  id="message" 
                  v-model="form.message" 
                  required
                  rows="6"
                  placeholder="Tell us how we can help you..."
                ></textarea>
              </div>

              <button type="submit" class="submit-btn" :disabled="loading">
                {{ loading ? 'Sending...' : 'Send Message' }}
              </button>
            </form>
          </div>
        </div>
      </div>

      <!-- FAQ Section -->
      <section class="faq-section">
        <div class="faq-container">
          <h2>Frequently Asked Questions</h2>
          <div class="faq-grid">
            <div class="faq-item">
              <h3>How do I book a stay?</h3>
              <p>Simply search for your destination, select your dates, choose a property that fits your needs, and click "Book Now" to complete your reservation.</p>
            </div>
            <div class="faq-item">
              <h3>What are the requirements to become a host?</h3>
              <p>You need a property in good condition, basic amenities, and to comply with local regulations. Our team will guide you through the verification process.</p>
            </div>
            <div class="faq-item">
              <h3>How are payments processed?</h3>
              <p>We use secure payment processing with DPO. Your payment is held until 24 hours after check-in to ensure everything meets your expectations.</p>
            </div>
            <div class="faq-item">
              <h3>What is your cancellation policy?</h3>
              <p>Cancellation policies vary by property. You can view the specific policy for each listing before booking. Most hosts offer flexible cancellation options.</p>
            </div>
          </div>
        </div>
      </section>
    </div>

    <!-- Footer -->
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

<script>
export default {
  name: 'ContactPage',
  data() {
    return {
      loading: false,
      form: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        subject: '',
        message: ''
      }
    }
  },
  methods: {
    async submitForm() {
      this.loading = true;
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      console.log('Form submitted:', this.form);
      alert('Thank you for your message! We will get back to you within 24 hours.');
      
      // Reset form
      this.form = {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        subject: '',
        message: ''
      };
      
      this.loading = false;
    },
    startChat() {
      alert('Live chat feature would be integrated here. For now, please call or email us.');
    }
  }
}
</script>

<style scoped>
.contact-page {
  min-height: 100vh;
  background: #f8f9fa;
}

/* Navigation */
.navbar {
  background: white;
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
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

.nav-links {
  display: flex;
  gap: 1.5rem;
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

/* Contact Container */
.contact-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

/* Hero Section */
.contact-hero {
  background: linear-gradient(135deg, #0F4C81 0%, #0a3a6b 100%);
  color: white;
  text-align: center;
  padding: 4rem 2rem;
  border-radius: 0 0 20px 20px;
  margin-bottom: 3rem;
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

/* Contact Content */
.contact-content {
  display: grid;
  grid-template-columns: 1fr 1.5fr;
  gap: 3rem;
  margin-bottom: 4rem;
}

/* Contact Info */
.contact-info {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1.5rem;
  height: fit-content;
}

.info-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.3s;
}

.info-card:hover {
  transform: translateY(-5px);
}

.info-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.info-card h3 {
  margin: 0 0 0.5rem 0;
  color: #333;
}

.info-card p {
  color: #666;
  margin: 0 0 1rem 0;
}

.contact-link {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s;
}

.contact-link:hover {
  color: #0a3a6b;
}

.chat-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
}

.chat-btn:hover {
  background: #0a3a6b;
}

/* Contact Form */
.contact-form-section {
  background: white;
  border-radius: 16px;
  padding: 2rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.form-container h2 {
  margin: 0 0 0.5rem 0;
  color: #333;
}

.form-container p {
  color: #666;
  margin: 0 0 2rem 0;
}

.contact-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: 600;
  color: #333;
}

.form-group input,
.form-group select,
.form-group textarea {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #0F4C81;
}

.submit-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 1rem 2rem;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s;
}

.submit-btn:hover:not(:disabled) {
  background: #0a3a6b;
}

.submit-btn:disabled {
  background: #6c757d;
  cursor: not-allowed;
}

/* FAQ Section */
.faq-section {
  background: white;
  border-radius: 16px;
  padding: 3rem 2rem;
  margin-bottom: 3rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.faq-container h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

.faq-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.faq-item {
  padding: 1.5rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  transition: border-color 0.3s;
}

.faq-item:hover {
  border-color: #0F4C81;
}

.faq-item h3 {
  margin: 0 0 1rem 0;
  color: #333;
}

.faq-item p {
  color: #666;
  margin: 0;
  line-height: 1.6;
}

/* Footer */
.footer {
  background: #333;
  color: white;
  padding: 3rem 0 1rem;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin-bottom: 2rem;
}

.footer-section h3,
.footer-section h4 {
  margin-bottom: 1rem;
  color: white;
}

.footer-section a {
  display: block;
  color: #ccc;
  text-decoration: none;
  margin-bottom: 0.5rem;
  transition: color 0.3s;
}

.footer-section a:hover {
  color: white;
}

.footer-section p {
  color: #ccc;
  margin: 0.25rem 0;
}

.footer-bottom {
  border-top: 1px solid #555;
  padding-top: 1rem;
  text-align: center;
  color: #ccc;
}

/* Responsive Design */
@media (max-width: 768px) {
  .contact-content {
    grid-template-columns: 1fr;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .hero-content h1 {
    font-size: 2rem;
  }
  
  .contact-hero {
    padding: 3rem 1rem;
  }
  
  .contact-container {
    padding: 0 1rem;
  }
}

@media (max-width: 480px) {
  .navbar {
    padding: 1rem;
  }
  
  .nav-links {
    gap: 1rem;
  }
  
  .faq-grid {
    grid-template-columns: 1fr;
  }
}
</style>