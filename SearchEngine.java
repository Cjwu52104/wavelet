import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine implements URLHandler {
    ArrayList<String> words = new ArrayList<String>();
    int size = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return words.toString();
        } else if (url.getPath().equals("/search")) {
            ArrayList<String> res = new ArrayList<String>();
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for (String word: words) {
                    if (word.contains(parameters[1])) {
                        res.add(word);
                    }
                }
            }
            return res.toString();
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    words.add(parameters[1]);
                }
                return String.format("Added %s to the word list", parameters[1]);
            }
            return "404 Not Found!";
        }
    }
    
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchEngine());
    }
}
