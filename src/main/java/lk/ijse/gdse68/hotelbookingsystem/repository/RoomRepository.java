package lk.ijse.gdse68.hotelbookingsystem.repository;

import lk.ijse.gdse68.hotelbookingsystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    @Query(" SELECT r FROM Room r " +
            " WHERE r.roomType LIKE %:roomType% " +
            " AND r.id NOT IN (" +
            "  SELECT br.room.id FROM BookedRoom br " +
            "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
            ")")
    List<Room> findAvailableRoomsByDatesAndType(
                                    @Param("checkInDate") LocalDate checkInDate,
                                    @Param("checkOutDate") LocalDate checkOutDate,
                                    @Param("roomType") String roomType);
}
