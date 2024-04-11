<template>
  <div id="login">
    <div id="logo">
      <p>FitnessPlusPlus</p>
      <img src="../assets/logo.png" alt="logo.png">
    </div>
    <div id="login_panel">
      <form @submit.prevent>
        <table>
          <tr><td><p>Username</p></td></tr>
          <tr><td><input type="text" v-model="username"></td></tr>
          <tr><td><p>Password</p></td></tr>
          <tr><td><input type="password" v-model="password"></td></tr>
          <tr><td><input type="submit" v-bind:disabled="!username || !password"
                     @click="login()" value="Log In"></td></tr>
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
  name: 'login',
  data () {
    return {
      username: '',
      password: '',
      errors: [],
      response: []
    }
  },
  //...

  created: function () {

    // If signed in
    if (localStorage.getItem("username") !== null && !localStorage.getItem("password") !== null) {
      //Redirect to right page
      AXIOS.post('/login', {
        username: localStorage.getItem("username"),
        password: localStorage.getItem("password")}, {})
        .then(response => {
          if (response.data.roleType === "Client") {
            this.$router.push('/Dashboard');

          } else if (response.data.roleType === "Instructor") {
            this.$router.push('/ManageSchedule');
          } else if (response.data.roleType === "Owner") {
            this.$router.push('/owner-page')
          }
        })
        .catch(e => {
          alert(e.message);
        })
    }

  },

  methods: {
    login: function () {

      AXIOS.post('/login', {
        username: this.username,
        password: this.password}, {})
        .then(response => {
          localStorage.setItem('userId', response.data.userId)
          localStorage.setItem('username', response.data.username)
          localStorage.setItem('password', response.data.password)
          localStorage.setItem('email', response.data.email)
          localStorage.setItem('accountRoleId', response.data.accountRole)
          localStorage.setItem('accountRoleType', response.data.roleType)

          if (localStorage.getItem("accountRoleType").includes("Client")) {
            this.$router.push('/Dashboard');
          }else if(localStorage.getItem("accountRoleType").includes("Instructor")){
            this.$router.push('/ManageSchedule');
          } else if(localStorage.getItem("accountRoleType").includes("Owner")){
            this.$router.push('/owner-page');
          }
        })
        .catch(e => {
          this.errors = e.response.data.errors
        })
    }
  }
}

</script>

<style scoped>
  body {
    margin: 0;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #login #login_panel {
    width: 50%;
    padding: 5%;
    background: #f5f5f5;
    border-radius: 8px;
    margin: 10% auto;
    position: relative;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }


  #login #logo {
    position: absolute;
    top: 10px;
    right: 10px;
    text-align: right;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 10px;
  }

  #login #logo p {
    font-size: 24px;
    margin: 0;
  }

  #login #logo img {
    width: 80px;
    height: auto;
  }

  #login #login_panel form {
    display: flex;
    flex-direction: column;
  }

  #login #login_panel p {
    text-align: left;
    padding: 0;
    margin: 0 16% 0;
    font-size: 24px;
  }

  #login #login_panel form input[type="text"],
  #login #login_panel form input[type="password"] {
    padding: 5px;
    width: 390px;
    margin-bottom: 40px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 18px;
  }

  #login #login_panel form input[type="submit"] {
    padding: 10px;
    width: 200px;
    background-color: #A276C5;
    color: black;
    border: none;
    border-radius: 20px;
    font-size: 20px;
    cursor: pointer;
  }

  #login #login_panel form input[type="submit"]:hover {
    background-color: #D1A5F3; /* Same as the home page button hover */
  }

  #login #login_panel form input[type="submit"]:disabled {
    background-color: #B69DA7;
    color: #ccc;
    cursor: default;
    border: none;
  }


  #login #login_panel form table {
    width: 100%;
  }

  #login #error_message {
    margin-left: auto;
    margin-right: auto;
  }

  #login #error {
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
