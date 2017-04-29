import com.google.firebase.database.DatabaseReference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.Objects;

public class NoticeReader {
    private DatabaseReference reference;
    private String lastTitle;
    private String baseUrl;
    private HtmlReader htmlReader;

    public NoticeReader(DatabaseReference ref) {
        this.reference = ref;
        baseUrl = "http://media.ajou.ac.kr/media/board/board01.jsp?mode=list&board_no=304&pager.offset=";
        htmlReader = new HtmlReader();
    }

    public void readNotice() throws FileNotFoundException {

        int offset = 0;

        while (offset < 50) {
//            String strUrl = "http://media.ajou.ac.kr/media/board/board01.jsp?mode=list&board_no=304&pager.offset=" + offset;

            String html = htmlReader.readHTML(baseUrl + offset);

            Document document = Jsoup.parse(html);
            Elements elements = document.select("td.title_comm > a:eq(0)");
//            Elements numbers = document.select("td.al_center:eq(0)");
            if (offset == 0)
                lastTitle = elements.first().text();

            if (elements.size() == 0)
                break;

            for (Element e : elements) {
                Document boardContents = Jsoup.parse(htmlReader.
                        readHTML("http://media.ajou.ac.kr/media/board/board01.jsp" + e.attr("href")));
                Elements contents = boardContents.select("div#article_text");

                for (Element element : contents) {
                    Notice notice = new Notice(e.text(), element.toString());
                    reference.child("media_notice").push().setValue(notice);
                }
            }
            offset += 10;
        }
        System.out.println("종료");

    }

    public void checkNewNotice() {
        String html = htmlReader.readHTML(baseUrl + 0);
        Document document = Jsoup.parse(html);
        Elements elements = document.select("td.title_comm > a:eq(0)");

        System.out.println(lastTitle);
        System.out.println(elements.get(0).text());

        if (!Objects.equals(lastTitle, elements.get(0).text())) {
            System.out.println("새글");

            Document boardContents = Jsoup.parse(htmlReader.
                    readHTML("http://media.ajou.ac.kr/media/board/board01.jsp" + elements.get(0).attr("href")));
            Elements contents = boardContents.select("div#article_text");

            for (Element element : contents) {
                Notice notice = new Notice(elements.get(0).text(), element.toString());
                reference.child("media_notice").push().setValue(notice);
            }
        } else {
            System.out.println("새글 아님");
        }
    }

}
