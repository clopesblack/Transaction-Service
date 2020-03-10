package com.challenge.transactionservice.endpoints;

import com.challenge.transactionservice.controllers.resources.TransactionRequest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class TransactionControllerTest extends AbstractControllerTest {

    @Test
    public void Should_ReturnTransactionCreatedWithoutParentId_WhenTryToCreate() {
        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(10L, 2500D, "phone", null))
                .post("/transactions")
                .then()
                .statusCode(CREATED.value())
                .contentType(JSON)
                .body("transaction_id", is(10))
                .body("amount", is(2500.0f))
                .body("type", is("phone"));
    }

    @Test
    public void Should_ReturnBadRequest_WhenTryToCreateWithoutFields() {
        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(null, null, null, null))
                .post("/transactions")
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void Should_ReturnNotFoundTransaction_WhenGetTransactionByIdThatNotExists() {
        given().when()
                .contentType(JSON)
                .get("/transactions/1")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    public void Should_ReturnNotFoundTransactionByType_WhenGetByType() {
        given().when()
                .contentType(JSON)
                .get("/transactions/types/house")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    public void Should_ReturnArrayWithIds_WhenGetTransactionsByType() {
        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(10L, 2500D, "cars", null))
                .post("/transactions")
                .then()
                .statusCode(CREATED.value());

        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(11L, 3000D, "cars", null))
                .post("/transactions")
                .then()
                .statusCode(CREATED.value());

        given().when()
                .contentType(JSON)
                .get("/transactions/types/cars")
                .then()
                .statusCode(OK.value()).body("", Matchers.is(Arrays.asList(10, 11)));

    }

    @Test
    public void Should_ReturnSumOfTransactions_WhenGetSumByTransaction() {
        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(10L, 2500D, "cars", null))
                .post("/transactions")
                .then()
                .statusCode(CREATED.value());

        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(11L, 3000D, "cars", 10L))
                .post("/transactions")
                .then()
                .statusCode(CREATED.value());

        given().when()
                .contentType(JSON)
                .get("/transactions/sum/11")
                .then()
                .statusCode(OK.value()).body("sum", is(5500.0f));

    }

    @Test
    public void Should_ReturnTransactionCreatedWithParentId_WhenTryToCreate() {
        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(11L, 4000D, "house", 10L))
                .post("/transactions")
                .then()
                .statusCode(CREATED.value())
                .contentType(JSON)
                .body("transaction_id", is(11))
                .body("amount", is(4000.0f))
                .body("type", is("house"))
                .body("parent_id", is(10));
    }

    @Test
    public void Should_ReturnTransactionWithSuccess_WhenGetById() {
        given().when()
                .contentType(JSON)
                .body(buildTransactionRequest(12L, 5000D, "cars", null))
                .post("/transactions");

        given().when()
                .contentType(JSON)
                .get("/transactions/12")
                .then()
                .statusCode(OK.value())
                .contentType(JSON)
                .body("transaction_id", is(12))
                .body("amount", is(5000.0f))
                .body("type", is("cars"));
    }

    private TransactionRequest buildTransactionRequest(Long id, Double amount, String type, Long parentId) {
        return TransactionRequest.builder()
                .transactionId(id)
                .amount(amount)
                .type(type)
                .parentId(parentId)
                .build();
    }


}
