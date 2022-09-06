package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.ArtworkRepository;
import com.kenzie.appserver.repositories.model.ArtworkRecord;
import com.kenzie.appserver.service.model.Artwork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;
    private CacheStore cache;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository, CacheStore cache) {
        this.artworkRepository = artworkRepository;
        this.cache = cache;
    }

    public Artwork findById(String id) {
        Artwork artworkFromBackend = artworkRepository
                .findById(id)
                .map(artwork -> new Artwork(artwork.getId(), artwork.getDatePosted(), artwork.getArtistName(),
                        artwork.getTitle(), artwork.getDateCreated(), artwork.getHeight(), artwork.getWidth(),
                        artwork.getSold(), artwork.getForSale(), artwork.getPrice()))
                .orElse(null);

        return artworkFromBackend;
    }

    public Artwork addNewArtwork(Artwork artwork) {
        ArtworkRecord artworkRecord = new ArtworkRecord();
        artworkRecord.setId(artwork.getId());
        artworkRecord.setDatePosted(artwork.getDatePosted());
        artworkRecord.setArtistName(artwork.getArtistName());
        artworkRecord.setTitle(artwork.getTitle());
        artworkRecord.setDateCreated(artwork.getDateCreated());
        artworkRecord.setHeight(artwork.getHeight());
        artworkRecord.setWidth(artwork.getWidth());
        artworkRecord.setSold(artwork.getSold());
        artworkRecord.setForSale(artwork.getForSale());
        artworkRecord.setPrice(artwork.getPrice());
        artworkRepository.save(artworkRecord);
        return artwork;
    }

    public void updateArtwork(Artwork artwork) {
        if (artworkRepository.existsById(artwork.getId())) {
            ArtworkRecord artworkRecord = new ArtworkRecord();
            artworkRecord.setId(artwork.getId());
            artworkRecord.setDatePosted(artwork.getDatePosted());
            artworkRecord.setArtistName(artwork.getArtistName());
            artworkRecord.setTitle(artwork.getTitle());
            artworkRecord.setDateCreated(artwork.getDateCreated());
            artworkRecord.setHeight(artwork.getHeight());
            artworkRecord.setWidth(artwork.getWidth());
            artworkRecord.setSold(artwork.getSold());
            artworkRecord.setForSale(artwork.getForSale());
            artworkRecord.setPrice(artwork.getPrice());
            artworkRepository.save(artworkRecord);
            cache.evict(artwork.getId());
        }
    }

    public void deleteArtwork(String id) {
        try {
            artworkRepository.deleteById(id);
            cache.evict(id);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Artwork> findAllArtwork() {
        List<Artwork> artworks = new ArrayList<>();

        Iterable<ArtworkRecord> artworkRecordIterable = artworkRepository.findAll();
        for (ArtworkRecord record : artworkRecordIterable) {
            artworks.add(new Artwork(record.getId(), record.getDatePosted(), record.getArtistName(), record.getTitle(),
                    record.getDateCreated(), record.getHeight(), record.getWidth(), record.getSold(),
                    record.getForSale(), record.getPrice()));
        }
        return artworks;
    }
}
