public class Podcast extends Stream {
  public Podcast(Integer id, Integer streamGenre, Long noOfStreams,
      Integer streamerId, Long length, Long dateAdded, String name) {
    super(2, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
  }
}
