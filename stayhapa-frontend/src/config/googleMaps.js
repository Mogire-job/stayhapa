export const googleMapsConfig = {
  apiKey: process.env.VUE_APP_GOOGLE_MAPS_API_KEY || 'YOUR_GOOGLE_MAPS_API_KEY',
  libraries: ['places'],
  language: 'en',
  region: 'US'
};

export const mapOptions = {
  zoom: 12,
  center: { lat: 0, lng: 0 },
  styles: [
    {
      featureType: 'poi',
      elementType: 'labels',
      stylers: [{ visibility: 'on' }]
    }
  ],
  mapTypeControl: false,
  streetViewControl: false,
  fullscreenControl: true
};