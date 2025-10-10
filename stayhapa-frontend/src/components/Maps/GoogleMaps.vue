<template>
  <div class="google-map-container">
    <div ref="map" class="google-map"></div>
    <div v-if="!isLoaded" class="map-loading">
      <div class="loading-spinner"></div>
      <p>Loading map...</p>
    </div>
    <slot :map="map" :isLoaded="isLoaded"></slot>
  </div>
</template>

<script>
import { Loader } from '@googlemaps/js-api-loader';
import { googleMapsConfig, mapOptions } from '@/config/googleMaps';

export default {
  name: 'GoogleMap',
  props: {
    center: {
      type: Object,
      default: () => ({ lat: 0, lng: 0 })
    },
    zoom: {
      type: Number,
      default: 12
    },
    options: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      map: null,
      isLoaded: false,
      loader: null
    };
  },
  mounted() {
    this.initializeMap();
  },
  methods: {
    async initializeMap() {
      try {
        this.loader = new Loader({
          apiKey: googleMapsConfig.apiKey,
          version: 'weekly',
          libraries: googleMapsConfig.libraries
        });

        await this.loader.load();

        const mapElement = this.$refs.map;
        this.map = new google.maps.Map(mapElement, {
          ...mapOptions,
          ...this.options,
          center: this.center,
          zoom: this.zoom
        });

        this.isLoaded = true;
        this.$emit('map-loaded', this.map);
      } catch (error) {
        console.error('Error loading Google Maps:', error);
        this.$emit('map-error', error);
      }
    }
  },
  watch: {
    center(newCenter) {
      if (this.map && newCenter.lat && newCenter.lng) {
        this.map.setCenter(newCenter);
      }
    },
    zoom(newZoom) {
      if (this.map) {
        this.map.setZoom(newZoom);
      }
    }
  }
};
</script>

<style scoped>
.google-map-container {
  position: relative;
  width: 100%;
  height: 100%;
}

.google-map {
  width: 100%;
  height: 100%;
  border-radius: 8px;
}

.map-loading {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
  border-radius: 8px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #e0e0e0;
  border-top: 4px solid #0F4C81;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>