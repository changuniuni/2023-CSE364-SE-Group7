![logo](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/969bf827-e539-483a-9b6b-e9d6416ebe20)  

- - -

## **🏃‍♂️ About paCSEmaker**  
paCEmaker is a project done from UNIST 2023 CSE364 Software Endineering course.  

This web application originated from needs of the university life planner.  

In UNIST portal, it is hard to plan 4-year course taking roadmap right away.  

Also, it takes additional step to view which courses has the professor opened.  

And there is no gathered data about CSE professors, so we need to visit another website.  

To resolve this, our team made integrated UNIST CSE planner called **paCSEmaker**.  

- - -

## **⚙️ Getting Started**  
### **Step 1**. Deploying WAR file to Apache Tomcat on Docker  
(Description on step 1)
### **Step 2**. Running JAR on Docker using run.sh  
(Description on step 2)  
### **Step 3**. Running JAR manually  
(Description on step 3)  

- - -

## **🔑 Sign up and Log in**  
The first paCSEmaker web screen looks like this.  
![beginning](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/8898d4d6-530d-4f90-8ddb-4fa66e9f171e)  
You need to sign up and log in to proceed.  
Click on the sign up button, and create a new account.  
![signup](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/27d3386c-8423-4c7c-a51c-f89649c4352b)  
Then, you are now able to log in, if you've typed the ID-Name pair correctly.  
![login](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/80a925ea-9d67-4a68-87bd-43fe1e3a3a67)  
- - -
## **📃 Adding Courses to Your Storage**  
After logging in, you can see your personal storage for taken/planned courses.  
If it's your first log in, table would be empty:  
(img1)
You can fill this table by clicking 'Add Course' button.  
When clicking, you can see whole 5-year(2018~2022) course histories.  
From this lists, you can add the courses that you've already taken.  
For those who struggles to find exact course, filtering option is given.  
(img2)
- - -
## **👩‍🏫 Information about Professors**  
On top left of the webpage, several navigations are provied.  
Upon clicking 'Professor', you can access to the information about UNIST CSE professors.  
At first glance, it contains minimized information, research topic and email address.  
(img3)
If you need more details, click on the professor name. Then, it will provide bunch of professor information to you.  
(img4)
- - -
## **🔍 Quick Course Catalog Browsing**    
Upon clicking 'Course' on the navigator, you can browse 2023 UNIST CSE course catalog.  
(img5)
Five filters are provided to obtian desired data quickly.  
Choose one checkbox of the filter that you are going to use.  
After specifying search option and click 'Filter', you'll get refreshed data.
(img6)
If you need more details, click on the course name.  
You can see two additional table, one with detailed course information, and another with 5-year opened history of the course.  
(img7)
- - -
## **🚩 Getting Roadmap Recommendation**    
Click 'Recommend' on the navigator to move on to the recommendation page.  
Then you'll see the empty table at first.  
(img8)
If you see on top center, you can choose a CSE area to be recommended.  
Total 10 recommendations are provided, and each option will give different table of courses.  
(img9)
If you decided to take some courses later, you can add that course to your private storage.  
Press 'Add' button on each course, then it will be contained.  
Note that courses that are already in storage cannot be clicked.  
(img10)
- - -
### This is all for Milestone 2 implementation.  