package milestone2;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

@RestController
class TendencyController {

  private final TendencyRepository repository;

  private final TendencyModelAssembler assembler;

  TendencyController(TendencyRepository repository, TendencyModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/tendency")
  CollectionModel<EntityModel<Tendency>> all() {
 
    List<EntityModel<Tendency>> tendency = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(tendency, linkTo(methodOn(TendencyController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/tendency")
  ResponseEntity<?> newTendency(@RequestBody Tendency newTendency) {

    EntityModel<Tendency> entityModel = assembler.toModel(repository.save(newTendency));
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // Single item
  
  @GetMapping("/tendency/{id}")
  EntityModel<Tendency> one(@PathVariable String id) {

    Tendency tendency = repository.findById(id) //
        .orElseThrow(() -> new TendencyNotFoundException(id));
  
    return assembler.toModel(tendency);
  }

  @PutMapping("/tendency/{id}")
  ResponseEntity<?> replaceTendency(@RequestBody Tendency newTendency, @PathVariable String id) {

    Tendency updatedTendency = repository.findById(id) //
        .map(tendency -> {
          tendency.setName(newTendency.getName());
          tendency.setRole(newTendency.getRole());
          return repository.save(tendency);
        })
        .orElseGet(() -> {
          newTendency.setId(id);
          return repository.save(newTendency);
        });
  
    EntityModel<Tendency> entityModel = assembler.toModel(updatedTendency);
  
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/tendency/{id}")
  ResponseEntity<?> deleteTendency(@PathVariable String id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }
}
