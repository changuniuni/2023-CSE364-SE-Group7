$(document).ready(function() {
    // Handle signup button click event
    $('#signup-button').click(function(e) {
      e.preventDefault();
      loadContent('signup-form.html');
    });
  
    // Handle login button click event
    $('#login-button').click(function(e) {
      e.preventDefault();
      loadContent('login-form.html');
    });
  
    // Function to load content into the content container
    function loadContent(url) {
      $.ajax({
        url: url,
        type: 'GET',
        dataType: 'html',
        success: function(response) {
          $('#content-container').html(response);
        },
        error: function(error) {
          console.log('Error loading content:', error);
        }
      });
    }
  });
  