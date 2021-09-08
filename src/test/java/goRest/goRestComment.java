package goRest;

import goRest.Model.Body;
import goRest.Model.Comment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class goRestComment {


    @Test
    public void getComments(){

        Response response =
        given()
                .when()
                .get("https://gorest.co.in/public/v1/comments")

                .then()
                .extract().response();

        List<Comment> comments =response.jsonPath().getList("data",Comment.class);

        System.out.println("comments = " + comments);



    }

    @Test
    public void getCommentBody(){

        List<String > commentsBody =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/comments")

                        .then()
                        .extract().path("data.body");

        System.out.println("commentsBody = " + commentsBody);

        Assert.assertTrue(commentsBody.contains("Dolores et dolor. Recusandae omnis iure. Nesciunt et velit."));

    }

    @Test
    public void getCommentDirekt(){
        Body body =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/comments")

                        .then()
                        .extract().as(Body.class);

        System.out.println("body.getData().get(3).getPost_id() = " + body.getData().get(3).getPost_id());
        System.out.println("body.getData() = " + body.getMeta());

    }


}
