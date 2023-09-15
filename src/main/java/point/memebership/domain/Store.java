package point.memebership.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    @Column(name = "store_key")
    @Getter
    private Long skey;

    @Column(unique = true, nullable = false)
    private String store_id;
    
    @Column(nullable = false)
    private String store_pw;
           
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;
    private String taxnumber;


    @OneToMany(mappedBy = "store")
    private List<PointAccumulate> points = new ArrayList<>();

    //==생성 메서드==//
    public static Store createStore(String name,String phone, String address) {
        Store store = new Store();
        store.name = name;
        store.phone = phone;
        store.address = address;

        return store;
    }
}
