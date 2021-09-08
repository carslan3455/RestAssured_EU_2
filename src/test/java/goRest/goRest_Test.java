package goRest;

import goRest.Model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import io.restassured.http.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import static io.restassured.RestAssured.*;

public class goRest_Test {
    int userID;

    @Test (priority = 1)
    public void getUsers(){

        List<User> users =
        given()
                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().jsonPath().getList("data",User.class)
        ;

        for (User user : users) {

            System.out.println("user = " + user);
        }
    }

    @Test (priority = 2)
    public void createUser(){

        userID =
        given()
                .header("Authorization", "Bearer 6602158fa76886d24a865ed485a4d3f01e190df0ab8cd4dadaea5b9cf6f3231a")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+randomString()+"\", \"gender\":\"female\", \"email\":\""+ randomString()+"@hotmail.com\", \"status\":\"active\"}")

                .when()
                .post("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().jsonPath().getInt("data.id")

        ;

        System.out.println("userID = " + userID);

    }

    public String randomString(){
        String rndmString = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        return rndmString;
    }


    @Test (priority = 3)
    public void updateUser(){

        String isim = randomString();
        given()
                .header("Authorization", "Bearer 6602158fa76886d24a865ed485a4d3f01e190df0ab8cd4dadaea5b9cf6f3231a")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+ isim +"\"}")
                .pathParam("userID",userID)

                .when()
                .put("https://gorest.co.in/public/v1/users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("data.name", equalTo(isim) )
        ;

    }

    @Test(priority = 4)
    public void deleteUser(){
        given()
                .header("Authorization", "Bearer 6602158fa76886d24a865ed485a4d3f01e190df0ab8cd4dadaea5b9cf6f3231a")
                .contentType(ContentType.JSON)
                .pathParam("userID",userID)

                .when()
                .delete("https://gorest.co.in/public/v1/users/{userID}")

                .then()
                .statusCode(204)
        ;


    }
    @Test (priority = 5)
    public void deleteUser_NegativeTest(){
        given()
                .header("Authorization", "Bearer 6602158fa76886d24a865ed485a4d3f01e190df0ab8cd4dadaea5b9cf6f3231a")
                .contentType(ContentType.JSON)
                .pathParam("userID",userID)

                .when()
                .delete("https://gorest.co.in/public/v1/users/{userID}")

                .then()
                .statusCode(404)
        ;


    }
}
