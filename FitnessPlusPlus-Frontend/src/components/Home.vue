<template>
  <div id="home">
    <div id="logo">
      <p>FitnessPlusPlus</p>
      <img src="../assets/logo.png" alt="logo.png">
    </div>
    <button @click="login">Login</button>
    <button @click="createAccount">Create Account</button>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  methods: {
    login() {
      this.$router.push({ name: 'Login' });
    },
    createAccount() {
      this.$router.push({ name: 'Create Account' }); // Note: route names are typically camelCase
    }
  }
}
</script>

<style scoped>
#home {
  text-align: center; /* Center-align text */
  font-family: 'Arial', sans-serif; /* Font style for the text */
  color: #333; /* Dark grey color for text */
  height: 100vh; /* Full height of the viewport */
  display: flex;
  flex-direction: column;
  justify-content: center; /* Center items vertically */
}

#logo {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding-top: 20px;
  padding-left: 20px; 
}

#logo p {
  font-family: 'Helvetica', sans-serif;
  margin-right: 40px;
  font-size: 7em;
  margin-bottom: 0;
  margin-left: 30px
}

#logo img {
  width: 400px;
  height: auto;
  margin-left: 50px;
}

button {
  background-color: #8a2be2;
    color: black;
    border: none;
    padding: 10px 20px;
    margin-top: 20px;
    border-radius: 20px;
    font-size: 2em;
    cursor: pointer;
    outline: none;
    width: 500px;
    display: block;
    margin-left: auto;
    margin-right: auto;
}

button:hover {
  background-color: #7c27cb;
}
</style>

