<template>
    <div class="signup-form">
      <h2>Sign Up</h2>
      <form @submit.prevent="submitForm">
        <div>
          <label for="studentId">Student ID:</label>
          <input type="text" id="studentId" v-model="studentId" required>
        </div>
        <div>
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="name" required>
        </div>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        studentId: '',
        name: ''
      };
    },
    methods: {
      submitForm() {
        const userData = {
          id: this.studentId,
          name: this.name
        };
  
        // Send the data to the Java Spring backend using POST API
        fetch('http://localhost:8080/users', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(userData)
        })
          .then(response => {
            if (response.ok) {
              // Success, do something
              console.log('Signup successful');
            } else {
              // Error, handle accordingly
              console.log('Signup failed');
            }
          })
          .catch(error => {
            // Handle error
            console.log('An error occurred:', error);
          });
      }
    }
  };
  </script>
  
  <style scoped>
  .signup-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
  }
  
  form {
    display: flex;
    flex-direction: column;
    width: 300px;
  }
  
  label {
    margin-bottom: 5px;
  }
  
  input {
    padding: 5px;
    margin-bottom: 10px;
  }
  
  button {
    padding: 10px 20px;
    font-size: 16px;
  }
  </style>
  