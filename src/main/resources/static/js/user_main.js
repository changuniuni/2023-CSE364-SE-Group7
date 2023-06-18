$(document).ready(function() {
  const urlParams = new URLSearchParams(location.search);

  const userId = urlParams.get('userid');
  const name = urlParams.get('name');

  $.ajax({
    url: `http://localhost:8080/users/${userId}`,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
      const newItem1 = $('<li>').addClass('nav-item');
      const newItem2 = $('<li>').addClass('nav-item');
      const newItem3 = $('<li>').addClass('nav-item');
      const newItem4 = $('<li>').addClass('nav-item');

      const newLink1 = $('<a>').attr('href', `user_main.html?userid=${userId}&name=${name}`).text('UserProfile');
      const newLink2 = $('<a>').attr('href', `professor.html?userid=${userId}&name=${name}`).text('Professor');
      const newLink3 = $('<a>').attr('href', `course.html?userid=${userId}&name=${name}`).text('Course');
      const newLink4 = $('<a>').attr('href', `recommend.html?userid=${userId}&name=${name}`).text('Recommend');
      
      newItem1.append(newLink1);
      newItem2.append(newLink2);
      newItem3.append(newLink3);
      newItem4.append(newLink4);
      
      const logout = $('<li>').addClass('button-container');
      const logoutlink = $('<button>').attr('href', `index.html`).text('LogOut');
      logout.append(logoutlink);
      
      $('.nav-list').append(newItem1, newItem2, newItem3, newItem4, logoutlink);

      const button = $('<a>').attr('href', `add_course.html?userid=${userId}&name=${name}`).addClass('btn btn-primary').text('Add Course');
      $('.button-container').append(button);

      displayUserInfo(response);
    },
    error: function(error) {
      console.log('Error retrieving user information:', error);
      // Handle error response here
    }
  });
  // Display user information
    function displayUserInfo(user) {
      const userInfoElement = $('<div>').addClass('user-info').css({
        'font-size': '18px'
      });
      const userIdElement = $('<p>').text('Student ID: ' + user.id).css('margin-bottom', '1px');
      const userNameElement = $('<p>').text('Student Name: ' + user.name).css('margin-top', '1px');
      
      userInfoElement.append(userIdElement, userNameElement);
      $(".section-title").css('margin-top', '-18px')
      $(".logo-container").css('margin-bottom', '-18px');
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
      $('.user-main-content').append($('<div>').addClass('user-info').append($('<p>').text(' ')));
      $('.user-main-content').append($('<caption>').text('Taken Course Information').css({
        'align-items': 'center',
        'font-size': '20px'
      })).css({
        'display': 'grid'
      });
      $('.user-main-content').append(table);
    }
});