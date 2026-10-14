import { expect, test } from '@playwright/test';

test('Show Map renders listing pins from the public map coordinates', async ({ page }) => {
  await page.route('**/api/properties', route => route.fulfill({
    status: 200,
    contentType: 'application/json',
    body: JSON.stringify([{
      id: 101,
      title: 'Map View Test Stay',
      city: 'Nairobi',
      country: 'Kenya',
      pricePerNight: 12500,
      mapLatitude: -1.29,
      mapLongitude: 36.82,
      latitude: null,
      longitude: null,
      active: true,
      propertyType: 'APARTMENT',
      amenities: [],
      images: []
    }])
  }));

  await page.goto('/listings');
  await expect(page.getByText('Map View Test Stay')).toBeVisible();
  await page.getByRole('button', { name: 'Show Map' }).click();

  await expect(page.locator('.listings-google-map .gm-style')).toBeVisible();
  await expect(page.getByRole('status')).toContainText('approximate locations');
  await expect(page.locator('.listings-google-map [title="Map View Test Stay"]')).toBeVisible();
});
