$(document).ready(function() {
  const urlParams = new URLSearchParams(location.search);

  const userId = urlParams.get('userid');
  const name = urlParams.get('name');

  $(".section-title").css('margin-top', '-40px', 'font-size', '60px');
  $(".logo-container").css('margin-bottom', '-18px');
  // Fetch the professors' information
  $.ajax({
    url: 'http://localhost:8080/courses',
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

      const logout = $('<div>').addClass('button-container');
      const logoutlink = $('<a>').attr('href', `index.html`, 'class',"btn btn-primary").text('LogOut');
      logout.append(logoutlink);
      
      $('.nav-list').append(newItem1, newItem2, newItem3, newItem4, logoutlink);

      // Display the course information in a table
      displayEmptyRecommend();
    },
    error: function(error) {
      console.log('Error:', error);
    }
  });
  
  const FilterClick = document.getElementById('getRecommend');
  FilterClick.addEventListener('click', function() {
    filterSelect = document.getElementById('recSelect');
    if(filterSelect.value != "none") {
      $.ajax({
        url: `http://localhost:8080/courses/recommend/${encodeURIComponent(filterSelect.value)}`,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          // Extract the relevant information from the response
          const courses = response._embedded.courses;
          const courseData = courses.map(function(course) {
            const { code, title, mandatory, type, desc } = course;
            return { code, title, mandatory, type, desc };
          });
    
          // Display the course information in a table
          displayRecommend(courseData);
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    }
    else {displayEmptyRecommend();}
  });

  const ReturnClick = document.getElementById('returnButton');
  ReturnClick.addEventListener('click', function() {
    returnSelect = document.getElementById('recSelect');
    if(returnSelect.value != "none") {
      $.ajax({
        url: `http://localhost:8080/courses/recommend/${encodeURIComponent(returnSelect.value)}`,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          // Extract the relevant information from the response
          const courses = response._embedded.courses;
          const courseData = courses.map(function(course) {
            const { code, title, mandatory, type, desc } = course;
            return { code, title, mandatory, type, desc };
          });
    
          // Display the course information in a table
          displayRecommend(courseData);
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    }
    else {displayEmptyRecommend();}
  });

  // Function to display the course information in a table
  function displayEmptyRecommend() {
    // Create the table element
    const table = $('<table>').addClass('course-table');
    const thead = $('<thead>');
    const tbody = $('<tbody>');
  
    // Create the table headers
    const headers = ['Course ID', 'Course Name', 'Course Mandatory', 'Course Area', 'Course Description', 'User Roadmap'];
    const headerRow = $('<tr>');
    headers.forEach(function(header) {
      const th = $('<th>').text(header);
      headerRow.append(th);
    });
    thead.append(headerRow);
    table.append(thead);

    // Append the table to the screen
    table.append(tbody);
    $('.recommend-content').empty().append(table);
    const returnButton = document.getElementById('returnButton');
    returnButton.style.display = 'none';
  }

  // Function to display the course information in a table
  function displayRecommend(courses) {
    // Create the table element
    const table = $('<table>').addClass('course-table');
    const thead = $('<thead>');
    const tbody = $('<tbody>');

    // Create the table headers
    const headers = ['Course ID', 'Course Name', 'Course Mandatory', 'Course Area', 'Course Description', 'User Roadmap'];
    const headerRow = $('<tr>');
    headers.forEach(function(header) {
      const th = $('<th>').text(header);
      headerRow.append(th);
    });
    thead.append(headerRow);
    table.append(thead);

    const disabledButtons = [];
    const abledButtons = [];

    // Create the table rows with course data, with filter applied
    courses.forEach(function(course) {
      const { code, title, mandatory, type, desc } = course;

      const row = $('<tr>');
      const codeCell = $('<td>').text(code).css({
        'color': 'blue',
        'cursor': 'pointer'
      }).addClass('course-id');
      const nameCell = $('<td>').text(title);
      const mandCell = $('<td>').text(mandatory);
      const areaCell = $('<td>').text(type);
      const descCell = $('<td>').text(desc);
      row.append(codeCell, nameCell, mandCell, areaCell, descCell);
      tbody.append(row);

      const buttonCell = $('<td>').css('text-align', 'center');
      let button = $('<button>').text('Add');

      $.ajax({
        url: `http://localhost:8080/users/${userId}/courses`,
        type: 'GET',
        dataType: 'json',
        success: function(c) {
            if(c.find(function(Id) {
            return Id.code === code;
            })){
            button.prop('disabled', true);
            }
        },
        error: function(error) {
            console.log('Error:', error);
        }
      });

      buttonCell.append(button);
      row.append(buttonCell);
      
      const courseMain = code.slice(0, 3);
      const courseSub = code.slice(3, 8);
      let courseId;
      switch (courseMain) {
        case "CSE":
          courseId = "1" + courseSub;
          break;
        case "ITP":
          courseId = "2" + courseSub;
          break;
        case "MTH":
          courseId = "3" + courseSub;
          break;
      }

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
    });
 
    // Append the table to the screen
    table.append(tbody);
    $('.recommend-content').empty().append(table);
    const returnButton = document.getElementById('returnButton');
    returnButton.style.display = 'none';

    // Attach click event to course id cells
    $('.course-id').click(function() {
      const courseMain = $(this).text().slice(0, 3);
      const courseSub = $(this).text().slice(3, 8);
      let courseId;
      switch (courseMain) {
        case "CSE":
          courseId = "1" + courseSub;
          break;
        case "ITP":
          courseId = "2" + courseSub;
          break;
        case "MTH":
          courseId = "3" + courseSub;
          break;
      }
      const apiUrl_1 = `http://localhost:8080/courses`;
      const apiUrl_2 = `http://localhost:8080/coursehistories/course/${courseId}`;

      $('.recommend-content').empty();

      $.ajax({
        url: apiUrl_1,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          // Extract the relevant information from the response
          const eCourses = response._embedded.courses;
          const eCourseData = eCourses.map(function(eCourse) {
            const { courseId, code, title, acadYear, openSmes, type, mandatory, prereq, desc } = eCourse;
            return { courseId, code, title, acadYear, openSmes, type, mandatory, prereq, desc };
          });

          displayDetailedCourse(eCourseData, courseId);

          $.ajax({
            url: apiUrl_2,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
              // Extract the relevant information from the response
              const histories = response._embedded.courseHistories;
              const historyData = histories.map(function(history) {
                const { openYear, openSmes, courseId, professorName } = history;
                return { openYear, openSmes, courseId, professorName };
              });
    
              displayCourseHistory(historyData);
            },
            error: function(error) {
              console.log('Error:', error);
            }
          });
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    });
  }

  // Function to display the detailed course in a table
  function displayDetailedCourse(eCourses, eCode) {
    // Create the table element
    const table = $('<table>').addClass('eCourse-table');
    const tbody = $('<tbody>');
    // Create the table rows with course data
    eCourses.forEach(function(eCourse) {
      const { courseId, code, title, acadYear, openSmes, type, mandatory, prereq, desc } = eCourse;
      if (eCode != courseId) {return;}
      let eachCell;
      let textCell;
      let row = $('<tr>');
      eachCell = $('<td>').text(code);
      textCell = $('<td>').text("Course ID").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(title);
      textCell = $('<td>').text("Course Title").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(acadYear);
      textCell = $('<td>').text("Recommended Grade to Take").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(openSmes);
      textCell = $('<td>').text("Allocated Semester").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(type);
      textCell = $('<td>').text("Course CSE Area").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(mandatory);
      textCell = $('<td>').text("Course Mandatory").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(prereq);
      textCell = $('<td>').text("Course Prerequisite").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
      row = $('<tr>');
      eachCell = $('<td>').text(desc);
      textCell = $('<td>').text("Course Description").css({
        'border-right': '1px solid #ccc'
      });
      row.append(textCell, eachCell);
      tbody.append(row);
    });
    // Append the table to the screen
    table.append(tbody).css({
      'border-collapse': 'separate'
    });
    $('.recommend-content').append(table);
    const returnButton = document.getElementById('returnButton');
    returnButton.style.display = 'block';
  }

  // Function to display the course information in a table
  function displayCourseHistory(histories) {
    // Create the table element
    const table = $('<table>').addClass('history-table');
    const thead = $('<thead>');
    const tbody = $('<tbody>');
  
    // Create the table headers
    const headers = ['Open Year', 'Open Semester', 'Course Code', 'Professor Name'];
    const headerRow = $('<tr>');
    headers.forEach(function(header) {
      const th = $('<th>').text(header);
      headerRow.append(th);
    });
    thead.append(headerRow);
    table.append(thead);
  
    // Create the table rows with course data
    histories.forEach(function(history) {
      const { openYear, openSmes, courseId, professorName } = history;
      const row = $('<tr>');
      const openYearCell = $('<td>').text(openYear);
      const openSmesCell = $('<td>').text(openSmes);
      const openProfCell = $('<td>').text(professorName);

      // Fetch the course name using courseId
      $.ajax({
        url: `http://localhost:8080/courses/${courseId}`,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          const openCode = response.code;
          const CourseIdCell = $('<td>').text(openCode);
          row.append(openYearCell, openSmesCell, CourseIdCell, openProfCell);
          tbody.append(row);
        },
        error: function(error) {
          console.log('Error:', error);
        }
      });
    });
  
    // Append the table to the screen
    table.append(tbody);
    $('.recommend-content').append(table);
    const returnButton = document.getElementById('returnButton');
    returnButton.style.display = 'block';
  }
});
  