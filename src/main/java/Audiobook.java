public class Audiobook extends Stream {
  public Audiobook(Integer id, Integer streamGenre, Long noOfStreams,
      Integer streamerId, Long length, Long dateAdded, String name) {
    super(3, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
  }
}