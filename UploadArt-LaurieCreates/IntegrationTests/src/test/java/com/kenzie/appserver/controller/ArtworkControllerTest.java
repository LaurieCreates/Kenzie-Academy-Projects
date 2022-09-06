package com.kenzie.appserver.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ArtworkCreateRequest;
import com.kenzie.appserver.controller.model.ArtworkUpdateRequest;
import com.kenzie.appserver.service.ArtworkService;
import com.kenzie.appserver.service.model.Artwork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class ArtworkControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ArtworkService artworkService;

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String ARTWORKS_TABLE_NAME = "Artworks";
    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1).build());

    @Test
    public void artworksTable_exists() {
        for (Table table : client.listTables()) {
            if (table.getTableName().equals(ARTWORKS_TABLE_NAME)) {
                return;
            }
        }
        fail(String.format("Did not find expected table, '%s'", ARTWORKS_TABLE_NAME));
    }

    @Test
    public void getArtwork_ArtworkDoesNotExist_returnsNotFoundStatus() throws Exception {
        // GIVEN
        String id = "12";
        // WHEN
        mvc.perform(get("/artwork/{id}", id)
            .accept(MediaType.APPLICATION_JSON))
        // THEN
            .andExpect(status().isNotFound());
    }

    @Test
    public void addArtworkEndpoint_CreateSuccessful_returnsIsCreatedStatusAndArtworkResponse() throws Exception {
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        int price = mockNeat.ints().val();

        ArtworkCreateRequest artworkCreateRequest = new ArtworkCreateRequest();
        artworkCreateRequest.setArtistName(artistName);
        artworkCreateRequest.setTitle(title);
        artworkCreateRequest.setDateCreated(dateCreated);
        artworkCreateRequest.setHeight(height);
        artworkCreateRequest.setWidth(width);
        artworkCreateRequest.setForSale(true);
        artworkCreateRequest.setPrice(price);

        mapper.registerModule(new JavaTimeModule());

        //WHEN
        mvc.perform(post("/artwork")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(artworkCreateRequest)))
        //THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("datePosted")
                        .exists())
                .andExpect(jsonPath("artistName")
                        .value(is(artistName)))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("dateCreated")
                        .value(is(dateCreated)))
                .andExpect(jsonPath("height")
                        .value(is(height)))
                .andExpect(jsonPath("width")
                        .value(is(width)))
                .andExpect(jsonPath("sold")
                         .value(is(false)))
                .andExpect(jsonPath("forSale")
                        .value(is(true)))
                .andExpect(jsonPath("price")
                        .value(is(price)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getArtworkById_artworkExists_returnsSuccessfulResponse() throws Exception {
        //GIVEN
        //This example exists on our table*
        String id = UUID.randomUUID().toString();
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        int price = mockNeat.ints().val();

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, false,
                true, price);
        Artwork persistedArtwork = artworkService.addNewArtwork(artwork);

        //WHEN
        mvc.perform(get("/artwork/{id}", persistedArtwork.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("datePosted")
                        .value(is(datePosted)))
                .andExpect(jsonPath("artistName")
                        .value(is(artistName)))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("dateCreated")
                        .value(is(dateCreated)))
                .andExpect(jsonPath("height")
                        .value(is(height)))
                .andExpect(jsonPath("width")
                        .value(is(width)))
                .andExpect(jsonPath("sold")
                        .value(is(false)))
                .andExpect(jsonPath("forSale")
                        .value(is(true)))
                .andExpect(jsonPath("price")
                        .value(is(price)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateArtworkEndpoint_PutSuccessful() throws Exception {
        // GIVEN
        String id = "84cdd9ea-de0f-4841-8645-58620adf49b2";
        String datePosted = "7-06-2022";
        String artistName = "TestName";
        String title = "TestTitle";
        String dateCreated = "07-06-2022";
        int height = 10;
        int width = 20;
        int price = 30;

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, true,
                false, price);

        ArtworkCreateRequest artworkCreateRequest = new ArtworkCreateRequest();
        artworkCreateRequest.setArtistName(artwork.getArtistName());
        artworkCreateRequest.setTitle(artwork.getTitle());
        artworkCreateRequest.setDateCreated(artwork.getDateCreated());
        artworkCreateRequest.setHeight(artwork.getHeight());
        artworkCreateRequest.setWidth(artwork.getWidth());
        artworkCreateRequest.setForSale(true);
        artworkCreateRequest.setPrice(artwork.getPrice());

        mapper.registerModule(new JavaTimeModule());

        String newName = "new";
        int newPrice = 80;

        ArtworkUpdateRequest artworkUpdateRequest = new ArtworkUpdateRequest();
        artworkUpdateRequest.setId(id);
        artworkUpdateRequest.setDatePosted(datePosted);
        artworkUpdateRequest.setArtistName(newName);
        artworkUpdateRequest.setTitle(title);
        artworkUpdateRequest.setDateCreated(dateCreated);
        artworkUpdateRequest.setHeight(height);
        artworkUpdateRequest.setWidth(width);
        artworkUpdateRequest.setSold(false);
        artworkUpdateRequest.setForSale(false);
        artworkUpdateRequest.setPrice(newPrice);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/artwork")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(artworkUpdateRequest)))
                // THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("datePosted")
                        .value(is(datePosted)))
                .andExpect(jsonPath("artistName")
                        .value(is(newName)))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("dateCreated")
                        .value(is(dateCreated)))
                .andExpect(jsonPath("height")
                        .value(is(height)))
                .andExpect(jsonPath("width")
                        .value(is(width)))
                .andExpect(jsonPath("sold")
                        .value(is(false)))
                .andExpect(jsonPath("forSale")
                        .value(is(false)))
                .andExpect(jsonPath("price")
                        .value(is(newPrice)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteArtworkEndpoint_deleteSuccessful() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        int price = mockNeat.ints().val();

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, true,
                false, price);

        Artwork persistedArtwork = artworkService.addNewArtwork(artwork);

        // WHEN
        mvc.perform(delete("/artwork/{id}", persistedArtwork.getId())
                        .accept(MediaType.APPLICATION_JSON))
                        // THEN
                        .andExpect(status().isNoContent());
        assertThat(artworkService.findById(id)).isNull();
    }

    @Test
    public void getAllArtworkEndpoint_returnsAllItemsFromTableSuccessfully() throws Exception {
        //GIVEN
        for (Table table : client.listTables()) {
            if (table.getTableName().equals(ARTWORKS_TABLE_NAME)) {

                //WHEN
                mvc.perform(get("/artwork")
                                .accept(MediaType.APPLICATION_JSON))
                        // THEN
                        .andExpect(status().isOk());
            }
        }
    }

    @Test
    public void getAllArtwork_returnsAllArtWork() throws Exception {
        // GIVEN
        String id = "99cdd9ea-de0f-4841-8645-58620adf49b2";
        String datePosted = mockNeat.strings().valStr();
        String artistName = mockNeat.strings().valStr();
        String title = mockNeat.strings().valStr();
        String dateCreated = mockNeat.strings().valStr();
        int height = mockNeat.ints().get();
        int width = mockNeat.ints().get();
        int price = mockNeat.ints().val();

        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, false,
                true, price);

        String idTwo = "84cdd9ea-de0f-4841-8645-58620adf49b2";
        String datePostedTwo = "7-06-2022";
        String artistNameTwo = "TestName";
        String titleTwo = "TestTitle";
        String dateCreatedTwo = "07-06-2022";
        int heightTwo = 10;
        int widthTwo = 20;
        int priceTwo = 30;

        Artwork artworkTwo = new Artwork(idTwo, datePostedTwo, artistNameTwo, titleTwo, dateCreatedTwo, heightTwo, widthTwo, false,
                false, priceTwo);

        artworkService.addNewArtwork(artwork);
        artworkService.addNewArtwork(artworkTwo);

        // WHEN
        String allArtworkResponse = mvc.perform(get("/artwork")
                        .accept(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
