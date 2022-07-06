package utils;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogListenerUtil implements ITestListener {
    private final ByteArrayOutputStream request = new ByteArrayOutputStream();
    private final ByteArrayOutputStream response = new ByteArrayOutputStream();
    private final PrintStream requestVar = new PrintStream(request, true);
    private final PrintStream responseVar = new PrintStream(response, true);

    @Override
    public void onStart(ITestContext context) {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL, requestVar),
                new ResponseLoggingFilter(LogDetail.BODY, responseVar));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logRequest(request);
        logResponse(response);
    }

    @Override
    public void onTestFailure(ITestResult result) {
    }

    @Attachment(value = "request")
    public byte[] logRequest(final ByteArrayOutputStream stream){
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(final ByteArrayOutputStream stream){
        return attach(stream);
    }

    public byte[] attach(final ByteArrayOutputStream log){
        final byte[] array = log.toByteArray();
        log.reset();
        return array;
    }
}
