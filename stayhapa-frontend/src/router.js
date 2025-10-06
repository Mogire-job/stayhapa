import { createRouter, createWebHistory } from 'vue-router'

import Home from './views/Home.vue'
import Listings from './views/Listings.vue'
import Bookings from './views/Bookings.vue'
import Dashboard from './views/Dashboard.vue'
import Account from './views/Account.vue'
import Login from './views/Login.vue'
import SignUp from './views/SignUp.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/listings', component: Listings },
  { path: '/bookings', component: Bookings },
  { path: '/dashboard', component: Dashboard },
  { path: '/account', component: Account },
  { path: '/login', component: Login },
  { path: '/signup', component: SignUp },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
