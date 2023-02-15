package utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class API {


    @Test
    public void getTestCase() {

        //Retrieve the Test Case matching the given key.
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("testCaseKey", "INSERT KEY").
                when().
                get(VAR.BASE_URI_2 + "rest/atm/1.0/testcase/{testCaseKey}").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }

    @Test
    public void getFeatureFiles() {

        //Retrieve a zip file containing Cucumber Feature Files.
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                queryParam("tql", "testCase.projectKey = 'INSERT PROJECT KEY'").
                when().
                get(VAR.BASE_URI_2 + "rest/atm/1.0/automation/testcases").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }


    @Test
    public void postJunitTestResults() {

        //Creates a new Test Cycle based on provided automated test results.
        File file = new File(System.getProperty("user.dir") + "/target/cucumber/result.zip");
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                multiPart(file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("projectKey", "INSERT PROJECT KEY").
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/automation/execution/{projectKey}").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }

    @Test
    public void postCucumberTestResults() {

        //Creates a new Test Cycle based on provided automated test results.
        File file = new File(System.getProperty("user.dir") + "/target/cucumber/result.zip");
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                multiPart(file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("projectKey", "INSERT PROJECT KEY").
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/automation/execution/cucumber/{projectKey}").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }

    @Test
    public void postAttachmentToTestRun() {

        //Attaches File to Test Run
        File file = new File(System.getProperty("user.dir") + "/target/cucumber/result.html");
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                multiPart(file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("testRunKey", "INSERT TEST RUN KEY").
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/testrun/{testRunKey}/attachments").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }

    @Test
    public void postAttachmentToTestCase() {

        //Attaches File to Test Case
        File file = new File(System.getProperty("user.dir") + "/target/cucumber/result.html");
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                multiPart(file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("testCaseKey", "INSERT TEST CASE KEY").
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/testcase/{testCaseKey}/attachments").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }

    @Test
    public void postAttachmentToTestPlan() {

        //Attaches File to Test Plan
        File file = new File(System.getProperty("user.dir") + "/target/cucumber/result.html");
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                multiPart(file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("testCaseKey", "INSERT TEST PLAN KEY").
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/testplan/{testPlanKey}/attachments").
                then().
                extract().
                response();

        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body : " + response.getBody().asString());
    }

    public static void postCucumberTestResultsAndResultsAttachment() {

        //Part-1 Send Zip and Store Test Run Key
        File zip_file = new File(System.getProperty("user.dir") + "/target/cucumber/result.zip");

        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().
                multiPart(zip_file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/automation/execution/cucumber/SRS").
                then().
                contentType(ContentType.JSON).
                extract().
                response();

        System.out.println("Status Code 1 : " + response.statusCode());
        System.out.println("Response Body 1 : " + response.getBody().asString());

        //Part-2 Use Test Run Key to Post Attachment File
        File html_file = new File(System.getProperty("user.dir") + "/target/cucumber/result.html");
        String testRunKey = response.jsonPath().getString("testCycle.key");

        Response response2 = given().
                multiPart(html_file).
                headers(
                        "Authorization",
                        "Bearer " + VAR.BEARER_TOKEN).
                contentType("multipart/form-data").
                pathParam("testRunKey", testRunKey).
                when().
                post(VAR.BASE_URI_2 + "rest/atm/1.0/testrun/{testRunKey}/attachments").
                then().
                extract().
                response();

        System.out.println("Status Code 2 : " + response2.statusCode());
        System.out.println("Response Body 2 : " + response2.getBody().asString());

    }

}
