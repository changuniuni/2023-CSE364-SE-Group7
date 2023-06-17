$(document).ready(function() {
    // Fetch the professors' information
  $.ajax({
    url: 'http://localhost:8080/professors',
    type: 'GET',
    dataType: 'json',
    success: function(response) {
      // Extract the relevant information from the response
      const professors = response._embedded.professors;
      const professorData = professors.map(function(professor) {
        const { name, topic, email } = professor;
        return { name, topic, email };
      });
  
      // Display the professor information in a table
      displayProfessorTable(professorData);
    },
    error: function(error) {
      console.log('Error:', error);
    }
  });
  
  // Function to display the professor information in a table
  function displayProfessorTable(professors) {
    // Create the table element
    const table = $('<table>').addClass('professor-table');
    const thead = $('<thead>');
    const tbody = $('<tbody>');
  
    // Create the table headers
    const headers = ['Name', 'Topic', 'Email'];
    const headerRow = $('<tr>');
    headers.forEach(function(header) {
      const th = $('<th>').text(header);
      headerRow.append(th);
    });
    thead.append(headerRow);
    table.append(thead);
  
    // Create the table rows with professor data
    professors.forEach(function(professor) {
      const { name, topic, email } = professor;
      const row = $('<tr>');
      const nameCell = $('<td>').text(name).addClass('professor-name');
      const topicCell = $('<td>').text(topic);
      const emailCell = $('<td>').text(email);
      row.append(nameCell, topicCell, emailCell);
      tbody.append(row);
    });
    
    // Append the table to the screen
    table.append(tbody);
    $('.user-main-content').append(table);
  
    // Attach click event to professor name cells
    $('.professor-name').click(function() {
      const professorName = $(this).text();
      const apiUrl = `http://localhost:8080/professors/search/${encodeURIComponent(professorName)}/courses`;
  
      // Fetch the course information for the clicked professor
      $.ajax({
        url: apiUrl,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          // Extract the relevant information from the response
          const courses = response._embedded.courseHistories;
          const courseData = courses.map(function(course) {
            const { openYear, courseId, openSmes } = course;
            return { openYear, courseId, openSmes };
          });
  
          // Display the course information in a table
          displayCourseTable(courseData);
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    });
  }
  
  // Function to display the course information in a table
  function displayCourseTable(courses) {
    // Create the table element
    const table = $('<table>').addClass('course-table');
    const thead = $('<thead>');
    const tbody = $('<tbody>');
  
    // Create the table headers
    const headers = ['Open Year', 'Course ID', 'Open Semester', 'Course Name'];
    const headerRow = $('<tr>');
    headers.forEach(function(header) {
      const th = $('<th>').text(header);
      headerRow.append(th);
    });
    thead.append(headerRow);
    table.append(thead);
  
    // Create the table rows with course data
    courses.forEach(function(course) {
      const { openYear, courseId, openSmes } = course;
      const row = $('<tr>');
      const openYearCell = $('<td>').text(openYear);
      const courseIdCell = $('<td>').text(courseId);
      const openSmesCell = $('<td>').text(openSmes);
  
      // Fetch the course name using courseId
      $.ajax({
        url: `http://localhost:8080/courses/${courseId}`,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          const courseName = response.title;
          const courseId = response.code;
          const courseNameCell = $('<td>').text(courseName);
          const courseIdCell = $('<td>').text(courseId);
          row.append(openYearCell, courseIdCell, openSmesCell, courseNameCell);
          tbody.append(row);
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    });
  
    // Append the table to the screen
    table.append(tbody);
    $('.user-main-content').empty().append(table);
  }
});
  
