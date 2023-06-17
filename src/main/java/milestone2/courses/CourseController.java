package milestone2.courses;
import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


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

  //curl -X GET http://localhost:8080/courses
  //Getting all existing courses in 2023 catalog.
  @GetMapping("/courses")
  public ResponseEntity<?> courseShowAll() {
 
    List<EntityModel<Course>> courses = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return ResponseEntity.ok(CollectionModel.of(courses, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }

  //curl -X GET http://localhost:8080/courses/1364
  //Searching certain course with specific id.
  @GetMapping("/courses/{id}")
  public ResponseEntity<?> courseShowOne(@PathVariable String id) {
    
    Course course = repository.findById(id).orElse(null);
    if (course == null)
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(assembler.toModel(course));
  }

  //curl -X POST http://localhost:8080/users/20201111/courses/1241   
  @PostMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> userCourseAdd(@PathVariable String userId, @PathVariable String courseId) {
    
    CourseCountMap courseCntMap = CourseCountMap.getInstance();
    User user = userRepository.findById(userId).orElseThrow();
    Course course = repository.findById(courseId).orElse(null);
    if (course == null)
      return ResponseEntity.notFound().build();
    user.getCourseList().add(course);
    userRepository.save(user);
    EntityModel<Course> courseModel = assembler.toModel(course);
    
    int countToUpdate = courseCntMap.getValue(courseId) + 1;
    // System.out.println("Course count updated to " +courseCntMap.getValue(courseId)+" to "+countToUpdate);
    courseCntMap.put(courseId, countToUpdate);
    
    return ResponseEntity
        .created(courseModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(courseModel);
  }

  //curl -X DELETE http://localhost:8080/users/20201111/courses/1221
  @DeleteMapping("/users/{userId}/courses/{courseId}")
  ResponseEntity<?> userCourseRemove(@PathVariable String userId, @PathVariable String courseId) {

    CourseCountMap courseCntMap = CourseCountMap.getInstance();
    User user = userRepository.findById(userId).orElseThrow();
    Course course = repository.findById(courseId).orElse(null);
    if(course == null)
      return ResponseEntity.notFound().build();
    user.deleteCourse(courseId);
    userRepository.save(user);
    EntityModel<Course> courseModel = assembler.toModel(course);
    
    int countToUpdate = courseCntMap.getValue(courseId) - 1;
    courseCntMap.put(courseId, countToUpdate);
    
    return ResponseEntity
        .created(courseModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(courseModel);
  }

  //curl -X GET http://localhost:8080/courses/grade/Sophomore
  //Filtering courses in grade:
  //  Freshman, Sophomore, Junior, Senior
  @GetMapping("/courses/grade/{acadYear}")
  ResponseEntity<?> courseGradeYear(@PathVariable String acadYear) {

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
    if (CourseYear.isEmpty())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }

  //curl -X GET http://localhost:8080/courses/grade/Sophomore/2
  //Filtering courses in grade and smester:
  //  Freshman, Sophomore, Junior, Senior
  //  0, 1, 2
  @GetMapping("/courses/grade/{acadYear}/{openSmes}")
  ResponseEntity<?> courseGradeYearSmes(@PathVariable String acadYear, @PathVariable Integer openSmes) {

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
    if (CourseYear.isEmpty())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(CollectionModel.of(CourseYear, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }
  
  //curl -X GET http://localhost:8080/courses/grade/Sophomore/2
  //Filtering courses in area:
  //  AI, Basic, Data, Graphics, HCI, Math, Mobile, Network, Security, Software, System, Virtual
  @GetMapping("/courses/area/{area}")
  ResponseEntity<?> courseArea(@PathVariable String area) {

    List<EntityModel<Course>> CourseArea = repository.findAll()
      .stream()
      .filter(course -> {
        String findArea = course.getType().toLowerCase();
        if(findArea.indexOf(area.toLowerCase()) >= 0)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    if (CourseArea.isEmpty())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(CollectionModel.of(CourseArea, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }

  //curl -X GET http://localhost:8080/courses/next/1221
  //Showing courses that requires given id's course to be already taken.
  @GetMapping("/courses/next/{id}")
  ResponseEntity<?> courseNext(@PathVariable String id) {

    List<EntityModel<Course>> CourseNext = repository.findAll()
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
    if (CourseNext.isEmpty())
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(CollectionModel.of(CourseNext, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }

  //curl -X GET http://localhost:8080/courses/tendency
  //Showing top 5 courses that have been taken(not organized order).
  @GetMapping("/courses/tendency")
  ResponseEntity<?> courseTendency() {
    
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
    
    List<EntityModel<Course>> CourseTend = repository.findAll()
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

    return ResponseEntity.ok(CollectionModel.of(CourseTend, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }
  
  //curl -X GET http://localhost:8080/courses/tendency/Junior
  //Showing top 5 certaain grade's courses that have been taken(not organized order):
  //  Freshman, Sophomore, Junior, Senior
  @GetMapping("/courses/tendency/{acadYear}")
  ResponseEntity<?> courseTendencyGrade(@PathVariable String acadYear) {

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

    List<EntityModel<Course>> CourseTend = repository.findAll()
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

    return ResponseEntity.ok(CollectionModel.of(CourseTend, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }

  //curl -X GET http://localhost:8080/courses/recommend/HCI
  //Listing courses that 1)Are required to take, 2)Matches with give area:
  //  AI, Data, Graphics, HCI, Mobile, Network, Security, Software, System, Virtual
  @GetMapping("/courses/recommend/{area}")
  ResponseEntity<?> courseRecommendArea(@PathVariable String area) {
    String[] prereqList = new String[15];
    List<Course> courseWhole = repository.findAll()
      .stream()
      .filter(course -> {return true;})
      .collect(Collectors.toList());
    
    for(Course courseTarget : courseWhole) {
      Boolean is_valid = false;
      if(courseTarget.getMandatory().indexOf("Required") == 0)
          is_valid = true;
      String findArea = courseTarget.getType().toLowerCase();
      if(courseTarget.getMandatory().indexOf("Selective_Required") == 0) {
        if((area.toLowerCase().indexOf("system") == 0 || area.toLowerCase().indexOf("mobile") == 0 || area.toLowerCase().indexOf("security") == 0)
           && courseTarget.getCode().indexOf("CSE311") == 0)
          is_valid = true;
        else if((area.toLowerCase().indexOf("system") == -1 && area.toLowerCase().indexOf("mobile") == -1 && area.toLowerCase().indexOf("security") == -1)
                && courseTarget.getCode().indexOf("CSE351") == 0)
          is_valid = true;
      }
      if(findArea.indexOf(area.toLowerCase()) >= 0)
        is_valid = true;
      
      if(is_valid) {
        for(String prereqElem : courseTarget.getPrereq()) {
          if (prereqElem.indexOf("0") != 0) {
            for(int i = 0; i < 15; i++) {
              if(prereqList[i] == null) {
                prereqList[i] = prereqElem;
                break;
              }
              else if(prereqList[i].indexOf(prereqElem) == 0) break;
            }
          }
        }
      }
    }
    
    List<EntityModel<Course>> CourseRec = repository.findAll()
      .stream()
      .filter(course -> {
        if(course.getMandatory().indexOf("Required") == 0)
          return true;
        String findArea = course.getType().toLowerCase();
        String findId = course.getCourseId();
        for(int i = 0; i < 15; i++) {
          if(prereqList[i] == null) break;
          else if(prereqList[i].indexOf(findId) == 0) return true;
        }
        if(course.getMandatory().indexOf("Selective_Required") == 0) {
          if((area.toLowerCase().indexOf("system") == 0 || area.toLowerCase().indexOf("mobile") == 0 || area.toLowerCase().indexOf("security") == 0)
            && course.getCode().indexOf("CSE311") == 0)
            return true;
          else if((area.toLowerCase().indexOf("system") == -1 && area.toLowerCase().indexOf("mobile") == -1 && area.toLowerCase().indexOf("security") == -1)
          && course.getCode().indexOf("CSE351") == 0)
            return true;
        }
        if(findArea.indexOf(area.toLowerCase()) >= 0)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());    

    return ResponseEntity.ok(CollectionModel.of(CourseRec, linkTo(methodOn(CourseController.class).courseShowAll()).withSelfRel()));
  }
}

/*
List<EntityModel<Course>> CourseRec = repository.findAll()
      .stream()
      .filter(course -> {
        if(course.getMandatory().indexOf("Required") == 0)
          return true;
        String findArea = course.getType().toLowerCase();
        if(course.getMandatory().indexOf("Selective_Required") == 0) {
          if((area.toLowerCase().indexOf("system") == 0 || area.toLowerCase().indexOf("mobile") == 0 || area.toLowerCase().indexOf("security") == 0)
            && course.getCode().indexOf("CSE311") == 0)
            return true;
          else if((area.toLowerCase().indexOf("system") == -1 && area.toLowerCase().indexOf("mobile") == -1 && area.toLowerCase().indexOf("security") == -1)
          && course.getCode().indexOf("CSE351") == 0)
            return true;
        }
        if(findArea.indexOf(area.toLowerCase()) >= 0)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
      */