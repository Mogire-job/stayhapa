export const googleMapsConfig = {
      apiKey: 'AIzaSyCA6A-dFKOn3hAG9fiqboTW6ABMJooINOg',
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