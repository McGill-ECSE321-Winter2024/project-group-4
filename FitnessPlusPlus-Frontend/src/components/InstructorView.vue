<template>
  <div class="container">
    <!-- Left Panel -->
    <div class="left-panel">
    <h2>Scheduled Classes</h2>
    <table>
      <thead>
        <tr>
          <th>Type</th>
          <th>Date</th>
          <th>Start Time</th>
          <th>End Time</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="scheduledClass in instructorClasses" :key="instructorClasses.scheduledClassId">
          <td>{{ scheduledClass.classType }}</td>
          <td>{{ scheduledClass.date }}</td>
          <td>{{ scheduledClass.startTime }}</td>
          <td>{{ scheduledClass.endTime }}</td>
        </tr>
      </tbody>
    </table>
      <button id="logout-button" @click="logout">Logout</button>
      <button id="cancel-class-button" @click="removeScheduledClass(scheduledClass.scheduledClassID)">Cancel Class</button>
  </div>

  <!-- Right Panel -->
    <div class="right-panel">
      <!-- Build a calendar with scheduledClasses -->
      <v-calendar :events="scheduledClasses" title-position="right" :min-date="new Date()">
      <ul>
        <li v-for="scheduledClass in scheduledClasses" :key="scheduledClass.scheduleClassID">
          {{ scheduledClass.startTime }} - {{ scheduledClass.endTime }}: {{ scheduledClass.date }} (Class ID: {{ scheduledClass.scheduleClassID }})
        </li>
      </ul>
      </v-calendar>
      <div id="right-panel-buttons">
        <button id="schedule-class-button" @click="scheduleOfferedClass">Schedule Class</button>
        <button id="register-class-button" @click="registerOfferedClass">Register for Class</button>
        <button id="create-class-button" @click="createOfferedClass">Create New Class</button>
      </div>
    </div>
</div>
</template>


<script>
import Vue from 'vue';
import VCalendar from 'v-calendar';
import axios from 'axios'
import config from '../../config'
import { VueCookies } from 'vue-cookies';


const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
Vue.use(VCalendar);

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
  name: 'template',
  data() {
    return {
      scheduledClasses: [],
      instructorClasses: [],
      errors: []
    };
  },
  created() {
    // this.checkUserType();
    this.fetchScheduledClasses();
  },
  methods: {
    checkUserType() {
      // Assuming you have stored the user type in cookies or Vuex state
      const userType = this.$cookies.get('userType'); // Adjust this according to how you store user type
      if (userType !== 'Instructor') {
        this.redirectToDashboard();
      }
    },

    redirectToDashboard() {
      // Redirect to the dashboard route
      this.$router.push({ name: 'Dashboard' });
    },
    fetchScheduledClasses() {
      AXIOS.get('/week-class').then(response => {
        this.scheduledClasses = response.data;
        this.filterInstructorClasses();
      }).catch(error => {
        this.errors.push(error.message || "Failed to fetch classes.");
      });
    },
    scheduleOfferedClass() {
      this.$router.push({ name: '/schedule-classes' });
    },
    registerOfferedClass() {
      this.$router.push({ name: 'RegisterdClass' });
    },
    createOfferedClass() {
      // Open Offer Class component
      this.$router.push({ name: '/offer-class' });
    },
    filterInstructorClasses(){
      const currentUserID = localStorage.getItem("accountRoleId"); // Implement this method to get current user's ID
      this.instructorClasses = this.scheduledClasses.filter(scheduledClass => scheduledClass.instructorId === currentUserID);
    },
    removeScheduledClass(offeredClassId) {
      AXIOS.delete(`/offered-classes/${offeredClassId}`)
        .then(() => {
          this.offered_classes = this.offered_classes.filter(classItem => classItem.offeredClassId !== offeredClassId);
        })
        .catch(error => {
          this.errors.push(error.message || "Failed to remove class.");
        });
    },
    logout() {
      this.$router.push({ name: 'Home' });
    },
  }
};
</script>

<style>
  .container {
    display: flex;
    justify-content: space-between;
    padding: 20px;
  }
  .left-panel, .right-panel {
    width: 48%;
  }
  table {
    width: 100%;
    border-collapse: collapse;
  }
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    padding: 10px;
    text-align: left;
  }
  .button-group {
    text-align: center;
    padding: 10px 0;
  }
  .button-group button {
    margin-right: 10px;
    padding: 5px 10px;
  }
  .calendar-table {
    margin-top: 20px;
  }
  .calendar-table th {
    background-color: #f2f2f2;
  }
  .calendar-table td {
    height: 50px;
  }
  .footer-link {
    text-align: right;
    font-size: 12px;
    padding-top: 10px;
  }
</style>
