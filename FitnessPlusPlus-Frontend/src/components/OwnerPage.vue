<template>
  <div id="owner-page">
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

    <button id="logout-button" @click="logout">Logout</button>
    <button id="promote-user-button" @click="promoteUser">Promote User</button>
    <button id="scheduled-classes-button" @click="scheduleClass">Schedule Class</button>

    <div id="logo">
      <p>FitnessPlusPlus</p>
      <img src="../assets/logo.png" alt="logo">
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
      this.$router.push({ name: 'Home' });
    },
    promoteUser() {
      this.$router.push({ name: 'PromoteUser' });
    },
    scheduleClass(){
      this.$router.push({name: 'ScheduleClasses'})
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

#offered-classes-container {
  width: 60%;
  max-height: 50vh;
  overflow-y: auto;
  margin: 20px 0;
  padding: 20px;
  background-color: #f5f5f5;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
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

.button-container {
  display: flex;
}

button {
  padding: 10px 20px;
  margin-left: 10px;
  margin-top: 5px;
  cursor: pointer;
  font-size: 1em;
  background-color: #8a2be2;
  color: white;
  border: none;
  border-radius: 20px;
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
</style>
