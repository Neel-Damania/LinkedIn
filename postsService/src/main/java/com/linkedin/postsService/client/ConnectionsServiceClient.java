package com.linkedin.postsService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.linkedin.postsService.dto.PersonDto;

@FeignClient(name = "connections-service", path="/connections")
public interface ConnectionsServiceClient {

    @GetMapping("/core/{userId}/first-degree")
    List<PersonDto> getFirstDegreeConnections(@PathVariable Long userId);

}
