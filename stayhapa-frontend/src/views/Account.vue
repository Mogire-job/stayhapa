<template>
  <div class="relative w-screen min-h-screen bg-gray-50 overflow-hidden">
    <!-- Top Navigation -->
    <nav class="absolute top-0 left-0 w-full flex justify-between items-center px-6 py-4 z-20 bg-gray-50">
      <!-- Left side: Login / Sign Up -->
      <div class="flex items-center space-x-4">
        <router-link
          to="/login"
          class="text-gray-800 font-semibold hover:text-red-600"
        >Login</router-link>
        <router-link
          to="/signup"
          class="text-gray-800 font-semibold hover:text-red-600"
        >Sign Up</router-link>
      </div>

      <!-- Right side: Collapsible Menu -->
      <div class="relative">
        <button
          @click="toggleMenu"
          class="text-gray-800 text-3xl focus:outline-none"
        >
          ☰
        </button>
        <div
          v-if="menuOpen"
          class="absolute top-12 right-0 bg-white rounded-lg shadow-lg p-4 space-y-3 w-40"
        >
          <router-link to="/" class="block hover:text-red-600 font-semibold" @click="closeMenu">Home</router-link>
          <router-link to="/about" class="block hover:text-red-600 font-semibold" @click="closeMenu">About</router-link>
          <router-link to="/contact" class="block hover:text-red-600 font-semibold" @click="closeMenu">Contact</router-link>
          <router-link to="/listings" class="block hover:text-red-600 font-semibold" @click="closeMenu">Listings</router-link>
          <router-link to="/bookings" class="block hover:text-red-600 font-semibold" @click="closeMenu">Bookings</router-link>
          <router-link to="/dashboard" class="block hover:text-red-600 font-semibold" @click="closeMenu">Dashboard</router-link>
          <router-link to="/account" class="block hover:text-red-600 font-semibold" @click="closeMenu">Account</router-link>
        </div>
      </div>
    </nav>

    <!-- Contact Form -->
    <section class="flex flex-col justify-center items-center text-center h-screen px-4">
      <h1 class="text-5xl font-bold text-red-600 mb-6">Contact Us</h1>
      <form class="w-full max-w-lg bg-white p-8 rounded-lg shadow-lg space-y-4">
        <div>
          <label class="block text-gray-700 font-semibold mb-2">Name</label>
          <input type="text" class="w-full border border-gray-300 rounded px-3 py-2" placeholder="Your Name"/>
        </div>
        <div>
          <label class="block text-gray-700 font-semibold mb-2">Email</label>
          <input type="email" class="w-full border border-gray-300 rounded px-3 py-2" placeholder="Your Email"/>
        </div>
        <div>
          <label class="block text-gray-700 font-semibold mb-2">Subject</label>
          <input type="text" class="w-full border border-gray-300 rounded px-3 py-2" placeholder="Subject"/>
        </div>
        <div>
          <label class="block text-gray-700 font-semibold mb-2">Message</label>
          <textarea class="w-full border border-gray-300 rounded px-3 py-2" rows="5" placeholder="Your Message"></textarea>
        </div>
        <button type="submit" class="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-2 rounded-lg transition-all">Send Message</button>
      </form>
    </section>
  </div>
</template>

<script>
export default {
  name: "Contact",
  data() {
    return {
      menuOpen: false,
    };
  },
  methods: {
    toggleMenu() {
      this.menuOpen = !this.menuOpen;
    },
    closeMenu() {
      this.menuOpen = false;
    },
  },
};
</script>

<style scoped>
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
}
</style>
