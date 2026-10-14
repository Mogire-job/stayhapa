import { apiRequest } from '@/api';

const getAuthHeaders = () => {
  const token = localStorage.getItem('stayhapa_token');
  return token ? { Authorization: `Bearer ${token}` } : {};
};

export default {
  createBooking(propertyId, booking) {
    return apiRequest(`/bookings/properties/${propertyId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...getAuthHeaders()
      },
      body: JSON.stringify(booking)
    });
  },

  getGuestBookings() {
    return apiRequest('/bookings/guest', {
      headers: getAuthHeaders()
    });
  },

  getHostBookings() {
    return apiRequest('/bookings/host', {
      headers: getAuthHeaders()
    });
  },

  cancelBooking(bookingId) {
    return apiRequest(`/bookings/${bookingId}/cancel`, {
      method: 'PUT',
      headers: getAuthHeaders()
    });
  }
};
