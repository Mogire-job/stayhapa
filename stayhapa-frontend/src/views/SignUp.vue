<template>
  <div class="signup-page">
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link to="/login" class="nav-link">Log In</router-link>
        </div>
      </div>
    </nav>

    <div class="signup-container">
      <div class="signup-card">
        <!-- User Type Header -->
        <div class="user-type-header">
          <div class="user-type-badge">{{ signupData.userType === 'host' ? 'Host' : 'Guest' }}</div>
          <h2>Create Your {{ signupData.userType === 'host' ? 'Host' : 'Stayhapa' }} Account</h2>
          <p>{{ signupData.userType === 'host' ? 'Start hosting and earn income from your property' : 'Join thousands of travelers worldwide' }}</p>
        </div>
        
        <form @submit.prevent="handleSignup" class="signup-form">
          <div class="form-group">
            <label>Full Name *</label>
            <input 
              type="text" 
              v-model="signupData.fullName" 
              placeholder="Enter your full name"
              required
            >
          </div>

          <div class="form-group">
            <label>Email Address *</label>
            <input 
              type="email" 
              v-model="signupData.email" 
              placeholder="Enter your email"
              required
            >
          </div>

          <div class="form-group">
            <label>Phone Number *</label>
            <input 
              type="tel" 
              v-model="signupData.phone" 
              placeholder="Enter your phone number"
              required
            >
          </div>

          <div class="form-group">
            <label>Password *</label>
            <input 
              type="password" 
              v-model="signupData.password" 
              placeholder="Create a password"
              required
              minlength="6"
            >
          </div>

          <div class="form-group">
            <label>Confirm Password *</label>
            <input 
              type="password" 
              v-model="signupData.confirmPassword" 
              placeholder="Confirm your password"
              required
            >
          </div>

          <div class="form-group">
            <label>I want to:</label>
            <div class="user-type-options">
              <label class="user-type-option" :class="{ active: signupData.userType === 'guest' }">
                <input 
                  type="radio" 
                  v-model="signupData.userType" 
                  value="guest"
                >
                <div class="option-content">
                  <span class="option-icon">👤</span>
                  <span class="option-text">Find places to stay</span>
                  <small>Book amazing accommodations worldwide</small>
                </div>
              </label>
              <label class="user-type-option" :class="{ active: signupData.userType === 'host' }">
                <input 
                  type="radio" 
                  v-model="signupData.userType" 
                  value="host"
                >
                <div class="option-content">
                  <span class="option-icon">🏠</span>
                  <span class="option-text">Host my property</span>
                  <small>Earn income by sharing your space</small>
                </div>
              </label>
            </div>
          </div>

          <button type="submit" :disabled="loading" class="signup-btn" :class="signupData.userType">
            {{ loading ? 'Creating Account...' : `Sign Up as ${signupData.userType === 'host' ? 'Host' : 'Guest'}` }}
          </button>

          <div class="login-link">
            <p>Already have an account? <router-link to="/login">Log in</router-link></p>
            <p class="user-type-hint" v-if="signupData.userType === 'host'">
              As a host, you'll be able to list properties, manage bookings, and earn money.
            </p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Signup',
  data() {
    return {
      loading: false,
      signupData: {
        fullName: '',
        email: '',
        phone: '',
        password: '',
        confirmPassword: '',
        userType: 'guest' // Default to guest
      }
    }
  },
  methods: {
    async handleSignup() {
      if (this.signupData.password !== this.signupData.confirmPassword) {
        alert('Passwords do not match');
        return;
      }

      if (this.signupData.password.length < 6) {
        alert('Password must be at least 6 characters long');
        return;
      }

      this.loading = true;

      try {
        // Check if user already exists
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        const existingUser = users.find(user => user.email === this.signupData.email);
        
        if (existingUser) {
          alert('User with this email already exists');
          return;
        }

        // Create new user
        const newUser = {
          id: Date.now().toString(),
          fullName: this.signupData.fullName,
          email: this.signupData.email,
          phone: this.signupData.phone,
          password: this.signupData.password, // In real app, hash this
          userType: this.signupData.userType,
          emailVerified: false,
          phoneVerified: false,
          isVerified: false,
          createdAt: new Date().toISOString()
        };

        // Save user
        users.push(newUser);
        localStorage.setItem('stayhapa_users', JSON.stringify(users));

        // Set as current user
        localStorage.setItem('stayhapa_current_user', JSON.stringify(newUser));

        alert(`Account created successfully! Please verify your email and phone number to start ${this.signupData.userType === 'host' ? 'hosting' : 'booking'}.`);
        
        // Redirect to verification page
        this.$router.push('/verify-account');

      } catch (error) {
        console.error('Error during signup:', error);
        alert('Failed to create account. Please try again.');
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    // Get userType from query parameters
    const userType = this.$route.query.userType;
    if (userType && (userType === 'host' || userType === 'guest')) {
      this.signupData.userType = userType;
    }
  }
};
</script>

<style scoped>
.signup-page {
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

.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 80px);
  padding: 2rem;
}

.signup-card {
  background: white;
  padding: 3rem;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 100%;
  overflow: hidden;
}

.user-type-header {
  text-align: center;
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 2px solid #f0f0f0;
}

.user-type-badge {
  display: inline-block;
  padding: 0.25rem 1rem;
  background: #e8f4fd;
  color: #0F4C81;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  margin-bottom: 1rem;
  text-transform: uppercase;
}

.signup-card h2 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2rem;
}

.signup-card > p {
  color: #666;
  margin-bottom: 0;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  width: 100%;
  min-width: 0;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-group input {
  padding: 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
  width: 100%;
  box-sizing: border-box;
  height: 48px;
}

.form-group input:focus {
  outline: none;
  border-color: #0F4C81;
}

.user-type-options {
  display: flex;
  gap: 1rem;
  margin-top: 0.5rem;
}

.user-type-option {
  flex: 1;
  padding: 1.5rem 1rem;
  border: 2px solid #e0e0e0;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
  background: white;
}

.user-type-option:hover {
  border-color: #0F4C81;
  transform: translateY(-2px);
}

.user-type-option.active {
  border-color: #0F4C81;
  background: #f0f7ff;
  box-shadow: 0 4px 12px rgba(15, 76, 129, 0.1);
}

.user-type-option input {
  display: none;
}

.option-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 0.5rem;
}

.option-icon {
  font-size: 2rem;
  margin-bottom: 0.5rem;
}

.option-text {
  font-weight: 600;
  color: #333;
}

.user-type-option.active .option-text {
  color: #0F4C81;
}

.user-type-option small {
  color: #666;
  font-size: 0.8rem;
  line-height: 1.3;
}

.signup-btn {
  padding: 1rem;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  margin-top: 1rem;
}

.signup-btn.host {
  background: #0F4C81;
}

.signup-btn.guest {
  background: #27ae60;
}

.signup-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.signup-btn.host:hover:not(:disabled) {
  background: #0a3a6b;
}

.signup-btn.guest:hover:not(:disabled) {
  background: #219653;
}

.signup-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.login-link {
  text-align: center;
  color: #666;
  padding-top: 1rem;
  border-top: 1px solid #f0f0f0;
}

.login-link a {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

.login-link a:hover {
  text-decoration: underline;
}

.user-type-hint {
  margin-top: 1rem;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 8px;
  font-size: 0.9rem;
  color: #666;
  border-left: 4px solid #0F4C81;
}

@media (max-width: 768px) {
  .signup-container {
    padding: 1rem;
  }
  
  .signup-card {
    padding: 2rem;
  }
  
  .user-type-options {
    flex-direction: column;
  }
  
  .nav-container {
    padding: 0 1rem;
  }
}
</style>