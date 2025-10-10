<template>
  <div class="host-guide">
    <!-- Hero Section -->
    <section class="guide-hero">
      <div class="hero-content">
        <h1>Host Guide</h1>
        <p>Everything you need to know to become a successful StayHapa host</p>
        <div class="hero-actions">
          <button class="btn-primary" @click="scrollToGettingStarted">Get Started</button>
          <button class="btn-secondary" @click="scrollToFAQs">View FAQs</button>
        </div>
      </div>
    </section>

    <!-- Quick Navigation -->
    <nav class="guide-nav">
      <div class="nav-container">
        <button 
          v-for="section in navigationSections" 
          :key="section.id"
          :class="{ active: activeSection === section.id }"
          @click="scrollToSection(section.id)"
          class="nav-item"
        >
          {{ section.label }}
        </button>
      </div>
    </nav>

    <!-- Main Content -->
    <div class="guide-container">
      <div class="guide-content">
        <!-- Getting Started Section -->
        <section id="getting-started" class="guide-section">
          <h2>Getting Started</h2>
          <div class="steps-grid">
            <div v-for="(step, index) in gettingStartedSteps" :key="index" class="step-card">
              <div class="step-number">{{ index + 1 }}</div>
              <h3 class="card-title">{{ step.title }}</h3>
              <p class="card-description">{{ step.description }}</p>
              <ul v-if="step.details" class="card-details">
                <li v-for="(detail, detailIndex) in step.details" :key="detailIndex">{{ detail }}</li>
              </ul>
            </div>
          </div>
        </section>

        <!-- Pricing Guide -->
        <section id="pricing" class="guide-section">
          <h2>Pricing Your Property</h2>
          <div class="pricing-tips">
            <div class="tip-card" v-for="(tip, index) in pricingTips" :key="index">
              <div class="tip-icon">{{ tip.icon }}</div>
              <h3 class="card-title">{{ tip.title }}</h3>
              <p class="card-description">{{ tip.description }}</p>
            </div>
          </div>
          
          <div class="pricing-calculator">
            <h3 class="calculator-title">Quick Pricing Calculator</h3>
            <div class="calculator-form">
              <div class="form-group">
                <label>Base Price per Night (KSh)</label>
                <input type="number" v-model.number="basePrice" min="0" placeholder="0">
              </div>
              <div class="form-group">
                <label>Cleaning Fee (KSh)</label>
                <input type="number" v-model.number="cleaningFee" min="0" placeholder="0">
              </div>
              <div class="form-group">
                <label>Service Fee Percentage (%)</label>
                <input type="number" v-model.number="serviceFeePercent" min="0" max="20" placeholder="10">
              </div>
              <div class="calculation-result">
                <h4 class="earnings-title">Estimated Earnings:</h4>
                <p class="earnings">KSh {{ formatKenyanShillings(calculatedEarnings) }} per night</p>
                <small class="earnings-note">After service fees and before taxes</small>
              </div>
            </div>
          </div>
        </section>

        <!-- Best Practices -->
        <section id="best-practices" class="guide-section">
          <h2>Best Practices</h2>
          <div class="practices-grid">
            <div v-for="(practice, index) in bestPractices" :key="index" class="practice-card">
              <h3 class="card-title">{{ practice.title }}</h3>
              <p class="card-description">{{ practice.description }}</p>
              <ul v-if="practice.tips" class="card-details">
                <li v-for="(tip, tipIndex) in practice.tips" :key="tipIndex">{{ tip }}</li>
              </ul>
            </div>
          </div>
        </section>

        <!-- FAQs -->
        <section id="faqs" class="guide-section">
          <h2>Frequently Asked Questions</h2>
          <div class="faq-list">
            <div v-for="(faq, index) in faqs" :key="index" class="faq-item">
              <button class="faq-question" @click="toggleFAQ(index)">
                <span class="faq-question-text">{{ faq.question }}</span>
                <span class="faq-icon">{{ expandedFAQ === index ? '−' : '+' }}</span>
              </button>
              <div v-if="expandedFAQ === index" class="faq-answer">
                <p>{{ faq.answer }}</p>
              </div>
            </div>
          </div>
        </section>

        <!-- Call to Action -->
        <section class="cta-section">
          <div class="cta-content">
            <h2 class="cta-title">Ready to Become a Host?</h2>
            <p class="cta-description">Join thousands of hosts earning extra income with StayHapa</p>
            <div class="cta-actions">
              <button @click="navigateToSignup('host')" class="btn-primary">List Your Property</button>
              <button @click="navigateToLogin('host')" class="btn-secondary">Host Login</button>
            </div>
            <div class="guest-options">
              <p class="guest-text">Looking to book a stay?</p>
              <div class="guest-buttons">
                <button @click="navigateToSignup('guest')" class="btn-outline">Create Guest Account</button>
                <button @click="navigateToLogin('guest')" class="btn-outline">Guest Login</button>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HostGuide',
  data() {
    return {
      activeSection: 'getting-started',
      expandedFAQ: null,
      basePrice: 5000, // Default in Kenyan Shillings
      cleaningFee: 1000, // Default in Kenyan Shillings
      serviceFeePercent: 10,
      navigationSections: [
        { id: 'getting-started', label: 'Getting Started' },
        { id: 'pricing', label: 'Pricing' },
        { id: 'best-practices', label: 'Best Practices' },
        { id: 'faqs', label: 'FAQs' }
      ],
      gettingStartedSteps: [
        {
          title: 'Create Your Host Account',
          description: 'Set up your host profile and verify your identity',
          details: ['Complete profile verification', 'Add payment information', 'Set up security measures']
        },
        {
          title: 'List Your Property',
          description: 'Add details about your space and amenities',
          details: ['Upload high-quality photos', 'Write compelling description', 'Set house rules']
        },
        {
          title: 'Set Availability & Pricing',
          description: 'Choose when guests can book and set your rates',
          details: ['Define booking calendar', 'Set base pricing', 'Add seasonal rates']
        },
        {
          title: 'Welcome Your First Guests',
          description: 'Manage bookings and provide great experiences',
          details: ['Respond to inquiries quickly', 'Prepare your space', 'Provide local tips']
        }
      ],
      pricingTips: [
        {
          icon: '📊',
          title: 'Research Local Market',
          description: 'Check similar properties in your area to understand competitive pricing'
        },
        {
          icon: '📅',
          title: 'Seasonal Pricing',
          description: 'Adjust prices for peak seasons, holidays, and local events'
        },
        {
          icon: '⭐',
          title: 'Value Your Amenities',
          description: 'Price higher for unique amenities like pools, hot tubs, or great views'
        },
        {
          icon: '💰',
          title: 'Factor in Costs',
          description: 'Include cleaning fees, utilities, and maintenance in your pricing'
        }
      ],
      bestPractices: [
        {
          title: 'Communication',
          description: 'Keep guests informed throughout their stay',
          tips: ['Respond within 2 hours', 'Send welcome message', 'Provide check-in instructions']
        },
        {
          title: 'Cleanliness',
          description: 'Maintain high standards of cleanliness',
          tips: ['Professional cleaning between guests', 'Stock essential supplies', 'Regular deep cleaning']
        },
        {
          title: 'Guest Experience',
          description: 'Create memorable stays for your guests',
          tips: ['Provide local recommendations', 'Offer welcome basket', 'Ensure smooth check-in process']
        },
        {
          title: 'Reviews & Ratings',
          description: 'Build your reputation through positive reviews',
          tips: ['Encourage guest reviews', 'Respond to all reviews', 'Learn from feedback']
        }
      ],
      faqs: [
        {
          question: 'How much can I earn as a host?',
          answer: 'Earnings vary based on location, property type, and occupancy. Many hosts earn enough to cover their mortgage or generate significant extra income. Use our pricing calculator above to estimate potential earnings.'
        },
        {
          question: 'What are the host fees?',
          answer: 'StayHapa charges a 10% service fee on each booking. This covers payment processing, customer support, and platform maintenance. There are no upfront costs to list your property.'
        },
        {
          question: 'How do I handle guest cancellations?',
          answer: 'You can choose from several cancellation policies (Flexible, Moderate, Strict). We recommend the Moderate policy which balances guest flexibility with host protection. All cancellations are handled according to your chosen policy.'
        },
        {
          question: 'What insurance coverage is provided?',
          answer: 'StayHapa offers Host Protection Insurance that provides primary liability coverage up to KSh 100 million. We recommend hosts also maintain their own homeowners or rental insurance.'
        },
        {
          question: 'How do I get paid?',
          answer: 'Payments are automatically deposited to your MPESA or bank account 24 hours after guest check-in. You can set up direct deposit and track all payments in your host dashboard.'
        }
      ]
    }
  },
  computed: {
    calculatedEarnings() {
      const serviceFee = (this.basePrice * this.serviceFeePercent) / 100;
      return (this.basePrice + this.cleaningFee - serviceFee);
    }
  },
  methods: {
    formatKenyanShillings(amount) {
      return new Intl.NumberFormat('en-KE').format(Math.round(amount));
    },
    
    scrollToSection(sectionId) {
      const element = document.getElementById(sectionId);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
        this.activeSection = sectionId;
      }
    },
    scrollToGettingStarted() {
      this.scrollToSection('getting-started');
    },
    scrollToFAQs() {
      this.scrollToSection('faqs');
    },
    toggleFAQ(index) {
      this.expandedFAQ = this.expandedFAQ === index ? null : index;
    },
    
    // Navigation methods
    navigateToLogin(userType) {
      this.$router.push({ 
        path: '/login', 
        query: { userType: userType } 
      });
    },
    
    navigateToSignup(userType) {
      this.$router.push({ 
        path: '/signup', 
        query: { userType: userType } 
      });
    }
  },
  mounted() {
    // Add scroll listener to update active section
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.activeSection = entry.target.id;
        }
      });
    }, { threshold: 0.5 });

    // Observe all sections
    this.navigationSections.forEach(section => {
      const element = document.getElementById(section.id);
      if (element) observer.observe(element);
    });
  }
}
</script>

<style scoped>
.host-guide {
  min-height: 100vh;
}

/* Hero Section */
.guide-hero {
  background: linear-gradient(135deg, #0F4C81 0%, #3498db 100%);
  color: white;
  padding: 4rem 1rem;
  text-align: center;
}

.hero-content h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
  font-weight: 700;
}

.hero-content p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.9;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.hero-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

/* Navigation */
.guide-nav {
  background: white;
  border-bottom: 1px solid #e0e0e0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.nav-item {
  padding: 1rem 1.5rem;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  color: #666;
  border-bottom: 3px solid transparent;
  transition: all 0.3s ease;
  font-weight: 500;
}

.nav-item:hover,
.nav-item.active {
  color: #0F4C81;
  border-bottom-color: #0F4C81;
  font-weight: 600;
}

/* Main Content */
.guide-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

.guide-section {
  margin-bottom: 4rem;
  scroll-margin-top: 80px;
}

.guide-section h2 {
  color: #2c3e50;
  font-size: 2.5rem;
  margin-bottom: 2rem;
  text-align: center;
  font-weight: 700;
}

/* Card Typography */
.card-title {
  font-weight: 700 !important;
  color: #2c3e50;
  text-align: center;
  margin-bottom: 1rem;
  font-size: 1.3rem;
  line-height: 1.4;
}

.card-description {
  text-align: center;
  color: #666;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.card-details {
  text-align: left;
  margin-top: 1rem;
  padding-left: 1.5rem;
}

.card-details li {
  margin-bottom: 0.5rem;
  color: #666;
  line-height: 1.5;
}

/* Steps Grid */
.steps-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
}

.step-card {
  background: white;
  padding: 2.5rem 2rem;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  text-align: center;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.step-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
}

.step-number {
  width: 60px;
  height: 60px;
  background: #0F4C81;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  font-weight: 700;
  margin: 0 auto 1.5rem;
}

/* Pricing Tips */
.pricing-tips {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 3rem;
}

.tip-card {
  text-align: center;
  padding: 2rem 1.5rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.tip-card:hover {
  transform: translateY(-3px);
}

.tip-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

/* Pricing Calculator */
.pricing-calculator {
  background: white;
  padding: 2.5rem;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  max-width: 500px;
  margin: 0 auto;
}

.calculator-title {
  text-align: center;
  margin-bottom: 2rem;
  color: #2c3e50;
  font-weight: 700;
  font-size: 1.5rem;
}

.calculator-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #2c3e50;
  text-align: left;
}

.form-group input {
  padding: 0.75rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  text-align: center;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #0F4C81;
}

.calculation-result {
  text-align: center;
  padding: 1.5rem;
  background: #e8f5e8;
  border-radius: 12px;
  margin-top: 1rem;
  border: 2px solid #27ae60;
}

.earnings-title {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-weight: 600;
  font-size: 1.2rem;
}

.earnings {
  font-size: 2rem;
  font-weight: 700;
  color: #27ae60;
  margin: 0.5rem 0;
}

.earnings-note {
  color: #666;
  font-size: 0.85rem;
}

/* Best Practices */
.practices-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.practice-card {
  background: white;
  padding: 2.5rem 2rem;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  text-align: center;
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.practice-card:hover {
  transform: translateY(-3px);
}

/* FAQs */
.faq-list {
  max-width: 800px;
  margin: 0 auto;
}

.faq-item {
  border-bottom: 1px solid #e0e0e0;
}

.faq-question {
  width: 100%;
  padding: 1.5rem 0;
  background: none;
  border: none;
  text-align: left;
  font-size: 1.1rem;
  font-weight: 600;
  color: #2c3e50;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: color 0.3s ease;
}

.faq-question:hover {
  color: #0F4C81;
}

.faq-question-text {
  flex: 1;
  text-align: left;
  font-weight: 600;
}

.faq-icon {
  font-size: 1.5rem;
  font-weight: normal;
  margin-left: 1rem;
}

.faq-answer {
  padding: 0 0 1.5rem 0;
  color: #666;
  line-height: 1.6;
  text-align: left;
}

/* CTA Section */
.cta-section {
  background: linear-gradient(135deg, #0F4C81 0%, #3498db 100%);
  color: white;
  padding: 4rem 2rem;
  text-align: center;
  border-radius: 20px;
}

.cta-title {
  color: white;
  margin-bottom: 1rem;
  font-weight: 700;
  font-size: 2.2rem;
}

.cta-description {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0.95;
  max-width: 500px;
  margin-left: auto;
  margin-right: auto;
}

.cta-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
  margin-bottom: 2rem;
}

.guest-options {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid rgba(255, 255, 255, 0.3);
}

.guest-text {
  margin-bottom: 1rem;
  font-size: 1.1rem;
  opacity: 0.9;
}

.guest-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

/* Buttons */
.btn-primary, .btn-secondary, .btn-outline {
  padding: 0.75rem 2rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
  transition: all 0.3s ease;
  text-align: center;
}

.btn-primary {
  background: #0F4C81;
  color: white;
}

.btn-primary:hover {
  background: #08355c;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(15, 76, 129, 0.3);
}

.btn-secondary {
  background: transparent;
  color: white;
  border: 2px solid white;
}

.btn-secondary:hover {
  background: white;
  color: #0F4C81;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
}

.btn-outline {
  background: transparent;
  color: white;
  border: 2px solid rgba(255, 255, 255, 0.5);
}

.btn-outline:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 255, 255, 0.1);
}

/* Responsive Design */
@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2.2rem;
  }
  
  .nav-container {
    flex-direction: column;
  }
  
  .nav-item {
    text-align: center;
    width: 100%;
  }
  
  .guide-section h2 {
    font-size: 2rem;
  }
  
  .cta-actions,
  .guest-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .btn-primary, 
  .btn-secondary,
  .btn-outline {
    width: 250px;
  }
  
  .step-card,
  .tip-card,
  .practice-card {
    padding: 2rem 1.5rem;
  }
  
  .pricing-calculator {
    padding: 2rem 1.5rem;
  }
  
  .cta-section {
    padding: 3rem 1.5rem;
  }
  
  .cta-title {
    font-size: 1.8rem;
  }
}

@media (max-width: 480px) {
  .hero-content h1 {
    font-size: 1.8rem;
  }
  
  .guide-section h2 {
    font-size: 1.6rem;
  }
  
  .steps-grid,
  .pricing-tips,
  .practices-grid {
    grid-template-columns: 1fr;
  }
}
</style>