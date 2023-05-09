package milestone2.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RestController
class CourseController {

  private final CourseRepository repository;

  private final CourseModelAssembler assembler;

  CourseController(CourseRepository repository, CourseModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
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
          course.setOpenSmes(newCourse.getOpenSmes());
          course.setType(newCourse.getType());
          course.setDesc(newCourse.getDesc());
          course.setCount(newCourse.getCount());
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
  ResponseEntity<?> deleteCourse(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/courses/academic/{acadYear}")
  CollectionModel<EntityModel<Course>> YearSearch(@PathVariable String acadYear) {
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
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).all()).withSelfRel());
  }

  @GetMapping("/courses/academic/{acadYear}/{openSmes}")
  CollectionModel<EntityModel<Course>> YearSmesSearch(@PathVariable String acadYear, @PathVariable int openSmes) {
    if (openSmes < 0 || openSmes > 2) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "Invalid Semester value (0~2 expected).");
    }
    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        String findYear = course.getAcadYear().toLowerCase();
        int findSmes = course.getOpenSmes();
        if(findYear.indexOf(acadYear.toLowerCase()) >= 0 && findSmes + openSmes != 3)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).all()).withSelfRel());
  }
/*
  @GetMapping("/tendencies")
  CollectionModel<EntityModel<Course>> TendencySearch() {

    List<EntityModel<Course>> courses = CourseRepository.findAll();
    List<EntityModel<Course>> tendencyList = new ArrayList<>();
    Map<String, Integer> tendenciesMap = new HashMap<>();
    int bestcourse = 0;
    for(Course courseAll : courses) {
      String courseId = courseAll.getCourseId();
      int courseCount = courseAll.getCount();
      if(bestcourse < 5) {
        bestcourse += 1;
      } else {
        String deleteId = "";
        int deleteCount = 999;
        for(Map.Entry<String, Integer> deleteTarget : tendenciesMap.entrySet()) {
          if(deleteCount > deleteTarget.getValue()) {
            deleteId = deleteTarget.getKey();
            deleteCount = deleteTarget.getValue();
          }
        }
        tendenciesMap.remove(deleteId);
      }
      tendenciesMap.put(courseId, courseCount);
    }

    for(Map.Entry<String, Integer> returnTarget : tendenciesMap.entrySet()) {
      String addCourseId = returnTarget.getKey();
      Course addCourse = CourseRepository.findById(addCourseId).orElse(null);
      if(addCourse != null) {tendencyList.add(addCourse);}
    }
    
    return tendencyList;
  }*/
}
