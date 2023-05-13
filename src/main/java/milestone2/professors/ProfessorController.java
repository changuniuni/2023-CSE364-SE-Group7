package milestone2.professors;


import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
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

  @PostMapping("/professors")
  ResponseEntity<?> newProfessor(@RequestBody Professor newProfessor) {

    EntityModel<Professor> entityModel = assembler.toModel(repository.save(newProfessor));
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }
  
  @GetMapping("/professors/search/phone/{phone}")
  EntityModel<Professor> searchPhone(@PathVariable String phone) {

    Professor professor = repository.findById(phone) //
        .orElseThrow(() -> new ProfessorNotFoundException(phone));
  
    return assembler.toModel(professor);
  }

  @PutMapping("/professors/{id}")
  ResponseEntity<?> replaceProfessor(@RequestBody Professor newProfessor, @PathVariable String id) {

    Professor updatedProfessor = repository.findById(id) //
        .map(professor -> {
          professor.setName(newProfessor.getName());
          professor.setArea(newProfessor.getArea());
          professor.setTopic(newProfessor.getTopic());
          professor.setDesc(newProfessor.getDesc());
          professor.setEmail(newProfessor.getEmail());
          professor.setOffice(newProfessor.getOffice());
          return repository.save(professor);
        })
        .orElseGet(() -> {
          newProfessor.setId(id);
          return repository.save(newProfessor);
        });
  
    EntityModel<Professor> entityModel = assembler.toModel(updatedProfessor);
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/professors/{id}")
  ResponseEntity<?> deleteProfessor(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/professors/search/area/{findarea}")
  CollectionModel<EntityModel<Professor>> searchArea(@PathVariable String findarea) {
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
    
    return CollectionModel.of(AreaProfessors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel());
  }

  @GetMapping("/professors/search/name/{findname}")
  CollectionModel<EntityModel<Professor>> searchaName(@PathVariable String findname) {
    String polishedName = findname.replaceAll("\\s+", "").toLowerCase();

    List<EntityModel<Professor>> NameProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        String targetName = professor.getName().replaceAll("\\s+", "").toLowerCase();
        if(targetName.indexOf(polishedName) >= 0)
          return true;
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(NameProfessors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel());
  }

  @GetMapping("/professors/search/topic/{findtopic}")
  CollectionModel<EntityModel<Professor>> searchTopic(@PathVariable String findtopic) {
    List<EntityModel<Professor>> TopicProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        if(professor.getTopic().toLowerCase().indexOf(findtopic.toLowerCase())>= 0)
          return true;
        
        return false;
      })
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(TopicProfessors, linkTo(methodOn(ProfessorController.class).all()).withSelfRel());
  }

// CourseHistory

  @GetMapping("/coursehistories")
  CollectionModel<EntityModel<CourseHistory>> historyall() {
 
    List<EntityModel<CourseHistory>> coursehistories = coursehistoryrepository.findAll()
      .stream() //
      .map(coursehistoryassembler::toModel) //
      .collect(Collectors.toList());

    return CollectionModel.of(coursehistories, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel());
  }


  @GetMapping("/coursehistories/{id}")
  EntityModel<CourseHistory> one(@PathVariable String id) {

    CourseHistory coursehistory = coursehistoryrepository.findById(id) //
        .orElseThrow(() -> new ProfessorNotFoundException(id));
  
    return coursehistoryassembler.toModel(coursehistory);
  }

  @GetMapping("/coursehistories/browse/{year}")
  CollectionModel<EntityModel<CourseHistory>> histroyBrowseYear(@PathVariable int year) {

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
    
    return CollectionModel.of(cHistoryYear, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel());
  }

  @GetMapping("/coursehistories/browse/{year}/{smes}")
  CollectionModel<EntityModel<CourseHistory>> histroyBrowseYearSmes(@PathVariable int year, @PathVariable int smes) {

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
    
    return CollectionModel.of(cHistoryYear, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel());
  }

  @GetMapping("/professors/search/{name}/courses")
  CollectionModel<EntityModel<CourseHistory>> professorCourse(@PathVariable String name) {
    String polishedName = name.replaceAll("\\s+", "").toLowerCase();

    List<Professor> NameProfessors = repository.findAll()
      .stream()
      .filter(professor -> {
        String targetName = professor.getName().replaceAll("\\s+", "").toLowerCase();
        if(targetName.indexOf(polishedName) >= 0)
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
    return CollectionModel.of(ProfessorCourse, linkTo(methodOn(ProfessorController.class).historyall()).withSelfRel());
  }
}
