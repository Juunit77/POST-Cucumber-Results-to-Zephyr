# POST-Cucumber-Results-to-Zephyr

Methods for posting Cucumber test result files to Zephyr Scale (v1) using Rest Assured.

Link to Zephyr v1 Documentation:
https://support.smartbear.com/zephyr-scale-server/api-docs/v1/

Summary:
- This document outlines the usage of zephyr scale endpoints for testing.
- The endpoints outlined here will be specific to test results and reporting.
- The relevant endpoints for our current zephyr version is version 1.0 or V1.

V1 Requests:

GET Test Case
BASE_URI + rest/atm/1.0/testcase/{testcasenumber}
- Retrieve the Test Case matching the given key.

public void getTestCase() {
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            when().
            get(Vars.BASE_URI + "rest/atm/1.0/testcase/SRS-T1").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

GET Feature Files
BASE_URI + rest/atm/1.0/automation/testcases/{tql}{}
tql = testCase.projectKey = 'SRS'
- Retrieve a zip file containing Cucumber Feature Files.

public void getFeatureFiles() {
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            queryParam("tql","testCase.projectKey = 'SRS'").
            when().
            get(Vars.BASE_URI + "rest/atm/1.0/testcases/").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

POST Junit Test Results
BASE_URI + rest/atm/1.0/automation/execution/{projectKey}
body = multipart/form-data
- Creates a new Test Cycle based on provided automated test results.

public static void postJunitTestResults() {
    File file=new File(System.getProperty("user.dir")+ "/target/cucumber/result.zip");
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            multiPart(file).
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            when().
            post(Vars.BASE_URI + "rest/atm/1.0/automation/execution/SRS").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

POST Cucumber Test Results
BASE_URI + rest/atm/1.0/automation/execution/cucumber/{projectKey}
body = multipart/form-data
- Creates a new Test Cycle based on provided automated test results.

public static void postCucumberTestResults() {
    File file=new File(System.getProperty("user.dir")+ "/target/cucumber/result.zip");
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            multiPart(file).
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            when().
            post(Vars.BASE_URI + "rest/atm/1.0/automation/execution/cucumber/SRS").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

POST Attachment to Test Run
BASE_URI + rest/atm/1.0/testrun/{testRunKey}/attachments

body = multipart/form-data

public static void postAttachmentToTestRun() {
    File file=new File(System.getProperty("user.dir")+ "/target/cucumber/result.html");
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            multiPart(file).
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            when().
            post(Vars.BASE_URI + "rest/atm/1.0/testrun/{testRunKey}/attachments").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

POST Attachment to Test Case
BASE_URI + rest/atm/1.0/testcase/{testCaseKey}/attachments

body = multipart/form-data

public static void postAttachmentToTestCase() {
    File file=new File(System.getProperty("user.dir")+ "/target/cucumber/result.html");
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            multiPart(file).
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            when().
            post(Vars.BASE_URI + "rest/atm/1.0/testcase/{testCaseKey}/attachments").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

POST Attachment to Test Plan
BASE_URI + rest/atm/1.0/testplan/{testPlanKey}/attachments

body = multipart/form-data
public static void postAttachmentToTestPlan() {
    File file=new File(System.getProperty("user.dir")+ "/target/cucumber/result.html");
    RestAssured.useRelaxedHTTPSValidation();
    Response response = given().
            multiPart(file).
            headers(
                    "Authorization",
                    "Bearer " + Vars.BEARER_TOKEN).
            contentType("multipart/form-data").
            when().
            post(Vars.BASE_URI + "rest/atm/1.0/testplan/{testPlanKey}/attachments").
            then().
            extract().response();

    System.out.println("Status Code : "+response.statusCode());
    System.out.println("Response Body : "+response.getBody().asString());
}

POST Test Results and POST Attachment to Test Plan (chained) 
BASE_URI + rest/atm/1.0/automation/execution/cucumber/SRS
BASE_URI + rest/atm/1.0/testrun/{testRunKey}/attachments

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
            post(VAR.BASE_URI + "rest/atm/1.0/automation/execution/cucumber/SRS").
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
            post(VAR.BASE_URI + "rest/atm/1.0/testrun/{testRunKey}/attachments").
            then().
            extract().
            response();

    System.out.println("Status Code 2 : " + response2.statusCode());
    System.out.println("Response Body 2 : " + response2.getBody().asString());

}
