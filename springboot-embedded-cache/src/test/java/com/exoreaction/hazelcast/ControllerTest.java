package com.exoreaction.hazelcast;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exoreaction.hazelcast.rest.Beer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@SuppressWarnings("deprecation")
	@Test
	void shouldPutAndGet() throws Exception {
		// given
		String number = "BO5489";
		Beer car = Beer.builder()
				.number(number)
				.name("Pilsen Callao")
				.build();

		// put
		String content = objectMapper.writeValueAsString(car);
		mockMvc.perform(
				post("/beers/" + number)
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isCreated());

		// get
		String json = mockMvc.perform(
						get("/beers/" + number))
				.andExpect(status().isOk()
				).andReturn().getResponse().getContentAsString();
		Beer response = objectMapper.readValue(json, Beer.class);

		assertThat(response).isEqualToComparingFieldByField(car);

	}

}
