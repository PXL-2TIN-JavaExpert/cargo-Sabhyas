package be.pxl.cargo.api;

import be.pxl.cargo.api.request.CreateCargoRequest;
import be.pxl.cargo.api.response.CargoStatistics;
import be.pxl.cargo.domain.CargoStatus;
import be.pxl.cargo.domain.Location;
import be.pxl.cargo.service.CargoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CargoController.class)
class CargoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CargoService cargoService;

        @Test
        void addCargoReturnsCreated() throws Exception {
        CreateCargoRequest request = new CreateCargoRequest("CARGO_01", 500, Location.SEA_PORT_Z, Location.CITY_B);

        mockMvc.perform(post("/cargos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        verify(cargoService).createCargo(any(CreateCargoRequest.class));
        }

    @Test
    void addCargoReturnsBadRequestForInvalidPayload() throws Exception {
        String requestBody = """
                {
                  "code": "CARGO_02",
                  "weight": 80,
                  "origin": "CITY_B",
                  "destination": "WAREHOUSE_A"
                }
                """;

        mockMvc.perform(post("/cargos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCargoStatisticsReturnsResponseBody() throws Exception {
        CargoStatistics statistics = new CargoStatistics();
        statistics.setStatusCount(Map.of(CargoStatus.MOVING, 1L));

        when(cargoService.getCargoStatistics()).thenReturn(statistics);

        mockMvc.perform(get("/cargos/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCount.MOVING").value(1));
    }
}package be.pxl.cargo.api;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(CargoController.class)
public class CargoControllerTest {


}
