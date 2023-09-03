import io.restassured.RestAssured;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.response.Response;

import java.io.File;


// дополнительный статический импорт нужен, чтобы использовать given(), get() и then()
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class Praktikum {

    //Создали объект класса Card и передали в него параметры Json
    Card card = new Card("Махачкала", "https://absolutera.ru/uploads/photo/file/17328/_____________4.jpg");
    //Создали объект класса User Info и передали в него параметры Json
    UserInfo userInfo = new UserInfo("Vasya", "QA Automation Engineer");
    //Протокол oauth2 имеет "срок годности" поэтому перенес его в переменную
    private String oauth2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGI0YTZkYzQ1M2NkYzAwNDJmZjNkMWYiLCJpYXQiOjE2OTA0MDkzODYsImV4cCI6MTY5MTAxNDE4Nn0.76A2qtoZHLiiBAhBxCXmygTQMxCHuTrb0_ME-Ln-bX4";
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    //Проверяем успешный статус код
    @Test
    public void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given()
                // указываем протокол и данные авторизации
                .auth().oauth2(oauth2)
                // отправляем GET-запрос с помощью метода get, недостающую часть URL (ручку) передаём в него в качестве параметра
                .get("/api/users/me")
                // проверяем, что статус-код ответа равен 200
                .then().statusCode(200);
    }

    //Проверяем имя пользователя используя метод GET
    @Test
    public void checkUserName() {
        given().auth().oauth2(oauth2)
                .get("api/users/me")
                .then().assertThat().body("data.name", equalTo("Vasya"));
    }

    //Проверяем информацию о пользователе используя метод GET
    @Test
    public void checkUserAbout() {
        Response response =
                given()
                        .auth().oauth2(oauth2)
                        .get("api/users/me");
        response.then().assertThat().body("data.about", equalTo("QA Automation Engineer"));
        System.out.println(response.body().asString());


    }

    //Проверяем имя пользователя  и выводим результат на экран
    @Test
    public void checkUserNameAndPrintResponseBody() {
        // отправляет запрос и сохраняет ответ в переменную response, экзмепляр класса Response
        Response response =
                given()
                        .auth().oauth2(oauth2).get("/api/users/me");// проверяет, что в теле ответа ключу name соответствует нужное имя пользователя
        response.then().assertThat().body("data.name", equalTo("Vasya"));
        // выводит тело ответа на экран
        System.out.println(response.body().asString());
    }

    //Добавляем новую карточку с POST  запросом
    @Test
    public void createNewPlaceAndCheckResponse() {
        //Конструктор класса который связывает файл на компьютере и объект класса file
        //File json = new File("src/test/resources/newCard.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(oauth2)
                        .and()
                        .body(card)
                        .when()
                        .post("/api/cards");
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void updateUserInformation() {
        //Передадим json прямо в строке
        //String json = "{\"name\": \"Vasya\", \"about\": \"QA Automation Engineer\"}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(oauth2)
                        .and()
                        .body(userInfo)
                        .when()
                        .patch("api/users/me");
        response.then().assertThat().body("data.name", equalTo("Vasya"))
                .assertThat().body("data.about", equalTo("QA Automation Engineer"))
                .and()
                .statusCode(200);
        System.out.println(response.body().asString());
    }
}