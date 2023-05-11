package milestone2.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.springframework.web.bind.annotation.*;

import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;


@RestController
public
class CourseController {

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
  CollectionModel<EntityModel<Course>> all() {
 
    List<EntityModel<Course>> courses = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(courses, linkTo(methodOn(CourseController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/courses")
  ResponseEntity<?> newCourse(@RequestBody Course newCourse) {

    EntityModel<Course> entityModel = assembler.toModel(repository.save(newCourse));
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // Single item
  @GetMapping("/courses/{id}")
  EntityModel<Course> one(@PathVariable String id) {

    Course course = repository.findById(id) //
        .orElseThrow(() -> new CourseNotFoundException(id));
  
    return assembler.toModel(course);
  }

  @PutMapping("/courses/{id}")
  ResponseEntity<?> replaceCourse(@RequestBody Course newCourse, @PathVariable String id) {

    Course updatedCourse = repository.findById(id) //
        .map(course -> {
          course.setCode(newCourse.getCode());
          course.setTitle(newCourse.getTitle());
          course.setMandatory(newCourse.getMandatory());
          course.setPrereq(newCourse.getPrereq());
          course.setAcadYear(newCourse.getAcadYear());
          course.setType(newCourse.getType());
          course.setDesc(newCourse.getDesc());
          course.setCount(newCourse.getCount());
          return repository.save(course);
        })
        .orElseGet(() -> {
          newCourse.setId(id);
          return repository.save(newCourse);
        });
  
    EntityModel<Course> entityModel = assembler.toModel(updatedCourse);
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/courses/{id}")
  ResponseEntity<?> deleteCourse(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/courses/year/{acadYear}")
  CollectionModel<EntityModel<Course>> AreaSearch(@PathVariable String acadYear) {
    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> new ArrayList<>(Arrays.asList(course.getAcadYear())).contains(acadYear))
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).all()).withSelfRel());
  }

  // curl -X POST http://localhost:8080/users/20201111/courses/1241   
  @PostMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> addCourseToUser(@PathVariable String userId, @PathVariable String courseId) {
    User user = userRepository.findById(userId).orElseThrow();
    Course course = repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    user.getCourseList().add(course);
    userRepository.save(user);
    EntityModel<Course> courseModel = assembler.toModel(course);
    return ResponseEntity
        .created(courseModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(courseModel);
  }
  // curl -X DELETE http://localhost:8080/users/20201111/courses/1221
  @DeleteMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> removeCourseFromUser(@PathVariable String userId, @PathVariable String courseId) 
  {
    User user = userRepository.findById(userId).orElseThrow();
    Course course = repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    user.deleteCourse(courseId);
    userRepository.save(user);
    EntityModel<Course> courseModel = assembler.toModel(course);
    return ResponseEntity
        .created(courseModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(courseModel);
  }
}
