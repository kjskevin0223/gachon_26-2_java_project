import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

// 3. 여러 업무 문서의 등록과 조회를 담당하는 클래스
class DocumentManager {

    private ArrayList<Document> documents;
    private FileManager fileManager;

    // 프로그램 시작 시 저장된 문서 불러오기
    public DocumentManager() throws IOException {
        fileManager = new FileManager();
        documents = fileManager.loadDocuments();
    }

    // 새로운 문서 등록
    public boolean addDocument(String title, String content)

        throws IOException {

        if (title == null || content == null ||
            title.trim().isEmpty() || content.trim().isEmpty()) {
            return false;
        }

        ArrayList<Document> updated = new ArrayList<>(documents);
        updated.add(new Document(title, content));

        fileManager.saveDocuments(updated);
        documents = updated;

        return true;
    }

    // 문서 제목 목록 출력
    public void showDocuments() {

        if (documents.isEmpty()) {
            System.out.println("저장된 문서가 없습니다.");
            return;
        }

        System.out.println("\n===== 저장된 업무 문서 =====");

        for (int i = 0; i < documents.size(); i++) {
            Document document = documents.get(i);

            System.out.println(
                    (i + 1) + ". " + document.getTitle()
            );
        }
    }

    // 제목으로 문서 검색
    public ArrayList<Document> searchDocuments(String keyword) {

        ArrayList<Document> results = new ArrayList<>();

        for (Document document : documents) {

            if (document.getTitle().toLowerCase()
                    .contains(keyword.trim().toLowerCase())) {

                results.add(document);
            }
        }

        return results;
    }

    // 수정 및 삭제를 위한 문서 번호 조회
    public Document getDocument(int number) {

        if (number < 1 || number > documents.size()) {
            return null;
        }

        return documents.get(number - 1);
    }

    // 선택한 문서 수정
    public boolean updateDocument(
            int number, String newTitle, String newContent) 
            throws IOException {

        if (getDocument(number) == null ||
            newTitle == null || newContent == null ||
            newTitle.trim().isEmpty() ||
            newContent.trim().isEmpty()) {
            return false;
        }

        ArrayList<Document> updated = new ArrayList<>(documents);

        // 기존 객체를 직접 변경하지 않고 새 객체로 교체
        updated.set(number - 1,
                new Document(newTitle, newContent));

        fileManager.saveDocuments(updated);
        documents = updated;

        return true;
    }

    // 선택한 문서 삭제
    public boolean deleteDocument(int number) {

        if (getDocument(number) == null) {
            return false;
        }

        documents.remove(number - 1);
        return true;
    }

    // 문서 목록이 비어 있는지 확인
    public boolean isEmpty() {
        return documents.isEmpty();
    }

    // 인수인계할 문서 번호를 해석하여 목록 반환
    public ArrayList<Document> selectDocuments(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "문서 번호를 입력해주세요.");
        }

        if (documents.isEmpty()) {
            throw new IllegalArgumentException(
                    "저장된 문서가 없습니다.");
        }

        ArrayList<Document> selected = new ArrayList<>();

        // all 입력 시 전체 문서 선택
        if (input.trim().equalsIgnoreCase("all")) {
            selected.addAll(documents);
            return selected;
        }

        // 중복 번호를 제거하면서 입력 순서 유지
        LinkedHashSet<Integer> numbers = new LinkedHashSet<>();

        String[] parts = input.split(",", -1);

        for (String part : parts) {
            int number;

            try {
                number = Integer.parseInt(part.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "문서 번호는 숫자로 입력해주세요.");
            }

            if (getDocument(number) == null) {
                throw new IllegalArgumentException(
                        number + "번 문서는 존재하지 않습니다.");
            }

            numbers.add(number);
        }

        // 모든 번호를 확인한 후에만 선택 결과 생성
        for (int number : numbers) {
            selected.add(getDocument(number));
        }

        return selected;
    }   
    // 인수인계받은 문서를 개인 목록에 추가
    public boolean addImportedDocuments(
            ArrayList<Document> imported) throws IOException {

        if (imported == null || imported.isEmpty()) {
            return false;
        }

        // 가져온 모든 문서가 유효한지 검사
        for (Document document : imported) {
            if (document == null ||
                document.getTitle() == null ||
                document.getContent() == null ||
                document.getTitle().trim().isEmpty() ||
                document.getContent().trim().isEmpty()) {
                return false;
            }
        }

        ArrayList<Document> updated = new ArrayList<>(documents);

        // 원본과 별개의 새 문서 객체를 생성
        for (Document document : imported) {
            updated.add(new Document(
                    document.getTitle(),
                    document.getContent()));
        }

        // 먼저 파일에 저장하고 성공하면 메모리에 반영
        fileManager.saveDocuments(updated);
        documents = updated;

        return true;
    }
}