<template>
 <div id="content">
   <header id="header">
     <div id="logo">
       <p>FitnessPlusPlus</p>
       <img src="../assets/logo.png"/>
     </div>
     <h2>Promote User</h2>
   </header>

   <main>
      <section id="clients">
        <ul>
          <li v-for="user in registered_user" :key="user.id">
            {{ user.username }} <!-- Assuming 'name' is the property you want to display -->
          </li>
          <li v-for="(placeholder, index) in placeholders" :key="`placeholder-${index}`" class="placeholder">
            &nbsp; <!-- Non-breaking space to ensure the item takes up space -->
          </li>
        </ul>
      </section>
      <section id="buttons">
        <!-- Save Changes and Previous Page Button-->
      </section>
   </main>

   <footer>
     <!-- Promote and Logout buttons -->
   </footer>

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
  name: "PromoteUser",
  data() {
    return {
      registered_user: [],
      errors: [],
      desiredItemCount: 11
    };
  },
  created() {
    this.fetchRegisteredUser();
  },
  computed: {
    placeholders() {
      const placeholdersCount = this.desiredItemCount - this.registered_user.length;
      return Array(placeholdersCount < 0 ? 0 : placeholdersCount).fill({});
    },
  },
  methods: {
    fetchRegisteredUser() {
      AXIOS.get('/registered-users/').then(response => {
        this.registered_user = response.data;
      }).catch(error => {
        this.errors.push(error.message || "Failed to load users");
      });
    }
  }
};
</script>

<style>

main {
  margin-top: 3%;
  grid-column: 1/-1;
  display: grid;
  grid-template-columns: 40fr 60fr;
  grid-template-rows: auto 60px auto;
  padding: 30px 30px 10px;
  height: 100%;
}

#clients {
  max-height: 500px;
  overflow-y: auto;

  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
#clients ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

#clients li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #e1e1e1;
  background-color: #fff;
  transition: background-color 0.3s;
}

#clients li.placeholder {
  height: 50px; /* Match the height of your user items */
  border-bottom: 1px solid #e1e1e1; /* Light border to keep consistent with user items */
  background-color: #f9f9f9
}

#clients li:hover {
  background-color: #f0f0f0;
}

#clients::-webkit-scrollbar {
  width: 10px;
}

#clients::-webkit-scrollbar-track {
  background: #f1f1f1;
}

#clients::-webkit-scrollbar-thumb {
  background: #888;
}

#clients::-webkit-scrollbar-thumb:hover {
  background: #555;
}


#content{
  grid-template-columns: 1fr 1fr;
  grid-gap: 16px;
  margin: auto;
  padding: 20px;
}

#header h1 {
  margin-top: 20px;
  grid-column: 1 / -1;
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

</style>
