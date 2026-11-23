import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileManager {

    private static final Path FILE_PATH =
            Paths.get("documents.txt");

    // 문자열을 Base64로 변환
    private String encode(String text) {
        return Base64.getEncoder().encodeToString(
                text.getBytes(StandardCharsets.UTF_8));
    }

    // Base64 문자열을 원래 문자열로 복원
    private String decode(String text) {
        byte[] bytes = Base64.getDecoder().decode(text);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // 문서 목록을 파일에 저장
    public void saveDocuments(ArrayList<Document> documents)
            throws IOException {

        List<String> lines = new ArrayList<>();

        for (Document document : documents) {

            String title = encode(document.getTitle());
            String content = encode(document.getContent());

            lines.add(title + "\t" + content);
        }

        // 임시 파일에 먼저 기록
        Path tempFile = Files.createTempFile(
                Paths.get(".").toAbsolutePath(),
                "albanote-", ".tmp");

        try {
            Files.write(
                    tempFile,
                    lines,
                    StandardCharsets.UTF_8
            );

            // 기록이 성공하면 기존 파일을 교체
            try {
                Files.move(
                        tempFile,
                        FILE_PATH,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );
            } catch (java.nio.file.AtomicMoveNotSupportedException e) {
                Files.move(
                        tempFile,
                        FILE_PATH,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    // 저장된 문서 목록 불러오기
    public ArrayList<Document> loadDocuments()
            throws IOException {

        ArrayList<Document> documents = new ArrayList<>();

        // 최초 실행 시 파일이 없어도 정상 진행
        if (Files.notExists(FILE_PATH)) {
            return documents;
        }

        List<String> lines = Files.readAllLines(
                FILE_PATH,
                StandardCharsets.UTF_8
        );

        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;

            String[] parts = line.split("\t", -1);

            if (parts.length != 2) {
                throw new IOException(
                        lineNumber + "번째 줄의 형식이 잘못되었습니다.");
            }

            try {
                String title = decode(parts[0]);
                String content = decode(parts[1]);

                if (title.trim().isEmpty()
                        || content.trim().isEmpty()) {
                    throw new IllegalArgumentException();
                }

                documents.add(new Document(title, content));

            } catch (IllegalArgumentException e) {
                throw new IOException(
                        lineNumber + "번째 줄의 문서 데이터가 손상되었습니다.",
                        e
                );
            }
        }

        return documents;
    }
}