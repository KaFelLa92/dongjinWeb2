package example.day05._2웹크롤링;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // 스프링이 컨테이너(메모리) 빈(객체) IOC (제어역전)
// 스프링 MVC 패턴의 비즈니스 로직을 담당
public class CrawlingService {

    // 1. 다음 날씨 정보 크롤링
    public Map<String, String> task1() {
        // 1-1 : 크롬 설치
        WebDriverManager.chromedriver().setup();
        // 1-2 : 크롬 옵션
        ChromeOptions chromeOptions = new ChromeOptions();
        // * 크롬은 사용하지만 크롬브라우저 창은 띄우지 않는다.
        chromeOptions.addArguments("--headless=new", "--disable-gpu");
        // 1-3 : 크롬 옵션을 웹드라이버(셀레니움)에 객체 생성
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        // 1-4 : 크롤링할 웹주소
        String URL = "https://weather.daum.net/";
        // 1-5 : 셀레니움(웹드라이버)으로 크롤링할 웹주소 가져오기
        webDriver.get(URL);
        // 1-6 : 셀레니움(웹드라이버) 잠시 대기 , new WebDriverWait( 셀레니움객체 , D )
        // * 왜 대기하는가? 동적페이지는 JS(fetch)가 정보를 가져올때까지 정보가 없으므로 기다린다.
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        // 1-7 : 대기후 크롤링할 HTML CSS 분석하기 , 권장 : 식별자 1개가 아닌, 상위 식별자를 넣어 중복 방지
        // wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector(" 크롤링할선택자 ") ))
        // (1) 지역 , .info_location .tit_location
        // (2) 온도 , .wrap_weather .num_deg
        // (3) 날씨 , .wrap_weather .txt_sub
        WebElement location = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".info_location .tit_location")));
        WebElement temper = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".wrap_weather .num_deg")));
        WebElement weather = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".wrap_weather .txt_sub")));
        // 1-8 : 크롤링한 요소(HTML마크업)의 텍스트를 추출하여 map 저장
        Map<String, String> map = new HashMap<>();
        map.put("위치 : ", location.getText());
        map.put("온도 : ", temper.getText());
        map.put("상태 : ", weather.getText());
        // 1-9 : 반환
        return map;

    } // func end

    // 2. CGV 영화 리뷰
    public List<String> task2() {
        // 2-1 : 크롬 설치
        WebDriverManager.chromedriver().setup();
        List<String> list = new ArrayList<>();
        // 2-9 : 아래 작업들을 i번 반복
        for (int i = 1; i <= 5; i++) {
            // 2-2 : 크롬 옵션 , 브라우저 창 사용안함
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new", "--disable-gpu");
            // 2-3 : 셀레니움(웹드라이버) 객체 생성
            WebDriver webDriver = new ChromeDriver(chromeOptions);
            // 2-4 : 크롤링 웹주소
            String URL = "https://cgv.co.kr/cnm/cgvChart/movieChart/89706";
            // 2-5 : 셀레니움으로 크롤링할 웹 주소 가져오기
            webDriver.get(URL);
            // [*] 자바에서 JS 사용
            // 2-6 : 리뷰 (.reveiwCard_txt__RrTgu) 여러개 가져오기
            // 1개 : WebElement element = webDriver.findElement();
            // N개 : List<WebElement> webElements = webDriver.findElements();
            List<WebElement> nick = webDriver.findElements(By.cssSelector(".reveiwCard_feedCard__3fbaW .reveiwCard_name__mSHc9"));
            List<WebElement> state = webDriver.findElements(By.cssSelector(".reveiwCard_feedCard__3fbaW .reveiwCard_stat__lv18I"));
            List<WebElement> review = webDriver.findElements(By.cssSelector(".reveiwCard_feedCard__3fbaW .reveiwCard_txt__RrTgu"));
            // 2-7 : 가져온 리뷰들을 리스트에 담아보기
            int startCount = list.size();
            for (WebElement element : review) {
                String text = element.getText();
                if (list.contains(text)) {
                    continue; // 가장 가까운 반복문으로 이동
                }
                list.add(text);
            } // for end
            // ** 비어있거나 list에 추가적 내용이 없으면
            int endCount = list.size();
            if( startCount == endCount ) break;  // 2-9 반복문 종료

            // 2- : 자바스크립트 조작 객체 , 셀레니움 객체를 JS실행객체로 변환
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            // document.body(화면) 에서 최하단으로 스크롤 이동
            js.executeScript("window.scrollTo( 0 , document.body.scrollHeight ); ");
            try {
                Thread.sleep(1000);  // 1초 대기
            } catch (Exception e) {
                System.out.println(e);
            } // catch end
        }

//        for (int i = 0; i < review.size(); i++){
//            String name = nick.get(i).getText();
//            String status = state.get(i).getText();
//            String text = review.get(i).getText();
//        }

        // (1) 닉네임 : .reveiwCard_feedCard__3fbaW .reveiwCard_name__mSHc9
        // (2) 작성시간, 영화관 : .reveiwCard_feedCard__3fbaW .reveiwCard_stat__lv18I
        // (3) 리뷰 : .reveiwCard_feedCard__3fbaW .reveiwCard_txt__RrTgu


        return list;
    } // func end


    // [*] 시간별 날씨 List
    public List<Map<String, String>> task0() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new", "--disable-gpu");
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        String URL = "https://weather.daum.net/";
        webDriver.get(URL);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".box_tit .txt_box")));
        WebElement hour = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".item_hourly .screen_out")));
        WebElement icon = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tooltip_icon .ico_wcondition ico_wc4")));
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

        }
        return list;
    }


} // class end
