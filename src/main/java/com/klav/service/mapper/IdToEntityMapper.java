package com.klav.service.mapper;

import com.klav.domain.*;
import com.klav.repository.ext.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IdToEntityMapper {

    @Autowired
    protected AddressRepositoryExtended addressRepositoryExtended;
    @Autowired
    protected ReviewRepositoryExtended reviewRepositoryExtended;
    @Autowired
    protected ChatRepositoryExtended chatRepositoryExtended;
    @Autowired
    protected BookingRepositoryExtended bookingRepositoryExtended;
    @Autowired
    protected FileRepositoryExtended fileRepositoryExtended;

    public Address addressFromId(Long id) {
        if (id == null)
            return null;
        Optional<Address> result = addressRepositoryExtended.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    public Long IdFromAddress(Address address) {
        if (address == null)
            return null;
        return address.getId();
    }

    public Booking bookingFromId(Long id) {
        if (id == null)
            return null;
        Optional<Booking> result = bookingRepositoryExtended.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    public Long idFromBooking(Booking booking) {
        if (booking == null)
            return null;
        return booking.getId();
    }

    public Review reviewFromId(Long id) {
        if (id == null)
            return null;
        Optional<Review> result = reviewRepositoryExtended.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    public Long idFromReview(Review review) {
        if (review == null)
            return null;
        return review.getId();
    }

    public Chat chatFromId(Long id) {
        if (id == null)
            return null;

        Optional<Chat> result = chatRepositoryExtended.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    public Long idFromChat(Chat chat) {

        if (chat == null) {
            return null;
        }
        return chat.getId();

    }

    public Set<Long> idsFromChat(Set<Chat> chats) {
        return chats.stream().map(this::idFromChat).collect(Collectors.toSet());
    }

    public Set<Chat> chatsFromIds(Set<Long> chatIds) {
        return chatIds.stream().map(this::chatFromId).collect(Collectors.toSet());

    }

    public Set<Long> idsFromBookings(Set<Booking> bookings) {
        return bookings.stream().map(this::idFromBooking).collect(Collectors.toSet());
    }

    public Set<Booking> BookingsFromIds(Set<Long> bookingIds) {
        return bookingIds.stream().map(this::bookingFromId).collect(Collectors.toSet());

    }

    public Set<Long> idsFromFiles(Set<File> files) {
        return files.stream().map(file -> {
            return file.getId();
        }).collect(Collectors.toSet());


    }

    public Set<File> filesFromIds(Set<Long> fileIds) {
        return fileIds.stream().map(fileId -> {
            Optional<File> file = fileRepositoryExtended.findById(fileId);
            return file.isPresent() ? file.get() : null;
        }).collect(Collectors.toSet());
    }

}
