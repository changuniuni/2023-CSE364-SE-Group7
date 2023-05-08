package milestone2.labs;

class LabNotFoundException extends RuntimeException {
 
  LabNotFoundException(String id) {
    super("Could not find lab " + id);
  }
}
