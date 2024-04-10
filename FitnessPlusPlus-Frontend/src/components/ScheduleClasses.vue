<template>
  <div class="row">
    <div class="column">
      <h2>Approved Classes</h2>
      <table id="classes">
      </table>
    </div>
    <div class="column">
      <label for="startTime"><b>Start Time</b></label><br>
      <input type="time" placeholder="Enter Start Time" name="startTime" required>
      <br>

      <label for="endTime"><b>End Time</b></label><br>
      <input type="time" placeholder="Enter End Time" name="endTime" required>
      <br>

      <label for="date"><b>Date</b></label><br>
      <input type="date" placeholder="Enter Date" name="date" required>
      <br>
    </div>
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
      // Initializing scheduledClasses from backend
      AXIOS.get('/scheduled-classes')
        .then(response => {
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
  .column {
    float: left;
    width: 50%;
    padding: 10px;
  }

  .input {
    alignment: center;
  }
</style>
