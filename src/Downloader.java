import java.io.File;

public class Downloader {

    private String url;

    public Downloader (String url){
        this.url = url;
    }

    public void createFolder (){
        File directory = new File("/Webpages");
        if (!directory.exists()){
            directory.mkdir();
            directory.setWritable(true, true);
        }
        return;
    }

  //  public void downloadWebpages ()
}
