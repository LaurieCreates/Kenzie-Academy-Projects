package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.ArtworkRepository;
import com.kenzie.appserver.repositories.model.ArtworkRecord;
import com.kenzie.appserver.service.model.Artwork;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.UUID.randomUUID;

import static org.mockito.Mockito.*;

public class ArtworkServiceTest {
    private ArtworkRepository artworkRepository;
    private CacheStore cacheStore;
    private ArtworkService artworkService;

    @BeforeEach
    void setup() {
        artworkRepository = mock(ArtworkRepository.class);
        cacheStore = mock(CacheStore.class);
        artworkService = new ArtworkService(artworkRepository, cacheStore);
    }

    /** ------------------------------------------------------------------------.
     *  artworkService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        ArtworkRecord record = new ArtworkRecord();
        record.setId(id);
        record.setDatePosted("testPostDate");
        record.setArtistName("testName");
        record.setTitle("testTitle");
        record.setDateCreated("testCreatedDate");
        record.setHeight(48);
        record.setWidth(48);
        record.setSold(false);
        record.setForSale(false);
        record.setPrice(50);

        // WHEN
        when(artworkRepository.findById(id)).thenReturn(Optional.of(record));
        Artwork artwork = artworkService.findById(id);

        // THEN
        Assertions.assertNotNull(artwork, "The object is returned");
        Assertions.assertEquals(record.getId(), artwork.getId(), "The id matches");
        Assertions.assertEquals(record.getDatePosted(), artwork.getDatePosted(), "The posted date matches");
        Assertions.assertEquals(record.getArtistName(), artwork.getArtistName(), "The artist name matches");
        Assertions.assertEquals(record.getTitle(), artwork.getTitle(), "The title matches");
        Assertions.assertEquals(record.getDateCreated(), artwork.getDateCreated(), "The created date matches");
        Assertions.assertEquals(record.getHeight(), artwork.getHeight(), "The height matches");
        Assertions.assertEquals(record.getWidth(), artwork.getWidth(), "The width matches");
        Assertions.assertEquals(record.getSold(), artwork.getSold(), "Whether the artworks are sold or " +
                "not match");
        Assertions.assertEquals(record.getForSale(), artwork.getForSale(), "Whether the artworks are for " +
                "sale or not match");
        Assertions.assertEquals(record.getPrice(), artwork.getPrice(), "The price matches");
    }

    @Test
    void findById_invalidArtworkId_returnsNulls() {
        // GIVEN
        String id = randomUUID().toString();

        when(artworkRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Artwork artwork = artworkService.findById(id);

        // THEN
        Assertions.assertNull(artwork, "The example is null when not found");
    }

    /** ------------------------------------------------------------------------.
     *  artworkService.findAllArtwork
     *  ------------------------------------------------------------------------ **/

    @Test
    void getAllArtwork() {
        // GIVEN
        ArtworkRecord record1 = new ArtworkRecord();
        record1.setId(randomUUID().toString());
        record1.setArtistName("artistname1");
        record1.setDateCreated("recorddate1");
        record1.setDatePosted("recorddate1");
        record1.setHeight(10);
        record1.setWidth(10);
        record1.setForSale(true);
        record1.setSold(false);
        record1.setPrice(10);

        ArtworkRecord record2 = new ArtworkRecord();
        record1.setId(randomUUID().toString());
        record1.setArtistName("artistname2");
        record1.setDateCreated("recorddate2");
        record1.setDatePosted("recorddate2");
        record1.setHeight(12);
        record1.setWidth(12);
        record1.setForSale(true);
        record1.setSold(false);
        record1.setPrice(15);

        List<ArtworkRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(artworkRepository.findAll()).thenReturn(recordList);

        // WHEN
        List<Artwork> artworks = artworkService.findAllArtwork();

        // THEN
        Assertions.assertNotNull(artworks, "The artwork list is returned");
        Assertions.assertEquals(2, artworks.size(), "There are two artworks");

        for (Artwork artwork : artworks) {
            if (Objects.equals(artwork.getId(), record1.getId())) {
                Assertions.assertEquals(record1.getId(), artwork.getId(), "The artwork id matches");
                Assertions.assertEquals(record1.getArtistName(), artwork.getArtistName(), "The artist name matches");
                Assertions.assertEquals(record1.getDateCreated(), artwork.getDateCreated(), "The date created matches");
                Assertions.assertEquals(record1.getDatePosted(), artwork.getDatePosted(), "The date posted matches");
                Assertions.assertEquals(record1.getHeight(), artwork.getHeight(), "The height matches");
                Assertions.assertEquals(record1.getWidth(), artwork.getWidth(), "The width matches");
                Assertions.assertEquals(record1.getForSale(), artwork.getForSale(), "The is for sale flag matches");
                Assertions.assertEquals(record1.getSold(), artwork.getSold(), "The is sold flag matches");
                Assertions.assertEquals(record1.getPrice(), artwork.getPrice(), "The artwork price matches");
            } else if (Objects.equals(artwork.getId(), record2.getId())) {
                Assertions.assertEquals(record2.getId(), artwork.getId(), "The artwork id matches");
                Assertions.assertEquals(record2.getArtistName(), artwork.getArtistName(), "The artist name matches");
                Assertions.assertEquals(record2.getDateCreated(), artwork.getDateCreated(), "The date created matches");
                Assertions.assertEquals(record2.getDatePosted(), artwork.getDatePosted(), "The date posted matches");
                Assertions.assertEquals(record2.getHeight(), artwork.getHeight(), "The height matches");
                Assertions.assertEquals(record2.getWidth(), artwork.getWidth(), "The width matches");
                Assertions.assertEquals(record2.getForSale(), artwork.getForSale(), "The is for sale flag matches");
                Assertions.assertEquals(record2.getSold(), artwork.getSold(), "The is sold flag matches");
                Assertions.assertEquals(record2.getPrice(), artwork.getPrice(), "The artwork price matches");
            } else {
                Assertions.assertTrue(false, "Artwork returned that was not in the records!");
            }
        }
    }

    /** ------------------------------------------------------------------------.
     *  artworkService.addNewArtwork
     *  ------------------------------------------------------------------------ **/

    @Test
    void addNewArtwork() {
        // GIVEN
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String id = randomUUID().toString();
        String datePosted = simpleDateFormat.format(new Date());
        String artistName = "test name";
        String title = "test title";
        String dateCreated = "01-01-2020";
        int height = 10;
        int width = 10;
        boolean isSold = false;
        boolean isForSale = true;
        int price = 100;
        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, isSold,
                isForSale, price);

        ArgumentCaptor<ArtworkRecord> artworkRecordArgumentCaptor = ArgumentCaptor.forClass(ArtworkRecord.class);

        // WHEN
        Artwork returnedArtwork = artworkService.addNewArtwork(artwork);

        // THEN
        Assertions.assertNotNull(returnedArtwork);
        verify(artworkRepository).save(artworkRecordArgumentCaptor.capture());
        ArtworkRecord record = artworkRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(record, "The artwork record is returned");
        Assertions.assertEquals(record.getId(), artwork.getId(), "The artwork id matches");
        Assertions.assertEquals(record.getDatePosted(), artwork.getDatePosted(), "The artwork date matches");
        Assertions.assertEquals(record.getArtistName(), artwork.getArtistName(), "The artwork name matches");
        Assertions.assertEquals(record.getTitle(), artwork.getTitle(), "The artwork title matches");
        Assertions.assertEquals(record.getDateCreated(), artwork.getDateCreated(), "The artwork created date matches");
        Assertions.assertEquals(record.getHeight(), artwork.getHeight(), "The artwork height matches");
        Assertions.assertEquals(record.getWidth(), artwork.getWidth(), "The artwork width matches");
        Assertions.assertEquals(record.getSold(), artwork.getSold(), "The artwork is sold flag matches");
        Assertions.assertEquals(record.getForSale(), artwork.getForSale(), "The artwork is for sale flag matches");
        Assertions.assertEquals(record.getPrice(), artwork.getPrice(), "The artwork price matches");
    }

    /**
     * ------------------------------------------------------------------------
     * artworkService.updateArtwork
     * ------------------------------------------------------------------------
     **/

    @Test
    void updateArtwork() {
        //GIVEN
        ArtworkRecord artworkRecord = new ArtworkRecord();
        artworkRecord.setId(randomUUID().toString());
        artworkRecord.setArtistName("artistname1");
        artworkRecord.setDateCreated("recorddate1");
        artworkRecord.setDatePosted("recorddate1");
        artworkRecord.setHeight(10);
        artworkRecord.setWidth(10);
        artworkRecord.setForSale(true);
        artworkRecord.setSold(false);
        artworkRecord.setPrice(10);

        Artwork artwork = new Artwork(artworkRecord.getId(), artworkRecord.getDatePosted(), artworkRecord.getArtistName(), artworkRecord.getTitle(),
                artworkRecord.getDateCreated(), artworkRecord.getHeight(), artworkRecord.getWidth(),
                artworkRecord.getSold(), artworkRecord.getForSale(), artworkRecord.getPrice());

        ArgumentCaptor<ArtworkRecord> artworkRecordCaptor = ArgumentCaptor.forClass(ArtworkRecord.class);

        artworkService.addNewArtwork(artwork);

        ArtworkRecord artworkRecordTwo = new ArtworkRecord();
        artworkRecordTwo.setId(artworkRecord.getId());
        artworkRecordTwo.setArtistName(artworkRecord.getArtistName());
        artworkRecordTwo.setDateCreated(artworkRecord.getDateCreated());
        artworkRecordTwo.setDatePosted(artworkRecord.getDatePosted());
        artworkRecordTwo.setHeight(artworkRecord.getHeight());
        artworkRecordTwo.setWidth(artworkRecord.getWidth());
        artworkRecordTwo.setForSale(artworkRecord.getForSale());
        artworkRecordTwo.setSold(artworkRecord.getSold());
        artworkRecordTwo.setPrice(20);

        Artwork artworkTwo = new Artwork(artworkRecordTwo.getId(), artworkRecordTwo.getDatePosted(),
                artworkRecordTwo.getArtistName(), artworkRecordTwo.getTitle(),
                artworkRecordTwo.getDateCreated(), artworkRecordTwo.getHeight(), artworkRecordTwo.getWidth(),
                artworkRecordTwo.getForSale(), artworkRecordTwo.getSold(), 20);
        //WHEN
        when(artworkRepository.existsById(artwork.getId())).thenReturn(true);
        artworkService.updateArtwork(artworkTwo);

        //THEN
        verify(cacheStore).evict(artworkRecord.getId());

    }

    /**
     * ------------------------------------------------------------------------
     * artworkService.deleteArtwork
     * ------------------------------------------------------------------------
     **/

    @Test
    void deleteArtwork() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String id = randomUUID().toString();
        String datePosted = simpleDateFormat.format(new Date());
        String artistName = "test name";
        String title = "test title";
        String dateCreated = "01-01-2020";
        int height = 10;
        int width = 10;
        boolean isSold = false;
        boolean isForSale = true;
        int price = 100;
        Artwork artwork = new Artwork(id, datePosted, artistName, title, dateCreated, height, width, isSold,
                isForSale, price);

        artworkService.deleteArtwork(id);

        verify(artworkRepository).deleteById(id);
        verify(cacheStore).evict(id);
    }
}
