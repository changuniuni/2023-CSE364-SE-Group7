package milestone2.labs;
  
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class LabModelAssembler implements RepresentationModelAssembler<Lab, EntityModel<Lab>> {

  @Override
  public EntityModel<Lab> toModel(Lab lab) {

    return EntityModel.of(lab, //
        linkTo(methodOn(LabController.class).one(lab.getId())).withSelfRel(),
        linkTo(methodOn(LabController.class).all()).withRel("labs"));
  }
}