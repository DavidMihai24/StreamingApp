GHELESEL DAVID-MIHAI

Application for managing a streaming service

The data (streams, streamers, users) is stored in csv files (in the /src/resources folder). The system performs the following actions: 
- add stream
- lists streams of a given streamer
- lists streams of a given user
- delete stream
- recommend stream
- keep track of the streamers an user listens to

  I implemented the project using 4 Design Patterns:
  1. Singleton - ensures that only one instance of the class is created throughout the application's lifecycle;
  2. Command - encapsulates commands (loading data, adding streams etc) into separate command objects
  3. Factory - the createStream() method acts as a factory method.
  4. Observer - users "observe" (listen to streams) and the data is updated accordingly.
 
  The main method handles each action depending on the input from the command line, performing a clean-up action when the program ends.
