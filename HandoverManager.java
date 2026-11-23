import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class HandoverManager {
    private static final String HEADER = "ALBANOTE_HANDOVER_V1";

    private String encode(String text) {
        return Base64.getEncoder().encodeToString(
                text.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String text) {
        byte[] bytes = Base64.getDecoder().decode(text);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // 선택한 문서를 별도의 인수인계 파일로 내보내기
    public Path exportDocuments(List<Document> selected)
            throws IOException {

        if (selected == null || selected.isEmpty()) {
            throw new IllegalArgumentException(
                    "인수인계할 문서가 없습니다.");
        }

        Path folder = Paths.get("handovers");
        Files.createDirectories(folder);

        // 매번 다른 이름의 파일 생성
        Path output = Files.createTempFile(
                folder, "handover_", ".txt");

        List<String> lines = new ArrayList<>();
        lines.add(HEADER);

        for (Document document : selected) {
            String title = encode(document.getTitle());
            String content = encode(document.getContent());

            lines.add(title + "\t" + content);
        }

        try {
            Files.write(output, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            try {
                Files.deleteIfExists(output);
            } catch (IOException cleanupError) {
                e.addSuppressed(cleanupError);
            }
            throw e;
        }

        return output.toAbsolutePath();
    }

    // 인수인계 파일을 읽어 문서 목록 반환
    public ArrayList<Document> importDocuments(String filePath)
            throws IOException {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IOException("파일 경로가 비어 있습니다.");
        }

        // 경로를 따옴표로 감싸서 입력해도 처리
        String pathText = filePath.trim();

        if (pathText.length() >= 2 &&
                pathText.startsWith("\"") &&
                pathText.endsWith("\"")) {
            pathText = pathText.substring(
                    1, pathText.length() - 1);
        }

        Path path = Paths.get(pathText);

        List<String> lines = Files.readAllLines(
                path, StandardCharsets.UTF_8);

        if (lines.size() < 2 || !HEADER.equals(lines.get(0))) {
            throw new IOException(
                    "올바른 알바노트 인수인계 파일이 아닙니다.");
        }

        ArrayList<Document> imported = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {

            String[] parts = lines.get(i).split("\t", -1);

            if (parts.length != 2) {
                throw new IOException(
                        (i + 1) + "번째 줄의 형식이 잘못되었습니다.");
            }

            try {
                String title = decode(parts[0]);
                String content = decode(parts[1]);

                if (title.trim().isEmpty() ||
                    content.trim().isEmpty()) {
                    throw new IllegalArgumentException();
                }

                imported.add(new Document(title, content));

            } catch (IllegalArgumentException e) {
                throw new IOException(
                        (i + 1) + "번째 줄의 데이터가 잘못되었습니다.",
                        e);
            }
        }

        return imported;
    }
}
