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
  CollectionModel<EntityModel<Course>> CourseShowAll() {
 
    List<EntityModel<Course>> courses = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(courses, linkTo(methodOn(CourseController.class).all()).withSelfRel());
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
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).all()).withSelfRel());
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
        if(findYear.indexOf(acadYear.toLowerCase()) >= 0 && findSmes + openSmes != 3)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).all()).withSelfRel());
  }
  /*
  @GetMapping("/courses/tendency")
  CollectionModel<EntityModel<Course>> CourseSearchTendency() {
    
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
  }*/
}
