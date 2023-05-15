2023 Software Engineering(CSE364) - Group 7
===============================
- - -
### 👥 Team Member
- Changhyeon Kim (20181069)
- KyeongHoon Min (20191102)
- JunSu Lee (20191244)


### ⚙️ Environment Setting
-  ```Java 17```
-  ```JDK 17.0.6```
-  **OS** : Ubuntu 22.04
-  **Database** : MongoDB 6.0.5
-  **IDE** : VS Code
-  **Framework** : Spring Boot 2.6.1


- - -
## 🚩 Milestone 2
#### First, build a given DockerFile.
```bash
docker build -t image_name . 
```
#### Then, run the docker image.
```bash
docker run -d -p 27017:27017 -p 8080:8080 image_name
docker exec -it container_name /bin/bash
```
#### Finally, inside the docker container, run the following command.
```bash
bash run.sh
```
<hr/>

### Part 2. 

#### 1. Default feature. 
We made user sign up feature by using student id and name.
```bash
curl -X POST -H "Content-Type: application/json" -d '{"id": "20201111", "name": "Hong gil dong"}' http://localhost:8080/users 
```
And by using GET command, we can check that user sign up is successfully done. 
![스크린샷 2023-05-15 오전 10 24 05](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/d4f26857-7690-47b8-b292-160a3ba1ce73)

<hr/>

#### 2. Feature 1
* Users can add or modify courses they have taken or plan to take according to the roadmap.   
* Users simply enter the course ID and the course information stored in the database is automatically retrieved and saved.   
* For example, if the user want to take course "Advanced Programming" with course id "1241", we can use the following command.
```bash
curl -X POST http://localhost:8080/users/20201111/courses/1241  
```
We can confirm that the new course information is added correctly.<br></br>
![스크린샷 2023-05-15 오전 10 31 33](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/b9068085-f8f9-49a9-b601-87feda659559)

* We can get the course information of use whose id is "20201111", by using the following command.
```bash
curl -X GET http://localhost:8080/users/20201111/courses
```
<img width="1418" alt="스크린샷 2023-05-15 오후 2 00 29" src="https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/41aa02cd-7c2c-4af1-bf6f-3d89a7c279c5">

<hr/>

#### 3. Feature 2  

* We made course database for CSE major of UNIST.
By using the following command, we can easily see the course information.
```bash
curl -X GET http://localhost:8080/courses
```
Then we can see the following result.

![cmdGET courses](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/d2095202-9c34-4564-85ee-d8a158d7680b)  

Also, we can find the course information using course id.  
Id is written in 4 digits, and follows corresopnding format:  
* First digit : Indicating school. (1: CSE, 2: ITP, 3:MTH)  
* Other digits : Indicating course code's number part.  

For example, below Id 1364 means CSE364 course. This rule is applied to all course Ids.  
```bash
curl -X GET http://localhost:8080/courses/1364
```
![cmdGET courses-1364](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/2b8d624a-f389-4e6e-8b12-1f414d5ab119)

* And then if you enter the type of each subject, for example "AI", information about artificial intelligence-related classes will appear.
```bash
curl -X GET http://localhost:8080/courses/area/AI
```
![cmdGET courses-area-AI](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/efe749db-6209-4b27-afac-5e721c3fedf1)

* It also recommends the classes you need to take classes in a specific field.   
* Get result will provide these filtered courses:  
  * All courses that is set to be "Required",  
  * Courses that matches the given area, but in "Selective".  
* For example, to advance into the system field, its recommendation can be obtained through the following command.
```bash
curl -X GET http://localhost:8080/courses/recommend/System
``` 
![cmdGET courses-recommend-System](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/aa7367fb-8ce7-4cf6-9103-c8d3285b480c)

* This web application also provides the 5-year database of courses opened in UNIST.  
* To browse all course histories in 'coursehistories' tab.  
* To see one specific course, indicate its Id on the back. It has different Id format with 1364. If has a five-digit format, and it follows such rules(XXYZZ):  
  * XX : Last two digits of opened year. (2022->22)
  * Y : Semester. (1: Spring, 2: Fall)
  * ZZ : Index of course in that semester, starting from 01.
* For example, course history Id 18112 stands for twelveth course in 2018 Spring semester.  
```bash
curl -X GET http://localhost:8080/coursehistories
curl -X GET http://localhost:8080/coursehistories/18112
```
<img width="992" alt="스크린샷 2023-05-15 오후 5 48 51" src="https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/0359052e-1fee-4e88-ad4d-d6e743d45b70">

<img width="1027" alt="스크린샷 2023-05-15 오후 5 49 26" src="https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/54fc1017-6007-4bab-8306-333b7c95ab09">

* You can also know the information of classes held in a specific year as follows. In database, course histories of 2018~2022 are stored.   
* Even we can specify the semester in 1(Spring) and 2(Fall).
```bash
curl -X GET http://localhost:8080/coursehistories/browse/2022
curl -X GET http://localhost:8080/coursehistories/browse/2020/1
```
![cmdGET coursehistories-browse-2022](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/e75169d7-cbb2-442d-8eee-841cba96b583)

![cmdGET coursehistories-browse-2020-1](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/2bdcde2b-7077-4dab-b7dd-10c2688932c4)


* Similar with previous function, we can find the courses recommended for specific grade(Freshman/Sophomore/Junior/Senior) as follows. 
* Even we can specify the semester in 1(Spring) and 2(Fall).
```bash
curl -X GET http: //localhost:8080/courses/grade/Junior
curl -X GET http: //localhost:8080/courses/grade/Senior/1
```
![cmdGET courses-grade-Junior](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/dc115559-5bfd-4a27-aed7-144816e21f05)

![cmdGET courses-grade-Senior-1](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/3a447464-d71a-45b1-a99a-13c74ad84d9b)


* In CSE courses, prerequisite courses are important matters. 
* We can browse courses that requires the given course Id to be taken.  
* For example, to check courses that has 1221(Data Structure), below commands can be used.  
``` bash
curl -X GET http://localhost:8080/courses/next/1221
```
![cmdGET courses-next-1221](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/4e9e6910-01b6-46d7-b014-e1b156d35cbe)

* One of our key feature is that users can be informed of tendencies in courses others are taking.    
* It shows top five courses people have taken the most.  
* For this milestone, tendencies of courses are manually typed. Please note that if # of users are large enough, top 5 courses can be changed.
```bash
curl -X GET http://localhost:8080/courses/tendency
curl -X GET http://localhost:8080/courses/tendency/Sophomore
```
![cmdGET courses-tendency](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/d875fe0c-14d9-483c-acb0-e989c67bad0c)

![cmdGET courses-tendency-Sophomore](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/aeb707ee-bbe6-4f72-9257-c3024e92989c)


<hr/>

#### 4. Feature 3
* This feature shows professors' information such that introductions to labs, email, office phone  and courses taken.
* First, we can see the list of professors in CSE major.
```bash
curl -X GET http://localhost:8080/professors
```
<img width="1726" alt="스크린샷 2023-05-15 오후 4 38 57" src="https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/5a8dd048-70e7-4fa2-9d5f-837b10df8245">

* Also, we can easily search professors using name.
* For example, we can search for professors whose last name is "Kim".
```bash
curl -X GET http://localhost:8080/professors/search/name/Kim
```
* We can also search by professor's research field. 
* For example, we can search for information about professors who are researching ai as follows.
```bash
curl -X GET http://localhost:8080/professors/search/area/ai
```
<img width="1728" alt="스크린샷 2023-05-15 오후 4 45 27" src="https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/d60e0d95-4402-4424-8b9c-e2022aabd3de">

* We can also find professor using office phone number.(e.g. ends with 2253)
```bash
curl -X GET http://localhost:8080/professors/search/phone/2253
```

* Additionally, we can search for courses using a partial of the professor's name.
* For example, we can search for courses that were opened by professors with 'kim' in their name.

```bash
curl -X GET http://localhost:8080/professors/search/kim/courses
```


!!!!!!
사진 넣어 주세요.
!!!!!!


Then we can see the following result.
<br></br><br></br>

<hr/>

### Part 3. 
In this part, we implemented "Junit test" for the code we implemented in Part 2.  
Here are the test results:  

![스크린샷 2023-05-15 오전 10 15 25](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/785cfff7-f7d5-454c-957e-b8dc578c174c)


<hr/>  

#### This is all for Milestone 2 implementation.
