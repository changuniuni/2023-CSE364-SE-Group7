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

### Guilelined for running the code(Part 2).

#### 1. To check the implementation of REST APIs, if we use the following command, 
```bash
curl -X GET http://localhost:8080/employees 
```
We can get the following output.
<img src="https://user-images.githubusercontent.com/48553632/229964494-bdb6dab2-fabc-4447-905b-0c6ea85c3306.png">  
![KakaoTalk_Photo_2023-04-06-13-15-59-1](https://user-images.githubusercontent.com/48553632/230270925-7beb1395-feec-4a78-b169-0d93d0444450.png)
  


#### 2. And if we add new element who has name "Test_people" and has a role of "Undergrad stuent" by using following command,
```bash
curl -X POST http://localhost:8080/employees -H 'Content-Type: application/json' -d '{"name":"Test_people","role":"Undergrad student"}'
```
We can confirm that the new element is added correctly.<br></br>
<img width="826" alt="스크린샷 2023-04-05 오전 10 46 29" src="https://user-images.githubusercontent.com/48553632/229964934-6a2f13a3-1604-4048-935d-c5c0aee49c0f.png">
  
  ![KakaoTalk_Photo_2023-04-06-13-15-59-2](https://user-images.githubusercontent.com/48553632/230270999-605a5ffb-6948-4792-b9da-602536bbf31a.png)



#### 3. If we want to modify the element with another role "Grad student", we can use the following command.
```bash
curl -X PUT http://localhost:8080/employees/id -H 'Content-Type: application/json' -d '{"name":"Test_people","role":"Grad student"}'
```
***Please note that "id" should be replaced with the id of the element that we want to modify.***
<br></br>

Then we can see the following result.
<img width="790" alt="스크린샷 2023-04-05 오전 10 49 35" src="https://user-images.githubusercontent.com/48553632/229965732-e41dddeb-1614-41ee-92f7-33e93474181b.png">  
 ![KakaoTalk_Photo_2023-04-06-13-15-59-3](https://user-images.githubusercontent.com/48553632/230271040-6f400d82-f7de-4845-9ef4-f63deb01e673.png)


  
#### 4. If we want to delete the element, we can use the following command.
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
