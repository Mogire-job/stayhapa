# Runtime Validation Report

**Generated:** 2026-10-14  
**Target:** StayHapa Vue frontend and Spring Boot backend

## Summary

| Step | Status | Exit code | Details |
|---|---|---:|---|
| Startup | PASS | 0 | Playwright started or attached to Vite and Spring Boot; both readiness URLs responded. |
| Backend integration tests | PASS | 0 | `mvn test`: 4 tests, 4 passed, 0 failed, 0 skipped, including image data longer than the default VARCHAR limit. |
| Frontend production build | PASS | 0 | `npm run build` completed successfully. |
| Browser E2E | PASS | 0 | Playwright: 6 tests, 6 passed, 0 failed, including property publishing, authorization errors, and 360px responsive checks. |
| Large-image API round trip | PASS | 0 | Created a property with a 143,551-character image data URL; API returned HTTP 201 and the stored image length matched. |

**Overall:** PASS

## Critical Journeys

| Journey | Evidence |
|---|---|
| Guest account signup and role-restricted dashboard access | `tests/e2e/account-roles.spec.js` |
| Host publishes a listing with a manually entered address; deprecated Places calls are absent | `tests/e2e/property-listing.spec.js` |
| Host listing persists image data larger than the database's default VARCHAR limit | `src/test/java/com/stayhapa/service/PropertyImagePersistenceIntegrationTest.java` |
| Invalid/stale bearer token returns 401; a guest trying host-only listing operations receives a clear 403 | `tests/e2e/property-auth.spec.js` |
| Guest reservation, public-address privacy, persisted booking, and overlapping-date rejection | `tests/e2e/guest-reservation.spec.js` |
| Host reservation management and cancellation | `tests/e2e/host-reservation.spec.js` |
| Public, guest, and host views fit a 360px mobile viewport | `tests/e2e/responsive-layout.spec.js` |
| Automatic confirmation, server-calculated total, overlap conflict, and capacity rejection | `src/test/java/com/stayhapa/service/BookingServiceIntegrationTest.java` |

## Environment and Coverage

- Docker daemon: unavailable; the app's embedded H2 database was used.
- Node.js: v20.19.0.
- Playwright: v1.64.0; Chromium installed and used in headless mode.
- Browser-tier status: primary Playwright path used; no browser-rendering fallback.
- Infra-tier status: embedded H2; production-database fidelity was not tested.
- Payment processing is not implemented or exercised.
- The responsive flow found horizontal overflow in the host properties navigation; the mobile navigation was updated and all viewport checks now pass.
- The browser's saved profile said `host`, but the backend rejected the bearer token. The browser and server were out of sync; with the in-memory database, restarting the backend clears accounts and makes their previously issued tokens stale.

## Issues Found and Resolved

- The bookings view passed a `Date` object into a formatter expecting an ISO date string. This prevented both guest and host bookings pages from mounting. A shared local-date key helper fixed the page initialization and related UTC/local date handling.
- Property location search now uses Google's `PlaceAutocompleteElement` and `gmp-select` / `Place.fetchFields()` flow instead of the legacy `Autocomplete` and `PlacesService` APIs that caused the browser console warnings.
- Invalid or stale tokens now receive a JSON `401` instead of being silently ignored by the JWT filter; authorized users with the wrong role receive a clear JSON `403`. The property form gives recovery links to host login and registration.
- The property form displayed the same submission error twice and the location picker used a fixed height that could let its content collide with the following form fields. Both were corrected; API errors now show the server's message.
