package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_Id(long userId);

    List<Request> findAllByEvent_Id(long eventId);

    @Modifying
    @Query("UPDATE Request s SET s.status = ?1 WHERE s.id IN ?2")
    void updateRequestsStatus(List<Long> ids, RequestStatus status);

    @Query("SELECT s FROM Request s WHERE s.id IN ?1")
    List<Request> findAllByIds(List<Long> ids);
}
