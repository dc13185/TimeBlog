import com.baidu.aip.ocr.AipOcr;
import com.timeblog.spilder.config.Sample;
import org.json.JSONObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.timeblog.spilder.utils.BASE64;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author: dong.chao
 * @create: 2020-01-04 15:29
 * @description:
 **/
public class SeisiumTest {

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://crm.test.telecomhb.com:8184/portal-web/main/mainPage");
       //填工号
        WebElement staffCode = driver.findElement(By.id("userName"));
        //密码
        WebElement pssword = driver.findElement(By.id("pwd"));
        staffCode.sendKeys("wh0306011");
        pssword.sendKeys("test_2019");
        //截图
        WebElement captchaImg = driver.findElement(By.id("captchaImg"));
        File imgFile = captchaImg.getScreenshotAs(OutputType.FILE);
        System.out.println(BASE64.encodeImgageToBase64(imgFile));
        //获取验证码
        String code = codeSrc(imgFile);
        WebElement validateCode = driver.findElement(By.id("validatecode"));
        validateCode.sendKeys(code);
        //找到登录按钮
       WebElement loginBtn = driver.findElement(By.id("loginBtn"));
       loginBtn.click();
       //找到校验的
        WebElement loginValidCode = driver.findElement(By.id("loginValidCode"));
        loginValidCode.click();
        WebElement msgLoginValid = driver.findElement(By.id("msgLoginValid"));
        msgLoginValid.click();

    }


    //进行验证码识别
    public static String codeSrc(File imgFile) throws IOException {
        AipOcr client = new AipOcr(Sample.APP_ID, Sample.API_KEY, Sample.SECRET_KEY);
        JSONObject res = client.basicGeneral(imgFile.getPath(), new HashMap<String, String>());
        String code = res.getJSONArray("words_result").getJSONObject(0).getString("words");
        System.out.println(code);
        return  code;
    }


    @Test
    public void  imageTest() throws IOException {
        File imgFile = new File("F:\\var\\code.jpg");
        System.out.println(codeSrc(imgFile));


    }

}
