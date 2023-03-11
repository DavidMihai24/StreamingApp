import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

abstract class Stream {
  Integer streamType;
  Integer id;
  Integer streamGenre;
  Long noOfStreams;
  Integer streamerId;
  Long length;
  Long dateAdded;
  String name;
  String auxDateAdded;

  protected Stream(Integer streamType, Integer id, Integer streamGenre, Long noOfStreams,
      Integer streamerId, Long length, Long dateAdded, String name) {
    this.streamType = streamType;
    this.id = id;
    this.streamGenre = streamGenre;
    this.noOfStreams = noOfStreams;
    this.streamerId = streamerId;
    this.length = length;
    this.dateAdded = dateAdded;
    this.name = name;
    this.auxDateAdded = convertDateAdded();
  }

  public String convertDateAdded() {
    Date date = new Date(dateAdded * 1000L);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    return sdf.format(date);
  }

  public String convertStreamLength() {
    long hours = length / 3600;
    long minutes = (length % 3600) / 60;
    long seconds = length % 60;

    if (hours >= 1) {
      return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    } else {
      return String.format("%02d:%02d", minutes, seconds);
    }
  }

  public String toString() {
    String streamerName = "";
    for (Streamer streamer : ProiectPOO.getInstance().getStreamers()) {
      if (streamer.getId().equals(streamerId)) {
        streamerName = streamer.getName();
      }
    }
    return "{" + "\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"streamerName\":\""
        + streamerName + "\",\"noOfListenings\":\"" + noOfStreams +
        "\",\"length\":\"" + convertStreamLength() + "\",\"dateAdded\":\"" + convertDateAdded() + "\"}";
  }
}