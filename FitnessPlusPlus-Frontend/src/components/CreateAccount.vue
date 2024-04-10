<template>
  <div id="createaccount">
    <div id="logo">
      <p>FitnessPlusPlus</p>
      <img src="../assets/logo.png" alt="logo.png">
    </div>
    <div id="new_account_panel">
      <form @submit.prevent>
        <table>
          <tr><td><p>Username</p></td></tr>
          <tr><td><input type="text" v-model="username"></td></tr>
          <tr><td><p>Email</p></td></tr>
          <tr><td><input type="text" v-model="email"></td></tr>
          <tr><td><p>Password</p></td></tr>
          <tr><td><input type="password" v-model="password"></td></tr>
          <tr><td><p>Confirm Password</p></td></tr>
          <tr><td><input type="password" v-model="confirmPassword"></td></tr>
          <tr><td><input type="submit" v-bind:disabled="!username || !email || !password || !confirmPassword"
                      @click="createAccount()" value="Create Account"></td></tr>
        </table>
      </form>
    </div>

    <table id="error_message">
      <tr v-for="err in errors">
        <td><p id="error">Error: {{err}}</p></td>
      </tr>
      <!-- ... -->
    </table>
  </div>
</template>

<script>

import axios from 'axios'
import config from '../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'createaccount',
  data () {
    return {
      username: '',
      password: '',
      confirmPassword: '',
      email: '',
      errors: [],
      response: []
    }
  },
  //...

  created: function () {
  },

  methods: {
    createAccount: function () {

      if (this.password === this.confirmPassword) {
        AXIOS.post('/register-user', {
          username: this.username,
          password: this.password,
          email: this.email}, {})
          .then(response => {
            this.$router.push('/login')
          })
          .catch(e => {
            this.errors = e.response.data.errors
          })
      } else {
        this.errors = ["Passwords do not match"]
      }
    }
  }
}

</script>

<style>

body {
  margin: 0;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

#createaccount #new_account_panel {
  width: 50%;
  padding: 5%;
  background: #f5f5f5;
  border-radius: 8px;
  margin: 4% auto;
  position: relative;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}


#createaccount #logo {
  position: absolute;
  top: 10px;
  right: 10px;
  text-align: right;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
}

#createaccount #logo p {
  font-size: 24px;
  margin: 0;
}

#createaccount #logo img {
  width: 80px;
  height: auto;
}

#createaccount #new_account_panel form {
  display: flex;
  flex-direction: column;
}

#createaccount #new_account_panel p {
  text-align: left;
  padding: 0;
  margin: 0 16% 0;
  font-size: 24px;
}

#createaccount #new_account_panel form input[type="text"],
#createaccount #new_account_panel form input[type="password"] {
  padding: 5px;
  width: 390px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 18px;
}

#createaccount #new_account_panel form input[type="submit"] {
  padding: 10px;
  width: 200px;
  background-color: #A276C5;
  color: black;
  border: none;
  border-radius: 20px;
  font-size: 20px;
  cursor: pointer;
  margin-top: 10px;
}

#createaccount #new_account_panel form input[type="submit"]:hover {
    background-color: #D1A5F3;
}

#createaccount #new_account_panel form input[type="submit"]:disabled {
  background-color: #B69DA7;
      color: #ccc;
      cursor: default;
      border: none;
}

#createaccount #new_account_panel form table {
  width: 100%;
}

#createaccount #error_message {
  margin-left: auto;
  margin-right: auto;
}

#createaccount #error {
  color: #D8000C;
  background-color: #FFD2D2;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 20px;
  font-size: 0.9em;
  text-align: center;
  border: 1px solid #D8000C;
  box-shadow: 0 0 5px rgba(216, 0, 12, 0.3);
  word-wrap: break-word;
  width: 100%
}


</style>
