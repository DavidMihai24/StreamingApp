import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LoadUsersCommand implements Command {

  public void execute(String fileName) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      int j = 0;
      while ((line = br.readLine()) != null) {
        if (j == 0) {
          j++;
          continue;
        }
        String[] values = line.split(",");
        int id;
        String name;
        List<Integer> numbersList;
        if (values[0].startsWith("\"")) {
          id = Integer.parseInt(values[0].substring(1, values[0].length() - 1));
          name = values[1].substring(1, values[1].length() - 1);
          String numbers = values[2].substring(1, values[2].length() - 1);
          numbersList = new ArrayList<>();
          String[] numbersArray = numbers.split(" ");
          for (int i = 0; i < numbersArray.length; i++) {
            numbersList.add(Integer.parseInt(numbersArray[i]));
          }
        }
        else {
          id = Integer.parseInt(values[0]);
          name = values[1];
          String numbers = values[2];
          numbersList = new ArrayList<>();
          String[] numbersArray = numbers.split(" ");
          for (int i = 0; i < numbersArray.length; i++) {
            numbersList.add(Integer.parseInt(numbersArray[i]));
          }
        }
        ProiectPOO.getInstance().getUsers().add(new User(id, name, numbersList));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}