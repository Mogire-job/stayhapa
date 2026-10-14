const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api';

export async function apiRequest(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;
  const config = {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  };

  try {
    const response = await fetch(url, config);
    if (!response.ok) {
      const body = await response.text();
      let message = body;
      try {
        const error = JSON.parse(body);
        message = error.message || error.error || body;
      } catch {
        // Keep the response body when the server does not return JSON.
      }
      throw new Error(message || `Request failed with status ${response.status}.`);
    }
    return response.json();
  } catch (err) {
    console.error('API request failed:', err);
    throw err;
  }
}
