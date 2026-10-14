import { defineConfig, devices } from '@playwright/test';

const baseURL = 'http://localhost:5173';

export default defineConfig({
  testDir: './tests/e2e',
  fullyParallel: false,
  workers: 1,
  retries: 0,
  reporter: 'list',
  use: {
    baseURL,
    ...devices['Desktop Chrome'],
    headless: true
  },
  webServer: [
    {
      command: 'npm run dev -- --host 127.0.0.1',
      url: baseURL,
      reuseExistingServer: !process.env.CI,
      timeout: 120_000
    },
    {
      command: 'mvn -f ..\\stayhapa-backend\\pom.xml spring-boot:run',
      url: 'http://localhost:3000/api/properties',
      reuseExistingServer: !process.env.CI,
      timeout: 120_000
    }
  ]
});
