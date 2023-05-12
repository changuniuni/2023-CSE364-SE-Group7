package milestone2;
  
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class TendencyModelAssembler implements RepresentationModelAssembler<Tendency, EntityModel<Tendency>> {

  @Override
  public EntityModel<Tendency> toModel(Tendency tendency) {

    return EntityModel.of(tendency, //
        linkTo(methodOn(TendencyController.class).one(tendency.getId())).withSelfRel(),
        linkTo(methodOn(TendencyController.class).all()).withRel("tendency"));
  }
}