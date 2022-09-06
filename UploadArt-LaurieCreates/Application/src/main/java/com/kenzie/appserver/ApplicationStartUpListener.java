package com.kenzie.appserver;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

//    ArtworkService artworkService = event.getApplicationContext()
//            .getBean(ArtworkService.class);
//    ConcurrentLinkedQueue queue = event.getApplicationContext().getBean(ConcurrentLinkedQueue.class);
//    List<Artwork> artworkList = artworkService.findAllArtwork();
//    queue.addAll(artworkList);
//  add
    }
}
