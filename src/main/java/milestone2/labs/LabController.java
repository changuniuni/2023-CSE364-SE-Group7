package milestone2.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

@RestController
class LabController {

  private final LabRepository repository;

  private final LabModelAssembler assembler;

  LabController(LabRepository repository, LabModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/labs")
  CollectionModel<EntityModel<Lab>> all() {
 
    List<EntityModel<Lab>> labs = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(labs, linkTo(methodOn(LabController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/labs")
  ResponseEntity<?> newLab(@RequestBody Lab newLab) {

    EntityModel<Lab> entityModel = assembler.toModel(repository.save(newLab));
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // Single item
  
  @GetMapping("/labs/{id}")
  EntityModel<Lab> one(@PathVariable String id) {

    Lab lab = repository.findById(id) //
        .orElseThrow(() -> new LabNotFoundException(id));
  
    return assembler.toModel(lab);
  }

  @PutMapping("/labs/{id}")
  ResponseEntity<?> replaceLab(@RequestBody Lab newLab, @PathVariable String id) {

    Lab updatedLab = repository.findById(id) //
        .map(lab -> {
          lab.setName(newLab.getName());
          lab.setArea(newLab.getArea());
          lab.setTopic(newLab.getTopic());
          lab.setDesc(newLab.getDesc());
          lab.setEmail(newLab.getEmail());
          lab.setOffice(newLab.getOffice());
          return repository.save(lab);
        })
        .orElseGet(() -> {
          newLab.setId(id);
          return repository.save(newLab);
        });
  
    EntityModel<Lab> entityModel = assembler.toModel(updatedLab);
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/labs/{id}")
  ResponseEntity<?> deleteLab(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/labs/search/{findarea}")
  CollectionModel<EntityModel<Lab>> AreaSearch(@PathVariable String findarea) {
    List<EntityModel<Lab>> AreaLabs = repository.findAll()
      .stream()
      .filter(lab -> new ArrayList<>(Arrays.asList(lab.getArea())).contains(findarea))
      .map(assembler::toModel)
      .collect(Collectors.toList());
    
    return CollectionModel.of(AreaLabs, linkTo(methodOn(LabController.class).all()).withSelfRel());
  }
}
