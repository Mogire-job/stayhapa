<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <router-link to="/" class="logo">
            <h2>Stayhapa</h2>
          </router-link>
          <h1>Welcome back</h1>
          <p>Sign in to your account to continue</p>
        </div>

        <form @submit.prevent="handleLogin" class="auth-form">
          <div class="form-group">
            <label for="email">Email Address</label>
            <input 
              type="email" 
              id="email" 
              v-model="form.email" 
              required
              placeholder="Enter your email"
            >
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input 
              type="password" 
              id="password" 
              v-model="form.password" 
              required
              placeholder="Enter your password"
            >
          </div>

          <div class="form-options">
            <label class="checkbox">
              <input type="checkbox" v-model="form.rememberMe">
              <span class="checkmark"></span>
              Remember me
            </label>
            <router-link to="/forgot-password" class="forgot-link">Forgot password?</router-link>
          </div>

          <button type="submit" class="auth-btn" :disabled="loading">
            {{ loading ? 'Signing in...' : 'Sign In' }}
          </button>
        </form>

        <div class="auth-divider">
          <span>or continue with</span>
        </div>

        <div class="social-auth">
          <button class="social-btn google" @click="socialLogin('google')">
            <span class="social-icon">🔍</span>
            Google
          </button>
          <button class="social-btn facebook" @click="socialLogin('facebook')">
            <span class="social-icon">📘</span>
            Facebook
          </button>
          <button class="social-btn apple" @click="socialLogin('apple')">
            <span class="social-icon">🍎</span>
            Apple
          </button>
        </div>

        <div class="auth-footer">
          <p>Don't have an account? <router-link to="/signup" class="auth-link">Sign up</router-link></p>
        </div>
      </div>

      <div class="auth-image">
        <img src="https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600" alt="Beautiful Stay">
        <div class="image-overlay">
          <h2>Find Your Perfect Stay</h2>
          <p>Discover unique accommodations and create unforgettable memories with Stayhapa</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      loading: false,
      form: {
        email: '',
        password: '',
        rememberMe: false
      }
    }
  },
  methods: {
    async handleLogin() {
      this.loading = true;
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      console.log('Login attempt:', this.form);
      // In real app, this would call your authentication API
      
      this.loading = false;
      this.$router.push('/dashboard');
    },
    
    socialLogin(provider) {
      console.log(`Social login with ${provider}`);
      // Implement social login logic
    }
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
  padding: 2rem;
}

.auth-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  max-width: 1000px;
  width: 100%;
  background: white;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
}

.auth-card {
  padding: 3rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.auth-header {
  text-align: center;
  margin-bottom: 2rem;
}

.logo h2 {
  color: #0F4C81;
  font-size: 1.8rem;
  margin-bottom: 1rem;
}

.auth-header h1 {
  margin: 0 0 0.5rem 0;
  color: #333;
  font-size: 2rem;
}

.auth-header p {
  color: #666;
  margin: 0;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
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

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  font-size: 0.9rem;
}

.checkbox input {
  display: none;
}

.checkmark {
  width: 18px;
  height: 18px;
  border: 2px solid #e0e0e0;
  border-radius: 4px;
  position: relative;
  transition: all 0.3s;
}

.checkbox input:checked + .checkmark {
  background: #0F4C81;
  border-color: #0F4C81;
}

.checkbox input:checked + .checkmark:after {
  content: '✓';
  color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 12px;
}

.forgot-link {
  color: #0F4C81;
  text-decoration: none;
  font-size: 0.9rem;
}

.forgot-link:hover {
  text-decoration: underline;
}

.auth-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 1rem;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s;
}

.auth-btn:hover:not(:disabled) {
  background: #0a3a6b;
}

.auth-btn:disabled {
  background: #6c757d;
  cursor: not-allowed;
}

.auth-divider {
  display: flex;
  align-items: center;
  margin: 2rem 0;
  color: #666;
}

.auth-divider::before,
.auth-divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: #e0e0e0;
}

.auth-divider span {
  padding: 0 1rem;
  font-size: 0.9rem;
}

.social-auth {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
}

.social-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background: white;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s;
}

.social-btn:hover {
  border-color: #0F4C81;
  color: #0F4C81;
}

.social-icon {
  font-size: 1.2rem;
}

.auth-footer {
  text-align: center;
}

.auth-footer p {
  color: #666;
  margin: 0;
}

.auth-link {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

.auth-link:hover {
  text-decoration: underline;
}

.auth-image {
  position: relative;
  background: #0F4C81;
}

.auth-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(15, 76, 129, 0.8);
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 2rem;
}

.image-overlay h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.image-overlay p {
  font-size: 1.1rem;
  opacity: 0.9;
  max-width: 400px;
}

/* Responsive Design */
@media (max-width: 768px) {
  .auth-container {
    grid-template-columns: 1fr;
  }
  
  .auth-image {
    display: none;
  }
  
  .auth-card {
    padding: 2rem;
  }
}

@media (max-width: 480px) {
  .auth-page {
    padding: 1rem;
  }
  
  .form-options {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }
}
</style>