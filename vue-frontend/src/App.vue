<template>
  <div id="app">
    <div class="logo-container">
      <img src="./assets/logo.png" alt="paCsemaker Logo">
    </div>

    <div class="button-container">
      <button @click="redirectToSignup">Sign Up</button>
      <button @click="redirectToLogin">Login</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "App",
  methods: {
    redirectToSignup() {
      // Redirect to the signup page or perform any desired action
      // Example: this.$router.push('/signup');
    },
    redirectToLogin() {
      // Redirect to the login page or perform any desired action
      // Example: this.$router.push('/login');
    },
  },
};
</script>

<style>
.logo-container {
  text-align: center;
  margin-top: 100px;
}

.button-container {
  text-align: center;
  margin-top: 20px;
}

.button-container button {
  padding: 10px 20px;
  font-size: 16px;
  margin: 0 10px;
}
</style>
