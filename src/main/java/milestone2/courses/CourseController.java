package milestone2.courses;

import java.util.List;
import java.util.Map;

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
  public
  CollectionModel<EntityModel<Course>> courseShowAll() {
 
    List<EntityModel<Course>> courses = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(courses, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }

  @GetMapping("/courses/{id}")
  public EntityModel<Course> courseShowOne(@PathVariable String id) {

    Course course = repository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException(id));
  
    return assembler.toModel(course);
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
  ResponseEntity<?> userCourseRemove(@PathVariable String userId, @PathVariable String courseId) {
    
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
  
  @GetMapping("/courses/area/{area}")
  CollectionModel<EntityModel<Course>> courseArea(@PathVariable String area) {
     
    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        String findArea = course.getType().toLowerCase();
        if(findArea.indexOf(area.toLowerCase()) >= 0)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }

  @GetMapping("/courses/next/{id}")
  CollectionModel<EntityModel<Course>> courseNext(@PathVariable String id) {
     
    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        String[] findPrereq = course.getPrereq();
        for(String targetPrereq : findPrereq) {
          if(targetPrereq.indexOf(id) == 0)
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

  @GetMapping("/courses/recommend/{area}")
  CollectionModel<EntityModel<Course>> courseRecommendArea(@PathVariable String area) {

    List<EntityModel<Course>> CourseYear = repository.findAll()
      .stream()
      .filter(course -> {
        if(course.getMandatory().indexOf("Required") == 0)
          return true;
        String findArea = course.getType().toLowerCase();
        if(findArea.indexOf(area.toLowerCase()) >= 0)
          return true;
        if(findArea.indexOf("System") != 0 || findArea.indexOf("Network") != 0)
          if(course.getCourseId().indexOf("1311") == 0)
            return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel());
  }
}
