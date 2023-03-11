import java.io.BufferedReader;
import java.io.FileReader;

public class LoadStreamersCommand implements Command{

  @Override
  public void execute(String fileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        if (i == 0) {
          i++;
          continue;
        }
        String[] values = line.split(",");
        int streamerType;
        int streamerId;
        String streamerName;
        if(values[0].startsWith("\"")) {
          streamerType = Integer.parseInt(
              values[0].substring(1, values[0].length() - 1));
          streamerId = Integer.parseInt(
              values[1].substring(1, values[1].length() - 1));
          streamerName = values[2].substring(1, values[2].length() - 1);
        }
        else {
          streamerType = Integer.parseInt(values[0]);
          streamerId = Integer.parseInt(values[1]);
          streamerName = values[2];
        }
        ProiectPOO.getInstance().getStreamers().add(new Streamer(streamerType, streamerId, streamerName));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
