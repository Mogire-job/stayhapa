<template>
  <main class="property-page">
    <nav class="property-nav">
      <router-link to="/listings" class="back-link">← Browse stays</router-link>
      <router-link to="/" class="brand">Stayhapa</router-link>
    </nav>

    <p v-if="loading" class="page-state" role="status">Loading stay details…</p>
    <section v-else-if="errorMessage" class="page-state error-state" role="alert">
      <h1>We couldn't load this stay</h1>
      <p>{{ errorMessage }}</p>
      <router-link to="/listings" class="primary-link">Back to available stays</router-link>
    </section>

    <article v-else-if="property" class="property-content">
      <header class="property-heading">
        <p class="eyebrow">{{ property.propertyType }}</p>
        <h1>{{ property.title }}</h1>
        <p class="location">{{ [property.city, property.country].filter(Boolean).join(', ') }}</p>
      </header>

      <div class="photo-grid">
        <img
          v-for="(image, index) in property.images"
          :key="`${property.id}-${index}`"
          :src="image"
          :alt="`${property.title} — photo ${index + 1}`"
        >
        <div v-if="!property.images?.length" class="photo-placeholder">Photos coming soon</div>
      </div>

      <div class="details-layout">
        <section class="details-copy">
          <div class="host-line">
            <h2>Hosted by {{ property.hostName || 'a Stayhapa host' }}</h2>
          </div>
          <p class="capacity">
            {{ property.maxGuests || 0 }} guests · {{ property.bedrooms || 0 }} bedrooms ·
            {{ property.beds || 0 }} beds · {{ property.bathrooms || 0 }} bathrooms
          </p>
          <p class="description">{{ property.description || 'The host has not added a description yet.' }}</p>

          <section v-if="property.amenities?.length" class="amenities">
            <h2>What this place offers</h2>
            <ul>
              <li v-for="amenity in property.amenities" :key="amenity">{{ amenity }}</li>
            </ul>
          </section>
          <p class="privacy-note">
            The exact street address is kept private and is not shown in public listing details.
          </p>
        </section>

        <aside class="booking-card">
          <p class="price">KSh {{ Number(property.pricePerNight).toLocaleString() }} <span>/ night</span></p>
          <div v-if="bookingConfirmation" class="booking-confirmation" role="status">
            <h2>Reservation confirmed</h2>
            <p>Your dates are reserved with {{ bookingConfirmation.hostName }}.</p>
            <p v-if="bookingConfirmation.address"><strong>Stay address:</strong> {{ bookingConfirmation.address }}</p>
            <p>
              {{ bookingConfirmation.nights }} nights ·
              KSh {{ Number(bookingConfirmation.totalPrice).toLocaleString() }}
            </p>
            <p class="payment-note">
              Payment is not collected online yet. Contact your host to arrange payment before travelling.
            </p>
            <router-link to="/bookings" class="primary-link">View my reservations</router-link>
          </div>

          <div v-else-if="accountType === 'host'" class="booking-note">
            Reservations are for guest accounts. Sign in with a guest account or
            <router-link to="/signup?userType=guest">create one</router-link>.
          </div>

          <div v-else-if="accountType !== 'guest' || !hasSession" class="booking-note">
            <p>Sign in as a guest to reserve available dates automatically.</p>
            <router-link
              :to="{ path: '/login', query: { userType: 'guest', redirect: $route.fullPath } }"
              class="primary-link"
            >Sign in to reserve</router-link>
            <router-link to="/signup?userType=guest" class="guest-signup-link">Create a guest account</router-link>
          </div>

          <form v-else class="booking-form" @submit.prevent="submitBooking">
            <label>
              Check-in
              <input v-model="bookingForm.checkInDate" type="date" :min="today" required>
            </label>
            <label>
              Check-out
              <input v-model="bookingForm.checkOutDate" type="date" :min="minimumCheckOut" required>
            </label>
            <label>
              Guests
              <input
                v-model.number="bookingForm.numberOfGuests"
                type="number"
                min="1"
                :max="property.maxGuests || undefined"
                required
              >
            </label>
            <label>
              Note for your host <span class="optional-label">(optional)</span>
              <textarea v-model="bookingForm.specialRequests" maxlength="1000" rows="3"></textarea>
            </label>
            <p v-if="bookingError" class="booking-error" role="alert">{{ bookingError }}</p>
            <div v-if="estimatedTotal" class="estimate">
              <span>{{ bookingNights }} nights × KSh {{ Number(property.pricePerNight).toLocaleString() }}</span>
              <strong>KSh {{ estimatedTotal.toLocaleString() }}</strong>
            </div>
            <button class="primary-link reserve-button" type="submit" :disabled="bookingSubmitting">
              {{ bookingSubmitting ? 'Confirming…' : 'Reserve stay' }}
            </button>
            <p class="booking-note">
              Your reservation confirms as soon as the dates are available. No online payment is collected yet.
            </p>
            <p v-if="property.maxGuests">Up to {{ property.maxGuests }} guests · {{ bookingForm.numberOfGuests }} selected</p>
          </form>
        </aside>
      </div>
    </article>
  </main>
</template>

<script>
import propertyService from '@/services/propertyService';
import bookingService from '@/services/bookingService';
import { toLocalDateKey } from '@/utils/date';

export default {
  name: 'PropertyDetails',
  data() {
    return {
      property: null,
      loading: true,
      errorMessage: '',
      currentUser: null,
      bookingForm: {
        checkInDate: '',
        checkOutDate: '',
        numberOfGuests: 1,
        specialRequests: ''
      },
      bookingSubmitting: false,
      bookingError: '',
      bookingConfirmation: null,
      today: this.formatDate(new Date())
    };
  },
  computed: {
    accountType() {
      return this.currentUser?.userType || '';
    },
    hasSession() {
      return Boolean(localStorage.getItem('stayhapa_token') && this.currentUser);
    },
    minimumCheckOut() {
      if (!this.bookingForm.checkInDate) return this.today;
      const date = new Date(`${this.bookingForm.checkInDate}T12:00:00`);
      date.setDate(date.getDate() + 1);
      return this.formatDate(date);
    },
    bookingNights() {
      if (!this.bookingForm.checkInDate || !this.bookingForm.checkOutDate) return 0;
      return Math.round(
        (new Date(`${this.bookingForm.checkOutDate}T12:00:00`) -
          new Date(`${this.bookingForm.checkInDate}T12:00:00`)) /
          (24 * 60 * 60 * 1000)
      );
    },
    estimatedTotal() {
      return this.bookingNights > 0
        ? this.bookingNights * Number(this.property?.pricePerNight || 0)
        : 0;
    }
  },
  watch: {
    '$route.params.id'() {
      this.loadProperty();
    }
  },
  mounted() {
    try {
      this.currentUser = JSON.parse(localStorage.getItem('stayhapa_current_user') || 'null');
    } catch (error) {
      console.error('Could not read the saved account session:', error);
      localStorage.removeItem('stayhapa_current_user');
      localStorage.removeItem('stayhapa_token');
    }
    this.loadProperty();
  },
  methods: {
    formatDate(date) {
      return toLocalDateKey(date);
    },
    async loadProperty() {
      this.loading = true;
      this.errorMessage = '';
      this.property = null;
      try {
        this.property = await propertyService.getPropertyById(this.$route.params.id);
      } catch (error) {
        console.error('Error loading property details:', error);
        this.errorMessage = error.message || 'Please try again later.';
      } finally {
        this.loading = false;
      }
    },
    async submitBooking() {
      this.bookingError = '';
      if (this.accountType !== 'guest' || !this.hasSession) {
        this.bookingError = 'Please sign in with a guest account to make a reservation.';
        return;
      }
      this.bookingSubmitting = true;
      try {
        this.bookingConfirmation = await bookingService.createBooking(this.property.id, {
          ...this.bookingForm
        });
      } catch (error) {
        console.error('Could not create reservation:', error);
        this.bookingError = error.message || 'Could not confirm these dates. Please try again.';
      } finally {
        this.bookingSubmitting = false;
      }
    }
  }
};
</script>

<style scoped>
.property-page {
  min-height: 100vh;
  background: #fbfaf7;
  color: #263532;
}

.property-nav,
.property-content {
  width: min(1120px, calc(100% - 40px));
  margin: 0 auto;
}

.property-nav {
  min-height: 76px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e7e8e2;
}

.back-link,
.brand {
  color: #23675b;
  text-decoration: none;
  font-weight: 700;
}

.brand {
  font-size: 1.4rem;
  letter-spacing: -0.04em;
}

.property-content {
  padding: 40px 0 64px;
}

.property-heading h1 {
  margin: 8px 0;
  font-size: clamp(2rem, 5vw, 3.3rem);
  letter-spacing: -0.05em;
}

.eyebrow {
  margin: 0;
  color: #ad634a;
  font-size: 0.8rem;
  font-weight: 800;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.location,
.capacity {
  color: #65736e;
}

.photo-grid {
  min-height: 300px;
  max-height: 540px;
  margin: 28px 0;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
  overflow: hidden;
  border-radius: 20px;
  background: #e7ece8;
}

.photo-grid img {
  width: 100%;
  height: 100%;
  min-height: 250px;
  max-height: 500px;
  object-fit: cover;
}

.photo-placeholder {
  grid-column: 1 / -1;
  min-height: 300px;
  display: grid;
  place-items: center;
  color: #65736e;
}

.details-layout {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 340px;
  gap: 44px;
  align-items: start;
}

.host-line h2,
.amenities h2 {
  margin: 0 0 12px;
  font-size: 1.2rem;
}

.description {
  line-height: 1.7;
  white-space: pre-line;
}

.amenities ul {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px;
  padding: 0;
  list-style: none;
}

.privacy-note,
.booking-note {
  padding: 14px 16px;
  border-radius: 12px;
  background: #eef4f0;
  color: #53655e;
  line-height: 1.6;
}

.booking-form {
  display: grid;
  gap: 14px;
  margin-top: 20px;
}

.booking-form label {
  display: grid;
  gap: 6px;
  font-size: 0.9rem;
  font-weight: 700;
}

.booking-form input,
.booking-form textarea {
  width: 100%;
  padding: 11px 12px;
  border: 1px solid #d9dfd9;
  border-radius: 9px;
  background: white;
  color: #263532;
  font: inherit;
}

.booking-form input:focus,
.booking-form textarea:focus {
  outline: 2px solid #78a99a;
  outline-offset: 1px;
}

.optional-label {
  color: #74807b;
  font-weight: 400;
}

.estimate {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  padding-top: 12px;
  border-top: 1px solid #e7e8e2;
}

.reserve-button {
  width: 100%;
  border: 0;
  cursor: pointer;
  font: inherit;
}

.reserve-button:disabled {
  cursor: wait;
  opacity: 0.7;
}

.booking-error {
  margin: 0;
  color: #9b2c2c;
  font-size: 0.9rem;
}

.booking-confirmation h2 {
  font-size: 1.2rem;
}

.payment-note {
  padding: 12px;
  border-radius: 10px;
  background: #fff4e5;
  color: #70511e;
  line-height: 1.5;
}

.guest-signup-link {
  display: block;
  margin-top: 14px;
  color: #23675b;
  text-align: center;
  font-weight: 700;
}

.booking-card {
  position: sticky;
  top: 24px;
  padding: 24px;
  border: 1px solid #e6e8e2;
  border-radius: 18px;
  background: white;
  box-shadow: 0 12px 32px rgb(32 52 46 / 8%);
}

.price {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 800;
}

.price span {
  color: #74807b;
  font-size: 0.95rem;
  font-weight: 500;
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

.page-state {
  width: min(720px, calc(100% - 40px));
  margin: 72px auto;
  text-align: center;
}

.error-state {
  color: #8f352d;
}

@media (max-width: 760px) {
  .details-layout {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .booking-card {
    position: static;
  }

  .photo-grid {
    grid-template-columns: 1fr;
  }

  .photo-grid img:not(:first-child) {
    display: none;
  }
}
</style>
