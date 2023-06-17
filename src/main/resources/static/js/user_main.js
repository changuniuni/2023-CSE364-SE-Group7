$(document).ready(function() {
    // Handle professor link click
    $('.nav-item-professor').click(function(e) {
      e.preventDefault();
  
      // Send a GET request to fetch the professor information from the server
      $.ajax({
        url: 'http://localhost:8080/professors',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          console.log('Professors retrieved:', response);
          // Display the professor information in the user main content section
          showProfessorInformation(response);
        },
        error: function(error) {
          console.log('Error retrieving professors:', error);
          // Handle error response here
        }
      });
    });
  
    // Function to display the professor information
    function showProfessorInformation(professors) {
        // Clear the existing content in the user main content section
        $('.user-main-content').empty();
      
        // Create the table element
        const table = $('<table>').addClass('professor-table');
        const thead = $('<thead>');
        const tbody = $('<tbody>');
      
        // Create the table headers
        const headers = ['Name', 'Area', 'Topic', 'Email', 'Office'];
        const headerRow = $('<tr>');
        headers.forEach(function(header) {
          const th = $('<th>').text(header);
          headerRow.append(th);
        });
        thead.append(headerRow);
        table.append(thead);
      
        // Create the table rows with professor data
        professors.forEach(function(professor) {
          const { name, area, topic, email, office } = professor;
          const row = $('<tr>');
          const nameCell = $('<td>').text(name);
          const areaCell = $('<td>').text(area.join(', ')); // Join the area array with a comma separator
          const topicCell = $('<td>').text(topic);
          const emailCell = $('<td>').text(email);
          const officeCell = $('<td>').text(office);
          row.append(nameCell, areaCell, topicCell, emailCell, officeCell);
          tbody.append(row);
        });
      
        // Append the table to the user main content section
        table.append(tbody);
        $('.user-main-content').append(table);
      }
      
  });
  