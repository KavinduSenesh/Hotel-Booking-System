package lk.ijse.gdse68.hotelbookingsystem.controller;

import lk.ijse.gdse68.hotelbookingsystem.exception.PhotoRetrievingException;
import lk.ijse.gdse68.hotelbookingsystem.model.BookedRoom;
import lk.ijse.gdse68.hotelbookingsystem.model.Room;
import lk.ijse.gdse68.hotelbookingsystem.response.BookingResponse;
import lk.ijse.gdse68.hotelbookingsystem.response.RoomResponse;
import lk.ijse.gdse68.hotelbookingsystem.service.BookedRoomService;
import lk.ijse.gdse68.hotelbookingsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "http://localhost:5174")
public class RoomController {

    private final RoomService roomService;
    private final BookedRoomService bookedRoomService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo")MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice")BigDecimal roomPrice
            ) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/room-types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("get/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte [] photoByte = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoByte != null && photoByte.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoByte);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
//        List<BookingResponse> bookingInfo = bookings
//                .stream()
//                .map(booking -> new BookingResponse(
//                        booking.getBookingId(),
//                        booking.getCheckInDate(),
//                        booking.getCheckOutDate(),
//                        booking.getBookingConfirmationCode()
//                        )).toList();
        byte[] photoByte = null;
        Blob photoBlog = room.getPhoto();
        if (photoBlog != null) {
            try {
                photoByte = photoBlog.getBytes(1, (int) photoBlog.length());
            } catch (SQLException e) {
                throw new PhotoRetrievingException("Error occurred while retrieving photo for room id : " + room.getId());
            }
        }
        return new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                photoByte,
//                bookingInfo,
                room.isBooked()
                );
    }

    private List<BookedRoom> getAllBookingsByRoomId(Long id) {
        return bookedRoomService.getAllBookingsByRoomId(id);
    }
}
