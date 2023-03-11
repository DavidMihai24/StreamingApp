public class Song extends Stream {
  public Song(Integer id, Integer streamGenre, Long noOfStreams,
      Integer streamerId, Long length, Long dateAdded, String name) {
    super(1, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
  }
}
