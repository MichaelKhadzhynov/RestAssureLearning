package API_2;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static API_2.Specification.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class Tests {
    private final static String url = "https://reqres.in";

    @Test(testName = "Тест №1 Get запрос")

    public void GetMethod() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(200));
        instellSpecfication1(expectResponseTime(2000L));
        String name = "Michael";
        List<GET_List_User> users = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", GET_List_User.class);

        //Проверка на то что ключ email содеожит значение
        assertFalse(users.stream().allMatch(x -> x.getEmail().isEmpty()));

        //Ожидание указанного окончания в email
        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        //Создание списка id
        List<String> id = users.stream().map(e -> e.getId().toString()).collect(Collectors.toList());

        //Создание списка имен
        List<String> names = users.stream().map(GET_List_User::getFirst_name).collect(Collectors.toList());


        //Содержание имени в списке имен
        assertTrue(names.contains(name));

        System.out.println(id);

        System.out.println(names);

        // System.out.println(RestAssured.given().when().get().getTimeIn(TimeUnit.MILLISECONDS));

    }

    @Test(testName = "Тест №2 GET запрос без использования pojo классов")
    public void getReqNOPojo() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(200));
        Response response = given()
                .when()
                .get("api/users/2")
                .then().log().all()
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .body("data.first_name", notNullValue())
                .extract().response();


    }

    @Test(testName = " Тест №3 GET запрос без pojo классов")
    public void getListResours() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(200));
        Response response = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .body("total_pages", equalTo(2))
                .body("data.name[0]", equalTo("cerulean"))
                .body("data.id", notNullValue())
                .body("data.name", notNullValue())
                .body("data.year", notNullValue())
                .body("data.color", notNullValue())
                .body("data.pantone_value", notNullValue())
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        List<Integer> ids = jsonPath.get("data.id");
        List<String> colors = jsonPath.get("data.color");

//        for (int i = 0; i < colors.size(); i++) {
//            Assert.assertTrue(ids.get(i).toString().contains(colors.get(i)));
//        }

        System.out.println(ids);
        System.out.println(colors);

    }

    @Test(testName = "Тест №4 Post запрос c pojo классами")
    public void createUser() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(201));
        RegUserData regUserData = new RegUserData("morfeus", "leader");
        CreateUserResponse createUsr = given()
                .body(regUserData)
                .when()
                .post("api/users")
                .then().log().all()
                .extract().as(CreateUserResponse.class);

        Assert.assertNotNull(createUsr.getName());
        Assert.assertNotNull(createUsr.getJob());
    }

    @Test(testName = "Тест №5 Post запрос ")
    public void loginSaccess() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(200));
        LogineUser logineUser = new LogineUser("eve.holt@reqres.in", "cityslicka");
        Response login = given()
                .body(logineUser)
                .when()
                .post("api/login")
                .then().log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .extract().response();


    }

    @Test(testName = "Тест №6 Get запрос 404 ")
    public void Err404() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(404));
        Response response = given()
                .when()
                .get("api/unknown/23")
                .then().log().all()
                .extract().response();
        Assert.assertEquals(404, response.statusCode());
        System.out.println(response.statusCode());
    }

    @Test(testName = "Тест №7 Put ")
    public void MethodPut() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(200));

        putUserData putUsrData = new putUserData("morpheus", "zion resident");

        ResponsePutUserData responsePutUserData = given()
                .body(putUsrData)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(ResponsePutUserData.class);

        Assert.assertEquals(putUsrData.getName(), responsePutUserData.getName());
        Assert.assertEquals(putUsrData.getJob(), responsePutUserData.getJob());

    }

    @Test(testName = "Тест №8 delete ")
    public void Delete() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(204));
        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();

    }

    @Test(testName = "Тест №9 DELAYED RESPONSE ")
    public void GetDeleyed() {
        instellSpecfication(requestSpec(url), responseCheckStatusCode(200));
        Response response = given()
                .when()
                .get("api/users?delay=3")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");

//        for (int i = 0; i < emails.size(); i++){
//            Assert.assertTrue(emails.get(i).contains(ids.get(i).toString()));
//        }

            System.out.println(emails);
        System.out.println(ids);
    }


}
