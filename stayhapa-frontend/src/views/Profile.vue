<template>
  <div class="profile-page">
    <!-- Header Background -->
    <div class="profile-header-bg">
      <div class="header-pattern"></div>
    </div>

    <div class="profile-container">
      <!-- Profile Card -->
      <div class="profile-card">
        <!-- Profile Header -->
        <div class="profile-header">
          <div class="back-btn-wrapper">
            <button class="back-btn" @click="goToDashboard">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M19 12H5M12 19l-7-7 7-7"/>
              </svg>
              Back to Dashboard
            </button>
          </div>
          
          <div class="avatar-section">
            <div class="avatar-wrapper">
              <img :src="user?.avatarUrl || defaultAvatar" :alt="userName" class="avatar">
              <button class="avatar-edit-btn" @click="triggerAvatarUpload">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/>
                  <circle cx="12" cy="13" r="4"/>
                </svg>
              </button>
              <input type="file" ref="avatarInput" @change="handleAvatarChange" accept="image/*" hidden>
            </div>
            <div class="user-info">
              <h1 class="user-name">{{ userName }}</h1>
              <p class="user-email">{{ user?.email }}</p>
              <span class="user-role" :class="user?.role?.toLowerCase()">
                {{ user?.role === 'HOST' ? 'Host' : 'Guest' }}
              </span>
            </div>
          </div>
          <div class="header-actions">
            <button class="btn btn-outline" @click="toggleEditMode">
              {{ isEditing ? 'Cancel' : 'Edit Profile' }}
            </button>
            <button v-if="isEditing" class="btn btn-primary" @click="saveProfile">
              Save Changes
            </button>
          </div>
        </div>

        <!-- Tabs -->
        <div class="profile-tabs">
          <button 
            v-for="tab in tabs" 
            :key="tab.id"
            class="tab-btn"
            :class="{ active: activeTab === tab.id }"
            @click="activeTab = tab.id"
          >
            <span class="tab-icon">{{ tab.icon }}</span>
            {{ tab.label }}
          </button>
        </div>

        <!-- Tab Content -->
        <div class="tab-content">
          <!-- Personal Info Tab -->
          <div v-if="activeTab === 'personal'" class="tab-panel">
            <div class="section-header">
              <h2>Personal Information</h2>
              <p>Manage your personal details and contact information</p>
            </div>

            <div class="form-grid">
              <div class="form-group">
                <label>First Name</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.firstName" 
                  type="text" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.firstName || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>Last Name</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.lastName" 
                  type="text" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.lastName || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>Email Address</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.email" 
                  type="email" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.email || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>Phone Number</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.phone" 
                  type="tel" 
                  class="form-input"
                  placeholder="+254 700 000 000"
                >
                <p v-else class="form-value">{{ user?.phone || 'Not set' }}</p>
              </div>

              <div class="form-group full-width">
                <label>Bio</label>
                <textarea 
                  v-if="isEditing" 
                  v-model="editData.bio" 
                  class="form-input form-textarea"
                  placeholder="Tell us about yourself..."
                  rows="4"
                ></textarea>
                <p v-else class="form-value">{{ user?.bio || 'No bio added yet' }}</p>
              </div>

              <div class="form-group">
                <label>Date of Birth</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.dateOfBirth" 
                  type="date" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.dateOfBirth || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>Gender</label>
                <select v-if="isEditing" v-model="editData.gender" class="form-input">
                  <option value="">Select gender</option>
                  <option value="male">Male</option>
                  <option value="female">Female</option>
                  <option value="other">Other</option>
                  <option value="prefer_not_to_say">Prefer not to say</option>
                </select>
                <p v-else class="form-value">{{ user?.gender || 'Not set' }}</p>
              </div>
            </div>
          </div>

          <!-- Address Tab -->
          <div v-if="activeTab === 'address'" class="tab-panel">
            <div class="section-header">
              <h2>Address Details</h2>
              <p>Your residential information for deliveries and verification</p>
            </div>

            <div class="form-grid">
              <div class="form-group full-width">
                <label>Street Address</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.address" 
                  type="text" 
                  class="form-input"
                  placeholder="123 Main Street"
                >
                <p v-else class="form-value">{{ user?.address || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>City</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.city" 
                  type="text" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.city || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>State/County</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.state" 
                  type="text" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.state || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>Country</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.country" 
                  type="text" 
                  class="form-input"
                  placeholder="Kenya"
                >
                <p v-else class="form-value">{{ user?.country || 'Not set' }}</p>
              </div>

              <div class="form-group">
                <label>ZIP Code</label>
                <input 
                  v-if="isEditing" 
                  v-model="editData.zipCode" 
                  type="text" 
                  class="form-input"
                >
                <p v-else class="form-value">{{ user?.zipCode || 'Not set' }}</p>
              </div>
            </div>
          </div>

          <!-- Settings Tab -->
          <div v-if="activeTab === 'settings'" class="tab-panel">
            <div class="section-header">
              <h2>Account Settings</h2>
              <p>Manage your preferences and account security</p>
            </div>

            <div class="settings-list">
              <div class="settings-item">
                <div class="settings-info">
                  <h3>Email Notifications</h3>
                  <p>Receive emails about bookings, messages, and updates</p>
                </div>
                <label class="toggle">
                  <input type="checkbox" v-model="settings.emailNotifications">
                  <span class="toggle-slider"></span>
                </label>
              </div>

              <div class="settings-item">
                <div class="settings-info">
                  <h3>SMS Notifications</h3>
                  <p>Receive text messages for important updates</p>
                </div>
                <label class="toggle">
                  <input type="checkbox" v-model="settings.smsNotifications">
                  <span class="toggle-slider"></span>
                </label>
              </div>

              <div class="settings-item">
                <div class="settings-info">
                  <h3>Marketing Emails</h3>
                  <p>Receive promotional offers and travel deals</p>
                </div>
                <label class="toggle">
                  <input type="checkbox" v-model="settings.marketingEmails">
                  <span class="toggle-slider"></span>
                </label>
              </div>

              <div class="settings-item">
                <div class="settings-info">
                  <h3>Two-Factor Authentication</h3>
                  <p>Add an extra layer of security to your account</p>
                </div>
                <button class="btn btn-outline btn-sm">Enable</button>
              </div>
            </div>

            <div class="section-header mt-8">
              <h2>Appearance</h2>
              <p>Customize how Stayhapa looks on your device</p>
            </div>

            <div class="settings-list">
              <div class="settings-item">
                <div class="settings-info">
                  <h3>Theme</h3>
                  <p>Choose your preferred color scheme</p>
                </div>
                <select v-model="settings.theme" class="form-input theme-select">
                  <option value="light">Light</option>
                  <option value="dark">Dark</option>
                  <option value="system">System Default</option>
                </select>
              </div>

              <div class="settings-item">
                <div class="settings-info">
                  <h3>Language</h3>
                  <p>Select your preferred language</p>
                </div>
                <select v-model="settings.language" class="form-input theme-select">
                  <option value="en">English</option>
                  <option value="sw">Swahili</option>
                </select>
              </div>

              <div class="settings-item">
                <div class="settings-info">
                  <h3>Currency</h3>
                  <p>Display prices in your preferred currency</p>
                </div>
                <select v-model="settings.currency" class="form-input theme-select">
                  <option value="KES">KES - Kenyan Shilling</option>
                  <option value="USD">USD - US Dollar</option>
                  <option value="EUR">EUR - Euro</option>
                </select>
              </div>
            </div>
          </div>

          <!-- Security Tab -->
          <div v-if="activeTab === 'security'" class="tab-panel">
            <div class="section-header">
              <h2>Security</h2>
              <p>Keep your account safe and secure</p>
            </div>

            <div class="security-section">
              <div class="security-item">
                <div class="security-icon">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                    <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                  </svg>
                </div>
                <div class="security-info">
                  <h3>Password</h3>
                  <p>Last changed 30 days ago</p>
                </div>
                <button class="btn btn-outline btn-sm">Change Password</button>
              </div>

              <div class="security-item">
                <div class="security-icon">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                  </svg>
                </div>
                <div class="security-info">
                  <h3>Phone Number</h3>
                  <p>{{ user?.phone || 'No phone added' }}</p>
                </div>
                <button class="btn btn-outline btn-sm">Update</button>
              </div>

              <div class="security-item">
                <div class="security-icon">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                    <polyline points="22,6 12,13 2,6"/>
                  </svg>
                </div>
                <div class="security-info">
                  <h3>Email Address</h3>
                  <p>{{ user?.email }}</p>
                </div>
                <button class="btn btn-outline btn-sm">Change Email</button>
              </div>

              <div class="security-item">
                <div class="security-icon warning">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/>
                    <line x1="12" y1="9" x2="12" y2="13"/>
                    <line x1="12" y1="17" x2="12.01" y2="17"/>
                  </svg>
                </div>
                <div class="security-info">
                  <h3>Delete Account</h3>
                  <p>Permanently delete your account and all data</p>
                </div>
                <button class="btn btn-danger btn-sm">Delete Account</button>
              </div>
            </div>

            <!-- Connected Accounts -->
            <div class="section-header mt-8">
              <h2>Connected Accounts</h2>
              <p>Link your social accounts for easier login</p>
            </div>

            <div class="connected-accounts">
              <div class="account-item">
                <div class="account-icon google">
                  <svg viewBox="0 0 24 24" width="20" height="20">
                    <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                    <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                    <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                    <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                  </svg>
                </div>
                <div class="account-info">
                  <h3>Google</h3>
                  <p>Not connected</p>
                </div>
                <button class="btn btn-outline btn-sm">Connect</button>
              </div>

              <div class="account-item">
                <div class="account-icon facebook">
                  <svg viewBox="0 0 24 24" width="20" height="20" fill="#1877F2">
                    <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
                  </svg>
                </div>
                <div class="account-info">
                  <h3>Facebook</h3>
                  <p>Not connected</p>
                </div>
                <button class="btn btn-outline btn-sm">Connect</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Success Toast -->
    <div v-if="showSuccess" class="toast success">
      <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
        <polyline points="22 4 12 14.01 9 11.01"/>
      </svg>
      Profile updated successfully!
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const user = ref(null)
const isEditing = ref(false)
const activeTab = ref('personal')
const showSuccess = ref(false)
const avatarInput = ref(null)

const defaultAvatar = 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200&h=200&fit=crop&crop=faces'

const tabs = [
  { id: 'personal', label: 'Personal Info', icon: '👤' },
  { id: 'address', label: 'Address', icon: '📍' },
  { id: 'settings', label: 'Settings', icon: '⚙️' },
  { id: 'security', label: 'Security', icon: '🔒' }
]

const editData = ref({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  bio: '',
  dateOfBirth: '',
  gender: '',
  address: '',
  city: '',
  state: '',
  country: 'Kenya',
  zipCode: ''
})

const settings = ref({
  emailNotifications: true,
  smsNotifications: false,
  marketingEmails: false,
  theme: 'light',
  language: 'en',
  currency: 'KES'
})

const userName = computed(() => {
  if (!user.value) return 'Guest User'
  return `${user.value.firstName || ''} ${user.value.lastName || ''}`.trim() || 'Guest User'
})

onMounted(() => {
  const storedUser = localStorage.getItem('stayhapa_current_user')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
    editData.value = { ...editData.value, ...user.value }
  }
})

const toggleEditMode = () => {
  isEditing.value = !isEditing.value
  if (!isEditing.value) {
    editData.value = { ...editData.value, ...user.value }
  }
}

const saveProfile = () => {
  user.value = { ...user.value, ...editData.value }
  localStorage.setItem('stayhapa_current_user', JSON.stringify(user.value))
  isEditing.value = false
  showSuccess.value = true
  setTimeout(() => showSuccess.value = false, 3000)
}

const triggerAvatarUpload = () => {
  avatarInput.value?.click()
}

const handleAvatarChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      editData.value.avatarUrl = e.target.result
      user.value = { ...user.value, avatarUrl: e.target.result }
      localStorage.setItem('stayhapa_current_user', JSON.stringify(user.value))
      showSuccess.value = true
      setTimeout(() => showSuccess.value = false, 3000)
    }
    reader.readAsDataURL(file)
  }
}

const goToDashboard = () => {
  if (user.value?.role === 'HOST') {
    router.push('/host/dashboard')
  } else {
    router.push('/guest/dashboard')
  }
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #f1f5f9;
  position: relative;
}

.profile-header-bg {
  height: 200px;
  background: linear-gradient(135deg, #0F4C81 0%, #1a6fa3 50%, #0F4C81 100%);
  position: relative;
  overflow: hidden;
}

.header-pattern {
  position: absolute;
  inset: 0;
  background-image: 
    radial-gradient(circle at 20% 50%, rgba(255,255,255,0.1) 0%, transparent 50%),
    radial-gradient(circle at 80% 50%, rgba(255,255,255,0.05) 0%, transparent 50%);
}

.profile-container {
  max-width: 900px;
  margin: -100px auto 0;
  padding: 0 1.5rem 3rem;
}

.profile-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.profile-header {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  padding: 2rem;
  border-bottom: 1px solid #e5e7eb;
}

.back-btn-wrapper {
  display: flex;
  justify-content: flex-start;
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  background: #f1f5f9;
  border: none;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 500;
  color: #475569;
  cursor: pointer;
  transition: all 0.3s;
}

.back-btn:hover {
  background: #e2e8f0;
  color: #1f2937;
}

.avatar-section {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}

.avatar-wrapper {
  position: relative;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  border: 4px solid white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.avatar-edit-btn {
  position: absolute;
  bottom: 0;
  right: 0;
  background: #0F4C81;
  color: white;
  border: none;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s;
}

.avatar-edit-btn:hover {
  background: #0a3a6b;
  transform: scale(1.1);
}

.user-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.user-name {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1f2937;
  margin: 0;
}

.user-email {
  color: #6b7280;
  font-size: 0.95rem;
  margin: 0;
}

.user-role {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  margin-top: 0.25rem;
  width: fit-content;
}

.user-role.host {
  background: #dbeafe;
  color: #1d4ed8;
}

.user-role.guest {
  background: #d1fae5;
  color: #059669;
}

.header-actions {
  display: flex;
  gap: 0.75rem;
}

.btn {
  padding: 0.625rem 1.25rem;
  border-radius: 8px;
  font-weight: 600;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s;
  border: none;
}

.btn-primary {
  background: #0F4C81;
  color: white;
}

.btn-primary:hover {
  background: #0a3a6b;
}

.btn-outline {
  background: white;
  border: 1px solid #d1d5db;
  color: #374151;
}

.btn-outline:hover {
  border-color: #0F4C81;
  color: #0F4C81;
}

.btn-sm {
  padding: 0.5rem 1rem;
  font-size: 0.85rem;
}

.btn-danger {
  background: #fee2e2;
  color: #dc2626;
}

.btn-danger:hover {
  background: #fecaca;
}

.profile-tabs {
  display: flex;
  border-bottom: 1px solid #e5e7eb;
  padding: 0 2rem;
  background: #fafafa;
}

.tab-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem 1.25rem;
  background: none;
  border: none;
  font-size: 0.9rem;
  font-weight: 500;
  color: #6b7280;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: all 0.3s;
}

.tab-btn:hover {
  color: #0F4C81;
}

.tab-btn.active {
  color: #0F4C81;
  border-bottom-color: #0F4C81;
}

.tab-content {
  padding: 2rem;
}

.tab-panel {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.section-header {
  margin-bottom: 2rem;
}

.section-header h2 {
  font-size: 1.25rem;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 0.25rem;
}

.section-header p {
  color: #6b7280;
  font-size: 0.9rem;
  margin: 0;
}

.mt-8 {
  margin-top: 2rem;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group.full-width {
  grid-column: 1 / -1;
}

.form-group label {
  font-size: 0.85rem;
  font-weight: 600;
  color: #374151;
}

.form-input {
  padding: 0.75rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 0.95rem;
  transition: all 0.3s;
}

.form-input:focus {
  outline: none;
  border-color: #0F4C81;
  box-shadow: 0 0 0 3px rgba(15, 76, 129, 0.1);
}

.form-textarea {
  resize: vertical;
  min-height: 100px;
}

.form-value {
  color: #1f2937;
  font-size: 0.95rem;
  padding: 0.75rem 0;
  margin: 0;
}

.settings-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.settings-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 1.25rem;
  background: #f9fafb;
  border-radius: 12px;
  gap: 1rem;
}

.settings-info h3 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 0.25rem;
}

.settings-info p {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 0;
}

.toggle {
  position: relative;
  display: inline-block;
  width: 48px;
  height: 26px;
}

.toggle input {
  opacity: 0;
  width: 0;
  height: 0;
}

.toggle-slider {
  position: absolute;
  cursor: pointer;
  inset: 0;
  background-color: #d1d5db;
  transition: 0.3s;
  border-radius: 26px;
}

.toggle-slider::before {
  position: absolute;
  content: "";
  height: 20px;
  width: 20px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: 0.3s;
  border-radius: 50%;
}

.toggle input:checked + .toggle-slider {
  background-color: #0F4C81;
}

.toggle input:checked + .toggle-slider::before {
  transform: translateX(22px);
}

.theme-select {
  min-width: 150px;
}

.security-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.security-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1.25rem;
  background: #f9fafb;
  border-radius: 12px;
}

.security-icon {
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e0e7ff;
  color: #4f46e5;
  border-radius: 12px;
}

.security-icon.warning {
  background: #fee2e2;
  color: #dc2626;
}

.security-info {
  flex: 1;
}

.security-info h3 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 0.25rem;
}

.security-info p {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 0;
}

.connected-accounts {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.account-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem 1.25rem;
  background: #f9fafb;
  border-radius: 12px;
}

.account-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
}

.account-info {
  flex: 1;
}

.account-info h3 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #1f2937;
  margin: 0;
}

.account-info p {
  font-size: 0.85rem;
  color: #6b7280;
  margin: 0;
}

.toast {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem 1.5rem;
  border-radius: 12px;
  font-weight: 500;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  animation: slideIn 0.3s ease;
  z-index: 1000;
}

.toast.success {
  background: #10b981;
  color: white;
}

@keyframes slideIn {
  from { transform: translateY(20px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}

@media (max-width: 768px) {
  .profile-header {
    flex-direction: column;
    gap: 1.5rem;
  }
  
  .avatar-section {
    flex-direction: column;
    text-align: center;
  }
  
  .header-actions {
    width: 100%;
  }
  
  .header-actions .btn {
    flex: 1;
  }
  
  .profile-tabs {
    overflow-x: auto;
    padding: 0 1rem;
  }
  
  .tab-btn {
    white-space: nowrap;
    padding: 0.75rem 1rem;
  }
  
  .form-grid {
    grid-template-columns: 1fr;
  }
  
  .settings-item {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .settings-info {
    width: 100%;
  }
}
</style>
