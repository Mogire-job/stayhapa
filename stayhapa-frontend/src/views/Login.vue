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
        <h1>Welcome Back</h1>
        <p>Log in to your Stayhapa account</p>

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

          <button type="submit" class="login-btn" :disabled="loading">
            {{ loading ? 'Logging in...' : 'Log In' }}
          </button>

          <div class="signup-link">
            Don't have an account? <router-link to="/signup">Sign up</router-link>
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
      loginData: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    async handleLogin() {
      this.loading = true;

      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 1000));
        
        // Check if user exists in localStorage
        const users = JSON.parse(localStorage.getItem('stayhapa_users') || '[]');
        const user = users.find(u => u.email === this.loginData.email && u.password === this.loginData.password);

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
          alert('Invalid email or password');
        }

      } catch (error) {
        console.error('Login error:', error);
        alert('Login failed. Please try again.');
      } finally {
        this.loading = false;
      }
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
  max-width: 400px;
}

.login-card h1 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2rem;
}

.login-card p {
  color: #666;
  margin-bottom: 2rem;
}

.login-form {
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

.login-btn {
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

.login-btn:hover:not(:disabled) {
  background: #0a3a6b;
}

.login-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.signup-link {
  text-align: center;
  color: #666;
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
}
</style>