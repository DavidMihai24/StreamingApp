public class Streamer {
  Integer streamerType;
  Integer id;
  String name;

  public Streamer(Integer streamerType, Integer id, String name) {
    this.id = id;
    this.name = name;
    this.streamerType = streamerType;
  }

  public String toString() {
    return "Streamer: " + name + " with id: " + id + " and type: " + streamerType;
  }

  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public Integer getStreamerType() {
    return streamerType;
  }

  public void setStreamerType(Integer streamerType) {
    this.streamerType = streamerType;
  }
}
