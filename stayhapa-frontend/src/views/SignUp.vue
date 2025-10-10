<!-- src/views/SignUp.vue -->
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
          <router-link to="/login" class="nav-link">Login</router-link>
        </div>
      </div>
    </nav>

    <div class="signup-container">
      <div class="signup-card">
        <h1>Create Your Account</h1>
        <p>Join Stayhapa as a {{ userType === 'host' ? 'Host' : 'Traveler' }}</p>

        <!-- User Type Selection -->
        <div class="user-type-selector">
          <button 
            class="type-btn" 
            :class="{ active: userType === 'traveler' }"
            @click="userType = 'traveler'"
          >
            🧳 Traveler
          </button>
          <button 
            class="type-btn" 
            :class="{ active: userType === 'host' }"
            @click="userType = 'host'"
          >
            🏠 Host
          </button>
        </div>

        <form @submit.prevent="handleSignup" class="signup-form">
          <div class="form-group">
            <label>Full Name</label>
            <input 
              type="text" 
              v-model="formData.fullName" 
              placeholder="Enter your full name"
              required
            >
          </div>

          <div class="form-group">
            <label>Email Address</label>
            <input 
              type="email" 
              v-model="formData.email" 
              placeholder="Enter your email"
              required
            >
          </div>

          <div class="form-group">
            <label>Password</label>
            <input 
              type="password" 
              v-model="formData.password" 
              placeholder="Create a password"
              required
              minlength="6"
            >
          </div>

          <div class="form-group">
            <label>Confirm Password</label>
            <input 
              type="password" 
              v-model="formData.confirmPassword" 
              placeholder="Confirm your password"
              required
            >
          </div>

          <!-- Host Specific Fields -->
          <div v-if="userType === 'host'" class="host-fields">
            <div class="form-group">
              <label>Phone Number</label>
              <input 
                type="tel" 
                v-model="formData.phone" 
                placeholder="Enter your phone number"
                required
              >
            </div>
            <div class="form-group">
              <label>Address</label>
              <input 
                type="text" 
                v-model="formData.address" 
                placeholder="Enter your address"
                required
              >
            </div>
          </div>

          <div class="form-group checkbox-group">
            <input type="checkbox" v-model="formData.agreeToTerms" required>
            <label>I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></label>
          </div>

          <button type="submit" class="signup-btn" :disabled="loading">
            {{ loading ? 'Creating Account...' : `Sign Up as ${userType === 'host' ? 'Host' : 'Traveler'}` }}
          </button>

          <div class="login-link">
            Already have an account? <router-link to="/login">Log in</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignUp',
  data() {
    return {
      userType: 'traveler', // Default to traveler
      loading: false,
      formData: {
        fullName: '',
        email: '',
        password: '',
        confirmPassword: '',
        phone: '',
        address: '',
        agreeToTerms: false
      }
    }
  },
  methods: {
    async handleSignup() {
      if (this.formData.password !== this.formData.confirmPassword) {
        alert('Passwords do not match!');
        return;
      }

      this.loading = true;

      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1500));
        
        const userData = {
          ...this.formData,
          userType: this.userType,
          id: Date.now(),
          createdAt: new Date().toISOString()
        };

        // Save to localStorage (in real app, this would be an API call)
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        users.push(userData);
        localStorage.setItem('stayhapa_users', JSON.stringify(users));

        // Save current user session
        localStorage.setItem('stayhapa_current_user', JSON.stringify({
          id: userData.id,
          fullName: userData.fullName,
          email: userData.email,
          userType: this.userType
        }));

        // Redirect based on user type
        if (this.userType === 'host') {
          this.$router.push('/host/dashboard');
        } else {
          this.$router.push('/dashboard');
        }

      } catch (error) {
        console.error('Signup error:', error);
        alert('Signup failed. Please try again.');
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    // Check if user type is passed in query params
    const type = this.$route.query.type;
    if (type && ['traveler', 'host'].includes(type)) {
      this.userType = type;
    }
  }
}
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
  width: 100%;
  max-width: 500px;
}

.signup-card h1 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2rem;
}

.signup-card p {
  color: #666;
  margin-bottom: 2rem;
}

.user-type-selector {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  margin-bottom: 2rem;
}

.type-btn {
  padding: 1rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  background: white;
  cursor: pointer;
  transition: all 0.3s;
  font-size: 1rem;
}

.type-btn.active {
  border-color: #0F4C81;
  background: #0F4C81;
  color: white;
}

.type-btn:hover:not(.active) {
  border-color: #0F4C81;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-group input {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-group input:focus {
  outline: none;
  border-color: #0F4C81;
}

.checkbox-group {
  flex-direction: row;
  align-items: center;
  gap: 0.5rem;
}

.checkbox-group input {
  width: auto;
}

.checkbox-group label {
  margin-bottom: 0;
  font-weight: normal;
}

.checkbox-group a {
  color: #0F4C81;
  text-decoration: none;
}

.host-fields {
  border-top: 1px solid #e0e0e0;
  padding-top: 1.5rem;
  margin-top: 1rem;
}

.signup-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 1rem;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.signup-btn:hover:not(:disabled) {
  background: #0a3a6b;
}

.signup-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.login-link {
  text-align: center;
  color: #666;
}

.login-link a {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

@media (max-width: 768px) {
  .signup-card {
    padding: 2rem;
    margin: 1rem;
  }
  
  .user-type-selector {
    grid-template-columns: 1fr;
  }
}
</style>