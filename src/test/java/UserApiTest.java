
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetAllUsers() {
        Response response = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();

        Assert.assertNotNull(response.jsonPath().getList(""));

    }

    @Test
    public void testGetSingleUser() {
        Response response = given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract().response();
        response.prettyPrint();
        String jsonResponse = response.getBody().asString();
        JSONObject jsonObject = new JSONObject(jsonResponse);

        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");

        Assert.assertEquals(id, 1);
        Assert.assertEquals(name, "Leanne Graham");
    }

    @Test
    public void testCreateUser() {
        User newUser = new User(11, "Tuğba", "Baykara", "trb@gmail.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().response();
          response.prettyPrint();
        User createdUser = response.as(User.class);
        Assert.assertEquals(createdUser.getUsername(), "Baykara");
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User(1, "Yunus", "Yasin", "YasinYunus@gmail.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedUser)
                .when()
                .put("/users/1")
                .then()
                .statusCode(200)
                .extract().response();
         response.prettyPrint();
        User userResponse = response.as(User.class);
        Assert.assertEquals(userResponse.getName(), "Yunus");
    }

    @Test
    public void testDeleteUser() {
        given()
                .when()
                .delete("/users/1")
                .then()
                .statusCode(200);


    }


}
