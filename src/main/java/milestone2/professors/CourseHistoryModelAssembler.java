package milestone2.professors;
 
import milestone2.courses.*;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CourseHistoryModelAssembler implements RepresentationModelAssembler<CourseHistory, EntityModel<CourseHistory>> {

  @Override
  public EntityModel<CourseHistory> toModel(CourseHistory coursehistory) {

    return EntityModel.of(coursehistory, //
        linkTo(methodOn(CourseController.class).courseShowOne(coursehistory.getCourseId())).withSelfRel(),
        linkTo(methodOn(ProfessorController.class).historyall()).withRel("coursehistories"));
  }
}