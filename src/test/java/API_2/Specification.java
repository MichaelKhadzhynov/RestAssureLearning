package API_2;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

public class Specification {

    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseCheckStatusCode(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public static void instellSpecfication(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static ResponseSpecification expectResponseTime(Long time) {
        return  new ResponseSpecBuilder()
                .expectResponseTime((Matchers.lessThan(time)))
                .build();

    }
    public static void instellSpecfication1( ResponseSpecification response) {

        RestAssured.responseSpecification = response;
    }
}

