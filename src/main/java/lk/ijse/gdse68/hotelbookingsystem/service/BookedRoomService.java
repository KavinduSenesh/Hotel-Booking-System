package lk.ijse.gdse68.hotelbookingsystem.service;

import lk.ijse.gdse68.hotelbookingsystem.model.BookedRoom;

import java.util.List;

public interface BookedRoomService {
    List<BookedRoom> getAllBookingsByRoomId(Long id);

    List<BookedRoom> getAllBookings();

    BookedRoom findBookingByConfirmationCode(String confirmationCode);

    String saveBooking(long roomId, BookedRoom bookingRequest);
}
