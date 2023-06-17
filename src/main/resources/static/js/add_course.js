$(document).ready(function() {
    // Fetch the professors' information
    $.ajax({
      url: 'http://localhost:8080/coursehistories',
      type: 'GET',
      dataType: 'json',
      success: function(response) {
        // Extract the relevant information from the response
        const courses = response._embedded.courseHistories;
        const courseData = courses.map(function(course) {
          const { openYear, openSmes, courseId, professorName } = course;
          return { openYear, openSmes, courseId, professorName };
        });
  
        // Display the professor information in a table
        displayCourseHistoryTable(courseData);
      },
      error: function(error) {
        console.log('Error:', error);
      }
    });
    // Display user information
    function displayCourseHistoryTable(courses) {
        // Create the table element
        const table = $('<table>').addClass('course-histories-table');
        const thead = $('<thead>');
        const tbody = $('<tbody>');
    
        // Create the table headers
        const headers = [ 'Open Year', 'Open Semester', 'Course Id', 'Course Name', 'Professor Name', 'Add to Course List' ];
        const headerRow = $('<tr>');
        headers.forEach(function(header) {
          const th = $('<th>').text(header);
          headerRow.append(th);
        });
        thead.append(headerRow);
        table.append(thead);
        
        const disabledButtons = [];
        const abledButtons = [];
        
        courses.forEach(function(course) {
            const { openYear, courseId, openSmes, professorName } = course;
            const row = $('<tr>');
            const openYearCell = $('<td>').text(openYear);
            const openSmesCell = $('<td>').text(openSmes);
            const professorNameCell = $('<td>').text(professorName);
            const userId = getCookie('userid');
            // Fetch the course name using courseId
            $.ajax({
                url: `http://localhost:8080/courses/${courseId}`,
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    const courseName = response.title;
                    const courseCode = response.code;
                    const courseNameCell = $('<td>').text(courseName);
                    const courseCodeCell = $('<td>').text(courseCode);
                    row.append(openYearCell, openSmesCell, courseCodeCell, courseNameCell, professorNameCell);
                    tbody.append(row);
                    // Create the button element
                    const buttonCell = $('<td>').css('text-align', 'center');
                    let button = $('<button>').text('Add');

                    $.ajax({
                        url: `http://localhost:8080/users/${userId}/courses`,
                        type: 'GET',
                        dataType: 'json',
                        success: function(c) {
                            if(c.find(function(Id) {
                            return Id.courseId === courseId;
                            })){
                            button.prop('disabled', true);
                            }
                        },
                        error: function(error) {
                            console.log('Error:', error);
                        }
                    });
                    if(button.is(':disabled')) {
                        disabledButtons.push({ row: row, button: button });

                    }
                    else {
                        abledButtons.push({ row: row, button: button });

                    }

                    buttonCell.append(button);
                    row.append(buttonCell);

                    button.on('click', function() {
                        $.ajax({
                            url: `http://localhost:8080/users/${userId}/courses/${courseId}`,
                            type: 'POST',
                            success: function(response) {
                            console.log('Course added to user course list:', response);
                        
                            button.prop('disabled', true);
                            },
                            error: function(error) {
                            console.log('Error adding course to user course list:', error);
                            }
                        });
                    });
                },
                error: function(error) {
                console.log('Error:', error);
                }
            });
        });

        // disabledButtons.sort(function(a, b) {
        //     const openyearA = parseInt($(a.row).find('td:nth-child(1)').text());
        //     const openyearB = parseInt($(b.row).find('td:nth-child(1)').text());
        //     if(openyearA === openyearB) {
        //         const openSmesA = $(a.row).find('td:nth-child(2)').text();
        //         const openSmesB = $(b.row).find('td:nth-child(2)').text();
        //         if(openSmesA === openSmesB) {
        //             const courseCodeA = $(a.row).find('td:nth-child(3)').text();
        //             const courseCodeB = $(b.row).find('td:nth-child(3)').text();
        //             return courseCodeA.localeCompare(courseCodeB);
        //         }
        //         else {
        //             return openSmesA.localeCompare(openSmesB);
        //         }
        //     }
        //     else {
        //         return openyearA - openyearB;
        //     }
        // });

        // abledButtons.sort(function(a, b) {
        //     const openyearA = parseInt($(a.row).find('td:nth-child(1)').text());
        //     const openyearB = parseInt($(b.row).find('td:nth-child(1)').text());
        //     if(openyearA === openyearB) {
        //         const openSmesA = $(a.row).find('td:nth-child(2)').text();
        //         const openSmesB = $(b.row).find('td:nth-child(2)').text();
        //         if(openSmesA === openSmesB) {
        //             const courseCodeA = $(a.row).find('td:nth-child(3)').text();
        //             const courseCodeB = $(b.row).find('td:nth-child(3)').text();
        //             return courseCodeA.localeCompare(courseCodeB);
        //         }
        //         else {
        //             return openSmesA.localeCompare(openSmesB);
        //         }
        //     }
        //     else {
        //         return openyearA - openyearB;
        //     }
        // });
            
        // abledButtons.forEach(function(item) {
        //     const { row } = item;
        //     tbody.append(row);
        // });


        // disabledButtons.forEach(function(item) {
        //     const { row } = item;
        //     tbody.append(row);
        // });

        // Append the table to the screen
        table.append(tbody);
        $('.add-course-content').append(table);
      }

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