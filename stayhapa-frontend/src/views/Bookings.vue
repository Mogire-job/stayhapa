<template>
  <main class="bookings-page">
    <nav class="bookings-nav">
      <router-link to="/" class="brand">Stayhapa</router-link>
      <router-link :to="isHost ? '/host/dashboard' : '/guest/dashboard'" class="back-link">
        ← Dashboard
      </router-link>
    </nav>

    <section class="bookings-content">
      <p class="eyebrow">{{ isHost ? 'HOST SPACE' : 'GUEST SPACE' }}</p>
      <h1>{{ isHost ? 'Reservations for your stays' : 'Your reservations' }}</h1>
      <p class="intro">
        {{ isHost
          ? 'See who is arriving, when they are coming, and how to reach them.'
          : 'Keep your confirmed stays and travel details together.' }}
      </p>

      <p v-if="loading" class="page-state" role="status">Loading reservations…</p>
      <p v-else-if="errorMessage" class="error-message" role="alert">{{ errorMessage }}</p>
      <div v-else-if="bookings.length === 0" class="empty-state">
        <div class="empty-icon" aria-hidden="true">{{ isHost ? '⌂' : '↗' }}</div>
        <h2>{{ isHost ? 'No reservations yet' : 'Your next trip starts here' }}</h2>
        <p>{{ isHost ? 'Confirmed reservations for your listings will appear here.' : 'Explore active stays and reserve dates that work for you.' }}</p>
        <router-link :to="isHost ? '/host/properties' : '/listings'" class="primary-link">
          {{ isHost ? 'Manage your listings' : 'Explore available stays' }}
        </router-link>
      </div>

      <div v-else class="booking-list">
        <article v-for="booking in bookings" :key="booking.id" class="booking-card">
          <div class="booking-image">
            <img v-if="booking.propertyImage" :src="booking.propertyImage" :alt="booking.propertyTitle">
            <div v-else class="image-placeholder">Stayhapa</div>
          </div>
          <div class="booking-content">
            <div class="booking-heading">
              <div>
                <span class="status-pill" :class="booking.status.toLowerCase()">
                  {{ formatStatus(booking.status) }}
                </span>
                <h2>{{ booking.propertyTitle }}</h2>
                <p class="location">{{ [booking.city, booking.country].filter(Boolean).join(', ') }}</p>
              </div>
              <router-link :to="`/property/${booking.propertyId}`" class="details-link">View stay</router-link>
            </div>

            <div class="booking-facts">
              <div>
                <span class="fact-label">Check-in</span>
                <strong>{{ formatDate(booking.checkInDate) }}</strong>
              </div>
              <div>
                <span class="fact-label">Check-out</span>
                <strong>{{ formatDate(booking.checkOutDate) }}</strong>
              </div>
              <div>
                <span class="fact-label">Guests</span>
                <strong>{{ booking.numberOfGuests }}</strong>
              </div>
              <div>
                <span class="fact-label">Total · {{ booking.nights }} nights</span>
                <strong>KSh {{ Number(booking.totalPrice).toLocaleString() }}</strong>
              </div>
            </div>

            <p v-if="booking.address" class="address-line"><strong>Stay address:</strong> {{ booking.address }}</p>
            <p v-if="isHost" class="contact-line">
              Guest: {{ booking.guestName }}<span v-if="booking.guestEmail"> · {{ booking.guestEmail }}</span>
            </p>
            <p v-else class="contact-line">Hosted by {{ booking.hostName }}</p>
            <p v-if="booking.specialRequests" class="request-line">
              <strong>Guest note:</strong> {{ booking.specialRequests }}
            </p>
            <p class="payment-note">Online payment is not available yet; arrange payment directly with your host.</p>

            <button
              v-if="booking.status === 'CONFIRMED' && booking.checkInDate > today"
              class="cancel-button"
              :disabled="cancellingId === booking.id"
              @click="cancelBooking(booking)"
            >
              {{ cancellingId === booking.id ? 'Cancelling…' : 'Cancel reservation' }}
            </button>
          </div>
        </article>
      </div>
    </section>
  </main>
</template>

<script>
import bookingService from '@/services/bookingService';
import { toLocalDateKey } from '@/utils/date';

export default {
  name: 'BookingsPage',
  data() {
    return {
      isHost: false,
      bookings: [],
      loading: true,
      errorMessage: '',
      cancellingId: null,
      today: toLocalDateKey()
    };
  },
  mounted() {
    this.isHost = this.$route.meta.role === 'host';
    this.loadBookings();
  },
  methods: {
    formatDate(date) {
      const [year, month, day] = date.split('-').map(Number);
      return new Date(year, month - 1, day).toLocaleDateString(undefined, {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
      });
    },
    formatStatus(status) {
      return status.charAt(0) + status.slice(1).toLowerCase();
    },
    async loadBookings() {
      this.loading = true;
      this.errorMessage = '';
      try {
        this.bookings = this.isHost
          ? await bookingService.getHostBookings()
          : await bookingService.getGuestBookings();
      } catch (error) {
        console.error('Could not load reservations:', error);
        this.errorMessage = error.message || 'Could not load reservations. Please try again.';
      } finally {
        this.loading = false;
      }
    },
    async cancelBooking(booking) {
      if (!window.confirm(`Cancel the reservation for ${booking.propertyTitle}?`)) return;
      this.cancellingId = booking.id;
      this.errorMessage = '';
      try {
        await bookingService.cancelBooking(booking.id);
        await this.loadBookings();
      } catch (error) {
        console.error('Could not cancel reservation:', error);
        this.errorMessage = error.message || 'Could not cancel this reservation.';
      } finally {
        this.cancellingId = null;
      }
    }
  }
};
</script>

<style scoped>
.bookings-page {
  min-height: 100vh;
  background: #fbfaf7;
  color: #263532;
}

.bookings-nav,
.bookings-content {
  width: min(1100px, calc(100% - 40px));
  margin: 0 auto;
}

.bookings-nav {
  min-height: 76px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e7e8e2;
}

.brand,
.back-link,
.details-link {
  color: #23675b;
  font-weight: 750;
  text-decoration: none;
}

.brand {
  font-size: 1.4rem;
  letter-spacing: -0.04em;
}

.bookings-content {
  padding: 48px 0 72px;
}

.eyebrow {
  margin: 0 0 10px;
  color: #ad634a;
  font-size: 0.8rem;
  font-weight: 800;
  letter-spacing: 0.13em;
}

h1 {
  margin: 0;
  font-size: clamp(2rem, 5vw, 3.5rem);
  letter-spacing: -0.06em;
}

.intro,
.location,
.contact-line,
.request-line {
  color: #687873;
}

.booking-list {
  display: grid;
  gap: 18px;
  margin-top: 30px;
}

.booking-card {
  display: grid;
  grid-template-columns: minmax(220px, 0.34fr) minmax(0, 1fr);
  overflow: hidden;
  border: 1px solid #e7e8e2;
  border-radius: 18px;
  background: white;
  box-shadow: 0 12px 34px rgb(32 48 41 / 5%);
}

.booking-image {
  min-height: 270px;
  background: #e8eee9;
}

.booking-image img {
  width: 100%;
  height: 100%;
  min-height: 270px;
  object-fit: cover;
}

.image-placeholder {
  height: 100%;
  display: grid;
  place-items: center;
  color: #687873;
  font-weight: 800;
}

.booking-content {
  padding: 24px;
}

.booking-heading {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.booking-heading h2 {
  margin: 12px 0 4px;
  font-size: 1.3rem;
}

.location {
  margin: 0;
}

.status-pill {
  display: inline-flex;
  padding: 5px 10px;
  border-radius: 99px;
  background: #eaf3ed;
  color: #256348;
  font-size: 0.76rem;
  font-weight: 800;
}

.status-pill.cancelled {
  background: #fff0ed;
  color: #9b4739;
}

.status-pill.completed {
  background: #edf0f3;
  color: #4d5c68;
}

.booking-facts {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 14px;
  margin: 22px 0;
}

.booking-facts > div {
  display: grid;
  gap: 5px;
}

.fact-label {
  color: #687873;
  font-size: 0.8rem;
}

.address-line,
.payment-note {
  padding: 11px 13px;
  border-radius: 9px;
  background: #eef4f0;
  color: #53655e;
  line-height: 1.5;
}

.payment-note {
  background: #fff4e5;
  color: #70511e;
  font-size: 0.86rem;
}

.cancel-button {
  padding: 10px 14px;
  border: 1px solid #d9a499;
  border-radius: 9px;
  background: white;
  color: #994435;
  font: inherit;
  font-weight: 700;
  cursor: pointer;
}

.cancel-button:disabled {
  opacity: 0.6;
  cursor: wait;
}

.empty-state,
.page-state {
  margin-top: 30px;
  padding: 44px 24px;
  border: 1px solid #e7e8e2;
  border-radius: 18px;
  background: white;
  text-align: center;
}

.empty-icon {
  color: #23675b;
  font-size: 2.5rem;
}

.primary-link {
  display: inline-flex;
  justify-content: center;
  margin-top: 12px;
  padding: 12px 16px;
  border-radius: 10px;
  background: #23675b;
  color: white;
  font-weight: 700;
  text-decoration: none;
}

.error-message {
  color: #9b2c2c;
  background: #fff1f0;
  padding: 14px;
  border-radius: 10px;
}

@media (max-width: 760px) {
  .booking-card {
    grid-template-columns: 1fr;
  }

  .booking-image,
  .booking-image img {
    min-height: 210px;
    max-height: 260px;
  }

  .booking-facts {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>
