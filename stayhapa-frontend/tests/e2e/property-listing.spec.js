import { expect, test } from '@playwright/test';
import { createAccount, loginThroughUi } from './support.js';

test('host can publish a manually located listing without overlapping layout or legacy Places warnings', async ({ page, request }) => {
  const host = await createAccount(request, 'HOST');
  const consoleMessages = [];
  page.on('console', message => consoleMessages.push(message.text()));

  await loginThroughUi(page, host, 'HOST');
  await page.setViewportSize({ width: 390, height: 844 });
  await page.goto('/host/add-property');
  await expect(page.getByRole('heading', { name: 'List Your Property' })).toBeVisible();

  const sections = page.locator('.form-section');
  await sections.nth(0).locator('input[type="text"]').fill('Playwright Garden Cottage');
  await sections.nth(0).locator('textarea').fill('A quiet, comfortable place to stay near the city.');
  await sections.nth(0).locator('select').selectOption('COTTAGE');
  await sections.nth(0).locator('input[type="number"]').fill('14500');

  const locationSection = sections.nth(1);
  await locationSection.getByPlaceholder('Full address for display').fill('12 Riverside Drive');
  await locationSection.getByPlaceholder('City').fill('Nairobi');
  await locationSection.getByPlaceholder('Country').fill('Kenya');

  const detailsSection = sections.nth(2);
  await detailsSection.locator('input[type="number"]').nth(0).fill('2');
  await detailsSection.locator('input[type="number"]').nth(1).fill('1');
  await detailsSection.locator('input[type="number"]').nth(2).fill('2');
  await detailsSection.locator('input[type="number"]').nth(3).fill('4');

  const documentFits = await page.evaluate(
    () => document.documentElement.scrollWidth <= document.documentElement.clientWidth
  );
  expect(documentFits, 'listing form should not cause horizontal overflow on a phone viewport').toBeTruthy();
  expect(await page.getByRole('alert').count()).toBe(0);
  await expect(page.getByRole('button', { name: 'Publish Property' })).toBeEnabled();

  await page.getByRole('button', { name: 'Publish Property' }).click();
  await page.waitForURL('**/host/dashboard');
  await expect(page.getByText('Playwright Garden Cottage')).toBeVisible();
  await page.goto('/host/properties');
  await expect(page.locator('.property-management-card .stat-value').first()).toHaveText('KES 14,500');

  const savedListings = await request.get('http://localhost:3000/api/properties/my-properties', {
    headers: { Authorization: `Bearer ${host.token}` }
  });
  expect(savedListings.ok()).toBeTruthy();
  expect((await savedListings.json()).some(property =>
    property.title === 'Playwright Garden Cottage' && property.city === 'Nairobi'
  )).toBeTruthy();

  const legacyPlacesWarnings = consoleMessages.filter(message =>
    /PlacesService|places\.Autocomplete|not available to new customers/i.test(message)
  );
  expect(legacyPlacesWarnings).toEqual([]);
});
