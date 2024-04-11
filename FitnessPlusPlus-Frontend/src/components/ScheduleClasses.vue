<template>
  <div class="row">
    <div class="column">
      <h2>Approved Classes</h2>
      <ul id="class-list">
        <li v-for="(offeredClass, index) in offered_classes" v-if"offeredClass.approved"
          :key="offeredClass.offeredClassId">
          <div class="class-description">
            <span>
              {{ offeredClass.classType }} - {{ offeredClass.description }}
            </span>
          </div>
        </li>
      </ul>
      <button style="float:left" @click="logout">Logout</button>
      <button style="float:right">Remove Class</button>
    </div>
    <div class="column">
      <label for="startTime"><b>Start Time</b></label><br>
      <input type="time" placeholder="Enter Start Time" id="startTime" required>
      <br>

      <label for="endTime"><b>End Time</b></label><br>
      <input type="time" placeholder="Enter End Time" id="endTime" required>
      <br>

      <label for="date"><b>Date</b></label><br>
      <input type="date" placeholder="Enter Date" id="date" required>
      <br>

      <button @click="getSchedule">Schedule Class</button>
      <br><br>
      <button @click="navRegisterClasses">Register for Classes</button>
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
      offered_classes: [],
      newStartTime: '',
      newEndTime: '',
      newDate: '',
      newOfferedClassID: '',
      newInstructorID: '',
      errors: [],
      response: [],
      startTime: '',
      endTime: '',
      date: '',
      selectedClassId: ''
    }
  },
  //...

  created: function () {
      // Initializing offeredClasses from backend
      AXIOS.get('/offered-classes')
        .then(response => {
          this.offered_classes = response.data
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
      },

      logout() {
            this.$router.push({ name: 'Home' });
      },

      navRegisterClasses() {
        this.$router.push('/register');
      }
  }
}

document.querySelector('ul').addEventListener('click', function(e) {
  var selected;

  if(e.target.tagName === 'LI') {
    selected= document.querySelector('li.selected');
    if(selected) {
      selected.className= '';
      this.selectedClassId = selected.offeredClassId
    }
    e.target.className= 'selected';
  }
}),

function getSchedule() {
  this.startTime = document.getElementById("startTime").value;
  this.endTime = document.getElementById("endTime").value;
  this.date = document.getElementById("date").value;
  createScheduledClass(this.startTime, this.endTime, this.date, this.selectedClassId, localStorage.accountRoleId);
}
;
</script>

<style scoped>
  .column {
    float: left;
    width: 50%;
    padding: 10px;
  }

  .input {
    alignment: center;
  }

  .selected {
    background: lightblue;
  }

  .button {
    border-radius: 5px;
    padding: 5px 10px;
    margin-right: 10px;
  }

</style>
