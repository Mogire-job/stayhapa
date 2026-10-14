import { expect, test } from '@playwright/test';
import {
  createAccount,
  createProperty,
  createReservation,
  dateOffset,
  loginThroughUi
} from './support.js';

test('guest confirms an available stay, sees private details, and overlapping dates are rejected', async ({ page, request }) => {
  const host = await createAccount(request, 'HOST');
  const guest = await createAccount(request, 'GUEST');
  const secondGuest = await createAccount(request, 'GUEST');
  const property = await createProperty(request, host);
  const checkIn = dateOffset(12);
  const checkOut = dateOffset(14);

  const publicListing = await request.get(`http://localhost:3000/api/properties/${property.id}`);
  expect(publicListing.ok()).toBeTruthy();
  expect((await publicListing.json()).address).toBeFalsy();

  await loginThroughUi(page, guest, 'GUEST');
  await page.goto(`/property/${property.id}`);
  await expect(page.getByRole('heading', { name: property.title })).toBeVisible();
  await expect(page.getByText('42 Test Street, Nairobi')).toHaveCount(0);
  await page.locator('input[type="date"]').nth(0).fill(checkIn);
  await page.locator('input[type="date"]').nth(1).fill(checkOut);
  await page.locator('input[type="number"]').fill('2');
  await page.getByRole('button', { name: 'Reserve stay' }).click();

  await expect(page.getByRole('heading', { name: 'Reservation confirmed' })).toBeVisible();
  await expect(page.getByText('42 Test Street, Nairobi')).toBeVisible();
  await expect(page.getByText(/KSh 25,000/)).toBeVisible();
  await page.getByRole('link', { name: 'View my reservations' }).click();
  await expect(page.getByRole('heading', { name: 'Your reservations' })).toBeVisible();
  await expect(page.getByText(property.title)).toBeVisible();
  await expect(page.getByText('42 Test Street, Nairobi')).toBeVisible();

  const conflict = await createReservation(request, property.id, secondGuest, dateOffset(13), dateOffset(15));
  expect(conflict.status()).toBe(409);
});
