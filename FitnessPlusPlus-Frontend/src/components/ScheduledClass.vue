<template>
<div class="container">
  <!-- Left Panel -->
  <div class="left-panel">
    <h2>My Scheduled Classes</h2>
    <table>
      <thead>
        <tr>
          <th>Type</th>
          <th>Date/Time</th>
        </tr>
      </thead>
      <tbody>
        <!-- Class entries -->
      </tbody>
    </table>
    <div class="button-group">
      <button>Logout</button>
      <button>Cancel Class</button>
    </div>
  </div>

  <!-- Right Panel -->
  <div class="right-panel">
    <h2>My Weekly Scheduled Classes</h2>
    <h3>January 2024</h3>
    <table class="calendar-table">
      <thead>
        <tr>
          <th>Sunday</th>
          <th>Monday</th>
          <th>Tuesday</th>
          <th>Wednesday</th>
          <th>Thursday</th>
          <th>Friday</th>
          <th>Saturday</th>
        </tr>
      </thead>
      <tbody>
        <!-- Week rows -->
        <tr>
          <!-- Days -->
        </tr>
        <!-- More week rows as needed -->
      </tbody>
    </table>
    <div class="button-group">
      <button>Create an Offered Class</button>
      <button>Manage my classes</button>
    </div>
    <div class="footer-link">
    </div>
  </div>
</div>
</template>


<script>
    export default {
        data() {
            return {

            }
        }
    }
</script>

<style scoped>
  .container {
    display: flex;
    justify-content: space-between;
    padding: 20px;
  }
  .left-panel, .right-panel {
    width: 48%;
  }
  table {
    width: 100%;
    border-collapse: collapse;
  }
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    padding: 10px;
    text-align: left;
  }
  .button-group {
    text-align: center;
    padding: 10px 0;
  }
  .button-group button {
    margin-right: 10px;
    padding: 5px 10px;
  }
  .calendar-table {
    margin-top: 20px;
  }
  .calendar-table th {
    background-color: #f2f2f2;
  }
  .calendar-table td {
    height: 50px;
  }
  .footer-link {
    text-align: right;
    font-size: 12px;
    padding-top: 10px;
  }
</style>
