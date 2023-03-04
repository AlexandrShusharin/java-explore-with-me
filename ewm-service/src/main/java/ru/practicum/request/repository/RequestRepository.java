package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_Id(long userId);

    List<Request> findAllByEvent_Id(long eventId);

    List<Request> findAllByIdInAndStatus(List<Long> ids, RequestStatus status);

    Request findFirstByRequester_IdAndEvent_Id(long requesterId, long eventId);

    List<Request> findAllByIdIn(List<Long> ids);
}
