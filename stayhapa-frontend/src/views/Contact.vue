<template>
  <div class="min-h-screen bg-gray-100 flex flex-col">
    <!-- Top Navigation -->
    <nav class="w-full flex justify-between items-center px-6 py-4 bg-white shadow-md sticky top-0 z-20">
      <router-link to="/" class="text-red-600 font-bold text-2xl">StayHapa</router-link>
      <div class="flex items-center space-x-4">
        <router-link to="/login" class="text-gray-700 font-semibold hover:text-red-600">Login</router-link>
        <router-link to="/signup" class="text-gray-700 font-semibold hover:text-red-600">Sign Up</router-link>
      </div>
    </nav>

    <!-- Contact Form -->
    <section class="flex-grow flex items-center justify-center py-16 px-4">
      <div class="bg-white rounded-lg shadow-lg max-w-3xl w-full p-8">
        <h2 class="text-3xl font-bold text-red-600 mb-6 text-center">Contact Us</h2>
        <form class="space-y-4">
          <div>
            <label class="block text-gray-700 font-semibold mb-1">Your Name</label>
            <input type="text" placeholder="John Doe" class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-red-500"/>
          </div>
          <div>
            <label class="block text-gray-700 font-semibold mb-1">Email</label>
            <input type="email" placeholder="john@example.com" class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-red-500"/>
          </div>
          <div>
            <label class="block text-gray-700 font-semibold mb-1">Subject</label>
            <input type="text" placeholder="Inquiry" class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-red-500"/>
          </div>
          <div>
            <label class="block text-gray-700 font-semibold mb-1">Message</label>
            <textarea placeholder="Your message..." rows="5" class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-red-500"></textarea>
          </div>
          <button type="submit" class="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-3 rounded-lg transition-all">
            Send Message
          </button>
        </form>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "Contact",
};
</script>

<style scoped>
/* Tailwind handles styling, optional additional tweaks can be added */
</style>
