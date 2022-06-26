import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<Stations> availableStations;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orders = orderList;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Stations> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<Stations> availableStations) {
        this.availableStations = availableStations;
    }

}
