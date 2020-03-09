package com.challenge.transactionservice.endpoints;

import com.challenge.transactionservice.controllers.resources.TransactionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class TransactionControllerTest extends AbstractControllerTest {

    @Test
    public void Should_ReturnTransactionCreatedWithoutParentId_WhenTryToCreate() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionId(10L)
                .amount(5000D)
                .type("cars")
                .build();

        given().when()
                .contentType(JSON)
                .body(request)
                .post("/transactions")
                .then()
                .statusCode(CREATED.value())
                .contentType(JSON)
                .body("transaction_id", is(10))
                .body("amount", is(5000.0f))
                .body("type", is("cars"));
    }

    @Test
    public void Should_ReturnTransactionCreatedWithParentId_WhenTryToCreate() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionId(11L)
                .amount(5000D)
                .type("cars")
                .parentId(10L)
                .build();

        given().when()
                .contentType(JSON)
                .body(request)
                .post("/transactions")
                .then()
                .statusCode(CREATED.value())
                .contentType(JSON)
                .body("transaction_id", is(11))
                .body("amount", is(5000.0f))
                .body("type", is("cars"))
                .body("parent_id", is(10));
    }

    @Test
    public void Should_ReturnTransactionWithSuccess_WhenGetById() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionId(12L)
                .amount(5000D)
                .type("cars")
                .build();

        given().when()
                .contentType(JSON)
                .body(request)
                .post("/transactions");

        given().when()
                .contentType(JSON)
                .body(request)
                .get("/transactions/12")
                .then()
                .statusCode(OK.value())
                .contentType(JSON)
                .body("transaction_id", is(12))
                .body("amount", is(5000.0f))
                .body("type", is("cars"));
    }


}
