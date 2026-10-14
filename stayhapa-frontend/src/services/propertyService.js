const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api';

const getAuthHeaders = () => {
  const token = localStorage.getItem('stayhapa_token');
  return token ? { 'Authorization': `Bearer ${token}` } : {};
};

async function getErrorMessage(response, fallback) {
  const body = await response.text();
  let message = fallback;
  try {
    const error = JSON.parse(body);
    message = error.message || error.error || fallback;
  } catch {
    message = body || fallback;
  }

  const requestError = new Error(message);
  requestError.status = response.status;
  throw requestError;
}

export default {
  async createProperty(propertyData) {
    const response = await fetch(`${API_BASE_URL}/properties`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...getAuthHeaders()
      },
      body: JSON.stringify(propertyData)
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to create property'));
    }
    
    return response.json();
  },

  async getMyProperties() {
    const response = await fetch(`${API_BASE_URL}/properties/my-properties`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        ...getAuthHeaders()
      }
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to fetch properties'));
    }
    
    return response.json();
  },

  async getAllProperties() {
    const response = await fetch(`${API_BASE_URL}/properties`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to fetch properties'));
    }
    
    return response.json();
  },

  async getPropertyById(id) {
    const response = await fetch(`${API_BASE_URL}/properties/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to fetch property'));
    }
    
    return response.json();
  },

  async updateProperty(id, propertyData) {
    const response = await fetch(`${API_BASE_URL}/properties/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        ...getAuthHeaders()
      },
      body: JSON.stringify(propertyData)
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to update property'));
    }
    
    return response.json();
  },

  async deleteProperty(id) {
    const response = await fetch(`${API_BASE_URL}/properties/${id}`, {
      method: 'DELETE',
      headers: {
        ...getAuthHeaders()
      }
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to delete property'));
    }
  },

  async searchProperties(params = {}) {
    const queryParams = new URLSearchParams();
    if (params.city) queryParams.append('city', params.city);
    if (params.minPrice) queryParams.append('minPrice', params.minPrice);
    if (params.maxPrice) queryParams.append('maxPrice', params.maxPrice);
    if (params.maxGuests) queryParams.append('maxGuests', params.maxGuests);
    
    const response = await fetch(`${API_BASE_URL}/properties/search?${queryParams}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    if (!response.ok) {
      throw new Error(await getErrorMessage(response, 'Failed to search properties'));
    }
    
    return response.json();
  }
};
