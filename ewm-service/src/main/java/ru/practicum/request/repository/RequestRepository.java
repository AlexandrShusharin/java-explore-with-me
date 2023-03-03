package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_Id(long userId);

    List<Request> findAllByEvent_Id(long eventId);

    List<Request> findAllByIdInAndStatus(List<Long> ids, RequestStatus status);

    Request findFirstByRequester_IdAndEvent_Id(long requesterId, long eventId);

/*    @Query("SELECT COUNT(r) FROM Request r WHERE r.event.id = ?1 AND r.status = 'CONFIRMED'")
    Long findCountConfirmedRequest(Long eventId);

    @Transactional
    @Modifying
    @Query("UPDATE Request r SET r.status = :status WHERE r.id IN (:ids)")
    void updateRequestsStatus(List<Long> ids, RequestStatus status);

    @Query("SELECT r FROM Request r WHERE r.id IN ?1")
 */

    List<Request> findAllByIdIn(List<Long> ids);
}
