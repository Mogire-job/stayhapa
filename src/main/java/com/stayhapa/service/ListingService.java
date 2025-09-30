package com.stayhapa.service;

import com.stayhapa.model.Listing;
import com.stayhapa.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Optional<Listing> getListingById(Long id) {
        return listingRepository.findById(id);
    }

    public List<Listing> getListingsByCity(String city) {
        return listingRepository.findByCity(city);
    }

    public List<Listing> getListingsByCountry(String country) {
        return listingRepository.findByCountry(country);
    }

    public List<Listing> getListingsByHostId(Long hostId) {
        return listingRepository.findByHost_Id(hostId);
    }

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public Listing updateListing(Long id, Listing listingDetails) {
        return listingRepository.findById(id)
                .map(listing -> {
                    listing.setTitle(listingDetails.getTitle());
                    listing.setDescription(listingDetails.getDescription());
                    listing.setAddress(listingDetails.getAddress());
                    listing.setCity(listingDetails.getCity());
                    listing.setCountry(listingDetails.getCountry());
                    listing.setPricePerNight(listingDetails.getPricePerNight());
                    listing.setMaxGuests(listingDetails.getMaxGuests());
                    listing.setHost(listingDetails.getHost());
                    listing.setUpdatedAt(java.time.LocalDateTime.now());
                    return listingRepository.save(listing);
                })
                .orElse(null);
    }

    public boolean deleteListing(Long id) {
        if (listingRepository.existsById(id)) {
            listingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
