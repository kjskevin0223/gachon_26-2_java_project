import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

// 1. 프로그램 실행 및 사용자 입력 담당
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        DocumentManager manager;

        try {
            manager = new DocumentManager();
            System.out.println("저장된 문서를 불러왔습니다.");
        } catch (IOException e) {
            System.out.println("문서를 불러오지 못했습니다.");
            System.out.println("원인: " + e.getMessage());
            System.out.println("기존 데이터를 보호하기 위해 종료합니다.");
            scanner.close();
            return;
        }

        HandoverManager handoverManager = new HandoverManager();
        GuideManager guideManager = new GuideManager();

        while (true) {
            System.out.println("\n===== 알바노트 =====");
            System.out.println("1. 업무 문서 작성");
            System.out.println("2. 저장된 문서 보기");
            System.out.println("3. 업무 문서 검색");
            System.out.println("4. 업무 문서 수정");
            System.out.println("5. 업무 문서 삭제");
            System.out.println("6. 업무 인수인계");
            System.out.println("7. 문서 작성 가이드");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            String menu = scanner.nextLine();

            // 문서 작성
            try {

                if (menu.equals("1")) {
                    System.out.print("문서 제목: ");
                    String title = scanner.nextLine();

                    System.out.print("문서 내용: ");
                    String content = scanner.nextLine();

                    boolean success = manager.addDocument(title, content);

                    if (success) {
                        System.out.println("문서 등록이 완료되었습니다!");
                    } else {
                        System.out.println("제목과 내용을 모두 입력해주세요.");
                    }

                // 모든 문서 조회
                } else if (menu.equals("2")) {

                    manager.showDocuments();

                    if (manager.isEmpty()) {
                        continue;
                    }

                    System.out.print(
                            "내용을 확인할 문서 번호 (0: 돌아가기): ");

                    String input = scanner.nextLine();

                    int number;

                    try {
                        number = Integer.parseInt(input.trim());
                    } catch (NumberFormatException e) {
                        System.out.println("올바른 숫자를 입력해주세요.");
                        continue;
                    }

                    if (number == 0) {
                        continue;
                    }

                    Document document = manager.getDocument(number);

                    if (document == null) {
                        System.out.println("존재하지 않는 문서 번호입니다.");
                        continue;
                    }

                    System.out.println("\n===== 문서 상세 내용 =====");
                    System.out.println("제목: " + document.getTitle());
                    System.out.println("내용: " + document.getContent());

                // 문서 검색
                } else if (menu.equals("3")) {

                    System.out.print("검색어 입력: ");
                    String keyword = scanner.nextLine();

                    if (keyword.trim().isEmpty()) {
                        System.out.println("검색어를 입력해주세요.");
                    } else {
                        ArrayList<Document> results = manager.searchDocuments(keyword);

                        if (results.isEmpty()) {
                            System.out.println("검색 결과가 없습니다.");
                        } else {
                            System.out.println("\n===== 검색 결과 =====");

                            for (Document document : results) {
                                System.out.println("제목: " + document.getTitle());
                                System.out.println("내용: " + document.getContent());
                                System.out.println("---------------------");
                            }
                        }
                    }

                // 문서 수정
                } else if (menu.equals("4")) {

                    manager.showDocuments();

                    if (manager.isEmpty()) {
                        continue;
                    }

                    System.out.print("수정할 문서 번호: ");
                    String input = scanner.nextLine();

                    int number;

                    try {
                        number = Integer.parseInt(input.trim());
                    } catch (NumberFormatException e) {
                        System.out.println("올바른 숫자를 입력해주세요.");
                        continue;
                    }

                    Document document = manager.getDocument(number);

                    if (document == null) {
                        System.out.println("존재하지 않는 문서 번호입니다.");
                        continue;
                    }

                    System.out.println("현재 제목: " + document.getTitle());
                    System.out.println("현재 내용: " + document.getContent());

                    System.out.print("새 제목 (Enter: 기존 유지): ");
                    String newTitle = scanner.nextLine();

                    System.out.print("새 내용 (Enter: 기존 유지): ");
                    String newContent = scanner.nextLine();

                    // 빈 입력 또는 공백만 입력하면 기존 데이터 유지
                    if (newTitle.trim().isEmpty()) {
                        newTitle = document.getTitle();
                    }

                    if (newContent.trim().isEmpty()) {
                        newContent = document.getContent();
                    }

                    // 실제 변경 사항이 있는지 확인
                    if (newTitle.equals(document.getTitle()) &&
                        newContent.equals(document.getContent())) {

                        System.out.println("변경된 내용이 없습니다.");

                    } else {

                        boolean success =
                                manager.updateDocument(number, newTitle, newContent);

                        if (success) {
                            System.out.println("문서 수정이 완료되었습니다!");
                        } else {
                            System.out.println("문서 수정에 실패했습니다.");
                            System.out.println("기존 문서는 변경되지 않았습니다.");
                        }
                    }

                // 문서 삭제
                } else if (menu.equals("5")) {

                    manager.showDocuments();

                    if (manager.isEmpty()) {
                        continue;
                    }

                    System.out.print("삭제할 문서 번호: ");
                    String input = scanner.nextLine();

                    int number;

                    try {
                        number = Integer.parseInt(input.trim());
                    } catch (NumberFormatException e) {
                        System.out.println("올바른 숫자를 입력해주세요.");
                        continue;
                    }

                    Document document = manager.getDocument(number);

                    if (document == null) {
                        System.out.println("존재하지 않는 문서 번호입니다.");
                        continue;
                    }

                    System.out.println("삭제할 문서: " + document.getTitle());
                    System.out.print("정말 삭제하시겠습니까? (Y/N): ");

                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {

                        boolean success = manager.deleteDocument(number);

                        if (success) {
                            System.out.println("문서 삭제가 완료되었습니다!");
                        } else {
                            System.out.println("문서 삭제에 실패했습니다.");
                        }

                    } else {
                        System.out.println("문서 삭제가 취소되었습니다.");
                    }
                

                } else if (menu.equals("6")) {

                    System.out.println("\n===== 업무 인수인계 =====");
                    System.out.println("1. 인수인계 문서 내보내기");
                    System.out.println("2. 인수인계 문서 가져오기");
                    System.out.println("0. 메인 메뉴로 돌아가기");
                    System.out.print("메뉴 선택: ");

                    String handoverMenu = scanner.nextLine();

                    // 1. 인수인계 문서 내보내기
                    if (handoverMenu.equals("1")) {

                        if (manager.isEmpty()) {
                            System.out.println("인수인계할 문서가 없습니다.");
                            continue;
                        }

                        manager.showDocuments();

                        System.out.print("\n인수인계 하시겠습니까? (Y/N): ");
                        String confirm = scanner.nextLine();

                        if (!confirm.equalsIgnoreCase("Y")) {
                            System.out.println("인수인계를 취소했습니다.");
                            continue;
                        }

                        System.out.print(
                                "인수인계할 문서 번호 (예: 1,2,5 / all): ");

                        String input = scanner.nextLine();

                        try {
                            ArrayList<Document> selected =
                                    manager.selectDocuments(input);

                            System.out.println("\n===== 선택한 문서 =====");

                            for (Document document : selected) {
                                System.out.println("- " + document.getTitle());
                            }

                            System.out.println("선택한 문서: " +
                                    selected.size() + "개");

                            System.out.print("파일로 내보내시겠습니까? (Y/N): ");
                            String saveConfirm = scanner.nextLine();

                            if (!saveConfirm.equalsIgnoreCase("Y")) {
                                System.out.println("인수인계를 취소했습니다.");
                                continue;
                            }

                            java.nio.file.Path output =
                                    handoverManager.exportDocuments(selected);

                            System.out.println("인수인계 파일 생성 완료!");
                            System.out.println("파일 위치: " + output);

                        } catch (IllegalArgumentException e) {
                            System.out.println("문서 선택 오류: " +
                                    e.getMessage());
                        }

                    // 2. 인수인계 문서 가져오기
                    } else if (handoverMenu.equals("2")) {

                        System.out.print("인수인계 파일 경로: ");
                        String filePath = scanner.nextLine();

                        try {
                            ArrayList<Document> imported =
                                    handoverManager.importDocuments(filePath);

                            System.out.println("\n===== 가져올 문서 =====");

                            for (Document document : imported) {
                                System.out.println("- " + document.getTitle());
                            }

                            System.out.println("가져올 문서: " +
                                    imported.size() + "개");

                            System.out.print(
                                    "내 문서 목록에 추가하시겠습니까? (Y/N): ");

                            String confirm = scanner.nextLine();

                            if (!confirm.equalsIgnoreCase("Y")) {
                                System.out.println("가져오기를 취소했습니다.");
                                continue;
                            }

                            boolean success =
                                    manager.addImportedDocuments(imported);

                            if (success) {
                                System.out.println("인수인계가 완료되었습니다!");
                                System.out.println(imported.size() +
                                        "개의 문서가 추가되었습니다.");
                            } else {
                                System.out.println("문서를 가져오지 못했습니다.");
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println("파일 경로 오류: " +
                                    e.getMessage());
                        }

                    } else if (handoverMenu.equals("0")) {

                        System.out.println("메인 메뉴로 돌아갑니다.");

                    } else {
                        System.out.println("올바른 번호를 입력해주세요.");
                    }

                } else if (menu.equals("7")) {

                    while (true) {

                        System.out.println("\n===== 문서 작성 가이드 =====");
                        System.out.println("1. 문서 작성 방법");
                        System.out.println("2. 기본 문서 템플릿 확인");
                        System.out.println("3. 문서 작성 예시");
                        System.out.println("0. 메인 메뉴로 돌아가기");
                        System.out.print("메뉴 선택: ");

                        String guideMenu = scanner.nextLine();

                        if (guideMenu.equals("1")) {

                            guideManager.showWritingGuide();

                        } else if (guideMenu.equals("2")) {

                            guideManager.showTemplate();

                        } else if (guideMenu.equals("3")) {

                            guideManager.showExample();

                        } else if (guideMenu.equals("0")) {

                            System.out.println("메인 메뉴로 돌아갑니다.");
                            break;

                        } else {

                            System.out.println("올바른 메뉴 번호를 입력해주세요.");
                        }
                    }                                
                // 프로그램 종료
                } else if (menu.equals("0")) {
                    System.out.println("알바노트를 종료합니다.");
                    break;

                } else {
                    System.out.println("올바른 메뉴 번호를 입력해주세요.");
                }
            } catch (IOException e) {

            System.out.println("문서 파일 저장에 실패했습니다.");
            System.out.println("원인: " + e.getMessage());
            System.out.println("이번 변경 사항은 적용되지 않았습니다.");
            }
        }
        scanner.close();
    }
}