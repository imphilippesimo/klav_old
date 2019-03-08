package com.klav.service.mapper;

import com.klav.domain.*;
import com.klav.repository.ext.*;
import com.klav.service.dto.KlavUserDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper
public abstract class KlavUserMapper {

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

    public abstract KlavUser klavUserDTOToKlavUser(KlavUserDTO klavUserDTO);

    public abstract KlavUserDTO klavUserToKlavUserDTO(KlavUser klavUser);

    public abstract List<KlavUser> klavUserDTOsToKlavUsers(List<KlavUserDTO> klavUserDTOs);

    public abstract List<KlavUserDTO> klavUsersToKlavUsersDTOs(List<KlavUser> klavUsers);

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

}


