import { createRouter, createWebHistory } from 'vue-router'

// Import files from their actual locations
import Home from './views/Home.vue'
import Listings from './views/Listings.vue'
import PropertyDetails from './views/PropertyDetails.vue'
import Bookings from './views/Bookings.vue'
import Dashboard from './views/Dashboard.vue'
import Account from './views/Account.vue'
import Login from './views/Login.vue'
import SignUp from './views/SignUp.vue'
import GetStartedPage from './views/layouts/GetStartedPage.vue'
import HostDashboard from './views/HostDashboard.vue'
import AddProperty from './views/layouts/AddProperty.vue'
import GuestDashboard from './views/GuestDashboard.vue'
import EmailVerification from './components/Auth/EmailVerification.vue'
import HostGuide from './views/HostGuide.vue'
import HelpCenter from './views/HelpCenter.vue'
import Safety from './views/Safety.vue'
import ContactUs from './views/ContactUs.vue'
import GuestProfile from './views/GuestProfile.vue'
import HostProfile from './views/HostProfile.vue'
import ForgotPassword from './views/ForgotPassword.vue'
import ResetPassword from './views/ResetPassword.vue'

// ListingsManagement is in components/Host/ so we need to import it differently
const ListingsManagement = () => import('./components/Host/ListingsManagement.vue')

const routes = [
  { path: '/', component: Home },
  { path: '/listings', component: Listings },
  { path: '/property/:id', component: PropertyDetails },
  { path: '/bookings', component: Bookings, meta: { requiresAuth: true, role: 'guest' } },
  { path: '/host/bookings', component: Bookings, meta: { requiresAuth: true, role: 'host' } },
  { path: '/dashboard', component: Dashboard, meta: { requiresAuth: true } },
  { path: '/account', component: Account, meta: { requiresAuth: true } },
  { path: '/login', component: Login },
  { path: '/signup', component: SignUp },
  { path: '/get-started', component: GetStartedPage },
  { path: '/host/dashboard', component: HostDashboard, meta: { requiresAuth: true, role: 'host' } },
  { path: '/host/add-property', component: AddProperty, meta: { requiresAuth: true, role: 'host' } },
  { path: '/host/edit-property/:id', component: AddProperty, meta: { requiresAuth: true, role: 'host' } },
  { path: '/host/properties', component: ListingsManagement, meta: { requiresAuth: true, role: 'host' } },
  { path: '/host/profile', component: HostProfile, meta: { requiresAuth: true, role: 'host' } },
  { path: '/guest/dashboard', component: GuestDashboard, meta: { requiresAuth: true, role: 'guest' } },
  { path: '/guest/profile', component: GuestProfile, meta: { requiresAuth: true, role: 'guest' } },
  { path: '/verify-account', component: EmailVerification },
  { path: '/host-guide', component: HostGuide },
  { path: '/help', component: HelpCenter },
  { path: '/safety', component: Safety },
  { path: '/contact', component: ContactUs },
  { path: '/forgot-password', component: ForgotPassword },
  { path: '/reset-password', component: ResetPassword },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// Add navigation guard to check authentication and verification
router.beforeEach((to) => {
  let currentUser;
  try {
    currentUser = JSON.parse(localStorage.getItem('stayhapa_current_user') || 'null');
  } catch (error) {
    console.error('Could not read the saved account session:', error);
    localStorage.removeItem('stayhapa_current_user');
    localStorage.removeItem('stayhapa_token');
    return '/login';
  }

  if (to.meta.requiresAuth && !currentUser) {
    return { path: '/login', query: { redirect: to.fullPath } };
  }

  if (to.meta.requiresAuth && currentUser && !currentUser.isVerified) {
    return '/verify-account';
  }

  if (to.meta.role && currentUser?.userType !== to.meta.role) {
    return currentUser?.userType === 'host' ? '/host/dashboard' : '/guest/dashboard';
  }

  return true;
})

export default router