package examples.concurrent.basic;

public interface OrderBoard {
    void postOrder(Order toBeProcessed);

    Order cookOrder();
}
