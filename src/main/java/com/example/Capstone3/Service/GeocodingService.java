package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.DTO.NominatimResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    public NominatimResponseDTO getLocationCoordinates(String placeName) {

        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + placeName;

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "SpringBootApp"); // مهم حتى لا يتم حظر الطلب

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<NominatimResponseDTO[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, NominatimResponseDTO[].class);

        if (response.getBody() != null && response.getBody().length > 0) {
            return response.getBody()[0];
        }

        throw new ApiException("There is no lon and lat to the given location");
    }
}

