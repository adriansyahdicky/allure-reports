package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.LogListenerUtil;

@Listeners(LogListenerUtil.class)
@Epic("Todo Regression Testing")
@Feature("Todo Testing")
public class AllureTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup() {
        requestSpecification = RestAssured.given();
        requestSpecification.log()
                .all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType(ContentType.JSON);

        responseSpecification = RestAssured.given().expect();
        responseSpecification.log()
                .all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .time(Matchers.lessThan(5000L));
    }

    @Test(priority = 0, description = "Get Data Todos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Testing Get Data Todos By ID")
    public void getDataTodosById(){
        RestAssured
                .given()
                .spec(requestSpecification)
                .get("/todos/1")
                .then()
                .log()
                .all()
                .spec(responseSpecification);
    }

}
