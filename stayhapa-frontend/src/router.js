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
import HostDashboard from './views/HostDashboard.vue'  // This is in views/ not layouts/
import AddProperty from './views/layouts/AddProperty.vue'

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
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router