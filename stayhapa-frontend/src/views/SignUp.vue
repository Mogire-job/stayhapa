<template>
  <div class="signup-container">
    <div class="signup-card">
      <h2>Create Your Stayhapa Account</h2>
      <p>Join thousands of travelers and hosts</p>
      
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
            <label class="user-type-option">
              <input 
                type="radio" 
                v-model="signupData.userType" 
                value="guest" 
                checked
              >
              <span>Find places to stay</span>
            </label>
            <label class="user-type-option">
              <input 
                type="radio" 
                v-model="signupData.userType" 
                value="host"
              >
              <span>Host my property</span>
            </label>
          </div>
        </div>

        <button type="submit" :disabled="loading" class="signup-btn">
          {{ loading ? 'Creating Account...' : 'Create Account' }}
        </button>

        <p class="login-link">
          Already have an account? <router-link to="/login">Log in</router-link>
        </p>
      </form>
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
        userType: 'guest'
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

        alert('Account created successfully! Please verify your email and phone number.');
        
        // Redirect to verification page
        this.$router.push('/verify-account');

      } catch (error) {
        console.error('Error during signup:', error);
        alert('Failed to create account. Please try again.');
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.signup-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 2rem;
}

.signup-card {
  background: white;
  padding: 3rem;
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 100%;
}

.signup-card h2 {
  text-align: center;
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2rem;
}

.signup-card > p {
  text-align: center;
  color: #666;
  margin-bottom: 2rem;
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
  padding: 1rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
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
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  flex: 1;
}

.user-type-option:hover {
  border-color: #0F4C81;
}

.user-type-option input {
  margin: 0;
}

.user-type-option span {
  font-weight: 500;
}

.signup-btn {
  padding: 1rem;
  background: #0F4C81;
  color: white;
  border: none;
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

.login-link a:hover {
  text-decoration: underline;
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
}
</style>