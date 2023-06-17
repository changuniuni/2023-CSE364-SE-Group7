<template>
  <div id="app">
    <div class="logo-container">
      <img src="./assets/logo.png" alt="paCsemaker Logo" class="logo-image">
    </div>

    <div class="button-container">
      <router-link to="/signup" class="button">Sign Up</router-link>
      <router-link to="/login" class="button">Login</router-link>
    </div>

    <router-view></router-view>
  </div>
</template>

<style>
#app {
  text-align: center;
  margin-top: 100px;
}

.logo-container {
  margin-bottom: 20px;
}

.logo-image {
  max-width: 400px;
}

.button-container {
  margin-bottom: 20px;
}

.button-container .button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #3490dc;
  color: white;
  text-decoration: none;
  border-radius: 4px;
}

.button-container .button + .button {
  margin-left: 10px;
}
</style>
