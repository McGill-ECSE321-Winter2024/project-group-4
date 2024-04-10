<!--Visibility Owner-->
<!--View all Offered Classes and manage them-->

<template>
<div id="owner-page">
  <div id="offered-classes-container">
    <h4>Offered Classes</h4>
    <ul id="offered-classes-list">
      <!-- Classes will be populated here -->
    </ul>
  </div>
  
  <button id="logout-button">Logout</button>
  <button id="approve-class-button">Approve Class</button>
  <button id="promote-user-button">Promote User</button>
  <button id="save-changes-button">Save Changes</button>
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
  name: 'login',
  data () {
    return {
      offeredClass : [],
      errors: [],
      response: []
    }
  },

  created() {
    this.fetchOfferedClasses();
  },

  methods: {
   

    // Approve an offered class
    approveOfferedClass(offeredClassId) {
      const dto = { offeredClassId }; // The DTO structure expected by the API
      fetch('approve-offered-class', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(dto),
      })
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
        // Update the list of offered classes or handle UI changes
      })
      .catch((error) => {
        console.error('Error:', error);
      });
    }
  }
};
</script>


<style>
#offered-classes-container {
  height: 300px; /* Adjust as needed */
  overflow-y: auto; /* This will create a vertical scrollbar when content overflows */
}

body {
    margin: 0;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }

#offered-classes-list {
  /* Add styles for your list here */
}

button {
  /* Style your buttons here */
}

/* Additional styles for layout, positioning and colors */
</style>