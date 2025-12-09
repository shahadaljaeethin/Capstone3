package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.DTO.SuggestedTripsBasedOnFishDTO;
import com.example.Capstone3.DTO.TripInfoSummaryDTO;
import com.example.Capstone3.DTO.TripRouteAdviceDTO;
import com.example.Capstone3.DTO.TripSafetyGuideDTO;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Repository.TripRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AiClient aiClient;
    private final TripRepository tripRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TripRouteAdviceDTO getTripRouteAdvice(Integer tripId) {

        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null) {
            throw new ApiException("Trip not found");
        }

        String systemPrompt = """
                You are an AI assistant for sea trip route planning.
                Your job is to give smart and realistic route advice,
                timing suggestions, safety tips, and experience improvements.

                You MUST return ONLY valid JSON.
                Do NOT add any explanation text.

                JSON format:
                {
                  "tripId": <int>,
                  "shortSummary": "<2–3 sentence summary>",
                  "routeSuggestions": [
                    "<main route suggestion>",
                    "<alternative route>"
                  ],
                  "timeSuggestions": [
                    "<best departure time advice>",
                    "<trip duration advice>"
                  ],
                  "safetyTips": [
                    "<safety tip 1>",
                    "<safety tip 2>"
                  ],
                  "extraIdeas": [
                    "<optional activities or stops>"
                  ]
                }
                """;

        StringBuilder userPrompt = new StringBuilder();

        userPrompt.append("Trip info:\n");
        userPrompt.append("id=").append(trip.getId())
                .append(", title=").append(trip.getTitle())
                .append(", tripType=").append(trip.getTripType())
                .append(", fishingGear=").append(trip.isFishingGear())
                .append(", startDate=").append(trip.getStartDate())
                .append(", endDate=").append(trip.getEndDate())
                .append(", startLocation=").append(trip.getStartLocation())
                .append(", destinationLocation=").append(trip.getDestinationLocation())
                .append(", endLocation=").append(trip.getEndLocation())
                .append(", status=").append(trip.getStatus())
                .append(", totalPrice=").append(trip.getTotalPrice())
                .append("\n");

        userPrompt.append("""
                Based on this trip data, suggest:
                - best sea route
                - best time
                - safety tips
                - experience ideas
                """);

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt.toString());

            TripRouteAdviceDTO dto = objectMapper.readValue(aiResponse, TripRouteAdviceDTO.class);

            if (dto.getTripId() == null) {
                dto.setTripId(trip.getId());
            }

            return dto;

        } catch (Exception e) {
            throw new ApiException("Failed to parse AI JSON: " + e.getMessage());
        }
    }

    public TripSafetyGuideDTO getGeneralTripSafetyGuide() {

        String systemPrompt = """
            You are an AI sea safety instructor.
            Your task is to provide general safety steps for sea trips.

            You MUST return ONLY valid JSON.
            Do NOT add any explanation or extra text.

            JSON format:
            {
              "title": "Sea Trip Safety Guide",
              "beforeTrip": [
                "<safety step before trip>",
                "<safety step before trip>"
              ],
              "duringTrip": [
                "<safety step during trip>",
                "<safety step during trip>"
              ],
              "emergencyActions": [
                "<what to do in emergency>",
                "<what to do in emergency>"
              ]
            }
            """;

        String userPrompt = """
            Give general safety guidelines for any sea trip.
            The advice should be practical and easy to follow.
            """;

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt);

            TripSafetyGuideDTO dto = objectMapper.readValue(aiResponse, TripSafetyGuideDTO.class);

            return dto;

        } catch (Exception e) {
            throw new ApiException("Failed to generate safety guide: " + e.getMessage());
        }
    }

//--------------------------------------------------------

    public TripInfoSummaryDTO getTripInfoSummary(Integer tripId) {

        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null) {
            throw new ApiException("Trip not found");
        }

        String systemPrompt = """
            You are an AI assistant specialized in providing informative summaries
            about sea trip destinations.

            You MUST return ONLY valid JSON.
            No explanations or extra text.

            JSON format:
            {
              "tripId": <int>,
              "regionSummary": "<what this region is known for>",
              "famousFor": "<key highlights of the area>",
              "popularFishTypes": "<famous fish types in this region>",
              "tripExperience": "<expected experience based on this trip type>"
            }
            """;

        String userPrompt = """
            Trip destination: """ + trip.getDestinationLocation() + """
            Trip type: """ + trip.getTripType() + """

            Provide:
            - What this destination is known for
            - Popular fish species in this area
            - Characteristics of this region
            - Expected experience for this trip type
            """;

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt);

            TripInfoSummaryDTO dto = objectMapper.readValue(aiResponse, TripInfoSummaryDTO.class);

            if (dto.getTripId() == null) {
                dto.setTripId(trip.getId());
            }

            return dto;

        } catch (Exception e) {
            throw new ApiException("Failed to generate destination summary: " + e.getMessage());
        }
    }


    public SuggestedTripsBasedOnFishDTO getTripsByFishType(String fishType) {

        List<Trip> trips = tripRepository.findAll();
        if (trips.isEmpty()) {
            throw new ApiException("No trips found in the database");
        }

        // Convert trips into a simple text list for the AI
        StringBuilder tripListPrompt = new StringBuilder();
        tripListPrompt.append("Here are all trips:\n");

        for (Trip trip : trips) {
            tripListPrompt.append("Trip ID: ").append(trip.getId())
                    .append(", Destination: ").append(trip.getDestinationLocation())
                    .append(", Trip Type: ").append(trip.getTripType())
                    .append(", Fishing Gear: ").append(trip.isFishingGear())
                    .append("\n");
        }

        String systemPrompt = """
            You are an AI fishing expert.
            Your task is to analyze all available trips and select those that are suitable 
            for catching a specific type of fish.

            You MUST return ONLY valid JSON.
            No explanations, no extra comments.

            JSON format:
            {
              "fishType": "<fish name>",
              "recommendedTripIds": [<tripId1>, <tripId2>, ...],
              "reasons": [
                 "<reason why these trips match>",
                 "<extra helpful notes>"
              ]
            }
            """;

        String userPrompt = tripListPrompt +
                "\nGiven the fish type: " + fishType + "\n" +
                "Select the best trips whose destination is well-known for this fish type. Return only the trip IDs.";

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt);

            SuggestedTripsBasedOnFishDTO dto = objectMapper.readValue(aiResponse, SuggestedTripsBasedOnFishDTO.class);

            return dto;

        } catch (Exception e) {
            throw new ApiException("Failed to generate trip fishing suggestions: " + e.getMessage());
        }
    }


}
