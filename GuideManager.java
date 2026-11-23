public class GuideManager {

    // 1. 문서 작성 방법 안내
    public void showWritingGuide() {

        System.out.println("\n===== 문서 작성 방법 =====");

        System.out.println("1. 업무 제목을 명확하게 작성하세요.");
        System.out.println("   예: 편의점 택배 접수 방법");

        System.out.println("2. 업무를 수행하는 순서대로 설명하세요.");
        System.out.println("   처음 업무를 하는 사람도 이해할 수 있도록 작성하세요.");

        System.out.println("3. 필요한 준비물과 주의 사항을 작성하세요.");
        System.out.println("   특히 실수하기 쉬운 부분을 기록하면 좋습니다.");

        System.out.println("4. 문제 발생 시 대처 방법을 작성하세요.");
        System.out.println("   해결하기 어려운 상황은 담당자에게 문의하도록 안내하세요.");

        System.out.println("5. 작성한 문서를 다시 읽고 확인하세요.");
        System.out.println("   처음 보는 사람도 따라 할 수 있는지 점검하세요.");
    }

    // 2. 기본 문서 템플릿 출력
    public void showTemplate() {

        System.out.println("\n===== 업무 문서 기본 템플릿 =====");

        System.out.println("[업무명]");
        System.out.println("어떤 업무인지 간단하게 작성합니다.\n");

        System.out.println("[업무 설명]");
        System.out.println("업무의 목적과 수행 상황을 작성합니다.\n");

        System.out.println("[준비물]");
        System.out.println("업무 수행에 필요한 물품과 도구를 작성합니다.\n");

        System.out.println("[작업 순서]");
        System.out.println("1. 첫 번째 작업");
        System.out.println("2. 두 번째 작업");
        System.out.println("3. 세 번째 작업\n");

        System.out.println("[주의 사항]");
        System.out.println("실수하기 쉬운 부분과 확인할 사항을 작성합니다.\n");

        System.out.println("[문제 발생 시 대처 방법]");
        System.out.println("문제가 발생했을 때의 대응 절차를 작성합니다.\n");

        System.out.println("[추가 참고 사항]");
        System.out.println("업무 중 새롭게 알게 된 정보를 작성합니다.");
    }

    // 3. 문서 작성 예시 출력
    public void showExample() {

        System.out.println("\n===== 문서 작성 예시 =====");

        System.out.println("[업무명]");
        System.out.println("편의점 택배 접수 방법\n");

        System.out.println("[업무 설명]");
        System.out.println("고객이 요청한 택배를 접수하는 업무입니다.\n");

        System.out.println("[준비물]");
        System.out.println("택배 접수 기기, 운송장 출력 장치\n");

        System.out.println("[작업 순서]");
        System.out.println("1. 고객의 택배 접수 요청을 확인합니다.");
        System.out.println("2. 접수 기기에서 신청 정보를 확인합니다.");
        System.out.println("3. 포장 상태와 운송장 정보를 확인합니다.");
        System.out.println("4. 접수 완료 후 고객에게 안내합니다.\n");

        System.out.println("[주의 사항]");
        System.out.println("포장이 불량하거나 제한 품목인 경우");
        System.out.println("매장의 공식 지침을 먼저 확인합니다.\n");

        System.out.println("[문제 발생 시 대처 방법]");
        System.out.println("기기 오류나 접수 불가 상황에서는");
        System.out.println("담당자에게 문의합니다.\n");

        System.out.println("[추가 참고 사항]");
        System.out.println("매장별 접수 절차가 다를 수 있습니다.");
    }
}