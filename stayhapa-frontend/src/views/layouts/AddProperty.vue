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
        <h1>{{ isEditing ? 'Edit Your Property' : 'List Your Property' }}</h1>
        <p>{{ isEditing ? 'Keep your listing details up to date' : 'Share your space with travelers from around the world' }}</p>
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
                <option value="APARTMENT">Apartment</option>
                <option value="HOUSE">House</option>
                <option value="CONDO">Condo</option>
                <option value="TOWNHOUSE">Townhouse</option>
                <option value="CABIN">Cabin</option>
                <option value="COTTAGE">Cottage</option>
                <option value="VILLA">Villa</option>
                <option value="LOFT">Loft</option>
                <option value="STUDIO">Studio</option>
                <option value="OTHER">Other</option>
              </select>
            </div>

            <div class="form-group">
              <label>Price per Night (KSh) *</label>
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
              Search the map to pinpoint your property, or enter the address below if the map is unavailable.
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
              <label>Beds *</label>
              <input
                type="number"
                v-model.number="propertyData.beds"
                placeholder="1"
                min="1"
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
          <div v-if="errorMessage" class="form-error" role="alert">
            <p>{{ errorMessage }}</p>
            <div v-if="authenticationError" class="auth-recovery">
              <router-link :to="loginLocation">Sign in as a host</router-link>
              <span>·</span>
              <router-link to="/signup?userType=host">Create a host account</router-link>
            </div>
          </div>
          <button type="button" @click="$router.back()" class="cancel-btn">Cancel</button>
          <button type="submit" :disabled="loading || !isFormValid" class="submit-btn">
            {{ loading ? 'Saving...' : isEditing ? 'Save Changes' : 'Publish Property' }}
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
import propertyService from '@/services/propertyService';
import LocationPicker from '@/components/Maps/LocationPicker.vue';

export default {
  name: 'AddProperty',
  components: { LocationPicker },
  data() {
    return {
      currentUser: null,
      loading: false,
      errorMessage: '',
      authenticationError: false,
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
        beds: 1,
        bathrooms: null,
        maxGuests: null,
        amenities: [],
        images: [],
        location: null,
        latitude: null,
        longitude: null,
        placeId: null
      }
    }
  },
  computed: {
    isEditing() {
      return Boolean(this.$route.params.id)
    },
    loginLocation() {
      return {
        path: '/login',
        query: { userType: 'host', redirect: this.$route.fullPath }
      };
    },
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
        this.propertyData.beds >= 1 &&
        this.propertyData.bathrooms !== null &&
        this.propertyData.maxGuests > 0
      )
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('stayhapa_current_user');
      localStorage.removeItem('stayhapa_token');
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
      
      const componentValue = (types) => {
        const component = location.addressComponents?.find(item =>
          types.some(type => item.types.includes(type))
        );
        return component?.longText || component?.long_name || '';
      };

      if (!this.propertyData.city) {
        this.propertyData.city = componentValue(['locality', 'postal_town', 'administrative_area_level_2']);
      }
      if (!this.propertyData.country) {
        this.propertyData.country = componentValue(['country']);
      }
    },
    validateForm() {
      const requiredFields = [
        'title', 'description', 'type', 'price', 
        'address', 'city', 'country', 'bedrooms', 'beds',
        'bathrooms', 'maxGuests'
      ];
      
      for (let field of requiredFields) {
        if (this.propertyData[field] === null || this.propertyData[field] === undefined || this.propertyData[field] === '') {
          return `${field.charAt(0).toUpperCase() + field.slice(1)} is required`;
        }
      }

      if (this.propertyData.price < 1) {
        return 'Price must be at least KSh 1';
      }

      if (this.propertyData.maxGuests < 1) {
        return 'Maximum guests must be at least 1';
      }

      if (this.propertyData.bedrooms < 0 || this.propertyData.bathrooms < 0) {
        return 'Bedrooms and bathrooms cannot be negative';
      }

      if (this.propertyData.beds < 1) {
        return 'Beds must be at least 1';
      }

      return null;
    },
    async handleSubmit() {
      this.errorMessage = '';
      this.authenticationError = false;
      const validationError = this.validateForm();
      if (validationError) {
        this.errorMessage = validationError;
        return;
      }

      this.loading = true;

      try {
        const propertyPayload = {
          title: this.propertyData.title,
          description: this.propertyData.description,
          address: this.propertyData.address,
          city: this.propertyData.city,
          country: this.propertyData.country,
          latitude: this.propertyData.latitude,
          longitude: this.propertyData.longitude,
          pricePerNight: this.propertyData.price,
          bedrooms: this.propertyData.bedrooms,
          beds: this.propertyData.beds,
          bathrooms: this.propertyData.bathrooms,
          maxGuests: this.propertyData.maxGuests,
          propertyType: this.propertyData.type,
          amenities: this.propertyData.amenities,
          images: this.propertyData.images,
          active: this.propertyData.active
        };

        if (this.isEditing) {
          await propertyService.updateProperty(this.$route.params.id, propertyPayload);
        } else {
          await propertyService.createProperty(propertyPayload);
        }

        await this.$router.push('/host/dashboard');

      } catch (error) {
        console.error('Error listing property:', error);
        if (error.status === 401) {
          this.authenticationError = true;
          this.errorMessage = 'Your host session is no longer valid. Sign in again; if your account was cleared when the backend restarted, create a new host account.';
        } else if (error.status === 403) {
          this.authenticationError = true;
          this.errorMessage = 'This action requires a host account. Sign in with your host account or create one.';
        } else {
          this.errorMessage = error.message || 'Failed to list property. Please try again.';
        }
      } finally {
        this.loading = false;
      }
    }
  },
  async mounted() {
    this.loadUserData();
    if (this.isEditing) {
      try {
        const property = await propertyService.getPropertyById(this.$route.params.id);
        this.propertyData = {
          ...this.propertyData,
          ...property,
          type: property.propertyType,
          price: Number(property.pricePerNight),
          location: property.latitude && property.longitude
            ? { lat: property.latitude, lng: property.longitude, address: property.address }
            : null
        };
      } catch (error) {
        console.error('Error loading property:', error);
        this.errorMessage = error.message || 'Could not load this property.';
      }
    }
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
  min-width: 0;
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
  grid-template-columns: repeat(2, minmax(0, 1fr));
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
  min-width: 0;
  gap: 0.5rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.amenity-checkbox span {
  line-height: 1.4;
  overflow-wrap: anywhere;
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
  flex-wrap: wrap;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 2rem;
}

.form-error {
  flex: 0 0 100%;
  margin: 0;
  line-height: 1.5;
  overflow-wrap: anywhere;
}

.form-error p {
  margin: 0;
}

.auth-recovery {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
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

  .user-welcome {
    max-width: 100%;
    overflow-wrap: anywhere;
    text-align: center;
  }
}
</style>