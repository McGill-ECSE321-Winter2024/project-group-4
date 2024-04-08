<template>
  <div id="login">
    <h2>ScheduledClasses</h2>
    <table>
      <!-- ... -->
      <tr>
        <td>
          <input type="text" v-model="" placeholder="Username">
          <input type="password" v-model="" placeholder="Password">
        </td>
        <td>
          <button v-bind:disabled=""
                  @click="">Log In</button>
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

      AXIOS.post('/scheduled-class/', {
        startTime: startTime.concat(":00"),
        endTime: endTime.concat(":00"),
        date: date,
        offeredClassID: offeredClassID,
        instructorID: instructorID}, {})
        .then(response => {
            // JSON responses are automatically parsed.
            this.scheduled_classes.push(response.data)
            this.errors = []
            this.newStartTime= ''
            this.newEndTime = ''
            this.newDate = ''
            this.newOfferedClassID = ''
            this.newInstructorID = ''
          }
        )
        .catch(e => {
          this.errors = e.response.data.errors
        })
    }
  }
}

</script>

<style>

</style>
