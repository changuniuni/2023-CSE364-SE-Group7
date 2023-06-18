$(document).ready(function() {
  // Handle signup form submission
  $('#signup-form').submit(function(e) {
      e.preventDefault();

      // Create the user object
      const formData = {
        userid: $('#student-id').val(),
        name: $('#name').val()
      };
      

      // Send the data to the server using AJAX
      $.ajax({
        url: 'http://localhost:8080/users',
        type: 'POST',
        data: formData,
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