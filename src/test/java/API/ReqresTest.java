package API;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static API.Specification.*;
import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String url = "https://reqres.in";

    @Test(testName = "Тест №1 get запрос")
    public void CheckAvatarAndIdTest() {
        installSpecification(requestSpec(url), responseSpec200());


        List<UserData> users = given()
                .when()
                //.contentType(ContentType.JSON)
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> IDs = users.stream().map(e -> e.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(IDs.get(i)));
        }

    }

    @Test(testName = "Test time ответа")
    public void CheckResponseTime() {
        installSpecification(requestSpec(url), expectResponseTime(500L));

        given()
                .when()
                //.contentType(ContentType.JSON)
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

    }

    @Test(testName = "Test №2 Post запрос на регистрацию")
    public void SuccessRegTest() {
        installSpecification(requestSpec(url), responseSpec200());
        int id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        RegistrationData user = new RegistrationData("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());


    }

    @Test(testName = "Ошибка регистрации")
    public void unsuccessUserReg() {
        installSpecification(requestSpec(url), responseErrorSpec400());

        RegistrationData user = new RegistrationData("sydney@fife", "");

        UnSuccessfulReg unSuccessfulReg = given()
                .body(user)
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessfulReg.class);

        Assert.assertEquals("Missing password", unSuccessfulReg.getError());
    }

    @Test(testName = "Сортировка данных")
    public void sortedYearsTest() {
        installSpecification(requestSpec(url), responseSpec200());

        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(sortedYears, years);
        System.out.println(years);
        System.out.println(sortedYears);
    }

    @Test(testName = "Удаление пользователя")
    public void deleteUser() {
        installSpecification(requestSpec(url), responseStatusUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();


    }


}
