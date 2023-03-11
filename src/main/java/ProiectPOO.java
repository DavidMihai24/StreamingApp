import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProiectPOO {

    private static ProiectPOO instance = null;

    private static ArrayList<Streamer> streamers = new ArrayList<>();
    private static ArrayList<Stream> streams = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();

    private ProiectPOO() {

    }

    public static ProiectPOO getInstance() {
        if(instance == null) {
            instance = new ProiectPOO();
        }
        return instance;
    }



    public ArrayList<Streamer> getStreamers() {
        return streamers;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public static void main(String[] args) {

        if(args == null) {
            System.out.println("Nothing to read here");
        }

        else {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();

            String resources = "/src/main/resources/";
            final String COMMANDS_FILE = s + resources + args[3];
            final String STREAMERS_FILE = s + resources + args[0];
            final String STREAMS_FILE = s + resources + args[1];
            final String USERS_FILE = s + resources + args[2];

            LoadStreamersCommand loadStreamersCommand = new LoadStreamersCommand();
            loadStreamersCommand.execute(STREAMERS_FILE);

            LoadStreamsCommand loadStreamsCommand = new LoadStreamsCommand();
            loadStreamsCommand.execute(STREAMS_FILE);

            LoadUsersCommand loadUsersCommand = new LoadUsersCommand();
            loadUsersCommand.execute(USERS_FILE);

            List<String> commands = FileOperations.readFromFile(COMMANDS_FILE, false);

            for (int i = 0; i < commands.size(); i++) {
                String[] arrayOfCommands = commands.get(i).split(" ");
                if (arrayOfCommands[1].equals("ADD")) {
                    for (int a = 7; a < arrayOfCommands.length; a++) {
                        arrayOfCommands[6] += " " + arrayOfCommands[a];
                    }
                    addStream(Integer.parseInt(arrayOfCommands[2]),
                        Integer.parseInt(arrayOfCommands[3]), Integer.parseInt(arrayOfCommands[4]),
                        Integer.parseInt(arrayOfCommands[0]), Long.parseLong(arrayOfCommands[5]),
                        arrayOfCommands[6]);
                }

                if (arrayOfCommands[1].equals("LIST")) {
                    for (int j = 0; j < streamers.size(); j++) {
                        if ((streamers.get(j).id == Integer.parseInt(arrayOfCommands[0]))) {
                            listStreamsOfGivenStreamer(Integer.parseInt(arrayOfCommands[0]));
                        }
                    }
                    for (int j = 0; j < users.size(); j++) {
                        if (users.get(j).id == Integer.parseInt(arrayOfCommands[0])) {
                            listStreamsOfGivenUser(Integer.parseInt(arrayOfCommands[0]));
                        }
                    }
                }
                if (arrayOfCommands[1].equals("DELETE")) {
                    deleteStream(Integer.parseInt(arrayOfCommands[2]));
                }
                if (arrayOfCommands[1].equals("LISTEN")) {
                    userListensToStream(arrayOfCommands[0], arrayOfCommands[2]);
                }
                if (arrayOfCommands[1].equals("RECOMMEND")) {
                    recommend5StreamsToUserAccordingToStreamersHeListensTo(
                        Integer.parseInt(arrayOfCommands[0]), arrayOfCommands[2]);
                }
                if (arrayOfCommands[1].equals("SURPRISE")) {
                    recommend3SurpriseStreamsByStreamersWhoTheUserDidNotListenToFromMostRecentlyAddedToLeastRecentlyAdded(
                        Integer.parseInt(arrayOfCommands[0]),
                        arrayOfCommands[2]);
                }
            }
        }
        cleanUp();
    }

    public static void addStream(Integer streamType, Integer streamId, Integer streamGenre, Integer streamerId, Long length, String name) {
        Long dateAdded = System.currentTimeMillis() / 1000L;
        streams.add(0, createStream(streamType, streamId, streamGenre, 0L, streamerId, length, dateAdded, name));
    }

    public static void listStreamsOfGivenStreamer(Integer streamerId) {
        System.out.print("[");
        String s = "";
        for (int i = streams.size() - 1; i >= 0; i--) {
            if (streams.get(i).streamerId.equals(streamerId)) {
                s += streams.get(i).toString() + ",";
            }
        }
        s = s.substring(0, s.length() - 1);
        System.out.println(s + "]");
    }

    public static void listStreamsOfGivenUser(Integer userId) {
        System.out.print("[");
        String s = "";
        for (User user : users) {
            if (user.id.equals(userId)) {
                for (Integer streamId : user.streams) {
                    for (Stream stream : streams) {
                        if (stream.id.equals(streamId)) {
                            s += stream + ",";
                        }
                    }
                }
            }
        }
        s = s.substring(0, s.length() - 1);
        System.out.println(s + "]");
    }

    public static void deleteStream(Integer streamId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStreams().contains(streamId)) {
                users.get(i).getStreams().remove(streamId);
            }
        }
        for (int i = 0; i < streams.size(); i++) {
            if (streams.get(i).id.equals(streamId)) {
                streams.remove(i);
            }
        }
    }

    public static void userListensToStream(String userId, String streamId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id.equals(Integer.parseInt(userId))) {
                users.get(i).getStreams().add(Integer.parseInt(streamId));
            }
        }
        for (int i = 0; i < streams.size(); i++) {
            if (streams.get(i).id.equals(Integer.parseInt(streamId))) {
                streams.get(i).noOfStreams++;
            }
        }
    }

    public static void sortStreamsAccordingToNoOfStreamsFromHighToLow() {
        for (int i = 0; i < streams.size(); i++) {
            for (int j = i + 1; j < streams.size(); j++) {
                if (streams.get(i).noOfStreams < streams.get(j).noOfStreams) {
                    Stream temp = streams.get(i);
                    streams.set(i, streams.get(j));
                    streams.set(j, temp);
                }
            }
        }
    }

    public static List<Streamer> listStreamersAnUserListensTo(Integer userId) {
        List<Streamer> streamersUserListensTo = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id.equals(userId)) {
                for (int j = 0; j < streams.size(); j++) {
                    if (users.get(i).streams.contains(streams.get(j).id)) {
                        for (int k = 0; k < streamers.size(); k++) {
                            if (streams.get(j).streamerId.equals(streamers.get(k).id)) {
                                streamersUserListensTo.add(streamers.get(k));
                            }
                        }
                    }
                }
            }
        }
        return streamersUserListensTo;
    }

    public static void recommend5StreamsToUserAccordingToStreamersHeListensTo(Integer userId,
        String streamType) {
        sortStreamsAccordingToNoOfStreamsFromHighToLow();
        List<Stream> recommendedStreams = new ArrayList<>();
        System.out.print("[");
        String s = "";
        switch (streamType) {
            case "SONG":
                streamType = "1";
                break;
            case "PODCAST":
                streamType = "2";
                break;
            case "AUDIOBOOK":
                streamType = "3";
                break;
            default:
                break;
        }
        for (int i = 0; i < listStreamersAnUserListensTo(userId).size(); i++) {
            for (int j = 0; j < streams.size(); j++) {
                if (listStreamersAnUserListensTo(userId).get(i).id.equals(streams.get(j).streamerId)
                    && streams.get(j).streamType.equals(Integer.parseInt(streamType))) {
                    recommendedStreams.add(streams.get(j));
                }
            }
        }
        for (int i = 0; i < recommendedStreams.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                if (users.get(j).id.equals(userId)) {
                    if (!users.get(j).streams.contains(recommendedStreams.get(i).id)) {
                        s += recommendedStreams.get(i).toString() + ",";
                    }
                }
            }
        }
        s = s.substring(0, s.length() - 1);
        System.out.println(s + "]");
    }

    public static void sortStreamsAccordingToDateAddedFromMostRecentlyAddedToLeastRecentlyAdded() {
        for (int i = 0; i < streams.size(); i++) {
            for (int j = i + 1; j < streams.size(); j++) {
                if (streams.get(i).dateAdded < streams.get(j).dateAdded) {
                    Stream temp = streams.get(i);
                    streams.set(i, streams.get(j));
                    streams.set(j, temp);
                }
                else if (streams.get(i).dateAdded.equals(streams.get(j).dateAdded)) {
                    if (streams.get(i).noOfStreams < streams.get(j).noOfStreams) {
                        Stream temp = streams.get(i);
                        streams.set(i, streams.get(j));
                        streams.set(j, temp);
                    }
                }
            }
        }
    }

    public static List<Integer> getListenedToStreamersById(Integer userId) {
        List<Integer> streamersUserListensTo = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id.equals(userId)) {
                for (int j = 0; j < streams.size(); j++) {
                    if (users.get(i).streams.contains(streams.get(j).id)) {
                        for (int k = 0; k < streamers.size(); k++) {
                            if (streams.get(j).streamerId.equals(streamers.get(k).id)) {
                                streamersUserListensTo.add(streamers.get(k).id);
                            }
                        }
                    }
                }
            }
        }
        return streamersUserListensTo;
    }

    public static void recommend3SurpriseStreamsByStreamersWhoTheUserDidNotListenToFromMostRecentlyAddedToLeastRecentlyAdded(
        Integer userId, String streamType) {
        sortStreamsAccordingToDateAddedFromMostRecentlyAddedToLeastRecentlyAdded();
        List<Stream> surpriseStreams = new ArrayList<>();
        List<Integer> notListenedToStreamers = new ArrayList<>();

        String s = "";
        System.out.print("[");
        switch (streamType) {
            case "SONG":
                streamType = "1";
                break;
            case "PODCAST":
                streamType = "2";
                break;
            case "AUDIOBOOK":
                streamType = "3";
                break;
            default:
                break;
        }

        for (Streamer streamer : streamers) {
            if (!getListenedToStreamersById(userId).contains(streamer.id)) {
                notListenedToStreamers.add(streamer.id);
            }
        }

        for (int i = 0; i < streams.size(); i++) {
            for (int j = 0; j < notListenedToStreamers.size(); j++) {
                if (streams.get(i).streamerId.equals(notListenedToStreamers.get(j))) {
                    surpriseStreams.add(streams.get(i));
                }
            }
        }

        for (int i = 0; i < surpriseStreams.size() && i < 3; i++) {
            s += surpriseStreams.get(i).toString() + ",";
        }
        s = s.substring(0, s.length() - 1);
        System.out.println(s + "]");
    }

    public static Stream createStream(Integer streamType, Integer id, Integer streamGenre, Long noOfStreams,
        Integer streamerId, Long length, Long dateAdded, String name) {
        switch (streamType) {
            case 1:
                return new Song(id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            case 2:
                return new Podcast(id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            case 3:
                return new Audiobook(id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            default:
                return null;
        }
    }

    public static void cleanUp() {
        users.clear();
        streamers.clear();
        streams.clear();
    }
}