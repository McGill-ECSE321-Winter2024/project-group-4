<template>
  <div id="content">
    <header id="header">
      <div id="logo">
        <p>FitnessPlusPlus</p>
        <img src="../assets/logo.png"/>
      </div>
    </header>
    <h2>Schedule Classes</h2>

    <main>
      <section id="offered_class">
        <ul>
          <li v-for="c in offered_classes" v-if="c.approved" :key="c.offeredClassId" :class="{ 'is-selected': c.offeredClassId === newOfferedClassID }" class="noselect"
              @click="selectOfferedClass(c.offeredClassId)">
            {{ c.classType }} - {{ c.description}}
          </li>
          <li v-for="(placeholder, index) in placeholders" :key="`placeholder-${index}`" class="placeholder noselect">
            &nbsp;
          </li>
        </ul>
      </section>
      <div id="removeContainer">
        <button @click="remove" v-bind:disabled="!newOfferedClassID" id="removeButton">Remove</button>
      </div>
      <div id="logoutContainer">
        <button @click="logout" id="logoutButton">Logout</button>
      </div>

        <form @submit.prevent>
          <label for="startTime" class="noselect"><b>Start Time</b></label><br>
          <input type="time" placeholder="Start Time" id="startTime" v-model="newStartTime" required><br>


          <label for="endTime" class="noselect"><b>End Time</b></label><br>
          <input type="time" placeholder="End Time" v-model="newEndTime" id="endTime" required><br>


          <label for="date" class="noselect"><b>Date</b></label><br>
          <input type="date" placeholder="Date" v-model="newDate" id="date" required><br><br>


          <button @click="createScheduledClass" v-bind:disabled="!newOfferedClassID || !newStartTime || !newEndTime || !newDate">Schedule Class</button><br>

        </form>
    </main>
    <button id="previousPage" @click="previousPage">Previous Page</button>

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
  name: 'ScheduleClasses',
  data () {
    return {
      offered_classes: [],
      newStartTime: '',
      newEndTime: '',
      newDate: '',
      newOfferedClassID: '',
      newInstructorID: '',
      desiredItemCount: 11,
      errors: [],
      response: []
    }
  },
  //...

  created: function () {
    // If not signed in
    if (localStorage.getItem("username") === null || !localStorage.getItem("password") === null) {
      this.$router.push('/login');
      return
    }

    //Otherwise check valid account
    AXIOS.post('/login', {
      username: localStorage.getItem("username"),
      password: localStorage.getItem("password")}, {})
      .then(response => {
        if (response.data.roleType === "Client") {
          this.$router.push('/');

        }
      })
      .catch(e => {
        alert(e.message);
      })

      this.fetchOfferedClasses()
    },

  computed: {
    placeholders() {
      let n = this.offered_classes.length
      for (let c in this.offered_classes) {
        if (!c.approved) {
          n = n - 1
        }
      }
      const placeholdersCount = this.desiredItemCount - n;
      return Array(placeholdersCount < 0 ? 0 : placeholdersCount).fill({});
    },
  },

  methods: {
    fetchOfferedClasses() {
      AXIOS.get('/offered-classes').then(response => {
        this.offered_classes = response.data;
      }).catch(error => {
        this.errors.push(error.message || "Failed to load users");
      });
    },

    createScheduledClass() {

      AXIOS.post('/scheduled-class', {
        startTime: this.newStartTime.concat(":00"),
        endTime: this.newEndTime.concat(":00"),
        date: this.newDate,
        offeredClassID: this.newOfferedClassID,
        instructorID: localStorage.getItem("accountRoleId")}, {})
        .then(response => {
          // JSON responses are automatically parsed.
          this.errors = []
          this.newStartTime= ''
          this.newEndTime = ''
          this.newDate = ''
          this.newOfferedClassID = ''
          this.newInstructorID = ''
          alert("Successfully scheduled");
        }
      )
      .catch(e => {
        alert(e.response.data.errors);
      })
    },

    logout() {
      localStorage.clear()
      this.$router.push({ name: 'Home' });
    },

    previousPage() {
      this.$router.back();
    },

    selectOfferedClass(id) {
      if (this.newOfferedClassID === id) {
        this.newOfferedClassID = null
      }
      else {
        this.newOfferedClassID = id;
      }
      console.log(this.newOfferedClassID)
    },
    remove() {
      AXIOS.delete('/offered-classes/'+this.newOfferedClassID)
        .then(response => {
          this.newOfferedClassID = null
          this.fetchOfferedClasses()
        }).catch(e => {
        alert(e.response.data.errors);
      })
    }
  }
}

</script>

<style scoped>
main {
  margin-top: 3%;
  grid-column: 1/-1;
  display: grid;
  grid-template-columns: 40fr 60fr;
  grid-template-rows: auto;
  grid-gap: 16px;
  padding: 30px 30px 10px;
  height: 100%;
}

#offered_class {
  height: 490px;
  overflow-y: auto;

  grid-column: 2/3;
  grid-row: 1/2;

  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
#offered_class ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

#offered_class li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #e1e1e1;
  background-color: #fff;
  transition: background-color 0.3s;
}

#offered_class li.placeholder {
  height: 50px;
  border-bottom: 1px solid #e1e1e1;
  background-color: #f9f9f9
}

#offered_class li:hover {
  background-color: #f0f0f0;
}

#offered_class::-webkit-scrollbar {
  width: 10px;
}

#offered_class::-webkit-scrollbar-track {
  background: #f1f1f1;
}

#offered_class::-webkit-scrollbar-thumb {
  background: #888;
}

#offered_class::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.noselect {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}


#content{
  margin: auto;
  padding: 20px;
}

h2 {
  margin-top: 20px;
  grid-column: 1 / -1;
  grid-row: 1/2;
}

label {
  margin-top: 20px;
}

#logo {
  position: absolute;
  top: 10px;
  right: 10px;
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


footer {
  display: flex;
  justify-content: space-between;
  padding: 10px 20px;
}

#removeContainer {
  grid-row: 2/3;
  grid-column: 2/3;
}

#logoutContainer {
  margin-top: 50px;
  grid-column: 1/2;
  grid-row: 2/3;
  text-align: center;
  justify-self: start;
  justify-content: center;
  align-items: center;
  align-self: end;
  gap: 10px;
}

button {
  padding: 10px 30px 10px 30px;
  margin-left: 0;
  width: fit-content;
  height: fit-content;
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

#previousPage {
  position: absolute;
  top: 20px;
  left: 20px;
  text-align: center;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 10px;
}

#offered_class li.is-selected {
  background-color: #D1A5F3;
  color: white;
}

input[type="time"] {
  border: 1px solid #ccc;
  width: 150px;
  outline: 2px solid #8e6a7e;
  border-radius: 5px;
  font-size: 16px;
  color: #333;
  padding: 8px 8px;
  background-color: #f8f8f8;
  transition: outline 0.3s ease, background-color 0.3s ease;
}
input[type="time"]:focus {
  outline: 3px solid #8a2be2;
  background-color: white;
}

input[type="date"] {
  border: 1px solid #ccc;
  width: 150px;
  outline: 2px solid #8e6a7e;
  border-radius: 5px;
  font-size: 16px;
  color: #333;
  padding: 8px 12px;
  background-color: #f8f8f8;
  transition: outline 0.3s ease, background-color 0.3s ease;
}
input[type="date"]:focus {
  outline: 3px solid #8a2be2;
  background-color: white;
}


</style>
