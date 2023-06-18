package milestone3.professors;
  
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ProfessorModelAssembler implements RepresentationModelAssembler<Professor, EntityModel<Professor>> {

  @Override
  public EntityModel<Professor> toModel(Professor professor) {

    return EntityModel.of(professor, //
        linkTo(methodOn(ProfessorController.class).searchPhone(professor.getId())).withSelfRel(),
        linkTo(methodOn(ProfessorController.class).all()).withRel("professors"));
  }
}