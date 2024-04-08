<!-- THIS IS NOT GONNA WORK BECAUSE THERE ARE NO INSTRUCTORS AND IN GENERAL WE SHOULDNT MAKE THE CLIENT
 ENTER THEIR ID THIS IS JUST TO SHOW HOW TO MAKE A COMMUNICATION WITH THE BACK END! I LOVE YOU YON-->

<template>
  <div id="template">
    <h2>ScheduledClasses</h2>
    <table>
      <tr v-for="scheduled_class in scheduled_classes" :key="scheduled_class.date">
        <td>{{ scheduled_class.scheduledClassID }}</td>
      </tr>
      <!-- ... -->
    </table>
    <table>
      <!-- ... -->
      <tr>
        <td>
          <input type="time" v-model="newStartTime" placeholder="Start time">
          <input type="time" v-model="newEndTime" placeholder="End time">
          <input type="date" v-model="newDate" placeholder="Date">
          <input type="number" v-model="newOfferedClassID" placeholder="Offered Class ID">
          <input type="number" v-model="newInstructorID" placeholder="Instructor ID">
        </td>
        <td>
          <button v-bind:disabled="!newStartTime || !newEndTime || !newDate || !newOfferedClassID || !newInstructorID"
                  @click="createScheduledClass(newStartTime, newEndTime, newDate, newOfferedClassID, newInstructorID)">Schedule Class</button>
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
  name: 'template',
  data () {
    return {
      scheduled_classes: [],
      newStartTime: '',
      newEndTime: '',
      newDate: '',
      newOfferedClassID: '',
      newInstructorID: '',
      errors: [],
      response: []
    }
  },
  //...

  created: function () {
    // Initializing scheduledclasses from backend
    AXIOS.get('/scheduled-classes')
      .then(response => {
        // JSON responses are automatically parsed.
        this.scheduled_classes = response.data
      })
      .catch(e => {
        this.errors.push = e.message
      })
  },

  methods: {
    createScheduledClass: function (startTime, endTime, date, offeredClassID, instructorID) {

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
  #template {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
