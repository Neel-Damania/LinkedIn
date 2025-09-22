package com.linkedin.ConnectionsService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.linkedin.ConnectionsService.entity.Person;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("match (personA:Person) - [:CONNECTED_TO] - (personB:Person) where personA.userId = $userId return personB")
    List<Person> getFirstDegreeConnections(Long userId);

    @Query("match (p1:Person) - [r:REQUESTED_TO] -> (p2:Person) where p1.userId = $senderId and p2.userId = $receiverId return count(r) >0")
    boolean connectionRequestsExists(Long senderId,Long receiverId);

    @Query("match (p1:Person) - [r:CONNECTED_TO] - (p2:Person) where p1.userId = $senderId and p2.userId = $receiverId return count(r) >0")
    boolean alreadyConnected(Long senderId,Long receiverId);

    @Query("match (p1:Person) , (p2:Person) where p1.userId = $senderId and p2.userId = $receiverId CREATE (p1)-[:REQUESTED_TO]->(p2)")
    void addConnectionRequest(Long senderId,Long receiverId);

    @Query("match (p1:Person)-[r:REQUESTED_TO]->(p2:Person) where p1.userId = $senderId and p2.userId = $receiverId DELETE r CREATE (p1)-[:CONNECTED_TO]->(p2)")
    void acceptConnectionRequest(Long senderId,Long receiverId);

    @Query("match (p1:Person)-[r:REQUESTED_TO]->(p2:Person) where p1.userId = $senderId and p2.userId = $receiverId DELETE r")
    void rejectConnectionRequest(Long senderId,Long receiverId);

}
