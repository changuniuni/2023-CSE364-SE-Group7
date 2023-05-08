package milestone2.courses;

class CourseNotFoundException extends RuntimeException {
 
  CourseNotFoundException(String id) {
    super("Could not find course " + id);
  }
}
