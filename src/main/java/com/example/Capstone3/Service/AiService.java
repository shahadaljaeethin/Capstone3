package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.DTO.*;
import com.example.Capstone3.Model.Boat;
import com.example.Capstone3.Model.Review;
import com.example.Capstone3.Model.Trip;
import com.example.Capstone3.Repository.BoatRepository;
import com.example.Capstone3.Repository.ReviewRepository;
import com.example.Capstone3.Repository.TripRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AiClient aiClient;
    private final TripRepository tripRepository;
    private final ReviewRepository reviewRepository ;
    private final BoatRepository boatRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TripRouteAdviceDTO getTripRouteAdvice(Integer tripId) {

        Trip trip = tripRepository.findTripById(tripId);
        if (trip == null) {
            throw new ApiException("Trip not found");
        }

        String systemPrompt = """
            You are an AI assistant for sea trip route planning.

            The trip description provided by the system is accurate and detailed.
            You MUST rely heavily on the description to understand:
            - the purpose of the trip
            - the type of guests
            - the desired experience (relaxing, fishing, sunset, family, etc.)

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
        userPrompt.append("id=").append(trip.getId()).append("\n");
        userPrompt.append("title=").append(trip.getTitle()).append("\n");
        userPrompt.append("type=").append(trip.getTripType()).append("\n");
        userPrompt.append("description=").append(trip.getDescription()).append("\n");
        userPrompt.append("fishingGear=").append(trip.getFishingGear()).append("\n");
        userPrompt.append("startLocation=").append(trip.getStartLocation()).append("\n");
        userPrompt.append("destinationLocation=").append(trip.getDestinationLocation()).append("\n");
        userPrompt.append("endLocation=").append(trip.getEndLocation()).append("\n");
        userPrompt.append("startDate=").append(trip.getStartDate()).append("\n");
        userPrompt.append("endDate=").append(trip.getEndDate()).append("\n");
        userPrompt.append("status=").append(trip.getStatus()).append("\n");
        userPrompt.append("totalPrice=").append(trip.getTotalPrice()).append("\n\n");

        userPrompt.append("""
            Use the description to shape the experience and tone of the trip,
            and use the locations and dates to plan a realistic route and timing.
            """);

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt.toString());

            TripRouteAdviceDTO dto =
                    objectMapper.readValue(aiResponse, TripRouteAdviceDTO.class);

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
                    .append(", Fishing Gear: ").append(trip.getFishingGear())
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


    public ReviewAnalysisDTO analyzeAllReviews() {

        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found");
        }

        StringBuilder reviewData = new StringBuilder();
        for (Review r : reviews) {
            reviewData.append("Rating: ").append(r.getRating()).append("\n");
            reviewData.append("Comment: ").append(r.getComment()).append("\n\n");
        }

        String systemPrompt = """
        You are an AI specialized in service quality analytics.
        Your job is to analyze customer reviews and extract insights.

        You MUST return ONLY valid JSON. No explanations.

        JSON format:
        {
          "totalReviews": <int>,
          "averageRating": <float>,
          "commonComplaints": ["...", "..."],
          "commonPraises": ["...", "..."],
          "improvementSuggestions": ["...", "..."],
          "strengths": ["...", "..."]
        }
        """;

        String userPrompt =
                "Here are all customer reviews:\n\n" +
                        reviewData +
                        "\nAnalyze them and provide insights based ONLY on the reviews.";

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt);
            ReviewAnalysisDTO dto = objectMapper.readValue(aiResponse, ReviewAnalysisDTO.class);

            return dto;

        } catch (Exception e) {
            throw new ApiException("Failed to analyze reviews: " + e.getMessage());
        }
    }

    public List<BoatRecommendationDTO> recommendBoats(String userPromptText) {

        List<Boat> boats = boatRepository.findAll();
        if (boats.isEmpty()) {
            throw new ApiException("No boats found");
        }

        StringBuilder boatList = new StringBuilder();
        for (Boat b : boats) {
            boatList.append("Boat ID: ").append(b.getId()).append("\n");
            boatList.append("Name: ").append(b.getName()).append("\n");
            boatList.append("Capacity: ").append(b.getCapacity()).append("\n");
            boatList.append("PricePerHour: ").append(b.getPricePerHour()).append("\n");
            boatList.append("Status: ").append(b.getStatus()).append("\n");
            boatList.append("Category: ");
            if (b.getCategory() != null) {
                boatList.append(b.getCategory().getName());
            } else {
                boatList.append("null");
            }
            boatList.append("\n");
            boatList.append("Description: ").append(b.getDescription()).append("\n\n");
        }

        String systemPrompt = """
        You are an AI assistant that recommends the best boats for users based on their preferences.

        You MUST return ONLY valid JSON.
        No explanations, no extra text.

        JSON format:
        [
          {
            "boatId": <int>,
            "score": <number between 0 and 5>,
            "reason": "<short explanation why this boat fits>"
          }
        ]
        """;

        String userPrompt =
                "User preferences: " + userPromptText + "\n\n" +
                        "Here is the list of available boats:\n" +
                        boatList;

        try {
            String aiResponse = aiClient.callOpenAi(systemPrompt, userPrompt);
            List<BoatRecommendationDTO> result =
                    objectMapper.readValue(aiResponse, new TypeReference<List<BoatRecommendationDTO>>() {});
            return result;
        } catch (Exception e) {
            throw new ApiException("Failed to get AI boat recommendations: " + e.getMessage());
        }
    }





}
