package milestone2;

class CourseNotFoundException extends RuntimeException {
 
  CourseNotFoundException(String id) {
    super("Could not find course " + id);
  }
}
