import { randomUUID } from 'node:crypto';

export const API_URL = 'http://localhost:3000/api';
export const TEST_PASSWORD = 'Stayhapa-E2E-Password-42';

export function uniqueEmail(role) {
  return `${role}-${randomUUID()}@example.test`;
}

export async function createAccount(request, role, email = uniqueEmail(role.toLowerCase())) {
  const response = await request.post(`${API_URL}/auth/register`, {
    data: {
      firstName: 'Stayhapa',
      lastName: role === 'HOST' ? 'Host' : 'Guest',
      email,
      password: TEST_PASSWORD,
      role
    }
  });

  if (!response.ok()) {
    throw new Error(`Could not register E2E ${role.toLowerCase()}: ${response.status()} ${await response.text()}`);
  }

  return { ...(await response.json()), email, password: TEST_PASSWORD };
}

export async function createProperty(request, host, title = `E2E Stay ${randomUUID()}`, coordinates = {}) {
  const response = await request.post(`${API_URL}/properties`, {
    headers: { Authorization: `Bearer ${host.token}` },
    data: {
      title,
      description: 'A comfortable test stay for a browser reservation flow.',
      address: '42 Test Street, Nairobi',
      city: 'Nairobi',
      country: 'Kenya',
      latitude: coordinates.latitude,
      longitude: coordinates.longitude,
      pricePerNight: 12500,
      bedrooms: 2,
      beds: 2,
      bathrooms: 1,
      maxGuests: 4,
      propertyType: 'APARTMENT',
      amenities: ['WiFi'],
      images: []
    }
  });

  if (!response.ok()) {
    throw new Error(`Could not create E2E listing: ${response.status()} ${await response.text()}`);
  }

  return response.json();
}

export async function createReservation(request, propertyId, guest, checkInDate, checkOutDate, guests = 2) {
  return request.post(`${API_URL}/bookings/properties/${propertyId}`, {
    headers: { Authorization: `Bearer ${guest.token}` },
    data: { checkInDate, checkOutDate, numberOfGuests: guests }
  });
}

export async function loginThroughUi(page, account, role) {
  await page.goto('/login');
  if (role === 'HOST') {
    await page.getByRole('button', { name: "I'm a Host" }).click();
  }
  await page.locator('input[type="email"]').fill(account.email);
  await page.locator('input[type="password"]').fill(account.password);
  await page.locator('button[type="submit"]').click();
  await page.waitForURL(role === 'HOST' ? '**/host/dashboard' : '**/guest/dashboard');
}

export function dateOffset(days) {
  const date = new Date();
  date.setDate(date.getDate() + days);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}
