<template>
  <div id="createaccount">
    <h2>Create Account</h2>
    <table>
      <!-- ... -->
      <tr>
        <td>
          <input type="text" v-model="username" placeholder="Username">
          <input type="text" v-model="email" placeholder="Email">
          <input type="password" v-model="password" placeholder="Password">
          <input type="password" v-model="confirmPassword" placeholder="Confirm Password">
        </td>
        <td>
          <button v-bind:disabled="!username || !email || !password || !confirmPassword"
                  @click="createAccount()">Create Account</button>
        </td>
      </tr>
    </table>
    <table>
      <tr v-for="err in errors">
        <td><p><span style="color:red">Error: {{err}}</span></p></td>
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
#createaccount {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
