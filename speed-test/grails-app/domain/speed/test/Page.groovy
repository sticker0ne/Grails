package speed.test

class Page {
    String url
    long time
    long size
    Date lastUpdateDate
    Integer count

    Page(String url, long time, long size, Date dt, Integer count) {
        this.url = url
        this.time = time
        this.size = size
        this.lastUpdateDate = dt
        this.count = count
    }
}
