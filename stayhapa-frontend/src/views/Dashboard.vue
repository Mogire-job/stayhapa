<template>
  <div class="dashboard-page">
    <!-- Navigation Bar -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link to="/listings" class="nav-link">Browse Stays</router-link>
          <!-- FIXED: Changed from /become-host to /get-started?type=host -->
          <router-link to="/get-started?type=host" class="nav-link">Become a Host</router-link>
          <div class="user-menu">
            <img :src="user.avatar" :alt="user.name" class="user-avatar">
            <span class="user-name">{{ user.name }}</span>
          </div>
        </div>
      </div>
    </nav>

    <div class="dashboard-container">
      <!-- User Profile Header -->
      <div class="profile-header">
        <div class="profile-info">
          <img :src="user.avatar" :alt="user.name" class="profile-avatar">
          <div class="profile-details">
            <h1 class="profile-name">{{ user.name }}</h1>
            <p class="profile-email">{{ user.email }}</p>
            <div class="profile-stats">
              <div class="stat">
                <span class="stat-number">{{ user.stats.trips }}</span>
                <span class="stat-label">Trips</span>
              </div>
              <div class="stat">
                <span class="stat-number">{{ user.stats.reviews }}</span>
                <span class="stat-label">Reviews</span>
              </div>
              <div class="stat">
                <span class="stat-number">{{ user.stats.joined }}</span>
                <span class="stat-label">Joined</span>
              </div>
            </div>
          </div>
        </div>
        <div class="profile-actions">
          <button class="edit-profile-btn" @click="editProfile">
            Edit Profile
          </button>
          <button class="verify-btn" v-if="!user.isVerified" @click="verifyAccount">
            Verify Account
          </button>
          <div v-else class="verified-badge">
            <span>✓ Verified</span>
          </div>
        </div>
      </div>

      <!-- Dashboard Tabs -->
      <div class="dashboard-tabs">
        <button 
          v-for="tab in tabs" 
          :key="tab.id"
          class="tab-button"
          :class="{ active: activeTab === tab.id }"
          @click="activeTab = tab.id"
        >
          {{ tab.label }}
          <span v-if="tab.count" class="tab-count">{{ tab.count }}</span>
        </button>
      </div>

      <!-- Tab Content -->
      <div class="tab-content">
        <!-- Upcoming Trips Tab -->
        <div v-if="activeTab === 'upcoming'" class="tab-panel">
          <div class="section-header">
            <h2>Upcoming Trips</h2>
            <p>Your confirmed bookings</p>
          </div>
          
          <div v-if="upcomingTrips.length === 0" class="empty-state">
            <div class="empty-icon">🏠</div>
            <h3>No upcoming trips</h3>
            <p>When you book a stay, it will appear here.</p>
            <router-link to="/listings" class="browse-btn">
              Browse Stays
            </router-link>
          </div>

          <div v-else class="trips-grid">
            <div 
              v-for="trip in upcomingTrips" 
              :key="trip.id"
              class="trip-card"
            >
              <div class="trip-image">
                <img :src="trip.listing.image" :alt="trip.listing.title">
              </div>
              <div class="trip-info">
                <h3 class="trip-title">{{ trip.listing.title }}</h3>
                <p class="trip-location">{{ trip.listing.location }}</p>
                <div class="trip-dates">
                  <span class="date">{{ formatDate(trip.checkIn) }}</span>
                  <span class="date-separator">→</span>
                  <span class="date">{{ formatDate(trip.checkOut) }}</span>
                </div>
                <div class="trip-guests">
                  {{ trip.guests }} guest{{ trip.guests !== 1 ? 's' : '' }}
                </div>
                <div class="trip-status" :class="trip.status">
                  {{ trip.status }}
                </div>
              </div>
              <div class="trip-actions">
                <button class="action-btn view-details" @click="viewTripDetails(trip.id)">
                  View Details
                </button>
                <button class="action-btn message-host" @click="messageHost(trip.hostId)">
                  Message Host
                </button>
                <button v-if="trip.canCancel" class="action-btn cancel-trip" @click="cancelTrip(trip.id)">
                  Cancel
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Booking History Tab -->
        <div v-if="activeTab === 'history'" class="tab-panel">
          <div class="section-header">
            <h2>Booking History</h2>
            <p>Your past stays and experiences</p>
          </div>

          <div class="history-list">
            <div 
              v-for="trip in bookingHistory" 
              :key="trip.id"
              class="history-card"
            >
              <div class="history-image">
                <img :src="trip.listing.image" :alt="trip.listing.title">
              </div>
              <div class="history-info">
                <h3>{{ trip.listing.title }}</h3>
                <p class="history-location">{{ trip.listing.location }}</p>
                <div class="history-dates">
                  {{ formatDate(trip.checkIn) }} - {{ formatDate(trip.checkOut) }}
                </div>
                <div class="history-rating">
                  <div class="stars">★★★★★</div>
                  <span class="rating-text">Rate your stay</span>
                </div>
              </div>
              <div class="history-actions">
                <button class="action-btn" @click="writeReview(trip.id)">
                  Write Review
                </button>
                <button class="action-btn" @click="bookAgain(trip.listingId)">
                  Book Again
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Wishlists Tab -->
        <div v-if="activeTab === 'wishlists'" class="tab-panel">
          <div class="section-header">
            <h2>Wishlists</h2>
            <p>Saved properties for future trips</p>
          </div>

          <div class="wishlists-container">
            <div class="wishlist-create">
              <input 
                v-model="newWishlistName" 
                placeholder="Create new wishlist"
                class="wishlist-input"
              >
              <button class="create-btn" @click="createWishlist">
                Create
              </button>
            </div>

            <div class="wishlists-grid">
              <div 
                v-for="wishlist in wishlists" 
                :key="wishlist.id"
                class="wishlist-card"
              >
                <div class="wishlist-header">
                  <h3>{{ wishlist.name }}</h3>
                  <span class="wishlist-count">{{ wishlist.listings.length }} stays</span>
                </div>
                <div class="wishlist-images">
                  <img 
                    v-for="(listing, index) in wishlist.listings.slice(0, 4)" 
                    :key="listing.id"
                    :src="listing.image" 
                    :alt="listing.title"
                    :class="['wishlist-img', `img-${index + 1}`]"
                  >
                </div>
                <button class="view-wishlist-btn" @click="viewWishlist(wishlist.id)">
                  View Wishlist
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Payments Tab -->
        <div v-if="activeTab === 'payments'" class="tab-panel">
          <div class="section-header">
            <h2>Payment Methods</h2>
            <p>Manage your payment options</p>
          </div>

          <div class="payments-section">
            <div class="payment-methods">
              <h3>Saved Payment Methods</h3>
              <div class="payment-cards">
                <div 
                  v-for="card in paymentMethods" 
                  :key="card.id"
                  class="payment-card"
                  :class="{ default: card.isDefault }"
                >
                  <div class="card-info">
                    <div class="card-type">{{ card.type }}</div>
                    <div class="card-number">**** **** **** {{ card.last4 }}</div>
                    <div class="card-expiry">Expires {{ card.expiry }}</div>
                  </div>
                  <div class="card-actions">
                    <button v-if="!card.isDefault" class="set-default-btn" @click="setDefaultCard(card.id)">
                      Set Default
                    </button>
                    <button class="remove-card-btn" @click="removeCard(card.id)">
                      Remove
                    </button>
                  </div>
                </div>
              </div>
              <button class="add-card-btn" @click="showAddCardModal = true">
                + Add Payment Method
              </button>
            </div>

            <div class="payment-history">
              <h3>Payment History</h3>
              <div class="history-table">
                <div class="table-header">
                  <span>Date</span>
                  <span>Description</span>
                  <span>Amount</span>
                  <span>Status</span>
                </div>
                <div 
                  v-for="payment in paymentHistory" 
                  :key="payment.id"
                  class="table-row"
                >
                  <span class="payment-date">{{ formatDate(payment.date) }}</span>
                  <span class="payment-desc">{{ payment.description }}</span>
                  <span class="payment-amount">KES {{ payment.amount.toLocaleString() }}</span>
                  <span class="payment-status" :class="payment.status">
                    {{ payment.status }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Reviews Tab -->
        <div v-if="activeTab === 'reviews'" class="tab-panel">
          <div class="reviews-container">
            <div class="reviews-stats">
              <div class="rating-overview">
                <div class="average-rating">
                  <span class="rating-number">{{ user.rating }}</span>
                  <div class="stars">★★★★★</div>
                  <span class="rating-count">{{ user.reviewCount }} reviews</span>
                </div>
              </div>
            </div>

            <div class="reviews-list">
              <div 
                v-for="review in reviews" 
                :key="review.id"
                class="review-card"
              >
                <div class="review-header">
                  <img :src="review.author.avatar" :alt="review.author.name" class="reviewer-avatar">
                  <div class="reviewer-info">
                    <h4>{{ review.author.name }}</h4>
                    <span class="review-date">{{ formatDate(review.date) }}</span>
                  </div>
                  <div class="review-rating">
                    ★★★★★
                  </div>
                </div>
                <p class="review-content">{{ review.content }}</p>
                <div class="review-listing">
                  Stayed at: {{ review.listingTitle }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Settings Tab -->
        <div v-if="activeTab === 'settings'" class="tab-panel">
          <div class="settings-section">
            <h3>Account Settings</h3>
            <div class="settings-form">
              <div class="form-group">
                <label>Full Name</label>
                <input type="text" v-model="user.name" class="settings-input">
              </div>
              <div class="form-group">
                <label>Email Address</label>
                <input type="email" v-model="user.email" class="settings-input">
              </div>
              <div class="form-group">
                <label>Phone Number</label>
                <input type="tel" v-model="user.phone" class="settings-input">
              </div>
              <div class="form-group">
                <label>Language</label>
                <select v-model="user.language" class="settings-select">
                  <option value="en">English</option>
                  <option value="sw">Swahili</option>
                </select>
              </div>
              <button class="save-settings-btn" @click="saveSettings">
                Save Changes
              </button>
            </div>
          </div>

          <div class="settings-section">
            <h3>Security</h3>
            <div class="security-options">
              <button class="security-btn" @click="changePassword">
                Change Password
              </button>
              <button class="security-btn" @click="enable2FA">
                {{ user.twoFactorEnabled ? 'Disable' : 'Enable' }} Two-Factor Authentication
              </button>
            </div>
          </div>

          <div class="settings-section">
            <h3>Notifications</h3>
            <div class="notification-settings">
              <label class="notification-toggle">
                <input type="checkbox" v-model="user.notifications.email">
                <span class="toggle-slider"></span>
                Email Notifications
              </label>
              <label class="notification-toggle">
                <input type="checkbox" v-model="user.notifications.sms">
                <span class="toggle-slider"></span>
                SMS Notifications
              </label>
              <label class="notification-toggle">
                <input type="checkbox" v-model="user.notifications.push">
                <span class="toggle-slider"></span>
                Push Notifications
              </label>
            </div>
          </div>
        </div>

        <!-- Host Dashboard Tab -->
        <div v-if="activeTab === 'host'" class="tab-panel">
          <div class="host-dashboard">
            <div class="host-stats">
              <div class="stat-card">
                <div class="stat-icon">💰</div>
                <div class="stat-content">
                  <div class="stat-value">KES {{ hostStats.earnings.toLocaleString() }}</div>
                  <div class="stat-label">Total Earnings</div>
                </div>
              </div>
              <div class="stat-card">
                <div class="stat-icon">📅</div>
                <div class="stat-content">
                  <div class="stat-value">{{ hostStats.bookings }}</div>
                  <div class="stat-label">Upcoming Bookings</div>
                </div>
              </div>
              <div class="stat-card">
                <div class="stat-icon">⭐</div>
                <div class="stat-content">
                  <div class="stat-value">{{ hostStats.rating }}</div>
                  <div class="stat-label">Average Rating</div>
                </div>
              </div>
              <div class="stat-card">
                <div class="stat-icon">🏠</div>
                <div class="stat-content">
                  <div class="stat-value">{{ hostStats.listings }}</div>
                  <div class="stat-label">Active Listings</div>
                </div>
              </div>
            </div>

            <div class="host-actions">
              <!-- FIXED: Changed from /become-host to /get-started?type=host -->
              <router-link to="/get-started?type=host" class="host-action-btn" v-if="!user.isHost">
                Become a Host
              </router-link>
              <!-- FIXED: Changed from /host/listings to /host/properties -->
              <router-link to="/host/properties" class="host-action-btn" v-else>
                Manage Listings
              </router-link>
              <button class="host-action-btn" @click="viewHostCalendar">
                View Calendar
              </button>
              <button class="host-action-btn" @click="viewEarnings">
                View Earnings
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Add Card Modal -->
    <div v-if="showAddCardModal" class="modal-overlay" @click="showAddCardModal = false">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>Add Payment Method</h3>
          <button class="close-btn" @click="showAddCardModal = false">×</button>
        </div>
        <div class="modal-body">
          <!-- Add card form would go here -->
          <p>Payment method integration would be implemented here.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DashboardPage',
  data() {
    return {
      activeTab: 'upcoming',
      showAddCardModal: false,
      newWishlistName: '',
      user: {
        name: 'John Doe',
        email: 'john.doe@email.com',
        phone: '+254 712 345 678',
        avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face',
        isVerified: true,
        isHost: true,
        language: 'en',
        rating: 4.8,
        reviewCount: 12,
        stats: {
          trips: 8,
          reviews: 12,
          joined: '2023'
        },
        notifications: {
          email: true,
          sms: false,
          push: true
        }
      },
      tabs: [
        { id: 'upcoming', label: 'Upcoming Trips', count: 2 },
        { id: 'history', label: 'History', count: 8 },
        { id: 'wishlists', label: 'Wishlists', count: 3 },
        { id: 'payments', label: 'Payments' },
        { id: 'reviews', label: 'Reviews', count: 12 },
        { id: 'settings', label: 'Settings' },
        { id: 'host', label: 'Host Dashboard' }
      ],
      upcomingTrips: [
        {
          id: 1,
          listing: {
            title: 'Modern Apartment in Kilimani',
            location: 'Kilimani, Nairobi',
            image: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=300'
          },
          checkIn: '2024-02-15',
          checkOut: '2024-02-20',
          guests: 2,
          status: 'confirmed',
          canCancel: true,
          hostId: 123
        },
        {
          id: 2,
          listing: {
            title: 'Luxury Studio in Kileleshwa',
            location: 'Kileleshwa, Nairobi',
            image: 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=300'
          },
          checkIn: '2024-03-01',
          checkOut: '2024-03-05',
          guests: 1,
          status: 'confirmed',
          canCancel: true,
          hostId: 124
        }
      ],
      bookingHistory: [
        {
          id: 1,
          listing: {
            title: 'Spacious 2-Bedroom Apartment',
            location: 'Parklands, Nairobi',
            image: 'https://images.unsplash.com/photo-1560185127-6ed189bf02f4?w=300'
          },
          checkIn: '2024-01-10',
          checkOut: '2024-01-15',
          listingId: 101
        }
      ],
      wishlists: [
        {
          id: 1,
          name: 'Nairobi City Stays',
          listings: [
            {
              id: 1,
              title: 'Modern Apartment',
              image: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=150'
            },
            {
              id: 2,
              title: 'Luxury Studio',
              image: 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=150'
            },
            {
              id: 3,
              title: 'Spacious Apartment',
              image: 'https://images.unsplash.com/photo-1560185127-6ed189bf02f4?w=150'
            },
            {
              id: 4,
              title: 'Executive Suite',
              image: 'https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=150'
            }
          ]
        }
      ],
      paymentMethods: [
        {
          id: 1,
          type: 'Visa',
          last4: '4242',
          expiry: '12/25',
          isDefault: true
        },
        {
          id: 2,
          type: 'MasterCard',
          last4: '8888',
          expiry: '08/24',
          isDefault: false
        }
      ],
      paymentHistory: [
        {
          id: 1,
          date: '2024-01-10',
          description: 'Modern Apartment in Kilimani',
          amount: 42500,
          status: 'completed'
        }
      ],
      reviews: [
        {
          id: 1,
          author: {
            name: 'Sarah M.',
            avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=50&h=50&fit=crop&crop=face'
          },
          date: '2024-01-20',
          content: 'Great stay! The apartment was clean and exactly as described. The host was very responsive and helpful.',
          listingTitle: 'Modern Apartment in Kilimani',
          rating: 5
        }
      ],
      hostStats: {
        earnings: 254000,
        bookings: 3,
        rating: 4.9,
        listings: 2
      }
    }
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'short', day: 'numeric' };
      return new Date(dateString).toLocaleDateString('en-US', options);
    },
    editProfile() {
      // Implement edit profile functionality
      console.log('Edit profile');
    },
    verifyAccount() {
      // Implement account verification
      console.log('Verify account');
    },
    viewTripDetails(tripId) {
      this.$router.push(`/trips/${tripId}`);
    },
    messageHost(hostId) {
      // Implement messaging functionality
      console.log('Message host:', hostId);
    },
    cancelTrip(tripId) {
      // Implement trip cancellation
      console.log('Cancel trip:', tripId);
    },
    writeReview(tripId) {
      // Implement review writing
      console.log('Write review for trip:', tripId);
    },
    bookAgain(listingId) {
      this.$router.push(`/listing/${listingId}`);
    },
    createWishlist() {
      if (this.newWishlistName.trim()) {
        const newWishlist = {
          id: Date.now(),
          name: this.newWishlistName,
          listings: []
        };
        this.wishlists.push(newWishlist);
        this.newWishlistName = '';
      }
    },
    viewWishlist(wishlistId) {
      this.$router.push(`/wishlist/${wishlistId}`);
    },
    setDefaultCard(cardId) {
      this.paymentMethods.forEach(card => {
        card.isDefault = card.id === cardId;
      });
    },
    removeCard(cardId) {
      this.paymentMethods = this.paymentMethods.filter(card => card.id !== cardId);
    },
    saveSettings() {
      // Implement settings save
      console.log('Save settings');
    },
    changePassword() {
      // Implement password change
      console.log('Change password');
    },
    enable2FA() {
      // Implement 2FA toggle
      console.log('Toggle 2FA');
    },
    viewHostCalendar() {
      this.$router.push('/host/calendar');
    },
    viewEarnings() {
      this.$router.push('/host/earnings');
    }
  },
  mounted() {
    // Load actual user data from localStorage
    const userData = localStorage.getItem('stayhapa_current_user');
    if (userData) {
      const currentUser = JSON.parse(userData);
      this.user.name = currentUser.fullName;
      this.user.email = currentUser.email;
      this.user.isHost = currentUser.userType === 'host';
    }
  }
}
</script>

<style scoped>
.dashboard-page {
  min-height: 100vh;
  background: #f8f9fa;
}

/* Navigation */
.navbar {
  background: white;
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
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

.user-menu {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem;
  border-radius: 8px;
  cursor: pointer;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
  color: #333;
}

/* Dashboard Container */
.dashboard-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

/* Profile Header */
.profile-header {
  background: white;
  border-radius: 16px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.profile-info {
  display: flex;
  gap: 1.5rem;
  align-items: flex-start;
}

.profile-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #0F4C81;
}

.profile-details h1 {
  margin: 0 0 0.5rem 0;
  color: #333;
  font-size: 1.8rem;
}

.profile-email {
  color: #666;
  margin: 0 0 1rem 0;
}

.profile-stats {
  display: flex;
  gap: 2rem;
}

.stat {
  text-align: center;
}

.stat-number {
  display: block;
  font-size: 1.5rem;
  font-weight: bold;
  color: #0F4C81;
}

.stat-label {
  font-size: 0.9rem;
  color: #666;
}

.profile-actions {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.edit-profile-btn, .verify-btn {
  padding: 0.75rem 1.5rem;
  border: 1px solid #0F4C81;
  border-radius: 8px;
  background: white;
  color: #0F4C81;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s;
}

.edit-profile-btn:hover, .verify-btn:hover {
  background: #0F4C81;
  color: white;
}

.verified-badge {
  background: #28a745;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 500;
}

/* Dashboard Tabs */
.dashboard-tabs {
  display: flex;
  background: white;
  border-radius: 12px;
  padding: 0.5rem;
  margin-bottom: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

.tab-button {
  flex: 1;
  padding: 1rem 1.5rem;
  border: none;
  background: none;
  cursor: pointer;
  font-weight: 500;
  color: #666;
  border-radius: 8px;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  white-space: nowrap;
}

.tab-button.active {
  background: #0F4C81;
  color: white;
}

.tab-count {
  background: #e9ecef;
  color: #666;
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
}

.tab-button.active .tab-count {
  background: rgba(255, 255, 255, 0.2);
  color: white;
}

/* Tab Content */
.tab-content {
  background: white;
  border-radius: 16px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.section-header {
  margin-bottom: 2rem;
}

.section-header h2 {
  margin: 0 0 0.5rem 0;
  color: #333;
  font-size: 1.5rem;
}

.section-header p {
  color: #666;
  margin: 0;
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  color: #666;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.empty-state h3 {
  margin-bottom: 0.5rem;
  color: #333;
}

.browse-btn {
  display: inline-block;
  background: #0F4C81;
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  margin-top: 1rem;
  transition: background 0.3s;
}

.browse-btn:hover {
  background: #0a3a6b;
}

/* Trips Grid */
.trips-grid {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.trip-card {
  display: grid;
  grid-template-columns: 120px 1fr auto;
  gap: 1.5rem;
  padding: 1.5rem;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  transition: all 0.3s;
}

.trip-card:hover {
  border-color: #0F4C81;
  box-shadow: 0 4px 12px rgba(15, 76, 129, 0.1);
}

.trip-image {
  width: 120px;
  height: 80px;
  border-radius: 8px;
  overflow: hidden;
}

.trip-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.trip-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.trip-title {
  margin: 0;
  color: #333;
  font-size: 1.1rem;
}

.trip-location {
  color: #666;
  margin: 0;
}

.trip-dates {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #333;
  font-weight: 500;
}

.date-separator {
  color: #999;
}

.trip-guests {
  color: #666;
  font-size: 0.9rem;
}

.trip-status {
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  width: fit-content;
}

.trip-status.confirmed {
  background: #d4edda;
  color: #155724;
}

.trip-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  min-width: 120px;
}

.action-btn {
  padding: 0.5rem 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s;
}

.action-btn.view-details {
  border-color: #0F4C81;
  color: #0F4C81;
}

.action-btn.view-details:hover {
  background: #0F4C81;
  color: white;
}

.action-btn.cancel-trip {
  border-color: #dc3545;
  color: #dc3545;
}

.action-btn.cancel-trip:hover {
  background: #dc3545;
  color: white;
}

/* History List */
.history-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.history-card {
  display: grid;
  grid-template-columns: 100px 1fr auto;
  gap: 1.5rem;
  padding: 1.5rem;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
}

.history-image {
  width: 100px;
  height: 80px;
  border-radius: 8px;
  overflow: hidden;
}

.history-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.history-info h3 {
  margin: 0 0 0.5rem 0;
  color: #333;
}

.history-location {
  color: #666;
  margin: 0 0 0.5rem 0;
}

.history-dates {
  color: #333;
  font-weight: 500;
  margin-bottom: 1rem;
}

.history-rating {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.stars {
  color: #0F4C81;
}

.rating-text {
  color: #666;
  font-size: 0.9rem;
}

.history-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  min-width: 120px;
}

/* Wishlists */
.wishlists-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.wishlist-create {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.wishlist-input {
  flex: 1;
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
}

.create-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}

.wishlists-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.wishlist-card {
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 1.5rem;
  transition: all 0.3s;
}

.wishlist-card:hover {
  border-color: #0F4C81;
  box-shadow: 0 4px 12px rgba(15, 76, 129, 0.1);
}

.wishlist-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.wishlist-header h3 {
  margin: 0;
  color: #333;
}

.wishlist-count {
  color: #666;
  font-size: 0.9rem;
}

.wishlist-images {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 0.5rem;
  height: 120px;
  margin-bottom: 1rem;
  border-radius: 8px;
  overflow: hidden;
}

.wishlist-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.wishlist-img.img-1 {
  grid-column: 1;
  grid-row: 1 / span 2;
}

.wishlist-img.img-2 {
  grid-column: 2;
  grid-row: 1;
}

.wishlist-img.img-3 {
  grid-column: 2;
  grid-row: 2;
}

.view-wishlist-btn {
  width: 100%;
  padding: 0.75rem;
  background: #0F4C81;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
}

.view-wishlist-btn:hover {
  background: #0a3a6b;
}

/* Payments */
.payments-section {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
}

.payment-methods h3, .payment-history h3 {
  margin: 0 0 1rem 0;
  color: #333;
}

.payment-cards {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.payment-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  transition: all 0.3s;
}

.payment-card.default {
  border-color: #0F4C81;
  background: #f8f9fa;
}

.card-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.card-type {
  font-weight: 600;
  color: #333;
}

.card-number {
  color: #666;
  font-size: 0.9rem;
}

.card-expiry {
  color: #999;
  font-size: 0.8rem;
}

.card-actions {
  display: flex;
  gap: 0.5rem;
}

.set-default-btn, .remove-card-btn {
  padding: 0.5rem 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s;
}

.set-default-btn:hover {
  border-color: #0F4C81;
  color: #0F4C81;
}

.remove-card-btn:hover {
  border-color: #dc3545;
  color: #dc3545;
}

.add-card-btn {
  width: 100%;
  padding: 1rem;
  border: 2px dashed #e0e0e0;
  border-radius: 8px;
  background: white;
  color: #666;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s;
}

.add-card-btn:hover {
  border-color: #0F4C81;
  color: #0F4C81;
}

/* Payment History */
.history-table {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
}

.table-header {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr 1fr;
  gap: 1rem;
  padding: 1rem;
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.table-row {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr 1fr;
  gap: 1rem;
  padding: 1rem;
  border-top: 1px solid #e0e0e0;
  transition: background 0.3s;
}

.table-row:hover {
  background: #f8f9fa;
}

.payment-status.completed {
  color: #28a745;
  font-weight: 600;
}

/* Reviews */
.reviews-container {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 3rem;
}

.rating-overview {
  background: #f8f9fa;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
}

.average-rating {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.rating-number {
  font-size: 3rem;
  font-weight: bold;
  color: #333;
}

.rating-count {
  color: #666;
}

.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.review-card {
  padding: 1.5rem;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
}

.review-header {
  display: flex;
  gap: 1rem;
  align-items: flex-start;
  margin-bottom: 1rem;
}

.reviewer-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.reviewer-info {
  flex: 1;
}

.reviewer-info h4 {
  margin: 0 0 0.25rem 0;
  color: #333;
}

.review-date {
  color: #666;
  font-size: 0.9rem;
}

.review-rating {
  color: #0F4C81;
}

.review-content {
  color: #333;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.review-listing {
  color: #666;
  font-size: 0.9rem;
  font-style: italic;
}

/* Settings */
.settings-section {
  margin-bottom: 3rem;
}

.settings-section h3 {
  margin: 0 0 1.5rem 0;
  color: #333;
  font-size: 1.2rem;
}

.settings-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  max-width: 400px;
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

.settings-input, .settings-select {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.settings-input:focus, .settings-select:focus {
  outline: none;
  border-color: #0F4C81;
}

.save-settings-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
  align-self: flex-start;
}

.save-settings-btn:hover {
  background: #0a3a6b;
}

.security-options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.security-btn {
  padding: 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background: white;
  cursor: pointer;
  text-align: left;
  transition: all 0.3s;
}

.security-btn:hover {
  border-color: #0F4C81;
  color: #0F4C81;
}

.notification-settings {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.notification-toggle {
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  padding: 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  transition: all 0.3s;
}

.notification-toggle:hover {
  border-color: #0F4C81;
}

.toggle-slider {
  width: 40px;
  height: 20px;
  background: #e0e0e0;
  border-radius: 20px;
  position: relative;
  transition: background 0.3s;
}

.toggle-slider:before {
  content: '';
  position: absolute;
  width: 16px;
  height: 16px;
  background: white;
  border-radius: 50%;
  top: 2px;
  left: 2px;
  transition: transform 0.3s;
}

.notification-toggle input:checked + .toggle-slider {
  background: #0F4C81;
}

.notification-toggle input:checked + .toggle-slider:before {
  transform: translateX(20px);
}

/* Host Dashboard */
.host-dashboard {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.host-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1.5rem;
  background: #f8f9fa;
  border-radius: 12px;
  transition: all 0.3s;
}

.stat-card:hover {
  background: #e9ecef;
}

.stat-icon {
  font-size: 2rem;
}

.stat-content {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: bold;
  color: #0F4C81;
}

.stat-label {
  color: #666;
  font-size: 0.9rem;
}

.host-actions {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.host-action-btn {
  padding: 1rem 1.5rem;
  background: #0F4C81;
  color: white;
  border: none;
  border-radius: 8px;
  text-decoration: none;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
}

.host-action-btn:hover {
  background: #0a3a6b;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 16px;
  padding: 2rem;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.modal-header h3 {
  margin: 0;
  color: #333;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
}

.modal-body {
  color: #666;
}

/* Responsive Design */
@media (max-width: 768px) {
  .dashboard-container {
    padding: 1rem;
  }
  
  .profile-header {
    flex-direction: column;
    gap: 1.5rem;
    text-align: center;
  }
  
  .profile-info {
    flex-direction: column;
    align-items: center;
  }
  
  .dashboard-tabs {
    flex-wrap: wrap;
  }
  
  .tab-button {
    flex: 1 0 calc(50% - 0.5rem);
  }
  
  .trip-card, .history-card {
    grid-template-columns: 1fr;
    text-align: center;
  }
  
  .trip-actions, .history-actions {
    flex-direction: row;
    justify-content: center;
  }
  
  .payments-section {
    grid-template-columns: 1fr;
    gap: 2rem;
  }
  
  .reviews-container {
    grid-template-columns: 1fr;
  }
  
  .table-header, .table-row {
    grid-template-columns: 1fr 1fr;
    gap: 0.5rem;
  }
  
  .nav-links {
    gap: 1rem;
  }
  
  .user-name {
    display: none;
  }
}

@media (max-width: 480px) {
  .navbar {
    padding: 1rem;
  }
  
  .profile-stats {
    justify-content: center;
  }
  
  .wishlists-grid {
    grid-template-columns: 1fr;
  }
  
  .host-stats {
    grid-template-columns: 1fr;
  }
  
  .host-actions {
    flex-direction: column;
  }
}
</style>