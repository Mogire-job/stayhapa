import { expect, test } from '@playwright/test';
import { uniqueEmail, TEST_PASSWORD } from './support.js';

test('guest can sign up, reach the guest dashboard, and cannot enter the host dashboard', async ({ page, request }) => {
  const email = uniqueEmail('guest');
  await page.goto('/signup');
  await page.locator('input[placeholder="Enter your full name"]').fill('Browser Guest');
  await page.locator('input[placeholder="your@email.com"]').fill(email);
  await page.locator('input[placeholder="Create a password (min 6 chars)"]').fill(TEST_PASSWORD);
  await page.locator('input[placeholder="Confirm your password"]').fill(TEST_PASSWORD);
  await page.getByRole('button', { name: 'Create Account' }).click();

  await page.waitForURL('**/guest/dashboard');
  await expect(page).toHaveURL(/\/guest\/dashboard$/);
  await page.goto('/host/dashboard');
  await expect(page).toHaveURL(/\/guest\/dashboard$/);

  const login = await request.post('http://localhost:3000/api/auth/login', {
    data: { email, password: TEST_PASSWORD }
  });
  expect(login.ok()).toBeTruthy();
  expect((await login.json()).role).toBe('GUEST');
});
