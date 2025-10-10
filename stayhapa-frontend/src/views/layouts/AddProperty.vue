<template>
  <div class="add-property">
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <span class="user-welcome" v-if="currentUser">
            Welcome, {{ currentUser.fullName }}
          </span>
          <span class="user-welcome" v-else>
            Loading...
          </span>
          <router-link to="/host/dashboard" class="nav-link">Dashboard</router-link>
          <button @click="handleLogout" class="nav-link">Logout</button>
        </div>
      </div>
    </nav>

    <div class="add-property-container" v-if="currentUser">
      <div class="form-header">
        <h1>List Your Property</h1>
        <p>Share your space with travelers from around the world</p>
      </div>

      <form @submit.prevent="handleSubmit" class="property-form">
        <div class="form-section">
          <h2>Basic Information</h2>
          
          <div class="form-group">
            <label>Property Title *</label>
            <input 
              type="text" 
              v-model="propertyData.title" 
              placeholder="e.g., Beautiful Beachfront Villa"
              required
            >
          </div>

          <div class="form-group">
            <label>Description *</label>
            <textarea 
              v-model="propertyData.description" 
              placeholder="Describe your property, amenities, and what makes it special..."
              rows="4"
              required
            ></textarea>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>Property Type *</label>
              <select v-model="propertyData.type" required>
                <option value="">Select type</option>
                <option value="apartment">Apartment</option>
                <option value="villa">Villa</option>
                <option value="beach_house">Beach House</option>
                <option value="cabin">Cabin</option>
                <option value="luxury">Luxury</option>
                <option value="unique">Unique Stay</option>
              </select>
            </div>

            <div class="form-group">
              <label>Price per Night ($) *</label>
              <input 
                type="number" 
                v-model.number="propertyData.price" 
                placeholder="0"
                min="1"
                required
              >
            </div>
          </div>
        </div>

        <div class="form-section">
          <h2>Location</h2>
          
          <div class="form-group">
            <label>Precise Location *</label>
            <p class="location-help">
              Use the map below to pinpoint your property's exact location. This helps guests find your property easily.
            </p>
            <LocationPicker
              ref="locationPicker"
              :initialLocation="propertyData.location"
              @location-selected="onLocationSelected"
            />
          </div>

          <div class="form-group">
            <label>Address Display *</label>
            <input 
              type="text" 
              v-model="propertyData.address" 
              placeholder="Full address for display"
              required
            >
            <small class="help-text">This is the address that will be shown to guests after booking</small>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>City *</label>
              <input 
                type="text" 
                v-model="propertyData.city" 
                placeholder="City"
                required
              >
            </div>

            <div class="form-group">
              <label>Country *</label>
              <input 
                type="text" 
                v-model="propertyData.country" 
                placeholder="Country"
                required
              >
            </div>
          </div>
        </div>

        <div class="form-section">
          <h2>Property Details</h2>
          
          <div class="form-row">
            <div class="form-group">
              <label>Bedrooms *</label>
              <input 
                type="number" 
                v-model.number="propertyData.bedrooms" 
                placeholder="0"
                min="0"
                required
              >
            </div>

            <div class="form-group">
              <label>Bathrooms *</label>
              <input 
                type="number" 
                v-model.number="propertyData.bathrooms" 
                placeholder="0"
                min="0"
                required
              >
            </div>

            <div class="form-group">
              <label>Max Guests *</label>
              <input 
                type="number" 
                v-model.number="propertyData.maxGuests" 
                placeholder="0"
                min="1"
                required
              >
            </div>
          </div>

          <div class="form-group">
            <label>Amenities</label>
            <div class="amenities-grid">
              <label v-for="amenity in amenitiesList" :key="amenity" class="amenity-checkbox">
                <input type="checkbox" :value="amenity" v-model="propertyData.amenities">
                <span>{{ amenity }}</span>
              </label>
            </div>
          </div>
        </div>

        <div class="form-section">
          <h2>Photos</h2>
          <div class="image-upload">
            <div class="upload-area" @click="triggerFileInput">
              <input 
                type="file" 
                ref="fileInput" 
                multiple 
                accept="image/*" 
                @change="handleImageUpload"
                style="display: none"
              >
              <div class="upload-content">
                <div class="upload-icon">📷</div>
                <p>Click to upload photos of your property</p>
                <span>Recommended: 5-10 high-quality images</span>
              </div>
            </div>
            
            <div v-if="propertyData.images.length > 0" class="image-preview">
              <div v-for="(image, index) in propertyData.images" :key="index" class="preview-item">
                <img :src="image" :alt="`Property image ${index + 1}`">
                <button type="button" @click="removeImage(index)" class="remove-btn">×</button>
              </div>
            </div>
          </div>
        </div>

        <div class="form-actions">
          <button type="button" @click="$router.back()" class="cancel-btn">Cancel</button>
          <button type="submit" :disabled="loading || !isFormValid" class="submit-btn">
            {{ loading ? 'Publishing...' : 'Publish Property' }}
          </button>
        </div>
      </form>
    </div>

    <!-- Loading state -->
    <div v-else class="loading-container">
      <div class="loading-spinner"></div>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script>
import { Loader } from '@googlemaps/js-api-loader';

export default {
  name: 'AddProperty',
  data() {
    return {
      currentUser: null,
      loading: false,
      amenitiesList: [
        'WiFi', 'Kitchen', 'Parking', 'Pool', 'Air Conditioning', 
        'Heating', 'Washer', 'Dryer', 'TV', 'Gym', 'Hot Tub',
        'Breakfast', 'Pet Friendly', 'Family Friendly', 'Smoking Allowed'
      ],
      propertyData: {
        title: '',
        description: '',
        type: '',
        price: null,
        address: '',
        city: '',
        country: '',
        bedrooms: null,
        bathrooms: null,
        maxGuests: null,
        amenities: [],
        images: [],
        location: null,
        latitude: null,
        longitude: null,
        placeId: null
      },
      mapLoader: null,
      isMapLoaded: false
    }
  },
  computed: {
    isFormValid() {
      return (
        this.propertyData.title &&
        this.propertyData.description &&
        this.propertyData.type &&
        this.propertyData.price > 0 &&
        this.propertyData.address &&
        this.propertyData.city &&
        this.propertyData.country &&
        this.propertyData.bedrooms !== null &&
        this.propertyData.bathrooms !== null &&
        this.propertyData.maxGuests > 0 &&
        this.propertyData.location
      )
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      this.$router.push('/');
    },
    loadUserData() {
      try {
        const userData = localStorage.getItem('stayhapa_current_user');
        if (userData) {
          this.currentUser = JSON.parse(userData);
          if (this.currentUser?.userType !== 'host') {
            this.$router.push('/dashboard');
            return;
          }
        } else {
          this.$router.push('/login');
        }
      } catch (error) {
        console.error('Error loading user data:', error);
        this.$router.push('/login');
      }
    },
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    handleImageUpload(event) {
      const files = event.target.files;
      if (files) {
        for (let file of files) {
          // Check file size (max 5MB)
          if (file.size > 5 * 1024 * 1024) {
            alert('File size too large. Please select images smaller than 5MB.');
            continue;
          }
          
          // Check file type
          if (!file.type.startsWith('image/')) {
            alert('Please select valid image files only.');
            continue;
          }

          const reader = new FileReader();
          reader.onload = (e) => {
            this.propertyData.images.push(e.target.result);
          };
          reader.onerror = () => {
            alert('Error reading file. Please try again.');
          };
          reader.readAsDataURL(file);
        }
      }
    },
    removeImage(index) {
      this.propertyData.images.splice(index, 1);
    },
    onLocationSelected(location) {
      this.propertyData.location = location;
      this.propertyData.latitude = location.lat;
      this.propertyData.longitude = location.lng;
      this.propertyData.placeId = location.placeId;
      
      // Auto-fill address fields if empty
      if (!this.propertyData.address) {
        this.propertyData.address = location.address;
      }
      
      // Try to extract city and country from the address
      if (!this.propertyData.city || !this.propertyData.country) {
        this.extractCityCountry(location.address);
      }
    },
    extractCityCountry(address) {
      // Simple extraction - you might want to use a more sophisticated approach
      const addressParts = address.split(',');
      if (addressParts.length >= 2) {
        if (!this.propertyData.city) {
          this.propertyData.city = addressParts[addressParts.length - 2].trim();
        }
        if (!this.propertyData.country) {
          this.propertyData.country = addressParts[addressParts.length - 1].trim();
        }
      }
    },
    validateForm() {
      const requiredFields = [
        'title', 'description', 'type', 'price', 
        'address', 'city', 'country', 'bedrooms', 
        'bathrooms', 'maxGuests'
      ];
      
      for (let field of requiredFields) {
        if (!this.propertyData[field]) {
          return `${field.charAt(0).toUpperCase() + field.slice(1)} is required`;
        }
      }

      if (!this.propertyData.location) {
        return 'Please select a precise location on the map';
      }

      if (this.propertyData.price < 1) {
        return 'Price must be at least $1';
      }

      if (this.propertyData.maxGuests < 1) {
        return 'Maximum guests must be at least 1';
      }

      if (this.propertyData.bedrooms < 0 || this.propertyData.bathrooms < 0) {
        return 'Bedrooms and bathrooms cannot be negative';
      }

      return null;
    },
    async handleSubmit() {
      const validationError = this.validateForm();
      if (validationError) {
        alert(validationError);
        return;
      }

      if (!this.propertyData.location) {
        alert('Please select a precise location on the map');
        return;
      }

      this.loading = true;

      try {
        // Simulate API call
        await new Promise(resolve => setTimeout(resolve, 2000));
        
        const property = {
          ...this.propertyData,
          id: Date.now().toString(),
          hostId: this.currentUser.id,
          hostName: this.currentUser.fullName,
          status: 'active',
          rating: 0,
          reviews: [],
          createdAt: new Date().toISOString(),
          // Include location data
          latitude: this.propertyData.latitude,
          longitude: this.propertyData.longitude,
          placeId: this.propertyData.placeId
        };

        // Save to localStorage
        const properties = JSON.parse(localStorage.getItem('stayhapa_properties') || '[]');
        properties.push(property);
        localStorage.setItem('stayhapa_properties', JSON.stringify(properties));

        alert('Property listed successfully!');
        this.$router.push('/host/dashboard');

      } catch (error) {
        console.error('Error listing property:', error);
        alert('Failed to list property. Please try again.');
      } finally {
        this.loading = false;
      }
    },
    async initializeGoogleMaps() {
      try {
        this.mapLoader = new Loader({
          apiKey: process.env.VUE_APP_GOOGLE_MAPS_API_KEY || 'YOUR_GOOGLE_MAPS_API_KEY',
          version: 'weekly',
          libraries: ['places']
        });

        await this.mapLoader.load();
        this.isMapLoaded = true;
      } catch (error) {
        console.error('Error loading Google Maps:', error);
        alert('Failed to load Google Maps. Please check your API key and try again.');
      }
    }
  },
  async mounted() {
    this.loadUserData();
    await this.initializeGoogleMaps();
  }
}
</script>

<style scoped>
.add-property {
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

.user-welcome {
  color: #333;
  font-weight: 500;
}

.nav-link {
  color: #333;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
}

.nav-link:hover {
  color: #0F4C81;
}

.add-property-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem;
  text-align: center;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #0F4C81;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.form-header {
  text-align: center;
  margin-bottom: 3rem;
}

.form-header h1 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 2.5rem;
}

.form-header p {
  color: #666;
  font-size: 1.1rem;
}

.property-form {
  background: white;
  padding: 2rem;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.form-section {
  margin-bottom: 3rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid #e0e0e0;
}

.form-section:last-child {
  border-bottom: none;
}

.form-section h2 {
  color: #333;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #0F4C81;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.amenities-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.amenity-checkbox {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.amenity-checkbox:hover {
  background: #f8f9fa;
}

.amenity-checkbox input {
  width: auto;
}

.image-upload {
  margin-top: 1rem;
}

.upload-area {
  border: 2px dashed #e0e0e0;
  border-radius: 12px;
  padding: 3rem 2rem;
  text-align: center;
  cursor: pointer;
  transition: border-color 0.3s;
  margin-bottom: 1.5rem;
}

.upload-area:hover {
  border-color: #0F4C81;
}

.upload-content .upload-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.upload-content p {
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.upload-content span {
  color: #666;
  font-size: 0.9rem;
}

.image-preview {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  gap: 1rem;
}

.preview-item {
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.preview-item img {
  width: 100%;
  height: 120px;
  object-fit: cover;
  display: block;
}

.remove-btn {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  background: rgba(255, 255, 255, 0.9);
  border: none;
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 1.2rem;
  font-weight: bold;
  transition: background-color 0.3s;
}

.remove-btn:hover {
  background: rgba(255, 255, 255, 1);
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 2rem;
}

.cancel-btn {
  padding: 1rem 2rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background: white;
  color: #333;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
}

.cancel-btn:hover {
  background: #f8f9fa;
}

.submit-btn {
  padding: 1rem 2rem;
  border: none;
  border-radius: 8px;
  background: #0F4C81;
  color: white;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-btn:hover:not(:disabled) {
  background: #0a3a6b;
}

.submit-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.location-help {
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 1rem;
  line-height: 1.4;
}

.help-text {
  color: #666;
  font-size: 0.8rem;
  margin-top: 0.25rem;
  display: block;
}

@media (max-width: 768px) {
  .add-property-container {
    padding: 1rem;
  }
  
  .property-form {
    padding: 1.5rem;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .amenities-grid {
    grid-template-columns: 1fr 1fr;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .nav-container {
    flex-direction: column;
    gap: 1rem;
  }
  
  .nav-links {
    flex-wrap: wrap;
    justify-content: center;
  }
}
</style>