package milestone2.professors;


import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

@RestController
class ProfessorController {

  private final ProfessorRepository repository;

  private final ProfessorModelAssembler assembler;

  private final CourseHistoryRepository coursehistoryrepository;

  private final CourseHistoryModelAssembler coursehistoryassembler;

  ProfessorController(ProfessorRepository repository, ProfessorModelAssembler assembler, CourseHistoryRepository coursehistoryrepository, CourseHistoryModelAssembler coursehistoryassembler) {
    this.repository = repository;
    this.assembler = assembler;
    this.coursehistoryrepository = coursehistoryrepository;
    this.coursehistoryassembler = coursehistoryassembler;
  }

  @GetMapping("/professors")
  CollectionModel<EntityModel<Professor>> all() {
 
    List<EntityModel<Professor>> professors = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(professors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel());
  }
  
  @GetMapping("/professors/search/phone/{phone}")
  ResponseEntity<?> searchPhone(@PathVariable String phone) {

    
    Professor professor = repository.findById(phone).orElse(null);

    if (professor == null) {
      return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(assembler.toModel(professor));
  }

  @GetMapping("/professors/search/area/{findarea}")
  ResponseEntity<?> searchArea(@PathVariable String findarea) {
    List<EntityModel<Professor>> AreaProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        for(String area : professor.getArea()){
          if(area.toLowerCase().indexOf(findarea.toLowerCase())>= 0)
            return true;
        }
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());

    if (AreaProfessors.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(CollectionModel.of(AreaProfessors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel()));
  }

  @GetMapping("/professors/search/name/{findname}")
  ResponseEntity<?> searchaName(@PathVariable String findname) {
    List<EntityModel<Professor>> NameProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        if(professor.getName().toLowerCase().indexOf(findname.toLowerCase())>= 0)
          return true;
        
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    if (NameProfessors.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(CollectionModel.of(NameProfessors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel()));
  }

  @GetMapping("/professors/search/topic/{findtopic}")
  ResponseEntity<?> searchTopic(@PathVariable String findtopic) {
    List<EntityModel<Professor>> TopicProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        if(professor.getTopic().toLowerCase().indexOf(findtopic.toLowerCase())>= 0)
          return true;
        
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    if (TopicProfessors.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(CollectionModel.of(TopicProfessors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel()));
  }

// CourseHistory

  @GetMapping("/coursehistories")
  CollectionModel<EntityModel<CourseHistory>> historyall() {
 
    List<EntityModel<CourseHistory>> coursehistories = coursehistoryrepository.findAll().stream() //
        .map(coursehistoryassembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(coursehistories, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel());
  }


  @GetMapping("/coursehistories/{id}")
  ResponseEntity<?> one(@PathVariable String id) {

    CourseHistory coursehistory = coursehistoryrepository.findById(id).orElse(null);
  
    if (coursehistory == null) {
      return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(coursehistoryassembler.toModel(coursehistory));
  }

  @GetMapping("/coursehistories/browse/{year}")
  ResponseEntity<?> histroyBrowseYear(@PathVariable int year) {

    List<EntityModel<CourseHistory>> cHistoryYear = coursehistoryrepository.findAll()
      .stream()
      .filter(coursehistory -> {
        int findYear = coursehistory.getOpenYear();
        if(year == findYear) {
          return true;
        }
        return false;
      })
      .map(coursehistoryassembler::toModel)
      .collect(Collectors.toList());
    
      if (cHistoryYear.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

    return ResponseEntity.ok(CollectionModel.of(cHistoryYear, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel()));
  }

  @GetMapping("/coursehistories/browse/{year}/{smes}")
  ResponseEntity<?> histroyBrowseYearSmes(@PathVariable int year, @PathVariable int smes) {

    List<EntityModel<CourseHistory>> cHistoryYear = coursehistoryrepository.findAll()
      .stream()
      .filter(coursehistory -> {
        int findYear = coursehistory.getOpenYear();
        int findSmes = coursehistory.getOpenSmes();
        if(year == findYear && smes == findSmes) {
          return true;
        }
        return false;
      })
      .map(coursehistoryassembler::toModel)
      .collect(Collectors.toList());
    
      if (cHistoryYear.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

    return ResponseEntity.ok(CollectionModel.of(cHistoryYear, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel()));
  }

  @GetMapping("/professors/search/{name}/courses")
  ResponseEntity<?> professorCourse(@PathVariable String name) {
    List<Professor> NameProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        if(professor.getName().toLowerCase().indexOf(name.toLowerCase())>= 0)
          return true;
        
        return false;
      })
      .collect(Collectors.toList());
    
      List<EntityModel<CourseHistory>> ProfessorCourse = coursehistoryrepository.findAll().stream().filter(coursehistory -> {
        boolean check = false;
        for(Professor professor : NameProfessors){
          if(coursehistory.getProfessorName().toLowerCase().indexOf(professor.getName().toLowerCase()) >= 0){
            check = true;
          }
        }
        return check;
      })
      .map(coursehistoryassembler::toModel)
      .collect(Collectors.toList());
      if (ProfessorCourse.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

    return ResponseEntity.ok(CollectionModel.of(ProfessorCourse, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel()));
  }
}
