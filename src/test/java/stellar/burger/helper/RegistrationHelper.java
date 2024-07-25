package stellar.burger.helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.UserRq;
import org.example.UserRs;

import static io.restassured.RestAssured.given;

public class RegistrationHelper {
    private static String accessToken;


    public static void createUser(String email, String name, String password) {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        UserRq requestDTO = new UserRq();

        requestDTO.setEmail(email);
        requestDTO.setPassword(password);
        requestDTO.setName(name);

        Response response = given()
                .contentType("application/json")
                .body(requestDTO)
                .when()
                .post("/api/auth/register")
                .then()
                .extract()
                .response();

        UserRs responseBody = response.getBody().as(UserRs.class);

        accessToken = responseBody.getAccessToken();
    }

    public static void removeUser() {
        if (accessToken != null) {
            given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete("/api/auth/user")
                    .then()
                    .statusCode(202);
        }
    }

    // Получаем accessToken для удаления пользователя
    public static String getAccessToken(String email, String password, String name) {
        UserRq userRq = new UserRq(email, password, name);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(userRq)
                .post("/api/auth/login");

        UserRs userRs = response.as(UserRs.class);
        return userRs.getAccessToken();
    }

    // Удаление зарегистрированного пользователя, если он существует
    public static void removeUser(String token) {
        if (token != null) {
            RestAssured.given()
                    .header("Authorization", token)
                    .delete("/api/auth/user");
        }
    }
}
