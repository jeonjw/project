import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class JavaWebCrawler {


    public static void main(String[] args) throws IOException, InterruptedException {

        int recentBoardNum = 0;

        Document doc = Jsoup.connect("http://gall.dcinside.com/board/lists?id=overwatch").get();

        String baseUrl = "http://gall.dcinside.com/";


        Elements titles = doc.select("td.t_subject a:eq(0)");


        for(int i = 0 ; i < titles.size(); i++) {
            System.out.println(titles.get(i).text());

        }


    }

}

//        Document doc = Jsoup.connect("http://media.ajou.ac.kr/media/board/board01.jsp").get();
//        Elements title = doc.select("td.title_comm a");
//:eq(n) 은 같은 태그가 있을때 몇번쨰 태그를 가져올지를 결정