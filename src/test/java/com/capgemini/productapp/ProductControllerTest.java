package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductControllerTest {
	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;
	private MockMvc mockmvc;
	Product product;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testaddProduct() throws Exception {
		when(productService.addProduct(Mockito.isA(Product.class)))
				.thenReturn(new Product(20, "oneplus", "mobile", 1000.0));
		mockmvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(
				"{\"productId\":\"20\",\"productName\":\"oneplus\",\"productCategory\":\"mobile\",\"price\":\"1000.0\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8))

				.andExpect(status().isOk()).andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())

				.andExpect(jsonPath("$.productCategory").exists()).andExpect(jsonPath("$.price").exists())

				.andExpect(jsonPath("$.productId").value("20")).andExpect(jsonPath("$.productName").value("oneplus"))
				.andExpect(jsonPath("$.productCategory").value("mobile")).andExpect(jsonPath("$.price").value(1000.0))

				.andDo(print());
	}

	@Test
	public void testEditProduct() throws Exception {

		String content = "{\"productId\":\"20\",\"productName\":oneplus7\",\"productCategory\":\"mobile\",\"price\":\"1000.0\"}";

		when(productService.updateProduct(Mockito.isA(Product.class)))
				.thenReturn(new Product(20, "oneplus7", "mobile", 1000.0));
		when(productService.findProductById(20)).thenReturn(new Product(20, "oneplus", "mobile", 1000.0));

		mockmvc.perform(put("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(
				"{\"productId\":\"20\",\"productName\":\"oneplus7\",\"productCategory\":\"mobile\",\"price\":\"1000.0\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.productName").value("oneplus7"))
				.andDo(print());
	}

	@Test
	public void testfindProductById() throws Exception {
		when(productService.findProductById(20)).thenReturn(new Product(20, "oneplus", "mobile", 1000.0));
		mockmvc.perform(MockMvcRequestBuilders.get("/products/20").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.productId").exists()).andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists()).andExpect(jsonPath("$.price").exists())

				.andExpect(jsonPath("$.productId").value(20)).andExpect(jsonPath("$.productName").value("oneplus"))
				.andExpect(jsonPath("$.productCategory").value("mobile")).andExpect(jsonPath("$.price").value(1000.0))
				.andDo(print());
	}

	@Test
	public void testdelete() throws Exception {
		when(productService.findProductById(20)).thenReturn(new Product(20, "oneplus", "mobile", 1000.0));
		mockmvc.perform(delete("/products/20").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
	}
}