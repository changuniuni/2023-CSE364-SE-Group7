$(document).ready(function() {
  
  const userId = getCookie('userid');
  $.ajax({
    url: `http://localhost:8080/users/${userId}`,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
      displayUserInfo(response);
    },
    error: function(error) {
      console.log('Error retrieving user information:', error);
      // Handle error response here
    }
  });
  // Display user information
    function displayUserInfo(user) {
      const userInfoElement = $('<div>').addClass('user-info');
      const userIdElement = $('<p>').text('Student ID: ' + user.id);
      const userNameElement = $('<p>').text('Student Name: ' + user.name);
      
      userInfoElement.append(userIdElement, userNameElement);
      
      $('.user-main-content').append(userInfoElement);
      // Display the professor information in a table
      displayUserCourseTable(user.courseList);
    }

    function displayUserCourseTable(courses) {
      const table = $('<table>').addClass('course-table');
      const thead = $('<thead>');
      const tbody = $('<tbody>');
  
      // Create the table headers
      const headers = ['Course ID', 'Course Name', 'Course Area', 'Course Description', 'Button'];
      const headerRow = $('<tr>');
      headers.forEach(function(header) {
        const th = $('<th>').text(header);
        headerRow.append(th);
      });
      thead.append(headerRow);
      table.append(thead);
  
      // Create the table rows with professor data
      courses.forEach(function(course) {
        const { code, title, type, desc } = course;
        const row = $('<tr>');
        const idCell = $('<td>').text(code);
        const nameCell = $('<td>').text(title);
        const areaCell = $('<td>').text(type);
        const descCell = $('<td>').text(desc);
        row.append(idCell, nameCell, areaCell, descCell);
        tbody.append(row);

        const buttonCell = $('<td>').css('text-align', 'center');
        const button = $('<button>').text('Remove');
        buttonCell.append(button);
        row.append(buttonCell);

        button.on('click', function() {
          const hide = $(this).closest('tr');
          $.ajax({
              url: `http://localhost:8080/users/${userId}/courses/${course.courseId}`,
              type: 'DELETE',
              success: function(response) {
                console.log('Course added to user course list:', response);
                hide.hide();
              },
              error: function(error) {
                console.log('Error adding course to user course list:', error);
              }
          });
        });
      });
  
      // Append the table to the screen
      table.append(tbody);
      $('.user-main-content').append(table);
    }


  // Get the value of a cookie by name
    function getCookie(name) {
      var nameEQ = name + "=";
      var ca = document.cookie.split(';');
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
      }
      return null;
    }
});