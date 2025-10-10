import { createRouter, createWebHistory } from 'vue-router'

// Import files from their actual locations
import Home from './views/Home.vue'
import Listings from './views/Listings.vue'
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

// ListingsManagement is in components/Host/ so we need to import it differently
const ListingsManagement = () => import('./components/Host/ListingsManagement.vue')

const routes = [
  { path: '/', component: Home },
  { path: '/listings', component: Listings },
  { path: '/bookings', component: Bookings },
  { path: '/dashboard', component: Dashboard },
  { path: '/account', component: Account },
  { path: '/login', component: Login },
  { path: '/signup', component: SignUp },
  { path: '/get-started', component: GetStartedPage },
  { path: '/host/dashboard', component: HostDashboard },
  { path: '/host/add-property', component: AddProperty },
  { path: '/host/properties', component: ListingsManagement },
  { path: '/guest/dashboard', component: GuestDashboard },
  { path: '/verify-account', component: EmailVerification },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// Add navigation guard to check authentication and verification
router.beforeEach((to, from, next) => {
  const currentUser = JSON.parse(localStorage.getItem('stayhapa_current_user') || 'null');
  
  // Routes that require authentication
  const requiresAuth = [
    '/dashboard', '/account', '/bookings', 
    '/host/dashboard', '/host/add-property', '/host/properties',
    '/guest/dashboard'
  ];
  
  // Check if route requires authentication
  if (requiresAuth.includes(to.path) && !currentUser) {
    next('/login');
    return;
  }
  
  // Check verification status for authenticated routes (excluding verification page itself)
  if (currentUser && to.path !== '/verify-account' && requiresAuth.includes(to.path)) {
    if (!currentUser.isVerified) {
      next('/verify-account');
      return;
    }
  }
  
  next();
})

export default router