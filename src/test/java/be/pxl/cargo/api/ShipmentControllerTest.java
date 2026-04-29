package be.pxl.cargo.api;

import be.pxl.cargo.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShipmentController.class)
class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @Test
    void addCargoToShipmentUsesPostAndReturnsOk() throws Exception {
        mockMvc.perform(post("/shipments/3/cargo/CARGO_01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shipmentService).addCargoToShipment(3L, "CARGO_01");
    }

    @Test
    void markShipmentAsArrivedReturnsAccepted() throws Exception {
        mockMvc.perform(put("/shipments/1/arrive"))
                .andExpect(status().isAccepted());

        verify(shipmentService).markShipmentAsArrived(1L);
    }
}
