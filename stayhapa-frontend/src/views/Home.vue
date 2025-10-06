<template>
  <div
    class="relative w-screen h-screen bg-cover bg-center bg-no-repeat overflow-hidden"
    style="background-image: url('/src/assets/hero.jpeg');"
  >
    <!-- Dark Overlay -->
    <div class="absolute inset-0 bg-black bg-opacity-50"></div>

    <!-- Top Navigation -->
    <nav class="absolute top-0 left-0 w-full flex justify-between items-center px-6 py-4 z-20">
      <!-- Left side: Login / Sign Up -->
      <div class="flex items-center space-x-4">
        <router-link
          to="/login"
          class="text-white font-semibold hover:text-blue-400"
        >Login</router-link>
        <router-link
          to="/signup"
          class="text-white font-semibold hover:text-blue-400"
        >Sign Up</router-link>
      </div>

      <!-- Right side: Collapsible Menu Button -->
      <div class="relative">
        <button
          @click="toggleMenu"
          class="text-white text-3xl focus:outline-none"
        >
          ☰
        </button>

        <!-- Menu Items -->
        <div
          v-if="menuOpen"
          class="absolute top-12 right-0 bg-white bg-opacity-90 text-black rounded-lg shadow-lg p-4 space-y-3 w-40"
        >
          <router-link
            to="/"
            class="block hover:text-blue-600 font-semibold"
            @click="closeMenu"
          >Home</router-link>
          <router-link
            to="/bookings"
            class="block hover:text-blue-600 font-semibold"
            @click="closeMenu"
          >Bookings</router-link>
          <router-link
            to="/listings"
            class="block hover:text-blue-600 font-semibold"
            @click="closeMenu"
          >Listings</router-link>
          <router-link
            to="/dashboard"
            class="block hover:text-blue-600 font-semibold"
            @click="closeMenu"
          >Dashboard</router-link>
          <router-link
            to="/account"
            class="block hover:text-blue-600 font-semibold"
            @click="closeMenu"
          >Account</router-link>
        </div>
      </div>
    </nav>

    <!-- Hero Section Content -->
    <div
      class="relative z-10 flex flex-col justify-center items-center text-center text-white h-full px-4"
    >
      <h1 class="text-5xl md:text-6xl font-bold text-red-500 mb-6">
        StayHapa
      </h1>
      <p class="text-lg md:text-xl mb-10 max-w-2xl text-gray-100">
        Discover cozy homes, apartments, and spaces designed for your comfort and convenience.
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
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
html,
body,
#app {
  margin: 0;
  padding: 0;
  height: 100%;
}

.bg-cover {
  background-size: cover !important;
}

.bg-center {
  background-position: center !important;
}

.bg-no-repeat {
  background-repeat: no-repeat !important;
}

.w-screen {
  width: 100vw;
}

.h-screen {
  height: 100vh;
}

.overflow-hidden {
  overflow: hidden;
}
</style>
