package com.ahmadthesis.image.adapter.output.webclient.data;

import com.ahmadthesis.image.application.port.output.CopernicusWebClient;
import com.ahmadthesis.image.domain.image.BBox;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CopernicusWebClientAdapter implements CopernicusWebClient {

  @Value("${copernicus.token-api}")
  private String copernicusTokenAPI;

  @Value("${copernicus.process-api}")
  private String copernicusProcessAPI;

  @Value("${copernicus.grant_type}")
  private String copernicusGrantType;

  @Value("${copernicus.client_id}")
  private String copernicusClientId;

  @Value("${copernicus.client_secret}")
  private String copernicusClientSecret;

  private static final BigDecimal EARTH_RADIUS_METERS = BigDecimal.valueOf(6371000);
  private static final BigDecimal DEG_TO_RAD = BigDecimal.valueOf(Math.PI / 180);

  @Override
  public Mono<CopernicusTokenDTO> generateCopernicusToken() {
    final WebClient webClient = WebClient.builder()
        .baseUrl(copernicusTokenAPI)
        .build();

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("grant_type", copernicusGrantType);
    formData.add("client_id", copernicusClientId);
    formData.add("client_secret", copernicusClientSecret);

    return webClient.post()
        .body(BodyInserters.fromFormData(formData))
        .retrieve().bodyToMono(CopernicusTokenDTO.class);
  }

  @Override
  public Mono<byte[]> getCopernicusImage(String token, BBox bBox) {
    final WebClient webClient = WebClient.builder()
        .baseUrl(copernicusProcessAPI)
        .defaultHeader("Authorization", "Bearer " + token)
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
            .build())
        .build();

    final BigDecimal[] resolutions = calculatePixelDimensions(bBox, 10, 2500);

    final String requestBody = "{\n" +
        "  \"input\": {\n" +
        "    \"bounds\": {\n" +
        "      \"properties\": {\n" +
        "        \"crs\": \"http://www.opengis.net/def/crs/OGC/1.3/CRS84\"\n" +
        "      },\n" +
        "      \"bbox\": [\n" +
        "        " + bBox.getMinLongitude() + ", " + bBox.getMinLatitude() + ", "
        + bBox.getMaxLongitude() + ", " + bBox.getMaxLatitude() + "\n" +
        "      ]\n" +
        "    },\n" +
        "    \"data\": [\n" +
        "      {\n" +
        "        \"type\": \"sentinel-2-l2a\",\n" +
        "        \"dataFilter\": {\n" +
        "          \"timeRange\": {\n" +
        "            \"from\": \"2022-10-01T00:00:00Z\",\n" +
        "            \"to\": \"2022-10-31T00:00:00Z\"\n" +
        "          }\n" +
        "        }\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  \"output\": {\n" +
        "    \"width\": " + resolutions[0] + ",\n" +
        "    \"height\": " + resolutions[1] + "\n" +
        "  },\n" +
        "  \"evalscript\": \"//VERSION=3\\nfunction setup() {\\n  return {\\n    input: [\\\"B02\\\", \\\"B03\\\", \\\"B04\\\"],\\n    output: {\\n      bands: 3,\\n      sampleType: \\\"AUTO\\\",\\n    },\\n  }\\n}\\n\\nfunction evaluatePixel(sample) {\\n  return [2.5 * sample.B04, 2.5 * sample.B03, 2.5 * sample.B02]\\n}\\n\"\n"
        +
        "}";

    return webClient.post()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(byte[].class)
        .timeout(Duration.ofMinutes(5));
  }

  public static BigDecimal[] calculatePixelDimensions(BBox bbox, double metersPerPixel, int maxDimension) {
    MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

    BigDecimal lonDiff = bbox.getMaxLongitude().subtract(bbox.getMinLongitude(), mc);
    BigDecimal latDiff = bbox.getMaxLatitude().subtract(bbox.getMinLatitude(), mc);

    BigDecimal latAvg = bbox.getMinLatitude().add(bbox.getMaxLatitude(), mc).divide(BigDecimal.valueOf(2), mc);

    BigDecimal widthMeters = lonDiff.multiply(DEG_TO_RAD, mc)
        .multiply(EARTH_RADIUS_METERS, mc)
        .multiply(BigDecimal.valueOf(Math.cos(latAvg.multiply(DEG_TO_RAD, mc).doubleValue())), mc);

    BigDecimal heightMeters = latDiff.multiply(DEG_TO_RAD, mc).multiply(EARTH_RADIUS_METERS, mc);

    BigDecimal widthPixels = widthMeters.divide(BigDecimal.valueOf(metersPerPixel), mc);
    BigDecimal heightPixels = heightMeters.divide(BigDecimal.valueOf(metersPerPixel), mc);

    BigDecimal scale = BigDecimal.valueOf(maxDimension).min(BigDecimal.valueOf(maxDimension).divide(widthPixels, mc)).min(BigDecimal.valueOf(maxDimension).divide(heightPixels, mc));

    BigDecimal scaledWidth = widthPixels.multiply(scale, mc).setScale(0, RoundingMode.HALF_UP);
    BigDecimal scaledHeight = heightPixels.multiply(scale, mc).setScale(0, RoundingMode.HALF_UP);

    return new BigDecimal[]{scaledWidth, scaledHeight};
  }

}
