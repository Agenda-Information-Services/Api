package AS_API.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenAiEmbeddingService {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    public float[] getEmbedding(String text) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        String body = """
            {
              "model": "text-embedding-3-small",
              "input": %s
            }
        """.formatted(mapper.writeValueAsString(text));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/embeddings"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openAiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = mapper.readTree(response.body());
        JsonNode data = root.get("data");

        if (data == null || !data.isArray() || data.size() == 0) {
            throw new IllegalStateException("OpenAI 응답에 'data' 항목이 없음: " + response.body());
        }

        JsonNode embeddingNode = data.get(0).get("embedding");

        if (embeddingNode == null || !embeddingNode.isArray()) {
            throw new IllegalStateException("'embedding' 항목이 없음 또는 배열 아님: " + response.body());
        }

        float[] result = new float[embeddingNode.size()];
        for (int i = 0; i < embeddingNode.size(); i++) {
            result[i] = embeddingNode.get(i).floatValue();
        }
        return result;
    }

    public float[] getNormalizedEmbedding(String text) throws IOException, InterruptedException {
        float[] result = getEmbedding(text); // 정규화되지 않은 벡터 받음
        float norm = 0f;
        for (float v : result) norm += v * v;
        norm = (float) Math.sqrt(norm);
        if (norm == 0f) norm = 1e-10f;
        for (int i = 0; i < result.length; i++) {
            result[i] /= norm;
        }
        return result;
    }

}
