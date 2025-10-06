<template>
  <div class="flex items-center justify-center h-screen bg-gray-100">
    <div class="bg-white p-10 rounded-lg shadow-md w-full max-w-md">
      <h2 class="text-2xl font-bold mb-6 text-center text-red-500">Login</h2>
      <form @submit.prevent="handleLogin" class="space-y-4">
        <div>
          <label class="block mb-1 font-semibold">Email</label>
          <input
            v-model="email"
            type="email"
            placeholder="Enter your email"
            class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:border-blue-400"
            required
          />
        </div>

        <div>
          <label class="block mb-1 font-semibold">Password</label>
          <input
            v-model="password"
            type="password"
            placeholder="Enter password"
            class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:border-blue-400"
            required
          />
        </div>

        <div v-if="error" class="text-red-500 text-sm">{{ error }}</div>

        <button
          type="submit"
          class="w-full bg-red-500 text-white font-semibold py-2 rounded hover:bg-red-600 transition-colors"
        >
          Login
        </button>
      </form>

      <p class="mt-4 text-center text-gray-600">
        Don't have an account?
        <router-link to="/signup" class="text-blue-500 hover:underline">Sign Up</router-link>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      error: "",
    };
  },
  methods: {
    handleLogin() {
      this.error = "";
      const users = JSON.parse(localStorage.getItem("users") || "[]");
      const user = users.find(
        (u) => u.email === this.email && u.password === this.password
      );

      if (!user) {
        this.error = "Invalid email or password!";
        return;
      }

      alert("Login successful!");
      // Redirect to home/dashboard after login
      this.$router.push("/");
    },
  },
};
</script>
