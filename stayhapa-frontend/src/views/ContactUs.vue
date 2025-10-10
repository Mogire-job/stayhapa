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
      </div>
    </nav>

    <div class="contact-container">
      <!-- Hero Section -->
      <section class="contact-hero">
        <div class="container">
          <h1>Contact Us</h1>
          <p>We're here to help! Get in touch with our support team</p>
        </div>
      </section>

      <!-- Contact Options -->
      <section class="contact-options">
        <div class="container">
          <div class="options-grid">
            <div class="option-card">
              <div class="option-icon">📞</div>
              <h3>Phone Support</h3>
              <p>Speak directly with our support team</p>
              <div class="option-details">
                <strong>+1 (555) 123-4567</strong>
                <span>Available 24/7 for urgent matters</span>
              </div>
            </div>
            
            <div class="option-card">
              <div class="option-icon">✉️</div>
              <h3>Email Support</h3>
              <p>Send us a detailed message</p>
              <div class="option-details">
                <strong>support@stayhapa.com</strong>
                <span>Response within 4 hours</span>
              </div>
            </div>
            
            <div class="option-card">
              <div class="option-icon">💬</div>
              <h3>Live Chat</h3>
              <p>Instant messaging support</p>
              <div class="option-details">
                <strong>Available Online</strong>
                <span>Mon-Sun: 6AM - 10PM EST</span>
              </div>
            </div>
            
            <div class="option-card">
              <div class="option-icon">🆘</div>
              <h3>Emergency</h3>
              <p>Critical safety concerns</p>
              <div class="option-details">
                <strong>Emergency Line</strong>
                <span>24/7 for immediate assistance</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Contact Form -->
      <section class="contact-form-section">
        <div class="container">
          <div class="form-container">
            <div class="form-header">
              <h2>Send us a Message</h2>
              <p>Fill out the form below and we'll get back to you as soon as possible</p>
            </div>
            
            <form @submit.prevent="submitForm" class="contact-form">
              <div class="form-row">
                <div class="form-group">
                  <label for="firstName">First Name *</label>
                  <input 
                    type="text" 
                    id="firstName" 
                    v-model="form.firstName"
                    required
                  >
                </div>
                <div class="form-group">
                  <label for="lastName">Last Name *</label>
                  <input 
                    type="text" 
                    id="lastName" 
                    v-model="form.lastName"
                    required
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
                >
              </div>
              
              <div class="form-group">
                <label for="userType">I am a *</label>
                <select 
                  id="userType" 
                  v-model="form.userType"
                  required
                >
                  <option value="">Select user type</option>
                  <option value="traveler">Traveler</option>
                  <option value="host">Host</option>
                  <option value="both">Both Traveler and Host</option>
                  <option value="other">Other</option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="subject">Subject *</label>
                <select 
                  id="subject" 
                  v-model="form.subject"
                  required
                >
                  <option value="">Select a subject</option>
                  <option value="booking">Booking Issue</option>
                  <option value="payment">Payment Problem</option>
                  <option value="safety">Safety Concern</option>
                  <option value="technical">Technical Issue</option>
                  <option value="account">Account Problem</option>
                  <option value="hosting">Hosting Question</option>
                  <option value="other">Other</option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="urgency">Urgency *</label>
                <select 
                  id="urgency" 
                  v-model="form.urgency"
                  required
                >
                  <option value="">Select urgency level</option>
                  <option value="low">Low - General inquiry</option>
                  <option value="medium">Medium - Need help within 24h</option>
                  <option value="high">High - Need immediate assistance</option>
                  <option value="emergency">Emergency - Critical issue</option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="message">Message *</label>
                <textarea 
                  id="message" 
                  rows="6" 
                  v-model="form.message"
                  placeholder="Please describe your issue in detail..."
                  required
                ></textarea>
              </div>
              
              <div class="form-group">
                <label class="checkbox-label">
                  <input 
                    type="checkbox" 
                    v-model="form.agreeToTerms"
                    required
                  >
                  <span>I agree to the terms of service and privacy policy</span>
                </label>
              </div>
              
              <button 
                type="submit" 
                class="submit-button"
                :disabled="!form.agreeToTerms"
                :class="{ disabled: !form.agreeToTerms }"
              >
                {{ isLoading ? 'Sending...' : 'Send Message' }}
              </button>
            </form>
          </div>
        </div>
      </section>

      <!-- FAQ Quick Links -->
      <section class="faq-quicklinks">
        <div class="container">
          <h2>Quick Answers</h2>
          <p>Check our help center for instant solutions to common questions</p>
          <div class="quicklinks-grid">
            <router-link to="/help" class="quicklink-card">
              <div class="quicklink-icon">❓</div>
              <h4>How do I reset my password?</h4>
              <span>View answer →</span>
            </router-link>
            <router-link to="/help" class="quicklink-card">
              <div class="quicklink-icon">💰</div>
              <h4>Payment and refund questions</h4>
              <span>View answer →</span>
            </router-link>
            <router-link to="/help" class="quicklink-card">
              <div class="quicklink-icon">🏠</div>
              <h4>Hosting guidelines and requirements</h4>
              <span>View answer →</span>
            </router-link>
            <router-link to="/safety" class="quicklink-card">
              <div class="quicklink-icon">🛡️</div>
              <h4>Safety and security information</h4>
              <span>View answer →</span>
            </router-link>
          </div>
        </div>
      </section>
    </div>

    <!-- Simplified Footer -->
    <footer class="main-footer">
      <div class="container">
        <div class="footer-bottom">
          <p>&copy; 2024 Stayhapa. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'ContactUs',
  data() {
    return {
      isLoading: false,
      form: {
        firstName: '',
        lastName: '',
        email: '',
        userType: '',
        subject: '',
        urgency: '',
        message: '',
        agreeToTerms: false
      }
    }
  },
  methods: {
    async submitForm() {
      this.isLoading = true;
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      // Show success message
      alert('Thank you for your message! We will get back to you within 4 hours.');
      
      // Reset form
      this.form = {
        firstName: '',
        lastName: '',
        email: '',
        userType: '',
        subject: '',
        urgency: '',
        message: '',
        agreeToTerms: false
      };
      
      this.isLoading = false;
    }
  }
}
</script>

<style scoped>
.contact-page {
  min-height: 100vh;
  background: #f8f9fa;
}

.contact-container {
  min-height: calc(100vh - 200px);
}

/* Hero Section */
.contact-hero {
  background: linear-gradient(135deg, #0F4C81, #1a6bb8);
  color: white;
  padding: 4rem 0;
  text-align: center;
}

.contact-hero h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
  font-weight: bold;
}

.contact-hero p {
  font-size: 1.2rem;
  opacity: 0.9;
}

/* Contact Options */
.contact-options {
  padding: 4rem 0;
}

.options-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
}

.option-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.option-card:hover {
  transform: translateY(-4px);
}

.option-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.option-card h3 {
  color: #333;
  margin-bottom: 1rem;
}

.option-card p {
  color: #666;
  margin-bottom: 1.5rem;
}

.option-details {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.option-details strong {
  color: #0F4C81;
  font-size: 1.1rem;
}

.option-details span {
  color: #666;
  font-size: 0.9rem;
}

/* Contact Form */
.contact-form-section {
  padding: 4rem 0;
  background: white;
}

.form-container {
  max-width: 800px;
  margin: 0 auto;
}

.form-header {
  text-align: center;
  margin-bottom: 3rem;
}

.form-header h2 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 2.2rem;
}

.form-header p {
  color: #666;
  font-size: 1.1rem;
}

.contact-form {
  background: #f8f9fa;
  padding: 2rem;
  border-radius: 12px;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
  font-weight: 600;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #0F4C81;
}

.checkbox-label {
  display: flex;
  align-items: flex-start;
  gap: 0.5rem;
  cursor: pointer;
  color: #666;
}

.checkbox-label input {
  width: auto;
  margin-top: 0.25rem;
}

.submit-button {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 1rem 2rem;
  border-radius: 6px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 100%;
}

.submit-button:hover:not(.disabled) {
  background: #0a3a6b;
}

.submit-button.disabled {
  background: #ccc;
  cursor: not-allowed;
}

/* FAQ Quick Links */
.faq-quicklinks {
  padding: 4rem 0;
  background: #f8f9fa;
}

.faq-quicklinks h2 {
  text-align: center;
  color: #333;
  margin-bottom: 1rem;
  font-size: 2.2rem;
}

.faq-quicklinks p {
  text-align: center;
  color: #666;
  margin-bottom: 3rem;
  font-size: 1.1rem;
}

.quicklinks-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.quicklink-card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  text-decoration: none;
  color: inherit;
  transition: all 0.3s;
  border: 1px solid #e0e0e0;
}

.quicklink-card:hover {
  border-color: #0F4C81;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(15, 76, 129, 0.1);
}

.quicklink-icon {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.quicklink-card h4 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 1.1rem;
}

.quicklink-card span {
  color: #0F4C81;
  font-weight: 600;
}

/* Simplified Footer */
.main-footer {
  background: #333;
  color: white;
  padding: 2rem 0 1rem;
}

.footer-bottom {
  text-align: center;
  opacity: 0.7;
}

/* Responsive Design */
@media (max-width: 768px) {
  .contact-hero h1 {
    font-size: 2.5rem;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .options-grid {
    grid-template-columns: 1fr;
  }
  
  .quicklinks-grid {
    grid-template-columns: 1fr;
  }
}
</style>