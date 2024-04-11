<template>
  <div id="owner-page">
    <div id="logo">
      <p>FitnessPlusPlus</p>
      <img src="../assets/logo.png" alt="logo">
    </div>

    <div id="content">
      <div id="offered-classes-container">
        <h4>Offered Classes</h4>
        <ul id="offered-classes-list">
          <li v-for="(offeredClass, index) in offered_classes" :key="offeredClass.offeredClassId"
              :class="{ 'approved': offeredClass.approved, 'not-approved': !offeredClass.approved }">
            <div class="class-description">
            <span :style="{ color: offeredClass.approved ? 'green' : 'red' }">
              {{ offeredClass.classType }} - {{ offeredClass.description }}
            </span>
            </div>
            <div class="button-container">
              <button v-if="!offeredClass.approved" @click="approveOfferedClass(offeredClass)">
                Approve
              </button>
              <button @click="removeOfferedClass(offeredClass.offeredClassId)">
                Remove
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div id="logoutContainer">
        <button @click="logout" id="logoutButton">Logout</button>
      </div>
      <button id="promote-user-button" @click="promoteUser">Promote User</button>
      <button id="scheduled-classes-button" @click="scheduleClass">Schedule Class</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import config from '../../config';

const frontendUrl = `http://${config.dev.host}:${config.dev.port}`;
const backendUrl = `http://${config.dev.backendHost}:${config.dev.backendPort}`;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  name: 'OwnerPage',
  data() {
    return {
      offered_classes: [],
      errors: []
    };
  },
  created() {
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
          this.$router.push('/Dashboard');

        } else if (response.data.roleType === "Instructor") {
          this.$router.push('/ManageSchedule');

        }
      })
      .catch(e => {
        alert(e.message);
      })

    this.fetchOfferedClasses();
  },
  methods: {
    fetchOfferedClasses() {
      AXIOS.get('/offered-classes').then(response => {
        this.offered_classes = response.data;
      }).catch(error => {
        this.errors.push(error.message || "Failed to fetch classes.");
      });
    },
    approveOfferedClass(offeredClass) {
    const dto = {
      offeredClassId: offeredClass.offeredClassId,
      // Assuming the backend toggles the approval status and just needs the ID
      // If you need to send more data, add it here
      };
      AXIOS.post('/approve-offered-class', dto)
      .then(response => {
        // Assuming the response contains the updated class data
        const updatedClass = response.data;
        const index = this.offered_classes.findIndex(c => c.offeredClassId === offeredClass.offeredClassId);
        if (index !== -1) {
          this.$set(this.offered_classes, index, updatedClass);
          // Vue.set(this.offered_classes, index, updatedClass); // Alternative syntax
        }
      })
      .catch(error => {
        console.error(error);
        this.errors.push("Failed to approve class: " + (error.response.data.message || error.message));
      });
    },
    removeOfferedClass(offeredClassId) {
      AXIOS.delete(`/offered-classes/${offeredClassId}`)
      .then(() => {
        this.offered_classes = this.offered_classes.filter(classItem => classItem.offeredClassId !== offeredClassId);
      })
      .catch(error => {
        this.errors.push(error.message || "Failed to remove class.");
      });
    },
    logout() {
      localStorage.clear()
      this.$router.push({ name: 'Home' });
    },
    promoteUser() {
      this.$router.push('/promote-user');
    },
    scheduleClass(){
      this.$router.push('/schedule-classes')
    }
  }
};
</script>

<style scoped>
#owner-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
  position: relative;
}

#content {
  display: grid;
  grid-template-columns: 1fr auto 1fr;  /* Updated for simplicity and responsiveness */
  grid-template-rows: auto 50px 50px; /* Ensure there's enough space for three rows */
  grid-template-areas:
    ". tabl ."
    ". . promote"
    "lout . schedule"; /* Updated for clarity and to match IDs correctly */
  grid-gap: 20px;
  margin-top: 100px;
  height: 90vh;
  width: 95vw;
}

#offered-classes-container {
  grid-area: tabl;
  max-height: 50vh;
  overflow-y: auto;
  width: 60vw;
  padding: 20px;
  background-color: #f5f5f5;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  align-self: center;
  justify-self: center;
}

#offered-classes-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

#offered-classes-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.class-description {
  flex-grow: 1;
  text-align: left; /* Align text to the left */
  display: flex;
  flex-direction: column;
  justify-content: center; /* Centers the text vertically in its container */
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

#owner-page #logo {
  position: absolute;
  top: 10px;
  right: 10px;
  text-align: right;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
}

#owner-page #logo p {
  font-size: 24px;
  margin: 0;
}

#owner-page #logo img {
  width: 80px;
  height: auto;

}

#logoutContainer {
  text-align: center;
  justify-self: start;
  justify-content: center;
  align-items: center;
  align-self: end;
  grid-area: lout;
}

#promote-user-button {
  grid-area: promote;
  text-align: center;
  justify-self: end;
  justify-content: center;
  align-items: center;
  align-self: end;

}

#scheduled-classes-button {
  grid-area: schedule;
  text-align: center;
  justify-self: end;
  justify-content: center;
  align-items: center;
  align-self: end;

}


</style>
