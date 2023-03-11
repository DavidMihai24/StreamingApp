import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LoadStreamsCommand implements Command {

  //private ArrayList<Stream> streams = new ArrayList<>();

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
        Integer streamType = Integer.parseInt(values[0]);
        Integer streamId = Integer.parseInt(values[1]);
        Integer streamGenre = Integer.parseInt(values[2]);
        Long noOfStreams = Long.parseLong(values[3]);
        Integer streamerId = Integer.parseInt(values[4]);
        Long length = Long.parseLong(values[5]);
        Long dateAdded = Long.parseLong(values[6]);
        String name = "";
        for (int j = 7; j < values.length; j++) {
          name += values[j] + ",";
        }
        if (name.startsWith("\"Taki")) {
          name = name.substring(1, name.length() - 2);
        }
        else {
          name = name.substring(0, name.length() - 1);
        }
        ProiectPOO.getInstance().getStreams().add(
            ProiectPOO.getInstance().createStream(streamType, streamId, streamGenre, noOfStreams, streamerId, length, dateAdded, name));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}