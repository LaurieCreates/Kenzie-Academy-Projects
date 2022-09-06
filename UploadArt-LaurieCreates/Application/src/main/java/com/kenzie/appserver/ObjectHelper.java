package com.kenzie.appserver;

import com.kenzie.appserver.service.model.Artwork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectHelper {

//Serialize
    private static String artworkToJsonString(Artwork artwork) {
        String serializedArtwork = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            serializedArtwork = objectMapper.writeValueAsString(artwork);
        } catch (JsonProcessingException e) {
            System.out.println("Unable to serialize project.");
            e.printStackTrace();
        }
        return serializedArtwork;
    }

//Deserialize
    private static Artwork jsonStringToArtwork(String jsonArtworkReponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        Artwork deserializedArtwork = null;
        try {
            deserializedArtwork = objectMapper.readValue(jsonArtworkReponse, Artwork.class);
        } catch (IOException e) {
            System.out.println("Unable to desrialize object :( !");
        }
        return deserializedArtwork;
    }
}
