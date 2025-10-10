<template>
  <div class="verification-container">
    <div class="verification-card">
      <h2>Verify Your Account</h2>
      <p>We've sent a verification code to your email and phone number</p>
      
      <div class="verification-methods">
        <div class="verification-method">
          <h3>Email Verification</h3>
          <p>Code sent to: {{ userEmail }}</p>
          <input 
            v-model="emailCode" 
            type="text" 
            placeholder="Enter email code" 
            maxlength="6"
            class="code-input"
          >
          <button @click="verifyEmail" :disabled="emailVerifying" class="verify-btn">
            {{ emailVerifying ? 'Verifying...' : 'Verify Email' }}
          </button>
        </div>

        <div class="verification-method">
          <h3>SMS Verification</h3>
          <p>Code sent to: {{ userPhone }}</p>
          <input 
            v-model="smsCode" 
            type="text" 
            placeholder="Enter SMS code" 
            maxlength="6"
            class="code-input"
          >
          <button @click="verifySMS" :disabled="smsVerifying" class="verify-btn">
            {{ smsVerifying ? 'Verifying...' : 'Verify SMS' }}
          </button>
        </div>
      </div>

      <div class="resend-options">
        <button @click="resendEmailCode" :disabled="resendEmailCooldown > 0" class="resend-btn">
          {{ resendEmailCooldown > 0 ? `Resend Email (${resendEmailCooldown}s)` : 'Resend Email Code' }}
        </button>
        <button @click="resendSMSCode" :disabled="resendSMSCooldown > 0" class="resend-btn">
          {{ resendSMSCooldown > 0 ? `Resend SMS (${resendSMSCooldown}s)` : 'Resend SMS Code' }}
        </button>
      </div>

      <div v-if="isFullyVerified" class="success-message">
        <h3>✅ Account Verified Successfully!</h3>
        <p>You can now access all features of Stayhapa</p>
        <button @click="redirectToDashboard" class="continue-btn">
          Continue to Dashboard
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EmailVerification',
  data() {
    return {
      userEmail: '',
      userPhone: '',
      emailCode: '',
      smsCode: '',
      emailVerifying: false,
      smsVerifying: false,
      emailVerified: false,
      smsVerified: false,
      resendEmailCooldown: 0,
      resendSMSCooldown: 0
    };
  },
  computed: {
    isFullyVerified() {
      return this.emailVerified && this.smsVerified;
    }
  },
  mounted() {
    this.loadUserData();
    this.generateVerificationCodes();
  },
  methods: {
    loadUserData() {
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        const user = JSON.parse(userData);
        this.userEmail = user.email;
        this.userPhone = user.phone;
        
        // Check if already verified
        if (user.emailVerified && user.phoneVerified) {
          this.emailVerified = true;
          this.smsVerified = true;
        }
      }
    },
    generateVerificationCodes() {
      // Generate random 6-digit codes
      const emailCode = Math.floor(100000 + Math.random() * 900000).toString();
      const smsCode = Math.floor(100000 + Math.random() * 900000).toString();
      
      // Store codes in localStorage (in real app, send to backend)
      localStorage.setItem('stayhapa_verification_email', emailCode);
      localStorage.setItem('stayhapa_verification_sms', smsCode);
      
      // Simulate sending codes
      console.log('Email verification code:', emailCode);
      console.log('SMS verification code:', smsCode);
      
      alert(`Verification codes generated:\nEmail: ${emailCode}\nSMS: ${smsCode}\n\nIn production, these would be sent to your email and phone.`);
    },
    async verifyEmail() {
      if (!this.emailCode) {
        alert('Please enter the email verification code');
        return;
      }

      this.emailVerifying = true;
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      const storedCode = localStorage.getItem('stayhapa_verification_email');
      if (this.emailCode === storedCode) {
        this.emailVerified = true;
        this.updateUserVerificationStatus();
        alert('Email verified successfully!');
      } else {
        alert('Invalid email verification code');
      }
      
      this.emailVerifying = false;
    },
    async verifySMS() {
      if (!this.smsCode) {
        alert('Please enter the SMS verification code');
        return;
      }

      this.smsVerifying = true;
      
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      const storedCode = localStorage.getItem('stayhapa_verification_sms');
      if (this.smsCode === storedCode) {
        this.smsVerified = true;
        this.updateUserVerificationStatus();
        alert('Phone number verified successfully!');
      } else {
        alert('Invalid SMS verification code');
      }
      
      this.smsVerifying = false;
    },
    updateUserVerificationStatus() {
      const userData = localStorage.getItem('stayhapa_current_user');
      if (userData) {
        const user = JSON.parse(userData);
        user.emailVerified = this.emailVerified;
        user.phoneVerified = this.smsVerified;
        user.isVerified = this.emailVerified && this.smsVerified;
        localStorage.setItem('stayhapa_current_user', JSON.stringify(user));
      }
    },
    resendEmailCode() {
      this.resendEmailCooldown = 60;
      this.startCooldown('email');
      this.generateVerificationCodes();
      alert('New email verification code sent!');
    },
    resendSMSCode() {
      this.resendSMSCooldown = 60;
      this.startCooldown('sms');
      this.generateVerificationCodes();
      alert('New SMS verification code sent!');
    },
    startCooldown(type) {
      const interval = setInterval(() => {
        if (type === 'email') {
          this.resendEmailCooldown--;
          if (this.resendEmailCooldown <= 0) {
            clearInterval(interval);
          }
        } else {
          this.resendSMSCooldown--;
          if (this.resendSMSCooldown <= 0) {
            clearInterval(interval);
          }
        }
      }, 1000);
    },
    redirectToDashboard() {
      this.$router.push('/guest/dashboard');
    }
  }
};
</script>

<style scoped>
.verification-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 2rem;
}

.verification-card {
  background: white;
  padding: 3rem;
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
}

.verification-card h2 {
  text-align: center;
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2rem;
}

.verification-card > p {
  text-align: center;
  color: #666;
  margin-bottom: 2rem;
}

.verification-methods {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  margin-bottom: 2rem;
}

.verification-method {
  padding: 1.5rem;
  border: 2px solid #f0f0f0;
  border-radius: 12px;
  text-align: center;
}

.verification-method h3 {
  color: #333;
  margin-bottom: 1rem;
  font-size: 1.2rem;
}

.verification-method p {
  color: #666;
  margin-bottom: 1rem;
  font-size: 0.9rem;
}

.code-input {
  width: 100%;
  padding: 1rem;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1.2rem;
  text-align: center;
  letter-spacing: 0.5rem;
  margin-bottom: 1rem;
  box-sizing: border-box;
}

.code-input:focus {
  outline: none;
  border-color: #0F4C81;
}

.verify-btn {
  width: 100%;
  padding: 1rem;
  background: #0F4C81;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.verify-btn:hover:not(:disabled) {
  background: #0a3a6b;
}

.verify-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.resend-options {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.resend-btn {
  flex: 1;
  padding: 0.75rem;
  background: #f8f9fa;
  color: #333;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.resend-btn:hover:not(:disabled) {
  background: #e9ecef;
  border-color: #0F4C81;
}

.resend-btn:disabled {
  background: #f8f9fa;
  color: #999;
  cursor: not-allowed;
}

.success-message {
  text-align: center;
  padding: 2rem;
  background: #f0f9f0;
  border: 2px solid #4caf50;
  border-radius: 12px;
}

.success-message h3 {
  color: #2e7d32;
  margin-bottom: 1rem;
}

.success-message p {
  color: #666;
  margin-bottom: 1.5rem;
}

.continue-btn {
  padding: 1rem 2rem;
  background: #4caf50;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.continue-btn:hover {
  background: #45a049;
}

@media (max-width: 768px) {
  .verification-container {
    padding: 1rem;
  }
  
  .verification-card {
    padding: 2rem;
  }
  
  .verification-methods {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
  
  .resend-options {
    flex-direction: column;
  }
}
</style>