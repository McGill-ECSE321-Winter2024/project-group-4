<template>
 <div id="content">
   <header id="header">
     <div id="logo">
       <p>FitnessPlusPlus</p>
       <img src="../assets/logo.png"/>
     </div>
   </header>
   <h2>Promote User</h2>

   <main>
      <section id="clients">
        <ul>
          <li v-for="user in registered_user" :key="user.userId" :class="{ 'is-selected': user.username === selectedUsername }" class="noselect"
              @click="selectUser(user.username, user.userId)">
            {{ user.username }} - Role: {{ user.roleType}} | Email: {{user.email}} | ID: {{user.userId}}
          </li>
          <li v-for="(placeholder, index) in placeholders" :key="`placeholder-${index}`" class="placeholder noselect">
            &nbsp;
          </li>
        </ul>
      </section>
     <div id="promoteContainer">
       <button @click="promote" v-bind:disabled="!selectedUsername" id="promoteButton">Promote</button>
     </div>
     <div id="logoutContainer">
       <button @click="logout" id="logoutButton">Logout</button>
     </div>
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
  name: "PromoteUser",
  data() {
    return {
      registered_user: [],
      errors: [],
      desiredItemCount: 11,
      selectedUsername: null,
      selectedUserID: null
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
          this.$router.push('/instructor-view');

        }
      })
      .catch(e => {
        alert(e.message);
      })

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
    },
    previousPage() {
      this.$router.back()
    },
    promote() {
      if(this.selectedUsername === null) {
        alert("Please select a user to promote.");
        return;
      }

      AXIOS.post('/promote/', {
        userId: this.selectedUserID
      }, {})
        .then(response => {
          this.fetchRegisteredUser();
        })
        .catch(error => {
          console.error("Failed to promote user:", error.message);
          alert(error.response.data.errors);
        });

    },
    logout() {
      localStorage.clear()
      this.$router.push({ name: 'Home' });
    },
    selectUser(username, id) {
      if (this.selectedUsername === username) {
        this.selectedUsername = null
        this.selectedUserID = null;
      }
      else {
        this.selectedUsername = username;
        this.selectedUserID = id;
      }
    }
  }
};
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

#clients {
  max-height: 490px;
  overflow-y: auto;

  grid-column: 2/3;
  grid-row: 1/2;

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
  height: 50px;
  border-bottom: 1px solid #e1e1e1;
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

#promoteContainer {
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

#clients li.is-selected {
  background-color: #D1A5F3;
  color: white;
}

</style>
