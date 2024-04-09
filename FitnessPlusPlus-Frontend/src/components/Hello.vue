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
      this.$router.push({ name: 'CreateAccount' }); // Note: route names are typically camelCase
    }
  }
}
</script>

<style scoped>
#home {
  text-align: center; /* Center-align text */
  background-color: #; /* Light grey background */
  font-family: 'Arial', sans-serif; /* Font style for the text */
  color: #333; /* Dark grey color for text */
  height: 100vh; /* Full height of the viewport */
  display: flex;
  flex-direction: column;
  justify-content: center; /* Center items vertically */
}

#logo {
  display: flex; /* Use flexbox for layout */
  align-items: center; /* Align items vertically in the center */
  justify-content: flex-start; /* Align items to the start (left) */
  padding-top: 20px; /* Adjust the top padding to bring the text up */
  padding-left: 20px; /* Adjust the left padding to bring the text towards the left */
}

#logo p {
  font-family: 'Helvetica', sans-serif; /* New font family */
  margin-right: 40px; /* Adds some space between the text and the logo */
  font-size: 7em; /* Larger text for the FitnessPlusPlus */
  margin-bottom: 0; /* Ensure there's no margin below the text pushing the logo down */
  margin-left: 30px
}

#logo img {
  width: 400px; /* Width of the logo */
  height: auto; /* Keep aspect ratio of the logo */
  margin-left: 50px;
}

button {
  background-color: #9b7181; /* Color for the buttons */
    color: black; /* Text color for the buttons */
    border: none; /* No border */
    padding: 10px 20px; /* Reduced padding inside the buttons */
    margin-top: 20px; /* Space between buttons */
    border-radius: 20px; /* Rounded corners for the buttons */
    font-size: 2em; /* Text size inside the buttons */
    cursor: pointer; /* Pointer cursor on hover */
    outline: none; /* Remove the outline */
    width: 500px; /* Default width behavior */
    display: block; /* Display as block to enable margin auto */
    margin-left: auto; /* Center the button horizontally */
    margin-right: auto; /* Center the button horizontally */
}

button:hover {
  background-color: #8b6574; /* Slightly darker color on hover */
}
</style>

