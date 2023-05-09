package milestone2.professors;

class ProfessorNotFoundException extends RuntimeException {
 
  ProfessorNotFoundException(String id) {
    super("Could not find professor " + id);
  }
}
