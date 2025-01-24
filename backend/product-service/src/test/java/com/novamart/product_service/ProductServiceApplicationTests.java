package com.novamart.product_service;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import io.restassured.RestAssured;
import org.testcontainers.shaded.org.hamcrest.Matchers;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				    "name": "Basic Chair",
				    "description": "Basic Chair a chair that is comfortable and cost effective bringing you from company known for being a user's company.",
				    "price": 50,
				    "currency_code": "CAD",
				    "categories": ["classic"],
				    "attributes": {
				        "color": "red",
				        "height": 100,
				        "width": 50
				    }
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/products/create")
				.then()
				.statusCode(201);
	}

	@Test
	void shouldGetAllProducts() {
		RestAssured.given()
				.when()
				.get("/api/products")
				.then()
				.statusCode(200)
				.body("[0].product_id", notNullValue())
				.body("[0].name", equalTo("Basic Chair"));
	}

	@Test
	void shouldGetProduct() {
		RestAssured.given()
				.when()
				.get("/api/products?productId=6792b199827e341fac03a2b6")
				.then()
				.statusCode(200)
				.body("product_id", notNullValue());
	}

}
