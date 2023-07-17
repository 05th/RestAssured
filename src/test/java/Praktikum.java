import io.restassured.RestAssured;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

// дополнительный статический импорт нужен, чтобы использовать given(), get() и then()
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Praktikum {

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
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGI0YTZkYzQ1M2NkYzAwNDJmZjNkMWYiLCJpYXQiOjE2ODk1NjA3OTcsImV4cCI6MTY5MDE2NTU5N30.4HrDvmX8boSU1XOF9PeWyu-QhNWvw5IT71O7cmrwgLw")
                // отправляем GET-запрос с помощью метода get, недостающую часть URL (ручку) передаём в него в качестве параметра
                .get("/api/users/me")
                // проверяем, что статус-код ответа равен 200
                .then().statusCode(200);
    }

    //Проверяем имя пользователя
    @Test
    public void checkUserName() {
        given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGI0YTZkYzQ1M2NkYzAwNDJmZjNkMWYiLCJpYXQiOjE2ODk1NjA3OTcsImV4cCI6MTY5MDE2NTU5N30.4HrDvmX8boSU1XOF9PeWyu-QhNWvw5IT71O7cmrwgLw")
                .get("api/users/me")
                .then().assertThat().body("data.name", equalTo("Жак-Ив Кусто"));
    }

}
