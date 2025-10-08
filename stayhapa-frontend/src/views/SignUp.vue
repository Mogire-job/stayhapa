<template>
  <div class="signup-container">
    <div class="container">
      <div class="left-panel">
        <div class="logo">Stayhapa</div>
        <h1 class="tagline">Join Stayhapa to discover amazing stays</h1>
        <p class="description">Start your journey with thousands of hosts and travelers across Kenya</p>
      </div>
      
      <div class="right-panel">
        <div class="form-container">
          <h2 class="form-title">Create Account</h2>
          <p class="form-subtitle">Join our community of hosts and travelers</p>
          
          <!-- Success message after account creation -->
          <div v-if="accountCreated" class="success-message">
            Account created successfully! Please check your email for the OTP code.
          </div>
          
          <!-- Error message for form validation -->
          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>
          
          <!-- Signup Form -->
          <form v-if="!showOtpVerification" @submit.prevent="submitSignupForm">
            <div class="form-row">
              <div class="form-group">
                <label for="first-name">First Name</label>
                <input type="text" id="first-name" v-model="signupForm.firstName" placeholder="Enter your first name" required>
              </div>
              <div class="form-group">
                <label for="last-name">Last Name</label>
                <input type="text" id="last-name" v-model="signupForm.lastName" placeholder="Enter your last name" required>
              </div>
            </div>
            
            <div class="form-group">
              <label for="email">Email Address</label>
              <input type="email" id="email" v-model="signupForm.email" placeholder="Enter your email" required>
            </div>
            
            <div class="form-group">
              <label for="phone">Phone Number</label>
              <input type="tel" id="phone" v-model="signupForm.phone" placeholder="+254 XXX XXX XXX" required>
            </div>
            
            <div class="form-group">
              <label for="password">Password</label>
              <input type="password" id="password" v-model="signupForm.password" placeholder="Create a password" required>
              <div class="password-strength">
                <div class="strength-meter" :style="{width: passwordStrength + '%', backgroundColor: passwordStrengthColor}"></div>
              </div>
            </div>
            
            <div class="form-group">
              <label for="confirm-password">Confirm Password</label>
              <input type="password" id="confirm-password" v-model="signupForm.confirmPassword" placeholder="Confirm your password" required>
            </div>
            
            <div class="checkbox-group">
              <input type="checkbox" id="terms" v-model="signupForm.agreeToTerms" required>
              <label for="terms">I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></label>
            </div>
            
            <button type="submit" class="btn-primary">Create Account</button>
          </form>
          
          <!-- OTP Verification Form -->
          <div v-if="showOtpVerification" class="otp-container">
            <h3>Verify Your Email</h3>
            <p>We've sent a 6-digit code to {{ signupForm.email }}</p>
            
            <div class="otp-inputs">
              <input v-for="(digit, index) in otpDigits" 
                     :key="index" 
                     type="text" 
                     maxlength="1" 
                     class="otp-input"
                     v-model="otpDigits[index]"
                     @input="handleOtpInput(index, $event)"
                     @keydown="handleOtpKeydown(index, $event)"
                     ref="otpInputs">
            </div>
            
            <button @click="verifyOtp" class="btn-primary">Verify OTP</button>
            
            <div class="resend-otp" @click="resendOtp">
              Didn't receive the code? Resend OTP
            </div>
          </div>
          
          <div class="divider" v-if="!showOtpVerification">
            <span>or sign up with</span>
          </div>
          
          <div class="social-buttons" v-if="!showOtpVerification">
            <button class="social-btn">
              <img src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTgiIGhlaWdodD0iMTgiIHZpZXdCb3g9IjAgMCAxOCAxOCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTE2LjUgOUgxMlYxMi41SDkuNVY5SDUuNVY2LjVIOFYyLjVIMTBWNi41SDE0LjVWOVoiIGZpbGw9IiMwMDAwMDAiLz4KPC9zdmc+Cg==" alt="Google">
              Google
            </button>
            <button class="social-btn">
              <img src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTgiIGhlaWdodD0iMTgiIHZpZXdCb3g9IjAgMCAxOCAxOCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTE2LjUgOUgxMlYxMi41SDkuNVY5SDUuNVY2LjVIOFYyLjVIMTBWNi41SDE0LjVWOVoiIGZpbGw9IiMwMDAwMDAiLz4KPC9zdmc+Cg==" alt="Facebook">
              Facebook
            </button>
            <button class="social-btn">
              <img src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTgiIGhlaWdodD0iMTgiIHZpZXdCb3g9IjAgMCAxOCAxOCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTE2LjUgOUgxMlYxMi41SDkuNVY5SDUuNVY2LjVIOFYyLjVIMTBWNi41SDE0LjVWOVoiIGZpbGw9IiMwMDAwMDAiLz4KPC9zdmc+Cg==" alt="Apple">
              Apple
            </button>
          </div>
          
          <p class="login-link" v-if="!showOtpVerification">Already have an account? <a href="#" @click.prevent="goToLogin">Sign in</a></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignUp',
  data() {
    return {
      signupForm: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '+254',
        password: '',
        confirmPassword: '',
        agreeToTerms: false
      },
      showOtpVerification: false,
      accountCreated: false,
      errorMessage: '',
      otpDigits: ['', '', '', '', '', ''],
      generatedOtp: ''
    }
  },
  computed: {
    passwordStrength() {
      const password = this.signupForm.password;
      let strength = 0;
      
      if (password.length >= 8) strength += 25;
      if (/[a-z]/.test(password)) strength += 25;
      if (/[A-Z]/.test(password)) strength += 25;
      if (/[0-9]/.test(password)) strength += 25;
      
      return strength;
    },
    passwordStrengthColor() {
      if (this.passwordStrength < 50) {
        return '#e74c3c';
      } else if (this.passwordStrength < 75) {
        return '#f39c12';
      } else {
        return '#2ecc71';
      }
    }
  },
  methods: {
    submitSignupForm() {
      this.errorMessage = '';
      
      // Validation
      if (this.signupForm.password !== this.signupForm.confirmPassword) {
        this.errorMessage = 'Passwords do not match!';
        return;
      }
      
      if (!this.signupForm.agreeToTerms) {
        this.errorMessage = 'You must agree to the Terms of Service and Privacy Policy';
        return;
      }
      
      // Simulate API call to create account
      this.simulateCreateAccount();
    },
    
    simulateCreateAccount() {
      // Generate a random 6-digit OTP
      this.generatedOtp = Math.floor(100000 + Math.random() * 900000).toString();
      
      // In a real app, you would send this OTP to the user's email
      console.log(`OTP for ${this.signupForm.email}: ${this.generatedOtp}`);
      
      // Show success message and OTP verification
      this.accountCreated = true;
      this.showOtpVerification = true;
      
      // Clear form after submission
      setTimeout(() => {
        this.accountCreated = false;
      }, 5000);
    },
    
    handleOtpInput(index, event) {
      const value = event.target.value;
      
      // Only allow numbers
      if (!/^\d*$/.test(value)) {
        this.otpDigits[index] = '';
        return;
      }
      
      // Auto-focus next input
      if (value && index < 5) {
        this.$nextTick(() => {
          this.$refs.otpInputs[index + 1].focus();
        });
      }
    },
    
    handleOtpKeydown(index, event) {
      if (event.key === 'Backspace' && !this.otpDigits[index] && index > 0) {
        this.$nextTick(() => {
          this.$refs.otpInputs[index - 1].focus();
        });
      }
    },
    
    verifyOtp() {
      const enteredOtp = this.otpDigits.join('');
      
      if (enteredOtp === this.generatedOtp) {
        // Success - redirect to login page
        alert('Account verified successfully! Redirecting to login page...');
        this.goToLogin();
      } else {
        this.errorMessage = 'Invalid OTP code. Please try again.';
      }
    },
    
    resendOtp() {
      // Generate a new OTP
      this.generatedOtp = Math.floor(100000 + Math.random() * 900000).toString();
      
      // In a real app, you would resend the OTP to the user's email
      console.log(`New OTP for ${this.signupForm.email}: ${this.generatedOtp}`);
      
      // Clear OTP inputs
      this.otpDigits = ['', '', '', '', '', ''];
      
      // Focus on first input
      this.$nextTick(() => {
        if (this.$refs.otpInputs.length > 0) {
          this.$refs.otpInputs[0].focus();
        }
      });
      
      // Show success message
      this.errorMessage = 'New OTP sent to your email!';
      setTimeout(() => {
        this.errorMessage = '';
      }, 3000);
    },
    
    goToLogin() {
      // Use Vue Router to navigate to login page
      this.$router.push('/login');
      
      // Reset form
      this.signupForm = {
        firstName: '',
        lastName: '',
        email: '',
        phone: '+254',
        password: '',
        confirmPassword: '',
        agreeToTerms: false
      };
      this.showOtpVerification = false;
      this.accountCreated = false;
      this.errorMessage = '';
      this.otpDigits = ['', '', '', '', '', ''];
    }
  }
}
</script>

<style scoped>
.signup-container {
  background-color: #f8f9fa;
  color: #333;
  line-height: 1.6;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.container {
  display: flex;
  max-width: 1000px;
  width: 100%;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.left-panel {
  flex: 1;
  background: linear-gradient(135deg, #3a8ffe 0%, #6c5ce7 100%);
  color: white;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.logo {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 20px;
}

.tagline {
  font-size: 24px;
  margin-bottom: 30px;
  font-weight: 600;
}

.description {
  font-size: 16px;
  max-width: 400px;
  opacity: 0.9;
}

.right-panel {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-container {
  max-width: 450px;
  width: 100%;
  margin: 0 auto;
}

.form-title {
  font-size: 28px;
  margin-bottom: 10px;
  color: #2d3436;
}

.form-subtitle {
  color: #636e72;
  margin-bottom: 30px;
}

.form-row {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.form-group {
  flex: 1;
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #2d3436;
}

input[type="text"],
input[type="email"],
input[type="tel"],
input[type="password"] {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 16px;
  transition: border 0.3s;
}

input:focus {
  border-color: #3a8ffe;
  outline: none;
  box-shadow: 0 0 0 2px rgba(58, 143, 254, 0.2);
}

.password-strength {
  height: 4px;
  background-color: #eee;
  border-radius: 2px;
  margin-top: 5px;
  overflow: hidden;
}

.strength-meter {
  height: 100%;
  width: 0;
  transition: width 0.3s, background-color 0.3s;
}

.checkbox-group {
  display: flex;
  align-items: flex-start;
  margin-bottom: 25px;
}

.checkbox-group input {
  margin-right: 10px;
  margin-top: 3px;
}

.checkbox-group label {
  font-size: 14px;
  color: #636e72;
}

.checkbox-group a {
  color: #3a8ffe;
  text-decoration: none;
}

.btn-primary {
  width: 100%;
  padding: 14px;
  background-color: #3a8ffe;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-primary:hover {
  background-color: #2a7de1;
}

.divider {
  text-align: center;
  margin: 25px 0;
  position: relative;
  color: #636e72;
}

.divider::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background-color: #ddd;
  z-index: 1;
}

.divider span {
  background-color: white;
  padding: 0 15px;
  position: relative;
  z-index: 2;
}

.social-buttons {
  display: flex;
  gap: 15px;
  margin-bottom: 30px;
}

.social-btn {
  flex: 1;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background-color 0.3s;
}

.social-btn:hover {
  background-color: #f8f9fa;
}

.social-btn img {
  width: 20px;
  height: 20px;
  margin-right: 8px;
}

.login-link {
  text-align: center;
  color: #636e72;
}

.login-link a {
  color: #3a8ffe;
  text-decoration: none;
  font-weight: 500;
}

.otp-container {
  text-align: center;
  margin-top: 20px;
}

.otp-inputs {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin: 20px 0;
}

.otp-input {
  width: 40px;
  height: 40px;
  text-align: center;
  font-size: 18px;
  border: 1px solid #ddd;
  border-radius: 6px;
}

.otp-input:focus {
  border-color: #3a8ffe;
  outline: none;
  box-shadow: 0 0 0 2px rgba(58, 143, 254, 0.2);
}

.resend-otp {
  margin-top: 15px;
  color: #3a8ffe;
  cursor: pointer;
}

.success-message {
  background-color: #d4edda;
  color: #155724;
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 20px;
  text-align: center;
}

.error-message {
  background-color: #f8d7da;
  color: #721c24;
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 20px;
  text-align: center;
}

@media (max-width: 768px) {
  .container {
    flex-direction: column;
  }
  
  .left-panel {
    padding: 30px 20px;
  }
  
  .right-panel {
    padding: 30px 20px;
  }
  
  .form-row {
    flex-direction: column;
    gap: 0;
  }
}
</style>