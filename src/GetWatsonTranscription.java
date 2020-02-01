import java.io.*;

public class GetWatsonTranscription {

    public GetWatsonTranscription() {
    }

    public void getTranscription () throws IOException {
        String[] envp = { "PYTHONPATH=api/python-sdk" };
        String command = "According to wikipedia, who is bill gates";
        String executable = "python3 api/python-sdk/examples/speech_to_text_v1.py";
        Process process = Runtime.getRuntime().exec(executable, envp);
        String line;
        System.out.println("Output:");
//        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        while ((line = br.readLine()) != null) {
//            System.out.print("Output is ");
//            System.out.println(line);
//        }
//        System.out.println("Errors:");
//        br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//        while ((line = br.readLine()) != null) {
//            System.out.print("Error is ");
//            System.out.println(line);
//        }
    }
    public static void main (String[] args) throws IOException {
        GetWatsonTranscription transcription = new GetWatsonTranscription();
        transcription.getTranscription();
        System.out.println("LEts see");
    }
}

