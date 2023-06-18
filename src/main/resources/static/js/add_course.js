$(document).ready(function() {
    // Fetch the professors' information
    const urlParams = new URLSearchParams(location.search);

    const userId = urlParams.get('userid');
    const name = urlParams.get('name');
    $(".section-title").css('margin-top', '-40px', 'font-size', '60px');
    $(".logo-container").css('margin-bottom', '-18px');
    $.ajax({
      url: 'http://localhost:8080/coursehistories',
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

    const FilterClick = document.getElementById('checkFilter');
    FilterClick.addEventListener('click', function() {
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
    
          // Display the course information in a table
          displayCourseHistoryTable(courseData);
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    });


    // Display user information
    function displayCourseHistoryTable(courses) {
      const radioButtons = document.querySelectorAll('input[name="filter"]');
      let selectedFilter;
      radioButtons.forEach(radioButton => {
        if (radioButton.checked) {
          selectedFilter = radioButton.value;
        }
      });

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
        
        courses.forEach(async function(course) {
            const { openYear, courseId, openSmes, professorName } = course;
            var type;
            var code;
            var prereq;
            var acadYear;

            try {
              const response = await new Promise((resolve, reject) => {
                $.ajax({
                  url: `http://localhost:8080/courses/${courseId}`,
                  type: 'GET',
                  dataType: 'json',
                  success: resolve,
                  error: reject
                });
              });
              type = response.type;
              code = response.code;
              prereq = response.prereq;
              acadYear = response.acadYear;

              let filterSelect;
              switch (selectedFilter) {
                case "cid":
                  filterSelect = document.getElementById('cidInput');
                  if(filterSelect.value != "" && !code.includes(filterSelect.value)) {return;}
                  break;
                case "acy":
                  console.log('Error:', code);
                  filterSelect = document.getElementById('acySelect');
                  if(filterSelect.value != "none" && filterSelect.value != acadYear) {return;}
                  break;
                case "ays":
                  filterSelect = document.getElementById('aysSelect');
                  const filterYear = filterSelect.value.slice(0, filterSelect.value.length-1);
                  const filterSmes = parseInt(filterSelect.value.slice(filterSelect.value.length-1, filterSelect.value.length));
                  if(filterYear != "none" && filterYear != acadYear) {return;}
                  if(filterSmes != 0 && filterSmes != openSmes) {return;}
                  break;
                case "car":
                  filterSelect = document.getElementById('carSelect');
                  if(filterSelect.value != "none" && filterSelect.value != type) {return;}
                  break;
                case "pre":
                  filterSelect = document.getElementById('preInput');
                  let isFound = 0;
                  prereq.forEach(function(courseCode) {
                    if(courseCode == "0") {return;}
                    const courseMain = courseCode.slice(0, 1);
                    const courseSub = courseCode.slice(1, 4);
                    let Id;
                    switch (courseMain) {
                      case "1":
                        Id = "CSE" + courseSub;
                        break;
                      case "2":
                        Id = "ITP" + courseSub;
                        break;
                      case "3":
                        Id = "MTH" + courseSub;
                        break;
                    }
                    if(Id.includes(filterSelect.value)) {
                      isFound = 1;
                    }
                  });
                  if(filterSelect.value != "" && isFound == 0) {return;}
                  break;
              }
            } catch (error) {
              console.log('Error:', error);
            }
            
            const row = $('<tr>');
            const openYearCell = $('<td>').text(openYear);
            const openSmesCell = $('<td>').text(openSmes);
            const professorNameCell = $('<td>').text(professorName);
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
                            success: function(response1) {
                            console.log('Course added to user course list:', response1);

                            tbody.each(function() {
                              const row = $(this);
                              row.find('tr').each(function() {
                                const thirdCell = $(this).find('td:eq(2)');
                            
                                if (thirdCell.text() === response1.code) {
                                  const lastButton = $(this).find('button:last-child');
                                  lastButton.prop('disabled', true);
                                }
                              });
                            });
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
        $('.add-course-content').empty().append(table);
      }
    });