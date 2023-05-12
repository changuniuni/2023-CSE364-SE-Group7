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

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/courses")
  CollectionModel<EntityModel<Course>> CourseShowAll() {
 
    List<EntityModel<Course>> courses = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(courses, linkTo(methodOn(CourseController.class).CourseShowAll()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/courses")
  ResponseEntity<?> CourseNew(@RequestBody Course newCourse) {

    EntityModel<Course> entityModel = assembler.toModel(repository.save(newCourse));
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // Single item
  @GetMapping("/courses/{id}")
public
  EntityModel<Course> CourseShowOne(@PathVariable String id) {

    Course course = repository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException(id));
  
    return assembler.toModel(course);
  }

  @PutMapping("/courses/{id}")
  ResponseEntity<?> CourseReplace(@RequestBody Course newCourse, @PathVariable String id) {

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
  ResponseEntity<?> CourseDelete(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/courses/academic/{acadYear}")
  CollectionModel<EntityModel<Course>> CourseSearchYear(@PathVariable String acadYear) {

    if (acadYear.toLowerCase() == "freshman" || acadYear.toLowerCase() == "sophomore" ||
    acadYear.toLowerCase() == "junior" || acadYear.toLowerCase() == "senior") {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "Invalid AcademicYear value (try freshman/sophomore/junior/senior).");
    }

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
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).CourseShowAll()).withSelfRel());
  }

  // curl -X POST http://localhost:8080/users/20201111/courses/1241   
  @PostMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> addCourseToUser(@PathVariable String userId, @PathVariable String courseId) {
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
  ResponseEntity<?> removeCourseFromUser(@PathVariable String userId, @PathVariable String courseId) 
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

  @GetMapping("/courses/academic/{acadYear}/{openSmes}")
  CollectionModel<EntityModel<Course>> CourseSearchYearSmes(@PathVariable String acadYear, @PathVariable int openSmes) {

    if (acadYear.toLowerCase() == "freshman" || acadYear.toLowerCase() == "sophomore" ||
    acadYear.toLowerCase() == "junior" || acadYear.toLowerCase() == "senior") {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "Invalid AcademicYear value (try freshman/sophomore/junior/senior).");
    }
    if (openSmes < 0 || openSmes > 2) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "Invalid Semester value (0~2 expected).");
    }

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
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).CourseShowAll()).withSelfRel());
  }
  
  @GetMapping("/courses/tendency")
  CollectionModel<EntityModel<Course>> CourseSearchTendency() {
    CourseCountMap courseCntMap = CourseCountMap.getInstance();

    String[] tendencyTop = new String[5];
    int position = -1;
    for(Map.Entry<String, Integer> entry : courseCntMap.getEntrySet()) {
      int value = entry.getValue();
      for(int i = 0; i < 5; i++) {
        if(tendencyTop[i] == null) {
          tendencyTop[i] = entry.getKey();
          break;
        }
        if(value > courseCntMap.getValue(tendencyTop[i])) {
          tendencyTop[4] = null;
          for(int j = 4; j > i; j--) {
            tendencyTop[j] = tendencyTop[j-1];
          }
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
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).CourseShowAll()).withSelfRel());
  }
}
