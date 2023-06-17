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
        const nameCell = $('<td>').text(name);
        const topicCell = $('<td>').text(topic);
        const emailCell = $('<td>').text(email);
        row.append(nameCell, topicCell, emailCell);
        tbody.append(row);
      });
  
      // Append the table to the screen
      table.append(tbody);
      $('.user-main-content').append(table);
    }
  });
  