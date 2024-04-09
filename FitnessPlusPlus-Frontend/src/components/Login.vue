<template>
  <div id="login">
    <h2>Login</h2>
    <table>
      <!-- ... -->
      <tr>
        <td>
          <input type="text" v-model="username" placeholder="Username">
          <input type="password" v-model="password" placeholder="Password">
        </td>
        <td>
          <button v-bind:disabled="!username || !password"
                  @click="login()">Log In</button>
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
            this.$router.push('/Admin');
          }
        })
        .catch(e => {
          this.errors = e.response.data.errors
        })
    }
  }
}

</script>

<style>
  #login {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
