<template>
  <div class="flex items-center justify-center h-screen bg-gray-100">
    <div class="bg-white p-10 rounded-lg shadow-md w-full max-w-md">
      <h2 class="text-2xl font-bold mb-6 text-center text-red-500">Sign Up</h2>
      <form @submit.prevent="handleSignup" class="space-y-4">
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

        <div>
          <label class="block mb-1 font-semibold">Confirm Password</label>
          <input
            v-model="confirmPassword"
            type="password"
            placeholder="Confirm password"
            class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:border-blue-400"
            required
          />
        </div>

        <div v-if="error" class="text-red-500 text-sm">{{ error }}</div>

        <button
          type="submit"
          class="w-full bg-red-500 text-white font-semibold py-2 rounded hover:bg-red-600 transition-colors"
        >
          Create Account
        </button>
      </form>

      <p class="mt-4 text-center text-gray-600">
        Already have an account?
        <router-link to="/login" class="text-blue-500 hover:underline">Login</router-link>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Signup",
  data() {
    return {
      email: "",
      password: "",
      confirmPassword: "",
      error: "",
    };
  },
  methods: {
    handleSignup() {
      this.error = "";

      if (this.password !== this.confirmPassword) {
        this.error = "Passwords do not match!";
        return;
      }

      // Save user to localStorage (temporary for testing)
      const users = JSON.parse(localStorage.getItem("users") || "[]");
      const exists = users.find((u) => u.email === this.email);

      if (exists) {
        this.error = "User already exists!";
        return;
      }

      users.push({ email: this.email, password: this.password });
      localStorage.setItem("users", JSON.stringify(users));

      alert("Account created successfully!");
      this.$router.push("/login");
    },
  },
};
</script>
