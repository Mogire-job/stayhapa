import { apiRequest } from './api.js';

export async function getUsers() {
  return apiRequest('/users');
}

export async function createUser(data) {
  return apiRequest('/users', {
    method: 'POST',
    body: JSON.stringify(data),
  });
}
