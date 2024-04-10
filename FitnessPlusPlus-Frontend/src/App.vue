<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
body, html {
  margin: 0;
  padding: 0;
  background: #DAC7AC;
}

#app {
  font-family: 'Avenir', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #000000;
  width: 100vw;
  height: 100vh;
}
</style>
