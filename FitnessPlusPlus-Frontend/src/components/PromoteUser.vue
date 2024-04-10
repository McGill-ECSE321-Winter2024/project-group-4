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

   </main>

 </div>
</template>

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
