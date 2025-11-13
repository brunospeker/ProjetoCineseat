package com.maisprati.Cineseat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maisprati.Cineseat.dto.FilmeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TMDBService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public TMDBService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    //Busca filme no TMDB pelo ID
    public FilmeDTO buscarFilmePorId(String filmeId) {
        try {
            String url = String.format("%s/movie/%s?api_key=%s&language=pt-BR", BASE_URL, filmeId, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                return null;
            }

            JsonNode root = objectMapper.readTree(response);

            // Busca créditos para obter o diretor
            String diretor = buscarDiretor(filmeId);

            // Monta o DTO
            FilmeDTO dto = new FilmeDTO();
            dto.setFilmeId(filmeId);
            dto.setTitulo(root.path("title").asText());
            dto.setSinopse(root.path("overview").asText());
            dto.setDiretor(diretor);

            // Data de lançamento
            String dataLancamento = root.path("release_date").asText();
            if (!dataLancamento.isEmpty()) {
                dto.setDataLancamento(LocalDate.parse(dataLancamento, DateTimeFormatter.ISO_DATE));
            }

            // Duração
            dto.setDuracao(root.path("runtime").asInt());

            // Gêneros
            List<String> generos = new ArrayList<>();
            JsonNode generosNode = root.path("genres");
            if (generosNode.isArray()) {
                generosNode.forEach(genero -> generos.add(genero.path("name").asText()));
            }
            dto.setGenero(String.join(", ", generos));

            // Poster
            String posterPath = root.path("poster_path").asText();
            if (!posterPath.isEmpty() && !posterPath.equals("null")) {
                dto.setPosterUrl(IMAGE_BASE_URL + posterPath);
            }

            // Nota
            dto.setNota(root.path("vote_average").asDouble());

            // Busca trailer
            String trailerUrl = buscarTrailer(filmeId);
            dto.setTrailerUrl(trailerUrl);

            // Classificação
            dto.setClassificacao(buscarClassificacao(filmeId));

            dto.setEmCartaz(true);
            dto.setOrigem("TMDB");

            return dto;

        } catch (Exception e) {
            System.err.println("Erro ao buscar filme no TMDB: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Busca o diretor do filme
    private String buscarDiretor(String filmeId) {
        try {
            String url = String.format("%s/movie/%s/credits?api_key=%s", BASE_URL, filmeId, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                return null;
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode crew = root.path("crew");

            if (crew.isArray()) {
                for (JsonNode member : crew) {
                    if ("Director".equals(member.path("job").asText())) {
                        return member.path("name").asText();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar diretor: " + e.getMessage());
        }
        return null;
    }

    //Busca o trailer do filme no YouTube
    private String buscarTrailer(String filmeId) {
        try {
            String url = String.format("%s/movie/%s/videos?api_key=%s&language=pt-BR", BASE_URL, filmeId, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                return null;
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            if (results.isArray() && results.size() > 0) {
                for (JsonNode video : results) {
                    String type = video.path("type").asText();
                    String site = video.path("site").asText();

                    if ("Trailer".equals(type) && "YouTube".equals(site)) {
                        String key = video.path("key").asText();
                        return "https://www.youtube.com/watch?v=" + key;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar trailer: " + e.getMessage());
        }
        return null;
    }

    //Busca a classificação indicativa do filme
    private String buscarClassificacao(String filmeId) {
        try {
            String url = String.format("%s/movie/%s/release_dates?api_key=%s", BASE_URL, filmeId, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                return null;
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            if (results.isArray()) {
                // Procura por Brasil (BR) primeiro
                for (JsonNode country : results) {
                    if ("BR".equals(country.path("iso_3166_1").asText())) {
                        JsonNode releaseDates = country.path("release_dates");
                        if (releaseDates.isArray() && releaseDates.size() > 0) {
                            String cert = releaseDates.get(0).path("certification").asText();
                            if (!cert.isEmpty()) {
                                return cert;
                            }
                        }
                    }
                }

                // Se não encontrar BR, procura US
                for (JsonNode country : results) {
                    if ("US".equals(country.path("iso_3166_1").asText())) {
                        JsonNode releaseDates = country.path("release_dates");
                        if (releaseDates.isArray() && releaseDates.size() > 0) {
                            String cert = releaseDates.get(0).path("certification").asText();
                            if (!cert.isEmpty()) {
                                return cert;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar classificação: " + e.getMessage());
        }
        return null;
    }
}
