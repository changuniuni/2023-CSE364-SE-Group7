package milestone2.courses;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

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
          course.setType(newCourse.getType());
          course.setDesc(newCourse.getDesc());
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
}
