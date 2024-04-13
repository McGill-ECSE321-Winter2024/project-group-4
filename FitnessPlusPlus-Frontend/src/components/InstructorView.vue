<template>
  <div class="page">
    <div id="container" class="noselect">
      <div id="logo">
        <p>FitnessPlusPlus</p>
        <img src="../assets/logo.png" alt="logo.png">
      </div>
      <h2 class="instructorhome">Instructor Home</h2>
      <h3 class="tablehead">Scheduled Classes</h3>
      <div class="classtable">
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
                @click="selectClass(scheduledClass.scheduledClassID)" :class="[{ 'is-mine': scheduledClass.instructorID === currentRoleId }, { 'is-selected': scheduledClass.scheduledClassID === classId }]">
              <td>{{ scheduledClass.date }}</td>
              <td>{{ scheduledClass.startTime }}</td>
              <td>{{ scheduledClass.endTime }}</td>
              <td>{{scheduledClass.type}}</td>
              <td>{{scheduledClass.description}}</td>
              <td>{{scheduledClass.instructorUsername}}</td>
            </tr>
            <tr v-for="(placeholder, index) in placeholders" :key="`placeholder-${index}`" class="placeholder noselect">
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
      <button id="cancel-class-button" v-bind:disabled="!classId" @click="removeScheduledClass">Cancel Class</button>
      <button id="schedule-class-button" @click="scheduleOfferedClass">Schedule Class</button>
      <button id="create-class-button" @click="createOfferedClass">Offer New Class</button>
      <button id="logout-button" @click="logout">Logout</button>
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
  name: 'instructorview',
  data() {
    return {
      classId: null,
      scheduledClasses: [],
      currentRoleId: null,
      desiredItemCount: 7,
      errors: []
    };
  },
  created () {
    // If not signed in
    if (localStorage.getItem("username") === null || !localStorage.getItem("password") === null) {
      this.$router.push('/');
      return
    }

    //Otherwise check valid account
    AXIOS.post('/login', {
      username: localStorage.getItem("username"),
      password: localStorage.getItem("password")}, {})
      .then(response => {
        if (response.data.roleType === "Client") {
          this.$router.push('/Dashboard');

        }
      })
      .catch(e => {
        alert(e.message);
      })

    this.fetchScheduledClasses();
    this.currentRoleId = parseInt(localStorage.getItem("accountRoleId"))
  },
  computed: {
    placeholders() {
      const placeholdersCount = this.desiredItemCount - this.scheduledClasses.length;
      return Array(placeholdersCount < 0 ? 0 : placeholdersCount).fill({});
    },
  },
  methods: {
    fetchScheduledClasses() {
      AXIOS.get('/week-class').then(response => {
        this.scheduledClasses = response.data;
      }).catch(error => {
        this.errors.push(error.message || "Failed to fetch classes.");
      });
    },
    scheduleOfferedClass() {
      this.$router.push('/schedule-classes');
    },
    createOfferedClass() {
      // Open Offer Class component
      this.$router.push('/offer-class');
    },
    removeScheduledClass() {
      AXIOS.delete(`/scheduled-classes/${this.classId}`)
        .then(() => {
          this.fetchScheduledClasses()
        })
        .catch(error => {
          this.errors.push(error.message || "Failed to remove class.");
        });
    },
    selectClass(id) {
      if (this.classId === id) {
        this.classId = null
      }
      else {
        this.classId = id
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
    grid-template-columns: 60fr 40fr;
    grid-template-rows: 40px 40px auto auto 50px 50px;
    grid-template-areas:"head head""tablehead .""t schedule""t offer""cancel .""logout .";
    grid-gap: 15px;

    justify-content: center;
    align-items: center;
    padding: 20px;
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

  .tablehead {
    grid-area: tablehead;
  }

  .instructorhome {
    grid-area: head;
  }

  #cancel-class-button {
    grid-area: cancel;
  }

  #schedule-class-button {
    grid-area: schedule;
    align-self: end;
  }

  #create-class-button {
    grid-area: offer;
  }

  .classtable {
    grid-area: t;
    height: 390px;  /* Fixed height */
    overflow-y: auto;  /* Enables vertical scrolling */
    width: 100%;  /* Optional: fits the container width */
  }

  .classtable table{
    table-layout: fixed;
    height: 50px;
    overflow-y: auto;
    font-family: 'Arial', sans-serif;
    border-collapse: collapse;
    width: 100%;
  }

  .classtable th, .classtable td {
    padding: 15px 10px; /* Increased padding for more vertical and horizontal space */
    text-align: left; /* Ensures content alignment is consistent */
  }

  .classtable td {
    height: 50px;
    width: 200px; /* Fixed width for demonstration */
    padding: 5px; /* Padding for text */
    text-align: left; /* Align text to the left */
    word-wrap: break-word; /* Allow long words to break and wrap */
    word-break: break-word; /* Use for aggressive word breaking */
  }

  .classtable tr {
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #e1e1e1;
    background-color: #fff;
    transition: background-color 0.3s ease;
    min-height: 50px;
  }

  .classtable tr:hover {
    background-color: #f0f0f0;
  }


  .classtable th {
    background-color: #f4f4f4;
    font-weight: bold;
    text-align: left;
  }

  .classtable td {
    text-align: left;
  }

  .classtable::-webkit-scrollbar {
    width: 10px;
  }

  .classtable::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  .classtable::-webkit-scrollbar-thumb {
    background: #888;
  }

  .classtable::-webkit-scrollbar-thumb:hover {
    background: #555;
  }

  tr.is-mine {
    background-color: #FFEEDD;
  }

  tr.is-mine:hover {
    background-color: #ffd8b6;
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
