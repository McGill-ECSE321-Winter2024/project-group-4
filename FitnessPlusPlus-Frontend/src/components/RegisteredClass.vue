<template>
  <div class="page">
    <div id="container" class="noselect">
      <div id="logo">
        <p>FitnessPlusPlus</p>
        <img src="../assets/logo.png" alt="logo.png">
      </div>
      <h2 class="dashboard-title">Dashboard</h2>
      <h3 class="class-title">Available classes</h3>
      <div class="scheduled_class_container">
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
            <tr v-for="scheduledClass in scheduledClasses" :key="scheduledClass.scheduledClassID"
                @click="selectClass(scheduledClass.scheduledClassID, scheduledClass.date)" :class="{ 'is-selected': scheduledClass.scheduledClassID === classId }">
              <td>{{ scheduledClass.date }}</td>
              <td>{{ scheduledClass.startTime }}</td>
              <td>{{ scheduledClass.endTime }}</td>
              <td>{{scheduledClass.type}}</td>
              <td>{{scheduledClass.description}}</td>
              <td>{{scheduledClass.instructorUsername}}</td>
            </tr>
            <tr v-for="(placeholder, index) in placeholders_class" :key="`placeholder-${index}`" class="placeholder noselect">
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <h3 class="reg-title">Your registrations</h3>
      <div class="registrations_container">
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
                @click="selectRegistration(reg.registrationId)" :class="{ 'is-selected': reg.registrationId === registrationId }">
              <!--          <td>{{ scheduledClass.classType }}</td>-->
              <td>{{ reg.scheduleClass.date }}</td>
              <td>{{ reg.scheduleClass.startTime }}</td>
              <td>{{ reg.scheduleClass.endTime }}</td>
              <td>{{ reg.scheduleClass.type }}</td>
              <td>{{ reg.scheduleClass.description }}</td>
              <td>{{ reg.scheduleClass.instructorUsername }}</td>
            </tr>
            <tr v-for="(placeholder, index) in placeholders_reg" :key="`placeholder-${index}`" class="placeholder noselect">
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <button id="logout-button" @click="logout">Logout</button>
      <button id="cancel-class-button" v-bind:disabled="!registrationId" @click="removeRegistration()">Cancel Class</button>
      <button id="register-button" v-bind:disabled="!classId" @click="registerClass()">Register</button>

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
  name: 'registeredclass',
  data() {
    return {
      classId: null,
      registrationId: null,
      date: null,
      scheduledClasses: [],
      registrations: [],
      desiredItemCount: 7,
      errors: []
    };
  },
  created: function () {
    if (localStorage.getItem("username") === null || !localStorage.getItem("password") === null) {
      this.$router.push('/');
      return
    }

    this.fetchScheduledClasses();
    this.fetchRegistrations()
  },
  computed: {
    placeholders_class() {
      const placeholdersCount = this.desiredItemCount - this.scheduledClasses.length;
      return Array(placeholdersCount < 0 ? 0 : placeholdersCount).fill({});
    },
    placeholders_reg() {
      const placeholdersCount = this.desiredItemCount - this.registrations.length;
      return Array(placeholdersCount < 0 ? 0 : placeholdersCount).fill({});
    },
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
        this.registrationId = null
        this.classId = id
        this.date = date
      }
    },
    selectRegistration(id) {
      if (this.registrationId === id) {
        this.registrationId = null
      }
      else {
        this.date = null
        this.classId = null
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
.page {
  padding: 0 0 0 20px;
  position: fixed;
}

#container {
  width: 100vw;
  height: 95vh;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 100px 100px auto 50px 50px;
  grid-template-areas:"head head""classhead reghead""classtable regtable""regbutton cancelbutton""logout .";
  grid-gap: 15px;

  justify-content: center;
  align-items: center;
  padding-right: 30px;
}

#logo {
  position: absolute;
  top: 30px;
  right: 30px;
  text-align: right;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
}

#logo p {
  font-size: 24px;
  margin: 0;
}

#logo img {
  width: 80px;
  height: auto;
}


.dashboard-title {
  grid-area: head;
  align-self: end;
}

.class-title {
  grid-area: classhead;
  align-self: end;
}

.reg-title {
  grid-area: reghead;
  align-self: end;
}

#cancel-class-button {
  grid-area: cancelbutton;
}

#register-button {
  grid-area: regbutton;
}

.scheduled_class_container {
  grid-area: classtable;
}

.registrations_container {
  grid-area: regtable;
}

.scheduled_class_container, .registrations_container {
  height: 390px;  /* Fixed height */
  overflow-y: auto;  /* Enables vertical scrolling */
  width: 100%;  /* Optional: fits the container width */
}

.scheduled_class_container table, .registrations_container table{
  table-layout: fixed;
  overflow-y: auto;
  font-family: 'Arial', sans-serif;
  border-collapse: collapse;
  width: 100%;
}

.scheduled_class_container th, .registrations_container th, .scheduled_class_container td, .registrations_container td {
  padding: 15px 10px; /* Increased padding for more vertical and horizontal space */
  text-align: left; /* Ensures content alignment is consistent */
}

.scheduled_class_container td, .registrations_container td {
  height: 50px;
  width: 200px; /* Fixed width for demonstration */
  padding: 5px; /* Padding for text */
  text-align: left; /* Align text to the left */
  word-wrap: break-word; /* Allow long words to break and wrap */
  word-break: break-word; /* Use for aggressive word breaking */
}

.scheduled_class_container tr, .registrations_container tr {
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e1e1e1;
  background-color: #fff;
  transition: background-color 0.3s ease;
  min-height: 50px;
}

.scheduled_class_container tr:hover, .registrations_container tr:hover {
  background-color: #f0f0f0;
}


.scheduled_class_container th, .registrations_container th {
  background-color: #f4f4f4;
  font-weight: bold;
  text-align: left;
}

.scheduled_class_container td, .registrations_container td {
  text-align: left;
}

.scheduled_class_container::-webkit-scrollbar, .registrations_container::-webkit-scrollbar {
  width: 10px;
}

.scheduled_class_container::-webkit-scrollbar-track, .registrations_container::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.scheduled_class_container::-webkit-scrollbar-thumb, .registrations_container::-webkit-scrollbar-thumb {
  background: #888;
}

.scheduled_class_container::-webkit-scrollbar-thumb:hover, .registrations_container::-webkit-scrollbar-thumb:hover {
  background: #555;
}

tr.is-selected {
  background-color: #D1A5F3;
  color: white;
}

tr.is-selected:hover {
  background-color: #ca98ef;
  color: white;
}

.noselect {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

button {
  padding: 10px 30px;
  background-color: #8e6a7e;
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 18px;
  cursor: pointer;
  justify-self: center;
  align-self: start;
}

button:hover:not(:disabled) {
  background-color: #8a2be2;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

#logout-button {
  grid-area: logout;
  justify-self: start;
  align-self: end;
}
</style>
