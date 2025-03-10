package lk.ijse.gdse68.hotelbookingsystem.service;

import lk.ijse.gdse68.hotelbookingsystem.model.BookedRoom;

import java.util.List;

public interface BookedRoomService {
    List<BookedRoom> getAllBookingsByRoomId(Long id);
}
