import { importLibrary, setOptions } from '@googlemaps/js-api-loader';

export const googleMapsConfig = {
  apiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY || '',
  libraries: ['places'],
  language: 'en',
  region: 'KE'
};

let librariesPromise;

export function loadGoogleMaps() {
  const apiKey = googleMapsConfig.apiKey;
  if (!apiKey || apiKey === 'YOUR_GOOGLE_MAPS_API_KEY') {
    return Promise.reject(new Error('Google Maps API key is not configured.'));
  }

  if (!librariesPromise) {
    setOptions({ key: apiKey, v: 'weekly' });
    librariesPromise = Promise.all([
      importLibrary('maps'),
      importLibrary('places')
    ]).catch((error) => {
      librariesPromise = undefined;
      throw error;
    });
  }

  return librariesPromise;
}

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