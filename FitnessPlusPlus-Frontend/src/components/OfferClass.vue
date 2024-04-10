<template>
  <div id="offerclass">
    <div id="logo">
      <p>FitnessPlusPlus</p>
      <img src="../assets/logo.png" alt="logo.png">
    </div>
    <h2>Create an Offered Class</h2>
    <form @submit.prevent>
      <div id="type">
        <p>Type</p>
        <input type="text">
      </div>
      <div id="description">
        <p>Description</p>
        <textarea id="description" name="description"></textarea>
      </div>
        <input id="submit" type="submit" v-bind:disabled="false"
                       @click="createAccount()" value="Save Changes">
        <button id="previous" type="button">Previous Page</button>
    </form>
    <button type="button" id="logout">Logout</button>
  </div>
</template>

<script>

</script>

<style>
  #offerclass {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 16px;
    margin: auto;
    padding: 20px;
  }

  #offerclass #logo {
    position: absolute;
    top: 10px;
    right: 10px;
    text-align: right;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 10px;
  }

  #offerclass #logo p {
    font-size: 24px;
    margin: 0;
  }

  #offerclass #logo img {
    width: 80px;
    height: auto;
  }

  #offerclass h2 {
    margin-top: 20px;
    grid-column: 1 / -1; /* Span the title across all columns */
  }

  #offerclass form {
    margin-top: 5%;
    grid-column: 1/-1;
    display: grid;
    grid-template-columns: 40fr 60fr;
    grid-template-rows: auto;
    padding: 30px;
  }

  #offerclass form p {
    text-align: left; /* Right align the text */
    align-self: start; /* Aligns the label vertically with the text area */
    margin: 0 0 0 5%;
    padding-bottom: 5px;

  }

  #offerclass #type {
    grid-column: 1/2;
    grid-row: 1/2;
  }

  #offerclass #description {
    grid-column: 2/3;
    grid-row: 1/4;
    padding-left: 30px;
  }

  #offerclass #submit {
    grid-column: 1/2;
    grid-row: 2/3;
  }

  #offerclass #previous {
    grid-column: 1/2;
    grid-row: 3/4;
  }

  #offerclass input[type="text"] {
    width: 90%;
    background-color: #F3ECDA;
    border: 2px solid #a67f8e; /* Set the default border color */
    padding: 8px 12px; /* Add some padding inside the text box */
    border-radius: 8px; /* Rounded corners */
    outline: none; /* Remove the default focus highlight */
    transition: border-color 0.3s, box-shadow 0.3s; /* Smooth transition for focus effect */
  }

  #offerclass input[type="text"]:focus {
    border-color: #B580e6; /* Darker border color on focus */
    box-shadow: 0 0 8px rgba(127, 34, 208, 0.5); /* Glowing effect on focus */
  }

  #offerclass textarea {
    width: 90%;
    padding: 8px 12px;
    resize: vertical;
    min-height: 50vh;
    background-color: #F3ECDA;
    border: 2px solid #a67f8e; /* Set the default border color */
    border-radius: 8px; /* Rounded corners */
    outline: none; /* Remove the default focus highlight */
    transition: border-color 0.3s, box-shadow 0.3s; /* Smooth transition for focus effect */
  }

  #offerclass textarea:focus {
    border-color: #B580e6; /* Darker border color on focus */
    box-shadow: 0 0 8px rgba(127, 34, 208, 0.5); /* Glowing effect on focus */
  }

  #offerclass button {
    padding: 10px 30px 10px 30px;
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

  #offerclass button:hover:not(:disabled) {
    background-color: #8a2be2;
  }

  #offerclass button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }

  #offerclass input[type="submit"] {
    padding: 10px 30px 10px 30px;
    margin-bottom: 10px;
    width: fit-content;
    height: fit-content;
    background-color: #8e6a7e;
    color: white;
    border: none;
    border-radius: 20px;
    font-size: 18px;
    cursor: pointer;
    justify-self: center;
    align-self: end;
  }

  #offerclass input[type="submit"]:hover:not(:disabled) {
    background-color: #8a2be2;
  }

  #offerclass input[type="submit"]:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }

  #offerclass #logout {
    position: absolute;
    bottom: 20px;
    left: 20px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
  }
</style>
