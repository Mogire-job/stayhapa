import { expect, test } from '@playwright/test';
import {
  createAccount,
  createProperty,
  createReservation,
  dateOffset,
  loginThroughUi
} from './support.js';

test('host sees reservation details and can cancel a future reservation', async ({ page, request }) => {
  const host = await createAccount(request, 'HOST');
  const guest = await createAccount(request, 'GUEST');
  const property = await createProperty(request, host);
  const reservation = await createReservation(
    request,
    property.id,
    guest,
    dateOffset(24),
    dateOffset(26)
  );
  expect(reservation.ok()).toBeTruthy();
  const savedReservation = await reservation.json();

  await loginThroughUi(page, host, 'HOST');
  await page.goto('/host/bookings');
  await expect(page.getByRole('heading', { name: 'Reservations for your stays' })).toBeVisible();
  await expect(page.getByText(property.title)).toBeVisible();
  await expect(page.getByText(guest.email)).toBeVisible();
  await expect(page.getByText('42 Test Street, Nairobi')).toBeVisible();

  page.once('dialog', dialog => dialog.accept());
  await page.getByRole('button', { name: 'Cancel reservation' }).click();
  await expect(page.locator('.status-pill.cancelled')).toHaveText('Cancelled');

  const guestReservations = await request.get('http://localhost:3000/api/bookings/guest', {
    headers: { Authorization: `Bearer ${guest.token}` }
  });
  expect(guestReservations.ok()).toBeTruthy();
  const saved = (await guestReservations.json()).find(item => item.id === savedReservation.id);
  expect(saved.status).toBe('CANCELLED');
});
