package com.capgemini.productapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.ProductService;
import com.capgemini.productapp.service.impl.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductServiceTest {
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	private MockMvc mockmvc;
	Product product;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(productServiceImpl).build();
	}

	@Test
	public void testaddproduct() throws Exception {
		Product product1 = new Product(20, "oneplus6", "mobile", 1000.0);
		when(productRepository.save(product1)).thenReturn(product1);
		assertEquals(productServiceImpl.addProduct(product1), product1);
	}

	@Test
	public void testfindproduct() throws Exception {
		Product product2 = new Product(20, "oneplus6", "mobile", 1000.0);
		Optional<Product> product3 = Optional.of(product2);
		when(productRepository.findById(20)).thenReturn(product3);
		assertEquals(productServiceImpl.findProductById(20), product2);
	}

}
