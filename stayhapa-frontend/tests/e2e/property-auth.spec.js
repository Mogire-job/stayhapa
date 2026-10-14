import { expect, test } from '@playwright/test';
import { createAccount, createProperty } from './support.js';

test('property API distinguishes stale sessions from accounts without host access', async ({ request }) => {
  const host = await createAccount(request, 'HOST');
  const guest = await createAccount(request, 'GUEST');
  const property = await createProperty(request, host);
  const payload = {
    title: `${property.title} Updated`,
    description: property.description,
    address: property.address,
    city: property.city,
    country: property.country,
    pricePerNight: property.pricePerNight,
    bedrooms: property.bedrooms,
    beds: property.beds,
    bathrooms: property.bathrooms,
    maxGuests: property.maxGuests,
    propertyType: property.propertyType,
    active: true,
    amenities: property.amenities,
    images: property.images
  };

  const staleSession = await request.post(`http://localhost:3000/api/properties`, {
    headers: { Authorization: 'Bearer invalid-or-stale-token' },
    data: payload
  });
  expect(staleSession.status()).toBe(401);
  expect((await staleSession.json()).error).toContain('session');

  const wrongRole = await request.post('http://localhost:3000/api/properties', {
    headers: { Authorization: `Bearer ${guest.token}` },
    data: payload
  });
  expect(wrongRole.status()).toBe(403);
  expect((await wrongRole.json()).error).toContain('host account');
});
