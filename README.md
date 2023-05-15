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

### Part 2. 

#### 1. Default feature. 
We made user sign up feature by using student id and name.
```bash
curl -X POST -H "Content-Type: application/json" -d '{"id": "20201111", "name": "Hong gil dong"}' http://localhost:8080/users 
```
And by using GET command, we can check that user sign up is successfully done. 
![스크린샷 2023-05-15 오전 10 24 05](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/d4f26857-7690-47b8-b292-160a3ba1ce73)


#### 2. Feature 1
Users can add or modify courses they have taken or plan to take according to the roadmap.   
Users simply enter the course ID and the course information stored in the database is automatically retrieved and saved.   
For example, if the user want to take course "Advanced Programming" with course id "1241", we can use the following command.
```bash
curl -X POST http://localhost:8080/users/20201111/courses/1241  
```
We can confirm that the new course information is added correctly.<br></br>
![스크린샷 2023-05-15 오전 10 31 33](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/b9068085-f8f9-49a9-b601-87feda659559)

<br></br>  

#### 3. Feature 2
We made course database for CSE major of UNIST.
By using the following command, we can easily see the course information.
```bash
curl -X GET http://localhost:8080/courses
```
Then we can see the following result.

![cmdGET courses](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/d2095202-9c34-4564-85ee-d8a158d7680b)  

Also, we can find the course information using course id.
```bash
curl -X GET http://localhost:8080/courses/1364
```

![cmdGET courses-1364](https://github.com/changuniuni/2023-CSE364-SE-Group7/assets/48553632/2b8d624a-f389-4e6e-8b12-1f414d5ab119)


And then if you enter the type of each subject, for example "AI", information about artificial intelligence-related classes will appear.
```bash
curl -X GET http://localhost: 8080/courses/area/AI
```
It also recommends the classes you need to take classes in a specific field. For example, to advance into the system field, information can be obtained through the following command.
```bash
curl -X GET http://localhost: 8080/courses/recommend/System
``` 

You can also know the information of classes held in a specific year as follows. Even we can specify the semester.
```bash
curl -X GET http://localhost:8080/coursehistories/browse/2022
curl -X GET http://localhost:8080/coursehistories/browse/2020/1
```

Similar with previous function, we can find the courses recommended for specific grade(e.g. sophomore or junior) as follows. Even we can specify the semester.
```bash
curl -X GET http: //localhost:8080/courses/grade/Junior
curl -X GET http: //localhost:8080/courses/grade/Senior/1
```

In CSE courses, prerequisite courses are important matters. Therefore, we can check the prerequisite courses of a specific course as follows.
```bash
curl -X GET http://localhost:8080/courses/next/1221
```
One of our key feature is that users can be informed of tendencies in courses others are taking.   
Students can establish your own course roadmap through information on courses taken by other students.   
It shows the courses people have taken the most.
```bash
curl -X GET http://localhost:8080/courses/tendency
curl -× GET http://localhost: 8080/courses/ tendency/Sophomore
```

<br></br>
  
#### 4. Feature 3
This feature shows professors' research fields, introductions to labs, and information on courses taken.
```bash
curl -X DELETE http://localhost:8080/employees/id
```
***Please note that "id" should be replaced with the id of the element that we want to delete.***
<br></br>
Then we can see the following result.
<img width="1493" alt="image" src="https://user-images.githubusercontent.com/48553632/229966245-b1d46a2c-7071-4f83-bd39-3bdcc1b2f014.png">  
![KakaoTalk_Photo_2023-04-06-13-15-59-4](https://user-images.githubusercontent.com/48553632/230271106-8956c4e0-cddf-4e96-8919-0ff88d86998b.png)

<br></br>

#### Basic REST API implementation is done for PART 2
- - -
### Guilelined for running the code(Part 3).
#### 1. If we want to see the total movie list by using the following command,
```bash
curl -X GET http://localhost:8080/movies
```
It will show the first top 20 movies.
![image](https://user-images.githubusercontent.com/48553632/229973289-41fef6e6-8410-495b-a812-bba0f978eb23.png)

#### 2. If we want to see the movie list with average rating above 5, we can use the following command.
```bash
curl -X GET http://localhost:8080/ratings/5
```
Then we can see the following result.
![스크린샷 2023-04-05 오후 2 21 41](https://user-images.githubusercontent.com/48553632/230007915-a7cc4011-f9d8-46d0-8f49-cc1963ecc810.png)

#### 3. If we add new movie with id = "9999", title = "test_Movie" and genres = "Crime", we can use the following command.
```bash
curl -X POST http://localhost:8080/movies -H 'Content-Type: application/json' -d '{"id":"9999","title":"test_Movie","genres":["Crime"]}'
```  
***Please note that we have to give genres with brackets.***
Then we can see the following result.  
<img width="1725" alt="스크린샷 2023-04-05 오전 10 41 28" src="https://user-images.githubusercontent.com/48553632/230009098-f20e619b-e1e3-4e27-9103-2c2f12e0326c.png">  

And we can see that the movie is added correctly.
<img width="574" alt="image" src="https://user-images.githubusercontent.com/48553632/230009791-07107c0f-f50f-4513-b995-a56cdacb5572.png">

#### 4. If we want to modify the movie with id = "9999", which has a genre of "Comedy", we can use the following command.
```bash
curl -X PUT http://localhost:8080/movies/9999 -H 'Content-Type: application/json' -d '{"id":"9999","title":"test_Movie","genres":["Comedy"]}'
```
Then we can see the following result.
<img width="1728" alt="스크린샷 2023-04-05 오전 10 42 46" src="https://user-images.githubusercontent.com/48553632/230010115-72cb2eec-4b69-41bf-8bc5-ed3d71612a22.png">
<br></br><br></br>


      
## This is all for Milestone 1 implementation.
