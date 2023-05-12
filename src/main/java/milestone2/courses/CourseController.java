package milestone2.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.springframework.web.bind.annotation.*;

import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class CourseController {

  private final CourseRepository repository;
  private final UserRepository userRepository;
  private final CourseModelAssembler assembler;

  CourseController(CourseRepository repository, CourseModelAssembler assembler, UserRepository userRepository) {
    this.repository = repository;
    this.assembler = assembler;
    this.userRepository = userRepository;
  }

  @GetMapping("/courses")
  CollectionModel<EntityModel<Course>> courseShowAll() {
 
    List<EntityModel<Course>> courses = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(courses, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }

  @PostMapping("/courses")
  ResponseEntity<?> courseNew(@RequestBody Course newCourse) {

    EntityModel<Course> entityModel = assembler.toModel(repository.save(newCourse));
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @GetMapping("/courses/{id}")
  public EntityModel<Course> courseShowOne(@PathVariable String id) {

    Course course = repository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException(id));
  
    return assembler.toModel(course);
  }

  @PutMapping("/courses/{id}")
  ResponseEntity<?> courseReplace(@RequestBody Course newCourse, @PathVariable String id) {

    Course updatedCourse = repository.findById(id)
        .map(course -> {
          course.setCode(newCourse.getCode());
          course.setTitle(newCourse.getTitle());
          course.setMandatory(newCourse.getMandatory());
          course.setPrereq(newCourse.getPrereq());
          course.setAcadYear(newCourse.getAcadYear());
          course.setOpenSmes(newCourse.getOpenSmes());
          course.setType(newCourse.getType());
          course.setDesc(newCourse.getDesc());
          return repository.save(course);
        })
        .orElseGet(() -> {
          newCourse.setCourseId(id);
          return repository.save(newCourse);
        });
  
    EntityModel<Course> entityModel = assembler.toModel(updatedCourse);
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/courses/{id}")
  ResponseEntity<?> courseDelete(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }

  // curl -X POST http://localhost:8080/users/20201111/courses/1241   
  @PostMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> userCourseAdd(@PathVariable String userId, @PathVariable String courseId) {
    
    CourseCountMap courseCntMap = CourseCountMap.getInstance();
    User user = userRepository.findById(userId).orElseThrow();
    Course course = repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    user.getCourseList().add(course);
    userRepository.save(user);
    EntityModel<Course> courseModel = assembler.toModel(course);
    
    int countToUpdate = courseCntMap.getValue(courseId) + 1;
    courseCntMap.put(courseId, countToUpdate);
    
    return ResponseEntity
        .created(courseModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(courseModel);
  }

  // curl -X DELETE http://localhost:8080/users/20201111/courses/1221
  @DeleteMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> userCourseRemove(@PathVariable String userId, @PathVariable String courseId) 
  {
    
    CourseCountMap courseCntMap = CourseCountMap.getInstance();
    User user = userRepository.findById(userId).orElseThrow();
    Course course = repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    user.deleteCourse(courseId);
    userRepository.save(user);
    EntityModel<Course> courseModel = assembler.toModel(course);
    
    int countToUpdate = courseCntMap.getValue(courseId) - 1;
    courseCntMap.put(courseId, countToUpdate);
    
    return ResponseEntity
        .created(courseModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(courseModel);
  }

  @GetMapping("/courses/grade/{acadYear}")
  CollectionModel<EntityModel<Course>> courseGradeYear(@PathVariable String acadYear) {
    
    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        String findYear = course.getAcadYear().toLowerCase();
        if(findYear.indexOf(acadYear.toLowerCase()) >= 0)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }

  @GetMapping("/courses/grade/{acadYear}/{openSmes}")
  CollectionModel<EntityModel<Course>> courseGradeYearSmes(@PathVariable String acadYear, @PathVariable int openSmes) {
    
    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        String findYear = course.getAcadYear().toLowerCase();
        int findSmes = course.getOpenSmes();
        if(findYear.indexOf(acadYear.toLowerCase()) >= 0 && findSmes + openSmes != 3) {
          return true;
        }
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }
  
  @GetMapping("/courses/tendency")
  CollectionModel<EntityModel<Course>> courseTendency() {
    
    CourseCountMap courseCntMap = CourseCountMap.getInstance();
    String[] tendencyTop = new String[5];
    
    for(Map.Entry<String, Integer> entry : courseCntMap.getEntrySet()) {
      int value = entry.getValue();
      for(int i = 0; i < 5; i++) {
        if(tendencyTop[i] == null) {
          tendencyTop[i] = entry.getKey();
          break;
        }
        if(value > courseCntMap.getValue(tendencyTop[i])) {
          tendencyTop[4] = null;
          for(int j = 4; j > i; j--)
            tendencyTop[j] = tendencyTop[j-1];
          tendencyTop[i] = entry.getKey();
          break;
        }
      }
    }

    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        for(int i = 0; i < 5; i++) {
          String traget = course.getCourseId();
          if(traget.indexOf(tendencyTop[i]) == 0)
            return true;
        }
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }
  
  @GetMapping("/courses/tendency/{acadYear}")
  CollectionModel<EntityModel<Course>> courseTendencyGrade(@PathVariable String acadYear) {
    
    CourseCountMap courseCntMap = CourseCountMap.getInstance();
    String[] tendencyTop = new String[5];

    List<Course> courseFiltered = repository.findAll()
      .stream()
      .filter(course -> {
        String findYear = course.getAcadYear().toLowerCase();
        if(findYear.indexOf(acadYear.toLowerCase()) >= 0)
          return true;
        return false;
      })
      .collect(Collectors.toList());

    for(Course courseTarget : courseFiltered) {
      String cTargetId = courseTarget.getCourseId();
      int value = courseCntMap.getValue(cTargetId);
      for(int i = 0; i < 5; i++) {
        if(tendencyTop[i] == null) {
          tendencyTop[i] = cTargetId;
          break;
        }
        if(value > courseCntMap.getValue(tendencyTop[i])) {
          tendencyTop[4] = null;
          for(int j = 4; j > i; j--)
            tendencyTop[j] = tendencyTop[j-1];
          tendencyTop[i] = cTargetId;
          break;
        }
      }
    }

    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        for(int i = 0; i < 5; i++) {
          String traget = course.getCourseId();
          if(traget.indexOf(tendencyTop[i]) == 0)
            return true;
        }
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }
}
