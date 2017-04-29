import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MedianAdmit {

    public static void main(String[] args) throws IOException, InterruptedException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("/Users/jinwoo/Downloads/median-234c4-firebase-adminsdk-h4qdn-1342f4f46c.json"))
                .setDatabaseUrl("https://median-234c4.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        NoticeReader noticeReader = new NoticeReader(ref);

        noticeReader.readNotice();

        while(true){
            noticeReader.checkNewNotice();
            TimeUnit.SECONDS.sleep(5);
        }
    }
}


