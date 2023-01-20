//package com.job_hunting;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.job_hunting.controller.FavouritesController;
//import com.job_hunting.entity.Favourites;
//import com.job_hunting.services.job.FavouritesService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.time.LocalDate;
//
//@WebMvcTest(controllers = FavouritesController.class)
//public class FavouriteController {
//
//    private Favourites book, FavouriteOne, FavouriteTwo, FavouriteThree, FavouriteOld;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FavouritesService favouritesService;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @BeforeEach
//    public void setUp() {
//        FavouriteOne= new Favourites(1, 564327, LocalDate.now());
//        FavouriteTwo = new Favourites(2, 275387, LocalDate.now());
//        FavouriteThree = new Favourites(3, 567732, LocalDate.now());
//    }
//
//    @AfterEach
//    public void tearDown() {
//        FavouriteOne= null;
//        FavouriteTwo = null;
//        FavouriteThree = null;
//    }
//
//    @Test
//    public void givenBookDetailsWhenAddedThenReturnSuccessCode() throws Exception {
//        when(favouritesService.addFavorites(any(Favourites.class))).thenReturn(favouritesService);
//
//        MvcResult mvcResult = mockMvc.perform(post("/favourites")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(favouriteOne)))
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Favourites.class))
//                .usingRecursiveComparison().isEqualTo(FavouriteOne);
//    }
//
//}
