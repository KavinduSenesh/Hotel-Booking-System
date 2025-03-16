package lk.ijse.gdse68.hotelbookingsystem.service;

import lk.ijse.gdse68.hotelbookingsystem.model.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookedRoomService {
    List<BookedRoom> getAllBookingsByRoomId(Long id);

    List<BookedRoom> getAllBookings();

    BookedRoom findBookingByConfirmationCode(String confirmationCode);

    String saveBooking(long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);
}
