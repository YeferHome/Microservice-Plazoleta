package retoPragma.MicroPlazoleta.domain.model;

public class PageRequestModel {
    private final int page;
    private final int size;

    public PageRequestModel(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}

