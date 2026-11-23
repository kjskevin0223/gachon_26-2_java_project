// 2. 업무 문서 하나의 데이터를 보관하는 클래스
class Document {

    private String title;
    private String content;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
    this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
