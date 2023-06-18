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

## **💡 Three Key Features of paCSEmaker**  
### *Feature 1.* Browse course catalog, revise 5-year histories of course opened, and getting course roadmap recommendatiosn depending on CSE areas.  

### *Feature 2.* Personal storage to store courses that are already taken or will be taken. Availabe users to add courses into the storage whenever they want.  

### *Feature 3.* Provide professor infromation in one table. Link professor with 5-year course histories to see which courses has this professor opened.  

- - -

## **⚙️ Getting Started**  
### *Case 1:* For ordinary users who want to use our product  
First, you need two files to start deloying. If you're missing with any one of these, please get them from our github repository.
* Dockerfile  
* CSE364_project.war
* run.sh

If you have two files on the same directory, make a Docker image by typing this command.  
```bash
docker build -t pacsemaker .
```
Then, you can see the Docker image has been created. Now, make a new container with port 8080.  
```bash
docker run -p 8080:8080 -it pacsemaker
```
After this process, wait for 2~3 minutes. Then you can start using our product on <http://localhost:8080>.  
### *Case 2:* For developers who want to view&edit our code  
For this case, you need three files to start deloying. Again, missing files can be obtained from our github repository.
* Dockerfile  
* CSE364_project.war
* run.sh

**Important!** You need to do a modification on Dockerfile. Please change the following parts as follows.  
```bash
//Change line 42 from  
CMD mongod --fork --logpath /var/log/mongodb.log; /root/tomcat/apache-tomcat-10.0.10/bin/catalina.sh run  
//into  
CMD mongod  
```
Then, make a Docker image on the directory that three files are put together.  
```bash
docker build -t pacsemaker .
```
After creating Docker image, make a new container with port 8080.  
```bash
docker run -p 8080:8080 -it pacsemaker
```
Execute that container with this command.  
```bash
docker exec -it container_name /bin/bash
```
Finally, inside the docker container, run the following command.  
```bash
bash run.sh
```
With 3~4 minutes of waitings, you can view our product on <http://localhost:8080>.  
Please refer to [Milestone2 README](https://github.com/changuniuni/2023-CSE364-SE-Group7/blob/milestone2/README.md) to see how to manipulate REST APIs.

- - -

## **🔑 Sign up and Log in**  
The first paCSEmaker web screen looks like this.  
![sign_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/b5db8170-ba98-4aae-98fa-ee6f17abf27d)  
You need to sign up and log in to proceed.  
Click on the sign up button, and create a new account.  
![sign_up](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/e56c6071-6d0c-486a-b2be-f7616c09a78d)  
Then, you are now able to log in, if you've typed the ID-Name pair correctly.  
![sign_in](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/9ffb1b47-f54c-4b09-b7ce-72c68be471ca)  
- - -
## **📃 Adding Courses to Your Storage**  
After logging in, you can see your personal storage for taken/planned courses.  
If it's your first log in, table would be empty:  
![user_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/99b6433e-2371-46c2-bbaa-1e4cc9663ec8)  
You can fill this table by clicking 'Add Course' button.  
When clicking, you can see whole 5-year(2018~2022) course histories.  
From this lists, you can add the courses that you've already taken.  
For those who struggles to find exact course, filtering option is given.  
![user_add](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/45d0679e-174e-47b8-b40c-797b617f5ad1)  
- - -
## **👩‍🏫 Information about Professors**  
On top left of the webpage, several navigations are provied.  
Upon clicking 'Professor', you can access to the information about UNIST CSE professors.  
At first glance, it contains minimized information, research topic and email address.  
![prof_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/0496e04a-e41e-44f5-9489-d101e53eaa55)  
If you need more details, click on the professor name. Then, it will provide bunch of professor information to you.  
On the bottom of the screen, professor's 5-year history of courses opened are also provided.  
![prof_detail](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/57198cb4-8c5e-4857-a068-f3abdc0b175e)  
- - -
## **🔍 Quick Course Catalog Browsing**    
Upon clicking 'Course' on the navigator, you can browse 2023 UNIST CSE course catalog.  
![course_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/48993d55-2dc5-4a56-8503-87221f2438c4)  
Five filters are provided to obtian desired data quickly.  
Choose one checkbox of the filter that you are going to use.  
After specifying search option and click 'Filter', you'll get refreshed data.
![course_filter](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/cbb4e8ae-53b3-46dc-8c1b-70f2e847bbb9)  
If you need more details, click on the course name.  
You can see two additional table, one with detailed course information, and another with 5-year opened history of the course.  
![course_detail](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/adf097cd-3c9d-4fb9-8d98-f9bb81564aad)  
- - -
## **🚩 Getting Roadmap Recommendation**    
Click 'Recommend' on the navigator to move on to the recommendation page.  
Then you'll see the empty table at first.  
![rec_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/fa82e527-7555-4664-bd29-ba1a3ec5e078)  
If you see on top center, you can choose a CSE area to be recommended.  
Total 10 recommendations are provided, and each option will give different table of courses.  
![rec_filter](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/ac35c780-b7cd-4dbe-84cb-618f8a69dfe9)  
If you decided to take some courses later, you can add that course to your private storage.  
Press 'Add' button on each course, then it will be contained.  
Note that courses that are already in storage cannot be clicked.  
![rec_user](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/ce56eec6-66fe-4aa6-b748-21dc479257b8)  
- - -
### This is all for paCSEmaker implementation.  
