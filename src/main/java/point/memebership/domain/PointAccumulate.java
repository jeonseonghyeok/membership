package point.memebership.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@Slf4j
@Entity
@Table(name = "point_acm")
public class PointAccumulate {

    @Id
    @GeneratedValue
    @Column @Getter
    private Long acm_key;

    @Column @Getter
    private int acm_point;

    @Column @Getter
    private int rmn_point;

    @Column @Getter
    private LocalDateTime create_date;

    @Column @Getter
    private LocalDateTime expiration_date;

    @Column @Getter
    private String reason;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_key")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_key")
    private Customer customer;

    @Column @Getter
    private String customer_phone;

    //==생성 메서드==//
    public static PointAccumulate createPoint(Store store,Customer customer,int acm_point,int expirationDays) {
        PointAccumulate pointAccumulate = new PointAccumulate();

        pointAccumulate.acm_point = acm_point;
        pointAccumulate.rmn_point = acm_point;
        pointAccumulate.store = store;
        pointAccumulate.customer =customer;
        pointAccumulate.customer_phone =customer.getPhone();

        pointAccumulate.create_date = LocalDateTime.now();
        pointAccumulate.expiration_date = LocalDateTime.of( LocalDate.now().plusDays(expirationDays) , LocalTime.MAX ) ;// 00:00:00

        return pointAccumulate;
    }
    //==비즈니스 로직==//
//    /** 주문 취소 */
//    public void cancel() {
//        if (delivery.getStatus() == DeliveryStatus.COMP) {
//            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
//        }
//        this.setStatus(OrderStatus.CANCEL);
//        for (OrderItem orderItem : orderItems) {
//            orderItem.cancel();
//        }
//    }
//    //==조회 로직==//
//    /** 전체 주문 가격 조회 */
//    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
//    }

}
