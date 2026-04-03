<template>
  <div class="auth-page">
    <!-- Top Navbar -->
    <nav class="top-navbar">
      <div class="nav-container">
        <router-link to="/" class="nav-logo">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none">
            <path d="M3 21h18M5 21V7l7-4 7 4v14M9 21v-6h6v6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span>Stayhapa</span>
        </router-link>
        <div class="nav-actions">
          <router-link to="/login" class="nav-link">Log in</router-link>
        </div>
      </div>
    </nav>

    <!-- Left Side - Visual/Info -->
    <div class="auth-visual">
      <div class="visual-slideshow">
        <div 
          v-for="(img, index) in heroImages" 
          :key="index"
          class="visual-slide"
          :class="{ active: currentSlide === index }"
          :style="{ backgroundImage: `url(${img})` }"
        ></div>
        <div class="visual-overlay"></div>
      </div>
      
      <div class="visual-content">
        <div class="welcome-message">
          <h1>Join Stayhapa</h1>
          <p>Create an account to start booking unique stays or list your property and earn extra income.</p>
        </div>
        <div class="slide-indicators">
          <span 
            v-for="(_, index) in heroImages" 
            :key="index"
            class="indicator"
            :class="{ active: currentSlide === index }"
            @click="currentSlide = index"
          ></span>
        </div>
      </div>
    </div>

    <!-- Right Side - Form -->
    <div class="auth-form-side">
      <div class="form-container">
        <div class="form-header">
          <h2>Create Account</h2>
          <p>Fill in your details to get started</p>
        </div>

        <!-- User Type Toggle -->
        <div class="user-type-toggle">
          <button 
            type="button"
            :class="{ active: signupData.userType === 'guest' }" 
            @click="signupData.userType = 'guest'"
          >
            I'm a Guest
          </button>
          <button 
            type="button"
            :class="{ active: signupData.userType === 'host' }" 
            @click="signupData.userType = 'host'"
          >
            I'm a Host
          </button>
        </div>

        <!-- Social Login -->
        <div class="social-login">
          <button type="button" class="social-btn google">
            <svg width="20" height="20" viewBox="0 0 24 24">
              <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
              <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
            </svg>
            Sign up with Google
          </button>
          <button type="button" class="social-btn apple">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
              <path d="M18.71 19.5c-.83 1.24-1.71 2.45-3.05 2.47-1.34.03-1.77-.79-3.29-.79-1.53 0-2 .77-3.27.82-1.31.05-2.3-1.32-3.14-2.53C4.25 17 2.94 12.45 4.7 9.39c.87-1.52 2.43-2.48 4.12-2.51 1.28-.02 2.5.87 3.29.87.78 0 2.26-1.07 3.81-.91.65.03 2.47.26 3.64 1.98-.09.06-2.17 1.28-2.15 3.81.03 3.02 2.65 4.03 2.68 4.04-.03.07-.42 1.44-1.38 2.83M13 3.5c.73-.83 1.94-1.46 2.94-1.5.13 1.17-.34 2.35-1.04 3.19-.69.85-1.83 1.51-2.95 1.42-.15-1.15.41-2.35 1.05-3.11z"/>
            </svg>
            Sign up with Apple
          </button>
        </div>

        <div class="divider">
          <span>or</span>
        </div>

        <form @submit.prevent="handleSignup" class="signup-form">
          <div class="form-group">
            <label>Full Name</label>
            <input 
              type="text" 
              v-model="signupData.fullName" 
              placeholder="Enter your full name"
              required
            >
          </div>

          <div class="form-group">
            <label>Email Address</label>
            <input 
              type="email" 
              v-model="signupData.email" 
              placeholder="your@email.com"
              required
            >
          </div>

          <div class="form-group">
            <label>Password</label>
            <input 
              type="password" 
              v-model="signupData.password" 
              placeholder="Create a password (min 6 chars)"
              required
              minlength="6"
            >
          </div>

          <div class="form-group">
            <label>Confirm Password</label>
            <input 
              type="password" 
              v-model="signupData.confirmPassword" 
              placeholder="Confirm your password"
              required
            >
          </div>

          <button type="submit" class="submit-btn" :class="signupData.userType" :disabled="loading">
            <span v-if="loading" class="spinner"></span>
            {{ loading ? 'Creating Account...' : 'Create Account' }}
          </button>
        </form>

        <div class="auth-footer">
          <p>Already have an account? <router-link to="/login">Log in</router-link></p>
        </div>
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
      currentSlide: 0,
      slideInterval: null,
      signupData: {
        fullName: '',
        email: '',
        phone: '',
        password: '',
        confirmPassword: '',
        userType: 'guest'
      },
      heroImages: [
        'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=1600',
        'https://images.unsplash.com/photo-1582719508461-905c673771fd?w=1600',
        'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=1600'
      ]
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
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        const existingUser = users.find(user => user.email === this.signupData.email);
        
        if (existingUser) {
          alert('User with this email already exists');
          return;
        }

        const newUser = {
          id: Date.now().toString(),
          fullName: this.signupData.fullName,
          email: this.signupData.email,
          password: this.signupData.password,
          userType: this.signupData.userType,
          isVerified: true,
          createdAt: new Date().toISOString()
        };

        users.push(newUser);
        localStorage.setItem('stayhapa_users', JSON.stringify(users));

        alert('Account created successfully! You can now log in.');
        this.$router.push('/login');
      } catch (error) {
        console.error('Error during signup:', error);
        alert('Failed to create account. Please try again.');
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    const userType = this.$route.query.userType;
    if (userType && (userType === 'host' || userType === 'guest')) {
      this.signupData.userType = userType;
    }
    
    this.slideInterval = setInterval(() => {
      this.currentSlide = (this.currentSlide + 1) % this.heroImages.length;
    }, 5000);
  },
  beforeDestroy() {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
  }
};
</script>

<style scoped>
.auth-page {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.top-navbar {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  padding: 1rem 2rem;
  background: transparent;
}

.top-navbar .nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1400px;
  margin: 0 auto;
}

.nav-logo {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  text-decoration: none;
  color: white;
  font-size: 1.25rem;
  font-weight: 700;
}

.nav-logo svg {
  color: white;
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: 600;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: all 0.3s;
}

.nav-link:hover {
  background: rgba(255, 255, 255, 0.15);
}

.auth-visual {
  position: absolute;
  top: 0;
  left: 0;
  width: 50%;
  height: 100%;
  overflow: hidden;
}

.visual-slideshow {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.visual-slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  opacity: 0;
  transition: opacity 1.2s ease-in-out;
}

.visual-slide.active {
  opacity: 1;
}

.visual-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(15, 76, 129, 0.85) 0%, rgba(45, 55, 72, 0.9) 100%);
}

.visual-content {
  position: relative;
  z-index: 2;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 5rem 2.5rem 2.5rem;
}

.welcome-message {
  max-width: 400px;
}

.welcome-message h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: white;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.welcome-message p {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.85);
  line-height: 1.6;
}

.slide-indicators {
  display: flex;
  gap: 0.5rem;
}

.indicator {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  cursor: pointer;
  transition: all 0.3s;
}

.indicator.active {
  background: white;
  width: 30px;
  border-radius: 5px;
}

.auth-form-side {
  width: 50%;
  margin-left: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fafbfc;
  padding: 3rem 2rem;
  min-height: 100vh;
  box-sizing: border-box;
}

.form-container {
  width: 100%;
  max-width: 380px;
}

.form-header {
  margin-bottom: 1.25rem;
}

.form-header h2 {
  font-size: 1.5rem;
  font-weight: 700;
  color: #1a1a2e;
  margin-bottom: 0.35rem;
}

.form-header p {
  color: #6b7280;
  font-size: 0.9rem;
}

.user-type-toggle {
  display: flex;
  background: #f3f4f6;
  border-radius: 10px;
  padding: 4px;
  margin-bottom: 1.25rem;
}

.user-type-toggle button {
  flex: 1;
  padding: 0.625rem;
  border: none;
  background: transparent;
  border-radius: 8px;
  font-size: 0.85rem;
  font-weight: 500;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.2s;
}

.user-type-toggle button.active {
  background: white;
  color: #0F4C81;
  font-weight: 600;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.social-login {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.social-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.7rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: white;
  font-size: 0.9rem;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  transition: all 0.3s;
}

.social-btn:hover {
  border-color: #d1d5db;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.divider {
  display: flex;
  align-items: center;
  color: #9ca3af;
  font-size: 0.8rem;
  margin-bottom: 1rem;
}

.divider::before,
.divider::after {
  content: '';
  flex: 1;
  border-bottom: 1px solid #e5e7eb;
}

.divider span {
  padding: 0 0.75rem;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 0.35rem;
  color: #374151;
  font-size: 0.85rem;
}

.form-group input {
  padding: 0.75rem 0.875rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 0.95rem;
  transition: all 0.3s;
  background: white;
}

.form-group input:focus {
  outline: none;
  border-color: #0F4C81;
  box-shadow: 0 0 0 3px rgba(15, 76, 129, 0.1);
}

.submit-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.8rem;
  border: none;
  border-radius: 8px;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  color: white;
  margin-top: 0.5rem;
}

.submit-btn.guest {
  background: #10b981;
}

.submit-btn.host {
  background: #0F4C81;
}

.submit-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.submit-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.auth-footer {
  text-align: center;
  margin-top: 1rem;
  color: #6b7280;
  font-size: 0.9rem;
}

.auth-footer a {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

.auth-footer a:hover {
  text-decoration: underline;
}

@media (max-width: 968px) {
  .auth-page {
    flex-direction: column;
  }
  
  .top-navbar {
    background: transparent;
  }
  
  .auth-visual {
    position: relative;
    width: 100%;
    height: 280px;
    top: 0;
    left: 0;
  }
  
  .auth-form-side {
    width: 100%;
    margin-left: 0;
    padding: 2rem 1.5rem;
    min-height: auto;
  }
  
  .visual-content {
    padding: 3rem 1.5rem 1.5rem;
  }
  
  .welcome-message h1 {
    font-size: 2rem;
  }
}
</style>