import java.io.*;

public class GetAssistantTranscription {

    public GetAssistantTranscription(){
    }
    public void getTranscription () throws IOException {
        String command = "According to wikipedia, youtube";
        String executable = "python3 ~/PycharmProjects/AI-Project/assistant-sdk-python/google-assistant-sdk/googlesamples/assistant/grpc/textinput.py";
        Process process = Runtime.getRuntime().exec(String.join(" ", executable, command));
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = br.readLine()) != null) {
            System.out.print("Output is ");
            System.out.println(line);
        }
    }
    public static void main (String[] args) throws IOException {
        GetAssistantTranscription transcription = new GetAssistantTranscription();
        transcription.getTranscription();
        System.out.println("LEts see");
    }
}
