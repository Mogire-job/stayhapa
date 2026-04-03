<!-- src/views/Login.vue -->
<template>
  <div class="login-page">
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link to="/signup" class="nav-link">Sign Up</router-link>
        </div>
      </div>
    </nav>

    <div class="login-container">
      <div class="login-card">
        <!-- Dynamic header based on user type -->
        <div class="user-type-header" :class="userType">
          <div class="user-type-badge">{{ userType === 'host' ? 'Host' : 'Guest' }}</div>
          <h1>{{ userType === 'host' ? 'Host Login' : 'Welcome Back' }}</h1>
          <p>{{ userType === 'host' ? 'Access your hosting dashboard' : 'Log in to your Stayhapa account' }}</p>
        </div>

        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label>Email Address</label>
            <input 
              type="email" 
              v-model="loginData.email" 
              placeholder="Enter your email"
              required
            >
          </div>

          <div class="form-group">
            <label>Password</label>
            <input 
              type="password" 
              v-model="loginData.password" 
              placeholder="Enter your password"
              required
            >
          </div>

          <button type="submit" class="login-btn" :disabled="loading" :class="userType">
            {{ loading ? 'Logging in...' : (userType === 'host' ? 'Login as Host' : 'Log In') }}
          </button>

          <div class="user-type-switch">
            <p v-if="userType === 'host'">
              Looking to book a stay? 
              <a href="#" @click.prevent="switchUserType('guest')">Login as Guest</a>
            </p>
            <p v-else>
              Want to host your property? 
              <a href="#" @click.prevent="switchUserType('host')">Login as Host</a>
            </p>
          </div>

          <div class="signup-link">
            Don't have an account? 
            <router-link :to="getSignupLink">Sign up as {{ userType === 'host' ? 'Host' : 'Guest' }}</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      userType: 'guest', // Default to guest
      loginData: {
        email: '',
        password: ''
      }
    }
  },
  computed: {
    getSignupLink() {
      return { path: '/signup', query: { userType: this.userType } };
    }
  },
  methods: {
    switchUserType(type) {
      this.userType = type;
      // Update URL without reloading
      this.$router.replace({ query: { userType: type } });
    },

    async handleLogin() {
      this.loading = true;

      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1000));
        
        // Check if user exists in localStorage
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        const user = users.find(u => 
          u.email === this.loginData.email && 
          u.password === this.loginData.password &&
          u.userType === this.userType // Check user type matches
        );

        if (user) {
          // Save current user session with all user data
          localStorage.setItem('stayhapa_current_user', JSON.stringify({
            id: user.id,
            fullName: user.fullName,
            email: user.email,
            phone: user.phone,
            userType: user.userType,
            emailVerified: user.emailVerified || false,
            phoneVerified: user.phoneVerified || false,
            isVerified: user.isVerified || false,
            createdAt: user.createdAt
          }));

          // Check verification status and redirect accordingly
          if (!user.isVerified) {
            this.$router.push('/verify-account');
          } else {
            // Redirect based on user type
            if (user.userType === 'host') {
              this.$router.push('/host/dashboard');
            } else {
              this.$router.push('/guest/dashboard');
            }
          }
        } else {
          // Check if user exists but wrong user type
          const userExists = users.find(u => u.email === this.loginData.email && u.password === this.loginData.password);
          if (userExists) {
            alert(`This account is registered as a ${userExists.userType}. Please login as ${userExists.userType === 'host' ? 'Host' : 'Guest'}.`);
            this.userType = userExists.userType;
            this.$router.replace({ query: { userType: userExists.userType } });
          } else {
            alert('Invalid email or password');
          }
        }

      } catch (error) {
        console.error('Login error:', error);
        alert('Login failed. Please try again.');
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    // Get userType from query parameters
    const userType = this.$route.query.userType;
    if (userType && (userType === 'host' || userType === 'guest')) {
      this.userType = userType;
    }
  }
}
</script>

<style scoped>
.login-page {
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

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 80px);
  padding: 2rem;
}

.login-card {
  background: white;
  padding: 3rem;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 450px;
  overflow: hidden;
}

.user-type-header {
  text-align: center;
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 2px solid #f0f0f0;
}

.user-type-header.host {
  border-bottom-color: #0F4C81;
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

.user-type-header.host .user-type-badge {
  background: #0F4C81;
  color: white;
}

.login-card h1 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2rem;
}

.login-card p {
  color: #666;
  margin-bottom: 0;
}

.login-form {
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
  padding: 0.75rem;
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

.login-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 1rem;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
}

.login-btn.host {
  background: #0F4C81;
}

.login-btn.guest {
  background: #27ae60;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.login-btn.host:hover:not(:disabled) {
  background: #0a3a6b;
}

.login-btn.guest:hover:not(:disabled) {
  background: #219653;
}

.login-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.user-type-switch {
  text-align: center;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 8px;
  margin: 0.5rem 0;
}

.user-type-switch p {
  margin: 0;
  color: #666;
  font-size: 0.9rem;
}

.user-type-switch a {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

.user-type-switch a:hover {
  text-decoration: underline;
}

.signup-link {
  text-align: center;
  color: #666;
  padding-top: 1rem;
  border-top: 1px solid #f0f0f0;
}

.signup-link a {
  color: #0F4C81;
  text-decoration: none;
  font-weight: 600;
}

@media (max-width: 768px) {
  .login-card {
    padding: 2rem;
    margin: 1rem;
  }
  
  .nav-container {
    padding: 0 1rem;
  }
}
</style>