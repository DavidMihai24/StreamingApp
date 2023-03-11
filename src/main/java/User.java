import java.util.List;

public class User {
  Integer id;
  String name;
  List<Integer> streams;

  public User(Integer id, String name, List<Integer> streams) {
    this.id = id;
    this.name = name;
    this.streams = streams;
  }

  public List<Integer> getStreams() {
    return streams;
  }
}