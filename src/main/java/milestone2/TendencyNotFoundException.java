package milestone2;

class TendencyNotFoundException extends RuntimeException {
 
  TendencyNotFoundException(String id) {
    super("Could not find tendency " + id);
  }
}
