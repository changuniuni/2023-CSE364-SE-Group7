$(document).ready(function() {
  const urlParams = new URLSearchParams(location.search);

  const userId = urlParams.get('userid');
  const name = urlParams.get('name');

  // Fetch the professors' information
  $.ajax({
    url: 'http://localhost:8080/courses',
    type: 'GET',
    dataType: 'json',
    success: function(response) {
      const newItem1 = $('<li>').addClass('nav-item');
      const newItem2 = $('<li>').addClass('nav-item');
      const newItem3 = $('<li>').addClass('nav-item');
      const newLink1 = $('<a>').attr('href', `user_main.html?userid=${userId}&name=${name}`).text('Home');
      const newLink2 = $('<a>').attr('href', `professor.html?userid=${userId}&name=${name}`).text('Professor');
      const newLink3 = $('<a>').attr('href', `course.html?userid=${userId}&name=${name}`).text('Course');

      newItem1.append(newLink1);
      newItem2.append(newLink2);
      newItem3.append(newLink3);

      $('.nav-list').append(newItem1, newItem2, newItem3);

      // Extract the relevant information from the response
      const courses = response._embedded.courses;
      const courseData = courses.map(function(course) {
        const { code, title, type, desc } = course;
        return { code, title, type, desc };
      });

      // Display the course information in a table
      displayCourseCatalog(courseData);
    },
    error: function(error) {
      console.log('Error:', error);
    }
  });
  
  const FilterClick = document.getElementById('checkFilter');
  FilterClick.addEventListener('click', function() {
    $.ajax({
      url: 'http://localhost:8080/courses',
      type: 'GET',
      dataType: 'json',
      success: function(response) {
        // Extract the relevant information from the response
        const courses = response._embedded.courses;
        const courseData = courses.map(function(course) {
          const { code, title, type, desc, acadYear, openSmes, prereq } = course;
          return { code, title, type, desc, acadYear, openSmes, prereq };
        });
  
        // Display the course information in a table
        displayCourseCatalog(courseData);
      },
      error: function(error) {
        console.log('Error:', error);
      }
    });
  });

  // Function to display the course information in a table
  function displayCourseCatalog(courses) {
    const radioButtons = document.querySelectorAll('input[name="filter"]');
    let selectedFilter;
    radioButtons.forEach(radioButton => {
      if (radioButton.checked) {
        selectedFilter = radioButton.value;
      }
    });

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

    // Create the table rows with course data
    courses.forEach(function(course) {
      const { code, title, type, desc, acadYear, openSmes, prereq } = course;
      let filterSelect;
      switch (selectedFilter) {
        case "cid":
          filterSelect = document.getElementById('cidInput');
          if(filterSelect.value != "" && !code.includes(filterSelect.value)) {return;}
          break;
        case "acy":
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
            let courseId;
            switch (courseMain) {
              case "1":
                courseId = "CSE" + courseSub;
                break;
              case "2":
                courseId = "ITP" + courseSub;
                break;
              case "3":
                courseId = "MTH" + courseSub;
                break;
            }
            if(courseId.includes(filterSelect.value)) {
              isFound = 1;
            }
          });
          if(filterSelect.value != "" && isFound == 0) {return;}
          break;
      }

      const row = $('<tr>');
      const codeCell = $('<td>').text(code).addClass('course-id');
      const nameCell = $('<td>').text(title);
      const areaCell = $('<td>').text(type);
      const descCell = $('<td>').text(desc);
      row.append(codeCell, nameCell, areaCell, descCell);
      tbody.append(row);
    });
 
    // Append the table to the screen
    table.append(tbody);
    $('.course-content').empty().append(table);

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
      const apiUrl = `http://localhost:8080/coursehistories/course/${encodeURIComponent(courseId)}`;
  
      // Fetch the course information for the clicked professor
      $.ajax({
        url: apiUrl,
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
    });
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
    $('.course-content').empty().append(table);
  }
});
  