<template>
  <div class="location-picker">
    <div
      ref="autocompleteContainer"
      class="search-container"
      aria-label="Search for a property location"
    >
    </div>

    <GoogleMap
      ref="map"
      :center="mapCenter"
      :zoom="zoom"
      class="location-map"
      @map-loaded="onMapLoaded"
      @map-error="onMapError"
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

    <p v-if="mapError" class="map-error" role="status">{{ mapError }}</p>

    <div v-if="selectedLocation" class="selected-location">
      <h4>Selected Location:</h4>
      <p>{{ selectedLocation.address }}</p>
      <p>Lat: {{ selectedLocation.lat.toFixed(6) }}, Lng: {{ selectedLocation.lng.toFixed(6) }}</p>
    </div>
  </div>
</template>

<script>
import GoogleMap from './GoogleMaps.vue';
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
      autocompleteElement: null,
      selectedLocation: null,
      marker: null,
      mapCenter: { lat: -1.2864, lng: 36.8172 },
      zoom: 12,
      geocoder: null,
      mapError: ''
    };
  },
  mounted() {
    if (this.initialLocation) {
      this.selectedLocation = this.initialLocation;
      this.mapCenter = {
        lat: this.initialLocation.lat,
        lng: this.initialLocation.lng
      };
    }
  },
  methods: {
    onMapError(error) {
      this.mapError = error.message || 'Map unavailable. Enter your address manually.';
    },
    setupPlaceAutocomplete() {
      const PlaceAutocompleteElement = google.maps.places.PlaceAutocompleteElement;
      if (!this.$refs.autocompleteContainer || !PlaceAutocompleteElement) {
        throw new Error('Google Places search could not be initialized. Enter the address manually below.');
      }

      this.autocompleteElement = new PlaceAutocompleteElement();
      this.autocompleteElement.includedRegionCodes = ['ke'];
      this.autocompleteElement.placeholder = 'Search for an address or place in Kenya';
      this.autocompleteElement.addEventListener('gmp-select', this.onPlaceSelected);
      this.$refs.autocompleteContainer.replaceChildren(this.autocompleteElement);
    },
    onMapLoaded(map) {
      this.map = map;
      this.geocoder = new google.maps.Geocoder();
      try {
        this.setupPlaceAutocomplete();
        this.mapError = '';
      } catch (error) {
        console.error('Could not initialize Google Places search:', error);
        this.mapError = error.message;
      }
      
      // Add click listener to map
      map.addListener('click', (event) => {
        this.setLocationFromLatLng(event.latLng);
      });
    },
    async onPlaceSelected(event) {
      try {
        if (!event.placePrediction) {
          throw new Error('The selected result did not include place details.');
        }
        const place = event.placePrediction.toPlace();
        await place.fetchFields({
          fields: ['formattedAddress', 'location', 'id', 'addressComponents']
        });
        this.selectPlace(place);
      } catch (error) {
        console.error('Could not retrieve the selected place:', error);
        this.mapError = 'Could not retrieve that place. Enter the address manually below.';
      }
    },
    selectPlace(place) {
      if (!place.location) {
        this.mapError = 'That place does not include a map location. Try another result or enter the address manually.';
        return;
      }

      const location = {
        lat: place.location.lat(),
        lng: place.location.lng(),
        address: place.formattedAddress || '',
        placeId: place.id,
        addressComponents: place.addressComponents || []
      };

      this.setSelectedLocation(location);
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
            placeId: results[0].place_id,
            addressComponents: results[0].address_components || []
          };
          this.setSelectedLocation(location);
        } else {
          this.mapError = 'Could not look up this map location. Enter the address manually below.';
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
      this.autocompleteElement?.remove();
      if (this.autocompleteElement && this.$refs.autocompleteContainer) {
        this.$refs.autocompleteContainer.append(this.autocompleteElement);
      }
      this.$emit('location-cleared');
    }
  },
  watch: {
    initialLocation(location) {
      if (location) {
        this.selectedLocation = location;
        this.mapCenter = { lat: location.lat, lng: location.lng };
      }
    }
  },
  beforeUnmount() {
    this.autocompleteElement?.removeEventListener('gmp-select', this.onPlaceSelected);
    this.autocompleteElement?.remove();
  }
};
</script>

<style scoped>
.location-picker {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.search-container {
  width: 100%;
  min-height: 48px;
}

.search-container :deep(gmp-place-autocomplete) {
  width: 100%;
}

.location-map {
  height: 300px !important;
}

.map-error {
  margin: 0;
  color: #6d5547;
  font-size: 0.875rem;
  line-height: 1.5;
  overflow-wrap: anywhere;
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
  line-height: 1.5;
  overflow-wrap: anywhere;
}
</style>