![logo](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/5b40ea08-c628-4978-84b9-81ec821b934a)  

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
![sign_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/b5d70552-7eb1-4edc-b49a-17f9818a0099)  
You need to sign up and log in to proceed.  
Click on the sign up button, and create a new account.  
![sign_up](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/a18c5626-a3d7-4b39-8534-22909efe9c7a)  
Then, you are now able to log in, if you've typed the ID-Name pair correctly.  
![sign_in](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/f45f0f06-fa7c-4b4b-bc3a-cc57f8bae87e)  
- - -
## **📃 Adding Courses to Your Storage**  
After logging in, you can see your personal storage for taken/planned courses.  
If it's your first log in, table would be empty:  
![user_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/c32adac0-5718-4a8a-b63e-f1ee42379810)  
You can fill this table by clicking 'Add Course' button.  
When clicking, you can see whole 5-year(2018~2022) course histories.  
From this lists, you can add the courses that you've already taken.  
For those who struggles to find exact course, filtering option is given.  
![user_add](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/801d2d7a-56be-47e4-a1ac-f65d26a8ced1)  
- - -
## **👩‍🏫 Information about Professors**  
On top left of the webpage, several navigations are provied.  
Upon clicking 'Professor', you can access to the information about UNIST CSE professors.  
At first glance, it contains minimized information, research topic and email address.  
![prof_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/fe3a81da-e3bf-43ac-b10a-34ec98de9a9b)  
If you need more details, click on the professor name. Then, it will provide bunch of professor information to you.  
On the bottom of the screen, professor's 5-year history of courses opened are also provided.  
![prof_detail](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/9e9c0393-fcad-4f3c-a565-700dc8910402)  
- - -
## **🔍 Quick Course Catalog Browsing**    
Upon clicking 'Course' on the navigator, you can browse 2023 UNIST CSE course catalog.  
![course_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/0d951a41-b2fb-46f8-a631-d8ed724958be)  
Five filters are provided to obtian desired data quickly.  
Choose one checkbox of the filter that you are going to use.  
After specifying search option and click 'Filter', you'll get refreshed data.
![course_filter](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/38b055af-d07f-4978-ad8b-eca7676b8903)  
If you need more details, click on the course name.  
You can see two additional table, one with detailed course information, and another with 5-year opened history of the course.  
![course_detail](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/67427b84-e4a8-4584-beec-a5d80fad16ad)  
- - -
## **🚩 Getting Roadmap Recommendation**    
Click 'Recommend' on the navigator to move on to the recommendation page.  
Then you'll see the empty table at first.  
![rec_init](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/4e608b0e-377a-460d-98e0-45f7579c8ad7)  
If you see on top center, you can choose a CSE area to be recommended.  
Total 10 recommendations are provided, and each option will give different table of courses.  
![rec_filter](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/1e3a9695-bb3b-468f-bb2e-1de5283baadd)  
If you decided to take some courses later, you can add that course to your private storage.  
Press 'Add' button on each course, then it will be contained.  
Note that courses that are already in storage cannot be clicked.  
![rec_user](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/64305539/d0839e0b-e000-4964-a4b9-45c2614c0452)  
- - -
### This is all for paCSEmaker implementation.  
