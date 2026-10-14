<template>
  <div class="listings-page">
    <!-- Navigation Bar -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="nav-logo">
          <router-link to="/">
            <h2>Stayhapa</h2>
          </router-link>
        </div>
        <div class="nav-links">
          <router-link to="/become-host" class="nav-link">Become a Host</router-link>
          <router-link to="/dashboard" class="nav-link">Dashboard</router-link>
        </div>
      </div>
    </nav>

    <div class="listings-container">
      <!-- Header with Breadcrumb and Controls -->
      <div class="listings-header">
        <div class="breadcrumb">
          <router-link to="/">Home</router-link>
          <span class="separator">></span>
          <span class="current">{{ currentLocation || 'Nairobi, Kenya' }}</span>
          <span class="results-count">({{ filteredListings.length }} stays)</span>
        </div>
        
        <div class="header-controls">
          <button 
            class="view-toggle"
            :class="{ active: viewMode === 'map' }"
            @click="toggleViewMode"
          >
            {{ viewMode === 'grid' ? 'Show Map' : 'Show List' }}
          </button>
          <div class="sort-dropdown">
            <label>Sort by:</label>
            <select v-model="sortBy" @change="sortListings">
              <option value="recommended">Recommended</option>
              <option value="price_low">Price: Low to High</option>
              <option value="price_high">Price: High to Low</option>
              <option value="rating">Highest Rated</option>
              <option value="newest">Newest</option>
            </select>
          </div>
        </div>
      </div>

      <div class="listings-content">
        <!-- Filters Sidebar -->
        <aside class="filters-sidebar" :class="{ collapsed: isFiltersCollapsed }">
          <div class="filters-header">
            <h3>Filters</h3>
            <button class="collapse-btn" @click="isFiltersCollapsed = !isFiltersCollapsed">
              {{ isFiltersCollapsed ? '›' : '‹' }}
            </button>
          </div>

          <div class="filters-content" v-if="!isFiltersCollapsed">
            <!-- Price Range Filter -->
            <div class="filter-group">
              <h4>Price Range (KES)</h4>
              <div class="price-inputs">
                <input 
                  type="number" 
                  placeholder="Min" 
                  v-model.number="filters.priceRange[0]"
                >
                <span>-</span>
                <input 
                  type="number" 
                  placeholder="Max" 
                  v-model.number="filters.priceRange[1]"
                >
              </div>
              <div class="price-display">
                KES {{ filters.priceRange[0] || 0 }} - KES {{ filters.priceRange[1] || 'Any' }}
              </div>
            </div>

            <!-- Property Type Filter -->
            <div class="filter-group">
              <h4>Property Type</h4>
              <div class="checkbox-group">
                <label v-for="type in propertyTypes" :key="type.value">
                  <input 
                    type="checkbox" 
                    :value="type.value" 
                    v-model="filters.propertyTypes"
                  >
                  {{ type.label }}
                </label>
              </div>
            </div>

            <!-- Location Filter -->
            <div class="filter-group">
              <h4>Location</h4>
              <div class="checkbox-group">
                <label v-for="location in locations" :key="location.value">
                  <input 
                    type="checkbox" 
                    :value="location.value" 
                    v-model="filters.locations"
                  >
                  {{ location.label }}
                </label>
              </div>
            </div>

            <!-- Bedrooms & Beds -->
            <div class="filter-group">
              <h4>Rooms & Beds</h4>
              <div class="counter-controls">
                <div class="counter-item">
                  <label>Bedrooms</label>
                  <div class="counter">
                    <button @click="decrementBedrooms">−</button>
                    <span>{{ filters.bedrooms }}</span>
                    <button @click="incrementBedrooms">+</button>
                  </div>
                </div>
                <div class="counter-item">
                  <label>Beds</label>
                  <div class="counter">
                    <button @click="decrementBeds">−</button>
                    <span>{{ filters.beds }}</span>
                    <button @click="incrementBeds">+</button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Amenities Filter -->
            <div class="filter-group">
              <h4>Amenities</h4>
              <div class="checkbox-group">
                <label v-for="amenity in amenities" :key="amenity.value">
                  <input 
                    type="checkbox" 
                    :value="amenity.value" 
                    v-model="filters.amenities"
                  >
                  {{ amenity.label }}
                </label>
              </div>
            </div>

            <!-- Instant Book -->
            <div class="filter-group">
              <label class="switch">
                <input type="checkbox" v-model="filters.instantBook">
                <span class="slider"></span>
                Instant Book
              </label>
            </div>

            <!-- Filter Actions -->
            <div class="filter-actions">
              <button class="apply-filters" @click="applyFilters">
                Apply Filters
              </button>
              <button class="reset-filters" @click="resetFilters">
                Reset All
              </button>
            </div>
          </div>
        </aside>

        <!-- Main Content Area -->
        <main class="listings-main">
          <!-- Map View -->
          <div v-if="viewMode === 'map'" class="map-view">
            <GoogleMap
              class="listings-google-map"
              :center="mapCenter"
              :zoom="12"
              @map-loaded="onListingsMapLoaded"
              @map-error="onListingsMapError"
            >
              <template #default="{ map, isLoaded }">
                <GoogleMapMarker
                  v-for="listing in isLoaded && map ? mapListings : []"
                  :key="listing.id"
                  :map="map"
                  :position="{ lat: listing.mapLatitude, lng: listing.mapLongitude }"
                  :title="listing.title"
                  :label="`KES ${listing.price.toLocaleString('en-KE')}`"
                  @marker-click="selectMapListing(listing)"
                />
              </template>
            </GoogleMap>

            <p class="map-notice" role="status">
              Map pins show approximate locations to protect host privacy.
              <span v-if="mapListings.length === 0">
                No hosts have added map locations to the current listings yet.
              </span>
              <span v-else-if="mapListings.length < filteredListings.length">
                Some listings do not have a saved map pin.
              </span>
            </p>

            <p v-if="mapError" class="map-error" role="alert">{{ mapError }}</p>

            <article v-if="selectedMapListing" class="map-listing-preview">
              <img
                v-if="selectedMapListing.image"
                :src="selectedMapListing.image"
                :alt="selectedMapListing.title"
              >
              <div>
                <h3>{{ selectedMapListing.title }}</h3>
                <p>{{ selectedMapListing.location }}</p>
                <strong>KES {{ selectedMapListing.price.toLocaleString('en-KE') }} / night</strong>
                <router-link :to="`/property/${selectedMapListing.id}`">View stay</router-link>
              </div>
              <button type="button" aria-label="Close listing preview" @click="selectedMapListing = null">×</button>
            </article>
          </div>

          <!-- Grid View -->
          <div v-else class="listings-grid">
            <!-- Loading State -->
            <div v-if="loading" class="loading-state">
              <div class="loading-spinner"></div>
              <p>Finding amazing stays in Nairobi...</p>
            </div>

            <!-- No Results State -->
            <div v-else-if="errorMessage" class="no-results" role="alert">
              <h3>Stays couldn't be loaded</h3>
              <p>{{ errorMessage }}</p>
            </div>

            <div v-else-if="filteredListings.length === 0" class="no-results">
              <h3>No listings found</h3>
              <p>Try adjusting your filters or search criteria</p>
              <button class="reset-search" @click="resetFilters">
                Reset All Filters
              </button>
            </div>

            <!-- Listings Grid -->
            <div v-else class="listings-cards">
              <div 
                v-for="listing in filteredListings" 
                :key="listing.id"
                class="listing-card"
                @click="viewListing(listing.id)"
              >
                <div class="listing-image">
                  <img v-if="listing.image" :src="listing.image" :alt="listing.title">
                  <div v-else class="no-image">No photo yet</div>
                  <button class="favorite-btn" @click.stop="toggleFavorite(listing.id)">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                      <path 
                        d="M12 21.35L10.55 20.03C5.4 15.36 2 12.28 2 8.5C2 5.42 4.42 3 7.5 3C9.24 3 10.91 3.81 12 5.09C13.09 3.81 14.76 3 16.5 3C19.58 3 22 5.42 22 8.5C22 12.28 18.6 15.36 13.45 20.04L12 21.35Z" 
                        :fill="listing.isFavorite ? 'currentColor' : 'none'"
                        stroke="currentColor" 
                        stroke-width="2"
                      />
                    </svg>
                  </button>
                  <div class="image-badges">
                    <span v-if="listing.instantBook" class="badge instant-badge">Instant Book</span>
                    <span v-if="listing.isNew" class="badge new-badge">New</span>
                    <span class="badge location-badge">{{ listing.area }}</span>
                  </div>
                </div>

                <div class="listing-info">
                  <div class="listing-header">
                    <h3 class="listing-title">{{ listing.title }}</h3>
                    <div v-if="listing.rating" class="listing-rating">
                      <span class="stars">★</span>
                      <span class="rating-number">{{ listing.rating }}</span>
                    </div>
                    <span v-else class="rating-number">New stay</span>
                  </div>

                  <p class="listing-location">{{ listing.location }}</p>
                  <p class="listing-dates">Published by a Stayhapa host</p>

                  <div class="listing-features">
                    <span class="feature">{{ listing.guests }} guests</span>
                    <span class="feature">{{ listing.bedrooms }} bedroom{{ listing.bedrooms !== 1 ? 's' : '' }}</span>
                    <span class="feature">{{ listing.beds }} bed{{ listing.beds !== 1 ? 's' : '' }}</span>
                    <span class="feature">{{ listing.bathrooms }} bath{{ listing.bathrooms !== 1 ? 's' : '' }}</span>
                  </div>

                  <div class="listing-amenities">
                    <span 
                      v-for="amenity in listing.topAmenities" 
                      :key="amenity"
                      class="amenity-tag"
                    >
                      {{ amenity }}
                    </span>
                  </div>

                  <div class="listing-footer">
                    <div class="listing-price">
                      <span class="price">KES {{ listing.price.toLocaleString() }}</span>
                      <span class="period">night</span>
                    </div>
                    <button class="book-now-btn" @click.stop="viewListing(listing.id)">
                      View details
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Pagination -->
            <div v-if="filteredListings.length > 0" class="pagination">
              <button 
                class="pagination-btn" 
                :disabled="currentPage === 1"
                @click="changePage(currentPage - 1)"
              >
                Previous
              </button>
              
              <div class="page-numbers">
                <span 
                  v-for="page in totalPages" 
                  :key="page"
                  class="page-number"
                  :class="{ active: page === currentPage }"
                  @click="changePage(page)"
                >
                  {{ page }}
                </span>
              </div>

              <button 
                class="pagination-btn" 
                :disabled="currentPage === totalPages"
                @click="changePage(currentPage + 1)"
              >
                Next
              </button>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script>
import GoogleMap from '@/components/Maps/GoogleMaps.vue';
import GoogleMapMarker from '@/components/Maps/GoogleMapMarker.vue';
import propertyService from '@/services/propertyService';

export default {
  name: 'ListingsPage',
  components: {
    GoogleMap,
    GoogleMapMarker
  },
  data() {
    return {
      viewMode: 'grid',
      map: null,
      mapError: '',
      selectedMapListing: null,
      isFiltersCollapsed: false,
      loading: true,
      errorMessage: '',
      currentPage: 1,
      listingsPerPage: 12,
      currentLocation: 'Nairobi, Kenya',
      sortBy: 'recommended',
      filters: {
        priceRange: [0, 20000],
        propertyTypes: [],
        locations: [],
        bedrooms: 0,
        beds: 0,
        amenities: [],
        instantBook: false
      },
      propertyTypes: [
        { value: 'apartment', label: 'Apartment' },
        { value: 'house', label: 'House' },
        { value: 'studio', label: 'Studio' }
      ],
      locations: [
        { value: 'kilimani', label: 'Kilimani' },
        { value: 'kileleshwa', label: 'Kileleshwa' },
        { value: 'parklands', label: 'Parklands' }
      ],
      amenities: [
        { value: 'wifi', label: 'WiFi' },
        { value: 'kitchen', label: 'Kitchen' },
        { value: 'pool', label: 'Pool' },
        { value: 'parking', label: 'Free Parking' },
        { value: 'ac', label: 'Air Conditioning' },
        { value: 'heating', label: 'Heating' },
        { value: 'washer', label: 'Washer' },
        { value: 'dryer', label: 'Dryer' },
        { value: 'tv', label: 'TV' },
        { value: 'gym', label: 'Gym' },
        { value: 'security', label: '24/7 Security' }
      ],
      allListings: [
        {
          id: 1,
          title: 'Modern Studio in Kilimani',
          location: 'Kilimani, Nairobi',
          area: 'Kilimani',
          rating: 4.8,
          price: 8500,
          image: 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400',
          guests: 2,
          bedrooms: 1,
          beds: 1,
          bathrooms: 1,
          propertyType: 'studio',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'security'],
          topAmenities: ['WiFi', 'Kitchen', 'AC', 'Security'],
          instantBook: true,
          isNew: true,
          isFavorite: false
        },
        {
          id: 2,
          title: 'Spacious 2-Bedroom Apartment',
          location: 'Kileleshwa, Nairobi',
          area: 'Kileleshwa',
          rating: 4.9,
          price: 12000,
          image: 'https://images.unsplash.com/photo-1560185127-6ed189bf02f4?w=400',
          guests: 4,
          bedrooms: 2,
          beds: 2,
          bathrooms: 2,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'parking', 'security', 'gym'],
          topAmenities: ['WiFi', 'Parking', 'Gym', 'Security'],
          instantBook: true,
          isNew: false,
          isFavorite: true
        },
        {
          id: 3,
          title: 'Luxury 3-Bedroom Penthouse',
          location: 'Parklands, Nairobi',
          area: 'Parklands',
          rating: 4.9,
          price: 18000,
          image: 'https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=400',
          guests: 6,
          bedrooms: 3,
          beds: 3,
          bathrooms: 3,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'parking', 'security', 'gym', 'pool'],
          topAmenities: ['Pool', 'Gym', 'Parking', 'Luxury'],
          instantBook: true,
          isNew: true,
          isFavorite: false
        },
        {
          id: 4,
          title: 'Cozy 1-Bedroom Apartment',
          location: 'Kilimani, Nairobi',
          area: 'Kilimani',
          rating: 4.7,
          price: 8000,
          image: 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=400',
          guests: 2,
          bedrooms: 1,
          beds: 1,
          bathrooms: 1,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'security'],
          topAmenities: ['WiFi', 'Kitchen', 'AC', 'Secure'],
          instantBook: true,
          isNew: false,
          isFavorite: false
        },
        {
          id: 5,
          title: 'Executive Studio with Balcony',
          location: 'Kileleshwa, Nairobi',
          area: 'Kileleshwa',
          rating: 4.8,
          price: 9500,
          image: 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400',
          guests: 2,
          bedrooms: 1,
          beds: 1,
          bathrooms: 1,
          propertyType: 'studio',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'security', 'parking'],
          topAmenities: ['Balcony', 'WiFi', 'Parking'],
          instantBook: false,
          isNew: true,
          isFavorite: true
        },
        {
          id: 6,
          title: 'Family Apartment in Parklands',
          location: 'Parklands, Nairobi',
          area: 'Parklands',
          rating: 4.6,
          price: 15000,
          image: 'https://images.unsplash.com/photo-1595428774223-ef52624120d2?w=400',
          guests: 5,
          bedrooms: 3,
          beds: 3,
          bathrooms: 2,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'parking', 'security'],
          topAmenities: ['Family', 'Spacious', 'Parking'],
          instantBook: true,
          isNew: false,
          isFavorite: false
        },
        {
          id: 7,
          title: 'Modern Bachelor Pad',
          location: 'Kilimani, Nairobi',
          area: 'Kilimani',
          rating: 4.7,
          price: 8200,
          image: 'https://images.unsplash.com/photo-1554995207-c18c203602cb?w=400',
          guests: 2,
          bedrooms: 1,
          beds: 1,
          bathrooms: 1,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'security'],
          topAmenities: ['Modern', 'WiFi', 'Central'],
          instantBook: true,
          isNew: false,
          isFavorite: false
        },
        {
          id: 8,
          title: 'Luxury Studio with City View',
          location: 'Kileleshwa, Nairobi',
          area: 'Kileleshwa',
          rating: 4.9,
          price: 11000,
          image: 'https://images.unsplash.com/photo-1484154218962-a197022b5858?w=400',
          guests: 2,
          bedrooms: 1,
          beds: 1,
          bathrooms: 1,
          propertyType: 'studio',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'security', 'gym'],
          topAmenities: ['City View', 'Luxury', 'Gym'],
          instantBook: true,
          isNew: true,
          isFavorite: false
        },
        {
          id: 9,
          title: 'Spacious Garden Apartment',
          location: 'Parklands, Nairobi',
          area: 'Parklands',
          rating: 4.5,
          price: 13000,
          image: 'https://images.unsplash.com/photo-1613977257363-707ba9348227?w=400',
          guests: 4,
          bedrooms: 2,
          beds: 2,
          bathrooms: 2,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'parking', 'security'],
          topAmenities: ['Garden', 'Spacious', 'Quiet'],
          instantBook: false,
          isNew: false,
          isFavorite: true
        },
        {
          id: 10,
          title: 'Executive 2-Bedroom Suite',
          location: 'Kilimani, Nairobi',
          area: 'Kilimani',
          rating: 4.8,
          price: 14000,
          image: 'https://images.unsplash.com/photo-1628133287836-40bd5453bed1?w=400',
          guests: 4,
          bedrooms: 2,
          beds: 2,
          bathrooms: 2,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'parking', 'security', 'gym'],
          topAmenities: ['Executive', 'Gym', 'Secure'],
          instantBook: true,
          isNew: false,
          isFavorite: false
        },
        {
          id: 11,
          title: 'Cozy Studio Near Amenities',
          location: 'Kileleshwa, Nairobi',
          area: 'Kileleshwa',
          rating: 4.6,
          price: 7800,
          image: 'https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?w=400',
          guests: 2,
          bedrooms: 1,
          beds: 1,
          bathrooms: 1,
          propertyType: 'studio',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'security'],
          topAmenities: ['Central', 'Affordable', 'WiFi'],
          instantBook: true,
          isNew: true,
          isFavorite: false
        },
        {
          id: 12,
          title: 'Premium 3-Bedroom Apartment',
          location: 'Parklands, Nairobi',
          area: 'Parklands',
          rating: 4.9,
          price: 20000,
          image: 'https://images.unsplash.com/photo-1605276374104-dee2a0ed3cd6?w=400',
          guests: 6,
          bedrooms: 3,
          beds: 3,
          bathrooms: 3,
          propertyType: 'apartment',
          amenities: ['wifi', 'kitchen', 'ac', 'tv', 'parking', 'security', 'gym', 'pool'],
          topAmenities: ['Premium', 'Pool', 'Gym', 'Luxury'],
          instantBook: true,
          isNew: false,
          isFavorite: true
        }
      ]
    }
  },
  computed: {
    mapListings() {
      return this.filteredListings.filter(listing =>
        Number.isFinite(listing.mapLatitude) && Number.isFinite(listing.mapLongitude)
      );
    },
    mapCenter() {
      if (this.mapListings.length === 0) {
        return { lat: -1.2864, lng: 36.8172 };
      }

      return {
        lat: this.mapListings.reduce((sum, listing) => sum + listing.mapLatitude, 0) / this.mapListings.length,
        lng: this.mapListings.reduce((sum, listing) => sum + listing.mapLongitude, 0) / this.mapListings.length
      };
    },
    filteredListings() {
      let filtered = [...this.allListings];

      // Price filter
      if (this.filters.priceRange[0] > 0 || this.filters.priceRange[1] < 20000) {
        filtered = filtered.filter(listing => 
          listing.price >= this.filters.priceRange[0] && 
          listing.price <= this.filters.priceRange[1]
        );
      }

      // Property type filter
      if (this.filters.propertyTypes.length > 0) {
        filtered = filtered.filter(listing => 
          this.filters.propertyTypes.includes(listing.propertyType)
        );
      }

      // Location filter
      if (this.filters.locations.length > 0) {
        filtered = filtered.filter(listing => 
          this.filters.locations.includes(listing.area.toLowerCase())
        );
      }

      // Bedrooms filter
      if (this.filters.bedrooms > 0) {
        filtered = filtered.filter(listing => 
          listing.bedrooms >= this.filters.bedrooms
        );
      }

      // Beds filter
      if (this.filters.beds > 0) {
        filtered = filtered.filter(listing => 
          listing.beds >= this.filters.beds
        );
      }

      // Amenities filter
      if (this.filters.amenities.length > 0) {
        filtered = filtered.filter(listing =>
          this.filters.amenities.every(amenity => 
            listing.amenities.includes(amenity)
          )
        );
      }

      // Instant book filter
      if (this.filters.instantBook) {
        filtered = filtered.filter(listing => listing.instantBook);
      }

      // Sort listings
      this.sortListingsComputed(filtered);

      return filtered;
    },
    totalPages() {
      return Math.ceil(this.filteredListings.length / this.listingsPerPage);
    },
    paginatedListings() {
      const start = (this.currentPage - 1) * this.listingsPerPage;
      const end = start + this.listingsPerPage;
      return this.filteredListings.slice(start, end);
    }
  },
  mounted() {
    this.loadSearchParams();
    this.loadListings();
  },
  methods: {
    loadSearchParams() {
      const params = this.$route.query;
      if (params.location) {
        this.currentLocation = params.location;
      }
      if (params.category) {
        this.filters.propertyTypes = [params.category];
      }
    },
    async loadListings() {
      this.loading = true;
      this.errorMessage = '';
      try {
        const properties = await propertyService.getAllProperties();
        const activeProperties = properties.filter(property => property.active);
        this.allListings = activeProperties.map(property => {
          const city = property.city || '';
          const country = property.country || '';
          const amenities = (property.amenities || []).map(item => item.toLowerCase());
          const propertyType = (property.propertyType || 'other').toLowerCase();

          return {
            id: property.id,
            title: property.title,
            location: [city, country].filter(Boolean).join(', '),
            mapLatitude: Number.isFinite(property.mapLatitude) ? property.mapLatitude : null,
            mapLongitude: Number.isFinite(property.mapLongitude) ? property.mapLongitude : null,
            area: city,
            rating: null,
            price: Number(property.pricePerNight),
            image: property.images?.[0] || '',
            guests: property.maxGuests || 0,
            bedrooms: property.bedrooms || 0,
            beds: property.beds || 0,
            bathrooms: property.bathrooms || 0,
            propertyType,
            amenities,
            topAmenities: (property.amenities || []).slice(0, 4),
            instantBook: false,
            isNew: property.createdAt
              ? Date.now() - new Date(property.createdAt).getTime() < 30 * 24 * 60 * 60 * 1000
              : false,
            isFavorite: false
          };
        });
        this.propertyTypes = [...new Set(this.allListings.map(listing => listing.propertyType))]
          .map(value => ({
            value,
            label: value.charAt(0).toUpperCase() + value.slice(1).toLowerCase()
          }));
        this.locations = [...new Set(this.allListings.map(listing => listing.area).filter(Boolean))]
          .map(value => ({
            value: value.toLowerCase(),
            label: value
          }));
      } catch (error) {
        console.error('Error loading available properties:', error);
        this.allListings = [];
        this.errorMessage = error.message || 'Could not load available stays.';
      } finally {
        this.loading = false;
      }
    },
    toggleViewMode() {
      this.viewMode = this.viewMode === 'grid' ? 'map' : 'grid';
      this.selectedMapListing = null;
      this.mapError = '';
    },
    onListingsMapLoaded(map) {
      this.map = map;
      this.$nextTick(this.fitListingsOnMap);
    },
    onListingsMapError(error) {
      this.mapError = error.message || 'The map could not be loaded. Check the Google Maps API key and try again.';
    },
    fitListingsOnMap() {
      if (!this.map || this.mapListings.length === 0 || !window.google?.maps) {
        return;
      }

      const bounds = new google.maps.LatLngBounds();
      this.mapListings.forEach(listing => bounds.extend({
        lat: listing.mapLatitude,
        lng: listing.mapLongitude
      }));
      this.map.fitBounds(bounds);
      if (this.mapListings.length === 1) {
        this.map.setZoom(13);
      }
    },
    selectMapListing(listing) {
      this.selectedMapListing = listing;
    },
    applyFilters() {
      this.currentPage = 1;
    },
    resetFilters() {
      this.filters = {
        priceRange: [0, 20000],
        propertyTypes: [],
        locations: [],
        bedrooms: 0,
        beds: 0,
        amenities: [],
        instantBook: false
      };
      this.currentPage = 1;
    },
    sortListings() {
      this.currentPage = 1;
    },
    sortListingsComputed(listings) {
      switch (this.sortBy) {
        case 'price_low':
          listings.sort((a, b) => a.price - b.price);
          break;
        case 'price_high':
          listings.sort((a, b) => b.price - a.price);
          break;
        case 'rating':
          listings.sort((a, b) => b.rating - a.rating);
          break;
        case 'newest':
          // Assuming newer listings have higher IDs
          listings.sort((a, b) => b.id - a.id);
          break;
        default:
          // Recommended - keep original order or implement recommendation algorithm
          break;
      }
    },
    incrementBedrooms() {
      this.filters.bedrooms++;
    },
    decrementBedrooms() {
      if (this.filters.bedrooms > 0) {
        this.filters.bedrooms--;
      }
    },
    incrementBeds() {
      this.filters.beds++;
    },
    decrementBeds() {
      if (this.filters.beds > 0) {
        this.filters.beds--;
      }
    },
    toggleFavorite(listingId) {
      const listing = this.allListings.find(l => l.id === listingId);
      if (listing) {
        listing.isFavorite = !listing.isFavorite;
      }
    },
    viewListing(listingId) {
      this.$router.push(`/property/${listingId}`);
    },
    changePage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    }
  },
  watch: {
    mapListings() {
      this.$nextTick(this.fitListingsOnMap);
    }
  }
}
</script>

<style scoped>
/* Add location badge style */
.location-badge {
  background: #28a745;
  color: white;
}

/* Update price display for KES */
.price {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.period {
  color: #666;
  font-size: 0.9rem;
}

/* Rest of the CSS remains the same as previous version */
.listings-page {
  min-height: 100vh;
  background: #f8f9fa;
}

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

.listings-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.listings-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #666;
}

.breadcrumb a {
  color: #0F4C81;
  text-decoration: none;
}

.breadcrumb a:hover {
  text-decoration: underline;
}

.separator {
  margin: 0 0.5rem;
}

.results-count {
  color: #666;
  font-weight: 500;
}

.header-controls {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.view-toggle {
  background: white;
  border: 1px solid #e0e0e0;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.view-toggle.active {
  background: #0F4C81;
  color: white;
  border-color: #0F4C81;
}

.sort-dropdown {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.sort-dropdown select {
  padding: 0.5rem;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  background: white;
}

.listings-content {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 2rem;
}

.filters-sidebar {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  height: fit-content;
  position: sticky;
  top: 100px;
  transition: all 0.3s;
}

.filters-sidebar.collapsed {
  width: 60px;
  padding: 1rem;
}

.filters-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.filters-header h3 {
  margin: 0;
  color: #333;
}

.collapse-btn {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  color: #666;
}

.filter-group {
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #f0f0f0;
}

.filter-group:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

.filter-group h4 {
  margin: 0 0 1rem 0;
  color: #333;
  font-size: 0.9rem;
}

.price-inputs {
  display: flex;
  gap: 0.5rem;
  align-items: center;
  margin-bottom: 0.5rem;
}

.price-inputs input {
  width: 80px;
  padding: 0.5rem;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
}

.price-display {
  font-size: 0.8rem;
  color: #666;
  text-align: center;
}

.checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.checkbox-group label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.9rem;
  cursor: pointer;
}

.checkbox-group input {
  margin: 0;
}

.counter-controls {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.counter-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.counter {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.counter button {
  background: #f8f9fa;
  border: 1px solid #e0e0e0;
  border-radius: 50%;
  width: 1.5rem;
  height: 1.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.counter button:hover {
  background: #e9ecef;
}

.switch {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
}

.switch input {
  display: none;
}

.slider {
  width: 40px;
  height: 20px;
  background: #e0e0e0;
  border-radius: 20px;
  position: relative;
  transition: background 0.3s;
}

.slider:before {
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

input:checked + .slider {
  background: #0F4C81;
}

input:checked + .slider:before {
  transform: translateX(20px);
}

.filter-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.apply-filters {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.75rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}

.reset-filters {
  background: #f8f9fa;
  color: #666;
  border: 1px solid #e0e0e0;
  padding: 0.75rem;
  border-radius: 8px;
  cursor: pointer;
}

.listings-main {
  min-height: 600px;
}

.map-view {
  position: relative;
  height: 600px;
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.listings-google-map {
  height: 100%;
}

.map-notice {
  position: absolute;
  z-index: 1;
  top: 1rem;
  left: 1rem;
  max-width: min(32rem, calc(100% - 2rem));
  padding: 0.65rem 0.85rem;
  border-radius: 0.75rem;
  background: rgba(255, 255, 255, 0.96);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.16);
  color: #334155;
  font-size: 0.875rem;
}

.map-error {
  position: absolute;
  inset: 50% 1rem auto;
  z-index: 1;
  margin: 0 auto;
  max-width: 32rem;
  padding: 1rem;
  transform: translateY(-50%);
  border-radius: 0.75rem;
  background: #fff;
  color: #9f1239;
  text-align: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.18);
}

.map-listing-preview {
  position: absolute;
  z-index: 2;
  right: 1rem;
  bottom: 1rem;
  left: 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  max-width: 32rem;
  padding: 0.85rem;
  border-radius: 0.9rem;
  background: #fff;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.2);
}

.map-listing-preview img {
  width: 5rem;
  height: 5rem;
  flex: 0 0 auto;
  border-radius: 0.65rem;
  object-fit: cover;
}

.map-listing-preview h3,
.map-listing-preview p {
  margin: 0 0 0.35rem;
}

.map-listing-preview a {
  display: inline-block;
  margin-left: 0.75rem;
  color: #0f4c81;
  font-weight: 600;
}

.map-listing-preview > button {
  align-self: flex-start;
  margin-left: auto;
  border: 0;
  background: transparent;
  font-size: 1.5rem;
  cursor: pointer;
}

.listings-grid {
  min-height: 400px;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem;
  color: #666;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f0f0f0;
  border-top: 4px solid #0F4C81;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.no-results {
  text-align: center;
  padding: 4rem;
  color: #666;
}

.no-results h3 {
  margin-bottom: 1rem;
  color: #333;
}

.reset-search {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 1rem;
}

.listings-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.listing-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  cursor: pointer;
}

.listing-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.listing-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.listing-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.listing-card:hover .listing-image img {
  transform: scale(1.05);
}

.favorite-btn {
  position: absolute;
  top: 0.75rem;
  right: 0.75rem;
  background: rgba(255, 255, 255, 0.9);
  border: none;
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #666;
  transition: all 0.3s;
}

.favorite-btn:hover {
  color: #ff385c;
  background: white;
}

.image-badges {
  position: absolute;
  top: 0.75rem;
  left: 0.75rem;
  display: flex;
  gap: 0.5rem;
}

.badge {
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.7rem;
  font-weight: 600;
}

.instant-badge {
  background: #0F4C81;
  color: white;
}

.new-badge {
  background: #28a745;
  color: white;
}

.listing-info {
  padding: 1.5rem;
}

.listing-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 0.5rem;
}

.listing-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
  color: #333;
  flex: 1;
}

.listing-rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  white-space: nowrap;
}

.stars {
  color: #0F4C81;
}

.rating-number {
  color: #666;
  font-size: 0.9rem;
}

.listing-location {
  color: #666;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
}

.listing-dates {
  color: #0F4C81;
  font-size: 0.8rem;
  margin-bottom: 1rem;
  font-weight: 500;
}

.listing-features {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.feature {
  font-size: 0.8rem;
  color: #666;
}

.listing-amenities {
  display: flex;
  flex-wrap: wrap;
  gap: 0.25rem;
  margin-bottom: 1rem;
}

.amenity-tag {
  background: #f8f9fa;
  color: #666;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.7rem;
}

.listing-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.book-now-btn {
  background: #0F4C81;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
}

.book-now-btn:hover {
  background: #0a3a6b;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  margin-top: 2rem;
}

.pagination-btn {
  background: white;
  border: 1px solid #e0e0e0;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s;
}

.pagination-btn:hover:not(:disabled) {
  background: #f8f9fa;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-numbers {
  display: flex;
  gap: 0.5rem;
}

.page-number {
  padding: 0.5rem 0.75rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s;
}

.page-number:hover {
  background: #f8f9fa;
}

.page-number.active {
  background: #0F4C81;
  color: white;
}

@media (max-width: 768px) {
  .listings-content {
    grid-template-columns: 1fr;
  }
  
  .filters-sidebar {
    position: static;
  }
  
  .listings-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }
  
  .header-controls {
    width: 100%;
    justify-content: space-between;
  }
  
  .listings-cards {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .listings-container {
    padding: 1rem;
  }
  
  .nav-links {
    gap: 1rem;
  }
  
  .nav-link {
    font-size: 0.9rem;
  }
}
</style>