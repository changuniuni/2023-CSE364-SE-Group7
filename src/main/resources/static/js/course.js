$(document).ready(function() {
    // Fetch the professors' information
    $.ajax({
      url: 'http://localhost:8080/courses',
      type: 'GET',
      dataType: 'json',
      success: function(response) {
        // Extract the relevant information from the response
        const courses = response._embedded.courses;
        const courseData = courses.map(function(course) {
          const { code, title, type, desc } = course;
          return { code, title, type, desc };
        });
  
        // Display the professor information in a table
        displayCourseTable(courseData);
      },
      error: function(error) {
        console.log('Error:', error);
      }
    });
  
    // Function to display the professor information in a table
    function displayCourseTable(courses) {
      // Create the table element
      const table = $('<table>').addClass('course-table');
      const thead = $('<thead>');
      const tbody = $('<tbody>');
  
      // Create the table headers
      const headers = ['Course ID', 'Course Name', 'Course Area', 'Course Description'];
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
      });
  
      // Append the table to the screen
      table.append(tbody);
      $('.course-content').append(table);
    }
  });
  