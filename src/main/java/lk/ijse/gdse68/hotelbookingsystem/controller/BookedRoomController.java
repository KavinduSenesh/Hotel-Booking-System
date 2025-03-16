package lk.ijse.gdse68.hotelbookingsystem.controller;

import lk.ijse.gdse68.hotelbookingsystem.exception.InvalidBookingRequestException;
import lk.ijse.gdse68.hotelbookingsystem.exception.ResourceNotFoundException;
import lk.ijse.gdse68.hotelbookingsystem.model.BookedRoom;
import lk.ijse.gdse68.hotelbookingsystem.response.BookingResponse;
import lk.ijse.gdse68.hotelbookingsystem.service.BookedRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookedRoomController {
    private final BookedRoomService bookedRoomService;

    @GetMapping("/get/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookedRoomService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom bookedRoom : bookings) {
            BookingResponse bookingResponse = getBookingResponse(bookedRoom);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/get/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookedRoomService.findBookingByConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    public ResponseEntity<?> saveBooking(@PathVariable long roomId, @RequestBody BookedRoom bookingRequest){
        try{
            String confirmationCode = bookedRoomService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Booking is successful. Your confirmation code is "+ confirmationCode);
        }catch (InvalidBookingRequestException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
