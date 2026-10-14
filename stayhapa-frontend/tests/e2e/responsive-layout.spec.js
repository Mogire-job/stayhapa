import { expect, test } from '@playwright/test';
import { createAccount, loginThroughUi } from './support.js';

async function expectNoHorizontalOverflow(page, path) {
  await page.goto(path);
  await page.locator('#app').waitFor();
  const dimensions = await page.evaluate(() => ({
    viewport: document.documentElement.clientWidth,
    content: document.documentElement.scrollWidth
  }));

  expect(
    dimensions.content,
    `${path} should fit the ${dimensions.viewport}px viewport without horizontal overflow`
  ).toBeLessThanOrEqual(dimensions.viewport);
}

test('public, guest, and host screens fit a narrow mobile viewport', async ({ page, request }) => {
  await page.setViewportSize({ width: 360, height: 780 });

  for (const path of ['/', '/listings', '/login', '/signup']) {
    await expectNoHorizontalOverflow(page, path);
  }

  const host = await createAccount(request, 'HOST');
  await loginThroughUi(page, host, 'HOST');
  for (const path of ['/host/dashboard', '/host/add-property', '/host/bookings', '/host/properties']) {
    await expectNoHorizontalOverflow(page, path);
  }

  const guest = await createAccount(request, 'GUEST');
  await loginThroughUi(page, guest, 'GUEST');
  for (const path of ['/guest/dashboard', '/bookings', '/guest/profile']) {
    await expectNoHorizontalOverflow(page, path);
  }
});
