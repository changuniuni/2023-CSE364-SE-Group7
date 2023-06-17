$(document).ready(function() {
    // Handle login form submission
    $('#login-form').submit(function(e) {
      e.preventDefault();
  
      // Get the form data
      const studentId = $('#student-id').val();
      const name = $('#name').val();
  
      // Send a GET request to fetch the list of users from the server
      $.ajax({
        url: 'http://localhost:8080/users',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          console.log('Users retrieved:', response);
  
          // Find the user with the matching student ID
          const user = response.find(function(user) {
            return user.id === studentId;
          });
  
          if (user) {
            console.log('Login successful:', user);
            // Redirect to the user_main page
            window.location.href = 'user_main.html';
          } else {
            console.log('Login failed: User not found');
            // Show the login failure screen
            $('#login-form').hide();
            $('#login-failure-screen').show();
          }
        },
        error: function(error) {
          console.log('Error retrieving users:', error);
          // Handle error response here
        }
      });
    });
  });
  