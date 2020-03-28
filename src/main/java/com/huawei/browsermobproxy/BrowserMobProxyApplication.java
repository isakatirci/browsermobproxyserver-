package com.huawei.browsermobproxy;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.proxy.auth.AuthType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrowserMobProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrowserMobProxyApplication.class, args);

        BrowserMobProxyServer mobProxyServer = new BrowserMobProxyServer();
        mobProxyServer.setTrustAllServers(true);
		mobProxyServer.autoAuthorization("", "","", AuthType.BASIC);

	/*	//https://stackoverflow.com/questions/50775287/unable-to-perfrom-http-basic-auth-using-browsermob-proxy
		String encodedCreadentials = "Basic " + (Base64.getEncoder().encodeToString("login:password".getBytes()));
		proxy.addHeader("Authorization", encodedCreadentials);*/


        mobProxyServer.start();


        System.out.println("=================================================================");
        System.out.println("This Execute Browser Port --> " + mobProxyServer.getPort());
        System.out.println("=================================================================");

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(mobProxyServer);
        seleniumProxy.setHttpProxy("localhost:" + mobProxyServer.getPort());
        seleniumProxy.setSslProxy("localhost:" + mobProxyServer.getPort());


/*        DesiredCapabilities seleniumCapabilities = new DesiredCapabilities();
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy);*/

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setProxy(seleniumProxy);
        //System.setProperty("webdriver.gecko.driver","C:\\Users\\isa\\Desktop\\browser-mob-proxy\\browser-mob-proxy\\src\\main\\resources\\geckodriver.exe");
        //WebDriver driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\isa\\Desktop\\browser-mob-proxy\\browser-mob-proxy\\src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        driver.get("https://www.sahibinden.com/");

        driver.close();
        mobProxyServer.stop();
    }


}
