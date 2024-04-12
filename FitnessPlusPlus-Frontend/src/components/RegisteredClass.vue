<template>
  <div class="container">
    <!-- Left Panel -->
    <div class="left-panel">
      <h2>Scheduled Classes</h2>
      <table>
        <thead>
        <tr>
<!--          <th>Type</th>-->
          <th>Date</th>
          <th>Start Time</th>
          <th>End Time</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="scheduledClass in scheduledClasses" :key="scheduledClass.scheduledClassID"
            @click="selectClass(scheduledClass.scheduledClassID, scheduledClass.date)">
<!--          <td>{{ scheduledClass.classType }}</td>-->
          <td>{{ scheduledClass.date }}</td>
          <td>{{ scheduledClass.startTime }}</td>
          <td>{{ scheduledClass.endTime }}</td>
        </tr>
        </tbody>
      </table>

      <table>
        <thead>
        <tr>
          <!--          <th>Type</th>-->
          <th>Date</th>
          <th>Start Time</th>
          <th>End Time</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="scheduledClass in scheduledClasses" :key="scheduledClass.scheduledClassID"
            @click="selectClass(scheduledClass.scheduledClassID, scheduledClass.date)">
          <!--          <td>{{ scheduledClass.classType }}</td>-->
          <td>{{ scheduledClass.date }}</td>
          <td>{{ scheduledClass.startTime }}</td>
          <td>{{ scheduledClass.endTime }}</td>
        </tr>
        </tbody>
      </table>

      <button id="logout-button" @click="logout">Logout</button>
      <button id="cancel-class-button" v-bind:disabled="!id" @click="removeScheduledClass()">Cancel Class</button>
   </div>
    <button id="register-button" v-bind:disabled="!id" @click="registerClass()">Register</button>

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
      id: null,
      date: null,
      scheduledClasses: [],
      instructorClasses: [],
      errors: []
    };
  },
  created: function () {
    this.fetchScheduledClasses();

  },
  methods: {
    registerClass: function () {

      AXIOS.post('/register', {
        dateOfRegistration: this.date,
        clientId: localStorage.getItem("accountRoleId"),
        scheduledClassID: this.id}, {})
        .then(response => {
          alert("Successfully registered");
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
    filterInstructorClasses(){
      const currentUserID = localStorage.getItem("accountRoleId"); // Implement this method to get current user's ID
      this.instructorClasses = this.scheduledClasses.filter(scheduledClass => scheduledClass.instructorId === currentUserID);
    },
    removeScheduledClass : function() {
      AXIOS.delete(`/registrations/${this.id}`)
        .then(() => {
          alert("Succesfully deleted registration")
          this.offered_classes = this.offered_classes.filter(classItem => classItem.offeredClassId !== offeredClassId);
        })
        .catch(error => {
          this.errors.push(error.message || "Failed to remove class.");
        });
    },
    selectClass(id, date) {
      if (this.id === id) {
        this.id = null
        this.date = null
      }
      else {
        this.id = id
        this.date = date
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
