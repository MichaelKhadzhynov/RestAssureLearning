package API;

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

    public static ResponseSpecification responseSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();


    }

    public static ResponseSpecification responseErrorSpec400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();

    }
    public static ResponseSpecification responseStatusUnique(int status) {
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();

    }

    public static ResponseSpecification expectResponseTime(Long time) {
        return  new ResponseSpecBuilder()
                .expectResponseTime((Matchers.lessThan(time)))
                .build();

    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {

        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;



    }
}
