<template>
  <div class="container">
    <!-- Left Panel -->
    <div class="left-panel">
      <h2>Dashboard</h2>
      <div class="scheduled_class_container">
        <p>Available classes</p>
        <table>
          <thead>
            <tr>
              <!--          <th>Type</th>-->
              <th>Date</th>
              <th>Start Time</th>
              <th>End Time</th>
              <th>Type</th>
              <th>Description</th>
              <th>Instructor</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="scheduledClass in scheduledClasses" :key="scheduledClass.scheduledClassID"
                @click="selectClass(scheduledClass.scheduledClassID, scheduledClass.date)">
              <td>{{ scheduledClass.date }}</td>
              <td>{{ scheduledClass.startTime }}</td>
              <td>{{ scheduledClass.endTime }}</td>
              <td>{{scheduledClass.type}}</td>
              <td>{{scheduledClass.description}}</td>
              <td>{{scheduledClass.instructorUsername}}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="registrations_container">
        <p>Your registrations</p>
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Start Time</th>
              <th>End Time</th>
              <th>Type</th>
              <th>Description</th>
              <th>Instructor</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="reg in registrations" :key="reg.registrationId"
                @click="selectRegistration(reg.registrationId)">
              <!--          <td>{{ scheduledClass.classType }}</td>-->
              <td>{{ reg.scheduleClass.date }}</td>
              <td>{{ reg.scheduleClass.startTime }}</td>
              <td>{{ reg.scheduleClass.endTime }}</td>
              <td>{{ reg.scheduleClass.type }}</td>
              <td>{{ reg.scheduleClass.description }}</td>
              <td>{{ reg.scheduleClass.instructorUsername }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <button id="logout-button" @click="logout">Logout</button>
      <button id="cancel-class-button" v-bind:disabled="!registrationId" @click="removeRegistration()">Cancel Class</button>
   </div>
    <button id="register-button" v-bind:disabled="!classId" @click="registerClass()">Register</button>

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
  name: 'registeredclass',
  data() {
    return {
      classId: null,
      registrationId: null,
      date: null,
      scheduledClasses: [],
      registrations: [],
      errors: []
    };
  },
  created: function () {
    this.fetchScheduledClasses();
    this.fetchRegistrations()
  },
  methods: {
    registerClass: function () {

      AXIOS.post('/register', {
        dateOfRegistration: this.date,
        clientId: localStorage.getItem("accountRoleId"),
        scheduledClassID: this.classId}, {})
        .then(response => {
          this.fetchRegistrations()
        })
        .catch(e => {
          alert(e.response.data.errors)
        })
    },
    fetchScheduledClasses() {
      AXIOS.get('/week-class').then(response => {
        this.scheduledClasses = response.data;
      }).catch(error => {
        this.errors.push(error.message || "Failed to fetch classes.");
      });
    },
    fetchRegistrations() {
      AXIOS.get('/client-registrations/'+localStorage.getItem("accountRoleId"))
        .then(response => {
        this.registrations = response.data;
      }).catch(error => {
        this.errors.push(error.message || "Failed to fetch registrations");
      });
    },

    removeRegistration : function() {
      AXIOS.delete(`/registrations/${this.registrationId}`)
        .then(() => {
          this.fetchRegistrations()
        })
        .catch(error => {
          this.errors.push(error.message || "Failed to remove class.");
        });
    },
    selectClass(id, date) {
      if (this.classId === id) {
        this.classId = null
        this.date = null
      }
      else {
        this.classId = id
        this.date = date
      }
    },
    selectRegistration(id) {
      if (this.registrationId === id) {
        this.registrationId = null
      }
      else {
        this.registrationId = id
      }
    },
      logout() {
      localStorage.clear()
      this.$router.push({ name: 'Home' });
    },
  }
};
</script>

<style scoped>
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
