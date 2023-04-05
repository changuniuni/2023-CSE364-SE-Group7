package milestone1.combined;

class EmployeeNotFoundException extends RuntimeException {
 
  EmployeeNotFoundException(String id) {
    super("Could not find employee " + id);
  }
}
