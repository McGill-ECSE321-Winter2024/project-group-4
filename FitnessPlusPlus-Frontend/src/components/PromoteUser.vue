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
        <!-- Clients List -->
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
      errors: []
    };
  },
  created() {

  },
  methods: {

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

#clients ul {

}

#clients li {

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
