package lk.ijse.gdse68.hotelbookingsystem.service.impl;

import lk.ijse.gdse68.hotelbookingsystem.model.BookedRoom;
import lk.ijse.gdse68.hotelbookingsystem.service.BookedRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookedRoomServiceImpl implements BookedRoomService {
    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long id) {
        return null;
    }
}
