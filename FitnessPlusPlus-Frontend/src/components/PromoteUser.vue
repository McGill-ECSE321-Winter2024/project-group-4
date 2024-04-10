<template>
 <div id="content">
   <header id="header">
     <h1>Promote User</h1>
     <div id="logo">
       <img src="../assets/logo.png"/>
     </div>
   </header>
 </div>
</template>

<style>

#header {
  display: flex;
  align-items: center;
  position: relative;
  padding: 1em;
}

#header::before {
  content: '';
  display: block;

  width: auto;
  height: 8rem;
}

#header h1 {
  margin: 0 auto;
  font-size: 3vw;
  line-height: 1;
}

#logo {
  position: absolute;
  right: 1em;
  top: 50%;
  transform: translateY(-50%);
}

  #logo img {
    height: 8vw;
    width: auto;
  }
</style>
