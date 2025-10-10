<template>
  <div class="location-picker">
    <div class="search-container">
      <input
        ref="searchInput"
        type="text"
        v-model="searchQuery"
        placeholder="Search for an address or place..."
        class="location-search-input"
        @input="onSearchInput"
      />
      <div v-if="searchSuggestions.length > 0" class="suggestions-dropdown">
        <div
          v-for="(suggestion, index) in searchSuggestions"
          :key="index"
          class="suggestion-item"
          @click="selectSuggestion(suggestion)"
        >
          {{ suggestion.description }}
        </div>
      </div>
    </div>

    <GoogleMap
      ref="map"
      :center="mapCenter"
      :zoom="zoom"
      class="location-map"
      @map-loaded="onMapLoaded"
    >
      <template #default="{ map }">
        <div v-if="map">
          <!-- Marker for selected location -->
          <GoogleMapMarker
            v-if="marker"
            :map="map"
            :position="marker.position"
            :draggable="true"
            @drag-end="onMarkerDragEnd"
          />
        </div>
      </template>
    </GoogleMap>

    <div v-if="selectedLocation" class="selected-location">
      <h4>Selected Location:</h4>
      <p>{{ selectedLocation.address }}</p>
      <p>Lat: {{ selectedLocation.lat.toFixed(6) }}, Lng: {{ selectedLocation.lng.toFixed(6) }}</p>
    </div>
  </div>
</template>

<script>
import GoogleMap from './GoogleMap.vue';
import GoogleMapMarker from './GoogleMapMarker.vue';

export default {
  name: 'LocationPicker',
  components: {
    GoogleMap,
    GoogleMapMarker
  },
  props: {
    initialLocation: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      map: null,
      autocomplete: null,
      searchQuery: '',
      searchSuggestions: [],
      selectedLocation: null,
      marker: null,
      mapCenter: { lat: 40.7128, lng: -74.0060 }, // Default to NYC
      zoom: 12,
      placesService: null,
      geocoder: null
    };
  },
  mounted() {
    this.initializeAutocomplete();
    if (this.initialLocation) {
      this.selectedLocation = this.initialLocation;
      this.searchQuery = this.initialLocation.address;
      this.mapCenter = {
        lat: this.initialLocation.lat,
        lng: this.initialLocation.lng
      };
    }
  },
  methods: {
    initializeAutocomplete() {
      if (window.google) {
        this.setupAutocomplete();
      } else {
        // Wait for Google Maps to load
        setTimeout(() => {
          this.setupAutocomplete();
        }, 1000);
      }
    },
    setupAutocomplete() {
      if (!this.$refs.searchInput) return;

      this.autocomplete = new google.maps.places.Autocomplete(this.$refs.searchInput, {
        types: ['address', 'establishment'],
        componentRestrictions: { country: 'us' }
      });

      this.autocomplete.addListener('place_changed', this.onPlaceChanged);
    },
    onMapLoaded(map) {
      this.map = map;
      this.placesService = new google.maps.places.PlacesService(map);
      this.geocoder = new google.maps.Geocoder();
      
      // Add click listener to map
      map.addListener('click', (event) => {
        this.setLocationFromLatLng(event.latLng);
      });
    },
    onPlaceChanged() {
      const place = this.autocomplete.getPlace();
      if (!place.geometry) return;

      this.selectPlace(place);
    },
    selectPlace(place) {
      const location = {
        lat: place.geometry.location.lat(),
        lng: place.geometry.location.lng(),
        address: place.formatted_address,
        placeId: place.place_id
      };

      this.setSelectedLocation(location);
      this.searchSuggestions = [];
    },
    onSearchInput() {
      if (this.searchQuery.length < 3) {
        this.searchSuggestions = [];
        return;
      }

      this.getPlacePredictions();
    },
    getPlacePredictions() {
      if (!this.placesService) return;

      const request = {
        input: this.searchQuery,
        types: ['address', 'establishment']
      };

      this.placesService.getPlacePredictions(request, (predictions, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK && predictions) {
          this.searchSuggestions = predictions;
        }
      });
    },
    selectSuggestion(suggestion) {
      const request = {
        placeId: suggestion.place_id
      };

      this.placesService.getDetails(request, (place, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
          this.selectPlace(place);
          this.searchQuery = suggestion.description;
        }
      });
    },
    onMarkerDragEnd(event) {
      this.setLocationFromLatLng(event.latLng);
    },
    setLocationFromLatLng(latLng) {
      this.geocoder.geocode({ location: latLng }, (results, status) => {
        if (status === 'OK' && results[0]) {
          const location = {
            lat: latLng.lat(),
            lng: latLng.lng(),
            address: results[0].formatted_address,
            placeId: results[0].place_id
          };
          this.setSelectedLocation(location);
        }
      });
    },
    setSelectedLocation(location) {
      this.selectedLocation = location;
      this.mapCenter = { lat: location.lat, lng: location.lng };
      
      // Update marker
      this.marker = {
        position: { lat: location.lat, lng: location.lng }
      };

      this.$emit('location-selected', location);
    },
    clearLocation() {
      this.selectedLocation = null;
      this.marker = null;
      this.searchQuery = '';
      this.$emit('location-cleared');
    }
  }
};
</script>

<style scoped>
.location-picker {
  width: 100%;
  height: 400px;
}

.search-container {
  position: relative;
  margin-bottom: 1rem;
}

.location-search-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
}

.suggestions-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  max-height: 200px;
  overflow-y: auto;
  z-index: 1000;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.suggestion-item {
  padding: 0.75rem;
  cursor: pointer;
  border-bottom: 1px solid #f0f0f0;
}

.suggestion-item:hover {
  background: #f8f9fa;
}

.suggestion-item:last-child {
  border-bottom: none;
}

.location-map {
  height: 300px;
  margin-bottom: 1rem;
}

.selected-location {
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}

.selected-location h4 {
  margin: 0 0 0.5rem 0;
  color: #333;
}

.selected-location p {
  margin: 0.25rem 0;
  color: #666;
  font-size: 0.9rem;
}
</style>
