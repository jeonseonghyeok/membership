package point.memebership.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_key")
    @Getter
    private Long ckey;

    @Column(unique = true)//주의! - null로 인서트시 방언 문제 가능성 존재
    private String customer_id;

    @Getter
    private String nickname;

    @Column(unique = true,nullable = false) @Getter
    private String phone;


    //==생성 메서드==//
    public static Customer createCustomer(String phone) {
        Customer customer = new Customer();
        customer.phone = phone;

        return customer;
    }
}
