package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FBLoginPage extends BasePage {

    //Declaring page web elements;

    @FindBy(xpath="//input[@id='email']")
    private WebElement usernameTxtBox;

    @FindBy(xpath="//input[@id='pass']")
    WebElement passwordTxtBox;

    @FindBy(xpath="//*[@id='loginbutton']/input")
    WebElement loginBtn;

    //Constructor
    public FBLoginPage(WebDriver driver){

        super(driver);

        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);

    }

    //------------------Below are method implementation for the Page Class-------------------------------------------//

    /**
     * @description Method to Set user name in textbox
     * @param strUserName
     * @throws Exception
     */
    public void setUserName(String strUserName)throws Exception{

            usernameTxtBox.sendKeys(strUserName);
    }


    /**
     * @description Method to Set password in password textbox
     * @param strPassword
     * @throws Exception
     */
    public void setPassword(String strPassword)throws Exception{

        passwordTxtBox.sendKeys(strPassword);

    }

    /**
     * @description method to Click on login button
     * @throws Exception
     */
    public void clickLogin()throws Exception{

        loginBtn.click();
    }

    /**
     * @description Method to do login
     * @param url
     * @param usernm
     * @param pass
     * @throws Exception
     */
    public void doLogin(String url, String usernm, String pass) throws Exception {
        try{
            openURL(url);
            setUserName(usernm);
            setPassword(pass);
            clickLogin();

        }catch (Exception ex){
            log.error("validateLoginFn(): Test Failed: "+ ex.getStackTrace().toString());
            throw new Exception("Exception thrown at doLogin() method!!!");
        }
    }
    //Get the title of Login Page

    /**
     * @description Method to get window title
     * @return
     */
    public String getLoginTitle(){

        return    driver.getTitle();

    }
}
