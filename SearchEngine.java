import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine implements URLHandler {
    ArrayList<String> words;
    int size = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return words.toString();
        } else if (url.getPath().equals("/search")) {
            ArrayList<String> res = new ArrayList<String>();
            String[] parameters = url.getQuery().split("=");
            int len = parameters.length;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < size; j++) {
                    String currentWord = words.get(j);
                    if (currentWord.contains(parameters[i])) {
                        res.add(currentWord);
                    }
                }
            }
            return res.toString();
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                int len = parameters.length;
                for (int i = 0; i < len; i++) {
                    words.add(parameters[i]);
                    ++size;
                }
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

        Server.start(port, new Handler());
    }
}
