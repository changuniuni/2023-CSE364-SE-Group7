$(document).ready(function() {
    // Handle signup form submission
    $('#signup-form').submit(function(e) {
      e.preventDefault();
  
      // Get the form data
      const studentId = $('#student-id').val();
      const name = $('#name').val();
  
      // Create the user object
      const user = {
        id: studentId,
        name: name
      };
  
      // Send the data to the server using AJAX
      $.ajax({
        url: 'http://localhost:8080/users',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function(response) {
          console.log('User created:', response);
          // Redirect to the home page
          window.location.href = 'index.html';
        },
        error: function(error) {
          console.log('Error creating user:', error);
          // Handle error response here
        }
      });
    });
  });
  